package ZtechAplication.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbCategoria")
public class Categoria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idCategoria")  // Mapeia para a coluna existente
	private int idCategoria;
	private String nome;
    
	
    //    
	public int getid() {
		return idCategoria;
	}
	public void setid(int idCategoria) {
		this.idCategoria = idCategoria;
	}
	
	public String getNomeCategoria() {
		return nome;
	}
	public void setNomeCategoria(String nomeCategoria) {
		this.nome = nomeCategoria;
	}
	
	
	

}
