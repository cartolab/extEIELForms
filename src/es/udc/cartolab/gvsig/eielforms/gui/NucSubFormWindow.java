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

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.iver.andami.ui.mdiManager.WindowInfo;

import es.udc.cartolab.gvsig.eielforms.formgenerator.FormException;
import es.udc.cartolab.gvsig.eielforms.util.FormsDAO;
import es.udc.cartolab.gvsig.eielutils.misc.EIELValues;
import es.udc.cartolab.gvsig.users.utils.DBSession;

public class NucSubFormWindow extends AlphanumericForm {

	private String title, dbtable;
	private WindowInfo windowInfo;
	private HashMap<String, String> fields;
	private JTable table;
	private ArrayList<TableElement> tableElements;
	private JButton addButton;
	private ArrayList<String> nucFields;

	public NucSubFormWindow(String table, String name, HashMap<String, String> values) throws FormException {
		super("Nucleo_relacion");

		nucFields = new ArrayList<String>();
		nucFields.add(EIELValues.FIELD_FASE);
		nucFields.add(EIELValues.FIELD_COD_PRO);
		nucFields.add(EIELValues.FIELD_COD_MUN);
		nucFields.add(EIELValues.FIELD_COD_ENT);
		nucFields.add(EIELValues.FIELD_COD_POB);

		title = "N�cleos para " + name;
		this.dbtable = table;
		this.fields = values;

		getTableElementsFromDB();
		prepareTable();



	}

	public WindowInfo getWindowInfo() {
		if (windowInfo == null) {
			windowInfo = super.getWindowInfo();
			windowInfo.setWidth(500);
			windowInfo.setHeight(650);
			windowInfo.setTitle(title);
		}
		return windowInfo;
	}

	public void open() {
		super.open();

		JScrollPane pane = new JScrollPane(this.table);
		add(pane);

		add(getSouthButtonsPanel());
	}

	protected JPanel getButtonsPanel() {

		JPanel panel = new JPanel();

		addButton = new JButton("A�adir n�cleo");
		addButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent paramActionEvent) {

				HashMap<String, String> nucValues = form.getFieldValues(nucFields);

				try {
					TableElement te = new TableElement(
							nucValues.get(EIELValues.FIELD_FASE),
							nucValues.get(EIELValues.FIELD_COD_PRO),
							nucValues.get(EIELValues.FIELD_COD_MUN),
							nucValues.get(EIELValues.FIELD_COD_ENT),
							nucValues.get(EIELValues.FIELD_COD_POB));

					addTableElement(te);

				} catch (FormException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		});

		panel.add(addButton);

		return panel;
	}

	private JPanel getSouthButtonsPanel() {

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JButton okButton = new JButton("Aceptar");
		okButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent paramActionEvent) {
				save();
			}

		});

		JButton cancelButton = new JButton("Cancelar");
		cancelButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent paramActionEvent) {
				close();
			}

		});

		JPanel auxPanel = new JPanel();
		auxPanel.add(okButton);
		auxPanel.add(cancelButton);

		JPanel auxPanel2 = new JPanel();
		JButton removeRowsButton = new JButton("Quitar elementos seleccionados");
		removeRowsButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent paramActionEvent) {

				DefaultTableModel model = (DefaultTableModel) table.getModel();
				for (int i=table.getSelectedRowCount()-1; i>=0; i--) {
					model.removeRow(table.getSelectedRows()[i]);
				}


			}

		});
		auxPanel2.add(removeRowsButton);


		panel.add(auxPanel2, BorderLayout.WEST);
		panel.add(auxPanel, BorderLayout.EAST);
