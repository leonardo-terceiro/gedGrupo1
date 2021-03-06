package br.upf.topicos.especiais.ged.grupo1.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.model.StreamedContent;

import br.upf.topicos.especiais.ged.grupo1.entity.TipoEventoEntity;
import br.upf.topicos.especiais.ged.grupo1.util.GenericDao;
import br.upf.topicos.especiais.ged.grupo1.util.JsfUtil;
import br.upf.topicos.especiais.ged.grupo1.util.RelatorioUtil;
import br.upf.topicos.especiais.ged.grupo1.util.TrataException;
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
			setSelecionado(dao.merge(selecionado));
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
			lista = dao.createQuery("from TipoEventoEntity order by id");
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addErrorMessage(TrataException.getMensagem(e));
		}
	}

	@SuppressWarnings("rawtypes")
	public StreamedContent gerarPDF() {
		try {
			HashMap parameters = new HashMap<>();
			return RelatorioUtil.gerarStreamRelatorioPDF("relatorios/tipoEventoRelatorio.jasper", parameters,
					"TipoEvento.pdf");
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addErrorMessage(e.getMessage());
			return null;
		}
	}

}
