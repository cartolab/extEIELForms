package es.udc.cartolab.gvsig.eielforms.subforms;

import java.util.ArrayList;

import es.udc.cartolab.gvsig.eielforms.forms.FormInterface;

public class SubFormController
{
  private boolean knowKey;
  private String dataBase;
  private String table;
  private String layer;
  private ArrayList key;
  private SubFormInterface subformInterface;
  private String name;

  public SubFormController(FormInterface formInterface, SubForm subform, String layer, String dataBase, String table, String layout, String name, String title)
  {
    this.layer = layer;
    this.dataBase = dataBase;
    this.table = table;
    this.name = name;

    this.knowKey = false;
    this.key = new ArrayList();
    this.subformInterface = new SubFormInterface(formInterface, subform, this, layout, title);
  }

  public String getDataBase() {
    return this.dataBase;
  }

  public String getTable() {
    return this.table;
  }

  public SubFormInterface getInterface() {
    return this.subformInterface;
  }

  public String getLayer() {
    return this.layer;
  }

  public String getName() {
    return this.name;
  }
}