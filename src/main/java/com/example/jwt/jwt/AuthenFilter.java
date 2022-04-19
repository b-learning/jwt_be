package com.example.jwt.jwt;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // chạy khi nào? Bất kì request nào tới server thì thằng filter
        // như lễ tân check coi có quyền hay k (check token).

        // check coi token có hợp lệ hay k?
        // validate token
        System.out.println("Filter run");

        // filter cho phép truy cập thì mới đc truy cập
        filterChain.doFilter(request, response);
    }
}
