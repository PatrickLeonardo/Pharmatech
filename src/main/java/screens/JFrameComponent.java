package screens;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

//testando commit 2 Miron
public class JFrameComponent {

    public static void setProperty(final JLabel jLabel) {
        
        jLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        jLabel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30)); 

    }

    public static void setProperty(final JTextField jTextField) {

        jTextField.setPreferredSize(new Dimension(150, 30));

    }

    public static void setProperty(final JButton jButton) {

        jButton.setPreferredSize(new Dimension(150, 50));

    }

    public static void dump(final Container container, final ArrayList<JComponent> jComponentList) {

        jComponentList.forEach((component) -> {
            container.add(component);
        });

    }

}
