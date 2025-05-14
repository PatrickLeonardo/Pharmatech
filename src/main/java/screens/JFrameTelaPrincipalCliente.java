package screens;

import javax.swing.*;
import java.awt.*;

public class JFrameTelaPrincipalCliente {

    public static void mudarPropriedade(final JLabel jLabel) {
        
        jLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        jLabel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30)); 

    }

    public static void mudarPropriedade(final JTextField jTextField) {

        jTextField.setPreferredSize(new Dimension(15, 3));

    }

    public static void mudarPropriedade(final JButton jButton) {

        jButton.setPreferredSize(new Dimension(15, 5));

    }

}
