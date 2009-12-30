package es.udc.cartolab.gvsig.eielforms.forms;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import com.hardcode.gdbms.engine.values.Value;
import com.hardcode.gdbms.engine.values.ValueWriter;
import com.iver.andami.PluginServices;
import com.iver.andami.ui.mdiManager.WindowInfo;
import com.iver.cit.gvsig.fmap.DriverException;
import com.iver.cit.gvsig.fmap.layers.FLyrVect;
import com.iver.cit.gvsig.fmap.layers.SelectableDataSource;
import com.jeta.forms.components.panel.FormPanel;

import es.udc.cartolab.gvsig.navtable.AbstractNavTable;

public class TramosCarreterasForm extends AbstractNavTable {
	
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

	public final String ID_COD_CARRT = "cod_carrt";
	private JComboBox cod_carrt;
	private int cod_carrt_idx = -1;

	public final String ID_PK_INICIAL = "pk_inicial";
	private JTextField pk_inicial;
	private int pk_inicial_idx = -1;

	public final String ID_PK_FINAL = "pk_final";
	private JTextField pk_final;
	private int pk_final_idx = -1;

	public final String ID_TITULAR = "titular";
	private JComboBox titular;
	private int titular_idx = -1;

	public final String ID_GESTION = "gestion";
	private JComboBox gestion;
	private int gestion_idx = -1;

	public final String ID_SENALIZA = "senaliza";
	private JComboBox senaliza;
	private int senaliza_idx = -1;

	public final String ID_FIRME = "firme";
	private JComboBox firme;
	private int firme_idx = -1;

	public final String ID_ESTADO = "estado";
	private JComboBox estado;
	private int estado_idx = -1;

	public final String ID_ANCHO = "ancho";
	private JTextField ancho;
	private int ancho_idx = -1;

	public final String ID_LONGITUD = "longitud";
	private JTextField longitud;
	private int longitud_idx = -1;

	public final String ID_PASOS_NIVE = "pasos_nive";
	private JTextField pasos_nive;
	private int pasos_nive_idx = -1;

	public final String ID_DIMENSIONA = "dimensiona";
	private JComboBox dimensiona;
	private int dimensiona_idx = -1;

	public final String ID_MUY_SINUOS = "muy_sinuos";
	private JComboBox muy_sinuos;
	private int muy_sinuos_idx = -1;

	public final String ID_PTE_EXCESI = "pte_excesi";
	private JComboBox pte_excesi;
	private int pte_excesi_idx = -1;

	public final String ID_FRE_ESTREC = "fre_estrec";
	private JComboBox fre_estrec;
	private int fre_estrec_idx = -1;


	public TramosCarreterasForm(FLyrVect layer) {
		super(layer);
		getIndexes();
		// TODO Auto-generated constructor stub
	}



	protected Vector checkChangedValues() {
		// TODO Auto-generated method stub
		return new Vector();
	}


