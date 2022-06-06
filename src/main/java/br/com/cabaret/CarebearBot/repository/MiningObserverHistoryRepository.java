package br.com.cabaret.CarebearBot.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.cabaret.CarebearBot.model.MiningObserverHistory;

public interface MiningObserverHistoryRepository  extends JpaRepository<MiningObserverHistory, Long> {

	@Query("select max(g.page) from MiningObserverHistory g where g.observer.observer_id = :id")
	public Long getMaxPage(@Param("id") Long observer_id);
	
	@Query("select g from MiningObserverHistory g where g.observer.observer_id = :observerId and g.corpMember.characterId = :characterId and g.type_id = :typeId and g.last_updated = :lastUpdate")
	public MiningObserverHistory searchData(@Param("observerId") Long observerId, @Param("characterId") Long characterId, @Param("typeId") Long typeId, @Param("lastUpdate") LocalDate lastUpdate);
	
	@Query("select distinct g.type_id from MiningObserverHistory g where g.typeDescription is null")
	public List<Long> distinctType();
	
	@Query("select g from MiningObserverHistory g where g.type_id = :typeId")
	public List<MiningObserverHistory> findByTypeId(@Param("typeId") Long typeId);
	
	@Query(value ="select cm.character_name characterName, moh.type_description typeName, sum(quantity) qtMining, format((sum(quantity) * p.taxa_mining)/100,0) taxaCorp, 1 fgChar        " + 
		          "   from mining_observer_history moh                                                                                                                                    " + 
		          " inner join corp_member cm on moh.corp_member_character_id = cm.character_id                                                                                           " + 
		          " inner join parametro p on p.id = 1                                                                                                                                    " + 
		          " where 1=1                                                                                                                                                             " + 
		          "   and moh.last_updated >= :dtIni                                                                                                                                      " + 
		          "   and moh.last_updated <= :dtFim                                                                                                                                      " + 
		          "   and not EXISTS (select 1 from grupo_detail gd where gd.member_character_id = cm.character_id)                                                                       " + 
		          " group by cm.character_name, moh.type_description                                                                                                                      " + 
		          " union ALL                                                                                                                                                             " + 
		          " select g.grupo_name characterName, moh.type_description typeName, sum(quantity) qtMining, format((sum(quantity) * p.taxa_mining)/100,0) taxaCorp, 0 fgChar            " + 
		          "   from mining_observer_history moh                                                                                                                                    " + 
		          " inner join grupo_detail gd on gd.member_character_id = moh.corp_member_character_id                                                                                   " + 
		          " inner join grupo g on g.id = gd.grupo_id                                                                                                                              " + 
		          " inner join parametro p on p.id = 1                                                                                                                                    " + 
		          " where 1=1                                                                                                                                                             " + 
		          "   and moh.last_updated >= :dtIni                                                                                                                                      " + 
		          "   and moh.last_updated <= :dtFim                                                                                                                                      " + 
		          " group by g.grupo_name, moh.type_description                                                                                                                           " +
		          " order by 1,2                                                                                                                                                          ",
			nativeQuery = true)
	public List<Object[]> reportMining(@Param("dtIni") LocalDate dtIni, @Param("dtFim") LocalDate dtFim);
	
	@Query(value ="select cm.character_name characterName, convert(sum((quantity*100)/qtmined), DECIMAL (5,2)) pcMining, 1 fgChar             " + 
		          "   from mining_observer_history moh                                                                                        " + 
		          " inner join corp_member cm on moh.corp_member_character_id = cm.character_id                                               " + 
		          " inner join parametro p on p.id = 1 																						  " + 
		          " inner join (select sum(quantity) qtmined        																		  " + 
		          "   from mining_observer_history 																							  " + 
		          " where 1=1                                                                                                                 " + 
		          "   and last_updated >= :dtIni                                                                                         	  " + 
		          "   and last_updated <= :dtFim                                                                                         	  " + 
		          "   ) totm   																												  " + 
		          " where 1=1                                                                                                                 " + 
		          "   and moh.last_updated >= :dtIni                                                                                          " + 
		          "   and moh.last_updated <= :dtFim                                                                                          " + 
		          "   and not EXISTS (select 1 from grupo_detail gd where gd.member_character_id = cm.character_id)                           " + 
		          " group by cm.character_name         																						  " + 
		          " union ALL                                                                                                                 " + 
		          " select g.grupo_name characterName, convert(sum((quantity*100)/qtmined), DECIMAL(5,2)) pcMining, 0 fgChar                  " + 
		          "   from mining_observer_history moh                                                                                        " + 
		          " inner join grupo_detail gd on gd.member_character_id = moh.corp_member_character_id                                       " +
		          " inner join grupo g on g.id = gd.grupo_id                                                                                  " + 
		          " inner join parametro p on p.id = 1                                   													  " + 
		          "  inner join (select sum(quantity) qtmined        																		  " + 
		          "   from mining_observer_history 																							  " + 
		          " where 1=1                                                                                                                 " + 
		          "   and last_updated >= :dtIni                                                                                              " + 
		          "   and last_updated <= :dtFim                                                                                          	  " +
		          "   ) totm   																												  " + 
		          " where 1=1                                                                                                                 " + 
		          "   and moh.last_updated >= :dtIni                                                                                          " + 
		          "   and moh.last_updated <= :dtFim                                                                                          " + 
		          " group by g.grupo_name                                                                                                     " + 
		          " order by 1,2   																											  " , 
			nativeQuery = true)
	public List<Object[]> reportMiningGraphPlayers(@Param("dtIni") LocalDate dtIni, @Param("dtFim") LocalDate dtFim);
	
	@Query(value ="select moh.type_description typeName, sum(quantity) qtMining        " + 
			      "   from mining_observer_history moh                                 " + 
			      " where 1=1                                                          " + 
			      "   and moh.last_updated >= :dtIni                                   " + 
			      "   and moh.last_updated <= :dtFim                                   " + 
			      " group by moh.type_description                                      " + 
			      " order by 1,2                                                       " , 
			nativeQuery = true)
	public List<Object[]> reportMiningGraphOres(@Param("dtIni") LocalDate dtIni, @Param("dtFim") LocalDate dtFim);
	
	
	
}
