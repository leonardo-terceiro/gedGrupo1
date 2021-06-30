package br.upf.topicos.especiais.ged.grupo1.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.upf.topicos.especiais.ged.grupo1.entity.ModalidadeSubEventoEntity;
import br.upf.topicos.especiais.ged.grupo1.entity.ParticipacaoEntity;
import br.upf.topicos.especiais.ged.grupo1.entity.PessoaEntity;
import br.upf.topicos.especiais.ged.grupo1.utils.GenericDao;
import br.upf.topicos.especiais.ged.grupo1.utils.JpaUtil;
import br.upf.topicos.especiais.ged.grupo1.utils.JsfUtil;
import br.upf.topicos.especiais.ged.grupo1.utils.TrataException;
import lombok.Data;

@Data
@Named
@ViewScoped
public class ParticipacaoBean implements Serializable{
	
	private static final long serialVersionUID = 8925546340357663722L;

	private ParticipacaoEntity selecionado;
	private List<ParticipacaoEntity> lista;
	private Boolean editando;
	private GenericDao<ParticipacaoEntity> dao = new GenericDao<ParticipacaoEntity>();
	
	public ParticipacaoBean() {
		super();
		setEditando(false);
		carregarLista();
	}
	
	public void incluir() {
		selecionado = new ParticipacaoEntity();
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
			Query qry = em.createQuery("DELETE from ParticipacaoEntity p WHERE p.id = :id");
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
			lista = dao.createQuery("from ParticipacaoEntity order by id");
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addErrorMessage(TrataException.getMensagem(e)); 
		}			
	}	
	
	@SuppressWarnings("unchecked")
	public List<PessoaEntity> carregarPessoas(String query) {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		List<PessoaEntity> results = em.createQuery(" from PessoaEntity where upper(nome) like " + "'" + query.trim().toUpperCase() + "%'" + " order by id").getResultList();
		em.close();
		return results;
	}
	
	@SuppressWarnings("unchecked")
	public List<ModalidadeSubEventoEntity> carregarModalidadesSubEvento(String query) {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		List<ModalidadeSubEventoEntity> results = em.createQuery(" from ModalidadeSubEventoEntity order by id").getResultList();
		em.close();
		return results;
	}
}
