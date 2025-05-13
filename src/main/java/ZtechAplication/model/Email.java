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
@Table(name = "tb_Email")
public class Email {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idEmail")  // Mapeia para a coluna existente
	private Long idEmail;

    @Column(nullable = false, length = 50)
	private String endEmail;
    
	
	public Long getIdEmail() {
		return idEmail;
	}
	public void setIdEmail(Long idEmail) {
		this.idEmail = idEmail;
	}
	public String getEmail() {
		return endEmail;
	}
	public void setEmail(String endEmail) {
		this.endEmail = endEmail;
	}
}
