package es.udc.cartolab.gvsig.eielforms.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.iver.cit.gvsig.fmap.drivers.DBException;

import es.udc.cartolab.gvsig.eielforms.formgenerator.FormException;
import es.udc.cartolab.gvsig.users.utils.DBSession;

public class FormsDAO {
	
	public FormsDAO() {
	}
	
	public HashMap<String, String> getValues(HashMap key, String schemaName, String table, List<String> fields) throws FormException {
		ResultSet resultSet = null;
	    Connection connection = null;
	    Statement statement = null;
	    String condition = getWhereCondition(key);
	    String queryString = "SELECT ";
	    HashMap<String, String> result = new HashMap<String, String>();

	    for (int i = 0; i < fields.size(); ++i) {
	      queryString = queryString + ((String)fields.get(i)) + ", ";
	    }
	    queryString = queryString.substring(0, queryString.length() - 2) + " ";

	    if (!(schemaName.equals("")))
	      queryString = queryString + "FROM \"" + schemaName + "\"." + table + " " + condition;
	    else {
	      queryString = queryString + "FROM " + table + " " + condition;
	    }
	    System.out.println("Ejecutando la consulta >>>>>>>>> \n" + queryString);
	    try
	    {
	    	DBSession dbs = DBSession.getCurrentSession();
	    	if (dbs!=null) {
	    	connection = dbs.getJavaConnection();
	    	statement = connection.createStatement();
	        resultSet = statement.executeQuery(queryString);

	        if (resultSet.next()) {
	          for (int i = 0; i<fields.size() ; ++i) { 
	            result.put((String)fields.get(i), resultSet.getString(fields.get(i)));
	          }
	        }
	    	} else {
	    		throw new FormException("La sesion no se ha iniciado");
	    	}

	        return result;
	    } catch (SQLException e) {
	    	try {
				DBSession.reconnect();
			} catch (DBException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {
				throw new FormException(e);
			}
	    } finally {
	    	closeResultSet(resultSet);
	    	closeStatement(statement);
	    }
	}

	 public void updateEntity(HashMap key, String schemaName, String table, HashMap fields)
	    throws FormException
	  {
	    Connection connection = null;
	    Statement statement = null;

	    String condition = getWhereCondition(key);
	    Set fieldsSet = fields.keySet();
	    Iterator fieldsIterator = fieldsSet.iterator();

	    String updateString = "";
	    if (!(schemaName.equals("")))
	      updateString = "UPDATE \"" + schemaName + "\"." + table + " SET \n";
	    else
	      updateString = "UPDATE " + table + " SET \n";
	    while (fieldsIterator.hasNext()) {
	      String oneField = (String)fieldsIterator.next();
	      String oneFieldValue = (String)fields.get(oneField);
	      if (oneFieldValue != null && oneFieldValue.compareTo("") != 0) {
	        updateString = updateString + oneField + " = '" + fields.get(oneField) + "',\n ";
	      } else {
	      updateString = updateString + oneField + " = null,\n ";
	      }
	    }

	    updateString = updateString.substring(0, updateString.length() - 3);
	    updateString = updateString + "\n " + condition;

	    System.out.println("SENTENCIA UPDATE EJECUTADA >>>>>>>>> \n" + updateString);
	    try
	    {
	      DBSession dbs = DBSession.getCurrentSession();
	      if (dbs!= null) {
	      connection = dbs.getJavaConnection();
	      statement = connection.createStatement();

	      statement.executeUpdate(updateString);
	      connection.commit();
	    	} else {
	    		throw new FormException("La sesion no se ha iniciado");
	    	}
	    }
	    catch (Exception e)
	    {
	      throw new FormException(e);
	    } finally {
//	      closeResultSet(resultSet);
	      closeStatement(statement);
	    }
	  }

	  public void insertEntity(HashMap fields, String schemaName, String table)
	    throws FormException
	  {
	    Connection connection = null;
	    Statement statement = null;

	    Set fieldsSet = fields.keySet();
	    Iterator fieldsIterator = fieldsSet.iterator();

	    String insertString = "";

	    if (!(schemaName.equals("")))
	      insertString = "INSERT INTO \"" + schemaName + "\"." + table + "\n";
	    else
	      insertString = "INSERT INTO " + table + "\n";
	    String fieldsString = "(";
	    String valuesString = "(";

	    while (fieldsIterator.hasNext()) {
	      String oneField = (String)fieldsIterator.next();
	      fieldsString = fieldsString + oneField + ", ";

	      if (((String)fields.get(oneField)).compareTo("") == 0) {
	        valuesString = valuesString + "null, ";
	      }
	      valuesString = valuesString + "'" + ((String)fields.get(oneField)).trim() + "', ";
	    }

	    fieldsString = fieldsString.substring(0, fieldsString.length() - 2) + ") ";
	    valuesString = valuesString.substring(0, valuesString.length() - 2) + ") ";

	    insertString = insertString + fieldsString + "VALUES " + valuesString;

	    System.out.println("SENTENCIA INSERT EJECUTADA >>>>>>>>> \n" + insertString);
	    try
	    {
	      DBSession dbs = DBSession.getCurrentSession();
	      if (dbs!=null) {
	      connection = dbs.getJavaConnection();

	      statement = connection.createStatement();
	      statement.execute(insertString);
    	} else {
    		throw new FormException("La sesion no se ha iniciado");
    	}
	    }
	    catch (Exception e)
	    {
	    	try {
				DBSession.reconnect();
			} catch (DBException e1) {
				throw new FormException(e1);
			} 
	      throw new FormException(e);
	    } finally {
	      closeStatement(statement);
	    }
	  }

	  public int deleteEntity(HashMap key, String schemaName, String table)
	    throws FormException
	  {
	    Connection connection = null;
	    Statement statement = null;
	    String condition = getWhereCondition(key);

	    String deleteString = "";

	    if (!(schemaName.equals("")))
	      deleteString = "DELETE FROM \"" + schemaName + "\"." + table + "\n";
	    else {
	      deleteString = "DELETE FROM " + table + "\n";
	    }
	    deleteString = deleteString + condition;
	    try
	    {
	    	DBSession dbs = DBSession.getCurrentSession();
	    	if (dbs != null) {
	      connection = dbs.getJavaConnection();

	      statement = connection.createStatement();
	      int updatedRows = statement.executeUpdate(deleteString);

	      System.out.println("SENTENCIA DELETE EJECUTADA >>>>>>>>> \n" + deleteString);

	      int i = updatedRows;

	      return i;
	    	} else {
	    		throw new FormException("La sesion no se ha iniciado");
	    	}
	    }
	      catch (Exception e)
		    {
		    	try {
					DBSession.reconnect();
				} catch (DBException e1) {
					throw new FormException(e1);
				} 
		      throw new FormException(e);
		    } finally {
		      closeStatement(statement);
		    }
	  }
	  
	  public String getHighestValue(HashMap key, String schemaName, String table, String field) throws FormException {
		  Connection connection = null;
		  Statement statement = null;
		  ResultSet resultSet = null;

		  ArrayList fieldsCollection = new ArrayList();
		  String condition = getWhereCondition(key);
		  condition = condition + " AND " + field + " IS NOT NULL";

		  String queryString = "SELECT " + field + " ";

		  if (!(schemaName.equals("")))
			  queryString = queryString + "FROM \"" + schemaName + "\"." + table + " " + condition;
		  else {
			  queryString = queryString + "FROM " + table + " " + condition;
		  }

		  queryString = queryString + " ORDER BY " + field + " DESC LIMIT 1";
		  System.out.println("CONSULTA EJECUTADA >>>>>>>>> \n" + queryString);

		  String highestValue = null;
		  try
		  {
			  DBSession dbs = DBSession.getCurrentSession();
			  if (dbs != null) {
				  connection = dbs.getJavaConnection();
				  statement = connection.createStatement();

				  resultSet = statement.executeQuery(queryString);

				  
				  while (resultSet.next()) {
					  highestValue = resultSet.getString(1);
				  }
			  } else {
				  throw new FormException("La sesion no se ha iniciado");
			  }
		  }
		  catch (Exception e)
		  {
			  try {
				  DBSession.reconnect();
			  } catch (DBException e1) {
				  throw new FormException(e1);
			  } 
			  throw new FormException(e);
		  } finally {
			  closeResultSet(resultSet);
			  closeStatement(statement);
		  }
		  
		  if (highestValue == null) {
			  highestValue = "00";
		  }
		  return highestValue;
	  }

	  public ArrayList getFieldsCollection(HashMap key, String schemaName, String table, ArrayList fields)
	    throws FormException
	  {
	    Connection connection = null;
	    Statement statement = null;
	    ResultSet resultSet = null;

	    ArrayList fieldsCollection = new ArrayList();
	    String condition = getWhereCondition(key);

	    String queryString = "SELECT ";

	    for (int i = 0; i < fields.size(); ++i) {
	      queryString = queryString + ((String)fields.get(i)) + ", ";
	    }
	    queryString = queryString.substring(0, queryString.length() - 2) + " ";

	    if (!(schemaName.equals("")))
	      queryString = queryString + "FROM \"" + schemaName + "\"." + table + " " + condition;
	    else {
	      queryString = queryString + "FROM " + table + " " + condition;
	    }
	    System.out.println("CONSULTA EJECUTADA >>>>>>>>> \n" + queryString);
	    try
	    {
	      DBSession dbs = DBSession.getCurrentSession();
	      if (dbs != null) {
	      connection = dbs.getJavaConnection();
	      statement = connection.createStatement();

	      resultSet = statement.executeQuery(queryString);

	      while (resultSet.next()) {
	        HashMap result = new HashMap();
	        for (int i = 0; i < fields.size(); ++i) {
	          result.put((String)fields.get(i), resultSet.getString(i + 1));
	        }
	        fieldsCollection.add(result);
	      }

	      ArrayList localArrayList1 = fieldsCollection;

	      return localArrayList1;
	    	} else {
	    		throw new FormException("La sesion no se ha iniciado");
	    	}
	    }
	      catch (Exception e)
		    {
		    	try {
					DBSession.reconnect();
				} catch (DBException e1) {
					throw new FormException(e1);
				} 
		      throw new FormException(e);
		    } finally {
		    	closeResultSet(resultSet);
		      closeStatement(statement);
		    }
	  }

	  private String getWhereCondition(HashMap key) {
	    Set keySet = key.keySet();
	    Iterator claveIterator = keySet.iterator();

	    String condition = "";

	    if (claveIterator.hasNext()) {
	      condition = "WHERE ";
	      while (claveIterator.hasNext()) {
	        String oneKey = (String)claveIterator.next();
	        condition = condition + oneKey + " = '" + key.get(oneKey) + "' and ";
	      }
	      condition = condition.substring(0, condition.length() - 5);
	    }
	    return condition;
	  }
	
	  
	  private void closeStatement(Statement statement) throws FormException {
		  if (statement!=null) {
			  try {
				statement.close();
			} catch (SQLException e) {
				throw new FormException(e);
			}
		  }
	  }
	  
	  private void closeResultSet(ResultSet resultSet) throws FormException {
		  if (resultSet != null) {
			  try {
				resultSet.close();
			} catch (SQLException e) {
				throw new FormException(e);
			}
		  }
	  }
}
