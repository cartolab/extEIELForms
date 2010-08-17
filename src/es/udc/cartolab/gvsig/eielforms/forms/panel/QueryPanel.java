package es.udc.cartolab.gvsig.eielforms.forms.panel;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import javax.swing.JButton;
import javax.swing.JPanel;

import es.udc.cartolab.gvsig.eielforms.forms.FormInterface;

public class QueryPanel extends JPanel
{
  private FormInterface formInterface;
  private JButton editButton;
  private JButton deleteButton;
  private JButton exitButton;

  public QueryPanel(FormInterface formInterface)
  {
    this.formInterface = formInterface;

    this.editButton = new JButton("Editar Entidad");
    this.editButton.addActionListener(new InitEditionAction());

    this.deleteButton = new JButton("Eliminar Entidad");
    this.deleteButton.addActionListener(new InitDeletionAction());

    this.exitButton = new JButton("Salir");
    this.exitButton.addActionListener(new ExitAction());

    setLayout(new FlowLayout());
    add(this.editButton);

    add(this.exitButton);
    setVisible(true);
  }

  private class ExitAction
    implements ActionListener
  {
    public void actionPerformed(ActionEvent evt)
    {
      try
      {
        QueryPanel.this.formInterface.showInterface(false);
      }
      catch (Exception e) {
        System.out.println("Ocurrio algún error inesperado...");
        e.printStackTrace();
      }
    }
  }

  private class InitDeletionAction
    implements ActionListener
  {
    public void actionPerformed(ActionEvent evt)
    {
      try
      {
        QueryPanel.this.formInterface.startDeletion();
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
        QueryPanel.this.formInterface.startEdition();
      }
      catch (Exception e) {
        System.out.println("Ocurrio algún error inesperado...");
        e.printStackTrace();
      }
    }
  }
}