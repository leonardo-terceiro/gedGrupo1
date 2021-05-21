package br.upf.topicos.especiais.ged.grupo1.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.upf.topicos.especiais.ged.grupo1.entity.TipoEventoEntity;
import br.upf.topicos.especiais.ged.grupo1.utils.GenericDao;
import br.upf.topicos.especiais.ged.grupo1.utils.JsfUtil;
import br.upf.topicos.especiais.ged.grupo1.utils.TrataException;
import lombok.Data;

@Data
@Named
@ViewScoped
public class TipoEventoBean implements Serializable {

	private static final long serialVersionUID = -4789151453766347484L;

	private TipoEventoEntity selecionado;
	private List<TipoEventoEntity> lista;
	private Boolean editando;

	private GenericDao<TipoEventoEntity> dao = new GenericDao<>();

	public TipoEventoBean() {
		super();
		setEditando(false);
		carregarLista();
	}
	
	public void incluir() {
		selecionado = new TipoEventoEntity(); 
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
			dao.remove(selecionado);
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
			lista = dao.createQuery("from TipoEvento order by id");
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addErrorMessage(TrataException.getMensagem(e)); 
		}			
	}	
	


}