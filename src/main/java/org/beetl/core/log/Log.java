/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.beetl.core.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;

/**
 * 日志实现
 */
public final class Log {

    /** 日志级别：全部   {@link Log#v} */
    public static final int VERBOSE = 2;
    /** 日志级别：调试   {@link Log#d} */
    public static final int DEBUG = 3;
    /** 日志级别：信息   {@link Log#i} */
    public static final int INFO = 4;
    /** 日志级别：警告   {@link Log#w} */
    public static final int WARN = 5;
    /** 日志级别：错误   {@link Log#e} */
    public static final int ERROR = 6;

    /** 日志级别：全部   {@link Log#v} */
    public static final String VERBOSE_TEXT = "V";
    /** 日志级别：调试   {@link Log#d} */
    public static final String DEBUG_TEXT = "D";
    /** 日志级别：信息   {@link Log#i} */
    public static final String INFO_TEXT = "I";
    /** 日志级别：警告   {@link Log#w} */
    public static final String WARN_TEXT = "W";
    /** 日志级别：错误   {@link Log#e} */
    public static final String ERROR_TEXT = "E";

    private Log() {
    }

    /**
     * 发送一条 {@link #VERBOSE} 级别的日志消息
     *
     * @param tag 标签
     * @param msg 日志消息
     */
    public static int v(String tag, String msg) {
        return println(VERBOSE, tag, msg);
    }

    /**
     * 发送一条 {@link #VERBOSE} 级别的日志消息
     *
     * @param tag 标签
     * @param msg 日志消息
     * @param tr  异常信息
     */
    public static int v(String tag, String msg, Throwable tr) {
        return println(VERBOSE, tag, msg + '\n' + getStackTraceString(tr));
    }


    /**
     * 发送一条 {@link #DEBUG} 级别的日志消息
     *
     * @param tag 标签
     * @param msg 日志消息
     */
    public static int d(String tag, String msg) {
        return println(DEBUG, tag, msg);
    }

    /**
     * 发送一条 {@link #DEBUG} 级别的日志消息
     *
     * @param tag 标签
     * @param msg 日志消息
     * @param tr  异常信息
     */
    public static int d(String tag, String msg, Throwable tr) {
        return println(DEBUG, tag, msg + '\n' + getStackTraceString(tr));
    }

    /**
     * 发送一条 {@link #INFO} 级别的日志消息
     *
     * @param tag 标签
     * @param msg 日志消息
     */
    public static int i(String tag, String msg) {
        return println(INFO, tag, msg);
    }

    /**
     * 发送一条 {@link #INFO} 级别的日志消息
     *
     * @param tag 标签
     * @param msg 日志消息
     * @param tr  异常信息
     */
    public static int i(String tag, String msg, Throwable tr) {
        return println(INFO, tag, msg + '\n' + getStackTraceString(tr));
    }

    /**
     * 发送一条 {@link #WARN} 级别的日志消息
     *
     * @param tag 标签
     * @param msg 日志消息
     */
    public static int w(String tag, String msg) {
        return println(WARN, tag, msg);
    }

    /**
     * 发送一条 {@link #VERBOSE} 级别的日志消息
     *
     * @param tag 标签
     * @param msg 日志消息
     * @param tr  异常信息
     */
    public static int w(String tag, String msg, Throwable tr) {
        return println(WARN, tag, msg + '\n' + getStackTraceString(tr));
    }

    /**
     * 发送一条 {@link #WARN} 级别的日志消息
     *
     * @param tag 标签
     * @param tr  异常信息
     */
    public static int w(String tag, Throwable tr) {
        return println(WARN, tag, getStackTraceString(tr));
    }

    /**
     * 发送一条 {@link #ERROR} 级别的日志消息
     *
     * @param tag 标签
     * @param msg 日志消息
     */
    public static int e(String tag, String msg) {
        return println(ERROR, tag, msg);
    }

    /**
     * 发送一条 {@link #ERROR} 级别的日志消息
     *
     * @param tag 标签
     * @param msg 日志消息
     * @param tr  异常信息
     */
    public static int e(String tag, String msg, Throwable tr) {
        return println(ERROR, tag, msg + '\n' + getStackTraceString(tr));
    }

    /**
     * 获取可记录的堆栈跟踪
     *
     * @param tr 异常信息
     * @return 堆栈跟踪的字符串表示(移除了网络不可用情况下的异常信息)
     */
    public static String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        }

        // 移除网络不可用的情况：这是为了减少应用程序在无错误情况下的日志溢出量
        Throwable t = tr;
        while (t != null) {
            if (t instanceof UnknownHostException) {
                return "";
            }
            t = t.getCause();
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }

    /**
     * 打印一行日志消息
     *
     * @param tag 标签
     * @param msg 日志消息
     */
    public static int println(int priority, String tag, String msg) {
        String priorityText;
        switch (priority) {
            case VERBOSE: {
                priorityText = VERBOSE_TEXT;
                break;
            }
            case DEBUG: {
                priorityText = DEBUG_TEXT;
                break;
            }
            case INFO: {
                priorityText = INFO_TEXT;
                break;
            }
            case WARN: {
                priorityText = WARN_TEXT;
                break;
            }
            case ERROR: {
                priorityText = ERROR_TEXT;
                break;
            }
            default: {
                priorityText = "";
                break;
            }
        }
        System.out.println(priorityText + "/" + tag + ": " + msg);
        return 0;
    }
}
