package es.udc.cartolab.gvsig.eielforms.groups;

import java.util.ArrayList;
import javax.swing.JTabbedPane;

public class TabbedPaneGroupInterface extends ComplexFieldGroup
{
  public TabbedPaneGroupInterface(String groupName, String layout)
  {
    super(groupName, layout, new JTabbedPane());
  }

  public void addGroup(FieldGroup group) {
    this.groups.add(group);
    ((JTabbedPane)this.interfazGrupo).addTab(group.getName(), group.getInterface());
  }
}