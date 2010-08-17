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

public class DomainReader
{

	  private final static String path = "gvSIG"+File.separator+"extensiones"+File.separator+"es.udc.cartolab.gvsig.eielforms"
		+File.separator+"domains"+File.separator; 

  public DomainReader()
  {

  }

  public String getDomainDefinition(String domainName) throws FormException
  {
	  File file = new File(path  + domainName.toLowerCase() + ".xml");
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

  public Domain getDomain(String domainName, String schema, String table)
    throws FormException
  {
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Connection connection = null;

    HashMap values = new HashMap();

    String queryString = "select codigo, nombre from \"" + schema + "\"." + table;
    queryString = queryString + " order by nombre";
      DBSession dbs = DBSession.getCurrentSession();
      if (dbs != null) {
    	  try
    	  {
      connection = dbs.getJavaConnection();
      preparedStatement = connection.prepareStatement(queryString);

      resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        values.put(resultSet.getString(1), resultSet.getString(2));
      }

      resultSet.close();
      preparedStatement.close();
      connection.close();
      }
    	  catch (Exception e)
    	  {
    	  }
    	  finally
    	  {
    		  if (preparedStatement!=null) {
    			  try {
    				preparedStatement.close();
    			} catch (SQLException e) {
    				throw new FormException(e);
    			}
    		  }
    		  if (resultSet != null) {
    			  try {
    				resultSet.close();
    			} catch (SQLException e) {
    				throw new FormException(e);
    			}
    		  }
    	  }
    } else {
    	throw new FormException("No se ha iniciado sesion");
    }

    Domain domain = new UserDomain(domainName, values);
    return domain;

  }
}