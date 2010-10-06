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

import com.iver.andami.PluginServices;
import com.iver.andami.messages.NotificationManager;
import com.iver.andami.plugins.Extension;
import com.iver.cit.gvsig.fmap.layers.FLayer;
import com.iver.cit.gvsig.fmap.layers.FLyrVect;
import com.iver.cit.gvsig.listeners.CADListenerManager;
import com.iver.cit.gvsig.listeners.EndGeometryListener;

import es.udc.cartolab.gvsig.navtable.AbstractNavTable;
import es.udc.cartolab.gvsig.navtable.NavTable;
import es.udc.cartolab.gvsig.navtable.ToggleEditing;

public class ThrowFormExtension extends Extension implements EndGeometryListener {

	//este valor se tomara desde las preferencias de NavTable y no en una variable aqui
	boolean formsEnabled = false;

	@Override
	public void initialize() {
		//se retira el listener de navtable y se pone el nuestro si procede,
		//se desactiva la extension de formularios automaticos de navtable.
	}

	@Override
	public void execute(String actionCommand) {
		if (!formsEnabled) {
			CADListenerManager.addEndGeometryListener("es.udc.cartolab.eielforms", this);
			NotificationManager.addInfo("Formularios activados");
			formsEnabled = true;
		} else {
			CADListenerManager.removeEndGeometryListener("es.udc.cartolab.eielforms");
			NotificationManager.addInfo("Formularios desactivados");
			formsEnabled = false;
		}
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean isVisible() {
		return true;
	}

	@Override
	public void endGeometry(FLayer layer) {

		if (layer.isEditing()) {
			ToggleEditing te = new ToggleEditing();
			te.stopEditing(layer, false);
		}

		if (layer instanceof FLyrVect) {
			FLyrVect l = (FLyrVect) layer;
			AbstractNavTable nt = new EIELNavTable(l);
			if (nt.init()) {
				PluginServices.getMDIManager().addCentredWindow(nt);
			} else {
				nt = new NavTable(l);
				if (nt.init()) {
					PluginServices.getMDIManager().addCentredWindow(nt);
				}
			}
			nt.last();
		}
	}

}
