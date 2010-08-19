package es.udc.cartolab.gvsig.eielforms.subforms;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListModel;

import com.iver.utiles.swing.threads.SwingWorker;

import es.udc.cartolab.gvsig.eielforms.domain.Domain;
import es.udc.cartolab.gvsig.eielforms.domain.UserDomain;
import es.udc.cartolab.gvsig.eielforms.field.ComboFieldInterface;
import es.udc.cartolab.gvsig.eielforms.field.FieldController;
import es.udc.cartolab.gvsig.eielforms.field.FieldInterface;
import es.udc.cartolab.gvsig.eielforms.formgenerator.FormException;
import es.udc.cartolab.gvsig.eielforms.util.CampoVO;
import es.udc.cartolab.gvsig.eielforms.util.ClaveForaneaVO;
import es.udc.cartolab.gvsig.eielforms.util.ObtenerDominioDAO;
import es.udc.cartolab.gvsig.eielforms.util.OtrosDatosVO;

public class PanelTipos extends JPanel
{
  private ArrayList entityIds;
  private FieldInterface field;
  private UserDomain domain;
  private ClaveForaneaVO claveForanea;
  private ObtenerDominioDAO obtenerDominioDAO;
  private String database;
  private String tabla;
  private SubFormController subformController;
  private JButton jButton1;
  private JButton jButton2;
  private JButton jButton3;
  private JButton jButton4;
  private JList jList1;
  private JList jList2;
  private JScrollPane jScrollPane1;
  private JScrollPane jScrollPane2;
  private JLabel jLabel1;
  private JLabel jLabel2;
  private MouseAdapter eventButton1;
  private MouseAdapter eventButton2;
  private MouseAdapter eventButton3;
  private MouseAdapter eventButton4;

