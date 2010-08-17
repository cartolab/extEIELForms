package es.udc.cartolab.gvsig.eielforms;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.hardcode.gdbms.driver.exceptions.ReadDriverException;
import com.hardcode.gdbms.engine.values.Value;
import com.hardcode.gdbms.engine.values.ValueWriter;
import com.iver.cit.gvsig.fmap.layers.FLyrVect;

import es.udc.cartolab.gvsig.eielforms.field.FieldController;
import es.udc.cartolab.gvsig.eielforms.formgenerator.FormException;
import es.udc.cartolab.gvsig.eielforms.formgenerator.FormGenerator;
import es.udc.cartolab.gvsig.eielforms.forms.FormController;
import es.udc.cartolab.gvsig.navtable.AbstractNavTable;

public class EIELNavTable extends AbstractNavTable {
	
	FormController form;
	HashMap<String, String> key;
	
	public EIELNavTable(FLyrVect layer) {
		super(layer);
		key = new HashMap();
	}

	@Override
	public Object getWindowProfile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean init() {
		add(getNorthPanel());
		add(getCenterPanel());
		add(getSouthPanel());

		currentPosition = 0;

		refreshGUI();
		super.repaint();
		super.setVisible(true);
		return true;

	}

	@Override
	public void fillValues() {
		ArrayList keyFields = form.getKey();
		key = new HashMap();
		boolean foundAll = true;
		try {
			for (int i=0; i<keyFields.size(); i++) {
				FieldController fc = (FieldController) keyFields.get(i);
				int idx;
				idx = recordset.getFieldIndexByName(fc.getName());
				if (idx > -1) {
					Value val = recordset.getFieldValue(currentPosition, idx);
					String strVal = val.getStringValue(ValueWriter.internalValueWriter);
					strVal = strVal.trim().replaceAll("'", "");
					key.put(fc.getName(), strVal);
				} else {
					foundAll = false;
					break;
				}
			}
		} catch (ReadDriverException e) {
			// TODO Auto-generated catch block
			foundAll = false;
			e.printStackTrace();
		}
		if (foundAll) {
			form.fillForm(key);
		} else {
			//lanzar excepcion?
		}

	}

	@Override
	public void selectRow(int row) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Vector checkChangedValues() {
		// TODO Auto-generated method stub
		return new Vector();
	}

	@Override
	protected void saveRegister() {
		saveRecord();
	}

	@Override
	protected boolean saveRecord() {
		if (form.validate()) {
			form.update(key);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public JPanel getCenterPanel() {
		if (centerPanel == null) {
			try {
				if (form == null) {
					FormGenerator fg = new FormGenerator();
					form = fg.createFormController(layer.getName());
				} 
				centerPanel = form.getInterface();
			} catch (FormException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return centerPanel;
	}
	
}
