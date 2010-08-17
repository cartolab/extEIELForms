package es.udc.cartolab.gvsig.eielforms.forms.panel;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JPanel;

import es.udc.cartolab.gvsig.eielforms.forms.FormInterface;

public class SubFormButtonPanel extends JPanel
{
  private FormInterface formInterfaz;
  private JButton subformButton;
  private String nombre;
  private Vector subformsButtons = new Vector();

  public SubFormButtonPanel(FormInterface formInterfaz) {
    this.formInterfaz = formInterfaz;

    setLayout(new FlowLayout());
    setVisible(true);
  }

  public void addButton(String nombreSubform)
  {
    this.nombre = nombreSubform;
    JButton button = new JButton(nombreSubform);
    button.addMouseListener(new MiMouseAdapter(nombreSubform) {
      public void mouseClicked(MouseEvent evt) {
        SubFormButtonPanel.this.jButtonAbrirSubformularioMouseClicked(evt, getNombre());
      }
    });
    setLayout(new FlowLayout());
    add(button, 0);
    this.subformsButtons.add(button);
    setVisible(true);
  }

  private void jButtonAbrirSubformularioMouseClicked(MouseEvent evt, String nombre)
  {
    this.formInterfaz.openSubForm(nombre);
  }

  private class MiMouseAdapter extends MouseAdapter
  {
    private String nombre;

    public MiMouseAdapter(String paramString)
    {
      this.nombre = paramString; }

    public String getNombre() {
      return this.nombre;
    }
  }
}