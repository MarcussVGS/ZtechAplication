package ZtechAplication.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_Produto")
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_Produto")
	private Long idProduto;
	
	@Column(nullable = false, length = 50)
	private String nome;
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal custo;
	@Column(nullable = false, length = 255)
	private String descricao;
	
	@ManyToOne
	@JoinColumn(name = "idMarca", referencedColumnName = "idMarca")
	private Marca marca;
	@ManyToOne
	@JoinColumn(name = "idCategoria", referencedColumnName = "idCategoria")
	private Categoria categoria;
	
	public Long getid() {
		return idProduto;
	}
	public void setid(Long idProduto) {
		this.idProduto = idProduto;
	}
	
	public String getnome() {
		return nome;
	}
	public void setnome(String nome) {
		this.nome = nome;
	}
	
	public BigDecimal getCusto() {
		return custo;
	}
	public void setCusto(BigDecimal custo) {
		this.custo = custo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	//chaves estrangeiras
	public Marca getMarca() {
		return marca;
	}
	public void setMarca(Marca marca) {
		this.marca = marca;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	

}
