package lib.scoreboard;

import lib.scoreboard.component.GameStat;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScoreboardUtils {

    private final Map<UUID, GameStat> stats = new HashMap<>();

    public UUID startGame(String homeTeam, String awayTeam) {
        GameStat gameStat = new GameStat(homeTeam, awayTeam);
        validateTeams(homeTeam, awayTeam);
        stats.put(gameStat.getGameId(), gameStat);
        return gameStat.getGameId();
    }

    public void updateScore(UUID gameId, Integer homeTeamScore, Integer awayTeamScore) {
        validateScore(homeTeamScore, awayTeamScore);
        stats.get(gameId).updateScore(homeTeamScore, awayTeamScore);
    }

    public void finishGame(UUID gameId) {
        stats.remove(gameId);
    }

    public List<GameStat> getSummary() {
        List<GameStat> statList = new ArrayList<>((stats.values()).stream().toList());
        statList.sort((Comparator.comparing(GameStat::getTotalScore)
                .thenComparing(GameStat::getStartDate)).reversed());
        return statList;
    }

    private void validateTeams(String homeTeam, String awayTeam) {
        if (homeTeam == null || awayTeam == null) {
            throw new IllegalArgumentException("Team must be not null");
        }

        if (homeTeam.isBlank() || awayTeam.isBlank()) {
            throw new IllegalArgumentException("Team must be not blank");
        }

        if (homeTeam.equals(awayTeam)) {
            throw new IllegalArgumentException("Home and away teams must be different");
        }

        Set<String> playingTeams = stats.values().stream()
                .flatMap(stat -> Stream.of(stat.getHomeTeam(), stat.getAwayTeam())).collect(Collectors.toSet());
        if (playingTeams.contains(homeTeam) || playingTeams.contains(awayTeam)) {
            throw new IllegalArgumentException("One or both of the teams are already playing");
        }
    }

    private void validateScore(Integer homeTeamScore, Integer awayTeamScore) {
        if (homeTeamScore < 0 || awayTeamScore < 0) {
            throw new IllegalArgumentException("Score must be positive or zero");
        }
    }
}
