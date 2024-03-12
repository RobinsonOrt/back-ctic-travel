package com.robinson.ctic_travel.service;

import com.robinson.ctic_travel.daos.usersDao.UsersDao;
import com.robinson.ctic_travel.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
    @Autowired
    private UsersDao usersDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersDao.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario o contraseña incorrectos"));

        Collection<? extends GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_".concat(user.getRole().getRoleName())));
        return new User(
                user.getUserEmail(),
                user.getUserPassword(),
                true,
                true,
                true,
                true,
                authorities
        );
    }
}
