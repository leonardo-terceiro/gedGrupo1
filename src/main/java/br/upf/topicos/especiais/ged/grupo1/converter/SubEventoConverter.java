package br.upf.topicos.especiais.ged.grupo1.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

import br.upf.topicos.especiais.ged.grupo1.entity.SubEventoEntity;
import br.upf.topicos.especiais.ged.grupo1.util.JpaUtil;

@FacesConverter(value = "subEventoConverter")
public class SubEventoConverter implements Converter<SubEventoEntity>{

	@Override
	public SubEventoEntity getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				EntityManager em = JpaUtil.getInstance().getEntityManager();
				SubEventoEntity ret = em.find(SubEventoEntity.class, Integer.parseInt(value));
				em.close();
				return ret;
			} catch (NumberFormatException e) {
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Erro de Conversao do sub evento", "Sub evento invalido."));
			}
		} else
			return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, SubEventoEntity value) {
		if (value != null) {
			return String.valueOf(((SubEventoEntity) value).getId());
		} else
			return null;
	}

}
