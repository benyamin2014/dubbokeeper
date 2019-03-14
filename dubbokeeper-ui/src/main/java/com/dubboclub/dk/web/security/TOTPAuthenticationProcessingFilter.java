package com.dubboclub.dk.web.security;

import com.dubboclub.dk.web.utils.TOTPUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: dubbokeeper
 * @description: ${description}
 * @author: benyamin
 * @create: 2019-03-13 17:28
 **/
public class TOTPAuthenticationProcessingFilter extends UsernamePasswordAuthenticationFilter {

    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
    public static final String SPRING_SECURITY_FORM_TOTPCODE_KEY = "totpcode";

    private boolean postOnly = true;

    /**/
    public TOTPAuthenticationProcessingFilter() {
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
        this.setAuthenticationManager(getAuthenticationManager());
        SimpleUrlAuthenticationFailureHandler failedHandler = (SimpleUrlAuthenticationFailureHandler) getFailureHandler();
        failedHandler.setDefaultFailureUrl("/login.htm");
//        SimpleUrlAuthenticationSuccessHandler successHandler = (SimpleUrlAuthenticationSuccessHandler)getSuccessHandler();
//        successHandler.setDefaultTargetUrl("/index.htm");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if (!requiresAuthentication(req, res)) {
            chain.doFilter(request, response);
            return;
        }
        if (!checkTotpode(req, res)) return;
        chain.doFilter(request, response);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException {
        return null;
    }

    protected boolean checkTotpode(HttpServletRequest httpServletRequest, HttpServletResponse response) throws IOException, ServletException {
        if (this.postOnly && !httpServletRequest.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + httpServletRequest.getMethod());
        }
        String username = obtainUsername(httpServletRequest);
        String totpcode = obtainTotpcode(httpServletRequest);
        if (username == null) {
            username = "";
        }
        if (totpcode == null) {
            totpcode = "";
        }
        username = username.trim();

        if (!TOTPUtils.getInstance().checkTotpcode(username, totpcode)) {
//            throw new BadCredentialsException(messages.getMessage(
//                    "AbstractUserDetailsAuthenticationProvider.badCode",
//                    "totp code not matched"));
            unsuccessfulAuthentication(httpServletRequest, response, new InsufficientAuthenticationException("Bad totpcode"));
            return false;
        }
        return true;
    }

    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter(SPRING_SECURITY_FORM_USERNAME_KEY);
    }

    protected String obtainTotpcode(HttpServletRequest request) {
        return request.getParameter(SPRING_SECURITY_FORM_TOTPCODE_KEY);
    }
}
