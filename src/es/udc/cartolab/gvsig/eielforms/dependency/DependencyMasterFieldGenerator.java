package es.udc.cartolab.gvsig.eielforms.dependency;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import org.w3c.dom.Node;

import es.udc.cartolab.gvsig.eielforms.domain.Domain;
import es.udc.cartolab.gvsig.eielforms.domain.UserDomain;
import es.udc.cartolab.gvsig.eielforms.field.FieldController;

public class DependencyMasterFieldGenerator
{
//  private GenericApplicationDAO genericApplicationDAO;

  public DependencyMasterFieldGenerator()
  {
//    this.genericApplicationDAO = new GenericApplicationDAO();
  }

  public DependencyMasterField processDependencyMasterField(Node fieldNode, Dependency dependency)
  {
    String name = new String();
    String foreignName = new String();
    String visibleField = new String();
    String label = new String();
    String isKey = new String();
    String editable = new String();
    String required = new String();
    String defaultValue = new String();
    String isOrden;
    boolean bool_isKey = false;
    boolean bool_editable = false;
    boolean bool_required = false;
    boolean bool_constant_value = false;
    boolean bool_isOrden = false;
    ArrayList secondaryFields = new ArrayList();

    Node atributos = fieldNode.getFirstChild();

    while (atributos != null) {
      if (atributos.getNodeName().compareTo("Name") == 0) {
        name = atributos.getFirstChild().getNodeValue();
      }
      else if (atributos.getNodeName().compareTo("ForeignName") == 0) {
        foreignName = atributos.getFirstChild().getNodeValue();
      }
      else if (atributos.getNodeName().compareTo("VisibleField") == 0) {
        visibleField = atributos.getFirstChild().getNodeValue();
      }
      else if (atributos.getNodeName().compareTo("Label") == 0) {
        label = atributos.getFirstChild().getNodeValue();
      }
      else if (atributos.getNodeName().compareTo("IsKey") == 0) {
        isKey = atributos.getFirstChild().getNodeValue();
        bool_isKey = getBoolean(isKey);
      }
      else if (atributos.getNodeName().compareTo("Editable") == 0) {
        editable = atributos.getFirstChild().getNodeValue();
        bool_editable = getBoolean(editable);
      }
      else if (atributos.getNodeName().compareTo("Required") == 0) {
        required = atributos.getFirstChild().getNodeValue();
        bool_required = getBoolean(required);
      } else if (atributos.getNodeName().compareTo("IsOrden") == 0) {
    	  isOrden = atributos.getFirstChild().getNodeValue();
    	  bool_isOrden = getBoolean(isOrden);
      }else {
        if (atributos.getNodeName().compareTo("DefaultValue") == 0)
        {
          if (atributos.getFirstChild() == null) break;
          Node defaultValueTypeNode = atributos.getFirstChild();
          while (true) {
            if (defaultValueTypeNode == null) break;
            if (defaultValueTypeNode.getNodeName().compareTo("SingleValue") == 0) {
              bool_constant_value = false;
              defaultValue = defaultValueTypeNode.getFirstChild().getNodeValue();
            }
            else if (defaultValueTypeNode.getNodeName().compareTo("ConstantValue") == 0) {
              bool_constant_value = true;
              defaultValue = defaultValueTypeNode.getFirstChild().getNodeValue();
            }

            defaultValueTypeNode = defaultValueTypeNode.getNextSibling();
          }
        }

        if (atributos.getNodeName().compareTo("SecondaryFields") == 0) {
          Node secondaryFieldsNode = atributos.getFirstChild();

          String oneSecondaryField = new String();
          while (secondaryFieldsNode != null) {
            if (secondaryFieldsNode.getNodeName().compareTo("SecondaryField") == 0) {
              oneSecondaryField = secondaryFieldsNode.getFirstChild().getNodeValue();
              secondaryFields.add(oneSecondaryField);
            }
            secondaryFieldsNode = secondaryFieldsNode.getNextSibling();
          }

        }

      }

      label538: atributos = atributos.getNextSibling();
    }

//    HashMap applicationDAOCondition = new HashMap();
    ArrayList dependencyForeignKey = dependency.getForeignKey();

    if (foreignName.compareTo("") == 0) {
      foreignName = name;
    }

    for (int i = 0; i < dependencyForeignKey.size(); ++i) {
      String oneForeignKeyField = (String)dependencyForeignKey.get(i);
    }

    ArrayList dependencyFields = dependency.getFields();
    ArrayList dependencyFieldsNames = new ArrayList();

    for (int i = 0; i < dependencyFields.size(); ++i) {
      FieldController oneDependencyField = (FieldController)dependencyFields.get(i);
      dependencyFieldsNames.add(oneDependencyField.getName());

//      if (oneDependencyField.getIsConstant() == true) {
//        applicationDAOCondition.put(oneDependencyField.getName(), this.constantsManager.getConstant(oneDependencyField.getName()));
//      }
    }

    dependencyFieldsNames.add(foreignName);
    dependencyFieldsNames.add(visibleField);
//    try
//    {
//      ArrayList allDependencyPosibleValues = this.genericApplicationDAO.getFieldsCollection(applicationDAOCondition, dependency.getDataBase(), dependency.getTable(), dependencyFieldsNames);

      HashMap dependencyRowValues = new HashMap();
      HashMap dependencyDomainValues = new HashMap();
      HashMap dependencyValuesHashMap = new HashMap();

//      for (int i = 0; i < allDependencyPosibleValues.size(); ++i) {
//        dependencyRowValues = (HashMap)allDependencyPosibleValues.get(i);
//        dependencyDomainValues.put(dependencyRowValues.get(foreignName), dependencyRowValues.get(visibleField));
//        dependencyValuesHashMap.put(dependencyRowValues.get(foreignName), dependencyRowValues);
//      }

      Domain domain = new UserDomain("dependencyMasterDomain", dependencyDomainValues);
      FieldController fieldController = new FieldController(label, name, domain, defaultValue, bool_editable, bool_required, bool_isKey, bool_constant_value, bool_isOrden);
      DependencyMasterField fieldInterface = new DependencyMasterField(fieldController, dependency, dependencyValuesHashMap, visibleField, secondaryFields, foreignName);

      return fieldInterface;
//    }
//    catch (InternalErrorException e) {
//      JOptionPane.showMessageDialog(null, "Ha ocurrido un error al generar el formulario " + name); }
  }

  private boolean getBoolean(String value)
  {
    boolean valorBooleano;
    if (value.toUpperCase().compareTo("TRUE") == 0)
      valorBooleano = true;
    else {
      valorBooleano = false;
    }

    return valorBooleano;
  }
}