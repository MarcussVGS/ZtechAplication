package ZtechAplication.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_Servico")
public class Servico {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idServico")  // Mapeia para a coluna existente
	private Long idServico;
	@Column(nullable = false, length = 255)
	private String descrisaoServico;
	@Column(nullable = false)
	private float valor;
	
	public Long getIdServico() {
		return idServico;
	}
	public void setIdServico(Long idServico) {
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
