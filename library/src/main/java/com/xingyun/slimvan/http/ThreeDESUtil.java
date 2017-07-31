package com.xingyun.slimvan.http;

import android.text.TextUtils;
import android.util.Log;

import java.security.Key;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * 3DES加密、解密工具
 */
public class ThreeDESUtil {


    // 数据加密key
//    public static int VERSION = 1;//0为测试用，1为正式环境（用户用）
    public static int VERSION = 0;//0为测试用，1为正式环境（用户用）


    // 数据加密key
//      private static String DESKEY = "YemuCC9pVZ9jTHYXsRRfAM6ZfLrYTZZ2";//生产
          private static String DESKEY = "TFyZF8ZaBk4ErTazepPtvw4Pst2wfGLd";//开发
    /**
     * 随机生成加密向量
     *
     * @return String 生成的加密向量
     */
    public static String getKeyIV() {
        StringBuffer buffer = new StringBuffer();
        char[] source = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        Random ran = new Random();
        int len = 8;
        for (int i = 0; i < len; i++) {
            buffer.append(source[ran.nextInt(source.length)]);
        }
        return buffer.toString();
    }

    /**
     * 3Des加密
     *
     * @param keyIV
     * @param data
     * @return
     */
    public static String des3EncodeCBC(String keyIV, String data) {
        return des3EncodeCBC(DESKEY, keyIV, data);
    }

    /**
     * 3Des加密
     *
     * @param key
     * @param keyiv
     * @param data
     * @return
     */
    public static String des3EncodeCBC(String key, String keyiv, String data) {
        try {
            byte[] keyByte = key.getBytes();
            byte[] keyivByte = keyiv.getBytes();
            byte[] dataByte = null;
            if (data.equals(new String(data.getBytes("utf-8")))) {
                dataByte = data.getBytes("utf-8");
            } else if (data.equals(new String(data.getBytes("gbk")))) {
                dataByte = data.getBytes("gbk");
            }
            byte[] result = des3EncodeCBC(keyByte, keyivByte, dataByte);
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < result.length; i++) {
                //拼成字符串上传到服务器
                String tempCode = Integer.toHexString(result[i] & 0xff);
                buffer.append(tempCode.length() == 1 ? "0" + tempCode : tempCode);
            }
            return buffer.toString();
        } catch (Exception e) {
            Log.e("e:", e.getMessage());
        }
        return null;
    }

    /**
     * CBC加密
     *
     * @param key   密钥
     * @param keyiv IV
     * @param data  明文
     * @return Base64编码的密文
     * @throws Exception
     */
    private static byte[] des3EncodeCBC(byte[] key, byte[] keyiv, byte[] data)
            throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(keyiv);
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        // byte[] temp = cipher.update(data);
        byte[] bOut = cipher.doFinal(data);
        return bOut;
    }


    /**
     * 3Des解密
     */
    public static String des3DecodeCBC(String keyiv, String data) {
        return des3DecodeCBC(DESKEY, keyiv, data);
    }

    /**
     * 3Des解密
     *
     * @param key
     * @param keyiv
     * @param data
     * @return
     */
    public static String des3DecodeCBC(String key, String keyiv, String data) {
        try {
            byte[] keyByte = key.getBytes();
            byte[] keyivByte = keyiv.getBytes();
            byte[] dataByte = convertHexStringToBytes(data);
            return new String(des3DecodeCBC(keyByte, keyivByte, dataByte), "utf-8");
        } catch (Exception e) {
            //MyLog.e(e.toString(), e);
        }
        return null;
    }

    /**
     * CBC解密
     *
     * @param key   密钥
     * @param keyiv IV
     * @param data  Base64编码的密文
     * @return 明文
     * @throws Exception
     */
    private static byte[] des3DecodeCBC(byte[] key, byte[] keyiv, byte[] data) throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(keyiv);
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
        //byte[] temp = cipher.update(data);
        byte[] bOut = cipher.doFinal(data);
        return bOut;
    }


    /**
     * 根据16进制字符串获取字符串原来对应的byte数组
     *
     * @param hexString 16进制的字符串
     * @return
     */
    public static byte[] convertHexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * 获取字符在16进制中对应的byte
     *
     * @param c
     * @return
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * unicode转utf-8
     *
     * @param theString
     * @return
     */
    public static String decodeUnicode(String theString) {
        if (!TextUtils.isEmpty(theString)) {
            char aChar;
            int len = theString.length();
            StringBuffer outBuffer = new StringBuffer(len);
            for (int x = 0; x < len; ) {
                aChar = theString.charAt(x++);
                if (aChar == '\\') {
                    aChar = theString.charAt(x++);
                    if (aChar == 'u') {
                        // Read the xxxx
                        int value = 0;
                        for (int i = 0; i < 4; i++) {
                            aChar = theString.charAt(x++);
                            switch (aChar) {
                                case '0':
                                case '1':
                                case '2':
                                case '3':
                                case '4':
                                case '5':
                                case '6':
                                case '7':
                                case '8':
                                case '9':
                                    value = (value << 4) + aChar - '0';
                                    break;
                                case 'a':
                                case 'b':
                                case 'c':
                                case 'd':
                                case 'e':
                                case 'f':
                                    value = (value << 4) + 10 + aChar - 'a';
                                    break;
                                case 'A':
                                case 'B':
                                case 'C':
                                case 'D':
                                case 'E':
                                case 'F':
                                    value = (value << 4) + 10 + aChar - 'A';
                                    break;
                                default:
                                    throw new IllegalArgumentException(
                                            "Malformed   \\uxxxx   encoding.");
                            }

                        }
                        outBuffer.append((char) value);
                    } else {
                        if (aChar == 't')
                            aChar = '\t';
                        else if (aChar == 'r')
                            aChar = '\r';
                        else if (aChar == 'n')
                            aChar = '\n';
                        else if (aChar == 'f')
                            aChar = '\f';
                        outBuffer.append(aChar);
                    }
                } else
                    outBuffer.append(aChar);
            }
            return outBuffer.toString();
        } else {
            return "decodeUnicode String is empty";
        }
    }

}