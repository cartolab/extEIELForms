package es.udc.cartolab.gvsig.eielforms.domain.generator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import es.udc.cartolab.gvsig.eielforms.domain.Domain;
import es.udc.cartolab.gvsig.eielforms.domain.UserDomain;
import es.udc.cartolab.gvsig.eielforms.formgenerator.FormException;
import es.udc.cartolab.gvsig.users.utils.DBSession;

public abstract class DomainReader {

	public abstract String getDomainDefinition(String domainName) throws FormException;


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
