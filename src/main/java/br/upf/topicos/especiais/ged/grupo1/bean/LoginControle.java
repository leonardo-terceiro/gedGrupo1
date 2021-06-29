package br.upf.topicos.especiais.ged.grupo1.bean;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.upf.topicos.especiais.ged.grupo1.entity.UsuarioEntity;
import br.upf.topicos.especiais.ged.grupo1.utils.JpaUtil;
import lombok.Data;


@Data
@Named
@SessionScoped
public class LoginControle implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String email = "";
	private String senha = "";

	private UsuarioEntity usuarioLogado;

	@SuppressWarnings("unchecked")
	public String entrar() { 
		usuarioLogado = null;
		JpaUtil.getInstance();
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		String oql = "from UsuarioEntity where email = :email and senha = :senha";
		Query qry = em.createQuery(oql);
		qry.setParameter("email", this.email);
		qry.setParameter("senha", this.senha);
		List<UsuarioEntity> ret = qry.getResultList();
		em.close();
		if (ret.size() > 0) {
			usuarioLogado = ret.get(0);
			System.out.println("entrar() -> Login feito com sucesso user <" + usuarioLogado.getNome() + ">");
			return "/faces/Privado/Home.xhtml?faces-redirect=true";
			
		} else {
			FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login ou senha inválida!" , "");
			FacesContext.getCurrentInstance().addMessage(null, mensagem); 			
			return ""; 
		}
	}
	
	public String sair() {
		usuarioLogado = null;
		return "/faces/LoginForm.xhtml?faces-redirect=true";
	}	

}
