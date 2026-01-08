package io.github.nishikizm.loganalyzer.formatter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import io.github.nishikizm.loganalyzer.model.FinalRecord;

public class ResultFormatter {
    public String format(FinalRecord ans) {
        List<Map.Entry<String, Integer>> sortList = new ArrayList<>(ans.errorNum().entrySet());
        sortList.sort(
            Comparator.comparingInt((Map.Entry<String, Integer> e) -> e.getValue())
                    .thenComparing(Map.Entry::getKey)
        );
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, Integer> e : sortList) {
            sb.append(e.getKey()).append(" ").append(e.getValue()).append("\n");
        }
        sb.append("skip ").append(ans.skipNum()).append("\n");
        sb.append("error ").append(ans.errorSum()).append("\n");
        return sb.toString();
    }
}
