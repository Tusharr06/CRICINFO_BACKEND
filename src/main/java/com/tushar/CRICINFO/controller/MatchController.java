package com.tushar.CRICINFO.controller;

import com.tushar.CRICINFO.Service.MatchService;
import com.tushar.CRICINFO.entities.Match;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cricket")
@CrossOrigin("*")
public class MatchController {

    private final MatchService cricketService;

    public MatchController(MatchService cricketService) {
        this.cricketService = cricketService;
    }

    @GetMapping("/live")
    public ResponseEntity<?> getLiveMatchScores() {
        return new ResponseEntity<>(this.cricketService.getLiveMatchScores(), HttpStatus.OK);
    }

    @GetMapping("/point-table")
    public ResponseEntity<?> getCWC2023PointTable() {
        return new ResponseEntity<>(this.cricketService.getCWC2023PointTable(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Match>> getAllMatches() {
        return new ResponseEntity<>(this.cricketService.getAllMatches(), HttpStatus.OK);
    }
}
