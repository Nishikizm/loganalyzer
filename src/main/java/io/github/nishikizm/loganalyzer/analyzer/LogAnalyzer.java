package io.github.nishikizm.loganalyzer.analyzer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import io.github.nishikizm.loganalyzer.model.FinalRecord;
import io.github.nishikizm.loganalyzer.model.LineRecord;
import io.github.nishikizm.loganalyzer.parser.LineParser;

public class LogAnalyzer {
    public FinalRecord analyse(Path logPath) throws IOException {
        List<String> lines = new ArrayList<>();
        try(Scanner sc = new Scanner(logPath, StandardCharsets.UTF_8)) {
            while(sc.hasNextLine()) {
                lines.add(sc.nextLine().strip());
            }
        }
        Record r = parseLogLine(lines);
        return new FinalRecord(r.errorNum, r.skipNum, r.errorSum);
    }

    private static class Record {
        Map<String, Integer> errorNum = new HashMap<>();
        int skipNum = 0;
        int errorSum = 0;
    }

    private static Record parseLogLine(List<String> lines) {
        Record r = new Record();
        LineParser parser = new LineParser();
        LineRecord l;
        for(String line: lines) {
            try {
                l = parser.parse(line);
            } catch(IllegalArgumentException e) {
                r.skipNum++;
                continue;
            }
            if("ERROR".equals(l.level())) {
                r.errorNum.merge(l.module(), 1, Integer::sum);
                r.errorSum++;
            }
        }
        return r;
    }

}