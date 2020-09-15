package com.linzhenghong.o2o.util;


import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;


/**
 * @author LinZhenHong
 */
public class DESUtils {

	private static Key key;
	private static String KEY_STR = "myKey";
	private static String CHARSETNAME = "UTF-8";
	private static String ALGORITHM = "DES";

	static {
		try {
			//生成DES算法
			KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
			//运用sha1安全策略
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			//设置上密钥种子
			secureRandom.setSeed(KEY_STR.getBytes());
			//生成密钥对象
			generator.init(secureRandom);
			key = generator.generateKey();
			generator = null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取解密之后的信息
	 * @param str
	 * @return
	 */
	public static String getEncryptString(String str) {
		//基于BASE64编码，接收byte[]并转换成String（采用JDK8的Base64）
		Base64.Encoder base64encoder=Base64.getEncoder();
		try {
			//按UTF-8编码
			byte[] bytes = str.getBytes(CHARSETNAME);
			//获取加密对象
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			//初始化密码信息
			cipher.init(Cipher.ENCRYPT_MODE, key);
			//加密
			byte[] doFinal = cipher.doFinal(bytes);
			//byte[] to encode好的String并返回
			return base64encoder.encodeToString(doFinal);
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}


	public static String getDecryptString(String str) {
		//基于BASE64解码，接收byte[]并转换成String
		Base64.Decoder base64decoder=Base64.getDecoder();
		try {
			//按UTF-8编码
			byte[] bytes = base64decoder.decode(str);
			//获取加密对象
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			//初始化密码信息
			cipher.init(Cipher.DECRYPT_MODE, key);
			//解密
			byte[] doFinal = cipher.doFinal(bytes);
			return new String(doFinal, CHARSETNAME);
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {
		//要加密的用户和密码，运行并复制粘贴到替换jdbc.properties配置文件对应的用户密码
		System.out.println(getEncryptString("root"));
		System.out.println(getEncryptString("root"));
		System.out.println(getDecryptString("WnplV/ietfQ="));
		System.out.println(getDecryptString("WnplV/ietfQ="));
	}

}