package ZtechAplication.pagina;

import javax.naming.Binding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ZtechAplication.model.Marca;
import ZtechAplication.repository.MarcaRepository;


@RestController
@RequestMapping(value = {"/marca", "/Marca"} ) //para que qualquer um deles seja valido
public class MarcaController {

	@Autowired
	private MarcaRepository mr;
	
	//indicar o metodo get no HTML
	@GetMapping(value = "/cadastrarMarca")
	public ModelAndView form() {
		ModelAndView mv = new ModelAndView("cadastroProduto/fomrMarca");
		mv.addObject("marca", new Marca() ); //inicializa o obj para o formulario
		return mv;
	}
	
	//indicar o metodo post no HTML
	@PostMapping(value = "/cadastrarMarca")
	public String form(@Validated Marca marca, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos...");
			return "redirect:/cadastrarMarca";
		}
		
		mr.save(marca);
		attributes.addFlashAttribute("mensagem", "Marca cadastrada com sucesso!");
		return "redirect:/cadastrarMarca";
	}
	
	@RequestMapping(value = "/marca")
	public ModelAndView listarMarca() {
		ModelAndView mv = new ModelAndView("produto/listaMarcas");
		mv.addObject("marca", mr.findAll());
		return mv;
	}
	
	@RequestMapping(value = "/editarMarca")
	public ModelAndView editarMarca(long id) {
		Marca marca = mr.findById(id);
		ModelAndView mv = new ModelAndView("produto/editarMarca");
		mv.addObject("marca", marca);
		return mv;
	}
	
	@RequestMapping(value = "/deletarMarca")
	public String deletarMarca(long id, RedirectAttributes attributes) {
		Marca marca = mr.findById(id);
		mr.delete(marca);
		attributes.addFlashAttribute("mensagem", "Marca Removida com Sucesso!");
		return "redirect:/marca";
	}
	
}
