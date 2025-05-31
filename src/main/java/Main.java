import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import screens.TelaPrincipal;
import screens.TelaPrincipalFarmaceutico;

public class Main {
    public static void main(String[] args) throws SQLException {

        // Tela Principal vai ser instanciada de forma assíncronoma 
        SwingUtilities.invokeLater(() -> {
            new TelaPrincipalFarmaceutico("693.868.540-15", new JFrame(), new TelaPrincipal(null));
        });

    }
}