//		panel.add(cancelButton, BorderLayout.EAST);C

		return panel;

	}

	public void save() {

	}

	private void getTableElementsFromDB() {

		tableElements = new ArrayList<TableElement>();

		FormsDAO fdao = new FormsDAO();
		DBSession dbs = DBSession.getCurrentSession();
		if (dbs != null) {

			try {
				List<HashMap<String, String>> elements = fdao.getValues(fields, dbs.getSchema(), dbtable, nucFields);
				for (HashMap<String, String> map : elements) {
					String fase = map.get(EIELValues.FIELD_FASE);
					String provincia = map.get(EIELValues.FIELD_COD_PRO);
					String municipio = map.get(EIELValues.FIELD_COD_MUN);
					String entidad = map.get(EIELValues.FIELD_COD_ENT);
					String nucleo = map.get(EIELValues.FIELD_COD_POB);
					TableElement te = new TableElement(fase, provincia, municipio, entidad, nucleo);
					tableElements.add(te);
				}
			} catch (FormException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void prepareTable() {

		String[][] elements = new String[tableElements.size()][5];
		String[] header = {"Provincia", "Municipio", "Entidad", "N�cleo", "Denominaci�n"};

		for (int i=0; i<tableElements.size(); i++) {
			TableElement te = tableElements.get(i);
			elements[i][0] = te.getProvincia();
			elements[i][1] = te.getMunicipio();
			elements[i][2] = te.getEntidad();
			elements[i][3] = te.getNucleo();
			try {
				elements[i][4] = te.getDenominacion();
			} catch (FormException e) {
				elements[i][4] = "";
			}
		}

//		table = new JTable(elements, header);
		table = new JTable();
		DefaultTableModel dataModel = new DefaultTableModel(elements, header);
		table.setModel(dataModel);

	}

	private void addTableElement(TableElement te) {

		String[] row = new String[5];
		row[0] = te.getProvincia();
		row[1] = te.getMunicipio();
		row[2] = te.getEntidad();
		row[3] = te.getNucleo();
		try {
			row[4] = te.getDenominacion();
		} catch (FormException e) {
			row[4] = "";
		}

		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.addRow(row);
		model.fireTableRowsInserted(model.getRowCount()-1, model.getRowCount()-1);

	}


	private class TableElement {

		private String fase, provincia, municipio, entidad, nucleo, denominaci=null;

		public TableElement(String fase, String provincia, String municipio, String entidad, String nucleo) throws FormException {
			this.fase = fase;
			this.provincia = provincia;
			this.municipio = municipio;
			this.entidad = entidad;
			this.nucleo = nucleo;

		}

		private void setDenominaci() throws FormException {
			DBSession dbs = DBSession.getCurrentSession();
			if (dbs != null) {
				FormsDAO fdao = new FormsDAO();
				HashMap<String, String> key = new HashMap<String, String>();
				key.put(EIELValues.FIELD_FASE, fase);
				key.put(EIELValues.FIELD_COD_PRO, provincia);
				key.put(EIELValues.FIELD_COD_MUN, municipio);
				key.put(EIELValues.FIELD_COD_ENT, entidad);
				key.put(EIELValues.FIELD_COD_POB, nucleo);

				ArrayList<String> fields = new ArrayList<String>();
				fields.add(EIELValues.FIELD_DENOM);

				HashMap map = fdao.getKeyValues(key, dbs.getSchema(), EIELValues.TABLE_NUCLEO, fields);
				if (!map.isEmpty()) {
					denominaci = (String) map.get(EIELValues.FIELD_DENOM);
				}
			}
		}

		public String getFase() {
			return fase;
		}

		public String getProvincia() {
			return provincia;
		}

		public String getMunicipio() {
			return municipio;
		}

		public String getEntidad() {
			return entidad;
		}

		public String getNucleo() {
			return nucleo;
		}

		public String getDenominacion() throws FormException {
			if (denominaci==null) {
				setDenominaci();
			}
			return denominaci;
		}

		public boolean equals(TableElement te) {
			if (fase.equals(te.getFase())) {
				if (provincia.equals(te.getProvincia())) {
					if (municipio.equals(te.getMunicipio())) {
						if (entidad.equals(te.getEntidad())) {
							if (nucleo.equals(te.getNucleo())) {
								return true;
							}
						}
					}
				}
			}
			return false;
		}

		public void saveInDB() {

		}

	}
}
