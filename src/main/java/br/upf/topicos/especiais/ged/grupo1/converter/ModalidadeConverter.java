package br.upf.topicos.especiais.ged.grupo1.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

import br.upf.topicos.especiais.ged.grupo1.entity.ModalidadeEntity;
import br.upf.topicos.especiais.ged.grupo1.utils.JpaUtil;

@FacesConverter(value = "modalidadeConverter")
public class ModalidadeConverter implements Converter<ModalidadeEntity>{

	@Override
	public ModalidadeEntity getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				EntityManager em = JpaUtil.getInstance().getEntityManager();
				ModalidadeEntity ret = em.find(ModalidadeEntity.class, Integer.parseInt(value));
				em.close();
				return ret;
			} catch (NumberFormatException e) {
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Erro de Conversao do Tipo de evento", "Tipo de evento invalido."));
			}
		} else
			return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, ModalidadeEntity value) {
		if (value != null) {
			return String.valueOf(((ModalidadeEntity) value).getId());
		} else
			return null;
	}

	
}
