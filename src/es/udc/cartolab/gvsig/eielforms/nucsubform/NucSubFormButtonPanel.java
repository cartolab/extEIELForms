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

package es.udc.cartolab.gvsig.eielforms.nucsubform;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JPanel;

import es.udc.cartolab.gvsig.eielforms.forms.FormInterface;
import es.udc.cartolab.gvsig.eielforms.gui.NucSubFormWindow;

public class NucSubFormButtonPanel extends JPanel {

	FormInterface formInterface;


	public NucSubFormButtonPanel(FormInterface formInterface) {
		this.formInterface = formInterface;
		addButton();
	}

	private void addButton() {
		JButton button = new JButton("Núcleos");
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				HashMap<String, String> values = formInterface.getFormController().getFieldValues(formInterface.getNucSubForm().getFields());
				NucSubFormWindow w = new NucSubFormWindow(
						formInterface.getNucSubForm().getTableName(),
						formInterface.getFormController().getName(),
						values);
				w.open();
			}

		});
	    setLayout(new FlowLayout());
	    add(button, 0);
	}

}
