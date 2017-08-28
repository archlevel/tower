package com.tower.service.web.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tower.service.domain.auth.AuthResourcesDto;
import com.tower.service.domain.auth.AuthUserDto;

public class User extends AuthUserDto implements UserDetails {

    /**
        *
        */
    private static final long serialVersionUID = 7212724580896479318L;

    private List<GrantedAuthority> authorities;

    private List<AuthResourcesDto> resourcess; // 用户能访问那些资源模块

    private String ticket;

    public List<AuthResourcesDto> getResourcess() {
        return resourcess;
    }

    public void setResourcess(List<AuthResourcesDto> resourcess) {
        this.resourcess = resourcess;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

}
