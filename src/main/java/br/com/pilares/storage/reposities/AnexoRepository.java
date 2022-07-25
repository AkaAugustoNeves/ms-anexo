package br.com.pilares.storage.reposities;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pilares.storage.model.entity.Anexo;

public interface AnexoRepository extends JpaRepository<Anexo, Long>{
	
	Optional<Anexo> findByHash(UUID hash);

}
