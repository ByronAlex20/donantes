package ec.edu.upse.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class PersonaDAO extends ClaseDAO{
	@SuppressWarnings("unchecked")
	public List<Persona> getPersona(String value) {
		List<Persona> resultado = new ArrayList<Persona>(); 
		String patron = value;
		Query query = getEntityManager().createNamedQuery("Persona.buscarPorCedula");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("patron", patron);
		resultado = (List<Persona>) query.getResultList();
		return resultado;
	}
	@SuppressWarnings("unchecked")
	public List<Persona> getPersonaBuscar(Integer ciudad,Integer sangre) {
		List<Persona> resultado = new ArrayList<Persona>(); 
		Query query = getEntityManager().createNamedQuery("Persona.buscarPorPatron");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("ciudad", ciudad);
		query.setParameter("sangre", sangre);
		resultado = (List<Persona>) query.getResultList();
		return resultado;
	}
	
	@SuppressWarnings("unchecked")
	public List<Persona> getPersonaBuscarTodos(Integer provincia,Integer sangre) {
		List<Persona> resultado = new ArrayList<Persona>(); 
		Query query = getEntityManager().createNamedQuery("Persona.buscarPorProvincia");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("provincia", provincia);
		query.setParameter("sangre", sangre);
		resultado = (List<Persona>) query.getResultList();
		return resultado;
	}
}
