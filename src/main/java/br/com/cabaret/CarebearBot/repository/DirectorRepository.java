package br.com.cabaret.CarebearBot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.cabaret.CarebearBot.model.Director;


public interface DirectorRepository  extends JpaRepository<Director, Long> {
	
	@Query("select d from Director d")
	public List<Director> getDirector();
	
}
