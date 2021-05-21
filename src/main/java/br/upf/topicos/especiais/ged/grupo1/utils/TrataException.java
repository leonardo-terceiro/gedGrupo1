package br.upf.topicos.especiais.ged.grupo1.utils;

public class TrataException {

	public static String getMensagem(Throwable e) {
		if (e.getCause() != null) {
			return getMensagem(e.getCause());
		} else {
			return e.getMessage();
		}
	}
	
}
