package com.palleteforco.palleteforco.global.config;

import com.palleteforco.palleteforco.domain.google.dto.Role;
import com.palleteforco.palleteforco.domain.security.oauth.OAuth2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final OAuth2Service oAuth2Service;

    @Autowired
    public SecurityConfig(OAuth2Service oAuth2Service) {
        this.oAuth2Service = oAuth2Service;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf((csrfConfig) ->
                        csrfConfig.disable())
//                .headers((headers) ->
//                        headers.disable())
                .logout((logoutConfig) ->
                        logoutConfig.disable())
                .formLogin((formLoginConfig) ->
                        formLoginConfig
                                .successHandler(loginSuccessHandler()))

                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers("/", "/login/**", "/product/**").permitAll()
                                .requestMatchers("/cart/**", "/orders", "/dib/**", "/review/**", "/inquiries").hasRole(Role.MEMBER.name())
                                .requestMatchers("/members/join").hasRole(Role.GUEST.name())
                                .anyRequest().authenticated()
                )

                .exceptionHandling((exception) ->
                        exception
                                .accessDeniedPage("/error/403")
                )

                .oauth2Login((oauth2Login) -> oauth2Login
                        .userInfoEndpoint((userInfoEndpoint -> userInfoEndpoint
                                .userService(oAuth2Service)))
                        .successHandler(new LoginSuccessHandler())

                )
                .build();
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
    }
}
