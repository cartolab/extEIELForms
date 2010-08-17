package es.udc.cartolab.gvsig.eielforms.formgenerator;

import java.util.Vector;

public abstract class Subject
{
  protected Vector<Observer> observeres;

  public Subject()
  {
    this.observeres = new Vector<Observer>();
  }

  public void addObserver(Observer o)
  {
    this.observeres.addElement(o);
  }

  public void deleteObserver(Observer o)
  {
    this.observeres.removeElement(o);
  }

  public void notifyObservers() {
    for (int i = 0; i < this.observeres.size(); ++i) {
      Observer o = this.observeres.elementAt(i);
      o.update(this);
    }
  }

  public void notifyDeletionToObservers() {
    for (int i = 0; i < this.observeres.size(); ++i) {
      Observer o = this.observeres.elementAt(i);
      o.updateDeletion(this);
    }
  }
}