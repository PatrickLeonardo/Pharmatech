import java.sql.SQLException;

import javax.swing.SwingUtilities;

import screens.TelaLogin;
import screens.TelaPrincipalCliente;

public class Main {
    public static void main(String[] args) throws SQLException {

        SwingUtilities.invokeLater(() -> {
            new TelaPrincipalCliente();
        });

    }
}
