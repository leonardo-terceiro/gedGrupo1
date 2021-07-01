package br.upf.topicos.especiais.ged.grupo1.bean;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.upf.topicos.especiais.ged.grupo1.entity.AssinaEntity;
import br.upf.topicos.especiais.ged.grupo1.utils.GenericDao;
import br.upf.topicos.especiais.ged.grupo1.utils.JsfUtil;
import br.upf.topicos.especiais.ged.grupo1.utils.RelatorioUtil;
import br.upf.topicos.especiais.ged.grupo1.utils.TrataException;
import lombok.Data;

@Data
@Named
@ViewScoped
public class AssinaBean implements Serializable{

	private static final long serialVersionUID = 2915171447207464280L;

	private AssinaEntity selecionado;
	private List<AssinaEntity> lista;
	private Boolean editando;
	private GenericDao<AssinaEntity> dao = new GenericDao<AssinaEntity>();
	
	public AssinaBean() {
		super();
		setEditando(false);
		carregarLista();
	}
	
	public void incluir() {
		selecionado = new AssinaEntity();
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
	
	public void ativar() {
		selecionado.setDataInativo(null);
		salvar();
	}
	
	public void inativar() {
		selecionado.setDataInativo(new Date());
		salvar();
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
			lista = dao.createQuery("from AssinaEntity order by id");
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addErrorMessage(TrataException.getMensagem(e)); 
		}			
	}	
	
	public void handleFileUpload(FileUploadEvent event) {
		selecionado.setImagem(event.getFile().getContent());
	}
	
	public StreamedContent getImagem() {
		if(selecionado != null && selecionado.getImagem() != null) {
			return DefaultStreamedContent.builder()
					.contentType("image/jpeg")
					.stream(() -> new ByteArrayInputStream(selecionado.getImagem()))
					.build();
		}else {
			return null;
		}
	}
	
	@SuppressWarnings("resource")
	public StreamedContent redenImagem(byte[] imagem) throws IOException {
		System.out.println("Imagem -> " + imagem);
		if(imagem != null) {
			return DefaultStreamedContent.builder()
					.contentType("image/jpeg")
					.stream(() -> new ByteArrayInputStream(imagem))
					.build();
		}else {
			String pathName = FacesContext.getCurrentInstance().getExternalContext().getRealPath("").concat("\\resources\\images\\noImage.png");
			System.out.println("Real path -> " + pathName);
			InputStream is = new BufferedInputStream(new FileInputStream(pathName));
			return DefaultStreamedContent.builder()
					.contentType("image/jpeg")
					.stream(() -> is)
					.build();
			 
		}
	}
	
	@SuppressWarnings("rawtypes")
	public StreamedContent gerarPDF() {
		try {
			HashMap parameters = new HashMap<>();
			return RelatorioUtil.gerarStreamRelatorioPDF("relatorios/assinaRelatorio.jasper", parameters,
					"Assinaturas.pdf");
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addErrorMessage(e.getMessage());
			return null;
		}
	}
}
