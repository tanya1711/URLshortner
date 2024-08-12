package com.example.demo.filter;


import com.example.demo.service.UserService;
import io.jsonwebtoken.Claims;
import io.micrometer.common.lang.NonNullApi;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@NonNullApi
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    @Autowired
    private DaoAuthenticationProvider authenticationProvider;

    @Autowired

    private UserService userService;
//    @Autowired
//    private JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            System.out.println("ENTERED FILTER!!!");
            System.out.println(request.getRequestURI());
            System.out.println(request.getMethod());
            if (request.getRequestURI().equals("/shorten")) {
                String authHeader = request.getHeader("Authorization");
                System.out.println(authHeader);
                byte[] decodedBytes = Base64.getDecoder().decode(authHeader.split("Basic ")[1]);
                String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);

                // Split the decoded string into username and password
                String[] credentials = decodedString.split(":", 2);
                String username = credentials[0];
                String password = credentials[1];

                // Output the results
                System.out.println("Username: " + username);
                System.out.println("Password: " + password);
                Authentication authentication = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(username, password));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                if (userService.getCountByUsername(username) < 4) {
                    userService.incrementCount(username);
                }
                else {
                    System.out.println("OOPs you reached your free trial limit!");
                }
//                    String token = "Bearer " + jwtTokenService.generateToken(username, password);
//                    response.addHeader("Authorization", token);
//                    System.out.println("context");
//                    System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
//            }
//            else {
//                String token = request.getHeader("Authorization");
//                if(jwtTokenService.isValidate(token)){
//                    System.out.println("hey token is valid");
//                    Claims claims = jwtTokenService.extractUserNamePasswordFromJwtToken(token);
//                    Authentication authentication = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(claims.get("username"), claims.get("password")));
//                    System.out.println("auth obj "+authentication.getPrincipal().toString());
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//
//                }
//                else {
//                    System.out.println("sorry invalid token");
//
//                }
            }

        } catch (BadCredentialsException e) {
            System.out.println("Invalid/UserName password");
        }

        filterChain.doFilter(request, response);

    }


}
