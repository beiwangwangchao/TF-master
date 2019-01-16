/*
 *
 */
package com.lkkhpg.dsis.platform.report.pdf;

import java.io.File;
import java.io.IOException;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.springframework.core.io.Resource;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;

/**
 * IText Render工厂类. 
 * @author chenjingxiong
 */
public class ITextRendererObjectFactory extends BasePoolableObjectFactory {

    private static final int MAX_ACTIVE = 15;

    private static final int MAX_IDLE = 5;

    private static final int MIN_IDLE = 1;

    private static final int MAX_WAIT = 5000;

    private static GenericObjectPool itextRendererObjectPool = null;

    private static Resource[] fontsPath;

    private static int maxActive = MAX_ACTIVE;

    private static int maxIdle = MAX_IDLE;

    private static int minIdle = MIN_IDLE;

    private static int maxWait = MAX_WAIT;

    @Override
    public Object makeObject() throws Exception {
        ITextRenderer renderer = createTextRenderer();
        return renderer;
    }

    public static GenericObjectPool getObjectPool() {
        synchronized (ITextRendererObjectFactory.class) {
            if (itextRendererObjectPool == null) {
                itextRendererObjectPool = new GenericObjectPool(new ITextRendererObjectFactory());
                GenericObjectPool.Config config = new GenericObjectPool.Config();
                config.lifo = false;
                config.maxActive = maxActive;
                config.maxIdle = maxIdle;
                config.minIdle = minIdle;
                config.maxWait = maxWait;
                itextRendererObjectPool.setConfig(config);
            }
        }
        return itextRendererObjectPool;
    }

    public static synchronized ITextRenderer createTextRenderer() throws DocumentException, IOException {
        ITextRenderer renderer = new ITextRenderer();
        renderer.getSharedContext().setReplacedElementFactory(new B64ImgReplacedElementFactory());
        ITextFontResolver fontResolver = renderer.getFontResolver();
        addFonts(fontResolver);
        return renderer;
    }

    public static ITextFontResolver addFonts(ITextFontResolver fontResolver) throws DocumentException, IOException {
        if (fontsPath != null && fontsPath.length > 0) {
            for (Resource font : fontsPath) {
                File fontsDir = font.getFile();
                if (fontsDir != null && fontsDir.isDirectory()) {
                    File[] files = fontsDir.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        File f = files[i];
                        if (f == null || f.isDirectory()) {
                            break;
                        }
                        String filePath = f.getAbsolutePath();
                        if (filePath.toLowerCase().endsWith(".ttf") || filePath.toLowerCase().endsWith(".ttc")) {
                            fontResolver.addFont(filePath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
                        } 
                    }
                }
            }
        }
        return fontResolver;
    }

    public static Resource[] getFontsPath() {
        return fontsPath;
    }

    public static void setFontsPath(Resource[] fontsPath) {
        ITextRendererObjectFactory.fontsPath = fontsPath;
    }

}
