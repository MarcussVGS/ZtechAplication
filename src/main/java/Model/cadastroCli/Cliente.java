package Model.cadastroCli;

import java.io.Serializable;
import java.util.List;

import Model.Estoque;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

public class Cliente {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private int idCliente;
	@NotEmpty
	private String nomeCliente;
	@Column(unique = true)
	private String cpf;
	@OneToMany(mappedBy = "Cliente", cascade = CascadeType.REMOVE)
	private List<Email> email;
	@OneToMany(mappedBy = "Cliente", cascade = CascadeType.REMOVE)
	private List<Endereco> endereco;
	@OneToMany(mappedBy = "Cliente", cascade = CascadeType.REMOVE)
	private List<Telefone> telefone;
	
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	public String getNomeCliente() {
		return nomeCliente;
	}
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	//chaves estrangeiras
	public List<Email> getEmail() {
		return email;
	}
	public void setEmail(List<Email> email) {
		this.email = email;
	}
	public List<Endereco> getEndereco() {
		return endereco;
	}
	public void setEndereco(List<Endereco> endereco) {
		this.endereco = endereco;
	}
	public List<Telefone> getTelefone() {
		return telefone;
	}
	public void setTelefone(List<Telefone> telefone) {
		this.telefone = telefone;
	}
	
	

}
