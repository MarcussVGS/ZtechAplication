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
	
	
	

}
