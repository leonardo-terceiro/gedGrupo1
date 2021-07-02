package br.upf.topicos.especiais.ged.grupo1.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

import br.upf.topicos.especiais.ged.grupo1.entity.EventoEntity;
import br.upf.topicos.especiais.ged.grupo1.util.JpaUtil;

@FacesConverter(value = "eventoConverter")
public class EventoConverter implements Converter<EventoEntity>{

	@Override
	public EventoEntity getAsObject(FacesContext context, UIComponent component, String value) {
		System.out.println("EventoConverter -> " + value);
		if (value != null && value.trim().length() > 0) {
			try {
				EntityManager em = JpaUtil.getInstance().getEntityManager();
				EventoEntity ret = em.find(EventoEntity.class, Integer.parseInt(value));
				em.close();
				System.out.println("EventoConverter -> " + ret);
				return ret;
			} catch (NumberFormatException e) {
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Erro de Conversao do Evento", "Evento invalido."));
			}
		} else
			return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, EventoEntity value) {
		System.out.println("EventoConverter -> getAsString() - " + value);
		try {
			if (value != null) {
				return String.valueOf(((EventoEntity) value).getId());
			} else
				return null;
		}catch (Exception e) {
			e.getStackTrace();
			System.out.println("EventoConverter -> getAsString() ERROR - " + e.getMessage());
		}
		
		return null;
	}

}
