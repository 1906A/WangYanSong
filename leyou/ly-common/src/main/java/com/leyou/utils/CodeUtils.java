package com.leyou.utils;

public class CodeUtils {

    public static void main(String[] args) {
        messageCode(6);
    }

    /**
     * 生成对应位数的验证码
     *
     * @param length
     * @return
     */
    public static String messageCode(Integer length){

        //生成6位随机数字 区间内 100000-999999
        //System.out.println((int)((Math.random()*9+1)*100000));

        int num1 =(int)(Math.pow(10,length)-(Math.pow(10,length-1)+1));
        int num2 = (int)Math.pow(10,length-1);
        int result = (int)(Math.random()*num1)+num2;

        System.out.println("验证码："+result);

        return result+"";
    }
}
