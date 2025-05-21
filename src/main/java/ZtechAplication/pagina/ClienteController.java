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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ZtechAplication.DTO.ClienteDTO;
import ZtechAplication.model.Cliente;
import ZtechAplication.model.Email;
import ZtechAplication.model.Endereco;
import ZtechAplication.model.Telefone;
import ZtechAplication.repository.ClienteRepository;


@Controller
@RequestMapping(value = "/cliente" ) //para que qualquer um deles seja valido
public class ClienteController {

	@Autowired
	private ClienteRepository classeRepo;
	
	//indicar o metodo get no HTML
	@GetMapping(value = "/cadastrarForm") //popula os campos da tela de cadastro
	public ModelAndView form() {
		ModelAndView mv = new ModelAndView("cadastroCliente"); //editar cliente
		mv.addObject("cliente", new ClienteDTO() ); //inicializa o obj para o formulario
		return mv;
	}
	
	//indicar o metodo post no HTML
	@RequestMapping(value = "/cadastrar", method = RequestMethod.POST) //  \/aqui usamos uma classe só para coletar as informações
	public String form(@Validated ClienteDTO clienteDTO, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos...");
			return "redirect:/cadastroCliente";
		}
		
//		aqui passamos tudo para a cliente
		Cliente cliente = new Cliente();
		cliente.setNomeCliente(clienteDTO.getNomeCliente());
		cliente.setCpf(clienteDTO.getCpf());
		
		
		//		aqui mapeamos as classes model com a classe DTO que coletou os dados
		Email email = new Email();
		email.setEmail(clienteDTO.getEndEmail());
		email.setCliente(cliente);
		cliente.setEmail(email);
		
		Telefone tele = new Telefone();
		tele.setTelefone(clienteDTO.getTelefone());
		tele.setCliente(cliente);
		cliente.setTelefone(tele);
		
		Endereco end = new Endereco();
		end.setRua(clienteDTO.getRua());
		end.setCep(clienteDTO.getCep());
		end.setBairro(clienteDTO.getBairro());
		end.setCidade(clienteDTO.getCidade());
		end.setNumeroCasa(clienteDTO.getNumeroCasa());
		end.setCliente(cliente);
		cliente.setEndereco(end);
		

		classeRepo.save(cliente); // salva todas as informaç~eos por conta do CASCATE
		attributes.addFlashAttribute("mensagem", "Cliente cadastrado(a) com sucesso!");
		return "redirect:/cliente/cadastrarForm";
	}
	
	@RequestMapping(value = "/listar")
	public ModelAndView listarCliente() {
		ModelAndView mv = new ModelAndView("clientes");
		List<Cliente> clientes = (List<Cliente>) classeRepo.findAllWithRelationships(); // Conversão explícita
	    System.out.println("Clientes encontrados: " + clientes.size()); // Agora funciona!
		
		mv.addObject("clientes", clientes);
		return mv;
	}
	
	@RequestMapping(value = "/editar/{cpf}")
	public ModelAndView editarCliente(@PathVariable String cpf) {
		ModelAndView mv = new ModelAndView("/templates/cadastro_cliente");
		mv.addObject("cliente", classeRepo.findByCpf(cpf).orElseThrow( () -> 
					 new IllegalArgumentException("Cliente invalida" + cpf) ));
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
