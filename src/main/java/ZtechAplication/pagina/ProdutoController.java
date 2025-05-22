package ZtechAplication.pagina;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ZtechAplication.DTO.ProdutoDTO;
import ZtechAplication.model.Categoria;
import ZtechAplication.model.Marca;
import ZtechAplication.model.Produto;
import ZtechAplication.repository.ProdutoRepository;


@RestController
@RequestMapping(value = {"/produto", "/Produto"} ) //para que qualquer um deles seja valido
public class ProdutoController {

	@Autowired
	private ProdutoRepository classeRepo;
	
	//indicar o metodo get no HTML
	@GetMapping(value = "/formProduto")
	public ModelAndView form() {
		ModelAndView mv = new ModelAndView("cadastroProduto/produto");
		mv.addObject("produto", new Produto() ); //inicializa o obj para o formulario
		return mv;
	}
	
	//indicar o metodo post no HTML
	@PostMapping(value = "/cadastrarProduto")
	public String form(@Validated ProdutoDTO produtoDTO, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos...");
			return "redirect:/produto/cadastrarProduto";
		}
		
		// aqui passamos tudo para produto
		Produto produto = new Produto();
		produto.setNome(produtoDTO.getNome());
		produto.setCusto(produtoDTO.getCusto());
		produto.setDescricao(produtoDTO.getDescricao());
		produto.setValor(produtoDTO.getValor());
		
		// aqui mapeamos as classes model com a classe DTO que coletou os dados
		Categoria categoria = new Categoria();
		categoria.setNome(produtoDTO.getCategoria());
		categoria.setProdutos((List<Produto>) produto);
		produto.setCategoria(categoria);
		
		Marca marca = new Marca();
		marca.setNome(produtoDTO.getMarca());
		marca.setProdutos((List<Produto>) produto);
		produto.setMarca(marca);
		
		
		classeRepo.save(produto);
		attributes.addFlashAttribute("mensagem", "Produto cadastrada com sucesso!");
		return "redirect:/produto/cadastrarProduto";
	}
	
	@GetMapping(value = "/listarProduto")
	public ModelAndView listarProduto() {
		ModelAndView mv = new ModelAndView("/produto/listarProdutos");
		
		mv.addObject("produto", classeRepo.findAll());
		return mv;
	}
	
	@PutMapping(value = "/editarProduto/{id}")
	public ModelAndView editarProduto(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("/produto/editarProduto");
		mv.addObject("produto", classeRepo.findById(id).orElseThrow( () -> 
					 new IllegalArgumentException("Produto invalida" + id) ));
		return mv;
	}
	
	@DeleteMapping(value = "/deletarProduto/{id}")
	public String remover(@PathVariable Long id, RedirectAttributes attributes) {
        classeRepo.deleteById(id);
        attributes.addFlashAttribute("mensagem", "Produto removida com sucesso!");
        return "redirect:/produto/cadastrarProduto";
	}
	
	private ProdutoDTO converterParaDTO(Produto produto) {
	    ProdutoDTO dto = new ProdutoDTO();
	    
	    // Atributos básicos
	    dto.setIdProduto(produto.getIdProduto());
	    dto.setNome(produto.getNome());
	    dto.setCusto(produto.getCusto());
	    dto.setValor(produto.getValor());
	    dto.setDescricao(produto.getDescricao());

	    // Verifica e converte Categoria (se existir)
	    if (produto.getCategoria() != null) {
	        dto.setIdCategoria(produto.getCategoria().getIdCategoria());
	        dto.setNome(produto.getCategoria().getNome()); // Adicione este campo no DTO se necessário
	    } else {
	        dto.setIdCategoria(null); // ou um valor padrão
	    }

	    // Verifica e converte Marca (se existir)
	    if (produto.getMarca() != null) {
	        dto.setIdMarca(produto.getMarca().getIdMarca());
	        dto.setNome(produto.getMarca().getNome()); // Adicione este campo no DTO se necessário
	    } else {
	        dto.setIdMarca(null); // ou um valor padrão
	    }

	    return dto;
	}
	
	@GetMapping(value = "/teste")
	public String teste (){
		return "correto";
	}
	
	
	
}
