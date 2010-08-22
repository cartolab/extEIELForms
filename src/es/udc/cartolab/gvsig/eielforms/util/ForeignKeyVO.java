package es.udc.cartolab.gvsig.eielforms.util;

import java.util.Vector;

public class ForeignKeyVO
{
  private Vector campos;

  public ForeignKeyVO()
  {
    this.campos = new Vector();
  }

  public Vector getCampos()
  {
    return this.campos;
  }

  public void setCampos(Vector campos) {
    this.campos = campos;
  }
}