	public void fillValues() {
		// TODO Auto-generated method stub
		try {
			
			if (currentPosition >= recordset.getRowCount()) {
				currentPosition = recordset.getRowCount()-1;
			}
			if (currentPosition < 0) {
				currentPosition = 0;
			}
			
			Value value = recordset.getFieldValue(currentPosition, fase_idx);
			String textValue = value.getStringValue(ValueWriter.internalValueWriter);
			textValue = textValue.replaceAll("'", "");
			fase.setText(textValue);
			
			value = recordset.getFieldValue(currentPosition, prov_idx);
			textValue = value.getStringValue(ValueWriter.internalValueWriter);
			textValue = textValue.replaceAll("'", "");
			prov.setText(textValue);
			
			value = recordset.getFieldValue(currentPosition, mun_idx);
			textValue = value.getStringValue(ValueWriter.internalValueWriter);
			textValue = textValue.replaceAll("'", "");
			mun.setText(textValue);
			
			value = recordset.getFieldValue(currentPosition, cod_carrt_idx);
			textValue = value.getStringValue(ValueWriter.internalValueWriter);
			textValue = textValue.replaceAll("'", "");
			
			
			value = recordset.getFieldValue(currentPosition, pk_inicial_idx);
			textValue = value.getStringValue(ValueWriter.internalValueWriter);
			textValue = textValue.replaceAll("'", "");
			pk_inicial.setText(textValue);
			
			value = recordset.getFieldValue(currentPosition, pk_final_idx);
			textValue = value.getStringValue(ValueWriter.internalValueWriter);
			textValue = textValue.replaceAll("'", "");
			pk_final.setText(textValue);
			
			value = recordset.getFieldValue(currentPosition, titular_idx);
			textValue = value.getStringValue(ValueWriter.internalValueWriter);
			textValue = textValue.replaceAll("'", "");
			
			if (textValue.equalsIgnoreCase("CA")) {
				titular.setSelectedIndex(0);
			}
			if (textValue.equalsIgnoreCase("ES")) {
				titular.setSelectedIndex(1);
			}
			if (textValue.equalsIgnoreCase("MU")) {
				titular.setSelectedIndex(2);
			}
			if (textValue.equalsIgnoreCase("OT")) {
				titular.setSelectedIndex(3);
			}
			if (textValue.equalsIgnoreCase("PR")) {
				titular.setSelectedIndex(4);
			}
			
			value = recordset.getFieldValue(currentPosition, gestion_idx);
			textValue = value.getStringValue(ValueWriter.internalValueWriter);
			textValue = textValue.replaceAll("'", "");
			
			if (textValue.equalsIgnoreCase("CA")) {
				gestion.setSelectedIndex(0);
			}
			if (textValue.equalsIgnoreCase("ES")) {
				gestion.setSelectedIndex(1);
			}
			if (textValue.equalsIgnoreCase("MU")) {
				gestion.setSelectedIndex(2);
			}
			if (textValue.equalsIgnoreCase("OT")) {
				gestion.setSelectedIndex(3);
			}
			if (textValue.equalsIgnoreCase("PR")) {
				gestion.setSelectedIndex(4);
			}
			if (textValue.equalsIgnoreCase("NO")) {
				gestion.setSelectedIndex(5);
			}
			
			
			value = recordset.getFieldValue(currentPosition, senaliza_idx);
			textValue = value.getStringValue(ValueWriter.internalValueWriter);
			textValue = textValue.replaceAll("'", "");
			
			if (textValue.equalsIgnoreCase("A")) {
				senaliza.setSelectedIndex(0);
			}
			if (textValue.equalsIgnoreCase("H")) {
				senaliza.setSelectedIndex(1);
			}
			if (textValue.equalsIgnoreCase("V")) {
				senaliza.setSelectedIndex(2);
			}
			if (textValue.equalsIgnoreCase("N")) {
				senaliza.setSelectedIndex(3);
			}

			
			value = recordset.getFieldValue(currentPosition, firme_idx);
			textValue = value.getStringValue(ValueWriter.internalValueWriter);
			textValue = textValue.replaceAll("'", "");
			
			if (textValue.equalsIgnoreCase("AD")) {
				firme.setSelectedIndex(0);
			}
			if (textValue.equalsIgnoreCase("HR")) {
				firme.setSelectedIndex(1);
			}
			if (textValue.equalsIgnoreCase("MB")) {
				firme.setSelectedIndex(2);
			}
			if (textValue.equalsIgnoreCase("MC")) {
				firme.setSelectedIndex(3);
			}
			if (textValue.equalsIgnoreCase("OT")) {
				firme.setSelectedIndex(4);
			}
			if (textValue.equalsIgnoreCase("RA")) {
				firme.setSelectedIndex(5);
			}
			if (textValue.equalsIgnoreCase("TI")) {
				firme.setSelectedIndex(6);
			}
			if (textValue.equalsIgnoreCase("ZE")) {
				firme.setSelectedIndex(7);
			}

			value = recordset.getFieldValue(currentPosition, estado_idx);
			textValue = value.getStringValue(ValueWriter.internalValueWriter);
			textValue = textValue.replaceAll("'", "");
			
			if (textValue.equalsIgnoreCase("B")) {
				estado.setSelectedIndex(0);
			}
			if (textValue.equalsIgnoreCase("E")) {
				estado.setSelectedIndex(1);
			}
			if (textValue.equalsIgnoreCase("M")) {
				estado.setSelectedIndex(2);
			}
			if (textValue.equalsIgnoreCase("R")) {
				estado.setSelectedIndex(3);
			}

			value = recordset.getFieldValue(currentPosition, ancho_idx);
			textValue = value.getStringValue(ValueWriter.internalValueWriter);
			textValue = textValue.replaceAll("'", "");
			ancho.setText(textValue);
			
			value = recordset.getFieldValue(currentPosition, longitud_idx);
			textValue = value.getStringValue(ValueWriter.internalValueWriter);
			textValue = textValue.replaceAll("'", "");
			longitud.setText(textValue);

			value = recordset.getFieldValue(currentPosition, pasos_nive_idx);
			textValue = value.getStringValue(ValueWriter.internalValueWriter);
			textValue = textValue.replaceAll("'", "");
			pasos_nive.setText(textValue);
			
			value = recordset.getFieldValue(currentPosition, dimensiona_idx);
			textValue = value.getStringValue(ValueWriter.internalValueWriter);
			textValue = textValue.replaceAll("'", "");

			if (textValue.equalsIgnoreCase("B")) {
				dimensiona.setSelectedIndex(0);
			}
			if (textValue.equalsIgnoreCase("M")) {
				dimensiona.setSelectedIndex(1);
			}
			if (textValue.equalsIgnoreCase("R")) {
				dimensiona.setSelectedIndex(2);
			}

			value = recordset.getFieldValue(currentPosition, muy_sinuos_idx);
			textValue = value.getStringValue(ValueWriter.internalValueWriter);
			textValue = textValue.replaceAll("'", "");

			if (textValue.equalsIgnoreCase("NO")) {
				muy_sinuos.setSelectedIndex(0);
			}
			if (textValue.equalsIgnoreCase("SI")) {
				muy_sinuos.setSelectedIndex(1);
			}

			value = recordset.getFieldValue(currentPosition, pte_excesi_idx);
			textValue = value.getStringValue(ValueWriter.internalValueWriter);
			textValue = textValue.replaceAll("'", "");

			if (textValue.equalsIgnoreCase("NO")) {
				pte_excesi.setSelectedIndex(0);
			}
			if (textValue.equalsIgnoreCase("SI")) {
				pte_excesi.setSelectedIndex(1);
			}
			
			value = recordset.getFieldValue(currentPosition, fre_estrec_idx);
			textValue = value.getStringValue(ValueWriter.internalValueWriter);
			textValue = textValue.replaceAll("'", "");

			if (textValue.equalsIgnoreCase("NO")) {
				fre_estrec.setSelectedIndex(0);
			}
			if (textValue.equalsIgnoreCase("SI")) {
				fre_estrec.setSelectedIndex(1);
			}
			
		} catch (com.hardcode.gdbms.engine.data.driver.DriverException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public WindowInfo getWindowInfo() {
		if (viewInfo == null) {
			viewInfo = new WindowInfo(WindowInfo.MODELESSDIALOG | WindowInfo.RESIZABLE | WindowInfo.PALETTE);
			viewInfo.setTitle(PluginServices.getText(this, "NavTable"));
			viewInfo.setWidth(475);
			viewInfo.setHeight(555);
		}
		return viewInfo;
	}
	
	
	

	public JPanel getCenterPanel() {
		
		JPanel panel = new JPanel();
		//panel.setPreferredSize(new Dimension(400, 400));
		formBody = new FormPanel("tramo_carretera.jfrm");
		initWidgets();
        JScrollPane scrollPane = new JScrollPane(formBody);
        scrollPane.setPreferredSize(new Dimension(465, 420));
        panel.add(scrollPane, "0, 0");
        
        return panel;
		
	}


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
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		centerPanel = getCenterPanel();

		super.add(centerPanel, c);

		c.gridy = 14;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.gridheight = 4;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.SOUTH;
		JPanel southPanel = super.getSouthPanel();
		super.add(southPanel, c);
		
		super.repaint();
		super.setVisible(true);
		super.setFocusCycleRoot(true);
		
		fillValues();
		return true;

	}

	
	protected void saveRegister() {
		// TODO Auto-generated method stub

	}

	public void selectRow(int row) {
		// TODO Auto-generated method stub

	}

	public void initWidgets() {
		 
		if (formBody == null){
			formBody = new FormPanel("tramo_carretera.jfrm");
		}
		fase = ((JTextField)formBody.getComponentByName( ID_FASE));
		prov = ((JTextField)formBody.getComponentByName( ID_PROV));
		mun = ((JTextField)formBody.getComponentByName( ID_MUN));
		cod_carrt = ((JComboBox)formBody.getComponentByName( ID_COD_CARRT));
		cod_carrt.setEditable(true);
		//cod_carrt.removeAllItems();
		pk_inicial = ((JTextField)formBody.getComponentByName( ID_PK_INICIAL));
		pk_final = ((JTextField)formBody.getComponentByName( ID_PK_FINAL));
		titular = ((JComboBox)formBody.getComponentByName( ID_TITULAR));
		titular.setEditable(true);
		//titular.removeAllItems();
		gestion = ((JComboBox)formBody.getComponentByName( ID_GESTION));
		gestion.setEditable(true);
		//gestion.removeAllItems();
		senaliza = ((JComboBox)formBody.getComponentByName( ID_SENALIZA));
		senaliza.setEditable(true);
		//senaliza.removeAllItems();
		firme = ((JComboBox)formBody.getComponentByName( ID_FIRME));
		firme.setEditable(true);
		//firme.removeAllItems();
		estado = ((JComboBox)formBody.getComponentByName( ID_ESTADO));
		estado.setEditable(true);
		//estado.removeAllItems();
		ancho = ((JTextField)formBody.getComponentByName( ID_ANCHO));
		longitud = ((JTextField)formBody.getComponentByName( ID_LONGITUD));
		pasos_nive = ((JTextField)formBody.getComponentByName( ID_PASOS_NIVE));
		dimensiona = ((JComboBox)formBody.getComponentByName( ID_DIMENSIONA));
		dimensiona.setEditable(true);
		//dimensiona.removeAllItems();
		muy_sinuos = ((JComboBox)formBody.getComponentByName( ID_MUY_SINUOS));
		muy_sinuos.setEditable(true);
		//muy_sinuos.removeAllItems();
		pte_excesi = ((JComboBox)formBody.getComponentByName( ID_PTE_EXCESI));
		pte_excesi.setEditable(true);
		//pte_excesi.removeAllItems();
		fre_estrec = ((JComboBox)formBody.getComponentByName( ID_FRE_ESTREC));
		fre_estrec.setEditable(true);
		//fre_estrec.removeAllItems();

	}
	
	   protected void getIndexes() {
			String[] fieldNames;
			try {
			    fieldNames = recordset.getFieldNames();
			    for (int i = 0; i < fieldNames.length; i++){
					if (fieldNames[i].toLowerCase().compareTo("fase") == 0) {
					    fase_idx = i;
					}
					if (fieldNames[i].toLowerCase().compareTo("prov") == 0) {
					    prov_idx = i;
					}
					if (fieldNames[i].toLowerCase().compareTo("mun") == 0) {
					    mun_idx = i;
					}
					if (fieldNames[i].toLowerCase().compareTo("cod_carrt") == 0) {
					    cod_carrt_idx = i;
					}
					if (fieldNames[i].toLowerCase().compareTo("pk_inicial") == 0) {
					    pk_inicial_idx = i;
					}
					if (fieldNames[i].toLowerCase().compareTo("pk_final") == 0) {
					    pk_final_idx = i;
					}
					if (fieldNames[i].toLowerCase().compareTo("titular") == 0) {
					    titular_idx = i;
					}
					if (fieldNames[i].toLowerCase().compareTo("gestion") == 0) {
					    gestion_idx = i;
					}
					if (fieldNames[i].toLowerCase().compareTo("senaliza") == 0) {
					    senaliza_idx = i;
					}
					if (fieldNames[i].toLowerCase().compareTo("firme") == 0) {
					    firme_idx = i;
					}
					if (fieldNames[i].toLowerCase().compareTo("estado") == 0) {
					    estado_idx = i;
					}
					if (fieldNames[i].toLowerCase().compareTo("ancho") == 0) {
					    ancho_idx = i;
					}
					if (fieldNames[i].toLowerCase().compareTo("longitud") == 0) {
					    longitud_idx = i;
					}
					if (fieldNames[i].toLowerCase().compareTo("pasos_nive") == 0) {
					    pasos_nive_idx = i;
					}
					if (fieldNames[i].toLowerCase().compareTo("dimensiona") == 0) {
					    dimensiona_idx = i;
					}
					if (fieldNames[i].toLowerCase().compareTo("muy_sinuos") == 0) {
					    muy_sinuos_idx = i;
					}
					if (fieldNames[i].toLowerCase().compareTo("pte_excesi") == 0) {
					    pte_excesi_idx = i;
					}
					if (fieldNames[i].toLowerCase().compareTo("fre_estrec") == 0) {
					    fre_estrec_idx = i;
					}
			    }
			} catch (com.hardcode.gdbms.engine.data.driver.DriverException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
	   }
			    
}
