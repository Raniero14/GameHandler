package edu.pascal.gamehandler.api.utils.path;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class MovePack {

    private final List<String> moves;
    private final int orientation;

}
