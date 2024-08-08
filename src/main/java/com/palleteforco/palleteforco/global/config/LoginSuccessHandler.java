package com.palleteforco.palleteforco.global.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        log.info("---------- login success ----------");
        List<String> roleName = new ArrayList<>();

        authentication.getAuthorities().forEach(authority -> {
            roleName.add(authority.getAuthority());
        });

        if (roleName.contains("ROLE_GUEST")) {
            response.sendRedirect("/members/join");
            return;
        }

        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(1800);

        response.sendRedirect("/");
    }
}
