package br.upf.topicos.especiais.ged.grupo1.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

import br.upf.topicos.especiais.ged.grupo1.entity.TipoEventoEntity;
import br.upf.topicos.especiais.ged.grupo1.util.JpaUtil;

@FacesConverter(value = "tipoEventoConverter")
public class TipoEventoConverter implements Converter<TipoEventoEntity>{

	@Override
	public TipoEventoEntity getAsObject(FacesContext context, UIComponent component, String value) {
		System.out.println("TipoEventoConverter -> " + value);
		if (value != null && value.trim().length() > 0) {
			try {
				EntityManager em = JpaUtil.getInstance().getEntityManager();
				TipoEventoEntity ret = em.find(TipoEventoEntity.class, Integer.parseInt(value));
				em.close();
				System.out.println("TipoEventoConverter -> " + ret);
				return ret;
			} catch (NumberFormatException e) {
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Erro de Conversao do Tipo de evento", "Tipo de evento invalido."));
			}
		} else
			return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, TipoEventoEntity value) {
		if (value != null) {
			return String.valueOf(((TipoEventoEntity) value).getId());
		} else
			return null;
	}

	
}
