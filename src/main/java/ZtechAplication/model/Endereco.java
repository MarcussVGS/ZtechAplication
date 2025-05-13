package ZtechAplication.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_Endereco")
public class Endereco {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idEndereco")  // Mapeia para a coluna existente
	private Long idEndereco;

    @Column(nullable = false, length = 75)
	private String rua;
    @Column(nullable = false, length = 10)
	private String cep;
    @Column(nullable = false, length = 30)
	private String bairro;
    @Column(nullable = false, length = 30)
	private String cidade;
    @Column(nullable = false)
	private int numeroCasa;
	
	
	public Long getIdEndereco() {
		return idEndereco;
	}
	public void setIdEndereco(Long idEndereco) {
		this.idEndereco = idEndereco;
	}
	public String getRua() {
		return rua;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public int getNumeroCasa() {
		return numeroCasa;
	}
	public void setNumeroCasa(int numeroCasa) {
		this.numeroCasa = numeroCasa;
	}
	
	

}
