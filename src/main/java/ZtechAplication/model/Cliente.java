package ZtechAplication.model;

import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_Cliente")
public class Cliente {
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	@Column(name = "idCliente")  // Mapeia para a coluna existente
	private int idCliente;

    @Column(nullable = false, length = 50)
	private String nomeCliente;
	@Column(unique = true, length = 20)
	private String cpf;
	
	@OneToMany
	@JoinColumn(name = "fk_Email")
	private List<Email> email;
	@OneToMany
	@JoinColumn(name = "fk_Endereco")
	private List<Endereco> endereco;
	@OneToMany
	@JoinColumn(name = "fk_Telefone")
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
