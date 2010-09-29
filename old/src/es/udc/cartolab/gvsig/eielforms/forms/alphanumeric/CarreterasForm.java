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

package es.udc.cartolab.gvsig.eielforms.forms.alphanumeric;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.iver.andami.PluginServices;
import com.iver.andami.ui.mdiManager.WindowInfo;
import com.iver.cit.gvsig.fmap.layers.SelectionEvent;
import com.jeta.forms.components.panel.FormPanel;
import com.jeta.forms.gui.common.FormException;

import es.udc.cartolab.gvsig.eielforms.AbstractNoNavTable;
import es.udc.cartolab.gvsig.users.utils.DBSession;

public class CarreterasForm extends AbstractNoNavTable {
	private  FormPanel formBody;

	public final String ID_FASE = "fase";
	private JTextField fase;
	private int fase_idx = -1;

	public final String ID_PROV = "prov";
	private JTextField prov;
	private int prov_idx = -1;

	public final String ID_DENOMINACI = "denominaci";
	private JTextField denominaci;
	private int denominaci_idx = -1;

	public final String ID_COD_CARRT = "cod_carrt";
	private javax.swing.JComboBox cod_carrtCB;
	private int cod_carrt_idx = -1;

	private JButton addButton;
	private JButton deleteButton;
	private JButton editButton;
	private String currentCod = null;
	private boolean addNewItem = false; //true if we want to add a new item, false to modify an existed one

