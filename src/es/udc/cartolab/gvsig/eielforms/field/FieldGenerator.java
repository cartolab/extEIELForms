package es.udc.cartolab.gvsig.eielforms.field;

import org.w3c.dom.Node;

import es.udc.cartolab.gvsig.eielforms.domain.Domain;
import es.udc.cartolab.gvsig.eielforms.domain.generator.DomainGenerator;

public class FieldGenerator
{
  private DomainGenerator domainGenerator;

  public FieldGenerator()
  {
    this.domainGenerator = new DomainGenerator();
  }

  public FieldInterface processField(Node fieldNode)
  {
    String name = new String();
    String label = new String();
    String string_dominio = new String("");
    String isKey = new String();
    String editable = new String();
    String required = new String();
    String defaultValue = new String();
    boolean bool_isKey = false;
    boolean bool_editable = false;
    boolean bool_required = false;
    boolean bool_constant_value = false;

    Node atributos = fieldNode.getFirstChild();

    while (atributos != null)
    {
      if (atributos.getNodeName().compareTo("Name") == 0) {
        name = atributos.getFirstChild().getNodeValue();
      }
      else if (atributos.getNodeName().compareTo("Label") == 0) {
        label = atributos.getFirstChild().getNodeValue();
      }
      else if (atributos.getNodeName().compareTo("Domain") == 0)
      {
        if (atributos.getFirstChild() != null)
          string_dominio = atributos.getFirstChild().getNodeValue();
        else {
          string_dominio = "";
        }

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
      }
      else if ((atributos.getNodeName().compareTo("DefaultValue") == 0) && 
        (atributos.getFirstChild() != null)) {
        Node defaultValueTypeNode = atributos.getFirstChild();

        while (defaultValueTypeNode != null) {
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

      atributos = atributos.getNextSibling();
    }
    Domain domain = this.domainGenerator.getDomain(string_dominio);

    FieldController fieldController = new FieldController(label, name, domain, defaultValue, bool_editable, bool_required, bool_isKey, bool_constant_value);
    FieldInterface fieldInterface;
    if (domain.getType().compareTo("usuario") == 0)
    {
      fieldInterface = new ComboFieldInterface(fieldController);
    }
    else {
      fieldInterface = new TextFieldInterface(fieldController);
    }
    return fieldInterface;
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