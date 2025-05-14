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

import ZtechAplication.model.Marca;
import ZtechAplication.repository.MarcaRepository;


@RestController
@RequestMapping(value = {"/marca", "/Marca"} ) //para que qualquer um deles seja valido
public class MarcaController {

	@Autowired
	private MarcaRepository mr;
	
	//indicar o metodo get no HTML
	@GetMapping(value = "/formMarca")
	public ModelAndView form() {
		ModelAndView mv = new ModelAndView("cadastroProduto/marca");
		mv.addObject("marca", new Marca() ); //inicializa o obj para o formulario
		return mv;
	}
	
	//indicar o metodo post no HTML
	@PostMapping(value = "/cadastrarMarca")
	public String form(@Validated Marca marca, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos...");
			return "redirect:/marca/cadastrarMarca";
		}
		
		mr.save(marca);
		attributes.addFlashAttribute("mensagem", "Marca cadastrada com sucesso!");
		return "redirect:/marca/cadastrarMarca";
	}
	
	@GetMapping(value = "/listarMarca")
	public ModelAndView listarMarca() {
		ModelAndView mv = new ModelAndView("/marca/listarMarcas");
		mv.addObject("marca", mr.findAll());
		return mv;
	}
	
	@PutMapping(value = "/editarMarca/{id}")
	public ModelAndView editarMarca(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("/marca/editarMarca");
		mv.addObject("marca", mr.findById(id).orElseThrow( () -> 
					 new IllegalArgumentException("Marca invalida" + id) ));
		return mv;
	}
	
	@DeleteMapping(value = "/deletarMarca/{id}")
	public String remover(@PathVariable Long id, RedirectAttributes attributes) {
        mr.deleteById(id);
        attributes.addFlashAttribute("mensagem", "Marca removida com sucesso!");
        return "redirect:/marca/cadastrarMarca";
	}
	
	@GetMapping(value = "/teste")
	public String teste (){
		return "correto";
	}
	
}
