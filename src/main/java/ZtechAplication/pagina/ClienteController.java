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
@RequestMapping(value = "/cliente" ) //para que qualquer um deles seja valido
public class ClienteController {

	@Autowired
	private ClienteRepository classeRepo;
	
	//indicar o metodo get no HTML
	@GetMapping(value = "/form") //popula os campos da tela de cadastro
	public ModelAndView form() {
		ModelAndView mv = new ModelAndView("cadastro_Cliente");
		mv.addObject("cliente", new Cliente() ); //inicializa o obj para o formulario
		return mv;
	}
	
	//indicar o metodo post no HTML
	@PostMapping(value = "/cadastrar")
	public String form(@Validated Cliente cliente, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos...");
			return "redirect:/clientes/form";
		}
		
		classeRepo.save(cliente);
		attributes.addFlashAttribute("mensagem", "Cliente cadastrada com sucesso!");
		return "redirect:/clientes/listar";
	}
	
	@RequestMapping(value = "/listar")
	public ModelAndView listarCliente() {
		ModelAndView mv = new ModelAndView("clientes");
		List<Cliente> clientes = (List<Cliente>) classeRepo.findAllWithRelationships(); // Conversão explícita
	    System.out.println("Clientes encontrados: " + clientes.size()); // Agora funciona!
		
		mv.addObject("clientes", clientes);
		return mv;
	}
	
	@RequestMapping(value = "/editar/{idCliente}")
	public ModelAndView editarCliente(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("editarCliente");
		mv.addObject("cliente", classeRepo.findById(id).orElseThrow( () -> 
					 new IllegalArgumentException("Cliente invalida" + id) ));
		return mv;
	}
	
	@RequestMapping(value = "/deletar/{idCliente}")
	public String remover(@PathVariable Long id, RedirectAttributes attributes) {
		Cliente cliente = classeRepo.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("Cliente inválido: " + id));
	    classeRepo.delete(cliente);
        attributes.addFlashAttribute("mensagem", "Cliente removida com sucesso!");
        return "redirect:/clientes/listar";
	}
	
	@GetMapping(value = "/teste")
	public String teste (){
		return "correto";
	}
	
}
