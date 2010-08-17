package es.udc.cartolab.gvsig.eielforms.groups;

import java.util.ArrayList;
import javax.swing.JComponent;

public abstract class FieldGroup
{
  protected String name;
  protected String layout;

  public FieldGroup(String groupName, String layout)
  {
    this.name = groupName;
    this.layout = layout.toUpperCase();
  }

  public String getName() {
    return this.name;
  }

  public String getLayout() {
    return this.layout;
  }

  public abstract ArrayList getFields();

  public abstract ArrayList getFieldsInterface();

  public abstract ArrayList getKey();

  public abstract JComponent getInterface();

  public abstract void refresh();

  public abstract void enableFields(boolean paramBoolean);

  public abstract boolean validate();

  public abstract void initFields();

  public abstract void loadData();

  public abstract void saveInMemory();

  public abstract void loadMemory();
}