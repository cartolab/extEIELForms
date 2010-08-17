package es.udc.cartolab.gvsig.eielforms.field;

//import es.udc.lbd.eiel.formsmodule.model.formManagement.constantsManager.ConstantsManager;
//import es.udc.lbd.eiel.formsmodule.model.formStructure.domain.Domain;
import java.io.PrintStream;

import es.udc.cartolab.gvsig.eielforms.domain.Domain;

public class FieldController
{
  private String label;
  private String name;
  private Domain domain;
  private String defaultValue;
  private boolean editable;
  private boolean required;
  private boolean isKey;
  private boolean constantValue;
  private String value;
  private String memoryValue;
//  private ConstantsManager constantsManager;

  public FieldController(String label, String name, Domain domain, String defaultValue, boolean editable, boolean required, boolean isKey, boolean constantValue)
  {
    this.label = label;
    this.name = name;
    this.domain = domain;
    this.defaultValue = defaultValue;
    this.value = defaultValue;
    this.editable = editable;
    this.required = required;
    this.isKey = isKey;
    this.constantValue = constantValue;
    this.memoryValue = "";
//    this.constantsManager = constantsManager;
  }

  public String getLabel() {
    return this.label;
  }

  public String getName() {
    return this.name;
  }

  public Domain getDomain() {
    return this.domain;
  }

  public void setDomain(Domain domain) {
    this.domain = domain;
  }

  public void setEditable(boolean editable) {
    this.editable = editable;
  }

  public String getDefaultValue()
  {
    return this.defaultValue;
  }

  public boolean getEditable() {
    return this.editable;
  }

  public boolean getRequired() {
    return this.required;
  }

  public boolean getIsKey() {
    return this.isKey;
  }

  public String getValue() {
    return this.value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public void setDefaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
  }

  public String getMemoryValue() {
    return this.memoryValue;
  }

  public void setMemoryValue(String value) {
    this.memoryValue = value;
  }

  public void setConstantValue(boolean value) {
    this.constantValue = value;
  }

  public boolean getIsConstant() {
    return this.constantValue;
  }

  public boolean validate()
  {
    boolean isValid1 = false;
    boolean isValid2 = false;

    if (this.value == null) {
      this.value = "";
    }
    if (this.value.length() == 0) {
      if (this.required == true)
        isValid1 = false;
      else
        isValid1 = true;
    }
    else {
      isValid2 = this.domain.validate(this.value);
    }

    boolean isValid = (isValid1) || (isValid2);

    if (!(isValid)) {
      System.out.println("El campo " + this.label + " con domain " + this.domain.getName() + " de tipo " + this.domain.getType() + ", no es valido!!!");
    }

    return isValid;
  }

  public FieldController clonar()
  {
    FieldController fieldController = new FieldController(this.label, this.name, this.domain, this.defaultValue, this.editable, this.required, this.isKey, this.constantValue);
    fieldController.setValue(getValue());
    fieldController.setMemoryValue(getMemoryValue());
    if (fieldController.getDefaultValue().equals("")) {
      fieldController.setDefaultValue(getValue());
    }
    return fieldController;
  }
}
