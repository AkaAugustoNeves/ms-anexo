package br.com.pilares.storage.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.pilares.storage.model.entity.Anexo;
import br.com.pilares.storage.reposities.AnexoRepository;
import br.com.pilares.storage.service.AnexoService;

@RestController
@RequestMapping("/anexo")
public class AnexoController {
	
	@Autowired
	private AnexoService anexoService;
	
	@PostMapping("/{sistema}/upload")
	public List<Anexo> upload(@RequestBody MultipartFile[] files, @PathVariable String sistema) {
		return anexoService.create(files, sistema);
	}

	@GetMapping("/download/{idAnexo}")
	public ResponseEntity<ByteArrayResource> download(@PathVariable Long idAnexo) {
		return anexoService.download(idAnexo);
	}
	
}
