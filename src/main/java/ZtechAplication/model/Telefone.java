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
@Table(name = "tb_Telefone")
public class Telefone {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idTelefone")  // Mapeia para a coluna existente
	private int idTelefone;
	private String telefone;
    
	@ManyToOne
	@JoinColumn(name = "fk_Cliente")
	private Cliente cliente;
    
	public int getIdTelefone() {
		return idTelefone;
	}
	public void setIdTelefone(int idTelefone) {
		this.idTelefone = idTelefone;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
    
    

}
