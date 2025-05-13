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
	private Integer idCategoria;

    @Column(nullable = false)
	private String nomeCategoria;
    
    @OneToOne
    @JoinColumn(name = "idProduto")  // ou "idCliente" se quiser bater com o nome exato
    private Produto produto;
    
	
    //    
	public Integer getid() {
		return idCategoria;
	}
	public void setid(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}
	
	public String getNomeCategoria() {
		return nomeCategoria;
	}
	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}
	//referencia da tabela
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	
	

}
