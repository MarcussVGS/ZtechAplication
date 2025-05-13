package ZtechAplication.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_Venda")
public class Venda {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_Venda")  // Mapeia para a coluna existente
	private Long idVenda;
	
	@Column(name = "data_Venda", nullable = false)
	private LocalDate dataInicio;
	@Column(name = "hora_Venda", nullable = false)
	private LocalTime horaInicio;
	@Column(name = "valor")
	private float valor;
	@Column(name = "lucro")
	private float lucro;
	
	
	public Long getIdVenda() {
		return idVenda;
	}
	public void setIdVenda(Long idVenda) {
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
	
	
	
	

}
