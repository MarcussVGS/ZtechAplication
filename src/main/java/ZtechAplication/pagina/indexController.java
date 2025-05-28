package ZtechAplication.pagina;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // Importar Model
import org.springframework.web.bind.annotation.RequestMapping;

import ZtechAplication.repository.ClienteRepository;
import ZtechAplication.repository.OrdemServicoRepository;
import ZtechAplication.repository.ProdutoRepository;
import ZtechAplication.model.OrdemServico; // Importar OrdemServico

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class indexController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;
	
	@RequestMapping("/")
	public String index() {
		return "index"; // Tela de login/entrada
	}
	
    // Método para a página inicial/dashboard
	@RequestMapping("/inicio")
	public String inicio(Model model) { // Adicionar Model como parâmetro
        // 1. Quantidade total de produtos
        long totalProdutos = produtoRepository.count();
        model.addAttribute("totalProdutos", totalProdutos);

        // 2. Quantidade total de clientes
        long totalClientes = clienteRepository.count();
        model.addAttribute("totalClientes", totalClientes);

        // 3. Status e quantidade de Ordens de Serviço
        List<OrdemServico> todasAsOS = ordemServicoRepository.findAllWithRelationships(); // Usar método que carrega relacionamentos

        // Agrupar OS por nome do serviço e depois por status (Em Andamento/Concluída)
        Map<String, Map<String, Long>> osStatusPorTipo = todasAsOS.stream()
            .filter(os -> os.getServico() != null && os.getServico().getNome() != null) // Garante que o serviço e seu nome não sejam nulos
            .collect(Collectors.groupingBy(
                os -> os.getServico().getNome(), // Chave externa: Nome do Serviço (Tipo de OS)
                Collectors.groupingBy(
                    os -> (os.getDataFim() == null) ? "Em Andamento" : "Concluída", // Chave interna: Status
                    Collectors.counting() // Valor: Contagem
                )
            ));
        
        model.addAttribute("osStatusPorTipo", osStatusPorTipo);
        model.addAttribute("totalOS", todasAsOS.size());


      

		return "inicio"; // Nome do template inicio.html
	}

	@RequestMapping("/clientes")
	public String clientes() {
        // Este método deve ser tratado pelo ClienteController.listarClientes agora
        // Se quiser manter um link direto para a view sem dados, não é ideal.
		// return "clientes"; 
        return "redirect:/cliente/listar"; // Redireciona para o controller correto
	}
	@RequestMapping("/estoque")
	public String estoque() {
        // Similar ao /clientes, redirecionar para o controller de produto
		// return "estoque";
        return "redirect:/produto/listar";
	}
	@RequestMapping("/vendas")
	public String vendas() {
        // Redirecionar para o controller de vendas
		// return "vendas";
        return "redirect:/vendas/listar";
	}
	@RequestMapping("/ordens")
	public String ordens() {
        // Redirecionar para o controller de ordens de serviço
		// return "ordens";
        return "redirect:/ordens/listar";
	}
	@RequestMapping("/cadastro_cliente")
	public String cadastro_cliente() {
        // Redirecionar para o formulário de cadastro no ClienteController
		// return "cadastro_cliente";
        return "redirect:/cliente/cadastrarForm";
	}
	@RequestMapping("/cadastro_produto")
	public String cadastro_produto() {
        // Redirecionar para o formulário de cadastro no ProdutoController
		// return "cadastro_produto";
        return "redirect:/produto/cadastrarForm";
	}
	@RequestMapping("/cadastro_OS")
	public String cadastro_OS() {
        // Redirecionar para o formulário de cadastro no OrdemServicoController
		// return "cadastro_OS";
        return "redirect:/ordens/cadastrarForm";
	}
	
}