package ZtechAplication.pagina;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import ZtechAplication.DTO.ProdutoDTO;
import ZtechAplication.DTO.ProdutoDTO;
import ZtechAplication.model.Categoria;
import ZtechAplication.model.Cliente;
import ZtechAplication.model.Marca;
import ZtechAplication.model.Produto;
import ZtechAplication.repository.CategoriaRepository;
import ZtechAplication.repository.MarcaRepository;
import ZtechAplication.repository.ProdutoRepository;


@RestController
@RequestMapping(value = "/produto") //para que qualquer um deles seja valido
public class ProdutoController {

	@Autowired
	private ProdutoRepository classeRepo;
	private CategoriaRepository categoriaRepo;
	private MarcaRepository marcaRepo;
	
	//indicar o metodo get no HTML
	@GetMapping(value = "/cadastrarForm")
	public ModelAndView form() {
		ModelAndView mv = new ModelAndView("cadastroProduto");
		mv.addObject("produto", new ProdutoDTO() ); //inicializa o obj para o formulario
		return mv;
	}
	
	//indicar o metodo post no HTML
	@RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
	public String form(@Validated ProdutoDTO produtoDTO, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos...");
			return "redirect:/cadastroProduto";
		}
		
		// validação para ver se a categoria ou marac ja existe
		// Busca ou cria a Categoria
	    Categoria categoria = categoriaRepo.findByNome(produtoDTO.getCategoria())
	                            .orElseGet(() -> {
	                                Categoria novaCategoria = new Categoria();
	                                novaCategoria.setNome(produtoDTO.getCategoria());
	                                return categoriaRepo.save(novaCategoria);
	                            });
	    // Busca ou cria a Marca
	    Marca marca = marcaRepo.findByNome(produtoDTO.getMarca())
	                    .orElseGet(() -> {
	                        Marca novaMarca = new Marca();
	                        novaMarca.setNome(produtoDTO.getMarca());
	                        return marcaRepo.save(novaMarca);
	                    });
		
		// aqui passamos tudo para produto
		Produto produto = new Produto();
		produto.setNome(produtoDTO.getNome());
		produto.setCusto(produtoDTO.getCusto());
		produto.setQuantidade(produtoDTO.getQuantidade());
		produto.setDescricao(produtoDTO.getDescricao());
		produto.setValor(produtoDTO.getValor());
		produto.setCategoria(categoria);
		produto.setMarca(marca);
		
		
		// FUTURO - criar metodod e inserção no service
		//aqui adiciona o produto a lista de categoria e marca
		if(categoria.getProdutos() == null) {
			categoria.setProdutos(new ArrayList<>());
		}  categoria.getProdutos().add(produto);
		
		if(marca.getProdutos() == null) {
			marca.setProdutos(new ArrayList<>());
		} marca.getProdutos().add(produto);
		
		
		classeRepo.save(produto);
		attributes.addFlashAttribute("mensagem", "Produto cadastrado com sucesso!");
		return "redirect:/produto/cadastrarForm";
	}
	
	@GetMapping(value = "/listar")
	public ModelAndView listarProduto() {
		ModelAndView mv = new ModelAndView("estoque");
		List<Produto> produtos = (List<Produto>) classeRepo.findAllWithRelationships();
		
		mv.addObject("produtos", produtos);
		return mv;
	}
	
	@RequestMapping("/buscar")
	public String buscar (@RequestParam(value ="termo", required=false) String termo,
						  @PageableDefault (size=12 ) Pageable pageale, 
						  Model model 	) {
		model.addAttribute("produtos", pesquisar(termo, pageale));
		model.addAttribute("termo", termo);
		return "produtos";
	}
	
	@RequestMapping(value = "/editarForm/{idProduto}")
	public ModelAndView editarProduto(@PathVariable Integer idProduto) {
		ModelAndView mv = new ModelAndView("alterarProduto");
		Produto produto = classeRepo.findById(idProduto)
				.orElseThrow(() -> new IllegalArgumentException("Produto inválido: " + idProduto));
				
//		 Converter para DTO
		ProdutoDTO produtoDTO = converterParaDTO(produto);
		mv.addObject("produtos", produtoDTO);
		return mv;
	}
	
	
//	falta metodo EDITAR
	@PostMapping(value = "/editar/{idProduto}") //  \/aqui usamos uma classe só para coletar as informações
	public String formEditar(@ModelAttribute("produto") @Validated ProdutoDTO produtoDTO,
							 @PathVariable Integer idProduto, 
							 BindingResult result, 
							 RedirectAttributes attributes) {

		Produto produto = classeRepo.findById(idProduto)
			    .orElseThrow(() -> new IllegalArgumentException("Cliente inválido: " + idProduto));
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos...");
			produto.setIdProduto(idProduto);
			return "/editar/{idProduto}";
		}
		
//		aqui passamos tudo para a produto
//      aqui mapeamos as classes model com a classe DTO que coletou os dados
		produto.setIdProduto(idProduto);
		produto.setNome(produtoDTO.getNome());
		produto.setCusto(produtoDTO.getCusto());
		produto.setValor(produtoDTO.getValor());
		produto.setQuantidade(produtoDTO.getQuantidade());
		produto.setDescricao(produtoDTO.getDescricao());
		produto.setQuantidade(produtoDTO.getQuantidade());
		
		
		produto.getCategoria().setNome(produtoDTO.getCategoria());
		produto.getMarca().setNome(produtoDTO.getMarca());
		
		classeRepo.save(produto); // salva todas as informaç~eos por conta do CASCATE
		attributes.addFlashAttribute("mensagem", "Cliente atualizado(a) com sucesso!");
		return "redirect:/produto/cadastrarForm";
	}
	
	
	
	
	@RequestMapping(value = "/deletar/{idProduto}")
	public String remover(@PathVariable Integer idProduto, RedirectAttributes attributes) {
        Produto produto = classeRepo.findById(idProduto)
				.orElseThrow(() -> new IllegalArgumentException("Produto inválido: " + idProduto));
		classeRepo.delete(produto);
        attributes.addFlashAttribute("mensagem", "Produto removido com sucesso!");
        return "redirect:/produto/listar";
	}
	
//	metodos axulizares
	public Page<Produto> pesquisar(String termo, Pageable pegeable){
		return classeRepo.findAll(
				SpecificationController.comTermoProd(termo), 
				pegeable);
	}
	
	private ProdutoDTO converterParaDTO(Produto produto) {
	    ProdutoDTO dto = new ProdutoDTO();
	    
	    // Atributos básicos
	    dto.setIdProduto(produto.getIdProduto());
	    dto.setNome(produto.getNome());
	    dto.setCusto(produto.getCusto());
	    dto.setValor(produto.getValor());
	    dto.setDescricao(produto.getDescricao());

	    // Verifica e converte Categoria (se existir)
	    if (produto.getCategoria() != null) {
	        dto.setIdCategoria(produto.getCategoria().getIdCategoria());
	        dto.setNome(produto.getCategoria().getNome()); // Adicione este campo no DTO se necessário
	    } else {
	        dto.setIdCategoria(null); // ou um valor padrão
	    }

	    // Verifica e converte Marca (se existir)
	    if (produto.getMarca() != null) {
	        dto.setIdMarca(produto.getMarca().getIdMarca());
	        dto.setNome(produto.getMarca().getNome()); // Adicione este campo no DTO se necessário
	    } else {
	        dto.setIdMarca(null); // ou um valor padrão
	    }

	    return dto;
	}
	
	
	@GetMapping(value = "/teste")
	public String teste (){
		return "correto";
	}
	
	
	
}





