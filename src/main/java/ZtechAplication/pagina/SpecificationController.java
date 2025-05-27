package ZtechAplication.pagina;

import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate; // Importar Predicate
import java.util.ArrayList; // Importar ArrayList
import java.util.List; // Importar List

import ZtechAplication.model.Cliente;
import ZtechAplication.model.OrdemServico;
import ZtechAplication.model.Produto;
import ZtechAplication.model.Venda; // Importar Venda
import java.time.LocalDate; // Importar LocalDate
import java.time.format.DateTimeFormatter; // Importar DateTimeFormatter
import java.time.format.DateTimeParseException; // Importar DateTimeParseException


public class SpecificationController {

	// METODO DE BUSCA ESPECIFICADA PARA CLIENTE
	public static Specification<Cliente> comTermoCli (String termo){
		return (root, query, cb) -> {
			if ( termo == null || termo.trim().isEmpty() ) {
				return cb.isTrue(cb.literal(true)); // Retorna todos se o termo for vazio
			}

			String likeTerm = "%" + termo.toLowerCase() + "%";
			// Garante que os joins sejam feitos para evitar N+1 e permitir a busca
            // query.distinct(true); // Adicionar distinct pode ser útil se houver duplicatas devido a joins
            root.fetch("email", jakarta.persistence.criteria.JoinType.LEFT);
            root.fetch("telefone", jakarta.persistence.criteria.JoinType.LEFT);
            root.fetch("endereco", jakarta.persistence.criteria.JoinType.LEFT);

			return cb.or(
					cb.like(cb.lower(root.get("nomeCliente")), likeTerm),
					cb.like(cb.lower(root.get("cpf")), likeTerm),
					cb.like(cb.lower(root.get("email").get("endEmail")), likeTerm),
					cb.like(cb.lower(root.get("telefone").get("telefone")), likeTerm),
					cb.like(cb.lower(root.get("endereco").get("bairro")), likeTerm)
			);
		};
	}

	// METODO DE BUSCA ESPECIFICADA PARA PRODUTO
	public static Specification<Produto> comTermoProd (String termo){
		return (root, query, cb) -> {
			if ( termo == null || termo.trim().isEmpty() ) {
				return cb.isTrue(cb.literal(true)); // Retorna todos se o termo for vazio
			}
			String likeTerm = "%" + termo.toLowerCase() + "%";
			// Garante que os joins sejam feitos
            // query.distinct(true);
            root.fetch("categoria", jakarta.persistence.criteria.JoinType.LEFT);
            root.fetch("marca", jakarta.persistence.criteria.JoinType.LEFT);

			return cb.or(
					cb.like(cb.lower(root.get("nome")), likeTerm),
					cb.like(cb.lower(root.get("descricao")), likeTerm),
					cb.like(cb.lower(root.get("categoria").get("nome")), likeTerm),
					cb.like(cb.lower(root.get("marca").get("nome")), likeTerm)
			);
		};
	}

