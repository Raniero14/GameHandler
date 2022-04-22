package edu.pascal.gamehandler.api.utils.reader;

import java.io.BufferedReader;
import java.io.IOException;

public class LineReader {

    public final BufferedReader reader;

    public LineReader(BufferedReader reader) {
        this.reader = reader;
    }

    public String readLine(char feed) throws IOException {
        StringBuilder builder = new StringBuilder();
        while (true) {
            int x = reader.read();
            if(x == feed) {
                break;
            }
            if(x == -1) {
                continue;
            }
            builder.append((char) x);
        }
        return builder.toString();
    }



}
