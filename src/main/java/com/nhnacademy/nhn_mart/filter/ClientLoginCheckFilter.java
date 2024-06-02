
package com.nhnacademy.nhn_mart.filter;//package com.nhnacademy.student_info.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebFilter(
        filterName = "ClientLoginCheckFilter",
        urlPatterns = {"/client/inquiry/*"}
)
public class ClientLoginCheckFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
       HttpSession session = req.getSession(false);
       log.warn("session클라이언트 필터!:"+session);
        if(session == null || session.getAttribute("id")==null) {
            res.sendRedirect("/client/login");
            return ;
        }
        chain.doFilter(req, res);
    }
}
