package br.upf.topicos.especiais.ged.grupo1.entity;

import static javax.persistence.GenerationType.SEQUENCE;

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
	
	@NotEmpty(message = "A descricao é obrigatoria!")
	@Column(name = "descricao")
	private String descricao;
	
	@NotEmpty(message = "O texto é obrigatorio!")
	@Column(name = "texto")
	private String texto;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "template", fetch = FetchType.EAGER)
	private List<ModalidadeSubEventoEntity> modalidades;
}
