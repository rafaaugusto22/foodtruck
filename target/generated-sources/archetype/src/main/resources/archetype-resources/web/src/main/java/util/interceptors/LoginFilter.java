#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.util.interceptors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ${package}.util.system.ConfigApplication;

/**
 * Classe responsável por filtrar as requisições via HTTP, caso o usuário não esteja ativo o usuario é redirecionado 
 * para o IdP (Identity Provider) para realizar o SSO (Single Sign On).
 *  
 * @author c112141
 *
 */
@WebFilter({ "/*"})
public class LoginFilter implements Filter {
	
	public LoginFilter() {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) servletRequest;
		HttpServletResponse res = (HttpServletResponse) servletResponse;
			
		String uri = req.getRequestURI().replace(req.getContextPath(), "");
		
		//verifica se a requisição não é da pagina inicial, pois se for precisa de autenticação via SSO.
		if(!uri.equals("/")){
			chain.doFilter(servletRequest, servletResponse);
			return;
		}
		
		//verifica se o parametro "code" foi passado na url. 
		if (req.getParameter("code") != null) {	
			chain.doFilter(servletRequest, servletResponse);
			return;
		}		
		//redireciona ao OpenID Provider para realizar o login.		
		res.sendRedirect(ConfigApplication.getSystemProperties(ConfigApplication.OIDC_URL_AUTH) + "?response_type=code"
				+ "&scope=openid" + "&client_id=" + ConfigApplication.getSystemProperties(ConfigApplication.OIDC_CLIENT)
				+ "&redirect_uri=" + req.getRequestURL().toString().replace(req.getRequestURI().toString(), "")
				+ req.getContextPath() + "/");
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

}