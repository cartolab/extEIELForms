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
import com.iver.cit.gvsig.fmap.MapControl;
import com.iver.cit.gvsig.fmap.tools.Behavior.PointBehavior;
import com.iver.cit.gvsig.project.documents.view.gui.View;

import es.udc.cartolab.gvsig.users.utils.DBSession;

public class FormPointExtension extends Extension {
	private final String iconPath = "images/information.png";
	private final String iconCode = "view-eiel-information";

	public void initialize() {
		registerIcon();
	}

	public void execute(String actionCommand) {
		View view = (View) PluginServices.getMDIManager().getActiveWindow();
		MapControl mc = view.getMapControl();
		if (!mc.getNamesMapTools().containsKey("formPoint")) {
			FormPointListener fpl = new FormPointListener(mc);
			mc.addMapTool("formPoint", new PointBehavior(fpl));
		}

		mc.setTool("formPoint");
	}

	public boolean isEnabled() {

		View view = (View) PluginServices.getMDIManager().getActiveWindow();
		MapControl mc = view.getMapControl();
		return mc.getMapContext().getLayers().getActives().length == 1;

	}

	public boolean isVisible() {

		return PluginServices.getMDIManager().getActiveWindow() instanceof View
				&& DBSession.getCurrentSession() != null;
	}

	private void registerIcon() {
		PluginServices.getIconTheme().registerDefault(iconCode,
				this.getClass().getClassLoader().getResource(iconPath));
	}

}