package es.udc.cartolab.gvsig.eielforms.domain.generator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import es.udc.cartolab.gvsig.eielforms.formgenerator.FormException;
import es.udc.cartolab.gvsig.users.utils.DBSession;

public class DomainDBReader extends DomainReader {

	private String aplicationSchemaName = "eiel_aplicaciones";

	public DomainDBReader() {

	}

	public DomainDBReader(String applicationSchemaName) {
		this.aplicationSchemaName = applicationSchemaName;
	}

	public String getDomainDefinition(String domainName) throws FormException
	{
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;

		String queryString = "select definicion from \"" + this.aplicationSchemaName + "\".dominios ";
		queryString = queryString + "where nombre = ?";
		String domainXMLDefinition;
		DBSession dbs = DBSession.getCurrentSession();
		if (dbs != null) {
			try
			{

				connection = dbs.getJavaConnection();

				preparedStatement = connection.prepareStatement(queryString);

				int i = 1;
				preparedStatement.setString(i++, domainName);

				resultSet = preparedStatement.executeQuery();

				if (resultSet.next())
					domainXMLDefinition = resultSet.getString(1);
				else {
					domainXMLDefinition = null;
				}

			}
			catch (Exception e)
			{
				throw new FormException(e);
			} finally {
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
			throw new FormException("Sesión no iniciada");
		}

		return domainXMLDefinition;
	}

}
