package com.xiaoshulin.vipbanlv.utils;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;


import com.alibaba.android.arouter.launcher.ARouter;
import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.circleFrags.adapter.CircleAdapter1;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MyTextView extends AppCompatTextView {




    public interface OnUrlClickLitener
    {

        void onUrlClick(String url);
    }

    private OnUrlClickLitener mOnUrlClickLitener;

    public void setOnUrlClickLitener(OnUrlClickLitener mOnUrlClickLitener)
    {
        this.mOnUrlClickLitener = mOnUrlClickLitener;
    }




    private Context context = null;

    public MyTextView(Context context, AttributeSet attr) {
        super(context, attr);
        this.context = context;
    }


    public MyTextView(Context context) {
        super(context);
        this.context = context;
    }



    /**
     * TextView显示有颜色的字，并添加点击事件，适用于一段文字中有多个内容要处理
     */
    public void handleText(String str) {
        String content = str;

        //处理匹配的url
        Pattern p = Pattern.compile(FilterUtils.urlRegex);
        Matcher m = p.matcher(content);
        ArrayList<String> urlList = new ArrayList<String>();
        while (m.find()) {
            String urlStr = m.group();
            if (urlStr.contains("https://") || urlStr.contains("http://")) {
                //如果末尾有英文逗号或者中文逗号等，就去掉
                while (urlStr.endsWith(",") || urlStr.endsWith("，")
                        || urlStr.endsWith(".") || urlStr.endsWith("。")
                        || urlStr.endsWith(";") || urlStr.endsWith("；")
                        || urlStr.endsWith("！") || urlStr.endsWith("!")
                        || urlStr.endsWith("?") || urlStr.endsWith("？")) {
                    urlStr = urlStr.substring(0, urlStr.length() - 1);
                }
                urlList.add(urlStr);
                content = content.replace(urlStr, FilterUtils.REPLACEMENT_STRING);
            }
        }

        final SpannableString spannableString = ExpressionUtil.getSpannableString(content, context);
        content = spannableString.toString();

        //处理链接
        if (urlList.size() > 0) {

            int urlStartNew = 0;
            int urlStartOld = 0;

            String urlTemp = content;

            for (int i = 0; i < urlList.size(); i++) {

                final String regexUrl = urlList.get(i);

                spannableString.setSpan(new ClickableSpan() {

                                            @Override
                                            public void updateDrawState(TextPaint ds) {
                                                super.updateDrawState(ds);
                                                ds.setColor(context.getResources().getColor(R.color.red));
                                                ds.setUnderlineText(false);
                                            }

                                            @Override
                                            public void onClick(View widget) {
                                              /**url解析回调*/
                                                try {

                                                    String result = URLDecoder.decode(regexUrl, "UTF-8");
//
                                                    mOnUrlClickLitener.onUrlClick(result);

                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                            }

                                        }, urlStartOld + urlTemp.indexOf(FilterUtils.REPLACEMENT_STRING), urlStartOld + urlTemp.indexOf(FilterUtils.REPLACEMENT_STRING) + FilterUtils.REPLACEMENT_STRING.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                setText(spannableString);
                setMovementMethod(LinkMovementMethod.getInstance());

                urlStartNew = urlTemp.indexOf(FilterUtils.REPLACEMENT_STRING) + FilterUtils.REPLACEMENT_STRING.length();
                urlStartOld += urlStartNew;
                urlTemp = urlTemp.substring(urlStartNew);
            }
        } else {
            setText(spannableString);
        }

        //处理要变红的字
        if (!TextUtils.isEmpty(FilterUtils.toRedWord)) {
            //只要把关键字变红就行了，不需要加点击事件。如果需要，可以自己加
            String temp = content;
            int startNew = 0;
            int startOld = 0;
            while (temp.contains(FilterUtils.toRedWord)) {
                spannableString.setSpan(new ClickableSpan() {

                                            @Override
                                            public void updateDrawState(TextPaint ds) {
                                                super.updateDrawState(ds);
                                                ds.setColor(getResources().getColor(R.color.red));
                                                ds.setUnderlineText(false);
                                            }

                                            @Override
                                            public void onClick(View widget) {
                                                Toast.makeText(context, "处理要变红的字", Toast.LENGTH_SHORT).show();
                                            }

                                        }, startOld + temp.indexOf(FilterUtils.toRedWord), startOld + temp.indexOf(FilterUtils.toRedWord) + FilterUtils.toRedWord.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                setText(spannableString);
                setMovementMethod(LinkMovementMethod.getInstance());
                startNew = temp.indexOf(FilterUtils.toRedWord) + FilterUtils.toRedWord.length();
                startOld += startNew;
                temp = temp.substring(startNew);
            }
        } else {
            setText(spannableString);
        }
    }



}
