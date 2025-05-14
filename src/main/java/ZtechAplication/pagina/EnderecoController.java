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

import ZtechAplication.model.Endereco;
import ZtechAplication.repository.EnderecoRepository;


@RestController
@RequestMapping(value = {"/endereco", "/Endereco"} ) //para que qualquer um deles seja valido
public class EnderecoController {

	@Autowired
	private EnderecoRepository classeRepo;
	
	//indicar o metodo get no HTML
	@GetMapping(value = "/formEndereco")
	public ModelAndView form() {
		ModelAndView mv = new ModelAndView("cadastroProduto/endereco");
		mv.addObject("endereco", new Endereco() ); //inicializa o obj para o formulario
		return mv;
	}
	
	//indicar o metodo post no HTML
	@PostMapping(value = "/cadastrarEndereco")
	public String form(@Validated Endereco endereco, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos...");
			return "redirect:/endereco/cadastrarEndereco";
		}
		
		classeRepo.save(endereco);
		attributes.addFlashAttribute("mensagem", "Endereco cadastrada com sucesso!");
		return "redirect:/endereco/cadastrarEndereco";
	}
	
	@GetMapping(value = "/listarEndereco")
	public ModelAndView listarEndereco() {
		ModelAndView mv = new ModelAndView("/endereco/listarEnderecos");
		mv.addObject("endereco", classeRepo.findAll());
		return mv;
	}
	
	@PutMapping(value = "/editarEndereco/{id}")
	public ModelAndView editarEndereco(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("/endereco/editarEndereco");
		mv.addObject("endereco", classeRepo.findById(id).orElseThrow( () -> 
					 new IllegalArgumentException("Endereco invalida" + id) ));
		return mv;
	}
	
	@DeleteMapping(value = "/deletarEndereco/{id}")
	public String remover(@PathVariable Long id, RedirectAttributes attributes) {
        classeRepo.deleteById(id);
        attributes.addFlashAttribute("mensagem", "Endereco removida com sucesso!");
        return "redirect:/endereco/cadastrarEndereco";
	}
	
	@GetMapping(value = "/teste")
	public String teste (){
		return "correto";
	}
	
}
