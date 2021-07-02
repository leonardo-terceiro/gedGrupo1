package br.upf.topicos.especiais.ged.grupo1.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

import br.upf.topicos.especiais.ged.grupo1.entity.AssinaEntity;
import br.upf.topicos.especiais.ged.grupo1.util.JpaUtil;

@FacesConverter(value = "assinaConverter")
public class AssinaConverter implements Converter<AssinaEntity>{

	@Override
	public AssinaEntity getAsObject(FacesContext context, UIComponent component, String value) {
		System.out.println("AssinaEntity -> " + value);
		if (value != null && value.trim().length() > 0) {
			try {
				EntityManager em = JpaUtil.getInstance().getEntityManager();
				AssinaEntity ret = em.find(AssinaEntity.class, Integer.parseInt(value));
				em.close();
				System.out.println("AssinaEntity -> " + value);
				return ret;
			} catch (NumberFormatException e) {
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Erro de Conversao de Assina", "Assina invalido."));
			}
		} else
			return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, AssinaEntity value) {
		if (value != null) {
			return String.valueOf(((AssinaEntity) value).getId());
		} else
			return null;
	}

}
