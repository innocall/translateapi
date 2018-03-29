package com.rhino.translateapi.utils;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IatUtils {

    private static Logger logger = LoggerFactory.getLogger(IatUtils.class);
    private static final String xAppid = "5abb5b42";
    private static final String apiKey = "ec88f8082f5d4931b8014753f53e134a";
    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static String requestTextSemantic(String url,String userId,String content) {
        long time = System.currentTimeMillis() / 1000;
        String curTime = String.valueOf(time);
        System.out.println("X-CurTime:" + curTime);
        String xParam = "{\"userid\":\""+ userId +"\",\"scene\":\"main\"}";
        String xParamBase64 = getBase64(xParam);
        System.out.println("X-Param:" + xParamBase64);
        String contentBase64 = getBase64(content);
        String fileData = "text=" + contentBase64;
        String token = apiKey + curTime + xParamBase64 + fileData;
        String xCheckSum = md5Encode(token);
        System.out.println("X-CheckSum:" + xCheckSum);
        String resBody = "";
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl
                    .openConnection();
            conn.setReadTimeout(2000);
            conn.setConnectTimeout(1000);
            conn.setRequestMethod("POST");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("X-Appid", xAppid);
            conn.setRequestProperty("X-CurTime", curTime);
            conn.setRequestProperty("X-Param", xParamBase64);
            conn.setRequestProperty("X-CheckSum", xCheckSum);
            conn.setRequestProperty("Connection", "keep-alive");
            conn.setRequestProperty("Content-type","application/x-www-form-urlencoded; charset=utf-8");
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(fileData);
            // flush输出流的缓冲
            out.flush();
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(
                    inputStream, "utf-8");
            in = new BufferedReader(inputStreamReader);
            String line;
            while ((line = in.readLine()) != null) {
                resBody += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("result body :" + resBody);
        return resBody;
    }

    /**
     * Base64加密
     *
     * @param str 加密字符串
     * @return
     * @author jlchen4
     * @date 2017年9月16日 下午3:45:30
     */
    public static String getBase64(String str) {
        if (str == null || "".equals(str)) {
            return "";
        }
        try {
            byte[] encodeBase64 = Base64.encodeBase64(str.getBytes("UTF-8"));
            str = new String(encodeBase64);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * md5加密
     *
     * @param source 加密字符串
     * @return
     * @author jlchen4
     * @date 2017年9月16日 下午3:44:46
     */
    public static String md5Encode(String source) {
        String result = null;
        try {
            result = source;
            // 获得MD5摘要对象
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            // 使用指定的字节数组更新摘要信息
            messageDigest.update(result.getBytes("utf-8"));
            // messageDigest.digest()获得16位长度
            result = byteArrayToHexString(messageDigest.digest());
        } catch (Exception e) {
            logger.error("Md5 Exception!", e);
        }
        return result;
    }

    private static String byteArrayToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte tem : bytes) {
            stringBuilder.append(byteToHexString(tem));
        }
        return stringBuilder.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
}
