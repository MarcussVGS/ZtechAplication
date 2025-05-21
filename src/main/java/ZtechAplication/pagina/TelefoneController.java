package ZtechAplication.pagina;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

import ZtechAplication.model.Telefone;
import ZtechAplication.repository.TelefoneRepository;


@Controller
@RequestMapping(value = "/telefone") //para que qualquer um deles seja valido
public class TelefoneController {

	@Autowired
	private TelefoneRepository classeRepo;
	
	//indicar o metodo get no HTML
	@GetMapping(value = "/form")
	public ModelAndView form() {
		ModelAndView mv = new ModelAndView("cadastro_cliente");  //editar cliente
		mv.addObject("telefone", new Telefone() ); //inicializa o obj para o formulario
		return mv;
	}
	
	//indicar o metodo post no HTML
	@PostMapping(value = "/cadastrarTelefone")
	public String form(@Validated Telefone telefone, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos...");
			return "redirect:clientes";
		}
		
		classeRepo.save(telefone);
		attributes.addFlashAttribute("mensagem", "Telefone cadastrada com sucesso!");
		return "redirect:/telefone/cadastrarTelefone";
	}
	
	@GetMapping(value = "/listarTelefone")
	public ModelAndView listarTelefone() {
		ModelAndView mv = new ModelAndView("/telefone/listarTelefones");
		mv.addObject("telefone", classeRepo.findAll());
		return mv;
	}
	
	@PutMapping(value = "/editarTelefone/{id}")
	public ModelAndView editarTelefone(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("/telefone/editarTelefone");
		mv.addObject("telefone", classeRepo.findById(id).orElseThrow( () -> 
					 new IllegalArgumentException("Telefone invalida" + id) ));
		return mv;
	}
	
	@DeleteMapping(value = "/deletarTelefone/{id}")
	public String remover(@PathVariable Long id, RedirectAttributes attributes) {
        classeRepo.deleteById(id);
        attributes.addFlashAttribute("mensagem", "Telefone removida com sucesso!");
        return "redirect:/telefone/cadastrarTelefone";
	}
	
	@GetMapping(value = "/teste")
	public String teste (){
		return "correto";
	}
	
}
