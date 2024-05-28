package com.palleteforco.palleteforco.domain.security.oauth;

import com.palleteforco.palleteforco.domain.google.dto.GoogleDto;
import com.palleteforco.palleteforco.domain.google.dto.Role;
import com.palleteforco.palleteforco.domain.google.mapper.GoogleMapper;
import com.palleteforco.palleteforco.global.config.OAuthAttributes;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class OAuth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final GoogleMapper googleMapper;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> deleget = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = deleget.loadUser(userRequest);

        String registrationId = userRequest
                .getClientRegistration()
                .getRegistrationId();

        String userNameAttributeName = userRequest
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes
                .of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        GoogleDto googleDto = saveOrUpdate(attributes);

        httpSession.setAttribute("googleDto", new SessionUtil(googleDto));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(googleDto.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );
    }

    private GoogleDto saveOrUpdate(OAuthAttributes attributes) {
        GoogleDto googleDto;
        if (googleMapper.selectGoogleEmail(attributes.getEmail()) != null) {
            googleDto = googleMapper.selectGoogleEmail(attributes.getEmail());
        } else {
            googleDto = attributes.toEntity();
            googleMapper.insertGoogle(googleDto);
            googleDto = googleMapper.selectGoogleEmail(attributes.getEmail());
        }
        return googleDto;
    }

    public void updateRole(String email, Role role) {
        GoogleDto googleDto = googleMapper.selectGoogleEmail(email);
        if (googleDto != null) {
            googleDto.setRole(role);
            googleMapper.updateGoogle(googleDto);
        }
    }

    public OAuth2User getPrincipal() {
        return (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public String getPrincipalMemberEmail() {
        OAuth2User oAuth2User = getPrincipal();
        return (String) oAuth2User.getAttributes().get("email");
    }
}
