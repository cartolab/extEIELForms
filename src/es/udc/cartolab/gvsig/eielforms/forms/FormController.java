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

package es.udc.cartolab.gvsig.eielforms.forms;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import es.udc.cartolab.gvsig.eielforms.dependency.Dependency;
import es.udc.cartolab.gvsig.eielforms.dependency.DependencyMasterField;
import es.udc.cartolab.gvsig.eielforms.dependency.DependencyMasterFieldRetriever;
import es.udc.cartolab.gvsig.eielforms.field.FieldController;
import es.udc.cartolab.gvsig.eielforms.field.FieldInterface;
import es.udc.cartolab.gvsig.eielforms.formgenerator.FormException;
import es.udc.cartolab.gvsig.eielforms.formgenerator.Subject;
import es.udc.cartolab.gvsig.eielforms.groups.FieldGroup;
import es.udc.cartolab.gvsig.eielforms.subforms.SubForm;
import es.udc.cartolab.gvsig.eielforms.util.FormsDAO;
import es.udc.cartolab.gvsig.eielutils.constants.Constants;

public class FormController extends Subject
{
	private boolean knowKey;
	private String dataBase;
	private String table;
	private String layer;
	private ArrayList key;
	private FormInterface formInterface;
	private String name;
	private DependencyMasterFieldRetriever dependencyMasterFieldRetriever;
	DecimalFormat df;

	public FormController(String layer, String dataBase, String table, String layout, String name, String title)
	{
		this.dependencyMasterFieldRetriever = new DependencyMasterFieldRetriever();
		this.layer = layer;
		this.dataBase = dataBase;
		this.table = table;
		this.name = name;

		this.df = new DecimalFormat("000");

		this.knowKey = false;
		this.key = new ArrayList();
		this.formInterface = new FormInterface(this, layout, title);
	}

	public String getDataBase() {
		return this.dataBase;
	}

	public String getTable() {
		return this.table;
	}

	public FormInterface getInterface() {
		return this.formInterface;
	}

	public String getLayer() {
		return this.layer;
	}

	public String getName() {
		return this.name;
	}

	public void addGroup(FieldGroup grupo) {
		this.formInterface.addGroup(grupo);
	}

	public void addSubForm(SubForm subform) {
		this.formInterface.addSubFormulario(subform);
	}

	public ArrayList getGroups() {
		return this.formInterface.getGroups();
	}

	public void addDependency(Dependency dependency) {
		this.formInterface.addDependency(dependency);
	}

	public ArrayList getDependencies() {
		return this.formInterface.getDependencies();
	}

	public void addSubformsButton(ArrayList subforms)
	{
		this.formInterface.addSubForm(subforms);
	}

	//  public void setModal(boolean modal)
	//  {
		//    this.formInterface.setModal(modal);
	//  }

	public ArrayList getFields()
	{
		ArrayList grupos = getGroups();
		ArrayList dependencias = getDependencies();
		ArrayList temporal = new ArrayList();
		for (int i = 0; i < grupos.size(); ++i) {
			temporal.addAll(((FieldGroup)grupos.get(i)).getFields());
		}
		for (int i = 0; i < dependencias.size(); ++i) {
			temporal.addAll(((Dependency)dependencias.get(i)).getOwnFields());
		}
		return temporal;
	}


	public ArrayList getFieldsInterface()
	{
		ArrayList grupos = getGroups();
		ArrayList dependencias = getDependencies();
		ArrayList temporal = new ArrayList();

		for (int i = 0; i < grupos.size(); ++i) {
			temporal.addAll(((FieldGroup)grupos.get(i)).getFieldsInterface());
		}

		for (int i = 0; i < dependencias.size(); ++i) {
			temporal.addAll(((Dependency)dependencias.get(i)).getOwnFieldsInterface());
		}

		return temporal;
	}

	public ArrayList getKey()
	{
		ArrayList grupos = getGroups();
		ArrayList dependencias = getDependencies();

		if (!this.knowKey) {
			for (int i = 0; i < grupos.size(); ++i) {
				this.key.addAll(((FieldGroup)grupos.get(i)).getKey());
			}
			for (int i = 0; i < dependencias.size(); ++i) {
				this.key.addAll(((Dependency)dependencias.get(i)).getKey());
			}
			this.knowKey = true;
		}
		return this.key;
	}

	public void showInterface(boolean visible) {
		this.formInterface.showInterface(visible);
	}

	private HashMap getEielKey(HashMap fields) {
		HashMap aux = new HashMap();

		ArrayList<String> keyFields = new ArrayList<String>();
		keyFields.add("fase");
		keyFields.add("provincia");
		keyFields.add("municipio");

		Set keySet = fields.keySet();
		Iterator iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String kStr = (String) iterator.next();
			if (keyFields.contains(kStr.toLowerCase())) {
				aux.put(kStr, fields.get(kStr));
			}
		}

