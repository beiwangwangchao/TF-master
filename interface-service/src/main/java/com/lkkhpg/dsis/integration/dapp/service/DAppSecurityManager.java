/*
 *
 */

package com.lkkhpg.dsis.integration.dapp.service;

import static com.lkkhpg.dsis.integration.dapp.service.DAppSecurityManager.TOPIC_DELETE;
import static com.lkkhpg.dsis.integration.dapp.service.DAppSecurityManager.TOPIC_UPDATE;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.lkkhpg.dsis.integration.ComparableNameValuePair;
import com.lkkhpg.dsis.integration.dapp.AppCode;
import com.lkkhpg.dsis.integration.dapp.IDAConstants;
import com.lkkhpg.dsis.integration.dapp.Token;
import com.lkkhpg.dsis.integration.dapp.adaptor.FeedJsonRequestBodyAdvice;
import com.lkkhpg.dsis.integration.dapp.controllers.DAppBaseController;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;
import com.lkkhpg.dsis.integration.utils.IpUtils;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.message.IMessageConsumer;
import com.lkkhpg.dsis.platform.message.TopicMonitor;
import com.lkkhpg.dsis.platform.message.profile.GlobalProfileListener;

/**
 * @author shengyang.zhou@hand-china.com
 */
@Component
@TopicMonitor(channel = { TOPIC_DELETE, TOPIC_UPDATE })
public class DAppSecurityManager
        implements IMessageConsumer<HashMap<String, String>>, InitializingBean, GlobalProfileListener {

    public static final String TOPIC_DELETE = "topic:dappcode:delete";
    public static final String TOPIC_UPDATE = "topic:dappcode";

    private Map<String, AppCode> codeMap = new HashMap<>();

    private int db = 6;

    private String prefix = "hap:interface:dapp";

    private int tokenTTL = 7200;

    private boolean securityEnabled = false;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    public AppCode getAppCode(String code) {
        return codeMap.get(code);
    }

    public void setAppCodes(List<AppCode> list) {
        list.forEach(e -> {
            if (codeMap.containsKey(e.getCode())) {
                throw new RuntimeException("appCode duplicate:" + e.getCode());
            }
            codeMap.put(e.getCode(), e);
        });
    }

    /**
     * gen token info by token.
     * 
     * @param accessToken
     *            token
     * @return Token or null (token not exists or expired.)
     */
    public Token getToken(String accessToken) {
        final String key = getTokenFullKey(accessToken);
        Map<String, String> data = redisTemplate.execute((RedisCallback<Map<String, String>>) (connection) -> {
            connection.select(getDb());
            Map<byte[], byte[]> data1 = connection.hGetAll(key.getBytes());
            if (data1 == null) {
                return null;
            }
            Map<String, String> data2 = new HashMap<>();
            data1.forEach((k, v) -> data2.put(new String(k), new String(v)));
            return data2;
        });
        if (data == null || data.isEmpty()) {
            return null;
        }
        Token token = new Token();
        data.forEach((k, v) -> {
            try {
                BeanUtils.setProperty(token, k, v);
            } catch (Exception e) {
                if (logger.isErrorEnabled()) {
                    logger.error(e.getMessage(), e);
                }
            }
        });
        return token;
    }

    /**
     * do authentication with appCode and appSecret on HTTP header.
     * 
     * @param request
     *            HttpServletRequest
     * @return Token
     * @throws DAppException
     *             On any error
     */
    public Token auth(HttpServletRequest request) throws DAppException {
        String appCode = getHeaderRequired(request, "appCode");
        String appSecret = getHeaderRequired(request, "appSecret");

        AppCode code = getAppCode(appCode);

        if (code == null) {
            throw new DAppException(IDAConstants.MSG_10006, IDAConstants.ERROR_10006, appCode);
        }
        if (code.expired()) {
            throw new DAppException(IDAConstants.MSG_10007, IDAConstants.ERROR_10007, appCode);
        }
        if (!code.getSecret().equalsIgnoreCase(appSecret)) {
            throw new DAppException(IDAConstants.MSG_10008, IDAConstants.ERROR_10008, appSecret);
        }
        final String accessToken = StringUtils.reverse(UUID.randomUUID().toString().replace("-", ""));

        final String tokenFullKey = getTokenFullKey(accessToken);

        Token token = new Token();
        token.setAppCode(appCode);
        token.setAppSecret(appSecret);
        token.setToken(accessToken);
        token.setRefreshCount(0);
        token.setExpireAt(System.currentTimeMillis() + getTokenTTL() * 1000);

        try {
            final Map<String, String> tokenInMap = BeanUtils.describe(token);
            tokenInMap.remove("class");
            redisTemplate.execute((RedisCallback<Object>) (connection) -> {
                connection.select(getDb());
                byte[] keyBytes = tokenFullKey.getBytes();
                tokenInMap.forEach((k, v) -> {
                    if (v != null) {
                        connection.hSet(keyBytes, k.getBytes(), v.getBytes());
                    }
                });
                connection.expire(keyBytes, getTokenTTL());
                if (logger.isDebugEnabled()) {
                    logger.debug("create token:{},appCode:{}", accessToken, appCode);
                }
                return null;
            });
        } catch (Exception e) {
            throw new DAppException(e.getMessage(), IDAConstants.ERROR_10000, null);
        }
        return token;
    }

    public Token refreshToken(HttpServletRequest request) throws DAppException {
        String accessToken = getHeaderRequired(request, "token");
        String appCode = getHeaderRequired(request, "appCode");
        AppCode code = getAppCode(appCode);
        if (code == null) {
            throw new DAppException(IDAConstants.MSG_10006, IDAConstants.ERROR_10006, appCode);
        }
        if (code.expired()) {
            throw new DAppException(IDAConstants.MSG_10007, IDAConstants.ERROR_10007, appCode);
        }
        final Token token = getToken(accessToken);
        if (token == null) {
            throw new DAppException(IDAConstants.MSG_10003, IDAConstants.ERROR_10003, accessToken);
        }
        if (!appCode.equalsIgnoreCase(token.getAppCode())) {
            // rarely happens when users refresh a token that not belongs to him
            throw new DAppException(IDAConstants.MSG_10006, IDAConstants.ERROR_10006, appCode);
        }
        final String tokenFullKey = getTokenFullKey(accessToken);

        redisTemplate.execute((RedisCallback<Object>) (connection) -> {
            connection.select(getDb());
            byte[] keyBytes = tokenFullKey.getBytes();
            long rc = connection.hIncrBy(keyBytes, "refreshCount".getBytes(), 1);
            connection.expire(keyBytes, getTokenTTL());
            token.setRefreshCount((int) rc);
            token.setExpireAt(System.currentTimeMillis() + getTokenTTL() * 1000);
            if (logger.isDebugEnabled()) {
                logger.debug("refresh token:{},appCode:{},refreshCount:{}", accessToken, appCode, rc);
            }
            return null;
        });
        return token;
    }

    public void checkAccess(HttpServletRequest request) throws DAppException {
        String accessToken = getHeaderRequired(request, "token");
        String sign = getHeaderRequired(request, "sign");
        String timestamp = getHeaderRequired(request, "timestamp");

        Token token = getToken(accessToken);
        if (token == null) {
            throw new DAppException(IDAConstants.MSG_10003, IDAConstants.ERROR_10003, accessToken);
        }

        AppCode code = getAppCode(token.getAppCode());
        if (code == null) {
            throw new DAppException(IDAConstants.MSG_10006, IDAConstants.ERROR_10006, null);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("appCode:{}, white list enabled:{}", code.getCode(), code.isWhiteIpListEnabled());
        }
        if (code.isWhiteIpListEnabled()) {
            String ip = IpUtils.getIpAddr(request);
            if (!"127.0.0.1".equals(ip) && ip.matches("\\d+\\.\\d+\\.\\d+\\.\\d+")) {
                String[] ips = StringUtils.split(ip, '.');
                boolean ok = false;
                for (String wip : code.getWhiteIps()) {
                    if (ipMatchPattern(ips, wip)) {
                        ok = true;
                        if (logger.isDebugEnabled()) {
                            logger.debug("ip:{} match pattern:{}", ip, wip);
                        }
                        break;
                    }
                }
                if (!ok) {
                    throw new DAppException(IDAConstants.MSG_10004, IDAConstants.ERROR_10004, ip);
                }
            }
        }

        checkSign(request, timestamp, sign, token.getAppSecret());

    }

    /**
     * get a header field from request .
     * 
     * @param request
     *            HttpServletRequest
     * @param field
     *            field name
     * @return field value
     * @throws DAppException
     *             ERROR_10002 ,if the field is not exists or empty
     */
    private String getHeaderRequired(HttpServletRequest request, String field) throws DAppException {
        String header = request.getHeader(field);
        if (StringUtils.isEmpty(header)) {
            throw new DAppException(IDAConstants.MSG_10002, IDAConstants.ERROR_10002, field);
        }
        return header;
    }

    private void checkSign(HttpServletRequest request, String timestamp, String sign, String secret)
            throws DAppException {
        String url = request.getRequestURI();
        int idx = url.indexOf(DAppBaseController.DAPP_FLAG);
        String uri = url.substring(idx + DAppBaseController.DAPP_FLAG.length());
        List<ComparableNameValuePair> queryParameters = new ArrayList<>();
        Enumeration<String> pNames = request.getParameterNames();
        while (pNames.hasMoreElements()) {
            String name = pNames.nextElement();
            queryParameters.add(new ComparableNameValuePair(name, request.getParameter(name)));
        }
        String currentBody = FeedJsonRequestBodyAdvice.getAndRemoveBody();
        String sign2 = getSign(uri, secret, timestamp, queryParameters, currentBody);
        if (!sign.equalsIgnoreCase(sign2)) {
            throw new DAppException(IDAConstants.MSG_10005, IDAConstants.ERROR_10005, null);
        }
    }

    public static String getSign(String uri, String appSecret, String timestamp,
            List<ComparableNameValuePair> queryParameters, String postBody) {
        StringBuilder content = new StringBuilder();
        content.append(uri);
        content.append(appSecret);
        content.append(timestamp);
        Collections.sort(queryParameters);
        queryParameters.forEach(p -> content.append(p.getName()).append(p.getValue()));
        if (postBody != null) {
            content.append(postBody);
        }
        return DigestUtils.sha256Hex(content.toString());
    }

    /**
     * check ip match pattern .
     * 
     * @param ip
     *            ipv4
     * @param wip
     *            a pattern
     * @return match :true, else :false
     */
    private boolean ipMatchPattern(String[] ip, String wip) {
        try {
            String[] wipp = wip.split("\\.");
            for (int i = 0; i < ip.length; i++) {
                String p = wipp[i];
                if ("*".equals(p) || p.equals(ip[i])) {
                    continue;
                }
                if (p.contains("-")) {
                    String[] range = p.split("-");
                    int ipi = Integer.parseInt(ip[i]);
                    if (ipi >= Integer.parseInt(range[0]) && ipi <= Integer.parseInt(range[1])) {
                        continue;
                    }
                }
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String getTokenFullKey(String token) {
        return getPrefix() + ":token:" + token;
    }

    private String getLockFullKey(String key) {
        return getPrefix() + ":lock:" + key;
    }

    public int getDb() {
        return db;
    }

    /**
     * which db (of redis) to store token.
     * <p>
     * default:6
     * 
     * @param db
     *            db
     */
    public void setDb(int db) {
        this.db = db;
    }

    public String getPrefix() {
        return prefix;
    }

    /**
     * which schema to store token.
     * <p>
     * default:hap:interface:dapp
     * 
     * @param prefix
     *            prefix
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getTokenTTL() {
        return tokenTTL;
    }

    /**
     * token time to live(in seconds).
     * <p>
     * default:7200
     * 
     * @param tokenTTL
     *            time in seconds
     */
    public void setTokenTTL(int tokenTTL) {
        this.tokenTTL = tokenTTL;
    }

    @Override
    public void onMessage(HashMap<String, String> message, String pattern) {
        if (TOPIC_DELETE.equals(pattern)) {
            codeMap.remove(message.get("appCode"));
        } else {
            AppCode code = codeMap.get(message.get("appCode"));
            if (code == null) {
                code = new AppCode();
                code.setCode(message.get("appCode"));
                codeMap.put(code.getCode(), code);
            }
            code.setSecret(message.get("appSecret"));
            code.setWhiteIpListEnabled("Y".equals(message.get("whiteListEnabled")));
            String[] list = StringUtils.split(message.get("whiteList"), " ;,\n\r\t");
            if (list == null) {
                list = new String[0];
            }
            code.setWhiteIps(Arrays.asList(list));
            String expire = message.get("expire");
            if (StringUtils.isNotBlank(expire)) {
                try {
                    Date d = new SimpleDateFormat(BaseConstants.DATE_FORMAT).parse(expire);
                    code.setExpire(d);
                } catch (ParseException e) {
                }
            }
        }
    }

    @Override
    public void afterPropertiesSet() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            List list = sqlSession
                    .selectList("com.lkkhpg.dsis.admin.integration.dapp.mapper.IsgDappAuthCodeMapper.selectList");
            for (Object o : list) {
                Map<String, Object> map = PropertyUtils.describe(o);
                HashMap<String, String> code = new HashMap<>();
                map.forEach((k, v) -> {
                    if (v instanceof Date) {
                        v = DateFormatUtils.format((Date) v, BaseConstants.DATE_FORMAT);
                    }
                    code.put(k, String.valueOf(v));
                });
                onMessage(code, TOPIC_UPDATE);
            }
        } catch (Exception e) {
            // ignore
        }
    }

    public boolean isSecurityEnabled() {
        return securityEnabled;
    }

    @Override
    public List<String> getAcceptedProfiles() {
        return Arrays.asList("ISG.DAPP_ENABLE_SECURITY");
    }

    @Override
    public void updateProfile(String profileName, String profileValue) {
        securityEnabled = Boolean.valueOf(profileValue);
    }
}
