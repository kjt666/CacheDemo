package com.csp.cache.util;

import android.text.TextUtils;
import com.csp.cache.App;

import java.io.*;

/**
 * Created by kjt on 2019-08-29
 */
public class FileUtil {

    public static boolean cacheData(String fileName, String s) {

        FileWriter writer = null;
        BufferedWriter bufferedWriter = null;
        try {
            //将序列化的数据转为16进制保存
            String hexString = bytesToHexString(s.getBytes());
            File file = new File(App.Companion.getCACHE_PATH() + fileName);
            if (file.exists())
                file.delete();
            else
                file.createNewFile();
            writer = new FileWriter(file);
            bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(hexString);
            return true;
        } catch (Exception e) {
            LogUtil.e(e.toString());
        } finally {
            try {
                if (bufferedWriter != null)
                    bufferedWriter.close();
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static String getCacheData(String fileName) {
        try {
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(App.CACHE_PATH + fileName), "utf-8");
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuffer buffer = new StringBuffer();
            String temp;
            while ((temp = bufferedReader.readLine()) != null) {
                buffer.append(temp);
            }
            reader.close();
            bufferedReader.close();
            if (TextUtils.isEmpty(buffer.toString())) {
                return null;
            }
            //将16进制的数据转为数组，准备反序列化
            byte[] bytes = stringToBytes(buffer.toString());
            if (bytes != null) {
                return new String(bytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将数组转为16进制
     */
    private static String bytesToHexString(byte[] bArray) {
        if (bArray == null) {
            return null;
        }
        if (bArray.length == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制的数据转为数组
     */
    private static byte[] stringToBytes(String data) {
        String hexString = data.toUpperCase().trim();
        if (hexString.length() % 2 != 0) {
            return null;
        }
        byte[] retData = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i++) {
            int int_ch;  // 两位16进制数转化后的10进制数
            char hex_char1 = hexString.charAt(i); ////两位16进制数中的第一位(高位*16)
            int int_ch3;
            if (hex_char1 >= '0' && hex_char1 <= '9')
                int_ch3 = (hex_char1 - 48) * 16;   //// 0 的Ascll - 48
            else if (hex_char1 >= 'A' && hex_char1 <= 'F')
                int_ch3 = (hex_char1 - 55) * 16; //// A 的Ascll - 65
            else
                return null;
            i++;
            char hex_char2 = hexString.charAt(i); ///两位16进制数中的第二位(低位)
            int int_ch4;
            if (hex_char2 >= '0' && hex_char2 <= '9')
                int_ch4 = (hex_char2 - 48); //// 0 的Ascll - 48
            else if (hex_char2 >= 'A' && hex_char2 <= 'F')
                int_ch4 = hex_char2 - 55; //// A 的Ascll - 65
            else
                return null;
            int_ch = int_ch3 + int_ch4;
            retData[i / 2] = (byte) int_ch;//将转化后的数放入Byte里
        }
        return retData;
    }
}
