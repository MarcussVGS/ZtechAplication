package ZtechAplication.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import ZtechAplication.model.Categoria;
import ZtechAplication.model.Cliente;

public interface CategoriaRepository extends CrudRepository<Categoria, Integer>{
	Optional<Categoria> findByNome(String cate);

}
