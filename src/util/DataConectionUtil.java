package util;

import java.sql.*;

public class DataConectionUtil {
    public static Connection openConnection(String driver, String url, String username, String password){
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection(ResultSet resultSet, Statement statement, Connection connection) {
        try {
            if (!ObjUtil.isEmpty(resultSet) && !resultSet.isClosed()) {
                resultSet.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            if (!ObjUtil.isEmpty(statement) && !statement.isClosed()) {
                statement.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            if (!ObjUtil.isEmpty(connection) && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}