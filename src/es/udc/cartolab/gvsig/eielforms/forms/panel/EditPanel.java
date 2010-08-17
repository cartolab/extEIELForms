package es.udc.cartolab.gvsig.eielforms.forms.panel;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import javax.swing.JButton;
import javax.swing.JPanel;

import es.udc.cartolab.gvsig.eielforms.forms.FormInterface;

public class EditPanel extends JPanel
{
  private FormInterface formInterfaz;
  private JButton insertButton;
  private JButton cancelButton;

  public EditPanel(FormInterface formInterfaz)
  {
    this.formInterfaz = formInterfaz;

    this.insertButton = new JButton("Confirmar Edicion");
    this.insertButton.addActionListener(new ConfirmEditonAction());

    this.cancelButton = new JButton("Cancelar Edicion");
    this.cancelButton.addActionListener(new CancelEditionAction());

    setLayout(new FlowLayout());
    add(this.insertButton);
    add(this.cancelButton);
    setVisible(true);
  }

  private class CancelEditionAction
    implements ActionListener
  {
    public void actionPerformed(ActionEvent evt)
    {
      try
      {
        EditPanel.this.formInterfaz.cancelEdition();
      }
      catch (Exception e) {
        System.out.println("Ocurrio algún error inesperado...");
        e.printStackTrace();
      }
    }
  }

  private class ConfirmEditonAction
    implements ActionListener
  {
    public void actionPerformed(ActionEvent evt)
    {
      try
      {
        EditPanel.this.formInterfaz.confirmEdition();
      }
      catch (Exception e) {
        System.out.println("Ocurrio algún error inesperado...");
        e.printStackTrace();
      }
    }
  }
}