    // NOVO MÉTODO DE BUSCA ESPECIFICADA PARA VENDA
    public static Specification<Venda> comTermoVenda(String termo) {
        return (root, query, cb) -> {
            if (termo == null || termo.trim().isEmpty()) {
                return cb.isTrue(cb.literal(true)); // Retorna todas as vendas se o termo for vazio
            }

            // Garante que os joins sejam feitos para buscar em Cliente e Produto
            // e também para evitar N+1 problemas ao carregar os dados relacionados.
            // Usar LEFT JOIN FETCH para carregar as entidades relacionadas na mesma query.
            // query.distinct(true); // Adicionar distinct pode ser útil
            root.fetch("cliente", jakarta.persistence.criteria.JoinType.LEFT);
            root.fetch("produto", jakarta.persistence.criteria.JoinType.LEFT);


            List<Predicate> predicates = new ArrayList<>();
            String likeTerm = "%" + termo.toLowerCase() + "%";

            // Busca por nome do cliente
            predicates.add(cb.like(cb.lower(root.get("cliente").get("nomeCliente")), likeTerm));
            // Busca por nome do produto
            predicates.add(cb.like(cb.lower(root.get("produto").get("nome")), likeTerm));

            // Tenta converter o termo para Integer para buscar por ID da Venda
            try {
                Integer idVenda = Integer.parseInt(termo);
                predicates.add(cb.equal(root.get("idVenda"), idVenda));
            } catch (NumberFormatException e) {
                // Não é um número, ignora a busca por ID
            }

            // Tenta converter o termo para LocalDate para buscar por dataInicio
            // Suporta formatos como YYYY-MM-DD e DD/MM/YYYY
            try {
                LocalDate dataBusca;
                if (termo.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    dataBusca = LocalDate.parse(termo, DateTimeFormatter.ISO_LOCAL_DATE);
                } else if (termo.matches("\\d{2}/\\d{2}/\\d{4}")) {
                    dataBusca = LocalDate.parse(termo, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                } else {
                    throw new DateTimeParseException("Formato de data não suportado", termo, 0);
                }
                predicates.add(cb.equal(root.get("dataInicio"), dataBusca));
            } catch (DateTimeParseException e) {
                // Não é uma data no formato esperado, ignora a busca por data
            }

            return cb.or(predicates.toArray(new Predicate[0]));
        };
    }
    
 // NOVO MÉTODO DE BUSCA ESPECIFICADA PARA VENDA
    public static Specification<OrdemServico> comTermoOS(String termo) {
        return (root, query, cb) -> {
            if (termo == null || termo.trim().isEmpty()) {
                return cb.isTrue(cb.literal(true)); // Retorna todas as vendas se o termo for vazio
            }

            // Garante que os joins sejam feitos para buscar em Cliente e Produto
            // e também para evitar N+1 problemas ao carregar os dados relacionados.
            // Usar LEFT JOIN FETCH para carregar as entidades relacionadas na mesma query.
            // query.distinct(true); // Adicionar distinct pode ser útil
            root.fetch("cliente", jakarta.persistence.criteria.JoinType.LEFT);
            root.fetch("servico", jakarta.persistence.criteria.JoinType.LEFT);
            root.fetch("produto", jakarta.persistence.criteria.JoinType.LEFT);


            List<Predicate> predicates = new ArrayList<>();
            String likeTerm = "%" + termo.toLowerCase() + "%";

            // Busca por nome do cliente
            predicates.add(cb.like(cb.lower(root.get("cliente").get("nomeCliente")), likeTerm));
            // Busca por nome do servico
            predicates.add(cb.like(cb.lower(root.get("servico").get("nome")), likeTerm));
            // Busca por nome do produto
            predicates.add(cb.like(cb.lower(root.get("produto").get("nome")), likeTerm));

            // Tenta converter o termo para Integer para buscar por ID da Venda
            try {
                Integer idOS = Integer.parseInt(termo);
                predicates.add(cb.equal(root.get("idOS"), idOS));
            } catch (NumberFormatException e) {
                // Não é um número, ignora a busca por ID
            }

            // Tenta converter o termo para LocalDate para buscar por dataInicio
            // Suporta formatos como YYYY-MM-DD e DD/MM/YYYY
            try {
                LocalDate dataBusca;
                if (termo.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    dataBusca = LocalDate.parse(termo, DateTimeFormatter.ISO_LOCAL_DATE);
                } else if (termo.matches("\\d{2}/\\d{2}/\\d{4}")) {
                    dataBusca = LocalDate.parse(termo, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                } else {
                    throw new DateTimeParseException("Formato de data não suportado", termo, 0);
                }
                predicates.add(cb.equal(root.get("dataInicio"), dataBusca));
                predicates.add(cb.equal(root.get("dataFim"), dataBusca));
            } catch (DateTimeParseException e) {
                // Não é uma data no formato esperado, ignora a busca por data
            }

            return cb.or(predicates.toArray(new Predicate[0]));
        };
    }
}