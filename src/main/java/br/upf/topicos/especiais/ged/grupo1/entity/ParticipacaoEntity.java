package br.upf.topicos.especiais.ged.grupo1.entity;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Participacao")
public class ParticipacaoEntity implements Serializable{

	private static final long serialVersionUID = -2505046029737651791L;

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "participacaoId")
	@SequenceGenerator(name = "participacaoId", allocationSize = 1, initialValue = 1)
	private Integer id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "pessoa_id")
	private PessoaEntity pessoa;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "modalidade_sub_evento_id")
	private ModalidadeSubEventoEntity modalidadeSubEvento;
	
	@NotNull(message = "Participação deve conter o numero de horas!")
	@Column(name = "horas_participou")
	private Double horasParticipou;
	
	@NotNull(message = "O arquivo é requerido!")
	@Lob
	@Column(name = "arquivo")
	private byte[] arquivo;
	
}
