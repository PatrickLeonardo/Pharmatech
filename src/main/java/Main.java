import java.sql.SQLException;

import javax.swing.SwingUtilities;

import screens.TelaPrincipal;

public class Main {
    public static void main(String[] args) throws SQLException {

        // Tela Principal vai ser instanciada de forma assíncronoma 
        SwingUtilities.invokeLater(() -> {
            new TelaPrincipal(null);
        });

    }
}
