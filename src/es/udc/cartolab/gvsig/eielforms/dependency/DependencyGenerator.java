package es.udc.cartolab.gvsig.eielforms.dependency;

import es.udc.cartolab.gvsig.eielforms.field.FieldGenerator;
import es.udc.cartolab.gvsig.eielforms.field.FieldInterface;

import java.util.ArrayList;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class DependencyGenerator
{
//  private DependencyMasterFieldGenerator dependencyMasterFieldGenerator;
  private ArrayList foreignKey;
  private ArrayList fields;
  private FieldGenerator fieldGenerator;
  private Node masterFieldNode;
private DependencyMasterFieldGenerator dependencyMasterFieldGenerator;

  public DependencyGenerator()
  {
    this.dependencyMasterFieldGenerator = new DependencyMasterFieldGenerator();
    this.fieldGenerator = new FieldGenerator();
  }

  public Dependency processDependency(Node dependencyNode)
  {
    this.masterFieldNode = null;
    this.foreignKey = new ArrayList();
    this.fields = new ArrayList();
    String layout = "flowlayout";

    String tabla = dependencyNode.getAttributes().getNamedItem("Table").getNodeValue();
    String database = dependencyNode.getAttributes().getNamedItem("DataBase").getNodeValue();
    String dependencyName = dependencyNode.getAttributes().getNamedItem("DependencyName").getNodeValue();

    Node contenidoDependencia = dependencyNode.getFirstChild();
    while (contenidoDependencia != null)
    {
      if (contenidoDependencia.getNodeName().compareTo("Layout") == 0) {
        layout = contenidoDependencia.getFirstChild().getNodeValue();
      }
      else if (contenidoDependencia.getNodeName().compareTo("ForeignKey") == 0) {
        processForeignKey(contenidoDependencia);
      }
      else if (contenidoDependencia.getNodeName().compareTo("Fields") == 0) {
        processFields(contenidoDependencia);
      }

      contenidoDependencia = contenidoDependencia.getNextSibling();
    }

    Dependency dependency = new Dependency(dependencyName, layout, tabla, database, this.foreignKey);
    for (int i = 0; i < this.fields.size(); ++i) {
      FieldInterface fieldInterface = (FieldInterface)this.fields.get(i);
      dependency.addField(fieldInterface);
    }

    if (this.masterFieldNode != null) {
      DependencyMasterField masterFieldInterface = this.dependencyMasterFieldGenerator.processDependencyMasterField(this.masterFieldNode, dependency);
      dependency.addMasterField(masterFieldInterface);
    }

    return dependency;
  }

  private void processForeignKey(Node foreignKeyNode)
  {
    Node foreignKeyAttributes = foreignKeyNode.getFirstChild();
    while (foreignKeyAttributes != null) {
      if (foreignKeyAttributes.getNodeName().compareTo("ForeignKeyField") == 0) {
        this.foreignKey.add(foreignKeyAttributes.getFirstChild().getNodeValue());
      }
      else if (foreignKeyAttributes.getNodeName().compareTo("ForeignKeyMasterField") == 0)
      {
        this.masterFieldNode = foreignKeyAttributes;
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