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
@Table(name = "tbOS")
public class OrdemServico {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idOS;

    @Column(nullable = false)
	private LocalDateTime datahoraInicio;
	private LocalDateTime datahoraFim;
	private String status;
	private float valor;
	private float lucro;
	@OneToMany(mappedBy = "ordemServico", cascade = CascadeType.REMOVE)
	private List<Servico> servico;
	@OneToMany(mappedBy = "ordemServico", cascade = CascadeType.REMOVE)
	private List<Estoque> estoque;
	@OneToOne
    @JoinColumn(name = "idCliente")  // ou "idCliente" se quiser bater com o nome exato
	private Cliente cliente;
	
	public Long getIdOS() {
		return idOS;
	}
	public void setIdOS(Long idOS) {
		this.idOS = idOS;
	}
	public LocalDateTime getDatahoraInicio() {
		return datahoraInicio;
	}
	public void setDatahoraInicio(LocalDateTime datahoraInicio) {
		this.datahoraInicio = datahoraInicio;
	}
	public LocalDateTime getDatahoraFim() {
		return datahoraFim;
	}
	public void setDatahoraFim(LocalDateTime datahoraFim) {
		this.datahoraFim = datahoraFim;
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
