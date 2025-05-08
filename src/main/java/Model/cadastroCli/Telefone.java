package Model.cadastroCli;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

public class Telefone {
	
	@Id
	private int idTelefone;
	@NotEmpty
	private String telefone;

}
