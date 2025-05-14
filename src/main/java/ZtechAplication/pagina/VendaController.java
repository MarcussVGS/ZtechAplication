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

import ZtechAplication.model.Venda;
import ZtechAplication.repository.VendaRepository;


@RestController
@RequestMapping(value = {"/venda", "/Venda"} ) //para que qualquer um deles seja valido
public class VendaController {

	@Autowired
	private VendaRepository classeRepo;
	
	//indicar o metodo get no HTML
	@GetMapping(value = "/formVenda")
	public ModelAndView form() {
		ModelAndView mv = new ModelAndView("cadastroProduto/venda");
		mv.addObject("venda", new Venda() ); //inicializa o obj para o formulario
		return mv;
	}
	
	//indicar o metodo post no HTML
	@PostMapping(value = "/cadastrarVenda")
	public String form(@Validated Venda venda, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos...");
			return "redirect:/venda/cadastrarVenda";
		}
		
		classeRepo.save(venda);
		attributes.addFlashAttribute("mensagem", "Venda cadastrada com sucesso!");
		return "redirect:/venda/cadastrarVenda";
	}
	
	@GetMapping(value = "/listarVenda")
	public ModelAndView listarVenda() {
		ModelAndView mv = new ModelAndView("/venda/listarVendas");
		mv.addObject("venda", classeRepo.findAll());
		return mv;
	}
	
	@PutMapping(value = "/editarVenda/{id}")
	public ModelAndView editarVenda(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("/venda/editarVenda");
		mv.addObject("venda", classeRepo.findById(id).orElseThrow( () -> 
					 new IllegalArgumentException("Venda invalida" + id) ));
		return mv;
	}
	
	@DeleteMapping(value = "/deletarVenda/{id}")
	public String remover(@PathVariable Long id, RedirectAttributes attributes) {
        classeRepo.deleteById(id);
        attributes.addFlashAttribute("mensagem", "Venda removida com sucesso!");
        return "redirect:/venda/cadastrarVenda";
	}
	
	@GetMapping(value = "/teste")
	public String teste (){
		return "correto";
	}
	
}
