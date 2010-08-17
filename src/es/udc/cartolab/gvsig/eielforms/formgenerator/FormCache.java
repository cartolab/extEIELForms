package es.udc.cartolab.gvsig.eielforms.formgenerator;

import java.util.ArrayList;
import java.util.HashMap;

import es.udc.cartolab.gvsig.eielforms.forms.FormController;

public class FormCache
{
  private HashMap cache;
  private ArrayList keys;
  private int size;

  public FormCache(Integer size)
  {
    this.cache = new HashMap();
    this.keys = new ArrayList();
    this.size = size.intValue();
  }

  public void addFormController(String layer, FormController formController) {
    this.cache.put(layer, formController);
    this.keys.add(0, layer);

    if (this.keys.size() > this.size) {
      this.cache.remove(this.keys.get(this.size));
      this.keys.remove(this.size);
    }
  }

  public FormController getFormController(String layer) {
    return ((FormController)this.cache.get(layer));
  }

  public void resetCache() {
    this.cache.clear();
  }
}