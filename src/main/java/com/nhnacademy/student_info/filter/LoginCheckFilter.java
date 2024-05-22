//package com.nhnacademy.student_info.filter;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.*;
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.IOException;
//
//@Slf4j
//@WebFilter(
//        filterName = "LoginCheckFilter",
//        urlPatterns = {"/student/modify/*","/student/view/*" }
//)
//public class LoginCheckFilter extends HttpFilter {
//    @Override
//    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
//        boolean sessionCookieExists = false;
//        Cookie[] cookies = req.getCookies();
//
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if ("SESSION".equals(cookie.getName())) {
//                    sessionCookieExists = true;
//                    break;
//                }
//            }
//        }
//
//        if (!sessionCookieExists) {
//            res.sendRedirect("/login");
//            return;
//        }
//
//        chain.doFilter(req, res);
//    }
//}