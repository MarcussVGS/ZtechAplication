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

import ZtechAplication.model.Cliente;
import ZtechAplication.repository.ClienteRepository;


@RestController
@RequestMapping(value = {"/cliente", "/Cliente"} ) //para que qualquer um deles seja valido
public class ClienteController {

	@Autowired
	private ClienteRepository classeRepo;
	
	//indicar o metodo get no HTML
	@GetMapping(value = "/formCliente")
	public ModelAndView form() {
		ModelAndView mv = new ModelAndView("cadastroProduto/cliente");
		mv.addObject("cliente", new Cliente() ); //inicializa o obj para o formulario
		return mv;
	}
	
	//indicar o metodo post no HTML
	@PostMapping(value = "/cadastrarCliente")
	public String form(@Validated Cliente cliente, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos...");
			return "redirect:/cliente/cadastrarCliente";
		}
		
		classeRepo.save(cliente);
		attributes.addFlashAttribute("mensagem", "Cliente cadastrada com sucesso!");
		return "redirect:/cliente/cadastrarCliente";
	}
	
	@GetMapping(value = "/listarCliente")
	public ModelAndView listarCliente() {
		ModelAndView mv = new ModelAndView("/cliente/listarClientes");
		mv.addObject("cliente", classeRepo.findAll());
		return mv;
	}
	
	@PutMapping(value = "/editarCliente/{id}")
	public ModelAndView editarCliente(@PathVariable Integer id) {
		ModelAndView mv = new ModelAndView("/cliente/editarCliente");
		mv.addObject("cliente", classeRepo.findById(id).orElseThrow( () -> 
					 new IllegalArgumentException("Cliente invalida" + id) ));
		return mv;
	}
	
	@DeleteMapping(value = "/deletarCliente/{id}")
	public String remover(@PathVariable Integer id, RedirectAttributes attributes) {
        classeRepo.deleteById(id);
        attributes.addFlashAttribute("mensagem", "Cliente removida com sucesso!");
        return "redirect:/cliente/cadastrarCliente";
	}
	
	@GetMapping(value = "/teste")
	public String teste (){
		return "correto";
	}
	
}