	public CarreterasForm(String formName) {
		super(formName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public WindowInfo getWindowInfo() {
		if (viewInfo == null) {
			viewInfo = new WindowInfo(WindowInfo.MODELESSDIALOG | WindowInfo.RESIZABLE | WindowInfo.PALETTE);
			viewInfo.setTitle(formName);
			viewInfo.setWidth(450);
			viewInfo.setHeight(300);
		}
		return viewInfo;
	}

	@Override
	public JPanel getCenterPanel() {
		JPanel panel = new JPanel();
		try {
			formBody = new FormPanel(getClass().getResourceAsStream("/carretera.jfrm"));
		} catch (FormException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}                                                                                                               
        JScrollPane scrollPane = new JScrollPane(formBody);                                                                                                              
        panel.add(scrollPane, "0, 0");                                                                                                                                   
        return panel;
	}

	@Override
	public boolean init() {
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();

		super.setLayout(layout);

		c.gridy = 0;
		c.gridx = 0;
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.BOTH;
		JPanel northPanel = super.getNorthPanel();
		super.add(northPanel, c);

		c.gridy = 1;
		c.gridheight = 13;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		centerPanel = getCenterPanel();

		super.add(centerPanel, c);

		c.gridy = 14;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.SOUTH;
		JPanel southPanel = super.getSouthPanel();
		addButton = new JButton(PluginServices.getText(this, "Add"));
		addButton.addActionListener(this);
		deleteButton = new JButton(PluginServices.getText(this, "Delete"));
		deleteButton.addActionListener(this);
		editButton = new JButton (PluginServices.getText(this, "Edit"));
		editButton.addActionListener(this);
		southPanel.add(addButton);
		southPanel.add(deleteButton);
		southPanel.add(editButton);
		super.add(southPanel, c);

		super.repaint();
		super.setVisible(true);
		super.setFocusCycleRoot(true);

		fillValues();

		return true;
	}

	public void fillValues() {
		fase = formBody.getTextField("fase");
		fase.setText("2009");
		fase.setEditable(false);

		prov = formBody.getTextField("prov");
		prov.setText("Pontevedra");
		prov.setEditable(false);

		try {
			cod_carrtCB = formBody.getComboBox("cod_carrt");
			DBSession dbs = DBSession.getCurrentSession();
			if (dbs != null) {

				Connection con = dbs.getJavaConnection();
				Statement stat = con.createStatement();
				String schema = dbs.getSchema();
				String query = "SELECT " + "cod_carrt" + " FROM " + schema + "." + "carretera";
				query = query + " ORDER BY " + "cod_carrt" + ";";

				cod_carrtCB.removeAllItems();

				ResultSet rs = stat.executeQuery(query);

				while (rs.next()) {
					String text = rs.getString("cod_carrt");
					cod_carrtCB.addItem(text);
				}
				rs.close();
			}
			cod_carrtCB.addActionListener(this);
			//cod_carrtCB.setEditable(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		refreshForm(cod_carrtCB.getSelectedItem().toString());
	}

	public void refreshForm(String codigo) {
		denominaci = formBody.getTextField("denominaci");
		denominaci.setEditable(false);
		try {
			DBSession dbs = DBSession.getCurrentSession();
			if (dbs != null) {
				Connection con = dbs.getJavaConnection();
				Statement stat = con.createStatement();
				String schema = dbs.getSchema();
				String query = "SELECT " + "denominaci" + " FROM " + schema + "." + "carretera";
				query = query + " WHERE cod_carrt = " + "'" + codigo + "'" + " ORDER BY " + "cod_carrt" + ";";
				System.out.println(query);

				ResultSet rs = stat.executeQuery(query);
				rs.next();
				String text = rs.getString("denominaci");
				denominaci.setText(text);
				rs.close();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		super.actionPerformed(event);

		if (event.getSource() == cod_carrtCB) {
			if ((cod_carrtCB.getSelectedItem() != null) && (addNewItem == false)) {
				refreshForm(cod_carrtCB.getSelectedItem().toString());
			}
		}
		if (event.getSource() == addButton) {
			addNewItem = true;
			cod_carrtCB.removeAllItems();
			cod_carrtCB.setEditable(true);
			denominaci.setEditable(true);
			denominaci.setText("");
			editButton.setEnabled(false);
			addButton.setEnabled(false);
			deleteButton.setEnabled(false);

			//			this.getSouthPanel().repaint();
		}
		if (event.getSource() == deleteButton) {
			delete();
		}
		if (event.getSource() == editButton) {
			currentCod = cod_carrtCB.getSelectedItem().toString();
			cod_carrtCB.removeActionListener(this);
			cod_carrtCB.setEditable(true);
			denominaci.setEditable(true);
			editButton.setEnabled(false);
			addButton.setEnabled(false);
			deleteButton.setEnabled(false);
		}
	}

	public void selectionChanged(SelectionEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowActivated() {
		// TODO Auto-generated method stub

	}

	@Override
	public void save() {
		try {
			String cod = cod_carrtCB.getSelectedItem().toString();
			String denominacion = denominaci.getText();

			DBSession dbs = DBSession.getCurrentSession();
			if (dbs != null) {
				Connection con = dbs.getJavaConnection();
				Statement stat;
				stat = con.createStatement();

				String query = null;
				String schema = dbs.getSchema();
				if (addNewItem == true) {
					query = "INSERT INTO " + schema + "." + "carretera";
					query = query + " VALUES (" + "'" + cod + "','" + denominacion + "','2009','Pontevedra')" + ";";
					System.out.println(query);
				}else {
					query = "UPDATE " +schema + "." + "carretera";
					query = query + " SET cod_carrt=" + "'" + cod + "'" + ", denominaci=" + "'" + denominacion + "'" + ", fase='2009', provincia='Pontevedra'";
					query = query + " WHERE cod_carrt = " + "'" + currentCod + "'" + ";";
					System.out.println(query);
				}
				stat.executeUpdate(query);
				con.commit();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fillValues();
		addNewItem = false;
		editButton.setEnabled(true);
		addButton.setEnabled(true);
		deleteButton.setEnabled(true);
	}

	public void delete() {
		try {
			String cod = cod_carrtCB.getSelectedItem().toString();

			DBSession dbs = DBSession.getCurrentSession();
			if (dbs != null) {
				Connection con = dbs.getJavaConnection();
				Statement stat;
				stat = con.createStatement();

				String schema = dbs.getSchema();
				String query = "DELETE FROM " + schema + "." + "carretera";
				query = query + " WHERE cod_carrt = " + "'" + cod + "'" + ";";
				System.out.println(query);

				stat.executeUpdate(query);
				con.commit();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fillValues();
	}

	@Override
	public void cancel() {
		editButton.setEnabled(true);
		addButton.setEnabled(true);
		deleteButton.setEnabled(true);
		fillValues();
	}
}//Class
