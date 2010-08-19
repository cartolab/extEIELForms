package es.udc.cartolab.gvsig.eielforms.util;

public class CampoVO
{
  private String nombre;
  private Object valor;

  public CampoVO()
  {
    this.nombre = "";
    this.valor = null;
  }

  public String getNombre() {
    return this.nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Object getValor() {
    return this.valor;
  }

  public void setValor(Object valor) {
    this.valor = valor;
  }
}