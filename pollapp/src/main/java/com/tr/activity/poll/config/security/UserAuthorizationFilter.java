package com.tr.activity.poll.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

public class UserAuthorizationFilter extends BasicAuthenticationFilter {

    public UserAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null) {
            Claims claims = Jwts.
                    parser().
                    setSigningKey("SecretKeyToGenJWTs".getBytes())
                    .parseClaimsJws(token.replace("Bearer", ""))
                    .getBody();

            String user = claims.getSubject();

            if (user != null) {
                Collection<? extends GrantedAuthority> authorities = getUserAuthority(claims.get("roles"));

                return new UsernamePasswordAuthenticationToken(user, null, authorities);
            }
            return null;
        }
        return null;
    }


    private Collection<? extends GrantedAuthority> getUserAuthority(Object claims) {
        if (claims == null) {
            return null;
        }

        ArrayList<LinkedHashMap<String,String>> authorities = (ArrayList<LinkedHashMap<String, String>>) claims;

        List<SimpleGrantedAuthority> grants = new ArrayList<>();

        for (int i = 0; i < authorities.size(); i++) {
            LinkedHashMap<String, String> arrayVal = (LinkedHashMap<String, String>) authorities.get(i);
            String value = arrayVal.get("authority");

            grants.add(new SimpleGrantedAuthority(value));
        }
        return grants;

    }


}
