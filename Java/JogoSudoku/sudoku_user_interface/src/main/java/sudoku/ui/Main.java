package sudoku.ui;

import static java.util.stream.Collectors.toMap;

import java.util.Map;
import java.util.stream.Stream;

import sudoku.ui.screen.MainScreen;

public class Main {
    
    public static void main(String[] args) {
        
        final Map<String, String> gameConfig = Stream.of(args).collect(toMap(
            k -> k.split(";")[0],
            v -> v.split(";")[1]
        ));
        MainScreen mainScreen = new MainScreen(gameConfig);
        mainScreen.buildMainScreen();   
    }
}