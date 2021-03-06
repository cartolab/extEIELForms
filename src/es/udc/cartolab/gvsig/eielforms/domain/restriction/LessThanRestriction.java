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

package es.udc.cartolab.gvsig.eielforms.domain.restriction;

public class LessThanRestriction extends NumericFieldRestriction
{
  private Float myValue;

  public LessThanRestriction(String name, Float value)
  {
    super(name);
    this.myValue = value;
  }

  public boolean validate(String value) {
    boolean valido = true;
    try
    {
      Float valorAux = new Float(value);
      if (valorAux.compareTo(this.myValue) > 0)
        valido = true;
      else
        valido = false;
    }
    catch (NumberFormatException e)
    {
      valido = false;
    }
    return valido;
  }

  public String toString() {
    return new String("El valor del campo debe de ser menor que " + this.myValue.toString());
  }
}
