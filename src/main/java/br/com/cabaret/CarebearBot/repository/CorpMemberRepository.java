package br.com.cabaret.CarebearBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cabaret.CarebearBot.model.CorpMember;

public interface CorpMemberRepository  extends JpaRepository<CorpMember, Long> {
	
}
