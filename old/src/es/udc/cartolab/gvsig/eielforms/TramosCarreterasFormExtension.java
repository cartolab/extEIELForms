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
import com.iver.cit.gvsig.fmap.layers.FLayers;
import com.iver.cit.gvsig.fmap.layers.FLyrVect;
import com.iver.cit.gvsig.project.documents.view.gui.View;

import es.udc.cartolab.gvsig.eielforms.forms.TramosCarreterasForm;

import es.udc.cartolab.gvsig.users.utils.DBSession;

public class TramosCarreterasFormExtension extends Extension {
	
	
	private String currentLayerName = "Tramos de carretera";
	private TramosCarreterasForm viewer = null;

	
	private FLyrVect getCurrentLayer(){
		
		View view = (View) PluginServices.getMDIManager().getActiveWindow();			
		FLayers layers = view.getModel().getMapContext().getLayers();
		return (FLyrVect)layers.getLayer(currentLayerName);
		
	}
	
	public void execute(String actionCommand){
				
		// TODO Auto-generated method stub
		FLyrVect layer = getCurrentLayer();
		
		if (layer == null){
			System.out.println("ERRRRRRRRROREEEEE: Haiche que carjare a capa: " + currentLayerName);
			return;
		}
		
		viewer = new TramosCarreterasForm(layer);
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
