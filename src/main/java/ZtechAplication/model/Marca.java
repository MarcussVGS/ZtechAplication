package ZtechAplication.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_Marca")
public class Marca {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idMarca")  // Mapeia para a coluna existente
	private Long idMarca;

    @Column(nullable = false, length = 50)
	private String nome;
	
    //
	public Long getidMarca() {
		return idMarca;
	}
	public void setidMarca(Long idMarca) {
		this.idMarca = idMarca;
	}
	
	public String getNomeMarca() {
		return nome;
	}
	public void setNomeMarca(String nomeMarca) {
		this.nome = nomeMarca;
	}
	
	
	

}
