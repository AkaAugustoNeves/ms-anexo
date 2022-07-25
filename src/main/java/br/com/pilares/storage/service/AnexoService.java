package br.com.pilares.storage.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

import br.com.pilares.storage.model.entity.Anexo;
import br.com.pilares.storage.model.entity.Sistema;
import br.com.pilares.storage.reposities.AnexoRepository;

@Service
public class AnexoService {
	
	@Autowired
	private AnexoRepository anexoRepository;
	
	@Autowired
	private S3Service s3Service;
	
	public List<Anexo> create(MultipartFile[] files, String sistema){
		List<Anexo> anexos = new ArrayList<Anexo>();
		/*System.out.println(files[0].getContentType());
		System.out.println(files[0].getOriginalFilename());*/
		for(MultipartFile file: files){
			Anexo anexo = anexoRepository.save(new Anexo(file.getOriginalFilename(), file.getContentType(), getSistema(sistema)));
			s3Service.upload(file, sistema, anexo.getHash());
			anexos.add(anexo);
		}
		return anexos;
	}
	
	public ResponseEntity<ByteArrayResource> download(Long idAnexo) {
		Optional<Anexo> anexo = anexoRepository.findById(idAnexo);
		if(anexo.isPresent()) {
			return s3Service.download(anexo.get());
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	public Anexo porHash(UUID hash){
		return anexoRepository.findByHash(hash).get();
	}
	
	private Sistema getSistema(String sistemaNome){
		if(sistemaNome.equals("sisvo")) {
			return Sistema.SISVO;
		}else if(sistemaNome.equals("sisci")) {
			return Sistema.SISCI;
		}else if(sistemaNome.equals("infa")) {
			return Sistema.INFA;
		}else {
			return null;
		}
	}

	public List<Anexo> findAll() {
		
		return anexoRepository.findAll();
	}


}
