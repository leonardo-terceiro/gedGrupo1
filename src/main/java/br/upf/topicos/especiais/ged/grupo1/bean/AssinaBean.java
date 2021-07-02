package br.upf.topicos.especiais.ged.grupo1.bean;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.upf.topicos.especiais.ged.grupo1.entity.AssinaEntity;
import br.upf.topicos.especiais.ged.grupo1.util.GenericDao;
import br.upf.topicos.especiais.ged.grupo1.util.JpaUtil;
import br.upf.topicos.especiais.ged.grupo1.util.JsfUtil;
import br.upf.topicos.especiais.ged.grupo1.util.RelatorioUtil;
import br.upf.topicos.especiais.ged.grupo1.util.TrataException;
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
		try {
			EntityManager em = JpaUtil.getInstance().getEntityManager();
			em.getTransaction().begin();
			Query qry = em.createQuery("UPDATE AssinaEntity a SET a.dataInativo = :data WHERE a.id = :id");
			qry.setParameter("data", null);
			qry.setParameter("id", selecionado.getId());
			qry.executeUpdate();
			em.getTransaction().commit();
			setSelecionado(null);
			carregarLista();
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addErrorMessage(TrataException.getMensagem(e));
		}
	}
	
	public void inativar() {
		try {
			EntityManager em = JpaUtil.getInstance().getEntityManager();
			em.getTransaction().begin();
			Query qry = em.createQuery("UPDATE AssinaEntity a SET a.dataInativo = :data WHERE a.id = :id");
			qry.setParameter("data", Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
			qry.setParameter("id", selecionado.getId());
			qry.executeUpdate();
			em.getTransaction().commit();
			setSelecionado(null);
			carregarLista();
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addErrorMessage(TrataException.getMensagem(e));
		}
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
			EntityManager em = JpaUtil.getInstance().getEntityManager();
			em.getTransaction().begin();
			Query qry = em.createQuery("DELETE from AssinaEntity a WHERE a.id = :id");
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
			lista = dao.createQuery("FROM AssinaEntity ORDER BY id");
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addErrorMessage(TrataException.getMensagem(e)); 
		}			
	}	
	
	public List<AssinaEntity> carregarAssinaturasDisponiveis() {
		try {
			return dao.createQuery("FROM AssinaEntity WHERE dataInativo IS NULL ORDER BY id");
		}catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addErrorMessage(TrataException.getMensagem(e));
		}
		return null;
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
