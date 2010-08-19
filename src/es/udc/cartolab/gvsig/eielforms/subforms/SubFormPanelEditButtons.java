package es.udc.cartolab.gvsig.eielforms.subforms;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import javax.swing.JButton;
import javax.swing.JPanel;

public class SubFormPanelEditButtons extends JPanel
{
  private SubFormInterface subformInterface;
  private JButton aceptarButton;
  private JButton cancelarButton;

  public SubFormPanelEditButtons(SubFormInterface subformInterface)
  {
    this.subformInterface = subformInterface;

    this.aceptarButton = new JButton("Aceptar");
    this.aceptarButton.addActionListener(new AceptarAction());

    this.cancelarButton = new JButton("Cerrar");
    this.cancelarButton.addActionListener(new ExitAction());

    setLayout(new FlowLayout());
    add(this.aceptarButton);
    add(this.cancelarButton);
    setVisible(true);
  }

  private class ExitAction
    implements ActionListener
  {
    public void actionPerformed(ActionEvent evt)
    {
      try
      {
        subformInterface.cerrarDialogo();
      }
      catch (Exception e) {
        System.out.println("Ocurrio algún error inesperado...");
        e.printStackTrace();
      }
    }
  }

  private class AceptarAction
    implements ActionListener
  {
    public void actionPerformed(ActionEvent evt)
    {
      try
      {
        subformInterface.aceptarModificacion();
      }
      catch (Exception e)
      {
        System.out.println("Ocurrio algún error inesperado...");
        e.printStackTrace();
      }
    }
  }
}