package Model;

import Model.cadastroCli.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

public class Venda {
	
	@Id
	@GeneratedValue
	private int idVenda;
	@NotEmpty
	private LocalDateTime datahora;
	@NotEmpty
	private float valor;
	@NotEmpty
	private float lucro;
	@OneToMany(mappedBy = "Venda", cascade = CascadeType.REMOVE)
	private List<Estoque> estoque;
	@NotEmpty
	private Cliente cliente;

}
