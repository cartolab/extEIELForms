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

import java.awt.BorderLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;

import com.hardcode.gdbms.driver.exceptions.ReadDriverException;
import com.hardcode.gdbms.engine.values.NullValue;
import com.hardcode.gdbms.engine.values.Value;
import com.hardcode.gdbms.engine.values.ValueWriter;
import com.iver.andami.PluginServices;
import com.iver.andami.ui.mdiManager.WindowInfo;
import com.iver.cit.gvsig.fmap.core.IGeometry;
import com.iver.cit.gvsig.fmap.layers.FLyrVect;
import com.iver.cit.gvsig.fmap.layers.ReadableVectorial;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Polygon;

import es.udc.cartolab.gvsig.eielforms.dependency.DependencyMasterField;
import es.udc.cartolab.gvsig.eielforms.field.FieldController;
import es.udc.cartolab.gvsig.eielforms.field.FieldInterface;
import es.udc.cartolab.gvsig.eielforms.formgenerator.FormException;
import es.udc.cartolab.gvsig.eielforms.formgenerator.FormGenerator;
import es.udc.cartolab.gvsig.eielforms.forms.FormController;
import es.udc.cartolab.gvsig.eielforms.forms.listener.FormChangeEvent;
import es.udc.cartolab.gvsig.eielforms.forms.listener.FormChangeListener;
import es.udc.cartolab.gvsig.eielutils.misc.EIELValues;
import es.udc.cartolab.gvsig.navtable.AbstractNavTable;
import es.udc.cartolab.gvsig.navtable.ToggleEditing;

public class EIELNavTable extends AbstractNavTable {

	FormController form;
	HashMap<String, String> key;
	private JPanel CenterPanel;
	private JPanel SouthPanel;
	private JPanel NorthPanel;

	public EIELNavTable(FLyrVect layer) {
		super(layer);
		key = new HashMap();

	}

	protected File getHeaderFile() {
		return EIELValues.getInstance().getHeaderFileNT();
	}

	public Object getWindowProfile() {
		return null;
	}

	private JPanel getThisNorthPanel() {
		if (NorthPanel == null) {
			NorthPanel = new JPanel();
		}
		return NorthPanel;
	}

	private JPanel getThisSouthPanel() {
		if (SouthPanel == null) {
			SouthPanel = new JPanel();
		}
		return SouthPanel;
	}

	private JPanel getThisCenterPanel() {
		if (CenterPanel == null) {
			CenterPanel = new JPanel();
			BorderLayout CenterPanelLayout = new BorderLayout();
			CenterPanel.setLayout(CenterPanelLayout);
		}
		return CenterPanel;
	}

	private void initGUI() {
		MigLayout thisLayout = new MigLayout("inset 0, align center", "[grow]",
				"[][grow][]");
		this.setLayout(thisLayout);
		this.add(getThisNorthPanel(), "shrink, wrap, align center");
		this.add(getThisCenterPanel(), "shrink, growx, growy, wrap");
		this.add(getThisSouthPanel(), "shrink, align center");
	}

	public boolean init() {

		initGUI();

		// Getting NavTable parent panels and add them on the tableLayoutPanels

		JPanel centerPanel = getCenterPanel();
		if (centerPanel != null) {

			JPanel northPanel = getNorthPanel();
			getThisNorthPanel().add(northPanel);

			getThisCenterPanel().add(centerPanel);

			JPanel southPanel = getSouthPanel();
			getThisSouthPanel().add(southPanel);

			// getButton(BUTTON_COPY_PREVIOUS).setVisible(false);
			// getButton(BUTTON_COPY_SELECTED).setVisible(false);

			currentPosition = 0;

			refreshGUI();
			super.repaint();
			super.setVisible(true);
			setOpenNavTableForm(true);
			return true;
		} else {
			return false;
		}
	}

	private void fillGeomFields() throws ReadDriverException {
		// Length and area
		IGeometry g;
		ReadableVectorial source = (layer).getSource();
		source.start();
		g = source.getShape(new Long(currentPosition).intValue());
		source.stop();
		Geometry geom = g.toJTSGeometry();
		if (geom instanceof LineString || geom instanceof MultiLineString) {
			form.setLengthValue(geom.getLength());
		}
		if (geom instanceof Polygon || geom instanceof MultiPolygon) {
			form.setAreaValue(geom.getArea());
			Envelope envelope = geom.getEnvelopeInternal();
			double maxLength = envelope.getMaxX() - envelope.getMinX();
			if (envelope.getMaxY() - envelope.getMinY() > maxLength) {
				maxLength = envelope.getMaxY() - envelope.getMinY();
			}
			form.setLengthValue(maxLength);
		}
	}

