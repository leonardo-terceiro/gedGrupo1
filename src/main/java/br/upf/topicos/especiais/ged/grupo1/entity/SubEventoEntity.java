package br.upf.topicos.especiais.ged.grupo1.entity;

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "SubEvento")
public class SubEventoEntity {

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "subEventoId")
	@SequenceGenerator(name = "subEventoId", allocationSize = 1, initialValue = 1)
	private Integer id;
	
	@Column(name = "evento_id")
	private Integer eventoId;
	
	@Column(name = "titulo")
	private String titulo;
	
	@Column(name = "descricao")
	private String descricao;
	
	@Column(name = "dataHoraInicio")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraInicio;
	
	@Column(name = "dataHoraTermino")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraTermino;
	
	@Column(name = "totalHoras")
	private Double totalHoras;
	
	@ManyToOne
	@JoinColumn(name = "sub_evento_id")
	private List<ModalidadeSubEventoEntity> modalidades;
}
