package com.vgorash.datamarkup.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Collection;
import java.util.LinkedHashSet;

@Entity
@Getter
@Setter
@Table(name = "ida_user")
public class User implements UserDetails {

    @Id
    private String username;
    private String password;

    private boolean isAccessor;
    private boolean isDeveloper;
    private boolean isAuthored;

    public User(){}

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        LinkedHashSet<Role> roles = new LinkedHashSet<>();
        if(isAccessor){
            roles.add(new Role(Role.RoleEnum.ROLE_ACCESSOR));
        }
        if(isDeveloper){
            roles.add(new Role(Role.RoleEnum.ROLE_DEVELOPER));
        }
        if(isAuthored){
            roles.add(new Role(Role.RoleEnum.ROLE_USER));
        }
        return roles;
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
