#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.util.system;

public class ConfigApplication {
	
	
	//GED	
	public static final String GED_BASE_CM 			= "and.ged.base.cm";
	public static final String GED_USUARIO_SERVICO 	= "and.ged.ususario.servico";
	public static final String GED_SENHA_SISTEMA	= "and.ged.senha.servico";
	public static final String GED_SIGLA_SISTEMA 	= "and.ged.sigla.sistema";
	public static final String GED_REPOSITORIO		= "and.ged.repositorio";
	public static final String GED_INDEX_PESQUISA 	= "and.ged.index.pesquisa";
	//OpenID Connect
	public static final String OIDC_CLIENT 			= "and.oidc.client";
	public static final String OIDC_SECRET 			= "and.oidc.secret";
	public static final String OIDC_URL_AUTH 		= "and.oidc.url.auth";
	public static final String OIDC_URL_TOKEN 		= "and.oidc.url.token";
	public static final String OIDC_URL_CERTS 		= "and.oidc.url.certs";
	public static final String OIDC_URL_INTROSPECCT	= "and.oidc.url.introspect";
	public static final String OIDC_URL_LOGOUT 		= "and.oidc.url.logout";
	

	public static String getSystemProperties(String propertie){
		String result = System.getProperty(propertie);
		if((result == null) || (result.trim().length() == 0))
			throw new RuntimeException(String.format(Messages.getString("MSG_SYS01"),propertie));
		return result;
	}
	

}
