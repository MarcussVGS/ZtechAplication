package ZtechAplication.pagina;


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
	public String form(@Validated Produto produto, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos...");
			return "redirect:/produto/cadastrarProduto";
		}
		
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
	
	@GetMapping(value = "/teste")
	public String teste (){
		return "correto";
	}
	
}
