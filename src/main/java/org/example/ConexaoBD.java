package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class ConexaoBD {
    private static final String URL = "jdbc:mysql://localhost:3306/pjbl?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Método abstrato para operações de CRUD
    public abstract void criarUsuario(Usuario usuario);
    public abstract Usuario lerUsuario(int id);
    public abstract void atualizarUsuario(Usuario usuario);
    public abstract void deletarUsuario(int id);
}
