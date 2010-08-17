package es.udc.cartolab.gvsig.eielforms.groups;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class BorderPaneGroupInterface extends SinglePaneGroupInterface
{
  public BorderPaneGroupInterface(String groupName, String layout)
  {
    super(groupName, layout);
    ((JPanel)this.interfazGrupo).setBorder(new TitledBorder(groupName));
  }
}