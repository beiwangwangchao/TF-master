/*
 *
 */

package com.lkkhpg.dsis.platform.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;

import org.apache.commons.lang.StringUtils;

/**
 * useful methods misc.
 * 
 * @author shengyang.zhou@hand-china.com
 */
public class CommonUtils {

    /**
     * do foreach over a collection.
     * 
     * @param collection
     *            can be null
     * @param closure
     *            can be null
     * @param <E>
     *            element type
     */
    public static <E> void foreach(Collection<E> collection, Consumer<E> closure) {
        if (collection != null && closure != null) {
            Iterator<E> itr = collection.iterator();
            for (; itr.hasNext(); itr.hasNext()) {
                closure.accept(itr.next());
            }
        }
    }

    /**
     * get index of element.
     * 
     * @param e
     *            target to find, can be null
     * @param arr
     *            array
     * @return index in array or -1
     */
    public static int indexOf(Object e, Object... arr) {
        if (e == null) {
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < arr.length; i++) {
                if (e.equals(arr[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * test element is in an array. <br>
     * in other words: one of them
     * 
     * @param e
     *            element, can be null
     * @param arr
     *            array
     * @return true for exists, false not exists
     * 
     * @see #indexOf(Object, Object...)
     */
    public static boolean in(Object e, Object... arr) {
        return indexOf(e, arr) != -1;
    }

    /**
     * safe equals.
     * 
     * @param o1
     *            can be null
     * @param o2
     *            can ne null
     * @return o1 == null ? o2 == null : o1.equals(o2)
     */
    public static boolean eq(Object o1, Object o2) {
        return o1 == null ? o2 == null : o1.equals(o2);
    }

    /**
     * return the first that is not null.
     * 
     * @param args
     *            args
     * @param <T>
     *            element type
     * @return maybe null
     */
    public static <T> T nvl(T... args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i] != null) {
                return args[i];
            }
        }
        return null;
    }
    
    public static String toColumnName(String property){
        ArrayList<String> tmp = new ArrayList<>();
        int lastIdx = 0;
        for (int i = 0; i < property.length(); i++) {
            if (Character.isUpperCase(property.charAt(i))) {
                tmp.add(property.substring(lastIdx, i).toLowerCase());
                lastIdx = i;
            }
        }
        tmp.add(property.substring(lastIdx));
        return StringUtils.join(tmp, "_");
    }

}
