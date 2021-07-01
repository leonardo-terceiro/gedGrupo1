package br.upf.topicos.especiais.ged.grupo1.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.model.StreamedContent;

import br.upf.topicos.especiais.ged.grupo1.entity.PessoaEntity;
import br.upf.topicos.especiais.ged.grupo1.utils.GenericDao;
import br.upf.topicos.especiais.ged.grupo1.utils.JsfUtil;
import br.upf.topicos.especiais.ged.grupo1.utils.RelatorioUtil;
import br.upf.topicos.especiais.ged.grupo1.utils.TrataException;
import lombok.Data;

@Data
@Named
@ViewScoped
public class PessoaBean implements Serializable{

	private static final long serialVersionUID = -2748962887047578836L;

	private PessoaEntity selecionado;
	private List<PessoaEntity> lista;
	private Boolean editando;
	private GenericDao<PessoaEntity> dao = new GenericDao<PessoaEntity>();
	
	public PessoaBean() {
		super();
		setEditando(false);
		carregarLista();
	}
	
	public void incluir() {
		selecionado = new PessoaEntity();
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
			lista = dao.createQuery("from PessoaEntity order by id");
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addErrorMessage(TrataException.getMensagem(e)); 
		}			
	}	
	
	@SuppressWarnings("rawtypes")
	public StreamedContent gerarPDF() {
		try {
			HashMap parameters = new HashMap<>();
			return RelatorioUtil.gerarStreamRelatorioPDF("relatorios/pessoaRelatorio.jasper", parameters,
					"Pessoas.pdf");
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addErrorMessage(e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public StreamedContent gerarUserPDF(Integer id) {
		try {
			HashMap parameters = new HashMap<>();
			parameters.put("pessoaId", id);
			return RelatorioUtil.gerarStreamRelatorioPDF("relatorios/pessoaParticipacoesRelatorio.jasper", parameters,
					"ParticipacoesPessoa_" + id +".pdf");
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addErrorMessage(e.getMessage());
			return null;
		}
	}
	
	public boolean hasParticipacoes() {
		
		if(selecionado.getParticipacoes() == null || selecionado.getParticipacoes().isEmpty()) {
			System.out.println("hasParticipacoes() - pessoa <" + selecionado.getId() + "> não possui participacoes");
			return false;
		}
		System.out.println("hasParticipacoes() - pessoa <" + selecionado.getId() + "> possui participacoes!");
		return true;
	}
	
}
