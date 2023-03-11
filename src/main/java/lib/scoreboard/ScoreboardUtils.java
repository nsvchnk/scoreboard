package lib.scoreboard;

import lib.scoreboard.component.GameStat;

import java.util.*;

public class ScoreboardUtils {

    private final Map<UUID, GameStat> stats = new HashMap<>();

    public UUID startGame(String homeTeam, String awayTeam) {
        GameStat gameStat = new GameStat(homeTeam, awayTeam);
        stats.put(gameStat.getGameId(), gameStat);
        return gameStat.getGameId();
    }

    public void updateScore(UUID gameId, Integer homeTeamScore, Integer awayTeamScore) {
        stats.get(gameId).updateScore(homeTeamScore, awayTeamScore);
    }

    public List<GameStat> getSummary() {
        return (stats.values()).stream().toList();
    }
}
