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
import com.iver.cit.gvsig.fmap.layers.FLayer;
import com.iver.cit.gvsig.fmap.layers.FLyrVect;
import com.iver.cit.gvsig.project.documents.view.toc.AbstractTocContextMenuAction;
import com.iver.cit.gvsig.project.documents.view.toc.ITocItem;

import es.udc.cartolab.gvsig.eielforms.gui.EIELNavTable;
import es.udc.cartolab.gvsig.navtable.AbstractNavTable;
import es.udc.cartolab.gvsig.navtable.NavTable;
import es.udc.cartolab.gvsig.users.utils.DBSession;

public class FormTOCMenuEntry extends AbstractTocContextMenuAction {

	public String getText() {
		// TODO Auto-generated method stub
		return "Formulario EIEL";
	}

	@Override
	public void execute(ITocItem item, FLayer[] selectedItems) {

		FLyrVect l = (FLyrVect) selectedItems[0];
		AbstractNavTable nt = new EIELNavTable(l);
		if (nt.init()) {
			((EIELNavTable) nt).open();
		} else {
			nt = new NavTable(l);
			if (nt.init()) {
				PluginServices.getMDIManager().addCentredWindow(nt);
			}
		}

	}

	public boolean isEnabled(ITocItem item, FLayer[] selectedItems) {
		if (selectedItems.length == 1) {
			return selectedItems[0].isAvailable()
					&& selectedItems[0] instanceof FLyrVect;
		} else {
			return false;
		}
	}

	public boolean isVisible(ITocItem item, FLayer[] selectedItems) {
		if (DBSession.getCurrentSession() == null) {
			return false;
		} else {
			return true;
		}
	}

	public String getGroup() {
		return "navtable";
	}

	public int getOrder() {
		return 2;
	}

	public int getGroupOrder() {
		return 100;
	}

}