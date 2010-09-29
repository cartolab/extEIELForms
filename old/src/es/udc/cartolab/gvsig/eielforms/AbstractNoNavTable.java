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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.iver.andami.PluginServices;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.andami.ui.mdiManager.IWindowListener;
import com.iver.andami.ui.mdiManager.WindowInfo;
import com.iver.cit.gvsig.fmap.layers.SelectionListener;

/**
 * AbstractNoNavTable is the base class that defines the
 * layout of the window that allows to create the alphanumeric
 * forms of EIEL.
 * 
 * It has three panels:
 * <ul>
 * <li>The north panel, with the controls to handle the 
 * navigation behavior.
 * <li>The main panel, with the representation of the 
 * data, it must be implemented in the subclasses.
 * <li>The south panel, with the navigation controls.
 * </ul>
 * 
 * If there are a image on 'gvSIG/extensiones/es.udc.cartolab.gvsig.eielforms/images/nonavtable_header.png' 
 * is loaded on the NorthPanel. 
 * 
 * @author Pablo Sanxiao
 */
public abstract class AbstractNoNavTable extends JPanel implements IWindow, ActionListener, SelectionListener, IWindowListener {
	private static final long serialVersionUID = 1L;

	private boolean closed = false;
	
	protected JPanel northPanel = null;
	protected JPanel centerPanel = null;
	protected JPanel southPanel = null;

	//South panel
	JButton okButton = null;
	JButton cancelButton = null;
	
	protected WindowInfo viewInfo = null;
	protected String formName = null;
	/**
	 * 
	 * Constructor of the class. 
	 * 	  
	 * @param formName	Name of the form.
	 */
	public AbstractNoNavTable(String formName) {
		super();
		this.formName = formName;
		WindowInfo window = this.getWindowInfo();
		//String title = window.getTitle();
		window.setTitle(formName);
	}
	
	/**
	 * It initializes the window.
	 * 
	 * @return 	true if it is successful, false if not.
	 */
	public abstract boolean init();

	/**
	 * Creates the upper panel.
	 * 
	 * @return the panel.
	 */
	protected JPanel getNorthPanel() {
		northPanel = new JPanel(new BorderLayout());
		
		JPanel northFirstRow = new JPanel(new BorderLayout());

		File iconPath = new File("gvSIG/extensiones/es.udc.cartolab.eielforms/images/nonavtable_header.png");
		northFirstRow.setBackground(Color.WHITE);
		ImageIcon logo = new ImageIcon(iconPath.getAbsolutePath());
		JLabel icon = new JLabel();
		icon.setIcon(logo);
		northFirstRow.add(icon, BorderLayout.WEST);
		//viewInfo.setHeight(575);
		
		JPanel labelPanel = new JPanel(new FlowLayout());
		labelPanel.setBackground(Color.WHITE);
		JLabel formLabel = new JLabel(formName);
		formLabel.setFont( new Font( "Arial", Font.BOLD, 16));

		labelPanel.add(formLabel);
		
		northFirstRow.add(labelPanel, BorderLayout.EAST);
		
		northPanel.add(northFirstRow, BorderLayout.CENTER);
		
		return northPanel;
	}
	
	/**
	 * Creates the main panel.
	 * 
	 * @return the panel.
	 */
	public abstract JPanel getCenterPanel();
	
	/**
	 * Creates the bottom panel.
	 * 
	 * @return the panel.
	 */
	protected JPanel getSouthPanel(){
		okButton = new JButton(PluginServices.getText(this, "ok"));
		okButton.addActionListener(this);
		cancelButton = new JButton(PluginServices.getText(this, "cancel"));
		cancelButton.addActionListener(this);
		
		JPanel buttonsPanel = new JPanel(new FlowLayout());
		buttonsPanel.add(okButton);
		buttonsPanel.add(cancelButton);
		
		return buttonsPanel;
	}
	
	/**
	 * Sets the configuration of the window.
	 * 
	 * @return	the configuration of the window.
	 */
	public WindowInfo getWindowInfo() {
		if (viewInfo == null) {
			viewInfo = new WindowInfo(WindowInfo.MODELESSDIALOG | WindowInfo.RESIZABLE | WindowInfo.PALETTE);
			//viewInfo.setTitle(PluginServices.getText(this, "NavTable"));
			viewInfo.setWidth(425);
			viewInfo.setHeight(525);
		}
		return viewInfo;
	}
	
	public abstract void save();
	public abstract void cancel();
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cancelButton) {
//			PluginServices.getMDIManager().closeWindow(this);
			cancel();
		}
		if (e.getSource() == okButton) {
			save();
		}		
	}
	
	public void windowClosed() {
		if (!closed) {
			closed = true;
		}
	}
}//Class
