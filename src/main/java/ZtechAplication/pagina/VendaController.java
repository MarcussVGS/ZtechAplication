package ZtechAplication.pagina;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
// import java.util.List; // Removido se não usado diretamente

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification; // Importar Specification
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ZtechAplication.DTO.VendaDTO;
import ZtechAplication.model.Cliente;
import ZtechAplication.model.Produto;
import ZtechAplication.model.Venda;
import ZtechAplication.repository.ClienteRepository;
import ZtechAplication.repository.ProdutoRepository;
import ZtechAplication.repository.VendaRepository;

@Controller
@RequestMapping(value = "/vendas")
public class VendaController {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping(value = "/cadastrarForm")
    public ModelAndView form() {
        ModelAndView mv = new ModelAndView("cadastroVenda");
        VendaDTO vendaDTO = new VendaDTO();
        vendaDTO.setDataInicio(LocalDate.now());
        vendaDTO.setHoraInicio(LocalTime.now());
        mv.addObject("venda", vendaDTO);
        mv.addObject("produtos", produtoRepository.findAllWithRelationships());
        mv.addObject("clientes", clienteRepository.findAllWithRelationships());
        return mv;
    }

    @PostMapping(value = "/cadastrar")
    public String cadastrarVenda(@Validated @ModelAttribute("venda") VendaDTO vendaDTO, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique os campos obrigatórios.");
            attributes.addFlashAttribute("produtos", produtoRepository.findAllWithRelationships());
            attributes.addFlashAttribute("clientes", clienteRepository.findAllWithRelationships());
            return "redirect:/vendas/cadastrarForm";
        }

        Produto produto = produtoRepository.findById(vendaDTO.getIdProduto())
                .orElseThrow(() -> new IllegalArgumentException("Produto inválido: " + vendaDTO.getIdProduto()));

        Cliente cliente = clienteRepository.findById(vendaDTO.getIdCliente())
                .orElseThrow(() -> new IllegalArgumentException("Cliente inválido: " + vendaDTO.getIdCliente()));

        if (produto.getQuantidade() < vendaDTO.getQuantidade()) {
            attributes.addFlashAttribute("mensagem", "Quantidade em estoque insuficiente para o produto: " + produto.getNome());
            attributes.addFlashAttribute("venda", vendaDTO);
            attributes.addFlashAttribute("produtos", produtoRepository.findAllWithRelationships());
            attributes.addFlashAttribute("clientes", clienteRepository.findAllWithRelationships());
            return "redirect:/vendas/cadastrarForm";
        }

        Venda venda = new Venda();
        venda.setDataInicio(vendaDTO.getDataInicio());
        venda.setHoraInicio(vendaDTO.getHoraInicio());
        venda.setQuantidade(vendaDTO.getQuantidade());
        venda.setProduto(produto);
        venda.setCliente(cliente);
        venda.setValor(produto.getValor().multiply(new BigDecimal(vendaDTO.getQuantidade())));
        venda.setLucro((produto.getValor().subtract(produto.getCusto())).multiply(new BigDecimal(vendaDTO.getQuantidade())));

        produto.removerQuantidade(vendaDTO.getQuantidade());
        produtoRepository.save(produto);

