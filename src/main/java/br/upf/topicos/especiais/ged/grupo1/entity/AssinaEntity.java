package br.upf.topicos.especiais.ged.grupo1.entity;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Assina")
public class AssinaEntity implements Serializable{

	private static final long serialVersionUID = -9185194172371924792L;

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "assinaId")
	@SequenceGenerator(name = "assinaId", allocationSize = 1, initialValue = 1)
	private Integer id;
	
	@NotEmpty(message = "O nome deve ser informado!")
	@Size(max = 120, message = "O nome deve conter no maximo {max} caracteres!")
	@Column(name = "nome")
	private String nome;
	
	@NotEmpty(message = "a funcao deve ser informada!")
	@Column(name = "funcao")
	private String funcao;
	
	@NotEmpty(message = "a imagem deve ser informada!")
	@Column(name = "imagem")
	private String imagem;
	
	@Column(name = "dataInativo")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInativo;
	
}
