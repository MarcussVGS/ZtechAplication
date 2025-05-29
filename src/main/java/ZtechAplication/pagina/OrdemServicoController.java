package ZtechAplication.pagina;


import java.time.LocalDate;
import java.time.LocalTime;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ZtechAplication.DTO.OrdemServicoDTO;
import ZtechAplication.DTO.VendaDTO;
import ZtechAplication.model.Cliente;
import ZtechAplication.model.OrdemServico;
import ZtechAplication.model.Produto;
import ZtechAplication.model.Servico;
import ZtechAplication.model.Venda;
import ZtechAplication.repository.ClienteRepository;
import ZtechAplication.repository.OrdemServicoRepository;
import ZtechAplication.repository.ProdutoRepository;
import ZtechAplication.repository.ServicoRepository;


@Controller
@RequestMapping(value = "/ordens")
public class OrdemServicoController {

	@Autowired
	private OrdemServicoRepository classeRepo;
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private ClienteRepository clienteRepository;
	
	//indicar o metodo get no HTML
	@GetMapping(value = "/cadastrarForm")
	public ModelAndView form() {
        ModelAndView mv = new ModelAndView("cadastro_OS");
        OrdemServicoDTO osDTO = new OrdemServicoDTO();
        osDTO.setDataInicio(LocalDate.now());
        osDTO.setHoraInicio(LocalTime.now());
      mv.addObject("ordemServico", osDTO);
      mv.addObject("produtos", produtoRepository.findAllWithRelationships());
      mv.addObject("servicos", servicoRepository.findAll());
      mv.addObject("clientes", clienteRepository.findAllWithRelationships());
      return mv;
	}
	
	//indicar o metodo post no HTML
	@PostMapping(value = "/cadastrar")
	public String cadastrarOS(@Validated @ModelAttribute("ordemServico") OrdemServicoDTO osDTO, 
				  BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique os campos obrigatórios.");
            attributes.addFlashAttribute("produtos", produtoRepository.findAllWithRelationships());
            attributes.addFlashAttribute("servicos", servicoRepository.findAll());
            attributes.addFlashAttribute("clientes", clienteRepository.findAllWithRelationships());
            return "redirect:/ordens/cadastrarForm";
		}
		
		Produto produto = produtoRepository.findById(osDTO.getIdProduto())
                .orElseThrow(() -> new IllegalArgumentException("Produto inválido: " + osDTO.getIdProduto()));
		
		Servico servico = servicoRepository.findById(osDTO.getIdServico())
                .orElseThrow(() -> new IllegalArgumentException("Servico inválido: " + osDTO.getIdServico()));

        Cliente cliente = clienteRepository.findById(osDTO.getIdCliente())
                .orElseThrow(() -> new IllegalArgumentException("Cliente inválido: " + osDTO.getIdCliente()));
		
        if (produto.getQuantidade() < osDTO.getQuantidade()) {
            attributes.addFlashAttribute("mensagem", "Quantidade em estoque insuficiente para o produto: " + produto.getNome());
        }
		
        OrdemServico os = new OrdemServico();
        os.setDataInicio(osDTO.getDataInicio());
        os.setHoraInicio(osDTO.getHoraInicio());
        os.setQuantidade(osDTO.getQuantidade());
        os.setStatus("Registrada");
        os.setProduto(produto);
        os.setServico(servico);
        os.setCliente(cliente);
        os.setValor(servico.getValor().add(produto.getValor() ));
        os.setLucro(servico.getValor().add(produto.getValor().subtract(produto.getCusto()) ));
        
        classeRepo.save(os);
        attributes.addFlashAttribute("mensagem", "Ordem de Serviço cadastrada com sucesso!");
        return "redirect:/ordens/listar";
	}
	
	@GetMapping(value = "/listar")
	public String listarOS(@PageableDefault(size = 10) Pageable pageable, Model model) {
		Page<OrdemServico> paginaDeOSsEntidades = classeRepo.findAll(null, pageable);
        Page<OrdemServicoDTO> paginaDeOSDTOs = paginaDeOSsEntidades.map(this::converterParaDTO);
        
        model.addAttribute("paginaOrdens", paginaDeOSDTOs);
        if (!model.containsAttribute("termo")) { 
            model.addAttribute("termo", null);
        }
        return "ordens";
	}
	
	@GetMapping(value = "/editarForm/{idOS}")
	public ModelAndView editarForm(@PathVariable Integer idOS) {
		OrdemServico os = classeRepo.findById(idOS)
                .orElseThrow(() -> new IllegalArgumentException("Venda inválida: " + idOS));
        
        ModelAndView mv = new ModelAndView("alterarOS");
        mv.addObject("ordemServico", converterParaDTO(os));
        mv.addObject("produtos", produtoRepository.findAllWithRelationships());
        mv.addObject("servicos", servicoRepository.findAll());
        mv.addObject("clientes", clienteRepository.findAllWithRelationships());
        return mv;
	}
	
	@DeleteMapping(value = "/deletar/{idOS}")
	public String deletarOS(@PathVariable Integer idOS, RedirectAttributes attributes) {
		OrdemServico os = classeRepo.findById(idOS)
                .orElseThrow(() -> new IllegalArgumentException("Venda inválida: " + idOS));

        Produto produto = os.getProduto();
        produto.adicionarQuantidade(os.getQuantidade());
        produtoRepository.save(produto);

        classeRepo.delete(os);
        attributes.addFlashAttribute("mensagem", "Venda removida com sucesso e estoque restaurado!");
        return "redirect:/vendas/listar";
	}
	
	private OrdemServicoDTO converterParaDTO(OrdemServico os) {
		OrdemServicoDTO dto = new OrdemServicoDTO();
        dto.setIdOS(os.getIdOS());
        dto.setDataInicio(os.getDataInicio());
        dto.setHoraInicio(os.getHoraInicio());
        dto.setDataFim(os.getDataFim());
        dto.setHoraFim(os.getHoraFim());
        dto.setValor(os.getValor());
        dto.setLucro(os.getLucro());
        dto.setStatusOS(os.getStatus());
        dto.setQuantidade(os.getQuantidade());

        if (os.getProduto() != null) {
            dto.setIdProduto(os.getProduto().getIdProduto());
            dto.setNomeProduto(os.getProduto().getNome());
        }
        if (os.getServico() != null) {
            dto.setIdServico(os.getServico().getIdServico());
            dto.setNomeServico(os.getServico().getNome());
        }
        if (os.getCliente() != null) {
            dto.setIdCliente(os.getCliente().getIdCliente());
            dto.setNomeCliente(os.getCliente().getNomeCliente());
        }
        return dto;
    }
	
//	FAZER UMA BIBLIOTECA
	
	@GetMapping(value = "/teste")
	public String teste (){
		return "correto";
	}
	
}
