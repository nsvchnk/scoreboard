Scoreboard utils

- startGame
    starts new game with initial score, returns game Id

- updateScore
    updates game score by Id

- finishGame
    removes game from list

- getSummary
    returns games summary

Scoreboard utils stores stats info, so if new instance is created, it will contain no data

I made assumptions for edge cases:
- team can't play with itself, so home and away teams must be different
- team can't play if it is already playing, 
  so it is not possible to add match if one or both teams are already playing (are on scoreboard)
- negative scores are not allowed
