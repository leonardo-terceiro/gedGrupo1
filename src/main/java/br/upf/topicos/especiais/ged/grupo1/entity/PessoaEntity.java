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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Pessoa")
public class PessoaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "PessoaId")
	@SequenceGenerator(name = "PessoaId", allocationSize = 1, initialValue = 1)
	private Integer id;

	@NotBlank(message = "O nome deve ser informado!")
	@Length(min = 2, max = 60, message = "O nome deve ter entre {min} e {max} caracteres.")
	@Column(length = 60, nullable = false)
	private String nome;

	@Column(length = 20)
	private String telefone;

	@Column(name = "email")
	@Email(message = "O email deve ser válido!")
	@NotBlank(message = "O email deve ser informado!")
	private String email;

	@Column(name = "senha")
	@NotBlank(message = "A senha deve ser informada!")
	private String senha;

	@Column(name = "documento")
	@NotBlank(message = "O documento deve ser informado!")
	private String documento;

	@Column(name = "tipoDocumento")
	@NotBlank(message = "O tipo de documento deve ser informado")
	private String tipoDocumento;

	@Column(name = "nacionalidade")
	private String nacionalidade;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "pessoa", fetch = FetchType.EAGER)
	private List<ParticipacaoEntity> participacoes;

	@Override
	public String toString() {
		return "PessoaEntity [id=" + id + ", nome=" + nome + ", telefone=" + telefone + ", email=" + email + ", senha="
				+ senha + ", documento=" + documento + ", tipoDocumento=" + tipoDocumento + ", nacionalidade="
				+ nacionalidade + "]";
	}

}
