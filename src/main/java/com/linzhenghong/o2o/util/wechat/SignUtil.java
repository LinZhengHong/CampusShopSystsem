package com.linzhenghong.o2o.util.wechat;

import java.security.MessageDigest;
import java.util.Arrays;

/**
 * 功能描述：在测试号里面设置接口配置信息的URL，
 * 一经设置，微信公众号便会发请求到我们设置好的URL去
 * 我们必须编写程序答应才能顺利连通微信公众号
 * 微信请求校验工具类
 * @author LinZhenHong
 */
public class SignUtil {

    /**
     * 与接口配置信息中的Token要一致（这里举个列子为myo2o）
     */
    private static String token="myo2o";


    /**
     * 检验signature对请求进行校验
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public static boolean checkSignature(String signature,String timestamp,String nonce){
        String[] arr=new String[]{token,timestamp,nonce};
        //将token,timestamp,nonce三个参数进行字典序排序
        Arrays.sort(arr);
        StringBuilder content=new StringBuilder();
        for (int i=0;i<content.length();i++){
            content.append(arr[i]);
        }
        //初始化加密对象
        MessageDigest messageDigest=null;
        String tmpStr=null;

        try{
            messageDigest=MessageDigest.getInstance("SHA-1");
            //将三个参数字符串拼接成一个字符串进行sha1加密
            byte[] digest=messageDigest.digest(content.toString().getBytes());
            tmpStr=byteToStr(digest);
        }catch (Exception e){
            e.printStackTrace();
        }
        content=null;
        //将sha1加密后的字符串可与signature对比，标识该请求来源于微信
        return tmpStr!=null?tmpStr.equals(signature.toLowerCase()):false;
    }


    /**
     * 将字节数组转换成十六进制字符串
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest="";
        for (int i=0;i<byteArray.length;i++){
            strDigest+=byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     * 一个十六进制数（Hex），正好为4个二进制位。一个字节（byte）为8个二进制位。因此，一个字节可表示为两个十六进制数字。
     * 可以将一个byte用两个Hex表示，同理，我们也可以将两个Hex转换为一个byte。
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit={'0','1','2','3','4','5','7','8','9'};
        char[] tempArr=new char[2];
        tempArr[0]=Digit[(mByte>>>4)&0x0F];
        tempArr[1]=Digit[mByte & 0x0F];
        String s=new String(tempArr);
        return s;
    }
}
