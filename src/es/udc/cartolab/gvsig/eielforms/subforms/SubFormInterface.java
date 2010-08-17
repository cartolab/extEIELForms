package es.udc.cartolab.gvsig.eielforms.subforms;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.border.TitledBorder;

import es.udc.cartolab.gvsig.eielforms.field.FieldController;
import es.udc.cartolab.gvsig.eielforms.field.FieldInterface;
import es.udc.cartolab.gvsig.eielforms.forms.FormInterface;

public class SubFormInterface extends JDialog
{
  private String layout;
  private SubFormController subformController;
  private ArrayList groups;
  private ArrayList dependencies;
  private SubForm subform;
  private JRootPane mainPane;
  private JPanel panel;
  private JPanel titlePanel;
  private GridBagConstraints gridbagconst;
  private GridBagConstraints gridbagconst2;
  private boolean visible;
//  private PanelBotones panelBotones;
//  private PanelEdicion panelEdicion;
//  private PanelTipos panelTipos;
  private FormInterface formInterface;
  private String fase;
//  private ClaveForaneaVO claveForanea;
  private FieldInterface primaryField;
//  private PanelOtrosCampos panelOtrosCampos;
  private JDialog dialog;
//  private PanelBotonesModificarRestoCampos panelBotonesModificarRestoCampos;
  private String item;
  private Vector datos;
  private boolean asignar = false;
  private boolean seleccionVarios = false;
  private boolean mostrarSiguiente = false;
  private boolean procesado = false;

  public SubFormInterface(FormInterface formInterface, SubForm subform, SubFormController subController, String layout, String title)
  {
    this.subformController = subController;
    this.gridbagconst = new GridBagConstraints();
    this.formInterface = formInterface;
    this.datos = new Vector();
    this.groups = new ArrayList();
    this.subform = subform;
    this.dependencies = new ArrayList();
    this.layout = layout.toUpperCase();
    this.visible = false;
    setTitle(title);
    initSubFormInterface();
    setLocation(500, 100);
    setDefaultCloseOperation(2);
  }

  private void initSubFormInterface() {
    this.formInterface.setEnabled(false);
    this.mainPane = getRootPane();
    this.mainPane.setLayout(new BorderLayout());
    String name = this.subformController.getName();
//    this.titlePanel = new TitlePanel(this.subformController.getName(), 25);

    this.panel = new JPanel();
    this.panel.setVisible(true);

//    this.panelBotonesModificarRestoCampos = new PanelBotonesModificarRestoCampos(this);
//    this.panelBotones = new PanelBotones(this);
//    this.panelEdicion = new PanelEdicion(this);

    this.mainPane.add(this.titlePanel, "North");
    this.mainPane.add(this.panel, "Center");
//    this.mainPane.add(this.panelBotones, "South");

    setSize(500, 650);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        SubFormInterface.this.formInterface.setEnabled(true);
        SubFormInterface.this.showInterface(false);
      }
    });
  }

  public void setSeleccionVarios(boolean seleccionVarios)
  {
    this.seleccionVarios = seleccionVarios;
  }

  public void setProcesado(boolean procesado) {
    this.procesado = procesado;
  }

  public boolean getProcesado() {
    return this.procesado;
  }

  public SubForm getSubForm() {
    return this.subform;
  }

  public void addPanelTipos(FieldInterface field)
  {
    this.primaryField = field;
    this.formInterface.setEnabled(false);
//    this.panelTipos = new PanelTipos(this.subformController, field, this.claveForanea);
//    this.panelTipos.setEnabled(false);
//    this.datos = this.panelTipos.obtenerDatosIniciales(this.subform, this.datos, this.primaryField.getField().getName());
//    this.panel.add(this.panelTipos, "South");
    this.mainPane.add(this.panel, "Center");
  }

  protected void startEdition()
  {
//    this.titlePanel.setVisible(true);
//    this.panelEdicion.setVisible(true);
//    this.panelBotones.setVisible(false);
//    this.mainPane.add(this.panelEdicion, "South");
//    this.panelTipos.habilitarBotones();
  }

  public boolean getAsignar() {
    return this.asignar;
  }

  public void cerrarDialogo() {
    this.dialog.setVisible(false);
    this.dialog.dispose();
    this.asignar = false;
    this.procesado = true;
  }

  public void confirmEdition()
  {
    int retValue = JOptionPane.showConfirmDialog(this, "Está seguro que desea confirmar la edición?");
    if (retValue == 0)
    {
//      this.panelTipos.borrarDatos();
//
//      this.panelTipos.insertarAsignados(this.datos);
//
//      this.panelTipos.deshabilitar();
//      this.panelTipos.reasignarDominiosIniciales();
//      this.panelBotones.setVisible(true);
//      this.panelEdicion.setVisible(false);
      this.mainPane.add(this.titlePanel, "North");
      this.titlePanel.setVisible(true);
    } else {
//      this.panelTipos.reasignarDominiosIniciales();
    }
  }

  public void cancelEdition() {
//    this.panelTipos.reasignarDominiosIniciales();
//
//    this.panelTipos.deshabilitar();
//    this.panelBotones.setVisible(true);
//    this.panelEdicion.setVisible(false);
    this.mainPane.add(this.titlePanel, "North");
    this.titlePanel.setVisible(true);
//    this.panelTipos.setVisible(true);
    this.mainPane.setVisible(true);
    setVisible(true);
  }

