package br.upf.topicos.especiais.ged.grupo1.entity;

import java.io.Serializable;
import java.lang.Integer;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Usuario")
public class UsuarioEntity implements Serializable {

	private static final long serialVersionUID = -9214202867576011156L;

	@Id
	@SequenceGenerator(name = "UsuarioId", allocationSize = 1, sequenceName = "UsuarioId")
	@GeneratedValue(generator = "UsuarioId", strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@NotEmpty(message = "O nome deve ser informado!")
	@Length(max = 60, min = 3, message = "O nome deve ter entre {min} e {max} caracteres!")
	@Column(length = 60, nullable = false)
	private String nome;
	
	@NotEmpty(message = "O email deve ser informado!")
	@Email(message = "O email deve ser válido!")
	@Column(length = 20, nullable = false)
	private String email;
	
	@NotEmpty(message = "A senha deve ser informada!")
	@Length(max = 40, min = 6, message = "A senha deve ter entre {min} e {max} caracteres!")
	@Column(length = 40, nullable = false)
	private String senha;

	public UsuarioEntity(Integer id,
			@NotEmpty(message = "O nome deve ser informado!") @Length(max = 60, min = 3, message = "O nome deve ter entre 3 e 60 caracteres!") String nome,
			@NotEmpty(message = "O email deve ser informado!") @Email(message = "O email deve ser válido!") String email,
			@NotEmpty(message = "A senha deve ser informada!") @Length(max = 40, min = 6, message = "O nome deve ter entre 6 e 40 caracteres!") String senha) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
	}
	
}
