package br.upf.topicos.especiais.ged.grupo1.entity;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TipoEvento")
public class TipoEventoEntity implements Serializable{

	private static final long serialVersionUID = 3611089238220040882L;

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "EnventoId")
	@SequenceGenerator(name = "EnventoId", allocationSize = 1, initialValue = 1)
	private Integer id;

	@Length(min = 2, max = 60)
	@Column(name = "descricao", length = 60)
	@NotBlank(message = "A descrição deve ser informada!")
	private String descricao;
	
}
