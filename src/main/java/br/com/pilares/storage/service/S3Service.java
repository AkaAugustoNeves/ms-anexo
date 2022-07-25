package br.com.pilares.storage.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;

import br.com.pilares.storage.model.entity.Anexo;

@Service
public class S3Service {
	
	@Autowired
	private AmazonS3 amazonS3;
	
	@Value("${aws.s3.bucket}")
	 private String bucket;
	
	public PutObjectResult upload(MultipartFile file, String sistema, UUID id) {
		try {
			return amazonS3.putObject(bucket, getKey(sistema, id, file), file.getInputStream(), null);
		} catch (AmazonServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SdkClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public ResponseEntity<ByteArrayResource> download(Anexo anexo) {
		S3Object object = amazonS3.getObject(bucket, anexo.getSistema().name().toLowerCase()+"/"+anexo.getHash()+"."+anexo.getExtensao().substring(anexo.getExtensao().indexOf("/")+1));
		S3ObjectInputStream objectContent = object.getObjectContent();
		try {
			byte[] filee = IOUtils.toByteArray(objectContent);
			return ResponseEntity.ok()
					.contentType(MediaType.parseMediaType(anexo.getExtensao()))
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+anexo.getNome()+"."+anexo.getExtensao().substring(anexo.getExtensao().indexOf("/")+1))
					.body(new ByteArrayResource(filee));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private String getKey(String sistema, UUID id, MultipartFile file) {
		int i = file.getContentType().indexOf("/");
		return sistema+ "/" +id+ "." +file.getContentType().substring(++i);
	}

}
