package br.upf.topicos.especiais.ged.grupo1.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.upf.topicos.especiais.ged.grupo1.entity.ModalidadeEntity;
import br.upf.topicos.especiais.ged.grupo1.entity.ModalidadeSubEventoEntity;
import br.upf.topicos.especiais.ged.grupo1.entity.SubEventoEntity;
import br.upf.topicos.especiais.ged.grupo1.entity.TemplateEntity;
import br.upf.topicos.especiais.ged.grupo1.utils.GenericDao;
import br.upf.topicos.especiais.ged.grupo1.utils.JpaUtil;
import br.upf.topicos.especiais.ged.grupo1.utils.JsfUtil;
import br.upf.topicos.especiais.ged.grupo1.utils.TrataException;
import lombok.Data;

@Data
@Named
@ViewScoped
public class ModalidadeSubEventoBean implements Serializable{
	
	private static final long serialVersionUID = -138402691449651120L;
	
	private ModalidadeSubEventoEntity selecionado;
	private List<ModalidadeSubEventoEntity> lista;
	private Boolean editando;
	private GenericDao<ModalidadeSubEventoEntity> dao = new GenericDao<ModalidadeSubEventoEntity>();
	
	public ModalidadeSubEventoBean() {
		super();
		setEditando(false);
		carregarLista();
	}
	
	public void incluir() {
		selecionado = new ModalidadeSubEventoEntity();
		setEditando(true);
	}

	public void alterar() {
		setEditando(true);
	}
	
	public void cancelar() {
		carregarLista();
		setSelecionado(null);
		setEditando(false);
	}
	
	public void salvar() {
		try {
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
		try {
			EntityManager em = JpaUtil.getInstance().getEntityManager();
			em.getTransaction().begin();
			Query qry = em.createQuery("DELETE from ModalidadeSubEventoEntity m WHERE m.id = :id");
			qry.setParameter("id", selecionado.getId());
			qry.executeUpdate();
			em.getTransaction().commit();
			JsfUtil.addSuccessMessage("Excluído com sucesso!");
			setSelecionado(null);
			carregarLista();
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addErrorMessage(TrataException.getMensagem(e));
		}
	}
	
	public void carregarLista() {
		try {
			lista = dao.createQuery("from ModalidadeSubEventoEntity order by id");
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addErrorMessage(TrataException.getMensagem(e)); 
		}			
	}
	
	@SuppressWarnings("unchecked")
	public List<SubEventoEntity> carregarSubEventos(String query) {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		List<SubEventoEntity> results = em.createQuery(" from SubEventoEntity where upper(titulo) like " + "'" + query.trim().toUpperCase() + "%'" + " order by id").getResultList();
		em.close();
		return results;
	}
	
	@SuppressWarnings("unchecked")
	public List<ModalidadeEntity> carregarModalidades(String query) {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		List<ModalidadeEntity> results = em.createQuery(" from ModalidadeEntity where upper(descricao) like " + "'" + query.trim().toUpperCase() + "%'" + " order by id").getResultList();
		em.close();
		return results;
	}

	@SuppressWarnings("unchecked")
	public List<TemplateEntity> carregarTemplates(String query) {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		List<TemplateEntity> results = em.createQuery(" from TemplateEntity where upper(descricao) like " + "'" + query.trim().toUpperCase() + "%'" + " order by id").getResultList();
		em.close();
		return results;
	}
	
}
