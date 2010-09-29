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

package es.udc.cartolab.gvsig.eielforms;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import com.iver.andami.PluginServices;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.andami.ui.mdiManager.WindowInfo;
import com.iver.cit.gvsig.fmap.layers.SelectionEvent;

public class NoNavTable extends AbstractNoNavTable {
	private IWindow window;

	public NoNavTable(String formName) {
		super(formName);
		// TODO Auto-generated constructor stub
	}

	public JPanel getCenterPanel() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean init() {
		window = PluginServices.getMDIManager().getActiveWindow();
		
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();

		super.setLayout(layout);
		c.gridy = 0;
		c.gridx = 1;
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.BOTH;
		JPanel northPanel = getNorthPanel();
		super.add(northPanel, c);
		
		c.gridy = 11;		
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.SOUTH;
		JPanel southPanel = getSouthPanel();
		super.add(southPanel, c);
		
		// TODO Auto-generated method stub
		super.repaint();		
		super.setVisible(true);
		return true;
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void selectionChanged(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowActivated() {
		// TODO Auto-generated method stub
		
	}

	public void windowClosed() {
		// TODO Auto-generated method stub
		
	}

	public void save() {
		// TODO Auto-generated method stub
		
	}


	public void cancel() {
		// TODO Auto-generated method stub
		
	}

}
