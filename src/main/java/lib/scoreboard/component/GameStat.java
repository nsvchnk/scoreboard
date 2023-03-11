package lib.scoreboard.component;

import java.util.UUID;

public class GameStat {

    private final UUID gameId;
    private final String homeTeam;
    private final String awayTeam;
    private Integer homeTeamScore;
    private Integer awayTeamScore;

    public GameStat(String homeTeam, String awayTeam) {
        this.gameId = UUID.randomUUID();
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamScore = 0;
        this.awayTeamScore = 0;
    }

    public GameStat(String homeTeam, String awayTeam, Integer homeTeamScore, Integer awayTeamScore) {
        this.gameId = UUID.randomUUID();
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
    }

    public void updateScore(Integer homeTeamScore, Integer awayTeamScore){
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
    }

    public UUID getGameId() {
        return gameId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameStat gameStat = (GameStat) o;

        if (!homeTeam.equals(gameStat.homeTeam)) return false;
        if (!awayTeam.equals(gameStat.awayTeam)) return false;
        if (!homeTeamScore.equals(gameStat.homeTeamScore)) return false;
        return awayTeamScore.equals(gameStat.awayTeamScore);
    }

    @Override
    public int hashCode() {
        int result = homeTeam.hashCode();
        result = 31 * result + awayTeam.hashCode();
        result = 31 * result + homeTeamScore.hashCode();
        result = 31 * result + awayTeamScore.hashCode();
        return result;
    }

}
