#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.util.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

/**
 * Classe criada para dar carga no Banco de dados, devido a limitação do Hiberante onde estrutura DML é criada automaticamente de acordo 
 * com as classes mapeadas quando utililzamos a propriedade "hibernate.hbm2ddl.auto" com os valores "create-drop" ou "create",
 * como precisamos customizar algumas nomenclaturas foi optado carregar as tabelas dessa forma. 
 * @author c112141
 * @since 1.0
 * @version 1.0.0
 *
 */
@WebListener
public class CreateDatabase implements javax.servlet.ServletContextListener {

	@Resource(mappedName = "java:jboss/H2DS")
	private DataSource ds;

	private static final Logger log = Logger.getLogger(CreateDatabase.class.getName());
	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		importDatabase("/${parentArtifactId}_create_bd.sql");
		importDatabase("/import.sql");
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
		
	}

	private void importDatabase(String file) {
		String s = new String();
		StringBuffer sb = new StringBuffer();

		try {
			URL url = getClass().getClassLoader().getResource(file);
			FileReader fr = new FileReader(new File(url.getPath()));

			BufferedReader br = new BufferedReader(fr);

			while ((s = br.readLine()) != null) {
				if (s.isEmpty())
					continue;
				sb.append(s.trim());
				sb.append(" ");
			}
			br.close();
			
			String[] inst = sb.toString().split(";");

			Connection conn = ds.getConnection();
			Statement st = conn.createStatement();

			for (int i = 0; i < inst.length; i++) {
				if (!inst[i].trim().equals("")) {
					st.executeUpdate(inst[i]);
					log.info(">>" + inst[i]);
				}
			}

		} catch (Exception e) {
			log.severe("*** Error : " + e.toString());
			log.severe("*** ");
			log.severe("*** Error : ");
			e.printStackTrace();
			log.severe("${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}");
			log.severe(sb.toString());
		}
	}

	

	
}
