package br.com.cabaret.CarebearBot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.cabaret.CarebearBot.model.GrupoDetail;

public interface GrupoDetailRepository  extends JpaRepository<GrupoDetail, Long> {
	
	@Query("select g from GrupoDetail g where g.characterName like :charName")
	public List<GrupoDetail> findLikeName(@Param("charName") String charName);
	
	
}
