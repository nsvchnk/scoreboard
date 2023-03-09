package lib.scoreboard;

import lib.scoreboard.component.GameStat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ScoreboardUtilsUnitTest {

    private static final String HOME_TEAM = "Mexico";
    private static final String AWAY_TEAM = "Canada";

    private ScoreboardUtils scoreboardUtils;

    @BeforeEach
    void setUp() {
        scoreboardUtils = new ScoreboardUtils();
    }

    @Test
    public void shouldAddNewGameWithInitialScore_whenStartGame_givenHomeTeamAndAwayTeam(){
        // given
        // HOME_TEAM and AWAY_TEAM
        GameStat expectedGameStat = new GameStat(HOME_TEAM, AWAY_TEAM);

        // when
        scoreboardUtils.startGame(HOME_TEAM, AWAY_TEAM);

        // then
        assertThat(scoreboardUtils.getSummary().size()).isEqualTo(1);
        assertThat(scoreboardUtils.getSummary().get(0)).isEqualTo(expectedGameStat);
    }

//    @Test
//    public void shouldUpdateGameScore_whenUpdateScore_givenGameIdAndTeamScores(){
//        // given
//        UUID gameId = scoreboardUtils.startGame(HOME_TEAM, AWAY_TEAM);
//        GameStat expectedGameStat = new GameStat(HOME_TEAM, AWAY_TEAM, 1, 2);
//
//        // when
//        scoreboardUtils.updateScore(gameId, 1, 2);
//
//        // then
//        assertThat(scoreboardUtils.getSummary().size()).isEqualTo(1);
//        assertThat(scoreboardUtils.getSummary().get(0)).isEqualTo(expectedGameStat);
//    }
//
//    @Test
//    public void shouldRemoveGame_whenFinishGame_givenGameId(){
//        // given
//        UUID gameId = scoreboardUtils.startGame(HOME_TEAM, AWAY_TEAM);
//        assertThat(scoreboardUtils.getSummary().size()).isEqualTo(1);
//
//        // when
//        scoreboardUtils.finishGame(gameId);
//
//        // then
//        assertThat(scoreboardUtils.getSummary().size()).isEqualTo(0);
//    }
//
//    @Test
//    public void shouldReturnOrderedGamesList_whenGetSummary_givenNoParams(){
//        // given
//        prepareGameStats();
//        List<GameStat> expectedGameStats = expectedGameStatList();
//
//        // when
//        List<GameStat> actualStatsList = scoreboardUtils.getSummary();
//
//        // then
//        assertThat(actualStatsList).usingRecursiveComparison().isEqualTo(expectedGameStats);
//    }
//
//    private void prepareGameStats(){
//        UUID gameId;
//        gameId = scoreboardUtils.startGame("Mexico", "Canada");
//        scoreboardUtils.updateScore(gameId, 0, 5);
//        gameId = scoreboardUtils.startGame("Spain", "Brazil");
//        scoreboardUtils.updateScore(gameId, 10, 2);
//        gameId = scoreboardUtils.startGame("Germany", "France");
//        scoreboardUtils.updateScore(gameId, 2, 2);
//        gameId = scoreboardUtils.startGame("Uruguay", "Italy");
//        scoreboardUtils.updateScore(gameId, 6, 6);
//        gameId = scoreboardUtils.startGame("Argentina", "Australia");
//        scoreboardUtils.updateScore(gameId, 3, 1);
//    }
//
//    private List<GameStat> expectedGameStatList(){
//        List<GameStat> gameStatList = new ArrayList<>();
//        gameStatList.add(new GameStat("Uruguay", "Italy", 6, 6));
//        gameStatList.add(new GameStat("Spain", "Brazil", 10, 2));
//        gameStatList.add(new GameStat("Mexico", "Canada", 0, 5));
//        gameStatList.add(new GameStat("Argentina", "Australia", 3, 1));
//        gameStatList.add(new GameStat("Germany", "France", 2, 2));
//        return gameStatList;
//    }
}