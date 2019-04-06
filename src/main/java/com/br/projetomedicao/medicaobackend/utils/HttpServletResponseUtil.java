package com.br.projetomedicao.medicaobackend.utils;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class HttpServletResponseUtil {
	
	public static void adicionarHeaderLocation(HttpServletResponse response, Long id) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(id).toUri();
		response.setHeader("Location", uri.toASCIIString());
	}

}