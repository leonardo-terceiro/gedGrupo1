package br.upf.topicos.especiais.ged.grupo1.entity;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Modalidade_sub_evento")
public class ModalidadeSubEventoEntity {

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "modalidadeId")
	@SequenceGenerator(name = "modalidadeId", allocationSize = 1, initialValue = 1)
	private Integer id;
	
	@Column(name = "participacao_id")
	private Integer participacaoId;
	
	@Column(name = "template_id")
	private Integer templateId;
	
	@Column(name = "modalidade_id")
	private Integer modalidadeId;
	
	@Column(name = "sub_evento_id")
	private Integer subEventoId;
}
