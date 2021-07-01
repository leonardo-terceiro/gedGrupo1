package br.upf.topicos.especiais.ged.grupo1.bean;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.model.StreamedContent;

import br.upf.topicos.especiais.ged.grupo1.entity.EventoEntity;
import br.upf.topicos.especiais.ged.grupo1.entity.ModalidadeSubEventoEntity;
import br.upf.topicos.especiais.ged.grupo1.entity.ParticipacaoEntity;
import br.upf.topicos.especiais.ged.grupo1.entity.SubEventoEntity;
import br.upf.topicos.especiais.ged.grupo1.utils.GenericDao;
import br.upf.topicos.especiais.ged.grupo1.utils.JsfUtil;
import br.upf.topicos.especiais.ged.grupo1.utils.RelatorioUtil;
import br.upf.topicos.especiais.ged.grupo1.utils.TrataException;
import lombok.Data;

@Data
@Named
@ViewScoped
public class EventoBean implements Serializable{
	
	private static final long serialVersionUID = 3381000002235033132L;

	private EventoEntity selecionado;
	private List<EventoEntity> lista;
	private Boolean editando;
	private GenericDao<EventoEntity> dao = new GenericDao<EventoEntity>();
	
	public EventoBean() {
		super();
		setEditando(false);
		carregarLista();
	}
	
	public void incluir() {
		selecionado = new EventoEntity();
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
			lista = dao.createQuery("from EventoEntity order by id");
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addErrorMessage(TrataException.getMensagem(e)); 
		}			
	}	
	
	@SuppressWarnings("rawtypes")
	public StreamedContent gerarPDF() {
		try {
			HashMap parameters = new HashMap<>();
			return RelatorioUtil.gerarStreamRelatorioPDF("relatorios/eventoRelatorio.jasper", parameters,
					"Eventos.pdf");
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addErrorMessage(e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public StreamedContent gerarUserPDF(Integer id) {
		try {
			System.out.println("Evento id -> " + id);
			HashMap parameters = new HashMap<>();
			parameters.put("eventoId", id);
			return RelatorioUtil.gerarStreamRelatorioPDF("relatorios/eventoParticipantesRelatorio.jasper", parameters,
					"EventoParticipacoes_" + id +".pdf");
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addErrorMessage(e.getMessage());
			return null;
		}
	}
	
	public boolean hasParticipacoes() {
		
		if(selecionado.getSubEventos() == null || selecionado.getSubEventos().isEmpty()) {
			System.out.println("hasParticipacoes() - evento <" + selecionado.getId() + "> nao possui subEventos!");
			return false;
		}
		List<ModalidadeSubEventoEntity> modalidades = selecionado.getSubEventos().stream()
				 .filter(subEvento -> subEvento != null && subEvento.getModalidades() != null && !subEvento.getModalidades().isEmpty())
				 .map(SubEventoEntity::getModalidades)
				 .flatMap(Collection::stream)
				 .collect(Collectors.toList());
		
		if(modalidades == null || modalidades.isEmpty()) {
			System.out.println("hasParticipacoes() - evento <" + selecionado.getId() + "> nao possui modalidades de sub evento!");
			return false;
		}
	
		List<ParticipacaoEntity> participacoes = modalidades.stream()
				.filter(modalidade -> modalidade.getParticipacoes() != null && !modalidade.getParticipacoes().isEmpty())
				.map(ModalidadeSubEventoEntity::getParticipacoes)
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
		
		if(participacoes == null || participacoes.isEmpty()) {
			System.out.println("hasParticipacoes() - evento <" + selecionado.getId() + "> nao possui participações!");
			return false;
		}
		System.out.println("hasParticipacoes() - evento <" + selecionado.getId() + "> possui participações!");
		return true;
	}
}
