package br.upf.topicos.especiais.ged.grupo1.filter;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.upf.topicos.especiais.ged.grupo1.bean.LoginControle;

@WebFilter(urlPatterns = { "/faces/Privado/*" })
public class FiltrarLogin implements Filter {
 
	@Inject
	private LoginControle loginControle;
	
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String contextPath = httpRequest.getContextPath();
 
		if (loginControle.getUsuarioLogado() != null) {
			 chain.doFilter(request, response);
		} else {
			httpResponse.sendRedirect(contextPath + "/faces/LoginForm.xhtml");
		}
		
	}

	@Override
	public void destroy() {
		
	}
	

}
