package io.github.nishikizm.loganalyzer.model;

import java.util.Map;

public record FinalRecord(Map<String, Integer> errorNum, int skipNum, int errorSum) {
    public FinalRecord {
        errorNum = Map.copyOf(errorNum);
    }
}
