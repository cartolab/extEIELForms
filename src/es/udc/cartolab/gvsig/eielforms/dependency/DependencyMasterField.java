package es.udc.cartolab.gvsig.eielforms.dependency;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javax.swing.JComboBox;

import es.udc.cartolab.gvsig.eielforms.domain.UserDomain;
import es.udc.cartolab.gvsig.eielforms.field.ComboFieldInterface;
import es.udc.cartolab.gvsig.eielforms.field.FieldController;

public class DependencyMasterField extends ComboFieldInterface
{
  private HashMap dependencyValues;
  private Dependency dependency;
  private String visibleFieldName;
  private String foreignField;
  private Collection secondaryFields;

  public DependencyMasterField(FieldController fieldController, Dependency dependency, HashMap dependencyValues, String visibleFieldName, Collection secondaryFields, String foreignField)
  {
    super(fieldController);
    this.dependencyValues = dependencyValues;
    this.comboField.addActionListener(new ComboFieldChangeAction());
    this.dependency = dependency;
    this.visibleFieldName = visibleFieldName;

    if (foreignField.compareTo("") == 0)
      this.foreignField = fieldController.getName();
    else {
      this.foreignField = foreignField;
    }
    this.secondaryFields = secondaryFields;
  }

  public void loadValue() {
    UserDomain userDomain = (UserDomain)this.fieldController.getDomain();
    this.comboField.setSelectedItem(userDomain.resolve(this.fieldController.getValue()));
    validate();
  }

  public void loadDefaultValue()
  {
    String defaultValueToLoad = new String();

    //TODO constants...
//    Iterator itemIterator = this.secondaryFields.iterator();
//    while (itemIterator.hasNext()) {
//      defaultValueToLoad = defaultValueToLoad + this.constantManager.getConstant((String)itemIterator.next()) + " ";
//    }
    defaultValueToLoad = defaultValueToLoad + this.fieldController.getDefaultValue();

    UserDomain userDomain = (UserDomain)this.fieldController.getDomain();
    this.comboField.setSelectedItem(userDomain.resolve(defaultValueToLoad));
    validate();
  }

  public String getVisibleFieldName()
  {
    return this.visibleFieldName;
  }

  public String getForeignField() {
    return this.foreignField; }

  public HashMap getDependencyValues() {
    return this.dependencyValues;
  }

  public Collection getSecondaryFields() {
    return this.secondaryFields;
  }

  public void setDependencyValues(HashMap dependencyValues, HashMap dependencyDomain) {
    UserDomain newDomain = new UserDomain("dependencyMasterDomain", dependencyDomain);
    this.fieldController.setDomain(newDomain);

    this.domainKeys = newDomain.getKeys();
    ArrayList domainValues = newDomain.getValues();

    this.dependencyValues = dependencyValues;
    this.comboField.removeAllItems();

    Iterator itemIterator = dependencyValues.keySet().iterator();

    while (itemIterator.hasNext())
      this.comboField.addItem(dependencyDomain.get(itemIterator.next()));
  }

  private class ComboFieldChangeAction
    implements ActionListener
  {
    public void actionPerformed(ActionEvent evt)
    {
      if ((DependencyMasterField.this.comboField.getItemCount() <= 0) || 
        (DependencyMasterField.this.comboField.getSelectedIndex() < 0)) return;
      String selectedValue = DependencyMasterField.this.getIndexKey(DependencyMasterField.this.comboField.getSelectedIndex());
      HashMap values = (HashMap)DependencyMasterField.this.dependencyValues.get(selectedValue);
      try {
        DependencyMasterField.this.dependency.updateSlaveFields(values);
      } catch (Exception e) {
        System.out.println("Ocurrio algún error inesperado en el combo selector de la dependencia...");
        e.printStackTrace();
      }
    }
  }
}