package br.ETS.almoxarifado.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public Connection recuperarConexao() {
        try {
            DriverManager.getConnection("jdbc:mysql://localhost:3306/almoxarifado?"+"user=root");
            System.out.println("Conexão realizada com sucesso");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
