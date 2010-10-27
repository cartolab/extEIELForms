/*
 * Copyright (c) 2010. Cartolab (Universidade da Coru�a)
 *
 * This file is part of extEIELForms
 *
 * extEIELForms is based on the forms application of GisEIEL <http://giseiel.forge.osor.eu/>
 * devoloped by Laboratorio de Bases de Datos (Universidade da Coru�a)
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

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;

import com.hardcode.gdbms.driver.exceptions.ReadDriverException;
import com.hardcode.gdbms.engine.values.Value;
import com.hardcode.gdbms.engine.values.ValueWriter;
import com.iver.andami.PluginServices;
import com.iver.cit.gvsig.exceptions.layers.ReloadLayerException;
import com.iver.cit.gvsig.fmap.layers.FLyrVect;

import es.udc.cartolab.gvsig.eielforms.dependency.DependencyMasterField;
import es.udc.cartolab.gvsig.eielforms.field.FieldController;
import es.udc.cartolab.gvsig.eielforms.field.FieldInterface;
import es.udc.cartolab.gvsig.eielforms.formgenerator.FormException;
import es.udc.cartolab.gvsig.eielforms.formgenerator.FormGenerator;
import es.udc.cartolab.gvsig.eielforms.forms.FormController;
import es.udc.cartolab.gvsig.eielforms.forms.listener.FormChangeEvent;
import es.udc.cartolab.gvsig.eielforms.forms.listener.FormChangeListener;
import es.udc.cartolab.gvsig.navtable.AbstractNavTable;

public class EIELNavTable extends AbstractNavTable {

	FormController form;
	HashMap<String, String> key;

	public EIELNavTable(FLyrVect layer) {
		super(layer);
		key = new HashMap();
	}

	@Override
	public Object getWindowProfile() {
		return null;
	}

	@Override
	public boolean init() {
		JPanel centerPanel = getCenterPanel();
		if (centerPanel != null) {
			add(getNorthPanel());
			add(getCenterPanel());
			add(getSouthPanel());

			getButton(BUTTON_COPY_PREVIOUS).setVisible(false);
			getButton(BUTTON_COPY_SELECTED).setVisible(false);

			currentPosition = 0;

			refreshGUI();
			super.repaint();
			super.setVisible(true);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void fillValues() {
		ArrayList keyFields = form.getKey();
		key = new HashMap();
		boolean foundAll = true;
		try {
			for (int i=0; i<keyFields.size(); i++) {
				FieldController fc = (FieldController) keyFields.get(i);
				int idx;
				idx = recordset.getFieldIndexByName(fc.getName());
				if (idx > -1) {
					Value val = recordset.getFieldValue(currentPosition, idx);
					String strVal = val.getStringValue(ValueWriter.internalValueWriter);
					strVal = strVal.trim().replaceAll("'", "");
					key.put(fc.getName(), strVal);
				} else {
					foundAll = false;
					break;
				}
			}
			setChangedValues(false);
		} catch (ReadDriverException e) {
			foundAll = false;
			e.printStackTrace();
		}
		if (foundAll) {
			form.fillForm(key);
		} else {
			//lanzar excepcion?
		}

		FieldInterface field = getFocusField();
		field.getComponent().requestFocusInWindow();

	}

	@Override
	public void selectRow(int row) {

	}

	@Override
	protected boolean saveRecord() {
		if (form.validate()) {
			form.update(key);
			try {
				layer.reload();
			} catch (ReloadLayerException e) {
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public JPanel getCenterPanel() {
		if (centerPanel == null) {
			try {
				if (form == null) {
					FormGenerator fg = new FormGenerator();
					form = fg.createFormController(layer.getName());
					form.addFormChangeListener(new FormChangeListener() {

						@Override
						public void formChanged(FormChangeEvent e) {
							setChangedValues(true);
							enableSaveButton(true);
						}

					});
				}
				centerPanel = form.getInterface();
				centerPanel.setFocusCycleRoot(true);

			} catch (FormException fe) {
				fe.printStackTrace();
				return null;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return centerPanel;
	}

	public void onlySelected() {
		onlySelectedCB.setSelected(true);
	}

	private FieldInterface getFocusField() {
		ArrayList fields = form.getFieldsInterface();
		boolean found = false;
		FieldInterface focusField = null;
		for (int i=0; i<fields.size(); i++) {
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

	public void open() {
		PluginServices.getMDIManager().addCentredWindow(this);
		FieldInterface field = getFocusField();
		field.getComponent().requestFocusInWindow();
	}

}