  public PanelTipos(SubFormController subformController, FieldInterface field, ClaveForaneaVO claveForanea)
  {
    this.subformController = subformController;
    this.claveForanea = claveForanea;
    this.field = field;

    setBorder(BorderFactory.createTitledBorder("Tipos"));
    initComponents();
    ComboFieldInterface combo = (ComboFieldInterface)field;
    this.domain = combo.getDomain();

    this.database = subformController.getDataBase();
    this.tabla = subformController.getTable();
    try {
		this.obtenerDominioDAO = new ObtenerDominioDAO();
	} catch (FormException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    reasignarDominiosIniciales();
  }

  private void initComponents()
  {
    this.jScrollPane1 = new JScrollPane();
    this.jList1 = new JList();
    this.jScrollPane2 = new JScrollPane();
    this.jList2 = new JList();
    this.jButton1 = new JButton();
    this.jButton2 = new JButton();
    this.jButton3 = new JButton();
    this.jButton4 = new JButton();
    this.jLabel1 = new JLabel();
    this.jLabel2 = new JLabel();

    this.jList1.setModel(new AbstractListModel() { String[] strings;

      public int getSize() { return this.strings.length; } 
      public Object getElementAt(int i) { return this.strings[i];
      }
    });
    this.jScrollPane1.setViewportView(this.jList1);

    this.jList2.setModel(new AbstractListModel() { String[] strings;

      public int getSize() { return this.strings.length; } 
      public Object getElementAt(int i) { return this.strings[i];
      }
    });
    this.jScrollPane2.setViewportView(this.jList2);

    this.jButton1.setText(">");

    this.jButton2.setText("<");

    this.jButton3.setText(">>");

    this.jButton4.setText("<<");

    this.jLabel1.setText("Asignados");

    this.jLabel2.setText("Disponibles");

    GroupLayout layout = new GroupLayout(this);
    setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(25, 25, 25).addComponent(this.jScrollPane1, -2, 128, -2).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jButton1, -1, -1, 32767).addComponent(this.jButton2, -1, -1, 32767).addComponent(this.jButton3, -1, -1, 32767).addComponent(this.jButton4, -1, -1, 32767)).addGap(22, 22, 22).addComponent(this.jScrollPane2, -2, 121, -2).addContainerGap(32, 32767)).addGroup(layout.createSequentialGroup().addGap(61, 61, 61).addComponent(this.jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 140, 32767).addComponent(this.jLabel2).addGap(116, 116, 116)));

    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(88, 88, 88).addComponent(this.jButton1).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jButton2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jButton3).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jButton4)).addGroup(layout.createSequentialGroup().addGap(49, 49, 49).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.jLabel2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane2, -2, -1, -2).addComponent(this.jScrollPane1, -2, -1, -2)))).addContainerGap(42, 32767)));
  }

  public void borrarDatos()
  {
    try {
		this.obtenerDominioDAO.borrarDatos(this.database, this.tabla, this.claveForanea);
	} catch (FormException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }

  public void insertarAsignados(Vector datos) {
    for (int i = 0; i < datos.size(); ++i)
		try {
			this.obtenerDominioDAO.insertarDatos(this.domain, this.field.getField().getName(), (OtrosDatosVO)datos.get(i), this.database, this.tabla, this.claveForanea);
		} catch (FormException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }

  public Domain getDomain()
  {
    return this.domain;
  }

  public Vector obtenerDatosIniciales(SubForm subform, Vector datos, String nombreCampoPrimario) {
    Vector nombreCampos = new Vector();
    if (subform.getFields().size() != 0)
    {
      for (int i = 0; i < subform.getFields().size(); ++i) {
        FieldInterface field = (FieldInterface)subform.getFields().get(i);
        FieldController fieldControl = field.getField();
        CampoVO campo = new CampoVO();
        campo.setNombre(fieldControl.getName());
        nombreCampos.add(campo);
      }

    }

    //TODO repasar esto, no puede estar bien ni de lejos (los valores no los coge de BD!)
    for (int i = 0; i < this.jList1.getModel().getSize(); ++i) {
      String valorCampoPrimario = (String)this.jList1.getModel().getElementAt(i);
      OtrosDatosVO campos = new OtrosDatosVO();
      if (nombreCampos.size() != 0) {
        try {
			campos = this.obtenerDominioDAO.obtenerDatosIniciales(this.domain, nombreCampos, nombreCampoPrimario, valorCampoPrimario, this.database, this.tabla, this.claveForanea);
		} catch (FormException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      } else {
        campos.setCodigo(valorCampoPrimario);
        campos.setDatos(new Vector());
      }
      datos.add(campos);
    }

    return datos;
  }

  public void reasignarDominiosIniciales()
  {
    Vector dominiosAsignados;
	try {
		dominiosAsignados = this.obtenerDominioDAO.obtenerDominiosAsignados(this.field.getField().getName(), this.field.getField().getDomain().getName(), this.database, this.tabla, this.claveForanea);
    Vector nomDominiosAsignados = new Vector();
    for (int i = 0; i < dominiosAsignados.size(); ++i) {
      nomDominiosAsignados.add(this.domain.resolve((String)dominiosAsignados.get(i)));
    }

    this.jList1.setListData(nomDominiosAsignados);
	} catch (FormException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

    Vector domains = new Vector();
    for (int i = 0; i < this.domain.getValues().size(); ++i) {
      domains.add(this.domain.getValues().get(i));
    }

    for (int i = 0; i < this.jList1.getModel().getSize(); ++i) {
      String item = (String)this.jList1.getModel().getElementAt(i);
      if (!(domains.contains(item))) continue; domains.remove(item);
    }
    this.jList2.setListData(domains);
  }

  private void jButtonDesasignarMouseClicked(MouseEvent evt)
  {
    Vector valoresList2 = new Vector();
    Vector valoresSeleccionados = new Vector();
    Vector valoresFinalesList2 = new Vector();
    Vector vacio = new Vector();

    int[] indicesValoresSeleccionados = this.jList1.getSelectedIndices();
    int indexValSele = 0;
    for (int i = 0; i < indicesValoresSeleccionados.length; ++i) {
      String nombre = (String)this.jList1.getModel().getElementAt(indicesValoresSeleccionados[i]);
      valoresSeleccionados.add(nombre);
      this.subformController.getInterface().eliminarDatos(nombre);

      ++indexValSele;
    }

    Vector list1Vector = new Vector();

    for (int i = 0; i < this.jList1.getModel().getSize(); ++i) {
      list1Vector.add(this.jList1.getModel().getElementAt(i));
    }

    for (int j = 0; j < valoresSeleccionados.size(); ++j) {
      if (list1Vector.contains(valoresSeleccionados.get(j))) {
        int posicion = list1Vector.indexOf(valoresSeleccionados.get(j));
        list1Vector.remove(posicion);
      }

    }

    this.jList1.setListData(list1Vector);

    for (int i = 0; i < this.jList2.getModel().getSize(); ++i) {
      valoresList2.add(this.jList2.getModel().getElementAt(i));
    }

    for (int i = 0; i < valoresSeleccionados.size(); ++i)
      valoresList2.add(valoresSeleccionados.get(i));
    this.jList2.setListData(valoresList2);
  }

  private void jButtonAsignarMouseClicked(MouseEvent evt)
  {
    SwingWorker sw = new SwingWorker()
    {
      public Object construct()
      {
        Vector valoresList1 = new Vector();
        Vector valoresSeleccionados = new Vector();
        Vector valoresFinalesList2 = new Vector();
        Vector vacio = new Vector();

        int[] indicesValoresSeleccionados = PanelTipos.this.jList2.getSelectedIndices();

        for (int i = 0; i < indicesValoresSeleccionados.length; ++i) {
          String nombre = (String)PanelTipos.this.jList2.getModel().getElementAt(indicesValoresSeleccionados[i]);
          PanelTipos.this.subformController.getInterface().setItem(nombre);
          while ((i > 0) && (!(PanelTipos.this.subformController.getInterface().getProcesado())));
          PanelTipos.this.subformController.getInterface().insertarDatosParaDisponibles(nombre);
//          if (PanelTipos.this.subformController.getInterface().getSubForm().getFields().size() != 0) {
//            PanelTipos.this.subformController.getInterface().setProcesado(false);
//          }
          while (!(PanelTipos.this.subformController.getInterface().getProcesado()));
          if (PanelTipos.this.subformController.getInterface().getAsignar()) {
            valoresSeleccionados.add(PanelTipos.this.subformController.getInterface().getItem());
          }

        }

        Vector list2Vector = new Vector();

        for (int i = 0; i < PanelTipos.this.jList2.getModel().getSize(); ++i) {
          list2Vector.add(PanelTipos.this.jList2.getModel().getElementAt(i));
        }

        for (int j = 0; j < valoresSeleccionados.size(); ++j) {
          if (list2Vector.contains(valoresSeleccionados.get(j))) {
            int posicion = list2Vector.indexOf(valoresSeleccionados.get(j));
            list2Vector.remove(posicion);
          }
        }

        PanelTipos.this.jList2.setListData(list2Vector);

        for (int i = 0; i < PanelTipos.this.jList1.getModel().getSize(); ++i) {
          valoresList1.add(PanelTipos.this.jList1.getModel().getElementAt(i));
        }

        for (int j = 0; j < valoresSeleccionados.size(); ++j) {
          valoresList1.add(valoresSeleccionados.get(j));
        }

        PanelTipos.this.jList1.setListData(valoresList1);

        return null;
      }

    };
    sw.start();
  }

  private void jButtonDesasignarTodosMouseClicked(MouseEvent evt)
  {
    Vector valoresList1 = new Vector();
    Vector valoresList2 = new Vector();
    Vector vacio = new Vector();

    for (int i = 0; i < this.jList1.getModel().getSize(); ++i) {
      String nombre = (String)this.jList1.getModel().getElementAt(i);
      valoresList1.add(nombre);
      this.subformController.getInterface().eliminarDatos(nombre);
    }
    this.jList1.setListData(vacio);

    for (int i = 0; i < this.jList2.getModel().getSize(); ++i) {
      valoresList2.add(this.jList2.getModel().getElementAt(i));
    }

    for (int j = 0; j < valoresList1.size(); ++j) {
      valoresList2.add(valoresList1.get(j));
    }

    this.jList2.setListData(valoresList2);
  }

  private void jButtonAsignarTodosMouseClicked(MouseEvent evt)
  {
    SwingWorker sw = new SwingWorker()
    {
      public Object construct()
      {
        Vector valoresSeleccionados = new Vector();
        Vector valoresList1 = new Vector();
        Vector valoresList2 = new Vector();

        for (int i = 0; i < PanelTipos.this.jList1.getModel().getSize(); ++i) {
          valoresList1.add(PanelTipos.this.jList1.getModel().getElementAt(i));
        }

        for (int i = 0; i < PanelTipos.this.jList2.getModel().getSize(); ++i) {
          valoresList2.add(PanelTipos.this.jList2.getModel().getElementAt(i));
        }

        for (int i = 0; i < PanelTipos.this.jList2.getModel().getSize(); ++i) {
          String nombre = (String)PanelTipos.this.jList2.getModel().getElementAt(i);
          PanelTipos.this.subformController.getInterface().setItem(nombre);
          while ((i > 0) && (!(PanelTipos.this.subformController.getInterface().getProcesado())));
          PanelTipos.this.subformController.getInterface().insertarDatosParaDisponibles(nombre);
          PanelTipos.this.subformController.getInterface().setProcesado(false);
          while (!(PanelTipos.this.subformController.getInterface().getProcesado()));
          if (PanelTipos.this.subformController.getInterface().getAsignar()) {
            valoresSeleccionados.add(PanelTipos.this.subformController.getInterface().getItem());
          }
        }
        for (int j = 0; j < valoresSeleccionados.size(); ++j) {
          if (valoresList2.contains(valoresSeleccionados.get(j))) {
            int posicion = valoresList2.indexOf(valoresSeleccionados.get(j));
            valoresList2.remove(posicion);
          }
        }
        PanelTipos.this.jList2.setListData(valoresList2);

        for (int i = 0; i < valoresSeleccionados.size(); ++i) {
          valoresList1.add(valoresSeleccionados.get(i));
        }
        PanelTipos.this.jList1.setListData(valoresList1);

        return null;
      }
    };
    sw.start();
  }

  public JList getJListAsignados()
  {
    return this.jList1;
  }

  public void deshabilitar()
  {
    this.jButton1.setEnabled(false);
    this.jButton2.setEnabled(false);
    this.jButton3.setEnabled(false);
    this.jButton4.setEnabled(false);
    this.jButton1.removeMouseListener(this.eventButton1);
    this.jButton2.removeMouseListener(this.eventButton2);
    this.jButton3.removeMouseListener(this.eventButton3);
    this.jButton4.removeMouseListener(this.eventButton4);
    setEnabled(false);
  }

  public void habilitarBotones()
  {
    this.eventButton1 = new MouseAdapter() {
      public void mouseClicked(MouseEvent evt) {
        PanelTipos.this.jButtonDesasignarMouseClicked(evt);
      }
    };
    this.jButton1.addMouseListener(this.eventButton1);

    this.eventButton2 = new MouseAdapter() {
      public void mouseClicked(MouseEvent evt) {
        PanelTipos.this.jButtonAsignarMouseClicked(evt);
      }
    };
    this.jButton2.addMouseListener(this.eventButton2);

    this.eventButton3 = new MouseAdapter() {
      public void mouseClicked(MouseEvent evt) {
        PanelTipos.this.jButtonDesasignarTodosMouseClicked(evt);
      }
    };
    this.jButton3.addMouseListener(this.eventButton3);

    this.eventButton4 = new MouseAdapter() {
      public void mouseClicked(MouseEvent evt) {
        PanelTipos.this.jButtonAsignarTodosMouseClicked(evt);
      }
    };
    this.jButton4.addMouseListener(this.eventButton4);

    this.jButton1.setEnabled(true);
    this.jButton2.setEnabled(true);
    this.jButton3.setEnabled(true);
    this.jButton4.setEnabled(true);
  }
}