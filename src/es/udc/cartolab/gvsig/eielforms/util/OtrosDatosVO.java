package es.udc.cartolab.gvsig.eielforms.util;

import java.util.Vector;

public class OtrosDatosVO
{
  private String codigo;
  private Vector datos;

  public OtrosDatosVO()
  {
    this.codigo = "";
    this.datos = new Vector();
  }

  public String getCodigo()
  {
    return this.codigo;
  }

  public void setCodigo(String codigo)
  {
    this.codigo = codigo;
  }

  public Vector getDatos()
  {
    return this.datos;
  }

  public void setDatos(Vector datos)
  {
    this.datos = datos;
  }
}