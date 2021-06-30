package br.upf.topicos.especiais.ged.grupo1.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

import br.upf.topicos.especiais.ged.grupo1.entity.PessoaEntity;
import br.upf.topicos.especiais.ged.grupo1.utils.JpaUtil;

@FacesConverter(value = "pessoaConverter")
public class PessoaConverter implements Converter<PessoaEntity>{

	@Override
	public PessoaEntity getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				EntityManager em = JpaUtil.getInstance().getEntityManager();
				PessoaEntity ret = em.find(PessoaEntity.class, Integer.parseInt(value));
				em.close();
				return ret;
			} catch (NumberFormatException e) {
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Erro de Conversao de Pessoa", "Pessoa invalido."));
			}
		} else
			return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, PessoaEntity value) {
		if (value != null) {
			return String.valueOf(((PessoaEntity) value).getId());
		} else
			return null;
	}

	
	
}
