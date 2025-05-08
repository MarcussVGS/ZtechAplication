package Model;

import Model.cadastroCli.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

public class OrdemServico {
	
	@Id
	@GeneratedValue
	private int idOS;
	@NotEmpty
	private LocalDateTime datahoraInicio;
	@NotEmpty
	private LocalDateTime datahoraFim;
	@NotEmpty
	private String status;
	@NotEmpty
	private float valor;
	@NotEmpty
	private float lucro;
	@NotEmpty
	private Servico servico;
	@NotEmpty
	private Estoque estoque;
	@NotEmpty
	private Cliente cliente;
	
	public int getIdOS() {
		return idOS;
	}
	public void setIdOS(int idOS) {
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
	public Servico getServico() {
		return servico;
	}
	public void setServico(Servico servico) {
		this.servico = servico;
	}
	public Estoque getEstoque() {
		return estoque;
	}
	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
	

}
