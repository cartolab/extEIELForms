/*
 * Copyright (c) 2010. Cartolab (Universidade da Coruña)
 *
 * This file is part of extEIELForms
 *
 * extEIELForms is based on the forms application of GisEIEL <http://giseiel.forge.osor.eu/>
 * devoloped by Laboratorio de Bases de Datos (Universidade da Coruña)
 *
 * extEIELForms is free software: you can redistribute it and/or modify it under the terms
 * of the GNU General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or any later version.
 *
 * extEIELForms is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with extEIELForms.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package es.udc.cartolab.gvsig.eielforms.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.iver.andami.PluginServices;
import com.iver.andami.ui.mdiFrame.MDIFrame;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.andami.ui.mdiManager.WindowInfo;

import es.udc.cartolab.gvsig.eielforms.dependency.Dependency;
import es.udc.cartolab.gvsig.eielforms.dependency.DependencyMasterField;
import es.udc.cartolab.gvsig.eielforms.field.FieldController;
import es.udc.cartolab.gvsig.eielforms.field.FieldInterface;
import es.udc.cartolab.gvsig.eielforms.formgenerator.FormException;
import es.udc.cartolab.gvsig.eielforms.formgenerator.FormGenerator;
import es.udc.cartolab.gvsig.eielforms.forms.FormController;
import es.udc.cartolab.gvsig.eielforms.util.FormsDAO;
import es.udc.cartolab.gvsig.eielutils.constants.Constants;
import es.udc.cartolab.gvsig.eielutils.misc.EIELValues;
import es.udc.cartolab.gvsig.users.utils.DBSession;

public class AlphanumericForm extends JPanel implements IWindow, ActionListener {

	protected String formName;
	private WindowInfo viewInfo;
	private JPanel southPanel;
	private JScrollPane centerPanel;
	protected FormController form;
	protected HashMap<String, String> key = new HashMap<String, String>();
	private JButton editButton, newButton, closeButton;
	private JPanel upperPanel;

	public AlphanumericForm(String formName) {
		this.formName = formName;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setFocusCycleRoot(true);
	}

	public WindowInfo getWindowInfo() {
		if (viewInfo == null) {
			viewInfo = new WindowInfo(WindowInfo.MODELESSDIALOG
					| WindowInfo.RESIZABLE | WindowInfo.PALETTE);
			viewInfo.setTitle("Alphanumeric form");
			Dimension dim = getPreferredSize();
			MDIFrame a = (MDIFrame) PluginServices.getMainFrame();
			int maxHeight = a.getHeight() - 175;
			int maxWidth = a.getWidth() - 15;

			int width, heigth = 0;
			if (dim.getHeight() > maxHeight) {
				heigth = maxHeight;
			} else {
				heigth = new Double(dim.getHeight()).intValue();
			}
			if (dim.getWidth() > maxWidth) {
				width = maxWidth;
			} else {
				width = new Double(dim.getWidth()).intValue();
			}
			viewInfo.setWidth(width + 25);
			viewInfo.setHeight(heigth + 40);
		}
		return viewInfo;
	}

	/**
	 * Adds a listener to the Primary Key field
	 * 
	 * @throws FormException
	 */
	protected void addPKChangeListener() throws FormException {
		ArrayList keyFields = form.getKey();
		if (keyFields.size() != 1) {
			throw new FormException("Clave mal formada");
		}
		final String keyName = ((FieldController) keyFields.get(0)).getName();
		ArrayList fields = ((Dependency) form.getDependencies().get(0))
				.getFieldsInterface();
		// ArrayList fields = form.getFieldsInterface();
		boolean found = false;
		for (int i = 0; i < fields.size(); i++) {
			FieldInterface fi = (FieldInterface) fields.get(i);
			String fieldName = fi.getField().getName();
			if (fieldName.equals(keyName)) {
				if (fi.getComponent() instanceof JTextField) {
					final JTextField tf = (JTextField) fi.getComponent();
					tf.getDocument().addDocumentListener(
							new DocumentListener() {

								public void changedUpdate(DocumentEvent arg0) {
									change();
								}

								public void insertUpdate(DocumentEvent arg0) {
									change();
								}

								public void removeUpdate(DocumentEvent arg0) {
									change();
								}
								
								private void change() {
									key.put(keyName, tf.getText());
									form.getInterface().enablePollButton(true);
									form.getInterface().enableNucleosRelationButton(true);
									if (editButton != null)
										editButton.setEnabled(true);
								}

							});
					found = true;
					break;
				} else {
					throw new FormException(
							"El campo de la clave no es un textfield!");
				}

			}
		}
		if (!found) {
			throw new FormException("No se encontró el campo de la PK!");
		}
	}

	private JScrollPane getCenterPanel() throws FormException {
		if (centerPanel == null) {

			if (form == null) {
				FormGenerator fg = new FormGenerator();
				form = fg.createFormController(formName);
				form.setAlpha(true);
				addPKChangeListener();
			}
			centerPanel = new JScrollPane(form.getInterface());
			centerPanel.setBorder(new EmptyBorder(0, 0, 0, 0));

		}
		return centerPanel;
	}

	protected JPanel getButtonsPanel() {
		if (southPanel == null) {
			southPanel = new JPanel();
			editButton = new JButton("Editar");
			editButton.setEnabled(false);
			editButton.addActionListener(this);
			southPanel.add(editButton);
			newButton = new JButton("Nuevo registro");
			newButton.addActionListener(this);
			southPanel.add(newButton);
			closeButton = new JButton("Cerrar");
			closeButton.addActionListener(this);
			southPanel.add(closeButton);
		}
		return southPanel;
	}

	protected JPanel getUpperPanel() {

		ImageIcon headerImg = EIELValues.getInstance().getHeader();
		Color bgColor = EIELValues.getInstance().getHeaderColor();

		if (upperPanel == null) {
			upperPanel = new JPanel();
			if (headerImg != null && bgColor != null) {
				upperPanel.setBackground(bgColor);
				JLabel icon = new JLabel();
				icon.setIcon(headerImg);
				upperPanel.add(icon);
			}
		}
		return upperPanel;
	}

	public Object getWindowProfile() {
		// TODO Auto-generated method stub
		return WindowInfo.PROPERTIES_PROFILE;
	}

	/**
	 * It only works if the window has been initialized
	 */
	public void setHeight(int height) {
		if (viewInfo != null) {
			viewInfo.setHeight(height);
		}
	}

	/**
	 * It only works if the window has been initialized
	 */
	public void setWidth(int width) {
		if (viewInfo != null) {
			viewInfo.setWidth(width);
		}
	}

	public void open() {

		try {
			add(getUpperPanel());
			add(getCenterPanel());
			add(getButtonsPanel());
			form.fillFieldsDefault();

			PluginServices.getMDIManager().addCentredWindow(this);

			viewInfo.setTitle(form.getName());

			FieldInterface field = getFocusField();
			if (field != null) {
				field.getComponent().requestFocusInWindow();
			}

		} catch (FormException e) {
			System.out.println("Error en el formulario: " + e.getMessage());
		}
	}
	
	public void fillDefault() {
	    form.fillFieldsDefault();
	}

	private FieldInterface getFocusField() {
		ArrayList fields = form.getFieldsInterface();
		boolean found = false;
		FieldInterface focusField = null;
		for (int i = 0; i < fields.size(); i++) {
			FieldInterface field = (FieldInterface) fields.get(i);
			if (field.getField().getEditable()) {
				if (!found || field instanceof DependencyMasterField) {
					focusField = field;
					found = true;
				}
			}
		}
		return focusField;
	}

	/**
	 * Sets the last key of the table, only used after closing edition panel.
	 */
	protected boolean setKey() throws FormException {
		ArrayList keyFields = form.getKey();
		key = new HashMap();

		if (keyFields.size() != 1) {
			throw new FormException(
					"Clave primaria mal formada en el formulario");
		}
		FieldController fc = (FieldController) keyFields.get(0);
		String fieldName = fc.getName();
		String fieldValue = fc.getValue();
		if (fieldValue.equals(""))
			fieldValue = null;
		boolean edit = fieldValue != null;
		enableEdit(edit);
		if (edit) {
			key.put(fieldName, fieldValue);
		}

		return edit;

	}

	private String getWhereClause() throws SQLException {
		DBSession dbs = DBSession.getCurrentSession();
		String where = " WHERE ";
		Constants c = Constants.getCurrentConstants();
		if (dbs != null && c.constantsSelected()) {
			List<String> cols = Arrays.asList(dbs.getColumns(
					form.getDataBase(), form.getTable()));

			ArrayList<String> upperCols = new ArrayList<String>();
			for (String column : cols) {
				upperCols.add(column.toUpperCase());
			}

			ArrayList<String> constants = new ArrayList<String>();
			constants.add("FASE");
			constants.add("PROVINCIA");
			constants.add("MUNICIPIO");
			constants.add("ENTIDAD");
			constants.add("NUCLEO");

			int constn = 0;
			for (String constant : constants) {
				int pos = upperCols.indexOf(constant);
				if (pos > -1 && c.getValue(constant) != null) {
					constn++;
					where = where + cols.get(pos) + "='" + c.getValue(constant)
							+ "' AND ";
				}
			}

			if (constn > 0) {
				where = where.substring(0, where.length() - 5);
			} else {
				where = "";
			}
		}
		return where;
	}

	private String getLastKey(String keyName) throws FormException {
	    FormsDAO fdao = new FormsDAO();
	    ArrayList<String> fields = new ArrayList<String>();
	    return fdao.getHighestValue(key, form.getDataBase(), form.getTable(), keyName);	    
	}

	private void enableEdit(boolean enable) {
		if (editButton != null) {
			editButton.setEnabled(enable);
		}
	}

	public void fillValues() {
		ArrayList fields = ((Dependency)form.getDependencies().get(0)).getFieldsInterface();
		for (int i=0; i<fields.size(); i++) {
			FieldInterface field = (FieldInterface) fields.get(i);
			if (field instanceof DependencyMasterField) {
				((DependencyMasterField) field).loadValue();
			}
		}
	}
	
	public void fillValuesFromKey() {
	    	form.fillForm(key);
	}

	public void close() {
		PluginServices.getMDIManager().closeWindow(this);
	}

	public void actionPerformed(ActionEvent arg0) {

		if (arg0.getSource() == editButton) {
			Iterator<String> it = key.keySet().iterator();
			String keyName = it.next();
			int pos = Integer.valueOf(key.get(keyName));
			EditAlphanumericForm eaf = new EditAlphanumericForm(this, formName,
					pos);
			eaf.open();
		}

		if (arg0.getSource() == newButton) {
			EditAlphanumericForm eaf = new EditAlphanumericForm(this, formName);
			eaf.open();
		}

		if (arg0.getSource() == closeButton) {
			close();
		}
	}
}
