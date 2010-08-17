package es.udc.cartolab.gvsig.eielforms.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class UserDomain extends Domain
{
  private HashMap values;
  private HashMap keys;
  private Set keySet;
  private String description;

  public UserDomain(String name, HashMap values)
  {
    super(name, "usuario");

    this.values = values;
    this.keySet = values.keySet();

    Iterator clavesIterator = this.keySet.iterator();
    this.description = new String("Domain usuario " + name + ". Posibles valores:\n");

    this.keys = new HashMap();
    String oneValue = new String();
    while (clavesIterator.hasNext())
    {
      oneValue = (String)clavesIterator.next();

      this.keys.put(values.get(oneValue), oneValue);
//      UserDomain tmp132_131 = this; tmp132_131.description = tmp132_131.description + "   " + tmp132_131;
    }
  }

  public boolean validate(String value)
  {
    boolean encontrado = false;

    Iterator clavesIterator = this.keySet.iterator();
    while ((clavesIterator.hasNext()) && (!(encontrado))) {
      if (value.compareTo((String)clavesIterator.next()) != 0) continue; encontrado = true;
    }
    return encontrado;
  }

  public ArrayList getValues() {
    ArrayList valoresArrayList = new ArrayList();
    Iterator clavesIterator = this.keySet.iterator();
    while (clavesIterator.hasNext()) {
      valoresArrayList.add(this.values.get(clavesIterator.next()));
    }
    return valoresArrayList;
  }

  public ArrayList getKeys() {
    ArrayList valoresArrayList = new ArrayList();
    Iterator clavesIterator = this.keySet.iterator();
    while (clavesIterator.hasNext()) {
      valoresArrayList.add(clavesIterator.next());
    }
    return valoresArrayList;
  }

  public String toString() {
    return new String(this.description);
  }

  public String resolve(String key) {
    return ((String)this.values.get(key));
  }

  public String unResolve(String value) {
    return ((String)this.keys.get(value));
  }
}