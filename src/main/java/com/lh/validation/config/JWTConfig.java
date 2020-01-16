//package com.lh.validation.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.stereotype.Component;
//
///**
// * @Author liuhh    @Date 2020/1/9 17:29
// */
//@Component
//@ConfigurationProperties(prefix = "jwt")
//public class JWTConfig {
//
//    public static String secret;
//    public static String tokenHeader;
//    public static String tokenPrefix;
//    public static Integer expiration;
//    public static String antMatchers;
//
//    @Value("${secret}")
//    public void setSecret(String secret){
//        JWTConfig.secret = secret;
//    }
//    @Value("${tokenHeader}")
//    public void setTokenHeader(String tokenHeader){
//        JWTConfig.tokenHeader = tokenHeader;
//    }
//    @Value("${tokenPrefix}")
//    public void setTokenPrefix(String tokenPrefix){
//        JWTConfig.tokenPrefix = tokenPrefix;
//    }@Value("${expiration}")
//    public void setExpiration(Integer expiration){
//        JWTConfig.expiration = expiration;
//    }@Value("${antMatchers}")
//    public void setAntMatchers(String antMatchers){
//        JWTConfig.antMatchers = antMatchers;
//    }
//
//
//}
