package com.yangnan.crm.security.custom;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yangnan.crm.common.pojo.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 封装用户信息：用户基本信息和权限信息
 * 这个类后期会序列化到redis
 *
 */
@Data
@JsonIgnoreProperties({"authorities","password","username","enabled","accountNonExpired","accountNonLocked","credentialsNonExpired"})
public class CustomUserDetails implements UserDetails {
    //用户信息
    private User user;
    //权限信息
    private List<String> permissions;

    public CustomUserDetails(User user, List<String> permissions) {
        this.user = user;
        this.permissions = permissions;
    }

    public CustomUserDetails() {

    }

    //不需要序列化到redis里面
    private List<SimpleGrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities == null) {
            authorities = new ArrayList<>();
            for (String permission : permissions) {
                authorities.add(new SimpleGrantedAuthority(permission));
            }
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
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
}
