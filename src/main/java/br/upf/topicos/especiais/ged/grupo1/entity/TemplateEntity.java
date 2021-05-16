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
@Table(name = "Template")
public class TemplateEntity {

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "templateId")
	@SequenceGenerator(name = "templateId", allocationSize = 1, initialValue = 1)
	private Integer id;
	
	@Column(name = "descricao")
	private String descricao;
	
	@Column(name = "texto")
	private String texto;
	
}
