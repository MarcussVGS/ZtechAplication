package ZtechAplication.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ZtechAplication.model.Produto;

public interface ProdutoRepository extends 
				CrudRepository<Produto, Integer>, JpaSpecificationExecutor<Produto> {
	@Query("SELECT p FROM Produto p "
			+ "LEFT JOIN FETCH p.marca "
			+ "LEFT JOIN FETCH p.categoria")
    List<Produto> findAllWithRelationships();
	
	Optional<Produto> findById(Integer idProduto);
}
