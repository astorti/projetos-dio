package board;

import static board.persistence.config.ConnectionConfig.getConnection;

import java.sql.Connection;
import java.sql.SQLException;

import board.persistence.migration.MigrationStrategy;
import board.ui.MainMenu;

public class Main {
    
    public static void main(String[] args) throws SQLException{
        
        try (Connection connection = getConnection()) {
            new MigrationStrategy(connection).executeMigration();
        } 
        new MainMenu().execute();
    }
}