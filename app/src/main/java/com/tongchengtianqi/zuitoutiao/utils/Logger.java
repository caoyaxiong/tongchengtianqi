package com.tongchengtianqi.zuitoutiao.utils;

import android.util.Log;

/**
 * logger工具类
 */

public class Logger {
    /**  控制Log是否输出，true则输出，false则不输出 */
    public static boolean showLog = true;

    /** 打印一个info级别的log */
    public static void i(Object objTag, Object objMsg) {
        if (showLog) {
            String tag = convertAsStringTag(objTag);
            String msg = convertAsStringMsg(objMsg);
            Log.i(tag, msg);
        }
    }

    /** 打印一个error级别的log */
    public static void e(Object objTag, Object objMsg, Throwable e) {
        if (showLog) {
            String tag = convertAsStringTag(objTag);
            String msg = convertAsStringMsg(objMsg);
            Log.e(tag, msg, e);
        }
    }

    /** 把一个Object类型的tag转换为String类型的tag */
    private static String convertAsStringTag(Object objTag) {
        String tag;
        if (objTag == null) {
            tag = "null";
        } else if (objTag instanceof String) {
            tag = (String) objTag;
        } else if (objTag instanceof Class) {	// 如果对象是一个Class，则直接取类名
            tag = ((Class<?>) objTag).getSimpleName();	// 获取类名
        } else {
            tag = objTag.getClass().getSimpleName();	//  获取类名
        }
        return tag;
    }

    /** 把一个Object类型的消息转换为String类型的消息 */
    private static String convertAsStringMsg(Object objMsg) {
        String msg;
        if (objMsg == null) {
            msg = "null";
        } else {
            msg = objMsg.toString();
        }
        return msg;
    }
}
