package ec.edu.upse.controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Include;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

import ec.edu.upse.modelo.SegMenu;
import ec.edu.upse.modelo.SegMenuDAO;
import ec.edu.upse.modelo.SegPermiso;
import ec.edu.upse.modelo.SegPermisoDAO;
import ec.edu.upse.modelo.SegUsuario;
import ec.edu.upse.modelo.SegUsuarioDAO;
import ec.edu.upse.util.SecurityUtil;


public class MenuControlador {

	@Wire Tree menu;
	@Wire Include areaContenido;
	SegMenu opcionSeleccionado;
	SegUsuarioDAO usuarioDAO = new SegUsuarioDAO();
	SegPermisoDAO permisoDAO = new SegPermisoDAO();
	SegMenuDAO menuDAO = new SegMenuDAO();
	List<SegPermiso> listaPermisosPadre = new ArrayList<SegPermiso>();
	List<SegPermiso> listaPermisosTodos = new ArrayList<SegPermiso>();
	
	@AfterCompose
	public void aferCompose(@ContextParam(ContextType.VIEW) Component view) throws IOException{
		Selectors.wireComponents(view, this, false);
		loadTree();
	}
	public void loadTree() throws IOException{		
		SegUsuario usuario = usuarioDAO.getUsuario(SecurityUtil.getUser().getUsername().trim());

		if (usuario != null){
			listaPermisosPadre = permisoDAO.getListaPermisosPadre(usuario.getSegPerfil().getIdPerfil());
			listaPermisosTodos = permisoDAO.getListaPermisosTodos(usuario.getSegPerfil().getIdPerfil());
			if (listaPermisosPadre.size() > 0) { //si tiene permisos el usuario
				//capturar solo los menus
				List<SegMenu> listaMenu = new ArrayList<SegMenu>();
				for(SegPermiso permiso : listaPermisosPadre) listaMenu.add(permiso.getSegMenu());
				menu.appendChild(getTreechildren(listaMenu));   
			}			
		}
		listaPermisosPadre = null;
	}
	private Treechildren getTreechildren(List<SegMenu> listaMenu) {
		Treechildren retorno = new Treechildren();
		for(SegMenu opcion : listaMenu) {
			Treeitem ti = getTreeitem(opcion);
			retorno.appendChild(ti);
			List<SegMenu> listaPadreHijo = new ArrayList<SegMenu>();
			for(SegPermiso permiso : listaPermisosTodos) {
				if(permiso.getSegMenu().getIdMenuPadre() == opcion.getIdMenu())
					listaPadreHijo.add(permiso.getSegMenu());
			}
			if (!listaPadreHijo.isEmpty()) {
				ti.appendChild(getTreechildren(listaPadreHijo));
			}
		}
		return retorno;
	}
	@SuppressWarnings({ "rawtypes", "deprecation", "unchecked" })
	private Treeitem getTreeitem(SegMenu opcion) {
		Treeitem retorno = new Treeitem();
		Treerow tr = new Treerow();
		Treecell tc = new Treecell(opcion.getDescripcion());
		//System.out.println("titulomenu: " + tc);
		if (opcion.getIcono() != null) {
			//tc.setIconSclass(opcion.getImagen());
			tc.setSrc(opcion.getIcono());
		}
		tr.addEventListener(Events.ON_CLICK, new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				// TODO Auto-generated method stub
				opcionSeleccionado = opcion; 
				if(opcionSeleccionado.getUrl() != null){
					loadContenido(opcionSeleccionado);
				}
			}
		});
		
		tr.appendChild(tc);
		retorno.appendChild(tr);
		retorno.setOpen(false);
		return retorno;
	}
	@NotifyChange({"areaContenido"})
	public void loadContenido(SegMenu opcion) {
		
		if (opcion.getUrl().toLowerCase().substring(0, 2).toLowerCase().equals("http")) {
			Sessions.getCurrent().setAttribute("FormularioActual", null);
			Executions.getCurrent().sendRedirect(opcion.getUrl(), "_blank");			
		} else {
			Sessions.getCurrent().setAttribute("FormularioActual", opcion);	
			areaContenido.setSrc(opcion.getUrl());
		}	
		
	}
	public String getNombreUsuario() {
		return SecurityUtil.getUser().getUsername();
	}
}
