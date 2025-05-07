package screens;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class JFrameComponent {

    public void setProperty(final JLabel jLabel) {
        
        jLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        jLabel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30)); 

    }

    public void setProperty(final JTextField jTextField) {

        jTextField.setPreferredSize(new Dimension(150, 30));

    }

    public void dump(final Container container, final ArrayList<JComponent> jComponentList) {

        jComponentList.forEach((component) -> {
            container.add(component);
        });

    }

}