        vendaRepository.save(venda);
        attributes.addFlashAttribute("mensagem", "Venda cadastrada com sucesso!");
        return "redirect:/vendas/listar";
    }

    @GetMapping(value = "/listar")
    public String listarVendas(@PageableDefault(size = 10) Pageable pageable, Model model) {
        Page<Venda> paginaDeVendasEntidades = vendaRepository.findAll(pageable);
        Page<VendaDTO> paginaDeVendaDTOs = paginaDeVendasEntidades.map(this::converterParaDTO);
        
        model.addAttribute("paginaVendas", paginaDeVendaDTOs);
        if (!model.containsAttribute("termo")) { 
            model.addAttribute("termo", null);
        }
        return "vendas";
    }

    @GetMapping(value = "/editarForm/{idVenda}")
    public ModelAndView editarForm(@PathVariable Integer idVenda) {
        Venda venda = vendaRepository.findById(idVenda)
                .orElseThrow(() -> new IllegalArgumentException("Venda inválida: " + idVenda));
        
        ModelAndView mv = new ModelAndView("alterarVenda");
        mv.addObject("venda", converterParaDTO(venda));
        mv.addObject("produtos", produtoRepository.findAllWithRelationships());
        mv.addObject("clientes", clienteRepository.findAllWithRelationships());
        return mv;
    }

    @PostMapping(value = "/editar/{idVenda}")
    public String editarVenda(@PathVariable Integer idVenda, @Validated @ModelAttribute("venda") VendaDTO vendaDTO, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique os campos obrigatórios.");
            attributes.addFlashAttribute("produtos", produtoRepository.findAllWithRelationships());
            attributes.addFlashAttribute("clientes", clienteRepository.findAllWithRelationships());
            return "redirect:/vendas/editarForm/" + idVenda;
        }

        Venda venda = vendaRepository.findById(idVenda)
                .orElseThrow(() -> new IllegalArgumentException("Venda inválida: " + idVenda));

        Produto produtoAntigo = venda.getProduto();
        int quantidadeAntiga = venda.getQuantidade();

        Produto produtoNovo = produtoRepository.findById(vendaDTO.getIdProduto())
                .orElseThrow(() -> new IllegalArgumentException("Produto novo inválido: " + vendaDTO.getIdProduto()));
        
        Cliente cliente = clienteRepository.findById(vendaDTO.getIdCliente())
                .orElseThrow(() -> new IllegalArgumentException("Cliente inválido: " + vendaDTO.getIdCliente()));

        produtoAntigo.adicionarQuantidade(quantidadeAntiga);
        
        if (produtoNovo.getIdProduto().equals(produtoAntigo.getIdProduto())) {
             if ((produtoAntigo.getQuantidade()) < vendaDTO.getQuantidade()) {
                attributes.addFlashAttribute("mensagem", "Quantidade em estoque insuficiente para o produto: " + produtoNovo.getNome());
                attributes.addFlashAttribute("venda", vendaDTO);
                produtoAntigo.removerQuantidade(quantidadeAntiga); 
                attributes.addFlashAttribute("produtos", produtoRepository.findAllWithRelationships());
                attributes.addFlashAttribute("clientes", clienteRepository.findAllWithRelationships());
                return "redirect:/vendas/editarForm/" + idVenda;
            }
        } else { 
            if (produtoNovo.getQuantidade() < vendaDTO.getQuantidade()) {
                attributes.addFlashAttribute("mensagem", "Quantidade em estoque insuficiente para o produto: " + produtoNovo.getNome());
                attributes.addFlashAttribute("venda", vendaDTO);
                produtoAntigo.removerQuantidade(quantidadeAntiga); 
                attributes.addFlashAttribute("produtos", produtoRepository.findAllWithRelationships());
                attributes.addFlashAttribute("clientes", clienteRepository.findAllWithRelationships());
                return "redirect:/vendas/editarForm/" + idVenda;
            }
        }
        
        produtoRepository.save(produtoAntigo); 

        venda.setDataInicio(vendaDTO.getDataInicio());
        venda.setHoraInicio(vendaDTO.getHoraInicio());
        venda.setQuantidade(vendaDTO.getQuantidade());
        venda.setProduto(produtoNovo);
        venda.setCliente(cliente);
        venda.setValor(produtoNovo.getValor().multiply(new BigDecimal(vendaDTO.getQuantidade())));
        venda.setLucro((produtoNovo.getValor().subtract(produtoNovo.getCusto())).multiply(new BigDecimal(vendaDTO.getQuantidade())));

        produtoNovo.removerQuantidade(vendaDTO.getQuantidade());
        produtoRepository.save(produtoNovo);
        
        vendaRepository.save(venda);
        attributes.addFlashAttribute("mensagem", "Venda atualizada com sucesso!");
        return "redirect:/vendas/listar";
    }

    @GetMapping(value = "/deletar/{idVenda}")
    public String deletarVenda(@PathVariable Integer idVenda, RedirectAttributes attributes) {
        Venda venda = vendaRepository.findById(idVenda)
                .orElseThrow(() -> new IllegalArgumentException("Venda inválida: " + idVenda));

        Produto produto = venda.getProduto();
        produto.adicionarQuantidade(venda.getQuantidade());
        produtoRepository.save(produto);

        vendaRepository.delete(venda);
        attributes.addFlashAttribute("mensagem", "Venda removida com sucesso e estoque restaurado!");
        return "redirect:/vendas/listar";
    }
    
    // MÉTODO BUSCAR ATUALIZADO
    @GetMapping("/buscar")
    public String buscar(@RequestParam(value = "termo", required = false) String termo,
                         @PageableDefault(size = 10) Pageable pageable,
                         Model model) {
        Page<Venda> paginaVendasEntidades;
        
        // Cria a Specification para a busca
        Specification<Venda> spec = SpecificationController.comTermoVenda(termo);
        
        // Realiza a busca usando a Specification
        paginaVendasEntidades = vendaRepository.findAll(spec, pageable);
        
        // Remove a mensagem de "não implementada" se a busca for feita
        if (termo != null && !termo.isEmpty() && paginaVendasEntidades.isEmpty()) {
            model.addAttribute("mensagemBusca", "Nenhuma venda encontrada para o termo: ' " + termo + " '.");
        } else if (termo != null && !termo.isEmpty() && !paginaVendasEntidades.isEmpty()) {
             model.addAttribute("mensagemBusca", "Exibindo resultados para: ' " + termo + " '.");
        }


        Page<VendaDTO> paginaVendaDTOs = paginaVendasEntidades.map(this::converterParaDTO);
        model.addAttribute("paginaVendas", paginaVendaDTOs);
        model.addAttribute("termo", termo);
        return "vendas";
    }

    private VendaDTO converterParaDTO(Venda venda) {
        VendaDTO dto = new VendaDTO();
        dto.setIdVenda(venda.getIdVenda());
        dto.setDataInicio(venda.getDataInicio());
        dto.setHoraInicio(venda.getHoraInicio());
        dto.setValor(venda.getValor());
        dto.setLucro(venda.getLucro());
        dto.setQuantidade(venda.getQuantidade());

        if (venda.getProduto() != null) {
            dto.setIdProduto(venda.getProduto().getIdProduto());
            dto.setNomeProduto(venda.getProduto().getNome());
        }
        if (venda.getCliente() != null) {
            dto.setIdCliente(venda.getCliente().getIdCliente());
            dto.setNomeCliente(venda.getCliente().getNomeCliente());
        }
        return dto;
    }

    @GetMapping(value = "/teste")
    public String teste() {
        return "correto";
    }
}