package es.udc.cartolab.gvsig.eielforms;

import com.iver.andami.PluginServices;
import com.iver.andami.plugins.Extension;

import es.udc.cartolab.gvsig.eielforms.forms.alphanumeric.AlumbradoForm;
import es.udc.cartolab.gvsig.users.utils.DBSession;

public class AlumbradoFormExtension extends Extension {
	
	private AlumbradoForm viewer = null;

	public void execute(String actionCommand) {
		// TODO Auto-generated method stub
		viewer = new AlumbradoForm("Alumbrado");
		if (viewer.init()){
			PluginServices.getMDIManager().addCentredWindow(viewer);
		} // TODO: throw a message on the else (when there's no data)
		  // or something like that
	}

	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	public boolean isEnabled() {
		if (DBSession.getCurrentSession() != null) {
			return true;
		}else {
			return false;
		}
	}

	public boolean isVisible() {
		// TODO Auto-generated method stub
		return true;
	}

}
