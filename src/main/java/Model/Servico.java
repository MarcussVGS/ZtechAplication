package Model;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

public class Servico {
	
	@Id
	private int idServico;
	@NotEmpty
	private String descrisaoServico;
	@NotEmpty
	private float valor;
	
	public int getIdServico() {
		return idServico;
	}
	public void setIdServico(int idServico) {
		this.idServico = idServico;
	}
	public String getDescrisaoServico() {
		return descrisaoServico;
	}
	public void setDescrisaoServico(String descrisaoServico) {
		this.descrisaoServico = descrisaoServico;
	}
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	
	

}
