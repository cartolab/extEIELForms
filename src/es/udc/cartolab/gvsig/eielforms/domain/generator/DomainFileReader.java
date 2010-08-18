package es.udc.cartolab.gvsig.eielforms.domain.generator;

import es.udc.cartolab.gvsig.eielforms.domain.Domain;
import es.udc.cartolab.gvsig.eielforms.domain.UserDomain;
import es.udc.cartolab.gvsig.eielforms.formgenerator.FormException;
import es.udc.cartolab.gvsig.users.utils.DBSession;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import javax.sql.DataSource;

public class DomainFileReader extends DomainReader
{

	private final static String path = "gvSIG"+File.separator+"extensiones"+File.separator+"es.udc.cartolab.gvsig.eielforms"
	+File.separator+"domains"+File.separator; 
	
	private String pathToDomains;
	
	public DomainFileReader() {
		this(path);
	}
	
	public DomainFileReader(String pathToDomains) {
		this.pathToDomains = pathToDomains;
	}

	public String getDomainDefinition(String domainName) throws FormException
	{
		File file = new File(pathToDomains  + domainName.toLowerCase() + ".xml");
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
		} catch (FileNotFoundException e) {
			throw new FormException(e);
		} catch (IOException e) {
			throw new FormException(e);
		}
		return sb.toString();
	}

}