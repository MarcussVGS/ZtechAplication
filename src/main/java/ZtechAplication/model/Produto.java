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
@Table(name = "tbProduto")
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private String nome;
	private float custo;
	private String descricao;
	
	@ManyToOne
	@JoinColumn(name = "id_marca", referencedColumnName = "idMarca")
	private Marca marca;
	@ManyToOne
	@JoinColumn(name = "id_categoria", referencedColumnName = "id")
	private Categoria categoria;
	
	public int getid() {
		return id;
	}
	public void setid(int id) {
		this.id = id;
	}
	
	public String getnome() {
		return nome;
	}
	public void setnome(String nome) {
		this.nome = nome;
	}
	
	public float getCusto() {
		return custo;
	}
	public void setCusto(float custo) {
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
