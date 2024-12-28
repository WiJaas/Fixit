package com.fixit.Model;

import java.sql.*;
import java.util.List;

public abstract class BaseDAO<T> {

    protected Connection connection;
    protected PreparedStatement preparedStatement;



    public BaseDAO() throws SQLException {
        final String url = "jdbc:mysql://127.0.0.1:3306/fixit";  // Your database URL
        final String login = "root";  // Your database username
        final String password = "";  // Your database password
        this.connection = DriverManager.getConnection(url, login, password);
    }

    public abstract void save(T object) throws SQLException;
    public abstract T getOne(int id) throws SQLException;
    public abstract List<T> getAll() throws SQLException;



}