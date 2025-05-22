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

import ZtechAplication.model.Estoque;
import ZtechAplication.repository.EstoqueRepository;


@RestController
@RequestMapping(value = "/estoque")
public class EstoqueController {

	@Autowired
	private EstoqueRepository classeRepo;
	
	//indicar o metodo get no HTML
	@GetMapping(value = "/formEstoque")
	public ModelAndView form() {
		ModelAndView mv = new ModelAndView("cadastroProduto/estoque");
		mv.addObject("estoque", new Estoque() ); //inicializa o obj para o formulario
		return mv;
	}
	
	//indicar o metodo post no HTML
	@PostMapping(value = "/cadastrarEstoque")
	public String form(@Validated Estoque estoque, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos...");
			return "redirect:/estoque/cadastrarEstoque";
		}
		
		classeRepo.save(estoque);
		attributes.addFlashAttribute("mensagem", "Estoque cadastrada com sucesso!");
		return "redirect:/estoque/cadastrarEstoque";
	}
	
	@RequestMapping(value = "/listar")
	public ModelAndView listarCliente() {
		ModelAndView mv = new ModelAndView("vendas");
		return mv;
	}
	
	@PutMapping(value = "/editarEstoque/{id}")
	public ModelAndView editarEstoque(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("/estoque/editarEstoque");
		mv.addObject("estoque", classeRepo.findById(id).orElseThrow( () -> 
					 new IllegalArgumentException("Estoque invalida" + id) ));
		return mv;
	}
	
	@DeleteMapping(value = "/deletarEstoque/{id}")
	public String remover(@PathVariable Long id, RedirectAttributes attributes) {
        classeRepo.deleteById(id);
        attributes.addFlashAttribute("mensagem", "Estoque removida com sucesso!");
        return "redirect:/estoque/cadastrarEstoque";
	}
	
	@GetMapping(value = "/teste")
	public String teste (){
		return "correto";
	}
	
}
