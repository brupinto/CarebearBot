package br.com.cabaret.CarebearBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cabaret.CarebearBot.model.MiningObserver;

public interface MiningObserverRepository  extends JpaRepository<MiningObserver, Long> {
	
}
