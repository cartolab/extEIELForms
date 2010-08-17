package es.udc.cartolab.gvsig.eielforms.domain;


import java.util.ArrayList;

import es.udc.cartolab.gvsig.eielforms.domain.restriction.Restriction;

public class BasicDomain extends Domain
{
  private String tipoBase;
  private ArrayList restrictiones;
  private String descripcion;

  public BasicDomain(String name, String tipoBase)
  {
    super(name, "basico");
    this.tipoBase = tipoBase;
    this.restrictiones = new ArrayList();
    this.descripcion = new String("Domain basico " + name + " con tipo base " + tipoBase + "\n    Restrictiones:\n");
  }

  public boolean validate(String valor) {
    int i = 0;
    boolean valid = true;

    for (i = 0; i < this.restrictiones.size(); ++i) {
      valid = (valid) && (((Restriction)this.restrictiones.get(i)).validate(valor));
    }
    return valid;
  }

  public void addRestriction(Restriction restriction)
  {
    this.restrictiones.add(restriction);
    BasicDomain tmp17_16 = this; tmp17_16.descripcion = tmp17_16.descripcion + restriction.toString() + "  \n";
  }

  public ArrayList getRestrictions() {
    return this.restrictiones;
  }

  public String toString() {
    return new String(this.descripcion);
  }
}