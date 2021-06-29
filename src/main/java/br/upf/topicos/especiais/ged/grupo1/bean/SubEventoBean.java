package br.upf.topicos.especiais.ged.grupo1.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.upf.topicos.especiais.ged.grupo1.entity.EventoEntity;
import br.upf.topicos.especiais.ged.grupo1.entity.SubEventoEntity;
import br.upf.topicos.especiais.ged.grupo1.entity.TipoEventoEntity;
import br.upf.topicos.especiais.ged.grupo1.utils.GenericDao;
import br.upf.topicos.especiais.ged.grupo1.utils.JpaUtil;
import br.upf.topicos.especiais.ged.grupo1.utils.JsfUtil;
import br.upf.topicos.especiais.ged.grupo1.utils.TrataException;
import lombok.Data;

@Data
@Named
@ViewScoped
public class SubEventoBean implements Serializable{
	
	private static final long serialVersionUID = 6093073375033123978L;

	private SubEventoEntity selecionado;
	private List<SubEventoEntity> lista;
	private Boolean editando;
	private GenericDao<SubEventoEntity> dao = new GenericDao<SubEventoEntity>();
	
	public SubEventoBean() {
		super();
		setEditando(false);
		carregarLista();
		selecionado = new SubEventoEntity();
		System.out.println("SubEventoBean() - construtor - selecionado " + selecionado);
		
	}
	
	public void incluir() {
		selecionado = new SubEventoEntity();
		setEditando(true);
		System.out.println("SubEventoBean - incluir()");
	}

	public void alterar() {
		setEditando(true);
		System.out.println("SubEventoBean - alterar()");
	}
	
	public void cancelar() {
		carregarLista();
		setSelecionado(null);
		setEditando(false);
		System.out.println("SubEventoBean - cancelar()");
	}
	
	public void salvar() {
		try {
			System.out.println("SubEventoBean - salvar() - salvando -> " + selecionado);
			setSelecionado( dao.merge(selecionado) );
			JsfUtil.addSuccessMessage("Salvo com sucesso!");
			carregarLista();
			setEditando(false);
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addErrorMessage(TrataException.getMensagem(e));
		}
	}
	
	public void excluir() {
		System.out.println("SubEventoBean - excluir() - " + selecionado);
		try {
//			dao.remove(selecionado);
//			JsfUtil.addSuccessMessage("Excluído com sucesso!");
			EntityManager em = JpaUtil.getInstance().getEntityManager();
			em.getTransaction().begin();
			Query qry = em.createQuery("DELETE from SubEventoEntity s WHERE s.id = :id");
			qry.setParameter("id", selecionado.getId());
			qry.executeUpdate();
			em.getTransaction().commit();
			setSelecionado(new SubEventoEntity());
			carregarLista();
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addErrorMessage(TrataException.getMensagem(e));
			System.out.println("Error " + e.getMessage());
		}
	}
	
	public void carregarLista() {
		System.out.println("SubEventoBean - carregarLista()");
		try {
			lista = dao.createQuery("from SubEventoEntity order by id");
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addErrorMessage(TrataException.getMensagem(e)); 
		}			
	}
	
	@SuppressWarnings("unchecked")
	public List<EventoEntity> completeEvento(String query) {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		List<EventoEntity> results = em.createQuery(" from EventoEntity where upper(titulo) like " + "'" + query.trim().toUpperCase() + "%'" + " order by id").getResultList();
		em.close();
		return results;
	}
	
	@SuppressWarnings("unchecked")
	public List<TipoEventoEntity> completeTipoEvento(String query) {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		List<TipoEventoEntity> results = em.createQuery(" from TipoEventoEntity where upper(descricao) like " + "'" + query.trim().toUpperCase() + "%'" + " order by id").getResultList();
		em.close();
		return results;
	}
}
