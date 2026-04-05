package com.siparisYonetim.SiparisYonetimSistemi.security.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class SessionService {

    private final HttpServletRequest request;

    public SessionService(HttpServletRequest request) {
        this.request = request;
    }

    public void setAttribute(String key, Object value) {
        HttpSession session = request.getSession(true);
        session.setAttribute(key, value);
    }

    public void setSessionUser(String username, Collection<? extends GrantedAuthority> authorities) {
        setAttribute("sessionUser", username);
        List<String> roles = authorities == null
                ? List.of()
                : authorities.stream().map(GrantedAuthority::getAuthority).toList();
        setAttribute("sessionAuthorities", roles);
    }
}
