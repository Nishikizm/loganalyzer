package io.github.nishikizm.loganalyzer.app;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if(args.length == 0) {
            System.err.println("引数を指定してください");
            System.exit(1);
        }
        Path logPath = Path.of(args[0]);
        int skipNum = 0;
        int errorSum = 0;
        Map<String, Integer> errorNum = new HashMap<>();
        try(Scanner sc = new Scanner(logPath, StandardCharsets.UTF_8)) {
            while(sc.hasNextLine()) {
                String line = sc.nextLine().strip();
                if(line.isEmpty()) {
                    skipNum++;
                    continue;
                }
                String[] elements = line.split("\\s+", 4);
                if(elements.length < 4) {
                    skipNum++;
                    continue;
                }
                if(elements[1].equals("ERROR")) {
                    errorNum.merge(elements[2], 1, Integer::sum);
                    errorSum++;
                }
            }
        } catch(IOException e) {
            System.err.println("ファイルが開けません");
            System.exit(2);
        }
        List<Map.Entry<String, Integer>> sortList = new ArrayList<>(errorNum.entrySet());
        sortList.sort(
            Comparator.comparingInt((Map.Entry<String, Integer> e) -> e.getValue())
                    .thenComparing(Map.Entry::getKey)
        );
        for(Map.Entry<String, Integer> e : sortList) {
            System.out.println(e.getKey() + " " + e.getValue());
        }
        System.out.println("skip " + skipNum);
        System.out.println("error " + errorSum);
    }
}
