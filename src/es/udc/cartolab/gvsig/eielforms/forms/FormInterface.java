package es.udc.cartolab.gvsig.eielforms.forms;

//import es.udc.lbd.eiel.formsmodule.model.formStructure.dependency.Dependency;
//import es.udc.lbd.eiel.formsmodule.model.formStructure.fieldGroup.FieldGroup;
//import es.udc.lbd.eiel.formsmodule.model.formStructure.subforms.SubForm;
//import es.udc.lbd.eiel.formsmodule.model.formStructure.subforms.SubFormController;
//import es.udc.lbd.eiel.formsmodule.model.formStructure.subforms.SubFormInterface;
//import es.udc.lbd.eiel.util.gui.TitlePanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;

import es.udc.cartolab.gvsig.eielforms.dependency.Dependency;
import es.udc.cartolab.gvsig.eielforms.field.FieldController;
import es.udc.cartolab.gvsig.eielforms.field.FieldInterface;
import es.udc.cartolab.gvsig.eielforms.forms.panel.EditPanel;
import es.udc.cartolab.gvsig.eielforms.forms.panel.InsertPanel;
import es.udc.cartolab.gvsig.eielforms.forms.panel.QueryPanel;
import es.udc.cartolab.gvsig.eielforms.forms.panel.SubFormButtonPanel;
import es.udc.cartolab.gvsig.eielforms.groups.FieldGroup;
import es.udc.cartolab.gvsig.eielforms.subforms.SubForm;
import es.udc.cartolab.gvsig.eielforms.subforms.SubFormController;
import es.udc.cartolab.gvsig.eielforms.subforms.SubFormInterface;

//public class FormInterface extends JDialog
public class FormInterface extends JPanel
{
  private String layout;
  private String title;
  private FormController formController;
  private ArrayList groups;
  private ArrayList dependencies;
  private ArrayList subforms;
  private JPanel panel;
//  private JPanel titlePanel;
  private GridBagConstraints gridbagconst;
  private boolean visible;
  private QueryPanel queryPanel;
  private SubFormButtonPanel subFormPanel;
  private EditPanel editPanel;
  private InsertPanel insertPanel;
  private boolean hasSubform = false;
  private ArrayList subformsControllers;
//  private SelectEntityPanel selectEntityPanel;

  protected FormInterface(FormController formController, String layout, String title)
  {
	  super();
    this.formController = formController;
    this.groups = new ArrayList();
    this.subforms = new ArrayList();
    this.subformsControllers = new ArrayList();
    this.dependencies = new ArrayList();
    this.layout = layout.toUpperCase();
    this.visible = false;
    setTitle(title);
    initFormInterface();
    configureLayout();

    setLocation(500, 100);
//    setDefaultCloseOperation(1);
  }

  private void initFormInterface() {
    setLayout(new BorderLayout());

//    this.titlePanel = new TitlePanel(this.formController.getName(), 25);

    this.panel = new JPanel();
    this.panel.setVisible(true);

    this.queryPanel = new QueryPanel(this);
    this.editPanel = new EditPanel(this);
//
//    this.subFormPanel = new SubFormButtonPanel(this);

//    this.subFormPanel.setVisible(true);
//
    this.insertPanel = new InsertPanel(this);

//    this.mainPane.add(this.titlePanel, "North");
    add(this.panel, "Center");
  }

//  protected void addEntitiesPanel(SelectEntityPanel selectEntityPanel)
//  {
//    this.selectEntityPanel = selectEntityPanel;
//    this.mainPane.add(selectEntityPanel, "West");
//  }

  protected void addGroup(FieldGroup group) {
    this.groups.add(group);
    this.panel.add(group.getInterface(), this.gridbagconst);
    updateLayout();
  }

  protected void addSubFormulario(SubForm subform) {
    this.subforms.add(subform);
  }

  protected void addDependency(Dependency dependency)
  {
    this.dependencies.add(dependency);
    this.panel.add(dependency.getInterface(), this.gridbagconst);
    updateLayout();
  }

  public ArrayList getGroups()
  {
    return this.groups;
  }

  public ArrayList getDependencies() {
    return this.dependencies; }

  public ArrayList getSubForms() {
    return this.subforms;
  }

  public ArrayList getSubformsControllers() {
    return this.subformsControllers;
  }

  public FormController getFormController() {
    return this.formController;
  }

  public void showInterface(boolean visible)
  {
    this.visible = visible;
    update();
  }

  private void configureLayout() {
    if (this.layout.compareTo("FLOWLAYOUT") == 0) {
      this.panel.setLayout(new FlowLayout());
    }
    else if (this.layout.compareTo("GRIDBAGLAYOUT") == 0) {
      this.panel.setLayout(new GridBagLayout());
      this.gridbagconst = new GridBagConstraints();

      this.gridbagconst.gridy = 1;
      this.gridbagconst.weightx = 0.5D;
      this.gridbagconst.weighty = 0.5D;
    }
  }

  private void updateLayout()
  {
    if (this.layout.compareTo("FLOWLAYOUT") != 0)
    {
      if (this.layout.compareTo("GRIDBAGLAYOUT") == 0) {
        this.gridbagconst.gridy += 1;
      }
    }

//    pack();
  }

  protected void update()
  {
    for (int i = 0; i < this.groups.size(); ++i) {
      ((FieldGroup)this.groups.get(i)).refresh();
    }
    setVisible(this.visible);
    if (!(this.visible)) {
      hide();
    }
//    pack();
  }

