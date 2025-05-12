package ZtechAplication.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbEmail")
public class Email {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idEmail;

    @Column(nullable = false)
	private String endEmail;
    @ManyToOne
    @JoinColumn(name = "idCliente")  // ou "idCliente" se quiser bater com o nome exato
    private Cliente cliente;
    
	
	public Long getIdEmail() {
		return idEmail;
	}
	public void setIdEmail(Long idEmail) {
		this.idEmail = idEmail;
	}
	public String getEmail() {
		return endEmail;
	}
	public void setEmail(String endEmail) {
		this.endEmail = endEmail;
	}
	//referencia da tabela
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}
