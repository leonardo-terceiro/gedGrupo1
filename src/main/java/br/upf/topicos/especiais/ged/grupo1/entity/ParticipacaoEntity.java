package br.upf.topicos.especiais.ged.grupo1.entity;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Participacao")
public class ParticipacaoEntity {

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "participacaoId")
	@SequenceGenerator(name = "participacaoId", allocationSize = 1, initialValue = 1)
	private Integer id;
	
	@Column(name = "horas_participou")
	private Double horasParticipou;
	
	@Lob
	@Column(name = "arquivo")
	private String arquivo;
	
}
