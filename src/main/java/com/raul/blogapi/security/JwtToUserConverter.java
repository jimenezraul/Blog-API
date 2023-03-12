package com.raul.blogapi.security;

import com.raul.blogapi.model.Role;
import com.raul.blogapi.model.User;
import com.raul.blogapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtToUserConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UsernamePasswordAuthenticationToken convert(Jwt jwt) {

        User user = new User();
        user.setId(Long.parseLong(jwt.getSubject()));
        User userFromDb = userRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("User not found"));
        List<Role> roles = userFromDb.getRoles().stream().map(role -> new Role(role)).toList();
        user.setRoles(roles);
        user.setPassword(userFromDb.getPassword());

        Collection<? extends GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(user, jwt, authorities);
    }

}
