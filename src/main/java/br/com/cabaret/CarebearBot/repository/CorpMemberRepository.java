package br.com.cabaret.CarebearBot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.cabaret.CarebearBot.model.CorpMember;

public interface CorpMemberRepository  extends JpaRepository<CorpMember, Long> {
	
	@Query("select c from CorpMember c where c.characterId = :characterId")
	public CorpMember findId(@Param("characterId") Long characterId);
	
	@Query("select c from CorpMember c where c.characterName = :charName")
	public List<CorpMember> findByName(@Param("charName") String charName);
	
	@Query(value ="select * from corp_member c where c.character_name is null", nativeQuery = true)
	public List<CorpMember> findByNameNull();
}
