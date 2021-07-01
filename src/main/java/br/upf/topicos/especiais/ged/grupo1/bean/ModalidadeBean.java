package br.upf.topicos.especiais.ged.grupo1.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.model.StreamedContent;

import br.upf.topicos.especiais.ged.grupo1.entity.ModalidadeEntity;
import br.upf.topicos.especiais.ged.grupo1.utils.GenericDao;
import br.upf.topicos.especiais.ged.grupo1.utils.JsfUtil;
import br.upf.topicos.especiais.ged.grupo1.utils.RelatorioUtil;
import br.upf.topicos.especiais.ged.grupo1.utils.TrataException;
import lombok.Data;

@Data
@Named
@ViewScoped
public class ModalidadeBean implements Serializable{
	
	private static final long serialVersionUID = -479002665201295664L;

	private ModalidadeEntity selecionado;
	private List<ModalidadeEntity> lista;
	private Boolean editando;
	private GenericDao<ModalidadeEntity> dao = new GenericDao<ModalidadeEntity>();
	
	public ModalidadeBean() {
		super();
		setEditando(false);
		carregarLista();
	}
	
	public void incluir() {
		System.out.println("Incluindo");
		selecionado = new ModalidadeEntity();
		System.out.println("Selectionado 1 -> " + selecionado);
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
			System.out.println("selecionado -> " + selecionado);
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
			lista = dao.createQuery("from ModalidadeEntity order by id");
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addErrorMessage(TrataException.getMensagem(e)); 
		}			
	}	
	
	@SuppressWarnings("rawtypes")
	public StreamedContent gerarPDF() {
		try {
			HashMap parameters = new HashMap<>();
			return RelatorioUtil.gerarStreamRelatorioPDF("relatorios/modalidadeRelatorio.jasper", parameters,
					"Modalidades.pdf");
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addErrorMessage(e.getMessage());
			return null;
		}
	}
}
