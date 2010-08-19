package es.udc.cartolab.gvsig.eielforms.field;

import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JComponent;

import es.udc.cartolab.gvsig.eielforms.domain.UserDomain;

public class ComboFieldInterface extends FieldInterface
{
  protected JComboBox comboField;
  protected ArrayList domainKeys;
  private int WIDTH_DEFAULT = 200;
  private UserDomain userDomain;

  public ComboFieldInterface(FieldController fieldController)
  {
    super(fieldController);

    this.userDomain = ((UserDomain)fieldController.getDomain());

    this.domainKeys = this.userDomain.getKeys();
    ArrayList domainValues = this.userDomain.getValues();
    this.comboField = new JComboBox(new Vector(domainValues));
    this.comboField.setPreferredSize(new Dimension(this.WIDTH_DEFAULT, 20));
    this.comboField.setEnabled(fieldController.getEditable());
    this.comboField.setLightWeightPopupEnabled(false);
    this.comboField.addFocusListener(new FocusListener() {

		@Override
		public void focusGained(FocusEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			// TODO Auto-generated method stub
			fillField();
			validate();
		}
    	
    });
  }

  public JComponent getComponent() {
    return this.comboField;
  }

  public boolean fillField() {
    this.fieldController.setValue(getIndexKey(this.comboField.getSelectedIndex()));
    return true;
  }

  public void enableField(boolean enabled) {
    if (enabled == true) {
      if (this.fieldController.getEditable() == true)
        this.comboField.setEnabled(enabled);
    }
    else
      this.comboField.setEnabled(enabled);
  }

  public void loadValue()
  {
    UserDomain userDomain = (UserDomain)this.fieldController.getDomain();

    this.comboField.setSelectedItem(userDomain.resolve(this.fieldController.getValue()));
    validate();
  }

  public void saveInMemory() {
    this.fieldController.setMemoryValue(getIndexKey(this.comboField.getSelectedIndex())); }

  public UserDomain getDomain() {
    return this.userDomain;
  }

  protected String getIndexKey(int index)
  {
    try
    {
      String returnedKey;
      if (index < 0)
        returnedKey = null;
      else {
        returnedKey = (String)this.domainKeys.get(index);
      }
      return returnedKey;
    } catch (Exception e) {
      e.printStackTrace(); }
    return null;
  }

  public FieldInterface clonar() {
    FieldController field = this.fieldController.clonar();
    field.setEditable(false);
    ComboFieldInterface comboField = new ComboFieldInterface(field);
    return comboField;
  }
}