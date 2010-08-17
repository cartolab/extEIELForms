package es.udc.cartolab.gvsig.eielforms.forms.panel;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import javax.swing.JButton;
import javax.swing.JPanel;

import es.udc.cartolab.gvsig.eielforms.forms.FormInterface;

public class InsertPanel extends JPanel
{
  private FormInterface formInterfaz;
  private JButton aceptButton;
  private JButton cancelButton;

  public InsertPanel(FormInterface formInterfaz)
  {
    this.formInterfaz = formInterfaz;

    this.aceptButton = new JButton("Confirmar Insercion");
    this.aceptButton.addActionListener(new ConfirmInsertionAction());
    this.cancelButton = new JButton("Cancelar Insercion");
    this.cancelButton.addActionListener(new CancelInsertionAction());

    setLayout(new FlowLayout());
    add(this.aceptButton);
    add(this.cancelButton);
    setVisible(true);
  }

  private class CancelInsertionAction
    implements ActionListener
  {
    public void actionPerformed(ActionEvent evt)
    {
      try
      {
        InsertPanel.this.formInterfaz.cancelInsertion();
      } catch (Exception e) {
        System.out.println("Ocurrio algún error inesperado...");
        e.printStackTrace();
      }
    }
  }

  private class ConfirmInsertionAction
    implements ActionListener
  {
    public void actionPerformed(ActionEvent evt)
    {
      try
      {
        InsertPanel.this.formInterfaz.confirmInsertion();
      }
      catch (Exception e) {
        System.out.println("Ocurrio algún error inesperado...");
        e.printStackTrace();
      }
    }
  }
}