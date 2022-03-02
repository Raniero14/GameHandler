package edu.pascal.seafight.api.utils.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Getter
public enum GameStatus {

    WAITING(0),COUNTDOWN(1),STARTING(2),STARTED(3),ENDING(4),END(5);

    int id;


}