	@Override
	public void fillValues() {
		ArrayList keyFields = form.getKey();
		key = new HashMap();
		boolean foundAll = true;
		boolean noNull = true;
		try {
			for (int i = 0; i < keyFields.size(); i++) {
				FieldController fc = (FieldController) keyFields.get(i);
				int idx;
				idx = recordset.getFieldIndexByName(fc.getName());
				if (idx > -1) {
					Value val = recordset.getFieldValue(currentPosition, idx);
					if (val instanceof NullValue) {
						noNull = false;
						break;
					} else {
						String strVal = val
								.getStringValue(ValueWriter.internalValueWriter);
						strVal = strVal.trim().replaceAll("'", "");
						key.put(fc.getName(), strVal);
					}
				} else {
					foundAll = false;
					break;
				}
			}

			fillGeomFields();

			setChangedValues(false);
		} catch (ReadDriverException e) {
			foundAll = false;
			e.printStackTrace();
		}
		if (foundAll && noNull) {
			form.fillForm(key);
		} else {
			if (!noNull) {
				form.fillFieldsDefault();
			}
			// lanzar excepcion?
		}

		FieldInterface field = getFocusField();
		if (field != null) {
			field.getComponent().requestFocusInWindow();
		}

		setChangedValues(false);
		enableSaveButton(false);

	}

	@Override
	public void selectRow(int row) {

	}

	@Override
	protected boolean saveRecord() {
		if (form.validate()) {

			HashMap<String, String> values = form.getFieldValues();
			ToggleEditing te = new ToggleEditing();
			boolean close = false;
			if (!layer.isEditing()) {
				te.startEditing(layer);
				close = true;
			}
			Set<String> keySet = values.keySet();
			Iterator<String> iterator = keySet.iterator();
			int total = values.size();
			int[] positions = new int[total];
			String[] strValues = new String[total];
			boolean allfound = true;
			try {

				int i = 0;
				while (iterator.hasNext() && allfound) {
					String attName = iterator.next();
					int p;
					p = this.recordset.getFieldIndexByName(attName);
					if (p > -1) {
						positions[i] = p;
						strValues[i] = values.get(attName);
						i++;
					} else {
						allfound = false;
					}
				}

				if (allfound) {
					int cp = (new Long(currentPosition)).intValue();
					te.modifyValues(layer, cp, positions, strValues);
				}
			} catch (ReadDriverException e) {
				allfound = false;
			}

			// form.update(key);
			// try {
			// layer.reload();
			// } catch (ReloadLayerException e) {
			// return false;
			// }
			if (close) {
				te.stopEditing(layer, false);
			}

			return allfound;
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

						public void formChanged(FormChangeEvent e) {
							setChangedValues(true);
							enableSaveButton(true);
						}

					});
				}

				centerPanel = new JPanel(new BorderLayout());
				JScrollPane pane = new JScrollPane(form.getInterface());
				// centerPanel = form.getInterface();
				centerPanel.add(pane);
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

	public void open() {
		PluginServices.getMDIManager().addCentredWindow(this);

		WindowInfo viewInfo = super.getWindowInfo();
		File header = getHeaderFile();
		if (viewInfo != null && header != null && header.exists()) {
			int ntHeight = viewInfo.getHeight();
			int imgHeight = EIELValues.getInstance().getHeaderNT()
					.getIconHeight();
			viewInfo.setHeight(ntHeight + imgHeight + 35);
		}

		// FieldInterface field = getFocusField();
		// field.getComponent().requestFocusInWindow();
	}

	protected void copyPrevious() {
		HashMap<String, String> currentKey = (HashMap<String, String>) key
				.clone();
		super.copyPrevious();
		key = currentKey;
		form.fillFields(currentKey);
		form.refreshGUI();
	}

	protected boolean copySelected() {
		HashMap<String, String> currentKey = (HashMap<String, String>) key
				.clone();
		if (super.copySelected()) {
			key = currentKey;
			form.fillFields(currentKey);
			form.refreshGUI();
			return true;
		} else {
			return false;
		}
	}

}
