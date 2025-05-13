package ZtechAplication.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_Marca")
public class Marca {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idMarca")  // Mapeia para a coluna existente
	private Integer idMarca;

    @Column(nullable = false)
	private String nome;
    @OneToOne
    @JoinColumn(name = "idMarcaProduto")  // ou "idMarcaCliente" se quiser bater com o nome exato
    private Produto produto;
	
	public Integer getidMarcaMarca() {
		return idMarca;
	}
	public void setidMarcaMarca(Integer idMarca) {
		this.idMarca = idMarca;
	}
	
	public String getNomeMarca() {
		return nome;
	}
	public void setNomeMarca(String nome) {
		this.nome = nome;
	}
	//referencia da tabela
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	

}
