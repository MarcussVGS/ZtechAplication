package ZtechAplication.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ZtechAplication.model.Cliente;

public interface ClienteRepository extends 
				CrudRepository<Cliente, Integer>, JpaSpecificationExecutor<Cliente>{
	//  CUIDADO COM O OBJETO E O TIPO AQUI /\      /\ ESSE SPECIFICATION TORNA POSSIVEL O METODO BUSCA
//	ESSA QUERY ESPECIAL JUNTA TODOS OS DADOS, DEVE SER ALTERADA CONFORME A CLASSE
	@Query("SELECT c FROM Cliente c "
			+ "LEFT JOIN FETCH c.email "
			+ "LEFT JOIN FETCH c.telefone "
			+ "LEFT JOIN FETCH c.endereco")
    List<Cliente> findAllWithRelationships();
	
//	ESTE É O METODO QUE PRECISA CRIAR..
//	POIS O METODO PADRÃO BUSCA POR LONG E QUEREMOS BUSCAR POR INTEGER
	Optional<Cliente> findById(Integer id);
	
	
	Optional<Cliente> findByCpf(String cpf);
	Optional<Cliente> deleteByCpf(String cpf);

	
	
	
	
	

}
