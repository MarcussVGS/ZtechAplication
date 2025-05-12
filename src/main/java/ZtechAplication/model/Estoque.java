package ZtechAplication.model;
// pagina 40 do documento do prof

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
@Table(name = "tbEstoque")
public class Estoque {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idEstoque ;

    @Column(nullable = false)
	private int quantidade;
    @OneToOne
    @JoinColumn(name = "produto_id")
	private Produto produto;
    @ManyToOne
    @JoinColumn(name = "OS_id")
    private OrdemServico ordemServico;
    @ManyToOne
    @JoinColumn(name = "venda_id")
    private Venda venda;
	
	//
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	

}
