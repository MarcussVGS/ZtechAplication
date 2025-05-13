package ZtechAplication.model;
// pagina 40 do documento do prof

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_Estoque")
public class Estoque {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idEstoque")  // Mapeia para a coluna existente
	private Long idEstoque ;

    @Column(nullable = false)
	private int quantidade;
    @OneToMany
    @JoinColumn(name = "idProduto")
	private List<Produto> produto;
	
	//
    public Long getIdEstoque() {
		return idEstoque;
	}
	public void setIdEstoque(Long idEstoque) {
		this.idEstoque = idEstoque;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public List<Produto> getProduto() {
		return produto;
	}
	public void setProduto(List<Produto> produto) {
		this.produto = produto;
	}
	

}
