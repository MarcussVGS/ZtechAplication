package ZtechAplication.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_OS")
public class OrdemServico {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idOS")  // Mapeia para a coluna existente
	private Long idOS;

	@Column(name = "data_inicio", nullable = false)
	private LocalDate dataInicio;
	@Column(name = "hora_inicio", nullable = false)
	private LocalTime horaInicio;
	@Column(name = "data_fim", nullable = false)
	private LocalDate dataFim;
	@Column(name = "hora_fim", nullable = false)
	private LocalTime horaFim;
	@Column(nullable = false, length = 10)
	private String status;
	@Column(nullable = false)
	private float valor;
	@Column(nullable = false)
	private float lucro;
	@OneToMany(mappedBy = "idServico")
	private List<Servico> servico;
	@OneToMany(mappedBy = "idEstoque")
	private List<Estoque> estoque;
	@OneToOne
	@JoinColumn (name = "idCliente")// ou "idCliente" se quiser bater com o nome exato
	private Cliente cliente;
	
	public Long getIdOS() {
		return idOS;
	}
	public void setIdOS(Long idOS) {
		this.idOS = idOS;
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
	public LocalDate getDataFim() {
		return dataFim;
	}
	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}
	public LocalTime getHoraFim() {
		return horaFim;
	}
	public void setHoraFim(LocalTime horaFim) {
		this.horaFim = horaFim;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public List<Servico> getServico() {
		return servico;
	}
	public void setServico(List<Servico> servico) {
		this.servico = servico;
	}
	public List<Estoque> getEstoque() {
		return estoque;
	}
	public void setEstoque(List<Estoque> estoque) {
		this.estoque = estoque;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
	

}
