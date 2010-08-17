package es.udc.cartolab.gvsig.eielforms.subforms;


import java.util.ArrayList;
import org.w3c.dom.Node;

import es.udc.cartolab.gvsig.eielforms.dependency.DependencyMasterFieldGenerator;
import es.udc.cartolab.gvsig.eielforms.field.FieldGenerator;
import es.udc.cartolab.gvsig.eielforms.field.FieldInterface;

public class SubFormGenerator
{
  private DependencyMasterFieldGenerator dependencyMasterFieldGenerator;
  private ArrayList foreignKey;
  private ArrayList fields;
  private ArrayList primaryKey;
  private FieldGenerator fieldGenerator;
  private Node masterFieldNode;

  public SubFormGenerator()
  {
    this.fieldGenerator = new FieldGenerator();
  }

  public SubForm processSubForm(Node subformNode)
  {
    FieldInterface primaryField = null;

    this.masterFieldNode = null;
    this.foreignKey = new ArrayList();
    this.fields = new ArrayList();
    String layout = "flowlayout";
    String tabla = "";
    String database = "";
    String name = "";

    Node contenidoSubform = subformNode.getFirstChild();
    while (contenidoSubform != null) {
      if (contenidoSubform.getNodeName().compareTo("Database") == 0) {
        database = contenidoSubform.getFirstChild().getNodeValue();
      }
      else if (contenidoSubform.getNodeName().compareTo("Name") == 0) {
        name = contenidoSubform.getFirstChild().getNodeValue();
      }
      else if (contenidoSubform.getNodeName().compareTo("Table") == 0) {
        tabla = contenidoSubform.getFirstChild().getNodeValue();
      }
      else if (contenidoSubform.getNodeName().compareTo("Layout") == 0) {
        layout = contenidoSubform.getFirstChild().getNodeValue();
      }
      else if (contenidoSubform.getNodeName().compareTo("ForeignKey") == 0) {
        processForeignKey(contenidoSubform);
      }
      else if (contenidoSubform.getNodeName().compareTo("PrimaryField") == 0) {
        Node foreignKeyAttributes = contenidoSubform.getFirstChild();
        while (foreignKeyAttributes.getNodeName().compareTo("Field") != 0) {
          foreignKeyAttributes = foreignKeyAttributes.getNextSibling();
        }
        if (foreignKeyAttributes.getNodeName().compareTo("Field") == 0) {
          primaryField = this.fieldGenerator.processField(foreignKeyAttributes);
        }

      }
      else if (contenidoSubform.getNodeName().compareTo("Fields") == 0) {
        processFields(contenidoSubform);
      }

      contenidoSubform = contenidoSubform.getNextSibling();
    }

    SubForm subForm = new SubForm(name, tabla, database, this.foreignKey, primaryField, this.fields);
    for (int i = 0; i < this.fields.size(); ++i) {
      FieldInterface fieldInterface = (FieldInterface)this.fields.get(i);
      subForm.addField(fieldInterface);
    }

    return subForm;
  }

  private void processForeignKey(Node foreignKeyNode)
  {
    Node foreignKeyAttributes = foreignKeyNode.getFirstChild();
    while (foreignKeyAttributes != null) {
      if (foreignKeyAttributes.getNodeName().compareTo("ForeignKeyField") == 0) {
        this.foreignKey.add(foreignKeyAttributes.getFirstChild().getNodeValue());
      }
      foreignKeyAttributes = foreignKeyAttributes.getNextSibling();
    }
  }

  private void processFields(Node fieldsNode)
  {
    Node foreignKeyAttributes = fieldsNode.getFirstChild();
    while (foreignKeyAttributes != null) {
      if (foreignKeyAttributes.getNodeName().compareTo("Field") == 0) {
        this.fields.add(this.fieldGenerator.processField(foreignKeyAttributes));
      }
      foreignKeyAttributes = foreignKeyAttributes.getNextSibling();
    }
  }
}