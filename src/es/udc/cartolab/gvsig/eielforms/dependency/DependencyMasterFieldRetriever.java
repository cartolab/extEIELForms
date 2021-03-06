/*
 * Copyright (c) 2010. Cartolab (Universidade da Coru�a)
 *
 * This file is part of extEIELForms
 *
 * extEIELForms is based on the forms application of GisEIEL <http://giseiel.forge.osor.eu/>
 * devoloped by Laboratorio de Bases de Datos (Universidade da Coru�a)
 *
 * extEIELForms is free software: you can redistribute it and/or modify it under the terms
 * of the GNU General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or any later version.
 *
 * extEIELForms is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with extEIELForms.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package es.udc.cartolab.gvsig.eielforms.dependency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import es.udc.cartolab.gvsig.eielforms.field.FieldController;
import es.udc.cartolab.gvsig.eielforms.util.FormsDAO;

public class DependencyMasterFieldRetriever
{
  private FormsDAO formsDAO;

  public DependencyMasterFieldRetriever()
  {
    this.formsDAO = new FormsDAO();
  }

  @SuppressWarnings("unchecked")
public void updateMasterFields(Dependency dependency, HashMap valoresCampos, boolean alpha)
  {
    try
    {
      HashMap applicationDAOCondition = new HashMap();

      if (dependency.getDependencyMasterField() != null)
      {
	ArrayList<String> masterFieldNames = new ArrayList(dependency.getDependencyMasterField().getSecondaryFields());

	String masterFieldMainFieldName = dependency.getDependencyMasterField().getField().getName();

	masterFieldNames.add(masterFieldMainFieldName);
	String visibleField = dependency.getDependencyMasterField().getVisibleFieldName();

	ArrayList dependencyForeignKey = dependency.getForeignKey();

	ArrayList dependencyFields = dependency.getFields();
	ArrayList dependencyFieldsNames = new ArrayList();

	for (int i = 0; i < dependencyFields.size(); ++i) {
	  FieldController oneDependencyField = (FieldController)dependencyFields.get(i);

	  if (oneDependencyField.getName().compareTo(masterFieldMainFieldName) == 0) {
	    if (!(dependencyFieldsNames.contains(dependency.getDependencyMasterField().getForeignField()))) {
	      dependencyFieldsNames.add(dependency.getDependencyMasterField().getForeignField());
	    }
	  }
	  else if (!(dependencyFieldsNames.contains(oneDependencyField.getName()))) {
	    dependencyFieldsNames.add(oneDependencyField.getName());
	  }

	  if (!(dependencyForeignKey.contains(oneDependencyField.getName())))
	    continue;
	  //          if ((valoresCampos.get(oneDependencyField.getName()) == null) && (oneDependencyField.getIsConstant() == true))
	  //            applicationDAOCondition.put(oneDependencyField.getName(), "");
	  //          else {
	  applicationDAOCondition.put(oneDependencyField.getName(), valoresCampos.get(oneDependencyField.getName()));
	  //          }

	}

	for (int i = 0; i < masterFieldNames.size(); ++i) {
	  if (((String)masterFieldNames.get(i)).compareTo(masterFieldMainFieldName) == 0) {
	    if (!(dependencyFieldsNames.contains(dependency.getDependencyMasterField().getForeignField()))) {
	      dependencyFieldsNames.add(dependency.getDependencyMasterField().getForeignField());
	    }
	  }
	  else if (!(dependencyFieldsNames.contains(masterFieldNames.get(i)))) {
	    dependencyFieldsNames.add(masterFieldNames.get(i));
	  }
	}

	dependencyFieldsNames.add(visibleField);

	ArrayList allDependencyPosibleValues = this.formsDAO.getFieldsCollection(applicationDAOCondition, dependency.getDataBase(), dependency.getTable(), dependencyFieldsNames);

	HashMap dependencyRowValues = new HashMap();
	LinkedHashMap dependencyDomainValues = new LinkedHashMap();
	LinkedHashMap dependencyValuesHashMap = new LinkedHashMap();
	ArrayList<String> depFields = new ArrayList<String>();

	//String[] visibleFields = visibleField.split(" \\|\\| ");
	for (String f : masterFieldNames) {
	  String key = dependency.getName() + ".." + f.trim();
	  if (valoresCampos.containsKey(key)) {
	    depFields.add(key);
	  } else if (valoresCampos.containsKey(f.trim())) {
	    depFields.add(f.trim());
	    if (!alpha) {
	      valoresCampos.put(key, valoresCampos.get(f.trim()));
	    }
	  }
	}

	for (int i = 0; i < allDependencyPosibleValues.size(); ++i) {
	  dependencyRowValues = (HashMap)allDependencyPosibleValues.get(i);

	  String masterFieldKey = "";
	  if (depFields.size()>0) {
	    for (String f : depFields) {
	      String key = f;
	      if (f.contains(".."))
		key = f.substring(f.indexOf("..") + 2);
	      if (dependencyRowValues.containsKey(key)) {
		masterFieldKey = masterFieldKey + dependencyRowValues.get(key) + " "; 
	      }
	    }
	    if (!masterFieldKey.equals("")) {
	      masterFieldKey = masterFieldKey.substring(0, masterFieldKey.length() - 1);
	    }
	  } else {
	    for (int j = 0; j < masterFieldNames.size(); ++j) {
	      //            if (((String)masterFieldNames.get(j)).compareTo(masterFieldMainFieldName) == 0)
	      //              masterFieldKey = masterFieldKey + dependencyRowValues.get(dependency.getDependencyMasterField().getForeignField()) + " ";
	      //            else {
	      //              masterFieldKey = masterFieldKey + dependencyRowValues.get(masterFieldNames.get(j)) + " ";
	      //            }
	      masterFieldKey = masterFieldKey + dependencyRowValues.get(visibleField) + " ";
	    }
	    masterFieldKey = masterFieldKey.substring(0, masterFieldKey.length() - 1);
	  }

	  dependencyDomainValues.put(masterFieldKey, dependencyRowValues.get(dependency.getDependencyMasterField().getVisibleFieldName()));
	  dependencyValuesHashMap.put(masterFieldKey, dependencyRowValues);
	}

	dependency.getDependencyMasterField().setDependencyValues(dependencyValuesHashMap, dependencyDomainValues);

	//set combobox value
	String depValue = "";
	if (depFields.size()>0) {
	  for (String f : depFields) {
	    Object valor = valoresCampos.get(f);
	    if (valor!=null) {
	      depValue = depValue + valor.toString() + " ";
	    }
	  }
	  if (!depValue.equals("")) {
	    depValue = depValue.substring(0, depValue.length() - 1);
	  }
	  dependency.getDependencyMasterField().setValue(depValue);
	} else {
	  dependency.getDependencyMasterField().setValue("");
	}

      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void updateFields(Dependency dependency, HashMap valoresCampos)
  {
    for (int i = 0; i < dependency.getFields().size(); ++i);
  }
}
