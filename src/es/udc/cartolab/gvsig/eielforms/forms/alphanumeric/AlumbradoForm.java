package es.udc.cartolab.gvsig.eielforms.forms.alphanumeric;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.iver.andami.ui.mdiManager.WindowInfo;
import com.iver.cit.gvsig.fmap.layers.SelectionEvent;
import com.iver.utiles.swing.JComboBox;
import com.jeta.forms.components.panel.FormPanel;

import es.udc.cartolab.gvsig.eielforms.AbstractNoNavTable;

public class AlumbradoForm extends AbstractNoNavTable {

	private  FormPanel formBody;

	public final String ID_FASE = "fase";
	private JTextField fase;
	private int fase_idx = -1;

	public final String ID_PROV = "prov";
	private JTextField prov;
	private int prov_idx = -1;

	public final String ID_MUN = "mun";
	private JTextField mun;
	private int mun_idx = -1;

	public final String ID_AH_ENER_RFL = "ah_ener_rfl";
	private JComboBox ah_ener_rfl;
	private int ah_ener_rfl_idx = -1;

	public final String ID_AH_ENER_RFI = "ah_ener_rfi";
	private JComboBox ah_ener_rfi;
	private int ah_ener_rfi_idx = -1;

	public final String ID_ENT = "ent";
	private JComboBox ent;
	private int ent_idx = -1;

	public final String ID_NUCLEO = "nucleo";
	private JComboBox nucleo;
	private int nucleo_idx = -1;

	public final String ID_CALIDAD = "calidad";
	private JComboBox calidad;
	private int calidad_idx = -1;

	public final String ID_POT_INSTAL = "pot_instal";
	private JTextField pot_instal;
	private int pot_instal_idx = -1;

	public final String ID_PUNTOS_LUZ = "puntos_luz";
	private JTextField puntos_luz;
	private int puntos_luz_idx = -1;

	public AlumbradoForm(String formName) {
		super(formName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public WindowInfo getWindowInfo() {
		if (viewInfo == null) {
			viewInfo = new WindowInfo(WindowInfo.MODELESSDIALOG | WindowInfo.RESIZABLE | WindowInfo.PALETTE);
			viewInfo.setTitle(formName);
			viewInfo.setWidth(500);
			viewInfo.setHeight(450);
		}
		return viewInfo;
	}

	@Override
	public JPanel getCenterPanel() {
		JPanel panel = new JPanel();
		formBody = new FormPanel("forms/alumbrado.jfrm");
		JScrollPane scrollPane = new JScrollPane(formBody);
		panel.add(scrollPane, "0, 0");
		return panel;
	}

	@Override
	public boolean init() {
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();

		super.setLayout(layout);

		c.gridy = 0;
		c.gridx = 0;
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.BOTH;
		JPanel northPanel = super.getNorthPanel();
		super.add(northPanel, c);

		c.gridy = 1;
		c.gridheight = 13;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		centerPanel = getCenterPanel();

		super.add(centerPanel, c);

		c.gridy = 14;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.SOUTH;
		JPanel southPanel = super.getSouthPanel();
		super.add(southPanel, c);

		super.repaint();
		super.setVisible(true);
		super.setFocusCycleRoot(true);

		return true;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void selectionChanged(SelectionEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowActivated() {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed() {
		// TODO Auto-generated method stub

	}

	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub

	}

}
