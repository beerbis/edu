package ru.beerbis.chatserver;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class AuthenticationService {
    private final Connection connection = connect();
    private final PreparedStatement qLogin = connection.prepareStatement("select nickname from users where login=? and rawpwd=?");

    public AuthenticationService() throws SQLException, ClassNotFoundException {
    }

    private Connection connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        return DriverManager.getConnection("jdbc:sqlite:auth.db");
    }

    public synchronized String findNicknameByLoginAndPassword(String login, String password) throws AuthActionException {
        try {
            qLogin.setString(1, login);
            qLogin.setString(2, password);
            try (ResultSet result = qLogin.executeQuery();) {
                if (!result.next()) return null;
                return result.getString(1);
            }
        } catch (SQLException e) {
            throw new AuthActionException("Не удалось проверить логин/пароль в базе", e);
        }
    }

    public static class AuthActionException extends Exception {
        public AuthActionException(String message, Throwable cause) { super(message, cause); }
        public AuthActionException(Throwable cause) { super(cause); }
    }
}
