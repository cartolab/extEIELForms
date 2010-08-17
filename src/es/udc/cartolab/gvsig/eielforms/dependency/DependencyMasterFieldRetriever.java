package es.udc.cartolab.gvsig.eielforms.dependency;

import java.util.ArrayList;
import java.util.HashMap;

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
public void updateMasterFields(Dependency dependency, HashMap valoresCampos)
  {
    try
    {
      HashMap applicationDAOCondition = new HashMap();

      if (dependency.getDependencyMasterField() != null)
      {
        ArrayList masterFieldNames = new ArrayList(dependency.getDependencyMasterField().getSecondaryFields());

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

          if ((!(dependencyForeignKey.contains(oneDependencyField.getName()))) || ((masterFieldNames.contains(oneDependencyField.getName())) && (oneDependencyField.getIsConstant() != true)) || 
            (oneDependencyField.getName().compareTo(masterFieldMainFieldName) == 0))
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
        HashMap dependencyDomainValues = new HashMap();
        HashMap dependencyValuesHashMap = new HashMap();

        for (int i = 0; i < allDependencyPosibleValues.size(); ++i) {
          dependencyRowValues = (HashMap)allDependencyPosibleValues.get(i);

          String masterFieldKey = "";
          for (int j = 0; j < masterFieldNames.size(); ++j) {
            if (((String)masterFieldNames.get(j)).compareTo(masterFieldMainFieldName) == 0)
              masterFieldKey = masterFieldKey + dependencyRowValues.get(dependency.getDependencyMasterField().getForeignField()) + " ";
            else {
              masterFieldKey = masterFieldKey + dependencyRowValues.get(masterFieldNames.get(j)) + " ";
            }
          }
          masterFieldKey = masterFieldKey.substring(0, masterFieldKey.length() - 1);

          dependencyDomainValues.put(masterFieldKey, dependencyRowValues.get(dependency.getDependencyMasterField().getVisibleFieldName()));
          dependencyValuesHashMap.put(masterFieldKey, dependencyRowValues);
        }

        dependency.getDependencyMasterField().setDependencyValues(dependencyValuesHashMap, dependencyDomainValues);
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