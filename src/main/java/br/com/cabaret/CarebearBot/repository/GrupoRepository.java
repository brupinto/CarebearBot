package br.com.cabaret.CarebearBot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.cabaret.CarebearBot.model.Grupo;

public interface GrupoRepository  extends JpaRepository<Grupo, Long> {
	
	@Query("select g from Grupo g where g.grupoName = :grupoName")
	public List<Grupo> findByName(@Param("grupoName") String grupoName);

	@Query("select g from Grupo g")
	public List<Grupo> getAll();
	
}
