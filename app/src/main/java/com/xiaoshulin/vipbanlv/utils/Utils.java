package com.xiaoshulin.vipbanlv.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import com.ta.utdid2.android.utils.StringUtils;
import com.xiaoshulin.vipbanlv.App;

/**
 * Created by jipeng on 2018/5/19.
 */

public class Utils {

    /**
     * 随机生成6
     * */
    public static int getNum(){
        int end=0;
        end= (int) ((Math.random()*9+1)*100000);
        return end;
    }
    /**
     * 随机生成0~9的一位数
     * */
    public static int getNUMBER()
    {
        int number = (int) (Math.random() * 10);
        return number;
    }

    public static int get0to41()
    {
        Random rand = new Random();
        int i = rand.nextInt(41);
        return i;
    }

    public static int get2to5()
    {
        Random rand = new Random();
        int i = rand.nextInt(5);
        return i;
    }

    public static String getTimestyle()
    {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String time =  formatter.format(date);
        return time;
    }

     public static String getTimes()
     {
         Date date = new Date();
         SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
         String time =  formatter.format(date);
         return time;
     }
    public static String getTime1()
    {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time =  formatter.format(date);
        return time;
    }

    public static String getTime2()
    {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String time =  formatter.format(date);
        return time;
    }

    public String bigNumberAdd(String f, String s) {
        System.out.print("加法:" + f + "+" + s + "=");
        // 翻转两个字符串，并转换成数组
        char[] a = new StringBuffer(f).reverse().toString().toCharArray();
        char[] b = new StringBuffer(s).reverse().toString().toCharArray();
        int lenA = a.length;
        int lenB = b.length;
        // 计算两个长字符串中的较长字符串的长度
        int len = lenA > lenB ? lenA : lenB;
        int[] result = new int[len + 1];
        for (int i = 0; i < len + 1; i++) {
            // 如果当前的i超过了其中的一个，就用0代替，和另一个字符数组中的数字相加
            int aint = i < lenA ? (a[i] - '0') : 0;
            int bint = i < lenB ? (b[i] - '0') : 0;
            result[i] = aint + bint;
        }
        // 处理结果集合，如果大于10的就向前一位进位，本身进行除10取余
        for (int i = 0; i < result.length; i++) {
            if (result[i] >= 10) {
                result[i + 1] += result[i] / 10;
                result[i] %= 10;
            }
        }
        StringBuffer sb = new StringBuffer();
        // 该字段用于标识是否有前置0，如果有就不要存储
        boolean flag = true;
        // 注意从后往前
        for (int i = len; i >= 0; i--) {
            if (result[i] == 0 && flag) {
                continue;
            } else {
                flag = false;
            }
            sb.append(result[i]);
        }
        // 结果
        System.out.println(sb.toString());
        return sb.toString();
    }


    public static String getLocalVersionName() {
        String localVersion = "";
        try {
            PackageInfo packageInfo = App.app.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(App.app.getPackageName(), 0);
            localVersion = packageInfo.versionName;
//            LogUtil.d("TAG", "本软件的版本号。。" + localVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }


    public static String getUidSign(String uid){
        StringBuffer reverseStr = new StringBuffer(uid).reverse();
        int i = Integer.parseInt(String.valueOf(reverseStr));
        int a= i+19920629;
        String sign1=String.valueOf(a);
        StringBuffer sign2 = new StringBuffer(sign1).reverse();
        String sign=String.valueOf(sign2);
        return sign;
    }
   // kBool  true为千为单位
     public static String formatNum(String num, Boolean kBool) {
        StringBuffer sb = new StringBuffer();
        if (kBool == null)
            kBool = false;
        BigDecimal b0 = new BigDecimal("1000");
        BigDecimal b1 = new BigDecimal("10000");
        BigDecimal b2 = new BigDecimal("100000000");
        BigDecimal b3 = new BigDecimal(num);
        String formatNumStr = "";
        String nuit = "";
//         以千为单位处理
          if (kBool) {
              if (b3.compareTo(b0) == 0 || b3.compareTo(b0) == 1) {
                     return "999+";
             }
           return num;
          }
//         以万为单位处理
           if (b3.compareTo(b1) == -1) {
                    sb.append(b3.toString());
         	        } else if ((b3.compareTo(b1) == 0 && b3.compareTo(b1) == 1)
         	                || b3.compareTo(b2) == -1) {
                 formatNumStr = b3.divide(b1).toString();
                    nuit = "万";
         } else if (b3.compareTo(b2) == 0 || b3.compareTo(b2) == 1) {
                   formatNumStr = b3.divide(b2).toString();
            nuit = "亿";
           }
         if (!"".equals(formatNumStr)) {
                int i = formatNumStr.indexOf(".");
               if (i == -1) {
           sb.append(formatNumStr).append(nuit);
                  } else {
            i = i + 1;
                String v = formatNumStr.substring(i, i + 1);
                      if (!v.equals("0")) {
                   sb.append(formatNumStr.substring(0, i + 1)).append(nuit);

         } else {

         sb.append(formatNumStr.substring(0, i - 1)).append(nuit);

          }
               }
                }
                if (sb.length() == 0)
                    return "0";
              return sb.toString();
         }

    public static boolean hourMinuteBetween(String nowDate, String startDate, String endDate) throws Exception{
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date now = format.parse(nowDate);
        Date start = format.parse(startDate);
        Date end = format.parse(endDate);
        long nowTime = now.getTime();
        long startTime = start.getTime();
        long endTime = end.getTime();
        return nowTime >= startTime && nowTime <= endTime;
    }

    public static String getCurrentHHmmTime()
    {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        String time =  formatter.format(date);
        return time;
    }



    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return  语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return  手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return  手机IMEI
     */
//    public static String getIMEI(Context ctx) {
//        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
//        if (tm != null) {
//            return tm.getDeviceId();
//        }
//        return null;
//    }

}
