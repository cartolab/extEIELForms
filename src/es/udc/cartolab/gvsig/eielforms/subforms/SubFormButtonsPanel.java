package es.udc.cartolab.gvsig.eielforms.subforms;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import javax.swing.JButton;
import javax.swing.JPanel;

public class SubFormButtonsPanel extends JPanel
{
  private SubFormInterface subformInterface;
  private JButton editButton;
  private JButton deleteButton;
  private JButton exitButton;
  private JButton datosButton;

  public SubFormButtonsPanel(SubFormInterface subformInterface)
  {
    this.subformInterface = subformInterface;

    this.editButton = new JButton("Edición");
    this.editButton.addActionListener(new InitEditionAction());

    this.exitButton = new JButton("Cerrar");
    this.exitButton.addActionListener(new ExitAction());

    this.datosButton = new JButton("Datos");
    this.datosButton.addActionListener(new DatosAction());

    setLayout(new FlowLayout());
    add(this.editButton);
    add(this.exitButton);
    add(this.datosButton);
    setVisible(true);
  }

  private class DatosAction
    implements ActionListener
  {
    public void actionPerformed(ActionEvent evt)
    {
      try
      {
        SubFormButtonsPanel.this.subformInterface.mostrarDatos(false);
      }
      catch (Exception e) {
        System.out.println("Ocurrio algún error inesperado...");
        e.printStackTrace();
      }
    }
  }

  private class ExitAction
    implements ActionListener
  {
    public void actionPerformed(ActionEvent evt)
    {
      try
      {
        SubFormButtonsPanel.this.subformInterface.showInterface(false);
      }
      catch (Exception e) {
        System.out.println("Ocurrio algún error inesperado...");
        e.printStackTrace();
      }
    }
  }

  private class InitEditionAction
    implements ActionListener
  {
    public void actionPerformed(ActionEvent evt)
    {
      try
      {
//        PanelBotones.this.subformInterface.startEdition();
    	  subformInterface.confirmEdition();
      }
      catch (Exception e) {
        System.out.println("Ocurrio algún error inesperado...");
        e.printStackTrace();
      }
    }
  }
}