package com.darren.projectmode.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 *
 * Created by ZENG DONG YANG on 2016/8/26.
 * e-mail:zengdongyang@incamel.com
 */
public class ToolUtils {

    /**
     * MD5+Base64编码
     *
     * @param src 加密数据
     * @return 加密完数据
     */
    public static String MD5(String src) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(src.getBytes("utf-8"));
            byte[] digest = messageDigest.digest();
            String encodeToString = Base64.encodeToString(digest, 0);
            return encodeToString;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断手机号码格式
     *
     * @param mobiles
     * @return false 手机号码格式错误
     */
    public static boolean isMobileNo(String mobiles) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 校验Tag Alias 只能是数字,英文字母和中文
     *
     * @param s
     * @return
     */
    public static boolean isValidTagAndAlias(String s) {
        Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_-]{0,}$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    /**
     * 判断网络是否开启
     *
     * @param context
     * @return false 网络未开启
     */
    public static Boolean isNetWork(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isAvailable()) {
            return false;
        }
        return true;
    }

    /**
     * Byte转换成16进制
     *
     * @param b
     * @return
     */
    public static String byte2HexStr(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs.toUpperCase();
    }

    /**
     * 卡号前六后四
     *
     * @return
     */
    public static String disposeCardNo(String cardNo) {

        return cardNo.substring(0, 6) + "******"
                + cardNo.substring(cardNo.length() - 4, cardNo.length());
    }

    /**
     * String 转换成16进制的字符串
     *
     * @param hexString
     * @return
     */

    public static byte[] getByteArray(String hexString) {
        byte[] byteArray = new BigInteger(hexString, 16).toByteArray();
        byte[] byteArray1 = new byte[byteArray.length - 1];
        for (int i = 1; i < byteArray.length; i++) {
            byteArray1[i - 1] = byteArray[i];
        }
        return byteArray1;
    }

    /**
     * String 转换成16进制的字符串
     *
     * @param b
     * @return
     * @throws Exception
     */
    public static String getHexString(byte[] b) throws Exception {

        String result = "";
        for (int i = 0; i < b.length; i++) {
            result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1)
                    .toUpperCase();
        }
        getByteArray(result);
        return result;

    }

    /**
     * 字符转换
     *
     * @param value
     * @return
     */
    public static int move(byte value) {
        int onebyte = 0;

        if ((value <= '9') && (value >= '0')) {
            onebyte = value - '0';
        } else if ((value < 'Z') && (value >= 'A')) {
            onebyte = value - 'A' + 0x0a;
        } else if ((value < 'z') && (value >= 'a')) {
            onebyte = value - 97;
        }
        return onebyte;
    }

    /**
     * 字符转换
     *
     * @param keyByte
     * @return
     */
    public static byte[] keyVlaueByte(byte[] keyByte) {
        byte[] keyValue = new byte[keyByte.length / 2];

        for (int i = 0; i < keyValue.length; i++) {
            int h = (move(keyByte[2 * i]) << 4);
            int l = move(keyByte[2 * i + 1]);
            keyValue[i] = (byte) (h + l);
        }
        return keyValue;
    }

    /**
     * 字节转换成String
     *
     * @param bytes
     * @return
     */
    public static String byteConversionString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; bytes != null && i < bytes.length; i++) {
            sb.append(String.format("%02X", bytes[i]));
        }
        return sb.toString();
    }

    /**
     * 校验银行卡卡号
     *
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId
                .substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null
                || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            // 如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

    /**
     * 处理带星的手机号码
     * @param phoneNum 手机号码
     * @return 带星的手机号码
     */
    public static String phoneStar(String phoneNum){
        if(!TextUtils.isEmpty(phoneNum) && phoneNum.length() > 6 ){
            StringBuilder sb  =new StringBuilder();
            for (int i = 0; i < phoneNum.length(); i++) {
                char c = phoneNum.charAt(i);
                if (i >= 3 && i <= 6) {
                    sb.append('*');
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        }
        return null;
    }

}
