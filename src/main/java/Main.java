import java.sql.SQLException;

import javax.swing.*;

import screens.*;

public class Main {
    public static void main(String[] args) throws SQLException {

        SwingUtilities.invokeLater(() -> {
            new TelaCarrinho();
        });

    }
}
