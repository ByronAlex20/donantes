package ec.edu.upse.controlador;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import ec.edu.upse.modelo.Parametrica;
import ec.edu.upse.modelo.ParametricaDAO;
import ec.edu.upse.modelo.Persona;


public class DestinatariosLista extends SelectorComposer{
	@Wire private Combobox cboProvincia;
	@Wire private Window winDestinatarioLista;
	List<Persona> donante;
	ParametricaDAO parametricaDAO = new ParametricaDAO();
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
	}
	
	@Listen("onClick=#btnBuscar")
	public void buscarDestinatarios() {
		//System.out.println("Buscar");
	}

	
	@Listen("onClick=#btnSalir")
	public void salir(){
		winDestinatarioLista.detach();
	}
	
	
	 public List<Parametrica> getProvincias(){
		return parametricaDAO.getProvincias().get(0).getParametricas();
	}


	
	
	public List<Persona> getDonante() {
		return donante;
	}


	public void setDonante(List<Persona> donante) {
		this.donante = donante;
	}	
}
