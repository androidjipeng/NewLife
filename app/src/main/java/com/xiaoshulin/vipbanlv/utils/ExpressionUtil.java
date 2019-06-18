package com.xiaoshulin.vipbanlv.utils;

import android.content.Context;
import android.text.SpannableString;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionUtil {

    public static SpannableString getSpannableString(String str, Context context) {
        SpannableString spannableString = new SpannableString(str);
        String string = "\\[(.+?)\\]";
        Pattern patten = Pattern.compile(string, Pattern.CASE_INSENSITIVE);
        try {
            dealSpannableString(context, spannableString, patten, 0);
        } catch (Exception e) {
        }
        return spannableString;
    }

    private static void dealSpannableString(Context context, SpannableString spannableString, Pattern patten, int start) {
        Matcher matcher = patten.matcher(spannableString);
        while (matcher.find()) {
            String key = matcher.group();
            if (matcher.start() < start) {
                continue;
            }
        }
    }


}
