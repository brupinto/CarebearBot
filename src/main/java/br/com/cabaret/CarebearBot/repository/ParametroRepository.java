package br.com.cabaret.CarebearBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cabaret.CarebearBot.model.Parametro;

public interface ParametroRepository  extends JpaRepository<Parametro, Long> {
	
}
