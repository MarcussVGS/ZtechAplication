package ZtechAplication.pagina;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class indexController {
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/inicio")
	public String inicio() {
		return "inicio";
	}
	@RequestMapping("/clientes")
	public String clientes() {
		return "clientes";
	}
	@RequestMapping("/estoque")
	public String estoque() {
		return "estoque";
	}
	@RequestMapping("/vendas")
	public String vendas() {
		return "vendas";
	}
	@RequestMapping("/ordens")
	public String ordens() {
		return "ordens";
	}
	@RequestMapping("/cadastro_cliente")
	public String cadastro_cliente() {
		return "cadastro_cliente";
	}
	@RequestMapping("/cadastro_produto")
	public String cadastro_produto() {
		return "cadastro_produto";
	}
	@RequestMapping("/cadastro_OS")
	public String cadastro_OS() {
		return "cadastro_OS";
	}
}
