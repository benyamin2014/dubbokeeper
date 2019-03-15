package com.dubboclub.dk.web.utils;

import com.warrenstrange.googleauth.*;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @program: dubbokeeper
 * @description: TOTP google 动态码管理工具类，按需扩展
 * @author: benyamin
 * @create: 2019-03-13 18:45
 **/
public class TOTPUtils {
   static final ConcurrentHashMap<String, String> store;
   static {
       store = new ConcurrentHashMap();
       store.put("admin","Q2UTRRRCNX4BQDBW");
   }

   private static TOTPUtils totpUtils = new TOTPUtils();
   private GoogleAuthenticator ga;

    private TOTPUtils() {
       //初始化google验证器，动态码长度6，时间步长30秒
       GoogleAuthenticatorConfig.GoogleAuthenticatorConfigBuilder cb = new GoogleAuthenticatorConfig.GoogleAuthenticatorConfigBuilder();
       cb.setCodeDigits(6).setTimeStepSizeInMillis(TimeUnit.SECONDS.toMillis(30));
       ga = new GoogleAuthenticator(cb.build());
       ga.setCredentialRepository(new CredentialRepository(store));
   }
   public static TOTPUtils getInstance() {
       return totpUtils;
   }

    /**
     * 验证动态码是否有效
     * @param username 登录帐号
     * @param totpcode 动态码
     * @return
     */
   public boolean checkTotpcode(String username, String totpcode) {
       return ga.authorizeUser(username, Integer.valueOf(totpcode));
   }

    /**
     * 动态码密钥生成方法
     * @param username
     * @return
     */
   public String generatorQrcode(String username) {
       GoogleAuthenticatorKey key = ga.createCredentials();
       final String secret = key.getKey();
       final List<Integer> scratchCodes = key.getScratchCodes();
       //公司名 标签 密钥
       String otpAuthURL = GoogleAuthenticatorQRGenerator.getOtpAuthURL("公司名称", username, key);
       System.out.println("Please register (otpauth uri): " + otpAuthURL);
       System.out.println("Base64-encoded secret key is " + secret);
       return otpAuthURL;
   }


    /**
     * 定义基于内存的TOTP 密钥仓库
     */
    public static class CredentialRepository implements ICredentialRepository {
        ConcurrentHashMap<String, String> store = null;


        public CredentialRepository(ConcurrentHashMap<String, String> store) {
            this.store = store;
        }

        @Override
        public String getSecretKey(String userName) {

            return store.get(userName) == null ? "" : store.get(userName);
        }

        @Override
        public void saveUserCredentials(String userName, String secretKey, int validationCode, List<Integer> scratchCodes) {
            store.put(userName, secretKey);
        }
    }

    public static void main(String[] args) {
        TOTPUtils.getInstance().generatorQrcode("admina");
    }
}
