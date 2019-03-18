package ec.edu.upse.controlador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Window;

import ec.edu.upse.modelo.Parametrica;
import ec.edu.upse.modelo.ParametricaDAO;
import ec.edu.upse.modelo.Persona;

public class EnvioCorreo extends SelectorComposer<Component> {
	
	@Wire private Window winEnvioCorreos;
	@Wire private Combobox cboProvincia;
	List<Persona> donante;
	ParametricaDAO parametricaDAO = new ParametricaDAO();
	
	
	@Listen("onClick=#btnBuscarDestinat")
	public void buscarListadoDestinatarios() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("VentanaPadre", this);
		Window ventanaCargar = (Window) Executions.createComponents("/principal/destinatarioLista.zul", winEnvioCorreos, params);
		ventanaCargar.doModal();
	}
	
	
	
}
