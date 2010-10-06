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

package es.udc.cartolab.gvsig.eielforms;

import com.iver.andami.PluginServices;
import com.iver.andami.plugins.Extension;
import com.iver.cit.gvsig.fmap.layers.FLayers;
import com.iver.cit.gvsig.fmap.layers.FLyrVect;
import com.iver.cit.gvsig.project.documents.view.gui.View;

import es.udc.cartolab.gvsig.navtable.AbstractNavTable;
import es.udc.cartolab.gvsig.navtable.NavTable;

public class FormExtension extends Extension {

	@Override
	public void initialize() {

	}

	@Override
	public void execute(String actionCommand) {
		//		ConstantsManager cm = new ConstantsManager();
		//		FormGenerator fg = new FormGenerator();
		//		try {
		//			FormController fc = fg.createFormController("ferrocarril");
		//			fc.getInterface().setVisible(true);
		//		} catch (FormException e) {
		//			e.printStackTrace();
		//		}
		View v = (View) PluginServices.getMDIManager().getActiveWindow();
		FLyrVect l = (FLyrVect) v.getMapControl().getMapContext().getLayers().getActives()[0];
		AbstractNavTable nt = new EIELNavTable(l);
		if (nt.init()) {
			PluginServices.getMDIManager().addCentredWindow(nt);
		} else {
			nt = new NavTable(l);
			if (nt.init()) {
				PluginServices.getMDIManager().addCentredWindow(nt);
			}
		}
	}

	@Override
	public boolean isEnabled() {
		View v = (View) PluginServices.getMDIManager().getActiveWindow();
		FLayers layers = v.getMapControl().getMapContext().getLayers();
		if (layers.getActives().length > 0) {
			return layers.getActives()[0] instanceof FLyrVect && !layers.getActives()[0].isEditing();
		} else {
			return false;
		}
	}

	@Override
	public boolean isVisible() {
		return PluginServices.getMDIManager().getActiveWindow() instanceof View;
	}
}
