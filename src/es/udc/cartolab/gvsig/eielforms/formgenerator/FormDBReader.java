package es.udc.cartolab.gvsig.eielforms.formgenerator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import es.udc.cartolab.gvsig.users.utils.DBSession;

public class FormDBReader extends FormReader {

	private String aplicationSchemaName = "eiel_aplicaciones";
	
	public FormDBReader() {
		
	}
	
	public FormDBReader(String applicationSchemaName) {
		this.aplicationSchemaName = applicationSchemaName;
	}

	@Override
	public String getFormDefinition(String formName) throws FormException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;

		String queryString = "select definicion from \"" + this.aplicationSchemaName + "\".formularios ";
		queryString = queryString + "where capa = ?";
		String formXMLDefinition;
		DBSession dbs = DBSession.getCurrentSession();
		if (dbs != null) {
			try
			{

				connection = dbs.getJavaConnection();

				preparedStatement = connection.prepareStatement(queryString);

				int i = 1;
				preparedStatement.setString(i++, formName);

				resultSet = preparedStatement.executeQuery();
				if (resultSet.next())
					formXMLDefinition = resultSet.getString(1);
				else {
					formXMLDefinition = null;
				}

			}
			catch (Exception e)
			{
				throw new FormException(e);
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
			throw new FormException("Sesión no iniciada");
		}
		return formXMLDefinition;
	}

}
