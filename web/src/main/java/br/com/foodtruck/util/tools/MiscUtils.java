package br.com.foodtruck.util.tools;

import javax.servlet.http.HttpServletRequest;

/**
 * Classe responsável pelos métodos utilitarios que podem ser usados em todo o sistema.
 * @author c112141
 *
 */
public class MiscUtils {

	public static String getIpAddress(HttpServletRequest req){
		 String ipAddress = req.getHeader("x-forwarded-for");
	        if (ipAddress == null) {
	            ipAddress = req.getHeader("X_FORWARDED_FOR");
	            if (ipAddress == null){
	                ipAddress = req.getRemoteAddr();
	            }
	        }
		
		return ipAddress;
	}
	
}