  protected void performQuery() {
//    enableFields(false);
    this.editPanel.setVisible(false);
    this.insertPanel.setVisible(false);
    this.queryPanel.setVisible(true);
    if (this.hasSubform)
      this.subFormPanel.setVisible(true);
//    else this.subFormPanel.setVisible(false);
    this.panel.setVisible(true);
//    this.mainPane.add(this.queryPanel, "South");
//    pack();
  }

  public void startEdition()
  {
    saveFieldsInMemory();
    enableFields(true);
    this.insertPanel.setVisible(false);
    this.queryPanel.setVisible(false);
    this.editPanel.setVisible(true);
    this.subFormPanel.setVisible(false);
    this.panel.setVisible(true);
    add(this.editPanel, "South");
  }

  public void startDeletion()
  {
    int confirmacionBorrado = JOptionPane.showConfirmDialog(null, "�Confirma que desea eliminar la entidad?");
    if (confirmacionBorrado == 0)
      confirmDeletion();
  }

  public void confirmEdition()
  {
    this.formController.notifyObservers();
    performQuery();
  }

  public void openSubForm(String nombre)
  {
    for (int i = 0; i < this.subforms.size(); ++i) {
      SubForm subformulario = (SubForm)this.subforms.get(i);
      if (!(subformulario.getName().equals(nombre)))
        continue;
      String table = subformulario.getTable();
      String database = subformulario.getDataBase();
      String name = subformulario.getName();

      SubFormController subformController = new SubFormController(this, subformulario, name, database, table, "gridbaglayout", name, name);
      if (!(this.subformsControllers.contains(subformController))) {
        this.subformsControllers.add(subformController);
      }

      SubFormInterface interfaz = subformController.getInterface();
      interfaz.addClaveForanea();
      interfaz.addPanelTipos(subformulario.getPrimaryField());
      interfaz.setResizable(false);
      interfaz.setVisible(true);
    }
  }

  public void cancelEdition()
  {
    loadMemory();
    performQuery();
  }

  protected void confirmDeletion()
  {
    this.formController.notifyDeletionToObservers();
  }

  protected void startInsertion() {
    enableFields(true);
    this.queryPanel.setVisible(false);
    this.editPanel.setVisible(false);
    this.insertPanel.setVisible(true);
    this.subFormPanel.setVisible(false);
    add(this.insertPanel, "South");
//    pack();
  }

  protected void addSubForm(ArrayList subforms)
  {
    for (int i = 0; i < subforms.size(); ++i) {
      SubForm subform = (SubForm)subforms.get(i);
      this.subFormPanel.addButton(subform.getName());
    }

    JPanel miSubFormPanel = new JPanel(new BorderLayout());

    miSubFormPanel.setSize(400, 400);
    miSubFormPanel.add(this.subFormPanel, "East");

    this.hasSubform = true;
    this.panel.add(miSubFormPanel, this.gridbagconst);
  }

  public void confirmInsertion()
  {
//    setModal(false);
    this.formController.notifyObservers();
  }

  public void cancelInsertion()
  {
    initFields();
    showInterface(false);
  }

  protected void loadData()
  {
    for (int i = 0; i < this.dependencies.size(); ++i) {
      ((Dependency)this.dependencies.get(i)).loadData();
    }

    for (int i = 0; i < this.groups.size(); ++i)
      ((FieldGroup)this.groups.get(i)).loadData();
  }

  private void enableFields(boolean enabled)
  {
    for (int i = 0; i < this.dependencies.size(); ++i) {
      ((Dependency)this.dependencies.get(i)).enableFields(enabled);
    }

    for (int i = 0; i < this.groups.size(); ++i)
      ((FieldGroup)this.groups.get(i)).enableFields(enabled);
  }

  protected void initFields()
  {
    for (int i = 0; i < this.dependencies.size(); ++i) {
      ((Dependency)this.dependencies.get(i)).initFields();
    }

    for (int i = 0; i < this.groups.size(); ++i)
      ((FieldGroup)this.groups.get(i)).initFields();
  }

  private void saveFieldsInMemory()
  {
    for (int i = 0; i < this.dependencies.size(); ++i) {
      ((Dependency)this.dependencies.get(i)).saveInMemory();
    }

    for (int i = 0; i < this.groups.size(); ++i)
      ((FieldGroup)this.groups.get(i)).saveInMemory();
  }

  private void loadMemory()
  {
    for (int i = 0; i < this.dependencies.size(); ++i) {
      ((Dependency)this.dependencies.get(i)).loadMemory();
    }

    for (int i = 0; i < this.groups.size(); ++i)
      ((FieldGroup)this.groups.get(i)).loadMemory();
  }

//  protected void fillEntitiesPanel(Collection entityIds, Integer defaultEntity)
//  {
//    this.selectEntityPanel.putEntities(entityIds, defaultEntity);
//  }

//  protected void showEntitiesPanel(boolean visible) {
//    this.selectEntityPanel.setVisible(visible);
//  }
  
  public void fillValues() {
	  for (int i=0; i<groups.size(); i++) {
		  
		  FieldGroup group = (FieldGroup) groups.get(i);
		  ArrayList fields = group.getFieldsInterface();
//		  ArrayList fields = group.getFields();
		  for (int j=0; j<fields.size(); j++) {
			  ((FieldInterface) fields.get(j)).setValue("aa");
		  }
	  }
  }
  
  private void setTitle(String title) {
	  this.title = title;
  }
  
  public String getTitle() {
	  return title;
  }
}
