package br.upf.topicos.especiais.ged.grupo1.entity;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Modalidade_sub_evento")
public class ModalidadeSubEventoEntity implements Serializable{

	private static final long serialVersionUID = -3517508744623250413L;

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "modalidadeId")
	@SequenceGenerator(name = "modalidadeId", allocationSize = 1, initialValue = 1)
	private Integer id;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "modalidadeSubEvento", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<ParticipacaoEntity> participacoes;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "template_id")
	private TemplateEntity template;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "modalidade_id")
	private ModalidadeEntity modalidade;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "sub_evento_id")
	private SubEventoEntity subEvento;

	@Override
	public String toString() {
		return "ModalidadeSubEventoEntity [id=" + id + ", template=" + template + ", modalidade=" + modalidade
				+ ", subEvento=" + subEvento + "]";
	}
	
}
