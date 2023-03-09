package lib.scoreboard.component;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ScoreboardApiUnitTest {

    private static final String HOME_TEAM = "Mexico";
    private static final String AWAY_TEAM = "Canada";

    private ScoreboardApi scoreboardApi;

    @BeforeEach
    void setUp() {
        scoreboardApi = new ScoreboardApi();
    }

    @Test
    public void shouldAddNewGameWithInitialScore_whenStartGame_givenHomeTeamAndAwayTeam(){
        // given
        // HOME_TEAM and AWAY_TEAM
        GameStat expectedGameStat = new GameStat(HOME_TEAM, AWAY_TEAM, 0, 0);

        // when
        UUID gameId = scoreboardApi.startGame(HOME_TEAM, AWAY_TEAM);

        // then
        assertThat(scoreboardApi.getSummary().size()).isEqualTo(1);
        assertThat(scoreboardApi.getSummary().get(0)).isEqualTo(expectedGameStat);
    }

    @Test
    public void shouldUpdateGameScore_whenUpdateScore_givenGameIdAndTeamScores(){
        // given
        UUID gameId = scoreboardApi.startGame(HOME_TEAM, AWAY_TEAM);
        GameStat expectedGameStat = new GameStat(HOME_TEAM, AWAY_TEAM, 1, 2);

        // when
        scoreboardApi.updateScore(gameId, 1, 2);

        // then
        assertThat(scoreboardApi.getSummary().size()).isEqualTo(1);
        assertThat(scoreboardApi.getSummary().get(0)).isEqualTo(expectedGameStat);
    }

    @Test
    public void shouldRemoveGame_whenFinishGame_givenGameId(){
        // given
        UUID gameId = scoreboardApi.startGame(HOME_TEAM, AWAY_TEAM);
        assertThat(scoreboardApi.getSummary().size()).isEqualTo(1);

        // when
        scoreboardApi.finishGame(gameId);

        // then
        assertThat(scoreboardApi.getSummary().size()).isEqualTo(0);
    }

    @Test
    public void shouldReturnOrderedGamesList_whenGetSummary_givenNoParams(){
        // given
        prepareGameStats();
        List<GameStat> expectedGameStats = expectedGameStatList();

        // when
        List<GameStat> actualStatsList = scoreboardApi.getSummary();

        // then
        assertThat(actualStatsList).usingRecursiveComparison().isEqualTo(expectedGameStats);
    }

    private void prepareGameStats(){
        UUID gameId;
        gameId = scoreboardApi.startGame("Mexico", "Canada");
        scoreboardApi.updateScore(gameId, 0, 5);
        gameId = scoreboardApi.startGame("Spain", "Brazil");
        scoreboardApi.updateScore(gameId, 10, 2);
        gameId = scoreboardApi.startGame("Germany", "France");
        scoreboardApi.updateScore(gameId, 2, 2);
        gameId = scoreboardApi.startGame("Uruguay", "Italy");
        scoreboardApi.updateScore(gameId, 6, 6);
        gameId = scoreboardApi.startGame("Argentina", "Australia");
        scoreboardApi.updateScore(gameId, 3, 1);
    }

    private List<GameStat> expectedGameStatList(){
        List<GameStat> gameStatList = new ArrayList<>();
        gameStatList.add(new GameStat("Uruguay", "Italy", 6, 6));
        gameStatList.add(new GameStat("Spain", "Brazil", 10, 2));
        gameStatList.add(new GameStat("Mexico", "Canada", 0, 5));
        gameStatList.add(new GameStat("Argentina", "Australia", 3, 1));
        gameStatList.add(new GameStat("Germany", "France", 2, 2));
        return gameStatList;
    }
}