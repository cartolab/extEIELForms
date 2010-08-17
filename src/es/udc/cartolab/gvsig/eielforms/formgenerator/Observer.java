package es.udc.cartolab.gvsig.eielforms.formgenerator;

public abstract interface Observer
{
  public abstract void update(Object paramObject);

  public abstract void updateDeletion(Object paramObject);
}