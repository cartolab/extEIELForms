package es.udc.cartolab.gvsig.eielforms;

import com.iver.andami.PluginServices;
import com.iver.andami.plugins.Extension;
import com.iver.cit.gvsig.fmap.layers.FLayers;
import com.iver.cit.gvsig.fmap.layers.FLyrVect;
import com.iver.cit.gvsig.project.documents.view.gui.View;

import es.udc.cartolab.gvsig.navtable.AbstractNavTable;
import es.udc.cartolab.gvsig.navtable.NavTable;

public class FormExtension extends Extension {

	@Override
	public void initialize() {
		// TODO Auto-generated method stub

	}

	@Override
	public void execute(String actionCommand) {
//		ConstantsManager cm = new ConstantsManager();
//		FormGenerator fg = new FormGenerator();
//		try {
//			FormController fc = fg.createFormController("ferrocarril");
//			fc.getInterface().setVisible(true);
//		} catch (FormException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		View v = (View) PluginServices.getMDIManager().getActiveWindow();
		FLyrVect l = (FLyrVect) v.getMapControl().getMapContext().getLayers().getActives()[0];
		AbstractNavTable nt = new EIELNavTable(l);
		if (nt.init()) {
			PluginServices.getMDIManager().addCentredWindow(nt);
		} else {
			nt = new NavTable(l);
			if (nt.init()) {
				PluginServices.getMDIManager().addCentredWindow(nt);
			}
		}
	}

	@Override
	public boolean isEnabled() {
		View v = (View) PluginServices.getMDIManager().getActiveWindow();
		FLayers layers = v.getMapControl().getMapContext().getLayers();
		if (layers.getActives().length > 0) {
			return layers.getActives()[0] instanceof FLyrVect;
		} else return false;
	}

	@Override
	public boolean isVisible() {
		return PluginServices.getMDIManager().getActiveWindow() instanceof View;
	}
	
	

}
