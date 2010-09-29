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
import com.iver.andami.plugins.Extension;
import es.udc.cartolab.gvsig.eielforms.forms.alphanumeric.CarreterasForm;
import es.udc.cartolab.gvsig.users.utils.DBSession;

public class CarreterasFormExtension extends Extension {
	private CarreterasForm viewer = null;

	public void execute(String actionCommand) {
		// TODO Auto-generated method stub
		viewer = new CarreterasForm("Carreteras");
		if (viewer.init()){
			PluginServices.getMDIManager().addCentredWindow(viewer);
		} // TODO: throw a message on the else (when there's no data)
		  // or something like that	
	}

	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	public boolean isEnabled() {
		if (DBSession.getCurrentSession() != null) {
			return true;
		}else {
			return false;
		}
	}

	public boolean isVisible() {
		// TODO Auto-generated method stub
		return true;
	}

}
