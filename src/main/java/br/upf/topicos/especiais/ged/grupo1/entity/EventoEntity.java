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
@Table(name = "Evento")
public class EventoEntity {

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "eventoId")
	@SequenceGenerator(name = "eventoId", allocationSize = 1, initialValue = 1)
	private Integer id;
	
	@Column(name = "titulo")
	private String titulo;
	
	@Column(name = "descricao")
	private String descricao;
	
	@Column(name = "dataInicio")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInicio;
	
	@Column(name = "dataTermino")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataTermino;
	
	@Column(name = "totalHoras")
	private Double totalHoras;
	
	@ManyToOne
	@JoinColumn(name = "evento_id")
	private List<SubEventoEntity> subEventos;
}
