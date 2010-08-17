package es.udc.cartolab.gvsig.eielforms.domain.generator;

import java.util.HashMap;

import es.udc.cartolab.gvsig.eielforms.domain.Domain;

public class DomainCache
{
  private HashMap cache;

  public DomainCache()
  {
    this.cache = new HashMap();
  }

  public void addDomain(String name, Domain domain) {
    this.cache.put(name, domain);
  }

  public Domain getDomain(String name) {
    return ((Domain)this.cache.get(name));
  }
}