package es.udc.cartolab.gvsig.eielforms.subforms;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JPanel;

import es.udc.cartolab.gvsig.eielforms.domain.UserDomain;
import es.udc.cartolab.gvsig.eielforms.domain.generator.DomainDBReader;
import es.udc.cartolab.gvsig.eielforms.domain.generator.DomainReader;
import es.udc.cartolab.gvsig.eielforms.field.FieldController;
import es.udc.cartolab.gvsig.eielforms.field.FieldInterface;
import es.udc.cartolab.gvsig.eielforms.formgenerator.FormException;
import es.udc.cartolab.gvsig.eielforms.util.ForeignKeyVO;
import es.udc.cartolab.gvsig.eielforms.util.ObtenerDominioDAO;

public class SubFormOtherFieldsPanel extends JPanel
{
  private SubFormInterface subformInterfaz;
  private SubForm subform;
  private String item;
  private GridBagConstraints gridbagconst;
  private FieldInterface primaryField;
  private ForeignKeyVO claveForanea;
  private boolean editable;
  private UserDomain domain;

  public SubFormOtherFieldsPanel(UserDomain domain, SubFormInterface subformInterfaz, SubForm subform, String item, FieldInterface primaryField, ForeignKeyVO claveForanea, boolean editable)
  {
    this.claveForanea = claveForanea;
    this.subformInterfaz = subformInterfaz;
    this.primaryField = primaryField;
    this.subform = subform;
    this.item = item;
    this.editable = editable;
    this.domain = domain;

    initComponents();

    setLayout(new FlowLayout());

    setVisible(true); }

  public void initComponents() {
    String database = this.subformInterfaz.getSubFormController().getDataBase();
    String tabla = this.subformInterfaz.getSubFormController().getTable();
    ObtenerDominioDAO odDAO;
	try {
		odDAO = new ObtenerDominioDAO();

    for (int i = 0; i < this.subform.getFields().size(); ++i) {
      FieldInterface field = (FieldInterface)this.subform.getFields().get(i);
      FieldController fieldController = field.getField();
      String nombreCampoPrimario = this.primaryField.getField().getName();

      Object valor = odDAO.obtenerValorCampo(this.domain, this.item, fieldController.getName(), nombreCampoPrimario, database, tabla, this.claveForanea);
      if (valor == null) valor = "";
      fieldController.setValue(valor.toString());

      field.loadValue();
      field.getComponent().setEnabled(this.editable);

      this.gridbagconst = new GridBagConstraints();
      this.gridbagconst.gridx = 0;

      add(field.getLabel(), this.gridbagconst);
      this.gridbagconst.gridx = 1;

      add(field.getComponent(), this.gridbagconst);
    }
	} catch (FormException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
}