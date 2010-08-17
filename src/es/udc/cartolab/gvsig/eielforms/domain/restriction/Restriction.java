package es.udc.cartolab.gvsig.eielforms.domain.restriction;

public abstract class Restriction
{
  private String name;

  public Restriction(String name)
  {
    this.name = name;
  }

  public abstract boolean validate(String paramString);

  public String toString() {
    return new String("Restriction " + this.name);
  }
}