package ZtechAplication.pagina;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping(value = "/cliente" ) //prefixo do URL
public class ClienteController {

	@Autowired
	private ClienteRepository classeRepo;
	
	//indicar o metodo get no HTML
	@RequestMapping(value = "/cadastrarForm")                 //Abre a tela de Cadastro Cliente
	public ModelAndView form() { //É uma função que retorna uma Model e uma View( neste caso apenas uma view)
		ModelAndView mv = new ModelAndView("cadastroCliente");//Nome direto do Templante, do HTMl
		mv.addObject("cliente", new ClienteDTO() ); //inicializa o obj para o formulario
		return mv;
	}
	
	//indica o metodo pos prefixo EX: /cliente/cadastrar
	@PostMapping(value = "/cadastrar")
	public String formCadastrar(@Validated ClienteDTO clienteDTO, //anotação que valida os dados vindo da DTO
										   BindingResult result, 
										   RedirectAttributes attributes) {
		// mesma anotação de erro do HTML, caso os campos estejam vazio
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos...");
			return "redirect:/cadastroCliente"; //redireciona para a propria tela direto
		}
		
//		Populamos o cliente com os dados da DTO
		Cliente cliente = new Cliente();
		cliente.setNomeCliente(clienteDTO.getNomeCliente());
		cliente.setCpf(clienteDTO.getCpf());
		
		
//		Populamos as outras tabelas dependentes com os dados vindo da DTO
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
		
//      Chamamos o metodo do repositorio para salvar o cliente, o CASCATE salva nas outras taelas relacionadas
		classeRepo.save(cliente); // salva todas as informaç~eos por conta do CASCATE
		attributes.addFlashAttribute("mensagem", "Cliente cadastrado(a) com sucesso!");
		return "redirect:/cliente/cadastrarForm"; //redireciona para a tela de cadastro com URL completa
	}
	
	//indica o metodo pos prefixo EX: /cliente/listar
	@RequestMapping(value = "/listar")     //É uma função que retorna uma Model e uma View...
	public ModelAndView listarCliente() { //  neste caso os dois, pois ela popula a tabela
		ModelAndView mv = new ModelAndView("clientes"); // chama o template
		List<Cliente> clientes = (List<Cliente>) classeRepo.findAllWithRelationships(); 
	//  /\ metodo de busca da repository que retorna uma lista de clinetes (precisa ser atualizado para cada tabela)
		mv.addObject("clientes", clientes);         //adiciona um OBJETO chamado "clientes" a view, que vai ser a referencia usada dentro do html
		return mv;//retorna a model/OBJETO e a view // clientes é a lista passada atraves do objeto "clientes"
	}

	//indica o metodo pos prefixo EX: /cliente/buscar
	@RequestMapping("/buscar")// \/ Variavel trasida pela URL para fazer a pesquisa
	public String buscar (@RequestParam(value ="termo", required=false) String termo,
						  @PageableDefault (size=12 ) Pageable pageale, //numero de respostas por pagina
						  Model model 	) { // Instacia uma model
		model.addAttribute("clientes", pesquisar(termo, pageale)); //OBJETO referencia Clientes, metodo expecifico pesquisar
		model.addAttribute("termo", termo);//esse eu n sei         //o pesquisar esta no fim do codigo
		return "clientes"; // retorna o OBjeto
	}
	
	//indica o metodo pos prefixo EX: /cliente/esitarForm/3
	@RequestMapping(value = "/editarForm/{idCliente}")
	public ModelAndView editarCliente(@PathVariable Integer idCliente) {//carrega o ID na URL
		ModelAndView mv = new ModelAndView("alterarCliente"); //chama a tela de alteração (TEM QUE CRIAR PARA AS OUTRAS TABELAS, A DE CLIENTE JA TEM)
	    Cliente cliente = classeRepo.findById(idCliente) //Metodo procurar por id do repositori (tem q criar)
	        .orElseThrow(() -> new IllegalArgumentException("Cliente inválido: " + idCliente)); //caso o id não exista
	    
	    // Converte para DTO
	    ClienteDTO clienteDTO = converterParaDTO(cliente); //passa todod os dados do cliente encontrado para a DTO (metodo no fim do codigo)
	    System.out.println(clienteDTO.toString());// teste, ignora
	    mv.addObject("cliente", clienteDTO); //adiciona um OBJETO chamado "cliente" a view, que vai ser a referencia usada dentro do html
	    return mv;							 // clienteDTO são os dados passados atraves do objeto "cliente"
	}
	
	//indica o metodo pos prefixo EX: /cliente/esitarForm/3
	// ELA PRECISA SER POST
	@PostMapping(value = "/editar/{idCliente}") // RECEBEMOS O OBJETO E INSTANCIAMOS O DTO QUE O RECEBE
	public String formEditar(@ModelAttribute("cliente") @Validated ClienteDTO clienteDTO,
							 @PathVariable Integer idCliente, //PUXAMOS O ID VIA URL
							 BindingResult result, 
							 RedirectAttributes attributes) {
//      CRIA UM OBJETO RECEBENDO AS INFORMAÇÕES DO ID QUE QUEREMOS ALTERAR
		Cliente cliente = classeRepo.findById(idCliente) //FAZ AS DEVIDAS VALIDAÇÕES
			    .orElseThrow(() -> new IllegalArgumentException("Cliente inválido: " + idCliente));
		if (result.hasErrors()) { // VALIDAÇÃO DE POPULAÇÃO DOS CAMPOS
			attributes.addFlashAttribute("mensagem", "Verifique os campos...");
			cliente.setIdCliente(idCliente);     
			return "/editar/{idCliente}";
		}
		
// A INTENÇÃO É ALTERAR UM ITEM Q JA EXISTE, ENTÃO N VAMOS INSTANCIAR NOVAMENTE...
// VAMOS APENAS ATUALIZAR OS CAMPOS E MANDAR PARA O SPRING COM O ID...
// ELE É INTELIGENTE E SABE QUE PELO ID JA EXISTIR NO BANCO É UM UPDATE E NÃO UM CREATE
//		Populamos o cliente com os dados da DTO
		cliente.setIdCliente(idCliente);
		cliente.setNomeCliente(clienteDTO.getNomeCliente());
		cliente.setCpf(clienteDTO.getCpf());
		
//		Populamos as outras tabelas dependentes com os dados vindo da DTO
		cliente.getEmail().setEmail(clienteDTO.getEndEmail());
		
		cliente.getTelefone().setTelefone(clienteDTO.getTelefone());
		
		cliente.getEndereco().setRua(clienteDTO.getRua());
		cliente.getEndereco().setCep(clienteDTO.getCep());
		cliente.getEndereco().setBairro(clienteDTO.getBairro());
		cliente.getEndereco().setCidade(clienteDTO.getCidade());
		cliente.getEndereco().setNumeroCasa(clienteDTO.getNumeroCasa());
		
		classeRepo.save(cliente); //MESMO METODO DE SALVAR, MAS AGORA PASSAMOS O ID QUANDO POPULAMOS OS CAMPOS
		attributes.addFlashAttribute("mensagem", "Cliente atualizado(a) com sucesso!");
		return "redirect:/cliente/listar"; //MANDA PARA  TELA DE LISTAR
	}
		
		
	//indica o metodo pos prefixo EX: /cliente/deletar/3
	@RequestMapping(value = "/deletar/{idCliente}")
	public String remover(@PathVariable Integer idCliente, RedirectAttributes attributes) {
		Cliente cliente = classeRepo.findById(idCliente) //CRIA UM OBJ CLIENTE PELO ID
	            .orElseThrow(() -> new IllegalArgumentException("Cliente inválido: " + idCliente));
	    classeRepo.delete(cliente); // PASSA O OBJETO PARA A FUNÇÃO DELETE 
        attributes.addFlashAttribute("mensagem", "Cliente removida com sucesso!");
        return "redirect:/cliente/listar"; // MANDA NOVAMENTE PARA QUE RECARREGUE A LISTA DE CLIENTES
	}
	
	

