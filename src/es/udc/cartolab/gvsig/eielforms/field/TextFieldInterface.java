package es.udc.cartolab.gvsig.eielforms.field;

import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JComponent;
import javax.swing.JTextField;

public class TextFieldInterface extends FieldInterface
{
  private int WIDTH_DEFAULT = 200;
  private JTextField textField;

  public TextFieldInterface(FieldController fieldController)
  {
    super(fieldController);
    this.textField = new JTextField(fieldController.getDefaultValue());
    this.textField.setPreferredSize(new Dimension(this.WIDTH_DEFAULT, 20));

    this.textField.setEnabled(fieldController.getEditable());

    this.textField.addFocusListener(new FocusLostAction(this));
  }

  public JComponent getComponent()
  {
    return this.textField;
  }

  public boolean fillField() {
    this.fieldController.setValue(this.textField.getText());
    return true;
  }

  public void enableField(boolean enabled) {
    if (enabled == true) {
      if (this.fieldController.getEditable() == true)
        this.textField.setEnabled(enabled);
    }
    else
      this.textField.setEnabled(enabled);
  }

  public void loadValue()
  {
    this.textField.setText(this.fieldController.getValue());
    validate();
  }

  public void saveInMemory() {
    this.fieldController.setMemoryValue(this.textField.getText());
  }

  public FieldInterface clonar()
  {
    FieldController field = this.fieldController.clonar();
    field.setEditable(false);
    TextFieldInterface textField = new TextFieldInterface(field);
    return textField;
  }

  private class FocusLostAction
    implements FocusListener
  {
    private TextFieldInterface textFieldInterface;

    public FocusLostAction(TextFieldInterface paramTextFieldInterface)
    {
      this.textFieldInterface = paramTextFieldInterface;
    }

    public void focusLost(FocusEvent evt) {
      this.textFieldInterface.fillField();
      this.textFieldInterface.validate();
    }

    public void focusGained(FocusEvent evt)
    {
    }
  }
}