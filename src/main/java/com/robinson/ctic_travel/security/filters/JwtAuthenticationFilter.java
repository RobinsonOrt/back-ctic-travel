package com.robinson.ctic_travel.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.robinson.ctic_travel.models.Users;
import com.robinson.ctic_travel.security.jwt.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private JwtUtils jwtUtils;

    public JwtAuthenticationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        Users user = null;
        String userEmail = "";
        String userPassword = "";
        try {
            user = new ObjectMapper().readValue(request.getInputStream(), Users.class);
            userEmail = user.getUserEmail();
            userPassword = user.getUserPassword();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userEmail, userPassword);
        return getAuthenticationManager().authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        String token = jwtUtils.generateAccessToken(user.getUsername());
        String userRole = user.getAuthorities().stream().findFirst().get().getAuthority();

        Map<String, Object> httpResponse = new HashMap<>();
        httpResponse.put("token", token);
        httpResponse.put("userEmail", user.getUsername());
        httpResponse.put("userId", jwtUtils.getUserId(user.getUsername()));
        httpResponse.put("userRole", userRole);
        httpResponse.put("error", false);

        response.getWriter().write(new ObjectMapper().writeValueAsString(httpResponse));
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());
        //add "ola" to the response body

        response.getWriter().flush();
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
