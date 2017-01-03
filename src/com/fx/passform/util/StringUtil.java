package com.fx.passform.util;

import java.sql.Timestamp;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Created by fan.xu on 2014/10/21.
 */
public class StringUtil {
    /**
     * 判断当前字符串是否合法
     *
     * @param val
     * @return
     */
    public static boolean isValid(String val) {
        return val != null && val.trim().length() > 0;
    }
    
    /**
     * 判断当前字符串是否包含‘.’字符
     * @param val
     * @return
     */
    public static boolean isContains(String val) {
        return val.contains(".");
    }
    
    /***
     * 判断输入字符是否是中文"正常"
     * @param val
     * @return
     */
    public static boolean isChineseChar1(String val) {
    	return val.equals("正常");
    }
    /**
     * 判断输入字符是否是中文"锁定"
     * @param val
     * @return
     */
    public static boolean isChineseChar2(String val) {
    	return val.equals("锁定");
    }

    /**
     * 判断是否是搜索框默认的中文值（请输入要查询的条件）
     * @param val
     * @return
     */
    public static boolean isChineseChar3(String val) {
    	return val.equals("请输入要查询的条件");
    }
    
    /**
     * 判断输入的是否是数字
     * @param val
     * @return
     */
    public static boolean isDigital(String val) {
    	Pattern pattern = Pattern.compile("[0-9]*");
    	return pattern.matcher(val).matches();
    }
    
    
    
    /**
     * 判断当前字符串是否合法
     *
     * @param val
     * @return
     */
    public static boolean isValid(Timestamp val) {
        return val != null && val.getTime() > 0;
    }

    /**
     * 判断当前字符串是否合法
     *
     * @param val
     * @return
     */
    public static boolean isValid(int val) {
        return val > 0;
    }

    /**
     * 生成随即密码
     *
     * @param pwd_len 生成的密码的总长度
     * @return 密码的字符串
     */
    public static String genRandomNum(int pwd_len) {
        // String re="(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&]).{10,}";
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[A-Za-z0-9@#$%]{8,16}$";
        //35是因为数组是从0开始的，26个字母+10个数字
        final int maxNum = 26;
        int i;  //生成的随机数
        int count = 0; //生成的密码的长度
        char[] str = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
                'x', 'y', 'z'};
        char[] upChar = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z'};
        char[] numChar = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        char[] speChar = {'!', '@', '#', '$', '%', '^', '&'};

        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while (count < 3) {
            //生成随机数，取绝对值，防止生成负数，
            i = Math.abs(r.nextInt(maxNum));  //生成的数最大为36-1
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        count=0;
        while (count < 3) {
            //生成随机数，取绝对值，防止生成负数，
            i = Math.abs(r.nextInt(7));  //生成的数最大为7-1
            if (i >= 0 && i < upChar.length) {
                pwd.append(upChar[i]);
                count++;
            }
        }
        count=0;
        while (count < 3) {
            //生成随机数，取绝对值，防止生成负数，
            i = Math.abs(r.nextInt(maxNum));  //生成的数最大为10-1
            if (i >= 0 && i < numChar.length) {
                pwd.append(numChar[i]);
                count++;
            }
        }
        return pwd.toString();
    }

    public static void main(String[] args) {
        for(int i=0;i<10;i++){
            System.out.println(genRandomNum(1));
        }
    }

}
