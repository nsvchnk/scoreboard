package lib.scoreboard;

import lib.scoreboard.component.GameStat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ScoreboardUtilsUnitTest {

    private static final String HOME_TEAM = "Mexico";
    private static final String AWAY_TEAM = "Canada";

    private ScoreboardUtils scoreboardUtils;

    private static Stream<Arguments> teamValidations() {
        return Stream.of(
                Arguments.of(HOME_TEAM, null, "Team must be not null"),
                Arguments.of("       ", HOME_TEAM, "Team must be not blank"),
                Arguments.of(HOME_TEAM, HOME_TEAM, "Home and away teams must be different"),
                Arguments.of(HOME_TEAM, AWAY_TEAM, "One or both of the teams are already playing")
        );
    }

    @BeforeEach
    void setUp() {
        scoreboardUtils = new ScoreboardUtils();
    }

    @Test
    public void shouldAddNewGameWithInitialScore_whenStartGame_givenHomeTeamAndAwayTeam() {
        // given
        // HOME_TEAM and AWAY_TEAM
        GameStat expectedGameStat = new GameStat(HOME_TEAM, AWAY_TEAM);

        // when
        scoreboardUtils.startGame(HOME_TEAM, AWAY_TEAM);

        // then
        assertThat(scoreboardUtils.getSummary().size()).isEqualTo(1);
        assertThat(scoreboardUtils.getSummary().get(0)).isEqualTo(expectedGameStat);
    }

    @ParameterizedTest
    @MethodSource("teamValidations")
    public void shouldThrowIllegalArgumentException_whenStartGame_givenInvalidTeams(
            String homeTeam, String awayTeam, String expectedExceptionMessage) {
        // given test parameters
        scoreboardUtils.startGame("someteam", AWAY_TEAM);

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> scoreboardUtils.startGame(homeTeam, awayTeam));
        assertThat(exception.getMessage()).isEqualTo(expectedExceptionMessage);
    }

    @Test
    public void shouldThrowIllegalArgumentException_whenUpdateScore_givenInvalidScore() {
        // given
        Integer homeTeamScore = 0;
        Integer awayTeamScore = -1;
        String expectedExceptionMessage = "Score must be positive or zero";
        UUID gameId = scoreboardUtils.startGame(HOME_TEAM, AWAY_TEAM);

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> scoreboardUtils.updateScore(gameId, homeTeamScore, awayTeamScore));
        assertThat(exception.getMessage()).isEqualTo(expectedExceptionMessage);
    }

    @Test
    public void shouldUpdateGameScore_whenUpdateScore_givenGameIdAndTeamScores() {
        // given
        UUID gameId = scoreboardUtils.startGame(HOME_TEAM, AWAY_TEAM);
        GameStat expectedGameStat = new GameStat(HOME_TEAM, AWAY_TEAM, 1, 2);

        // when
        scoreboardUtils.updateScore(gameId, 1, 2);

        // then
        assertThat(scoreboardUtils.getSummary().size()).isEqualTo(1);
        assertThat(scoreboardUtils.getSummary().get(0)).isEqualTo(expectedGameStat);
    }

    @Test
    public void shouldRemoveGame_whenFinishGame_givenGameId() {
        // given
        UUID gameId = scoreboardUtils.startGame(HOME_TEAM, AWAY_TEAM);
        assertThat(scoreboardUtils.getSummary().size()).isEqualTo(1);

        // when
        scoreboardUtils.finishGame(gameId);

        // then
        assertThat(scoreboardUtils.getSummary().size()).isEqualTo(0);
    }

    @Test
    public void shouldReturnOrderedGamesList_whenGetSummary_givenNoParams() throws InterruptedException {
        // given
        prepareGameStats();
        List<GameStat> expectedGameStats = expectedGameStatList();

        // when
        List<GameStat> actualStatsList = scoreboardUtils.getSummary();

        // then
        assertThat(actualStatsList).usingRecursiveComparison()
                .ignoringFields("gameId", "startDate")
                .isEqualTo(expectedGameStats);
    }

    // wait time added so that all games start at a bit different time
    private void prepareGameStats() throws InterruptedException {
        UUID gameId;
        gameId = scoreboardUtils.startGame("Mexico", "Canada");
        scoreboardUtils.updateScore(gameId, 0, 5);
        Thread.sleep(1);
        gameId = scoreboardUtils.startGame("Spain", "Brazil");
        scoreboardUtils.updateScore(gameId, 10, 2);
        Thread.sleep(1);
        gameId = scoreboardUtils.startGame("Germany", "France");
        scoreboardUtils.updateScore(gameId, 2, 2);
        Thread.sleep(1);
        gameId = scoreboardUtils.startGame("Uruguay", "Italy");
        scoreboardUtils.updateScore(gameId, 6, 6);
        Thread.sleep(1);
        gameId = scoreboardUtils.startGame("Argentina", "Australia");
        scoreboardUtils.updateScore(gameId, 3, 1);
    }

    private List<GameStat> expectedGameStatList() {
        List<GameStat> gameStatList = new ArrayList<>();
        gameStatList.add(new GameStat("Uruguay", "Italy", 6, 6));
        gameStatList.add(new GameStat("Spain", "Brazil", 10, 2));
        gameStatList.add(new GameStat("Mexico", "Canada", 0, 5));
        gameStatList.add(new GameStat("Argentina", "Australia", 3, 1));
        gameStatList.add(new GameStat("Germany", "France", 2, 2));
        return gameStatList;
    }
}