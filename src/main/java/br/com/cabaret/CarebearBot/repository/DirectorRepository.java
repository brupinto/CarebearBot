package br.com.cabaret.CarebearBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cabaret.CarebearBot.model.Director;

public interface DirectorRepository  extends JpaRepository<Director, Long> {
	
}
