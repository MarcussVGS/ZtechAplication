package ZtechAplication.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ZtechAplication.model.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Integer>{
	@Query("SELECT c FROM Cliente c "
			+ "LEFT JOIN FETCH c.email "
			+ "LEFT JOIN FETCH c.telefone "
			+ "LEFT JOIN FETCH c.endereco")
    List<Cliente> findAllWithRelationships();
	
	Optional<Cliente> findByCpf(String cpf);
	Optional<Cliente> deleteByCpf(String cpf);

	Optional<Cliente> findById(Integer id);
	
	@Modifying
    @Query("UPDATE Cliente c SET c.nomeCliente = :nome, c.cpf = :cpf WHERE c.idCliente = :id")
	void updateCliente(@Param("id") Integer id, 
	                     @Param("nome") String nome, 
	                     @Param("cpf") String cpf);
	    
	@Modifying
	@Query("UPDATE Email e SET e.endEmail = :email WHERE e.cliente.idCliente = :clienteId")
	void updateEmail(@Param("clienteId") Integer clienteId,
	                   @Param("email") String email);
	    
	@Modifying
	@Query("UPDATE Telefone t SET t.telefone = :telefone WHERE t.cliente.idCliente = :clienteId")
	void updateTelefone(@Param("clienteId") Integer clienteId,
	                      @Param("telefone") String telefone);
	    
	@Modifying
	@Query("UPDATE Endereco e SET e.rua = :rua, e.cep = :cep, e.bairro = :bairro, " +
	       "e.cidade = :cidade, e.numeroCasa = :numero WHERE e.cliente.idCliente = :clienteId")
	void updateEndereco(@Param("clienteId") Integer clienteId,
	                    @Param("rua") String rua,
	                    @Param("cep") String cep,
	                    @Param("bairro") String bairro,
	                    @Param("cidade") String cidade,
	                    @Param("numero") Integer numero);


}
