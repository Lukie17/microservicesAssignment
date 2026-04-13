package com.tus.orderServiceA.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;

public class JwtFilter extends GenericFilter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;

		String header = req.getHeader("Authorization");

		// Allow auth endpoint always
		if (req.getRequestURI().contains("/auth")) {
			chain.doFilter(request, response);
			return;
		}

		if (header != null && header.startsWith("Bearer ")) {
			String token = header.substring(7);
			try {
				String username = JwtUtil.validateToken(token);

				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null,
						List.of(new SimpleGrantedAuthority("ROLE_USER")));

				SecurityContextHolder.getContext().setAuthentication(auth);

			} catch (Exception e) {
				System.out.println("❌ Invalid token");
				((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return;
			}
		} else {
			System.out.println("❌ Missing token for request: " + req.getRequestURI());
			((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		chain.doFilter(request, response);
	}
}