package ZtechAplication.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_Venda")
public class Venda {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_Venda")  // Mapeia para a coluna existente
	private int idVenda;
	
	private LocalDate dataInicio;
	private LocalTime horaInicio;
	private float valor;
	private float lucro;
	@OneToMany(mappedBy = "idEstoque")
	private List<Estoque> estoque;
	
	
	public int getIdVenda() {
		return idVenda;
	}
	public void setIdVenda(int idVenda) {
		this.idVenda = idVenda;
	}
	public LocalDate getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}
	public LocalTime getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(LocalTime horaInicio) {
		this.horaInicio = horaInicio;
	}
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	public float getLucro() {
		return lucro;
	}
	public void setLucro(float lucro) {
		this.lucro = lucro;
	}
	public List<Estoque> getEstoque() {
		return estoque;
	}
	public void setEstoque(List<Estoque> estoque) {
		this.estoque = estoque;
	}
	
	
	
	
	
	

}