//  public void addDatos(OtrosDatosVO otroDato)
//  {
//    boolean encontrado = false;
//
//    for (int i = 0; i < this.datos.size(); ++i) {
//      OtrosDatosVO otrosDatosAux = (OtrosDatosVO)this.datos.get(i);
//      if (otrosDatosAux.getCodigo().equals(otroDato.getCodigo())) {
//        this.datos.remove(i);
//      }
//
//    }
//
//    this.datos.add(otroDato);
//  }

//  public void eliminarDatos(String nombre)
//  {
//    for (int i = 0; i < this.datos.size(); ++i) {
//      OtrosDatosVO otrosDatos = (OtrosDatosVO)this.datos.get(i);
//      if (otrosDatos.getCodigo().equals(nombre))
//        this.datos.remove(i);
//    }
//  }

//  public void insertarDatosParaDisponibles(Vector items)
//  {
//    this.panelOtrosCampos = new PanelOtrosCampos((UserDomain)this.panelTipos.getDomain(), this, this.subform, this.item, this.primaryField, this.claveForanea, true);
//
//    this.dialog = new JDialog();
//    this.dialog.add(this.panelBotonesModificarRestoCampos, "South");
//
//    this.dialog.setTitle("Datos del nivel: " + this.item);
//    JPanel titlePanelDatos = new TitlePanel("Datos del nivel: " + this.item, 10);
//
//    this.dialog.add(titlePanelDatos, "North");
//    this.dialog.add(this.panelOtrosCampos, "Center");
//    this.dialog.setSize(500, 250);
//    this.dialog.setVisible(true);
//    this.dialog.show();
//    this.dialog.addWindowListener(new WindowAdapter() {
//      public void windowClosing(WindowEvent e) {
//        SubFormInterface.this.dialog.setVisible(false);
//        SubFormInterface.this.dialog.dispose();
//      }
//    });
//  }

//  public void insertarDatosParaDisponibles(String item)
//  {
//    if (this.subform.getFields().size() != 0) {
//      this.panelOtrosCampos = new PanelOtrosCampos((UserDomain)this.panelTipos.getDomain(), this, this.subform, item, this.primaryField, this.claveForanea, true);
//
//      this.dialog = new JDialog();
//      this.dialog.add(this.panelBotonesModificarRestoCampos, "South");
//
//      this.dialog.setTitle("Datos del nivel: " + item);
//
//      this.dialog.add(this.titlePanel, "North");
//      this.dialog.add(this.panelOtrosCampos, "Center");
//      this.dialog.setSize(500, 250);
//      this.dialog.setVisible(true);
//      this.dialog.show();
//      this.dialog.addWindowListener(new WindowAdapter() {
//        public void windowClosing(WindowEvent e) {
//          SubFormInterface.this.dialog.setVisible(false);
//          SubFormInterface.this.dialog.dispose();
//        }
//
//      });
//    }
//    else
//    {
//      aceptarModificacion();
//    }
//  }

