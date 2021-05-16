package br.upf.topicos.especiais.ged.grupo1.entity;

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Assina")
public class AssinaEntity {

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "assinaId")
	@SequenceGenerator(name = "assinaId", allocationSize = 1, initialValue = 1)
	private Integer id;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "funcao")
	private String funcao;
	
	@Column(name = "imagem")
	private String imagem;
	
	@Column(name = "dataInativo")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInativo;
}
