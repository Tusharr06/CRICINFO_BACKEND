package com.tushar.CRICINFO.repositries;

import com.tushar.CRICINFO.entities.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatchRepo extends JpaRepository<Match,Integer> {
    Optional<Match> findByTeamHeading(String teamHeading);

}
