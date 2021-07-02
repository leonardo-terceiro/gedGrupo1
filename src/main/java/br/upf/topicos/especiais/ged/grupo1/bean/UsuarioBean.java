package br.upf.topicos.especiais.ged.grupo1.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.upf.topicos.especiais.ged.grupo1.entity.UsuarioEntity;
import br.upf.topicos.especiais.ged.grupo1.util.GenericDao;
import br.upf.topicos.especiais.ged.grupo1.util.JsfUtil;
import br.upf.topicos.especiais.ged.grupo1.util.TrataException;
import lombok.Data;

@Data
@Named
@ViewScoped
public class UsuarioBean implements Serializable{

	private static final long serialVersionUID = 7762788662036954339L;

	private UsuarioEntity selecionado;
	private List<UsuarioEntity> lista;
	private Boolean editando;
	private Boolean ativo;
	private GenericDao<UsuarioEntity> dao = new GenericDao<UsuarioEntity>();
	
	public UsuarioBean() {
		super();
		setEditando(false);
		carregarLista();
		ativo = false;
	}
	
	public void incluir() {
		selecionado = new UsuarioEntity();
		setEditando(true);
		ativo = true;
	}

	public void alterar() {
		setEditando(true);
		ativo = true;
	}
	
	public void cancelar() {
		carregarLista();
		setSelecionado(null);
		setEditando(false);
		ativo = false;
	}
	
	public void salvar() {
		try {
			if(Boolean.FALSE.equals(ativo)) {
				selecionado.setDataInativo(new Date());
			}else {
				selecionado.setDataInativo(null);
			}
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
			lista = dao.createQuery("from UsuarioEntity order by id");
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addErrorMessage(TrataException.getMensagem(e)); 
		}			
	}
	
}
