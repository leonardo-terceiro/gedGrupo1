package br.upf.topicos.especiais.ged.grupo1.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

import br.upf.topicos.especiais.ged.grupo1.entity.ModalidadeSubEventoEntity;
import br.upf.topicos.especiais.ged.grupo1.utils.JpaUtil;

@FacesConverter(value = "modalidadeSubEventoConverter")
public class ModalidadeSubEventoConverter implements Converter<ModalidadeSubEventoEntity>{

	@Override
	public ModalidadeSubEventoEntity getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				EntityManager em = JpaUtil.getInstance().getEntityManager();
				ModalidadeSubEventoEntity ret = em.find(ModalidadeSubEventoEntity.class, Integer.parseInt(value));
				em.close();
				return ret;
			} catch (NumberFormatException e) {
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Erro de Conversao do Modalidade Sub Evento", "Modalidade Sub Evento invalido."));
			}
		} else
			return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, ModalidadeSubEventoEntity value) {
		if (value != null) {
			return String.valueOf(((ModalidadeSubEventoEntity) value).getId());
		} else
			return null;
	}

}
