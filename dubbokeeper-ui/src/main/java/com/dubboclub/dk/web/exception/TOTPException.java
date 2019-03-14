package com.dubboclub.dk.web.exception;


/**
 * @program: dubbokeeper
 * @description: ${description}
 * @author: benyamin
 * @create: 2019-03-13 18:41
 **/
public class TOTPException extends org.springframework.security.core.AuthenticationException {
    public TOTPException(String msg) {
        super(msg);
    }

}
