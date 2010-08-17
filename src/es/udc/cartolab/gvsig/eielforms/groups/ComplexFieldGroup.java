package es.udc.cartolab.gvsig.eielforms.groups;

import java.util.ArrayList;
import javax.swing.JComponent;

public abstract class ComplexFieldGroup extends FieldGroup
{
  protected ArrayList groups;
  protected JComponent interfazGrupo;

  protected ComplexFieldGroup(String groupName, String layout, JComponent groupInterface)
  {
    super(groupName, layout);
    this.groups = new ArrayList();
    this.interfazGrupo = groupInterface;
  }

  public ArrayList getFields()
  {
    ArrayList temporal = new ArrayList();

    for (int i = 0; i < this.groups.size(); ++i) {
      temporal.addAll(((FieldGroup)this.groups.get(i)).getFields());
    }
    return temporal;
  }

  public ArrayList getFieldsInterface()
  {
    ArrayList temporal = new ArrayList();

    for (int i = 0; i < this.groups.size(); ++i) {
      temporal.addAll(((FieldGroup)this.groups.get(i)).getFieldsInterface());
    }
    return temporal;
  }

  public ArrayList getKey()
  {
    ArrayList temporal = new ArrayList();

    for (int i = 0; i < this.groups.size(); ++i) {
      temporal.addAll(((FieldGroup)this.groups.get(i)).getKey());
    }
    return temporal;
  }

  public JComponent getInterface() {
    return this.interfazGrupo;
  }

  public abstract void addGroup(FieldGroup paramFieldGroup);

  public void refresh() {
    this.interfazGrupo.setVisible(true);
  }

  public void enableFields(boolean enabled)
  {
    for (int i = 0; i < this.groups.size(); ++i)
      ((FieldGroup)this.groups.get(i)).enableFields(enabled);
  }

  public boolean validate()
  {
    boolean esValido = true;

    for (int i = 0; i < this.groups.size(); ++i) {
      esValido &= ((FieldGroup)this.groups.get(i)).validate();
    }
    return esValido;
  }

  public void initFields()
  {
    for (int i = 0; i < this.groups.size(); ++i)
      ((FieldGroup)this.groups.get(i)).initFields();
  }

  public void loadData()
  {
    for (int i = 0; i < this.groups.size(); ++i)
      ((FieldGroup)this.groups.get(i)).loadData();
  }

  public void saveInMemory()
  {
    for (int i = 0; i < this.groups.size(); ++i)
      ((FieldGroup)this.groups.get(i)).saveInMemory();
  }

  public void loadMemory()
  {
    for (int i = 0; i < this.groups.size(); ++i)
      ((FieldGroup)this.groups.get(i)).loadMemory();
  }
}