//	METODOS AUXILIARES
	
//	ESTE METODO É RESPONSAVEL POR REALIZAR UMA BUSCA COM ATRIBUTOS ESPECIAIS CONFIGURADOS MANUALMENTE...
//  METODO ESPECIAL QUE USA UMA CLASSE CHAMADA "SPECIFICATIONcONTROLLER"...
//	DEVE SE CRIAR UM METODO NA ESPECIFICATION PARA CADA CONSULTA...
//	ATENÇÃO::: DEVE FAZER UM EXTENDS NO REPOSITORY
	public Page<Cliente> pesquisar(String termo, Pageable pegeable){
		return classeRepo.findAll(
				SpecificationController.comTermoCli(termo), 
				pegeable);
	}
	
//	METODO QUE PASSA TODOS OS DADOS DAS MODELS PARA A DTO
//	O METODO JA RETORNA A DTO PREENCHIDA
	private ClienteDTO converterParaDTO(Cliente cliente) {
	    ClienteDTO dto = new ClienteDTO(); //PRIMEIRO A PRINCIPAL
	    dto.setIdCliente(cliente.getIdCliente());
	    dto.setNomeCliente(cliente.getNomeCliente());
	    dto.setCpf(cliente.getCpf());
	    
	    // Verifica e converte Email (se existir)// JA SE FAZ ESSA VERIFICAÇÃO EM TELA...
	    if (cliente.getEmail() != null) {        //MAS N CUSTA NADA FAZER DNV
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
	
}
