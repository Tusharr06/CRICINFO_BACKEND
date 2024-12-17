package com.tushar.CRICINFO.Service.impl;

import com.tushar.CRICINFO.Service.MatchService;
import com.tushar.CRICINFO.entities.Match;
import com.tushar.CRICINFO.repositries.MatchRepo;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class MatchServiceImpl implements MatchService {

    private final MatchRepo matchRepo;

    public MatchServiceImpl(MatchRepo matchRepo) {
        this.matchRepo = matchRepo;
    }

    @Override
    public List<Match> getLiveMatchScores() {
        List<Match> matches = new ArrayList<>();
        try {
            URL url = new URL("https://www.cricbuzz.com/cricket-match/live-scores");
            Source source = new Source(url);
            List<Element> matchElements = source.getAllElements("div", Pattern.compile("cb-mtch-lst cb-tms-itm"));

            for (Element matchElement : matchElements) {
                String teamsHeading = getElementText(matchElement, "h3.cb-lv-scr-mtch-hdr");
                String matchNumberVenue = getElementText(matchElement, "span");

                String battingTeam = getElementText(matchElement, "div.cb-hmscg-bat-txt div.cb-hmscg-tm-nm");
                String score = getElementText(matchElement, "div.cb-hmscg-bat-txt div.cb-hmscg-tm-nm+div");

                String bowlTeam = getElementText(matchElement, "div.cb-hmscg-bwl-txt div.cb-hmscg-tm-nm");
                String bowlTeamScore = getElementText(matchElement, "div.cb-hmscg-bwl-txt div.cb-hmscg-tm-nm+div");

                String textLive = getElementText(matchElement, "div.cb-text-live");
                String textComplete = getElementText(matchElement, "div.cb-text-complete");

                String matchLink = getElementLink(matchElement, "a.cb-lv-scrs-well.cb-lv-scrs-well-live");

                Match match = new Match();
                match.setTeamHeading(teamsHeading);
                match.setMatchNumberVenue(matchNumberVenue);
                match.setBattingTeam(battingTeam);
                match.setBattingTeamScore(score);
                match.setBowlTeam(bowlTeam);
                match.setBowlTeamScore(bowlTeamScore);
                match.setLiveText(textLive);
                match.setMatchLink(matchLink);
                match.setTextComplete(textComplete);

                match.updateMatchStatus();

                matches.add(match);

                updateMatch(match);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matches;
    }

    private String getElementText(Element element, String query) {
        Element subElement = element.getFirstElement(query);
        return (subElement != null) ? subElement.getTextExtractor().toString() : "";
    }

    private String getElementLink(Element element, String query) {
        Element subElement = element.getFirstElement(query);
        return (subElement != null) ? subElement.getAttributeValue("href") : "";
    }

    private void updateMatch(Match match) {
        Match existingMatch = this.matchRepo.findByTeamHeading(match.getTeamHeading()).orElse(null);
        if (existingMatch == null) {
            this.matchRepo.save(match);
        } else {
            match.setMatchId(existingMatch.getMatchId());
            this.matchRepo.save(match);
        }
    }

    @Override
    public List<List<String>> getCWC2023PointTable() {
        List<List<String>> pointTable = new ArrayList<>();
        String tableURL = "https://www.cricbuzz.com/cricket-series/6732/icc-cricket-world-cup-2023/points-table";
        try {
            URL url = new URL(tableURL);
            Source source = new Source(url);
            List<Element> table = source.getAllElements("table", Pattern.compile("cb-srs-pnts"));

            List<String> headers = new ArrayList<>();
            Element thead = table.get(0).getFirstElement("thead");
            if (thead != null) {
                List<Element> tableHeads = thead.getAllElements("tr");
                for (Element header : tableHeads) {
                    headers.add(header.getTextExtractor().toString());
                }
            }
            pointTable.add(headers);

            Element tbody = table.get(0).getFirstElement("tbody");
            if (tbody != null) {
                List<Element> bodyRows = tbody.getAllElements("tr");
                for (Element row : bodyRows) {
                    List<String> points = new ArrayList<>();
                    List<Element> tds = row.getAllElements("td");
                    for (Element td : tds) {
                        points.add(td.getTextExtractor().toString());
                    }
                    pointTable.add(points);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pointTable;
    }

    @Override
    public List<Match> getAllMatches() {
        return this.matchRepo.findAll();
    }
}
