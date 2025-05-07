package orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    public Connection connection;
    public Statement statement;

    public DatabaseConnection() throws ClassNotFoundException, SQLException {
        
        connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/DBPharmatech?autoReconnect=true&useSSL=false",
            "patrick", "123"
        );

        statement = connection.createStatement();
        
    }

    public void consultLogin(final String CPF, final String SENHA) throws SQLException {

        final String query = "SELECT cpf='" + CPF + "', senha='" + SENHA + "' FROM tbCliente";
        final ResultSet queryCallBack = statement.executeQuery(query);

        while(queryCallBack.next()) {

            if(String.valueOf(queryCallBack.getString(1)).equals("1") && String.valueOf(queryCallBack.getString(2)).equals("1")) {
                System.out.println("Login Autorizado");
            } else {
                System.out.println("Login Não Autorizado");
            }
            
        }

    }

}
