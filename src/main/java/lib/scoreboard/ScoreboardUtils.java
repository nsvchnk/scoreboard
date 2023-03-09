package lib.scoreboard;

import lib.scoreboard.component.GameStat;

import java.util.*;

public class ScoreboardUtils {

    private final List<GameStat> stats = new ArrayList<>();

    public UUID startGame(String homeTeam, String awayTeam) {
        GameStat gameStat = new GameStat(homeTeam, awayTeam);
        stats.add(gameStat);
        return gameStat.getGameId();
    }

    public List<GameStat> getSummary() {
        return stats;
    }
}
