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

import org.w3c.dom.Node;

public class NucSubFormGenerator {

	public NucSubFormGenerator() {

	}

	public NucSubForm processNucSubForm(Node nucSubformNode) {

		String table = nucSubformNode.getAttributes().getNamedItem("Table").getNodeValue();

		NucSubForm nsf = new NucSubForm(table);

		Node subformContent = nucSubformNode.getFirstChild();
		while (subformContent != null) {
			if (subformContent.getNodeName().equals("Field")) {
				nsf.addField(subformContent.getFirstChild().getNodeValue());
			}

			subformContent = subformContent.getNextSibling();
		}

		return nsf;

	}

}
