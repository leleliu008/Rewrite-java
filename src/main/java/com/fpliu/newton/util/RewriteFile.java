package com.fpliu.newton.util;

import java.io.*;

/**
 * 重写文件的某些字节
 * 792793182@qq.com 2017-12-10
 */
public final class RewriteFile {

    /**
     * 将字节数组转换成16进制数字字符串
     *
     * @param src 字节数组
     * @return 16进制数字字符串
     */
    public static String bytesToHex(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 将16进制数字字符串转换成字节数组
     * @param hexString 16进制数字字符串
     * @return 字节数组
     */
    public static byte[] hexStringToBytes(String hexString) {
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
     * 将字符转成字节
     * @param c ASCII字符
     * @return 字节
     */
    public static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /*
     * 支持命令行运行
     */
    public static void main(String[] args) {
        int length = args.length;
        if (length < 5) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("usage:   java -jar RewriteFile.jar <hex | string> <replacement> <fromBytes> <toBytes> <filePath>\n");
            stringBuilder.append("example: java -jar RewriteFile.jar hex FD09A623CC 0 5 ~/xx.png\n");
            stringBuilder.append("         java -jar RewriteFile.jar string \"1544889600\" 20 30 ~/index.json\n");
            stringBuilder.append("note:    fromBytes must bigger than 0\n");
            stringBuilder.append("warning: this command will rewrite your origin file, so, please bak your origin file!\n");
            System.out.println(stringBuilder.toString());
        } else {
            String type = args[0];
            String replacement = args[1];
            int fromBytes = 0;
            int toBytes = 0;
            String filePath = args[4];

            try {
                fromBytes = Integer.parseInt(args[2]);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }

            try {
                toBytes = Integer.parseInt(args[3]);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(2);
            }

            if (fromBytes >= toBytes) {
                System.out.println("fromBytes must smaller than toBytes");
                System.exit(3);
            }

            if (fromBytes <= 0) {
                System.out.println("fromBytes must bigger than 0");
                System.exit(4);
            }

            byte[] bytes = null;
            if ("hex".equals(type)) {
                bytes = hexStringToBytes(replacement);
            } else if ("string".equals(type)){
                try {
                    bytes = replacement.getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("the type is only can be hex or string!");
                System.exit(5);
            }

            if (bytes.length != (toBytes - fromBytes)) {
                System.out.println("the replacement's length is not right!");
                System.exit(6);
            }

            RandomAccessFile randomAccessFile = null;
            try {
                randomAccessFile = new RandomAccessFile(filePath, "rws");
                //移动文件指针位置，人类以开始，但是计算机以0开始
                randomAccessFile.seek(fromBytes - 1);
                randomAccessFile.write(bytes);
                System.out.println("game over! enjoy!");
            } catch (java.io.IOException e) {
                e.printStackTrace();
            } finally {
                if (randomAccessFile != null) {
                    try {
                        randomAccessFile.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
