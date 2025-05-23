package ZtechAplication.pagina;

import org.springframework.data.jpa.domain.Specification;

import ZtechAplication.model.Cliente;

public class SpecificationController {

	public static Specification<Cliente> comTermoCli (String termo){
		return (root, query, cb) -> {
			if ( termo == null || termo.trim().isEmpty() ) {
				return cb.isTrue(cb.literal(true));
			} 
			
			String likeTerm = "%" + termo.toLowerCase() + "%";
			Integer termoNumerico = Integer.valueOf(termo);
			return cb.or(
					cb.like(cb.lower(root.get("nomeCliente")), likeTerm),
					cb.like(cb.lower(root.get("cpf")), likeTerm),
					cb.like(cb.lower(root.get("email").get("endEmail")), likeTerm),
					cb.like(cb.lower(root.get("telefone").get("telefone")), likeTerm),
					cb.like(cb.lower(root.get("endereco").get("bairro")), likeTerm)
			);
		};
	}
	
}
