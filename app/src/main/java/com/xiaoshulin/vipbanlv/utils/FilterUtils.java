package com.xiaoshulin.vipbanlv.utils;

public class FilterUtils {
    /**
     * 网址要被替换成的文字
     */
    public static String REPLACEMENT_STRING = "#点击我跳转#";
    /**
     * 匹配网址的正则表达式。以https://为例
     */
    public static String urlRegex = "((http[s]{0,1}|ftp)://[a-zA-Z0-9\\.\\-]+\\.([a-zA-Z]{2,4})(:\\d+)?(/[a-zA-Z0-9\\.\\-~!@#$%^&*+?:_/=<>,]*)?)|(www.[a-zA-Z0-9\\.\\-]+\\.([a-zA-Z]{2,4})(:\\d+)?(/[a-zA-Z0-9\\.\\-~!@#$%^&*+?:_/=<>,]*)?)|([a-zA-Z0-9\\.\\-]+\\.([a-zA-Z]{2,4})(:\\d+)?(/[a-zA-Z0-9\\.\\-~!@#$%^&*+?:_/=<>,]*)?)";

    /**
     * 要变红的字。一般用于搜索，如：把搜索的字变为红色用于突出显示
     */
    public static String toRedWord = "#BA2835";
}
