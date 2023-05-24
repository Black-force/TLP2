package Vitor.alex.modelo;


import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.JoinColumn;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Aluno {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(min = 3, message = "o nome deve ter no minimo 3 caracteres")
	private String nome;
	
	@NotNull
	@Size(min = 5, message = "o login deve ter no minimo 5 caracteres")
	private String login;
	
	@NotNull
	@Size(min = 8, message = "a senha deve ter no minimo 8 caracteres")
	private String senha;
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	@CPF(message = "CPF inválido")
	private String cpf;
	
	@Basic
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern ="dd/MM/yyyy")
	private Date dataNascimento;
	
	@NotEmpty(message  = "Seu RA não pode estar vazio")
	@Size(min = 6, max = 6, message  = "Seu RA deve ter 6 caracteres")
	private String RA;
	
	@NotEmpty(message  = "Informe o Curso")
	private String Curso;
	
	private boolean ativo;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="alunos_papel",
			 joinColumns = @JoinColumn(name = "Aluno_id"),
			   inverseJoinColumns = @JoinColumn(name = "papel_id"))
	

	private List<Papel> papeis;
	
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	

	
	public List<Papel> getPapeis() {
		return papeis;
	}
	public void setPapeis(List<Papel> papeis) {
		this.papeis = papeis;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public String getRA() {
		return RA;
	}
	public void setRA(String rA) {
		RA = rA;
	}
	public String getCurso() {
		return Curso;
	}
	public void setCurso(String curso) {
		Curso = curso;
	}
	
	
}
