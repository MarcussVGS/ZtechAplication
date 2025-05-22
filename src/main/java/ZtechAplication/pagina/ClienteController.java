package ZtechAplication.pagina;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public String formCadastrar(@Validated ClienteDTO clienteDTO, BindingResult result, RedirectAttributes attributes) {
		
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
	
	@RequestMapping(value = "/editarForm/{idCliente}")
	public ModelAndView editarCliente(@PathVariable Integer idCliente) {
		ModelAndView mv = new ModelAndView("cadastroCliente");
	    Cliente cliente = classeRepo.findById(idCliente)
	        .orElseThrow(() -> new IllegalArgumentException("Cliente inválido: " + idCliente));
	    
	    // Converte para DTO
	    teste(idCliente);
	    ClienteDTO clienteDTO = converterParaDTO(cliente);
	    mv.addObject("cliente", clienteDTO);
	    return mv;
	}
	
	@GetMapping("/editar/{idCliente}")
	public String editarForm(@PathVariable("idCliente") Integer id, RedirectAttributes attributes, Model model ) {
	    Cliente cliente = classeRepo.findById(id)
			   .orElseThrow(() -> new IllegalArgumentException("Cliente inválido: " + id));
	    model.addAttribute("ObjetoCliente", cliente);
		return "redirect:/clientes/listar" ;
	}
	
	
	
	//indicar o metodo post no HTML
		@RequestMapping(value = "/editar/{idCliente}", method = RequestMethod.POST) //  \/aqui usamos uma classe só para coletar as informações
		public String formEditar(@Validated ClienteDTO clienteDTO,@PathVariable Integer id, BindingResult result, RedirectAttributes attributes) {
			
			if (result.hasErrors()) {
				attributes.addFlashAttribute("mensagem", "Verifique os campos...");
				return "/editar/{idCliente}";
			}
			
//			// Chama o serviço para atualização completa
            atualizarClienteCompleto(id, clienteDTO);
			

			attributes.addFlashAttribute("mensagem", "Cliente atualizado(a) com sucesso!");
			return "redirect:/cliente/cadastrarForm";
		}
	
	@RequestMapping(value = "/deletar/{idCliente}")
	public String remover(@PathVariable Integer idCliente, RedirectAttributes attributes) {
		Cliente cliente = classeRepo.findById(idCliente)
	            .orElseThrow(() -> new IllegalArgumentException("Cliente inválido: " + idCliente));
	    classeRepo.delete(cliente);
        attributes.addFlashAttribute("mensagem", "Cliente removida com sucesso!");
        return "redirect:/cliente/listar";
	}
	
	private ClienteDTO converterParaDTO(Cliente cliente) {
	    ClienteDTO dto = new ClienteDTO();
	    dto.setIdCliente(cliente.getIdCliente());
	    dto.setNomeCliente(cliente.getNomeCliente());
	    dto.setCpf(cliente.getCpf());
	    
	    // Verifica e converte Email (se existir)
	    if (cliente.getEmail() != null) {
	        dto.setEndEmail(cliente.getEmail().getEndEmail());
	    } else {
	        dto.setEndEmail(""); // Valor padrão
	    }
	    
	    // Verifica e converte Telefone (se existir)
	    if (cliente.getTelefone() != null) {
	        dto.setTelefone(cliente.getTelefone().getTelefone());
	    } else {
	        dto.setTelefone(""); // Valor padrão
	    }
	    
	    // Verifica e converte Endereco (se existir)
	    if (cliente.getEndereco() != null) {
	        Endereco end = cliente.getEndereco();
	        dto.setRua(end.getRua());
	        dto.setCep(end.getCep());
	        dto.setBairro(end.getBairro());
	        dto.setCidade(end.getCidade());
	        dto.setNumeroCasa(end.getNumeroCasa());
	    }
	    
	    return dto;
	}
	
	private void atualizarClienteCompleto(Integer id, ClienteDTO clienteDTO) {
	    // Atualiza cliente
	    classeRepo.updateCliente(id, clienteDTO.getNomeCliente(), clienteDTO.getCpf());
	    
	    // Atualiza relacionamentos
	    classeRepo.updateEmail(id, clienteDTO.getEndEmail());
	    classeRepo.updateTelefone(id, clienteDTO.getTelefone());
	    classeRepo.updateEndereco(
	        id,
	        clienteDTO.getRua(),
	        clienteDTO.getCep(),
	        clienteDTO.getBairro(),
	        clienteDTO.getCidade(),
	        clienteDTO.getNumeroCasa()
	    );
	    
	}
	
	@GetMapping(value = "/teste")
	public String teste (){
		return "correto";
	}
	
	public void teste(Integer inf) {
		System.out.print(inf);
	}
	
}
