package ZtechAplication.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import ZtechAplication.model.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Integer>{
	Optional<Cliente> findByCpf(String cpf);
	Optional<Cliente> deleteByCpf(String cpf);


}
