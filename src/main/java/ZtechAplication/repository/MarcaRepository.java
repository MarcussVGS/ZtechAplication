package ZtechAplication.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ZtechAplication.model.Marca;

public interface MarcaRepository extends CrudRepository<Marca, String> {
	List<Marca> fingByNome(String nome);

	Marca findById(long id);

}
