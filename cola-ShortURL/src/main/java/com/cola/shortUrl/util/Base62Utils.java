package com.cola.shortUrl.util;


import java.net.URLEncoder;
import java.util.Base64;

/**
 * @author cola
 * @description 短链接 - 工具类
 * @data 2024/1/4
 **/
public class Base62Utils {
    private static final int SCALE = 62;
    // 下面的字符，可以随便打乱，安全性更高
    private static final char[] BASE_62_ARRAY = {
            'a', 'c', '9', 'Q', 'R', '1', 'T', 'v', 'V', 'W', 'X', 'Y', 'U',
            'n', 'o', 'p', 'q', 'z', 's', 't', 'u', 'Z', '0', 'x', '5', 'r',
            '3', 'B', 'C', 'D', 'h', '2', 'G', 'H', '8', 'J', 'K', 'L', 'M',
            'N', 'b', 'O', 'd', 'e', 'f', 'g', 'E', 'i', 'j', 'k', 'l', 'm',
            '6', 'S', 'P', 'A', '4', 'y', 'w', '7', 'I', 'F'
    };
    private static final String BASE_62_CHARACTERS = String.valueOf(BASE_62_ARRAY);

    /**
     * 将long类型编码成Base62字符串
     *
     * @param num
     * @return
     */
    public static String encodeToBase62String(long num) {
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            sb.insert(0, BASE_62_ARRAY[(int) (num % SCALE)]);
            num /= SCALE;
        }
        return sb.toString();
    }

    /**
     * 将Base62字符串解码成long类型
     *
     * @param base62Str
     * @return
     */
    public static long decodeToLong(String base62Str) {
        long num = 0, coefficient = 1;
        String reversedBase62Str = new StringBuilder(base62Str).reverse().toString();
        for (char base62Character : reversedBase62Str.toCharArray()) {
            num += BASE_62_CHARACTERS.indexOf(base62Character) * coefficient;
            coefficient *= SCALE;
        }
        return num;
    }

    public static void main(String[] args) {
        String data = "6s3brYkS9OQp7YpY7RHR+GOJUdp//tdRrVPyiUcuJhJZPaHS9dStwDCdOWNWuHk=";
        Base64.Encoder encoder = Base64.getEncoder();
        System.out.println(encoder.encodeToString(data.getBytes()));
        Base64.Encoder encoder2 = Base64.getUrlEncoder();
        System.out.println(encoder2.encodeToString(data.getBytes()));
        // 编码 这个编码后 有 url的特殊字符
        System.out.println(URLEncoder.encode(data));
    }
}
