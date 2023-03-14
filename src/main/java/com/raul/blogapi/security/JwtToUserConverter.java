package com.raul.blogapi.security;

import com.raul.blogapi.model.Role;
import com.raul.blogapi.model.User;
import com.raul.blogapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtToUserConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UsernamePasswordAuthenticationToken convert(Jwt jwt) {
        System.out.println(jwt);
        User userFromDb = userRepository.findById(Long.valueOf(jwt.getSubject())).orElseThrow(() -> new RuntimeException("User not found"));

        List<Role> roles = userFromDb.getRoles().stream().map(role -> new Role(role)).toList();

        Collection<? extends GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        User user = new User();
        user.setId(userFromDb.getId());
        user.setRoles(roles);

        return new UsernamePasswordAuthenticationToken(user, jwt, authorities);
    }

}
