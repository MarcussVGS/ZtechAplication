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

import ZtechAplication.model.Servico;
import ZtechAplication.repository.ServicoRepository;


@RestController
@RequestMapping(value = {"/servico", "/Servico"} ) //para que qualquer um deles seja valido
public class ServicoController {

	@Autowired
	private ServicoRepository classeRepo;
	
	//indicar o metodo get no HTML
	@GetMapping(value = "/formServico")
	public ModelAndView form() {
		ModelAndView mv = new ModelAndView("cadastroProduto/servico");
		mv.addObject("servico", new Servico() ); //inicializa o obj para o formulario
		return mv;
	}
	
	//indicar o metodo post no HTML
	@PostMapping(value = "/cadastrarServico")
	public String form(@Validated Servico servico, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos...");
			return "redirect:/servico/cadastrarServico";
		}
		
		classeRepo.save(servico);
		attributes.addFlashAttribute("mensagem", "Servico cadastrada com sucesso!");
		return "redirect:/servico/cadastrarServico";
	}
	
	@GetMapping(value = "/listarServico")
	public ModelAndView listarServico() {
		ModelAndView mv = new ModelAndView("/servico/listarServicos");
		mv.addObject("servico", classeRepo.findAll());
		return mv;
	}
	
	@PutMapping(value = "/editarServico/{id}")
	public ModelAndView editarServico(@PathVariable Integer id) {
		ModelAndView mv = new ModelAndView("/servico/editarServico");
		mv.addObject("servico", classeRepo.findById(id).orElseThrow( () -> 
					 new IllegalArgumentException("Servico invalida" + id) ));
		return mv;
	}
	
	@DeleteMapping(value = "/deletarServico/{id}")
	public String remover(@PathVariable Integer id, RedirectAttributes attributes) {
        classeRepo.deleteById(id);
        attributes.addFlashAttribute("mensagem", "Servico removida com sucesso!");
        return "redirect:/servico/cadastrarServico";
	}
	
	@GetMapping(value = "/teste")
	public String teste (){
		return "correto";
	}
	
}
