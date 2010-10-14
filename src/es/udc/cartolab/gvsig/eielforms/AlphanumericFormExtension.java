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

import com.iver.andami.plugins.Extension;

import es.udc.cartolab.gvsig.eielforms.gui.AlphanumericForm;
import es.udc.cartolab.gvsig.users.utils.DBSession;

public class AlphanumericFormExtension extends Extension {

	@Override
	public void initialize() {

	}

	@Override
	public void execute(String actionCommand) {
		AlphanumericForm af = new AlphanumericForm(actionCommand);
		af.open();

	}

	@Override
	public boolean isEnabled() {
		DBSession dbs = DBSession.getCurrentSession();
		return dbs!=null;
	}

	@Override
	public boolean isVisible() {
		DBSession dbs = DBSession.getCurrentSession();
		return dbs!=null;
	}

}
