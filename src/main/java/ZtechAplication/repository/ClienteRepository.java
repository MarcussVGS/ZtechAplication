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
	@Query("SELECT c FROM Cliente c "
			+ "LEFT JOIN FETCH c.email "
			+ "LEFT JOIN FETCH c.telefone "
			+ "LEFT JOIN FETCH c.endereco")
    List<Cliente> findAllWithRelationships();
	
	Optional<Cliente> findByCpf(String cpf);
	Optional<Cliente> deleteByCpf(String cpf);

	Optional<Cliente> findById(Integer id);
	
	
	
	

}
