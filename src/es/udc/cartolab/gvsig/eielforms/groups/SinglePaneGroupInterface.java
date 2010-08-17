package es.udc.cartolab.gvsig.eielforms.groups;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.JPanel;

public class SinglePaneGroupInterface extends ComplexFieldGroup
{
  private GridBagConstraints gridbagconst;

  public SinglePaneGroupInterface(String groupName, String layout)
  {
    super(groupName, layout, new JPanel());
    configureLayout();
  }

  public void addGroup(FieldGroup group) {
    this.groups.add(group);
    if (this.layout.compareTo("FLOWLAYOUT") == 0) {
      ((JPanel)this.interfazGrupo).add(group.getInterface());
    }
    else if (this.layout.compareTo("GRIDBAGLAYOUT") == 0) {
      ((JPanel)this.interfazGrupo).add(group.getInterface(), this.gridbagconst);
      refreshLayout();
    }
  }

  private void configureLayout()
  {
    if (this.layout.compareTo("FLOWLAYOUT") == 0) {
      ((JPanel)this.interfazGrupo).setLayout(new FlowLayout());
    }
    else if (this.layout.compareTo("GRIDBAGLAYOUT") == 0) {
      ((JPanel)this.interfazGrupo).setLayout(new GridBagLayout());
      this.gridbagconst = new GridBagConstraints();

      this.gridbagconst.gridy = 0;
    }
  }

  private void refreshLayout()
  {
    if (this.layout.compareTo("FLOWLAYOUT") == 0) {
      return;
    }
    if (this.layout.compareTo("GRIDBAGLAYOUT") == 0)
      this.gridbagconst.gridy += 1;
  }
}