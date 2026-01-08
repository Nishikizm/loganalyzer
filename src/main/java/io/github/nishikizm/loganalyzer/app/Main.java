package io.github.nishikizm.loganalyzer.app;

import java.io.IOException;
import java.nio.file.Path;
import io.github.nishikizm.loganalyzer.analyzer.LogAnalyzer;
import io.github.nishikizm.loganalyzer.formatter.ResultFormatter;
import io.github.nishikizm.loganalyzer.model.FinalRecord;

public class Main {

    public static void main(String[] args) {
        if(args.length == 0) {
            System.err.println("引数を指定してください");
            System.exit(1);
        }
        Path logPath = Path.of(args[0]);
        LogAnalyzer analyzer = new LogAnalyzer();
        FinalRecord ans;
        try {
            ans = analyzer.analyse(logPath);
        } catch(IOException e) {
            System.err.println("ファイルが開けません");
            System.exit(2);
            return;
        }
        ResultFormatter formetter = new ResultFormatter();
        System.out.println(formetter.format(ans));
    }

}
