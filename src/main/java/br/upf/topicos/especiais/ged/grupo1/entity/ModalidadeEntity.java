package br.upf.topicos.especiais.ged.grupo1.entity;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Modalidade")
public class ModalidadeEntity implements Serializable{

	private static final long serialVersionUID = 8899535096280622017L;

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "modalidadeId")
	@SequenceGenerator(name = "modalidadeId", allocationSize = 1, initialValue = 1)
	private Integer id;
	
	@NotEmpty(message = "A modalidade deve conter uma descrição!")
	@Size(min = 2, max = 255, message = "A descrição deve ser entre {min} e {max} caracteres!")
	@Column(name = "descricao")
	private String descricao;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "modalidade", fetch = FetchType.EAGER)
	private List<ModalidadeSubEventoEntity> modalidades;

	@Override
	public String toString() {
		return "ModalidadeEntity [id=" + id + ", descricao=" + descricao + "]";
	}
	
}
