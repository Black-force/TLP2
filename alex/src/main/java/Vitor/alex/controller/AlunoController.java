package Vitor.alex.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Controller;
	import org.springframework.ui.Model;
	import org.springframework.validation.BindingResult;
	import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
	
	import Vitor.alex.modelo.Aluno;
import Vitor.alex.modelo.Papel;
import Vitor.alex.Repository.AlunoRepository;
import Vitor.alex.Repository.PapelRepository;


@Controller
@RequestMapping("/Aluno")

public class AlunoController {

	@Autowired
	private AlunoRepository alunoRepository;
	
	@Autowired
	private PapelRepository papelRepository;
	
	@GetMapping("/novo")
	public String adicionarAluno(Model model) {
		model.addAttribute("aluno", new Aluno());
		return "/publica-criar-aluno";
	}
	
	
	@PostMapping("/salvar")
	public String salvarAluno(@Valid Aluno aluno, BindingResult result, 
				RedirectAttributes attributes, Model model) {
		if (result.hasErrors()) {
			return "/publica-criar-aluno";
		}
		Aluno aln = alunoRepository.findByLogin(aluno.getLogin());
		if (aln != null) {
			model.addAttribute("loginExiste", "Login já existe cadastrado");
			return "/publica-criar-aluno";
		}
		alunoRepository.save(aluno);
		attributes.addFlashAttribute("mensagem", "Aluno salvo com sucesso!");
		//return "redirect:/Aluno/novo";
		
		
		Papel papel = papelRepository.findByPapel("USER");
		List<Papel> papeis = new ArrayList<Papel>();
		papeis.add(papel);				
		aluno.setPapeis(papeis); 
		
		alunoRepository.save(aluno);
		attributes.addFlashAttribute("mensagem", "Aluno salvo com sucesso!");
		return "redirect:/Aluno/novo";


	}
	
	@RequestMapping("/admin/listar")
	public String listarAluno(Model model) {
		List<Aluno> lista = alunoRepository.findAll();
		model.addAttribute("Alunos",lista);
		return "/auth/admin/admin-listar-aluno";
	}
	
	
	@GetMapping("/admin/apagar/{id}")
	public String deleteUser(@PathVariable("id") long id, Model model) {
		Aluno aluno = alunoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Id inválido:" + id));
		alunoRepository.delete(aluno);
	    return "redirect:/Aluno/admin/listar";
	}
	
	@GetMapping("/editar/{id}")
	public String editarAluno(@PathVariable("id") long id, Model model) {
		Optional<Aluno> alunoVelho = alunoRepository.findById(id);
		if (!alunoVelho.isPresent()) {
            throw new IllegalArgumentException("Aluno inválido:" + id);
        } 
		Aluno aluno = alunoVelho.get();
	    model.addAttribute("aluno", aluno);
	    return "/auth/user/user-alterar-aluno";
	}
	
	@PostMapping("/editar/{id}")
	public String editaraluno(@PathVariable("id") long id, 
			@Valid Aluno aluno, BindingResult result) {
		if (result.hasErrors()) {
	    	aluno.setId(id);
	        return "/auth/user/user-alterar-aluno";
	    }
	    alunoRepository.save(aluno);
	    return "redirect:/aluno/admin/listar";
	}

		
	@GetMapping("/editarPapel/{id}")
	public String selecionarPapel(@PathVariable("id") long id, Model model) {
		Optional<Aluno> alunoVelho = alunoRepository.findById(id);
		if (!alunoVelho.isPresent()) {
            throw new IllegalArgumentException("Aluno inválido:" + id);
        } 
		Aluno aluno = alunoVelho.get();
	    model.addAttribute("Aluno", aluno);
	    
	    model.addAttribute("listaPapeis", papelRepository.findAll());
	    return "/auth/admin/admin-editar-papel-aluno";
	}
	@PostMapping("/editarPapel/{id}")
	public String atribuirPapel(@PathVariable("id") long idAluno, 
								@RequestParam(value = "pps", required=false) int[] pps, 
								Aluno aluno, 
								RedirectAttributes attributes) {
		if (pps == null) {
			aluno.setId(idAluno);
			attributes.addFlashAttribute("mensagem", "Pelo menos um papel deve ser informado");
			return "redirect:/Aluno/editarPapel/"+idAluno;
		} else {
			//Obtém a lista de papéis selecionada pelo usuário do banco
			List<Papel> papeis = new ArrayList<Papel>();			 
			for (int i = 0; i < pps.length; i++) {
				long idPapel = pps[i];
				Optional<Papel> papelOptional = papelRepository.findById(idPapel);
				if (papelOptional.isPresent()) {
					Papel papel = papelOptional.get();
					papeis.add(papel);
		        }
			}
			Optional<Aluno> AlunoOptional = alunoRepository.findById(idAluno);
			if (AlunoOptional.isPresent()) {
				Aluno aln = AlunoOptional.get();
				aln.setPapeis(papeis); // relaciona papéis ao usuário
				aln.setAtivo(aluno.isAtivo());
				alunoRepository.save(aln);
	        }			
		}		
	    return "redirect:/Aluno/admin/listar";
	}

}


