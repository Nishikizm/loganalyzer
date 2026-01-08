package io.github.nishikizm.loganalyzer.parser;

import io.github.nishikizm.loganalyzer.model.LineRecord;

public class LineParser {
    public LineRecord parse(String line) {
        if(line.isEmpty()) {
            throw new IllegalArgumentException("空行です");
        }
        String[] elements = line.split("\\s+", 4);
        if(elements.length < 4) {
            throw new IllegalArgumentException("要素が不足しています");
        }
        return new LineRecord(elements[0], elements[1], elements[2], elements[3]);
    }
}
