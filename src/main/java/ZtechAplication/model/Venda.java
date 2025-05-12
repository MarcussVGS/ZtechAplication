package ZtechAplication.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbVenda")
public class Venda {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idVenda;
	
	@Column(nullable = false)
	private LocalDateTime datahora;
	
	private float valor;
	private float lucro;
	@OneToMany(mappedBy = "venda", cascade = CascadeType.REMOVE)
	private List<Estoque> estoque;
	@OneToOne
    @JoinColumn(name = "idCliente")  // ou "idCliente" se quiser bater com o nome exato
	private Cliente cliente;
	
	

}
