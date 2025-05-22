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

import ZtechAplication.model.OrdemServico;
import ZtechAplication.repository.OrdemServicoRepository;


@RestController
@RequestMapping(value = "/ordens")
public class OrdemServicoController {

	@Autowired
	private OrdemServicoRepository classeRepo;
	
	//indicar o metodo get no HTML
	@GetMapping(value = "/formOrdemServico")
	public ModelAndView form() {
		ModelAndView mv = new ModelAndView("cadastroProduto/ordemServico");
		mv.addObject("ordemServico", new OrdemServico() ); //inicializa o obj para o formulario
		return mv;
	}
	
	//indicar o metodo post no HTML
	@PostMapping(value = "/cadastrarOrdemServico")
	public String form(@Validated OrdemServico ordemServico, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos...");
			return "redirect:/ordemServico/cadastrarOrdemServico";
		}
		
		classeRepo.save(ordemServico);
		attributes.addFlashAttribute("mensagem", "OrdemServico cadastrada com sucesso!");
		return "redirect:/ordemServico/cadastrarOrdemServico";
	}
	
	@RequestMapping(value = "/listar")
	public ModelAndView listarCliente() {
		ModelAndView mv = new ModelAndView("ordens");
		return mv;
	}
	@PutMapping(value = "/editarOrdemServico/{id}")
	public ModelAndView editarOrdemServico(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("/ordemServico/editarOrdemServico");
		mv.addObject("ordemServico", classeRepo.findById(id).orElseThrow( () -> 
					 new IllegalArgumentException("OrdemServico invalida" + id) ));
		return mv;
	}
	
	@DeleteMapping(value = "/deletarOrdemServico/{id}")
	public String remover(@PathVariable Long id, RedirectAttributes attributes) {
        classeRepo.deleteById(id);
        attributes.addFlashAttribute("mensagem", "OrdemServico removida com sucesso!");
        return "redirect:/ordemServico/cadastrarOrdemServico";
	}
	
	@GetMapping(value = "/teste")
	public String teste (){
		return "correto";
	}
	
}
