package ZtechAplication.pagina;


import java.util.List;

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

import ZtechAplication.model.Cliente;
import ZtechAplication.repository.ClienteRepository;


@Controller
//@RequestMapping(value = "/cliente" ) //para que qualquer um deles seja valido
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
	
	@RequestMapping(value = "/listarCliente")
	public ModelAndView listarCliente() {
		ModelAndView mv = new ModelAndView("/clientes");
		List<Cliente> clientes = (List<Cliente>) classeRepo.findAll(); // Conversão explícita
	    System.out.println("Clientes encontrados: " + clientes.size()); // Agora funciona!
		
		mv.addObject("clientes", classeRepo.findAll());
		return mv;
	}
	
	@RequestMapping(value = "/editarCliente")
	public ModelAndView editarCliente(@PathVariable String cpf) {
		ModelAndView mv = new ModelAndView("/cliente/editarCliente");
		mv.addObject("cliente", classeRepo.findByCpf(cpf).orElseThrow( () -> 
					 new IllegalArgumentException("Cliente invalida" + cpf) ));
		return mv;
	}
	
	@RequestMapping(value = "/deletarCliente")
	public String remover(@PathVariable String cpf, RedirectAttributes attributes) {
		classeRepo.findByCpf(cpf).orElseThrow(() -> 
        new IllegalArgumentException("Cliente inválido: " + cpf));
		classeRepo.deleteByCpf(cpf);
        attributes.addFlashAttribute("mensagem", "Cliente removida com sucesso!");
        return "redirect:/cliente/cadastrarCliente";
	}
	
	@GetMapping(value = "/teste")
	public String teste (){
		return "correto";
	}
	
}