//  public void mostrarDatos(boolean editable)
//  {
//    this.item = ((String)this.panelTipos.getJListAsignados().getSelectedValue());
//    if ((this.subform.getFields().size() == 0) || 
//      (this.item == null))
//      return;
//    this.panelOtrosCampos = new PanelOtrosCampos((UserDomain)this.panelTipos.getDomain(), this, this.subform, this.item, this.primaryField, this.claveForanea, editable);
//    this.dialog = new JDialog();
//
//    this.dialog.add(this.panelBotonesModificarRestoCampos, "South");
//    JPanel titlePanel2 = new TitlePanel(this.subformController.getName(), 25);
//    this.dialog.setTitle("Datos del nivel: " + this.item);
//    this.dialog.add(titlePanel2, "North");
//    this.dialog.add(this.panelOtrosCampos, "Center");
//    this.dialog.setSize(500, 250);
//    this.dialog.setVisible(true);
//
//    this.dialog.show();
//  }

  public void setItem(String nombre)
  {
    this.item = nombre;
  }

  public String getItem() {
    return this.item;
  }

  public void aceptarModificacion()
  {
//    OtrosDatosVO otrosDatos = new OtrosDatosVO();
//    Vector datosHash = new Vector();

//    otrosDatos.setCodigo(this.item);

    for (int i = 0; i < this.subform.getFields().size(); ++i)
    {
      FieldInterface field = (FieldInterface)this.subform.getFields().get(i);
      field.fillField();
//      FieldController fieldControl = field.getField();

//      CampoVO campo = new CampoVO();
//      campo.setNombre(fieldControl.getName());
//      campo.setValor(fieldControl.getValue());
//      datosHash.add(campo);
    }

//    otrosDatos.setDatos(datosHash);

//    addDatos(otrosDatos);

//    if (this.dialog != null) {
//      this.dialog.setVisible(false);
//      this.dialog.dispose();
//    }
//    this.asignar = true;
//    this.procesado = true;
  }

  public void showInterface(boolean visible)
  {
    this.formInterface.setEnabled(true);
    setVisible(visible);
  }

  public SubFormController getSubFormController()
  {
    return this.subformController;
  }

  public void addClaveForanea()
  {
    JPanel panelClaveForanea = new JPanel();
    panelClaveForanea.setLayout(new GridBagLayout());
    this.gridbagconst2 = new GridBagConstraints();
    this.gridbagconst2.gridx = 0;
    this.gridbagconst.gridy = 0;
    this.dependencies = this.formInterface.getDependencies();
    this.groups = this.formInterface.getGroups();

//    this.claveForanea = new ClaveForaneaVO();
    Vector campos = new Vector();
    panelClaveForanea.setBorder(new TitledBorder(this.formInterface.getTitle()));

    for (int i = 0; i < this.subform.getForeignKey().size(); ++i) {
      String claveForanea = (String)this.subform.getForeignKey().get(i);
//      CampoVO campo = new CampoVO();
      for (int j = 0; j < this.formInterface.getFormController().getFields().size(); ++j) {
        FieldController field = (FieldController)this.formInterface.getFormController().getFields().get(j);
        FieldInterface fieldInterface = (FieldInterface)this.formInterface.getFormController().getFieldsInterface().get(j);

        if (claveForanea.equals(field.getName())) {
//          campo.setNombre(claveForanea);
//          campo.setValor(field.getValue());
//          campos.add(campo);
          FieldInterface clonFieldInterface = fieldInterface.clonar();
          clonFieldInterface.loadValue();

          this.gridbagconst2 = new GridBagConstraints();
          this.gridbagconst2.gridx = 0;

          panelClaveForanea.add(clonFieldInterface.getLabel(), this.gridbagconst2);
          this.gridbagconst2.gridx = 1;

          panelClaveForanea.add(clonFieldInterface.getComponent(), this.gridbagconst2);
          this.gridbagconst2.gridy += 1;
          break;
        }
      }

    }

//    this.claveForanea.setCampos(campos);

    panelClaveForanea.setVisible(true);
    this.panel.add(panelClaveForanea, "North");

    this.panel.setVisible(true);
  }
}