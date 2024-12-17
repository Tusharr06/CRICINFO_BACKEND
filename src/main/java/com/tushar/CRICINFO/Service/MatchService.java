package com.tushar.CRICINFO.Service;

import com.tushar.CRICINFO.entities.Match;

import java.util.List;


public interface MatchService {

    List<Match> getLiveMatchScores();
    List<List<String>> getCWC2023PointTable();
    List<Match> getAllMatches();

}
