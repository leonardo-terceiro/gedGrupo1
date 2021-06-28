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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
	
	@NotEmpty(message = "O subevento deve possuir um titulo!")
	@Column(name = "titulo")
	private String titulo;
	
	@NotEmpty(message = "O subevento deve conter a descrição!")
	@Size(min = 2, max = 255, message = "A descrição do subevento deve conter entre {min} e {max} caracteres!")
	@Column(name = "descricao")
	private String descricao;
	
	@NotNull(message = "O subevento deve conter uma data de inicio!")
	@Column(name = "dataHoraInicio")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraInicio;
	
	@NotNull(message = "O subevento deve conter uma data de fim!")
	@Column(name = "dataHoraTermino")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraTermino;
	
	@NotNull(message = "O subevento deve conter um total de horas!")
	@Column(name = "totalHoras")
	private Double totalHoras;
	
	@NotNull(message = "Assinatura é obrigatoria!")
	@Size(min = 1, message = "Assinatura requerida!")
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<AssinaEntity> assina;
	
	@NotNull(message = "evento é requerido!")
	@ManyToOne(optional = false)
	@JoinColumn(name = "evento_id")
	private EventoEntity evento;
	
	@NotNull(message = "Tipo de evento é obrigatorio!")
	@ManyToOne(optional = false)
	@JoinColumn(name = "tipo_evento_id")
	private TipoEventoEntity tipoEvento;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "subEvento", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<ModalidadeSubEventoEntity> modalidades;
}
