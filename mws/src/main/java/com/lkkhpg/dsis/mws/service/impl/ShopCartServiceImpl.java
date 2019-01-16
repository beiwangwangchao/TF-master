/*
 *
 */
package com.lkkhpg.dsis.mws.service.impl;

import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.mws.constant.MwsOrderConstants;
import com.lkkhpg.dsis.mws.constant.ProductConstants;
import com.lkkhpg.dsis.mws.dto.Product;
import com.lkkhpg.dsis.mws.dto.ShopCartInfo;
import com.lkkhpg.dsis.mws.dto.ShopCartItem;
import com.lkkhpg.dsis.mws.exception.MemberException;
import com.lkkhpg.dsis.mws.service.IProductService;
import com.lkkhpg.dsis.mws.service.IShopCartService;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * MWS购物车Service实现类.
 * 
 * @author gulin
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ShopCartServiceImpl implements IShopCartService {
    public static final String DATE_FORMATE = "yyyy-MM-dd HH:mm:ss.SSS";
    
    private final Logger logger = LoggerFactory.getLogger(ShopCartServiceImpl.class);

    @Autowired
    public RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    public IProductService productService;

    public static final String CATEGORY = "mws:shopcart";
    public static final String MAPPING_KEY = CATEGORY + ":membermapping";
    public static final String INFO_KEY = "info";
    public static final String CONTENT_KEY = "content";
    protected SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMATE);

    private Map<byte[], byte[]> describe(Object obj) {
        RedisSerializer<String> stringRedisSerializer = redisTemplate.getStringSerializer();
        Map<byte[], byte[]> cartBytes = new HashMap<byte[], byte[]>();
        try {
            Map<String, Object> beanMap = PropertyUtils.describe(obj);
            beanMap.remove("class");
            beanMap.forEach((k, v) -> {
                byte[] keyBytes = stringRedisSerializer.serialize(k);
                if (v instanceof Date) {
                    byte[] valueBytes = stringRedisSerializer.serialize(dateFormat.format(v));
                    cartBytes.put(keyBytes, valueBytes);
                } else if (v != null) {
                    byte[] valueBytes = stringRedisSerializer.serialize(v.toString());
                    cartBytes.put(keyBytes, valueBytes);
                }
            });
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("shopcart redis describe error", e);
            }
        }
        return cartBytes;
    }

    private void construct(Map<byte[], byte[]> dataBytes, Object bean) {
        RedisSerializer<String> stringRedisSerializer = redisTemplate.getStringSerializer();
        dataBytes.forEach((k, v) -> {
            String pName = stringRedisSerializer.deserialize(k);
            String pValue = stringRedisSerializer.deserialize(v);
            try {
                PropertyDescriptor pd = PropertyUtils.getPropertyDescriptor(bean, pName);
                if (pd != null) {
                    Class<?> pType = pd.getPropertyType();
                    if (pType == Date.class) {
                        BeanUtils.setProperty(bean, pName, dateFormat.parse(pValue));
                    } else {
                        BeanUtils.setProperty(bean, pName, pValue);
                    }
                }
            } catch (Exception e) {
                if (logger.isErrorEnabled()) {
                    logger.error("shopcart redis construct error", e);
                }
            }
        });
    }

    protected String getShopCartInfoKey(String uuid) {
        return CATEGORY + ":" + uuid + ":" + INFO_KEY;
    }

    protected String getShopCartContentKey(String uuid) {
        return CATEGORY + ":" + uuid + ":" + CONTENT_KEY;
    }

    protected String getShopCartItemKey(String uuid, Object productId) {
        return getShopCartContentKey(uuid) + ":" + productId;
    }

    protected String getMappingKey(Long memberId, Long storedId) {
        return memberId.toString() + "-" + storedId.toString();
    }

    /**
     * 获取当前会员销售区域可用购物车uuid，若没有，则新建.
     * 
     * @param request
     *            统一上下文.
     * @return 购物车uuid.
     */
    private String getAccessibleShopCartUuid(IRequest request) {
        // HttpSession session = request.getSession(false);
        Long memberId = null;
        Long salesOrgId = null;
        if (request != null) {
            memberId = Long.parseLong(request.getAttribute(Member.FIELD_MEMBER_ID).toString());
            salesOrgId = Long.parseLong(request.getAttribute(SystemProfileConstants.SALES_ORG_ID).toString());
        }
        // 判断当前用户是否已有购物车
        ShopCartInfo info = this.getUserShopCart(memberId, salesOrgId);
        if (info != null) {
            // 已有购物车信息，返回购物车uuid
            return info.getUuid();
        } else {
            // 没有，则生成
            return createShopCartForMember(memberId, salesOrgId);
        }
    }

    /**
     * 为当前用户，当前销售区域创建购物车.
     * 
     * @param memberId
     *            会员Id.
     * @param salesOrgId
     *            销售区域ID.
     * @return 返回新建购物车uuid.
     */
    private String createShopCartForMember(Long memberId, Long salesOrgId) {
        String uuid = UUID.randomUUID().toString();
        this.bindShopCartWithMember(uuid, memberId, salesOrgId);
        return uuid;
    }

    /**
     * 根据会员ID，销售组织ID获取购物车信息.
     * 
     * @param memberId
     *            会员ID.
     * @param salesOrgId
     *            销售组织ID.
     * @return 返回购物车信息，若无则返回为空.
     */
    private ShopCartInfo getUserShopCart(Long memberId, Long salesOrgId) {
        RedisSerializer<String> stringRedisSerializer = redisTemplate.getStringSerializer();
        return (ShopCartInfo) redisTemplate.execute((RedisCallback<ShopCartInfo>) (connection) -> {
            byte[] memberMappingKeyBytes = stringRedisSerializer.serialize(MAPPING_KEY);
            byte[] memberIdBytes = stringRedisSerializer.serialize(getMappingKey(memberId, salesOrgId));
            List<byte[]> uuidByteList = connection.hMGet(memberMappingKeyBytes, memberIdBytes);
            if (uuidByteList.size() == 0) {
                return (ShopCartInfo) null;
            }
            String uuid = stringRedisSerializer.deserialize(uuidByteList.get(0));
            byte[] shopCartInfoKeyByte = stringRedisSerializer.serialize(getShopCartInfoKey(uuid));
            Map<byte[], byte[]> shopCartInfoByte = connection.hGetAll(shopCartInfoKeyByte);
            if (shopCartInfoByte.size() == 0) {
                return (ShopCartInfo) null;
            }
            ShopCartInfo info = new ShopCartInfo();
            construct(shopCartInfoByte, info);
            return info;
        });
    }

    /**
     * 将当前用户，销售区域Id与购物车uuid绑定.
     * 
     * @param uuid
     *            购物车uuid.
     * @param memberId
     *            会员Id.
     * @param salesOrgId
     *            销售区域Id.
     */
    private void bindShopCartWithMember(String uuid, Long memberId, Long salesOrgId) {
        RedisSerializer<String> stringRedisSerializer = redisTemplate.getStringSerializer();
        redisTemplate.execute((RedisCallback<Object>) (connection) -> {
            byte[] memberMappingKeyBytes = stringRedisSerializer.serialize(MAPPING_KEY);
            byte[] memberIdBytes = stringRedisSerializer.serialize(getMappingKey(memberId, salesOrgId));
            Map<byte[], byte[]> memberMapping = new HashMap<>();
            // memberId:salesOrgId-->uuid
            memberMapping.put(memberIdBytes, stringRedisSerializer.serialize(uuid));
            connection.hMSet(memberMappingKeyBytes, memberMapping);
            // create or overwrite shop cart info
            byte[] shopCartInfoKeyByte = stringRedisSerializer.serialize(getShopCartInfoKey(uuid));
            ShopCartInfo info = new ShopCartInfo();
            info.setUuid(uuid);
            info.setMemberId(memberId);
            info.setSalesOrgId(salesOrgId);
            Map<byte[], byte[]> shopCartInfoByte = describe(info);
            connection.hMSet(shopCartInfoKeyByte, shopCartInfoByte);
            return null;
        });
    }

    /**
     * 验证明细是否合法.
     * 
     * @param cart
     *            购物车明细.
     */
    private void verifyShopCart(ShopCartItem cart) {
        if (cart.getProductId() == null) {
            throw new NullPointerException("productId is null");
        }
        if (BigDecimal.ZERO.compareTo(cart.getQuantity()) >= 0
                || new BigDecimal(ProductConstants.SHOPCART_QUANTITY_MAX).compareTo(cart.getQuantity()) < 0) {
            throw new IllegalArgumentException("quantity is invalid:" + cart.getQuantity());
        }
    }

    /**
     * 根据购物车uuid，商品id获取购物车明细.
     * 
     * @param uuid
     *            购物车uuid.
     * @param productId
     *            商品id.
     * @return 返回购物车明细.
     */
    private ShopCartItem getShopCartItem(String uuid, Long productId) {
        RedisSerializer<String> stringRedisSerializer = redisTemplate.getStringSerializer();
        return (ShopCartItem) redisTemplate.execute((RedisCallback<ShopCartItem>) (connection) -> {
            byte[] shopCartItemKeyBytes = stringRedisSerializer.serialize(getShopCartItemKey(uuid, productId));
            Map<byte[], byte[]> cartItemBytes = connection.hGetAll(shopCartItemKeyBytes);
            if (cartItemBytes.size() == 0) {
                return (ShopCartItem) null;
            }
            ShopCartItem item = new ShopCartItem();
            construct(cartItemBytes, item);
            return item;
        });
    }

    /**
     * 增加商品至购物车，若购物车中以有该商品，则增加数量.
     * 
     * @param cartItem
     *            增加的商品明细.
     */
    private void insertOrUpdateShopCart(ShopCartItem cartItem) {
        RedisSerializer<String> stringRedisSerializer = redisTemplate.getStringSerializer();
        redisTemplate.execute((RedisCallback<ShopCartItem>) (connection) -> {
            byte[] shopCartContentKeyBytes = stringRedisSerializer.serialize(getShopCartContentKey(cartItem.getUuid()));
            byte[] productIdByte = stringRedisSerializer.serialize(cartItem.getProductId().toString());
            Double score = connection.zScore(shopCartContentKeyBytes, productIdByte);
            if (score == null) {
                connection.zAdd(shopCartContentKeyBytes, System.currentTimeMillis(), productIdByte);
            }
            byte[] shopCartItemKeyBytes = stringRedisSerializer
                    .serialize(getShopCartItemKey(cartItem.getUuid(), cartItem.getProductId()));
            Map<byte[], byte[]> cartBytes = describe(cartItem);
            connection.hMSet(shopCartItemKeyBytes, cartBytes);
            return (ShopCartItem) null;
        });
    }

    /**
     * 从购物车中移除商品.
     * 
     * @param shopCartItem
     *            移除的商品明细.
     */
    private void removeShopCart(ShopCartItem shopCartItem) {
        RedisSerializer<String> stringRedisSerializer = redisTemplate.getStringSerializer();
        redisTemplate.execute((RedisCallback<ShopCartItem>) (connection) -> {
            byte[] shopCartContentKeyBytes = stringRedisSerializer
                    .serialize(getShopCartContentKey(shopCartItem.getUuid()));
            byte[] productIdByte = stringRedisSerializer.serialize(shopCartItem.getProductId().toString());
            connection.zRem(shopCartContentKeyBytes, productIdByte);
            byte[] shopCartItemKeyBytes = stringRedisSerializer
                    .serialize(getShopCartItemKey(shopCartItem.getUuid(), shopCartItem.getProductId()));
            connection.del(shopCartItemKeyBytes);
            return null;
        });
    }

    /**
     * 根据购物车uuid获取当前购物车明细.
     * 
     * @param uuid
     *            购物车uuid.
     * @return 购物车明细集合.
     */
    private List<ShopCartItem> getShopCartItems(String uuid) {
        RedisSerializer<String> stringRedisSerializer = redisTemplate.getStringSerializer();
        return (List<ShopCartItem>) redisTemplate.execute((RedisCallback<List<ShopCartItem>>) (connection) -> {
            byte[] shopCartContentKeyBytes = stringRedisSerializer.serialize(getShopCartContentKey(uuid));
            Set<byte[]> productsItSet = connection.zRange(shopCartContentKeyBytes, 0, -1);
            List<ShopCartItem> shopCartItems = new ArrayList<ShopCartItem>();
            for (byte[] productId : productsItSet) {
                String pid = stringRedisSerializer.deserialize(productId);
                byte[] shopCartItemKeyBytes = stringRedisSerializer.serialize(getShopCartItemKey(uuid, pid));
                Map<byte[], byte[]> cartItemBytes = connection.hGetAll(shopCartItemKeyBytes);
                if (cartItemBytes.size() == 0) {
                    continue;
                }
                ShopCartItem item = new ShopCartItem();
                construct(cartItemBytes, item);
                shopCartItems.add(item);
            }
            return shopCartItems;
        });
    }

    private Integer getShopCartCount(String uuid) {
        RedisSerializer<String> stringRedisSerializer = redisTemplate.getStringSerializer();
        return (Integer) redisTemplate.execute((RedisCallback<Integer>) (connection) -> {
            byte[] shopCartContentKeyBytes = stringRedisSerializer.serialize(getShopCartContentKey(uuid));
            Long count = connection.zCard(shopCartContentKeyBytes);
            return count.intValue();
        });
    }

    private List<Product> queryProductDetails(IRequest irequest, List<ShopCartItem> shopCartItems) {
        Product product;
        List<Product> shopProducts = new ArrayList<Product>();
        for (ShopCartItem temp : shopCartItems) {
            product = new Product();
            product.setItemId(temp.getProductId());
            product.setFunctionArea("WEB");
            List<Product> products = productService.getSimpleProductsByWhereClause(irequest, product, 1, -1);
            if (products.size() > 0) {
                Product shopProduct = products.get(0);
                if (null != temp.getQuantity()) {
                    shopProduct.setItemAmount(Long.parseLong(temp.getQuantity().toString()));
                }
                shopProducts.add(shopProduct);
            } else {
                removeShopCart(temp);
            }
        }
        return shopProducts;
    }

    /**
     * 生成token.
     * 
     * @return 返回 UUID 形式的 token
     */
    private String generateCaptchaKey() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void insertShopCartItem(ShopCartItem shopCartItem, IRequest request) throws MemberException {
        try {
            verifyShopCart(shopCartItem);
            String cartUuid = getAccessibleShopCartUuid(request);
            shopCartItem.setUuid(cartUuid);
            ShopCartItem item = this.getShopCartItem(cartUuid, shopCartItem.getProductId());
            if (item == null) {
                shopCartItem.setFlag(ProductConstants.SHOPCART_CONFIRM_N);
                this.insertOrUpdateShopCart(shopCartItem);
            } else {
                item.setQuantity(item.getQuantity().add(shopCartItem.getQuantity()));
                this.insertOrUpdateShopCart(item);
            }
        } catch (Exception e) {
            throw new MemberException(MemberException.SHOPCART_ADD_ITEM_ERROR, null);
        }

    }

    @Override
    public void deleteShopCartItems(List<ShopCartItem> shopCartItems, IRequest request) throws MemberException {
        try {
            for (ShopCartItem cart : shopCartItems) {
                String cartUuid = getAccessibleShopCartUuid(request);
                if (cartUuid == null) {
                    throw new MemberException(MemberException.SHOPCART_UUID_NOT_FOUND, null);
                }
                if (cart.getProductId() == null) {
                    throw new MemberException(MemberException.PRODUCT_ID_NOT_SPECIFIED, null);
                }
                cart.setUuid(cartUuid);
                ShopCartItem requestCart = this.getShopCartItem(cartUuid, cart.getProductId());
                if (requestCart == null) {
                    // 要删除的数据必须存在
                    throw new MemberException(MemberException.SHOPCART_ITEM_NOT_EXISTS, null);
                }
                this.removeShopCart(requestCart);
            }
        } catch (Exception e) {
            throw new MemberException(MemberException.SHOPCART_ITEM_DELETE_ERROR, null);
        }
    }

    @Override
    public void updateShopCartItem(ShopCartItem shopCartItem, IRequest request) throws MemberException {
        try {
            verifyShopCart(shopCartItem);
            String cartUuid = getAccessibleShopCartUuid(request);
            shopCartItem.setUuid(cartUuid);
            ShopCartItem existsShopCartItem = this.getShopCartItem(cartUuid, shopCartItem.getProductId());
            if (existsShopCartItem == null) {
                throw new MemberException(MemberException.SHOPCART_ITEM_NOT_EXISTS, null);
            }
            existsShopCartItem.setQuantity(shopCartItem.getQuantity());
            this.insertOrUpdateShopCart(existsShopCartItem);
        } catch (Exception e) {
            throw new MemberException(MemberException.SHOPCART_ITEM_UPDATE_ERROR, null);
        }
    }

    @Override
    public List<Product> queryShopCartItem(IRequest request, String flag) {
        String cartUuid = getAccessibleShopCartUuid(request);
        List<ShopCartItem> carts = this.getShopCartItems(cartUuid);
        if (ProductConstants.SHOPCART_CONFIRM_Y.equals(flag)) {
            List<ShopCartItem> cartsByY = new ArrayList<ShopCartItem>();
            for (ShopCartItem temp : carts) {
                if (ProductConstants.SHOPCART_CONFIRM_Y.equals(temp.getFlag())) {
                    cartsByY.add(temp);
                }
            }
            return queryProductDetails(request, cartsByY);
        } else {
            return queryProductDetails(request, carts);
        }
    }

    @Override
    public List<String> confirmShopCartItem(List<ShopCartItem> confirmList, IRequest irequest,
            HttpServletRequest request) {
        String cartUuid = getAccessibleShopCartUuid(irequest);
        List<Product> confirmProducts = queryProductDetails(irequest, confirmList);
        // 验证是否确认商品的数量是否大于最小数
        List<String> errorList = new ArrayList<String>();
        for (Product temp : confirmProducts) {
            ShopCartItem newItem = this.getShopCartItem(cartUuid, temp.getItemId());
            if (temp.getMinOrderQuantity() > Long.parseLong(newItem.getQuantity().toString())) {
                errorList.add(temp.getItemName() + "_" + temp.getMinOrderQuantity());
            }
        }
        if (0 < errorList.size()) {
            return errorList;
        }
        // 将选中商品的flag改为Y,其余的为N
        rollbackhopCartItem(irequest);
        for (ShopCartItem temp : confirmList) {
            ShopCartItem newItem = this.getShopCartItem(cartUuid, temp.getProductId());
            newItem.setFlag(ProductConstants.SHOPCART_CONFIRM_Y);
            this.insertOrUpdateShopCart(newItem);
        }
        HttpSession session = request.getSession();
        String token = generateCaptchaKey();
        session.setAttribute(MwsOrderConstants.ORDER_CONFIRM_TOKEN, token);
        errorList.add("success");
        errorList.add(token);
        return errorList;
    }

    @Override
    public void rollbackhopCartItem(IRequest request) {
        String cartUuid = getAccessibleShopCartUuid(request);
        List<ShopCartItem> carts = this.getShopCartItems(cartUuid);
        for (ShopCartItem temp : carts) {
            temp.setFlag(ProductConstants.SHOPCART_CONFIRM_N);
            this.insertOrUpdateShopCart(temp);
        }
    }

    @Override
    public Integer getShopCartCount(Long memberId, Long salesOrgId) {
        ShopCartInfo info = this.getUserShopCart(memberId, salesOrgId);
        if (info != null) {
            return getShopCartCount(info.getUuid());
        }
        return 0;
    }

    @Override
    public void deleteConfirmItems(IRequest request) throws MemberException {
        String cartUuid = getAccessibleShopCartUuid(request);
        List<ShopCartItem> carts = this.getShopCartItems(cartUuid);
        List<ShopCartItem> cartsByY = new ArrayList<ShopCartItem>();
        for (ShopCartItem temp : carts) {
            if (ProductConstants.SHOPCART_CONFIRM_Y.equals(temp.getFlag())) {
                cartsByY.add(temp);
            }
        }
        self().deleteShopCartItems(cartsByY, request);
    }

    @Override
    public List<String> checkMinQuantity(IRequest request, List<ShopCartItem> products) {
        // 验证是否确认商品的数量是否大于最小数
        List<String> errorList = new ArrayList<String>();
        Product product;
        for (ShopCartItem temp : products) {
            product = new Product();
            product.setItemId(temp.getProductId());
            product.setFunctionArea("WEB");
            List<Product> productTemp = productService.getSimpleProductsByWhereClause(request, product, 1, -1);
            if (productTemp.size() > 0) {
                Product shopProduct = productTemp.get(0);
                if (shopProduct.getMinOrderQuantity() > Long.parseLong(temp.getQuantity().toString())) {
                    errorList.add(shopProduct.getItemName() + "_" + shopProduct.getMinOrderQuantity());
                }
            }
        }
        if (0 < errorList.size()) {
            return errorList;
        }
        errorList.add("success");
        return errorList;
    }
}
