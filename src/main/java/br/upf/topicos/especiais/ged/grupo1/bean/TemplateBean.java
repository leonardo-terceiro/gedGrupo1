package br.upf.topicos.especiais.ged.grupo1.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.model.StreamedContent;

import br.upf.topicos.especiais.ged.grupo1.entity.TemplateEntity;
import br.upf.topicos.especiais.ged.grupo1.util.GenericDao;
import br.upf.topicos.especiais.ged.grupo1.util.JsfUtil;
import br.upf.topicos.especiais.ged.grupo1.util.RelatorioUtil;
import br.upf.topicos.especiais.ged.grupo1.util.TrataException;
import lombok.Data;

@Data
@Named
@ViewScoped
public class TemplateBean implements Serializable{
	
	private static final long serialVersionUID = 8276065184316704169L;

	private TemplateEntity selecionado;
	private List<TemplateEntity> lista;
	private Boolean editando;
	private GenericDao<TemplateEntity> dao = new GenericDao<TemplateEntity>();
	
	public TemplateBean() {
		super();
		setEditando(false);
		carregarLista();
	}
	
	public void incluir() {
		selecionado = new TemplateEntity();
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
			lista = dao.createQuery("from TemplateEntity order by id");
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addErrorMessage(TrataException.getMensagem(e)); 
		}			
	}
	
	@SuppressWarnings("rawtypes")
	public StreamedContent gerarPDF() {
		try {
			HashMap parameters = new HashMap<>();
			return RelatorioUtil.gerarStreamRelatorioPDF("relatorios/templateRelatorio.jasper", parameters,
					"Templates.pdf");
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addErrorMessage(e.getMessage());
			return null;
		}
	}
}