		return aux;
	}

	public void executeQuery(HashMap fields)
	{
		ArrayList groups = getGroups();
		for (int i = 0; i < groups.size(); ++i) {
			ArrayList groupOfFields = ((FieldGroup)groups.get(i)).getFields();

			for (int j = 0; j < groupOfFields.size(); ++j) {
				FieldController oneField = (FieldController)groupOfFields.get(j);
				String value = (String)fields.get(oneField.getName());
				if (value == null && oneField.isOrden()) {
					FormsDAO fdao = new FormsDAO();
					try {
						String highestVal = fdao.getHighestValue(getEielKey(fields), dataBase, table, oneField.getName());
						Integer val = Integer.parseInt(highestVal) + 1;
						value = df.format(val);

					} catch (FormException e) {
						e.printStackTrace();
					} catch (Exception e) {
						value = "999";
					}
				}
				if (value == null) {
					if (!oneField.getDefaultValue().equals("")) {
						value = oneField.getDefaultValue();
					}
				}
				oneField.setValue(value);
			}

		}

		ArrayList dependencies = getDependencies();
		for (int i = 0; i < dependencies.size(); ++i) {
			Dependency oneDependency = (Dependency)dependencies.get(i);
			ArrayList groupOfFields = oneDependency.getFields();

			if (oneDependency.getDependencyMasterField() != null)
			{
				for (int j = 0; j < groupOfFields.size(); ++j) {
					FieldController oneField = (FieldController)groupOfFields.get(j);

					if (oneField.getName().compareTo(oneDependency.getDependencyMasterField().getField().getName()) == 0) {
						oneField.setValue((String)fields.get(oneDependency.getName() + ".." + oneDependency.getDependencyMasterField().getForeignField()));
					} else {
//						oneField.setValue((String)fields.get(oneDependency.getName() + ".." + oneField.getName()));
						oneField.setValue(oneField.getDefaultValue());
					}

				}

				DependencyMasterField oneDependencyMasterField = oneDependency.getDependencyMasterField();
				if (oneDependencyMasterField.getSecondaryFields().size() > 0) {
					String masterFieldKey = "";

					ArrayList masterFieldNames = new ArrayList(oneDependencyMasterField.getSecondaryFields());
					masterFieldNames.add(oneDependencyMasterField.getField().getName());

					for (int j = 0; j < masterFieldNames.size(); ++j) {
						masterFieldKey = masterFieldKey + (String)fields.get(new StringBuilder().append(oneDependency.getName()).append("..").append(masterFieldNames.get(j)).toString()) + " ";
					}
					masterFieldKey = masterFieldKey.substring(0, masterFieldKey.length() - 1);

					FieldController oneField = oneDependencyMasterField.getField();
					oneField.setValue(masterFieldKey);
				}

			}
			else
			{
				for (int j = 0; j < groupOfFields.size(); ++j) {
					FieldController oneField = (FieldController)groupOfFields.get(j);
//					oneField.setValue((String)fields.get(oneDependency.getName() + ".." + oneField.getName()));
					oneField.setValue(oneField.getDefaultValue());
				}
			}
		}

		this.formInterface.loadData();
		this.formInterface.performQuery();
	}

	public void initFields() {
		this.formInterface.initFields();
	}

	public void startInsertion() {
		this.formInterface.startInsertion();
	}

	public void insert()
	{
		ArrayList<String> idFields = new ArrayList<String>();
		idFields.add("id");
		idFields.add("gid");
		ArrayList fields = getFields();
		ArrayList fieldsInterface = getFieldsInterface();
		HashMap<String, String> fieldValues = new HashMap<String, String>();
		for (int i=0; i< fieldsInterface.size(); i++) {
			FieldInterface fi = (FieldInterface) fieldsInterface.get(i);
			FieldController fc = fi.getField();
			String fieldName = fc.getName();
			if (!(fi instanceof DependencyMasterField) && !idFields.contains(fieldName.toLowerCase())) {
				fieldValues.put(fieldName, fc.getValue());
			}
		}
		FormsDAO fdao = new FormsDAO();
		try {
			fdao.insertEntity(fieldValues, dataBase, table);
		} catch (FormException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


//		this.formInterface.showInterface(true);

	}

	public boolean validate()
	{
		ArrayList grupos = getGroups();
		ArrayList dependencias = getDependencies();
		boolean esValido = true;

		for (int i = 0; i < grupos.size(); ++i) {
			esValido &= ((FieldGroup)grupos.get(i)).validate();
		}

		for (int i = 0; i < dependencias.size(); ++i) {
			esValido &= ((Dependency)dependencias.get(i)).validate();
		}

		//    this.formInterface.update();

		return esValido;
	}

	public void fillForm(HashMap keys) {

		ArrayList fields = getFields();
		ArrayList<String> fieldNames = new ArrayList<String>();
		for (int i=0; i<fields.size(); i++){
			fieldNames.add(((FieldController) fields.get(i)).getName());
		}

		//	  ArrayList dep = getDependencies();
		//	  for (int i=0; i<dep.size(); i++) {
		//		  ArrayList depFields = ((Dependency)dep.get(i)).getFields();
		//		  for (int j=0; j<depFields.size(); j++) {
		//			  String fieldName = ((FieldController)depFields.get(j)).getName();
		//			  if (!fieldNames.contains(fieldName)) {
		//				  fieldNames.add(fieldName);
		//			  }
		//		  }
		//	  }
		FormsDAO fdao = new FormsDAO();
		HashMap map;
		try {
			map = fdao.getValues(keys, getDataBase(), getTable(), fieldNames);

			map.putAll(getDependenciesFields(map));

			updateDependencyFields(map);
			executeQuery(map);
		} catch (FormException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateDependencyFields(HashMap fields) {
		ArrayList dependencies = getDependencies();

		for (int i = 0; i < dependencies.size(); ++i) {
			if (((Dependency)dependencies.get(i)).getDependencyMasterField() != null) {
				this.dependencyMasterFieldRetriever.updateMasterFields((Dependency)dependencies.get(i), fields);
			} else {
				this.dependencyMasterFieldRetriever.updateFields((Dependency)dependencies.get(i), fields);
			}
		}
	}

	private HashMap getDependenciesFields(HashMap formFields) {

		ArrayList dependencies = getDependencies();
		ArrayList<String> foreignKey = new ArrayList<String>();

		HashMap allDependenciesResult = new HashMap();

		try {
			for (int i=0; i< dependencies.size(); i++) {

				Dependency oneDependency = (Dependency) dependencies.get(i);
				HashMap dependencyFK = new HashMap();

				ArrayList<String> dependencyFieldNames = new ArrayList<String>();
				ArrayList<FieldController> dependencyFields = oneDependency.getFields();

				for (int j=0; j<dependencyFields.size(); j++) {
					String oneDependencyFieldName =  dependencyFields.get(j).getName();;
					DependencyMasterField dmf = oneDependency.getDependencyMasterField();
					if (dmf != null && oneDependencyFieldName.equals(dmf.getField().getName())) {
						oneDependencyFieldName = dmf.getForeignField();
					}
					dependencyFieldNames.add(oneDependencyFieldName);
				}

				foreignKey = oneDependency.getForeignKey();
				for (int j=0; j<foreignKey.size(); j++) {
					String oneForeignKeyField = foreignKey.get(j);
					String oneForeignKeyValue = (String) formFields.get(oneForeignKeyField);
					if (oneForeignKeyValue == null) {
						Constants cts = Constants.getCurrentConstants();
						String value = null;
						if (cts != null) {
							value = cts.getValue(oneForeignKeyField);
						}
						if (value != null) {
							oneForeignKeyValue = value;
						} else {
							oneForeignKeyValue = "";
						}
						formFields.put(oneForeignKeyField, oneForeignKeyValue);
					}
					dependencyFK.put(oneForeignKeyField, oneForeignKeyValue);
				}

				FormsDAO fdao = new FormsDAO();
				HashMap dependencyResult = fdao.getValues(dependencyFK, oneDependency.getDataBase(), oneDependency.getTable(), dependencyFieldNames);
				Set fieldSet = dependencyResult.keySet();
				Iterator fieldsIterator = fieldSet.iterator();

				while (fieldsIterator.hasNext()) {
					String oneField = (String) fieldsIterator.next();
					allDependenciesResult.put(new String(oneDependency.getName() + ".." + oneField), dependencyResult.get(oneField));
				}

				dependencyResult = null;
				dependencyFK = null;


			}
			return allDependenciesResult;
		} catch (FormException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public void update(HashMap key) {

		ArrayList fields = getFields();
		ArrayList fieldsInterface = getFieldsInterface();
		HashMap<String, String> fieldValues = new HashMap<String, String>();
		for (int i=0; i< fieldsInterface.size(); i++) {
			FieldInterface fi = (FieldInterface) fieldsInterface.get(i);
			if (!(fi instanceof DependencyMasterField)) {
				FieldController fc = fi.getField();
				fieldValues.put(fc.getName(), fc.getValue());
			}
		}

		FormsDAO fdao = new FormsDAO();
		try {
			fdao.updateEntity(key, dataBase, table, fieldValues);
		} catch (FormException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	//  public void addEntitiesPanel(SelectEntityPanel selectEntityPanel) {
	//    this.formInterface.addEntitiesPanel(selectEntityPanel);
	//  }

	//  public void showEntitiesPanel(boolean visible) {
	//    this.formInterface.showEntitiesPanel(visible);
	//  }

	//  public void fillEntitiesPanel(Collection entityIds, Integer defaultEntity) {
	//    this.formInterface.fillEntitiesPanel(entityIds, defaultEntity);
	//  }
}
