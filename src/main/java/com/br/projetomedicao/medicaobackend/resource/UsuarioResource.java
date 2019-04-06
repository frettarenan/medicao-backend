package com.br.projetomedicao.medicaobackend.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.projetomedicao.medicaobackend.model.Usuario;
import com.br.projetomedicao.medicaobackend.service.UsuarioService;
import com.br.projetomedicao.medicaobackend.utils.HttpServletResponseUtil;

@RestController
@RequestMapping("/usuarios")
public class UsuarioResource {

	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/status/ativo")
	@PreAuthorize("isAuthenticated()")
	public List<Usuario> listarUsuariosComStatusAtivoPorConstrutora(@RequestParam(required = true) Long idConstrutora) {
		return usuarioService.listarUsuariosComStatusAtivoPorConstrutora(idConstrutora);
	}
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_USUARIO')")
	public Page<Usuario> pesquisar(@RequestParam(required = false) String nome, @RequestParam(required = false) String email, @RequestParam(required = false) Long idConstrutora, Pageable pageable) {		
		return usuarioService.pesquisar(nome, email, idConstrutora, pageable);
	}
	
	@PutMapping("/{idUsuario}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_USUARIO') and #oauth2.hasScope('write')")
	public void atualizarPropriedadeAtivo(@PathVariable Long idUsuario, @RequestBody Boolean ativo) {
		usuarioService.atualizarPropriedadeAtivo(idUsuario, ativo);
	}
	
	@DeleteMapping("/{idUsuario}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_USUARIO') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long idUsuario) {
		usuarioService.remover(idUsuario);
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_USUARIO') and #oauth2.hasScope('write')")
	public ResponseEntity<Usuario> salvar(@Valid @RequestBody Usuario usuario, HttpServletResponse response) {
		Usuario usuarioBD = usuarioService.salvar(usuario);
		HttpServletResponseUtil.adicionarHeaderLocation(response, usuarioBD.getId());
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioBD);
	}

	@GetMapping("/{idUsuario}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_USUARIO') and #oauth2.hasScope('read')")
	public ResponseEntity<Usuario> buscarPeloId(@PathVariable Long idUsuario) {
		Usuario usuarioBD = usuarioService.buscarUsuarioPeloId(idUsuario);
		return ResponseEntity.ok(usuarioBD);
	}
	
	@PutMapping("/{idUsuario}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_USUARIO') and #oauth2.hasScope('write')")
	public ResponseEntity<Usuario> atualizar(@PathVariable Long idUsuario, @Valid @RequestBody Usuario usuario) {
		Usuario usuarioBD = usuarioService.atualizar(idUsuario, usuario);
		return ResponseEntity.ok(usuarioBD);
	}
	
}