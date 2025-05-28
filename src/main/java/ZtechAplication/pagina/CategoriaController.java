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

import ZtechAplication.model.Categoria;
import ZtechAplication.repository.CategoriaRepository;

@RestController
@RequestMapping(value = "/categoria")
public class CategoriaController {
	
	@Autowired
	private CategoriaRepository classeRepo;
	
	@GetMapping(value = "dataList")
	public List<Categoria> listarCategorias() {
        return (List<Categoria>) classeRepo.findAll();
    }
	
	//indicar o metodo get no HTML
	@GetMapping(value = "/formCategoria")
	public ModelAndView form() {
		ModelAndView mv = new ModelAndView("cadastroProduto/Categoria");
		mv.addObject("Categoria", new Categoria() ); //inicializa o obj para o formulario
		return mv;
	}
	
	//indicar o metodo post no HTML
	@PostMapping(value = "/cadastrarCategoria")
	public String form(@Validated Categoria categoria, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos...");
			return "redirect:/categoria/cadastrarCategoria";
		}
		
		classeRepo.save(categoria);
		attributes.addFlashAttribute("mensagem", "Categoria cadastrada com sucesso!");
		return "redirect:/categoria/cadastrarCategoria";
	}
	
	@GetMapping(value = "/listarCategoria")
	public ModelAndView listarCategoria() {
		ModelAndView mv = new ModelAndView("/categoria/listarCategoria");
		mv.addObject("categoria", classeRepo.findAll());
		return mv;
	}
	
	@PutMapping(value = "/editarCategoria/{id}")
	public ModelAndView editarCategoria(@PathVariable Integer id) {
		ModelAndView mv = new ModelAndView("/categoria/editarCategoria");
		mv.addObject("categoria", classeRepo.findById(id).orElseThrow( () -> 
					 new IllegalArgumentException("Categoria invalida" + id) ));
		return mv;
	}
	
	@DeleteMapping(value = "/deletarCategoria/{id}")
	public String remover(@PathVariable Integer id, RedirectAttributes attributes) {
        classeRepo.deleteById(id);
        attributes.addFlashAttribute("mensagem", "Categoria removida com sucesso!");
        return "redirect:/categoria/cadastrarCategoria";
	}
	
	@GetMapping(value = "/teste")
	public String teste (){
		return "correto";
	}

}
