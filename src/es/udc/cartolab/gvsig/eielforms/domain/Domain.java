package es.udc.cartolab.gvsig.eielforms.domain;

public abstract class Domain
{
  protected String name;
  protected String tipo;

  public Domain(String name, String tipo)
  {
    this.name = name;
    this.tipo = tipo;
  }

  public String getName() {
    return this.name;
  }

  public String getType() {
    return this.tipo;
  }

  public abstract boolean validate(String paramString);
}