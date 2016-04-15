package com.snail.iweibo.util;
import android.util.Log;

import com.snail.iweibo.BuildConfig;

/**
 * LogUtils
 * Created by alexwan on 16/4/15.
 */
public class LogUtils {
    private static boolean DEBUG = BuildConfig.DEBUG;
    private static int LEVEL_DEBUG = 0x01;
    private static int LEVEL_INFO = 0x02;
    private static int LEVEL_ERROR = 0x03;

    /**
     * 指定TAG的错误日志
     *
     * @param tag tag
     * @param msg msg
     */
    public static void error(String tag, String msg) {
        printLog(LEVEL_ERROR, tag, msg);
    }

    /**
     * 当前类用作TAG的错误日志
     *
     * @param msg msg
     */
    public static void error(String msg) {
        printLog(LEVEL_ERROR, getClassName(), msg);
    }

    /**
     * 指定TAG的信息日志
     *
     * @param tag tag
     * @param msg msg
     */
    public static void info(String tag, String msg) {
        printLog(LEVEL_INFO, tag, msg);
    }

    /**
     * 当前类用作TAG的信息日志
     *
     * @param msg msg
     */
    public static void info(String msg) {
        printLog(LEVEL_INFO, getClassName(), msg);
    }

    /**
     * 指定TAG的调试日志
     *
     * @param tag tag
     * @param msg msg
     */
    public static void debug(String tag, String msg) {
        printLog(LEVEL_DEBUG, tag, msg);
    }

    /**
     * 当前类用作TAG的调试日志
     *
     * @param msg msg
     */
    public static void debug(String msg) {
        printLog(LEVEL_DEBUG, getClassName(), msg);
    }

    /**
     * printLog
     * @param level level
     * @param tag tag
     * @param msg msg
     */
    private static void printLog(int level, String tag, String msg) {
        if (!DEBUG) {
            return;
        }
        switch (level) {
            case 0x01:
                Log.d(tag, msg);
                break;
            case 0x02:
                Log.i(tag, msg);
                break;
            case 0x03:
                Log.e(tag, msg);
                break;
            default:
                Log.d(tag, msg);
                break;
        }
    }

    /**
     * 获取当前类名
     *
     * @return className
     */
    private static String getClassName() {
        StackTraceElement element = (new Exception()).getStackTrace()[2];
        String result = element.getClassName();
        int lastIndex = result.lastIndexOf(".");
        return result.substring(lastIndex + 1, result.length());
    }

    /**
     * 获取当前堆栈方法和行数信息
     *
     * @return String
     */
    public static String callMethodLine() {
        final StackTraceElement traceElement = Thread.currentThread().getStackTrace()[1];
        return " : at " + traceElement.getClassName() +"."+ traceElement.getMethodName() +
            " (" + traceElement.getFileName() + " : " + traceElement.getLineNumber() + ") ";
    }

}
