package br.upf.topicos.especiais.ged.grupo1.entity;

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
	
	@NotEmpty(message = "O titulo deve ser informado!")
	@Column(name = "titulo")
	private String titulo;
	
	@NotEmpty(message = "A descrição deve ser informada!")
	@Size(min = 2, max = 255, message = "a descrição deve ser entre {min} e {max} caracteres.")
	@Column(name = "descricao")
	private String descricao;
	
	@NotNull(message = "O evento deve ter uma data de inicio!")
	@Column(name = "dataInicio")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInicio;
	
	@NotNull(message = "O evento deve ter uma data de fim!")
	@Column(name = "dataTermino")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataTermino;
	
	@NotNull(message = "O evento deve possuir um total de horas!")
	@Column(name = "totalHoras")
	private Double totalHoras;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "evento", fetch = FetchType.EAGER)
	private List<SubEventoEntity> subEventos;
}
