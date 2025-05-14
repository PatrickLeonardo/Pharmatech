package screens;

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class TelaPrincipalCliente {

    public TelaPrincipalCliente() {

        final JFrame tela = new JFrame(" ");

        final JLabel labelBemVindo = new JLabel("Bem-vindo ao Pharmatech!");
        final JButton botaoCarrinho= new JButton("Carrinho");
        final JButton botaoConsultarPedido = new JButton("Consultar Pedido");
        final JButton botaoPerfil = new JButton("Acessar Perfil");

        final ArrayList<JComponent> componentsList = new ArrayList<JComponent>(); 
        Collections.addAll(
            componentsList,
            labelBemVindo,
            botaoCarrinho,
            botaoConsultarPedido,
            botaoPerfil
        );
        
        for(final JComponent component : componentsList){

            if(component instanceof JLabel) JFrameTelaPrincipalCliente.mudarPropriedade((JLabel) component);
            else if(component instanceof JTextField) JFrameTelaPrincipalCliente.mudarPropriedade((JTextField) component);
            else if(component instanceof JButton) JFrameTelaPrincipalCliente.mudarPropriedade((JButton) component);
            
        }

         final Container container = tela.getContentPane(); 
        container.setLayout(new GridLayout(5, 10)); 

        JFrameComponent.dump(container, componentsList); 

        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setLayout(new FlowLayout());
        tela.setContentPane(container);
        tela.pack();
        tela.setSize(500, 500);
        tela.setLocationRelativeTo(null);
        tela.setVisible(true);

    }

}
