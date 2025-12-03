package board.persistence.config;

import lombok.NoArgsConstructor;
import static lombok.AccessLevel.PRIVATE;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@NoArgsConstructor(access = PRIVATE)
public final class ConnectionConfig {

    public static Connection getConnection() throws SQLException {

        final String URL = "jdbc:mysql://localhost/board_db";
        final String USER = "informar-user";
        final String PASSWORD = "informar-password";
        
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        connection.setAutoCommit(false);
        return connection;
    }
}
