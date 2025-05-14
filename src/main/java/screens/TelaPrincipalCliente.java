package screens;

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class TelaPrincipalCliente {

    public TelaPrincipalCliente() {

        final JFrame tela = new JFrame(" ");

        final JLabel msgPesquisa = new JLabel("Bem-vindo ao Pharmatech!");
        final JButton botaoCarrinho= new JButton("Carrinho");
        final JButton botaoConsultarPedido = new JButton("Consultar Pedido");
        final JButton botaoPerfil = new JButton("Acessar Perfil");
        final JTextField barraPesquisa = new JTextField("Pesquisar produtos...");

         final Container container = tela.getContentPane(); 
        container.setLayout(null); 
        container.setBackground(Color.WHITE);

        msgPesquisa.setFont(new Font("Arial", Font.PLAIN, 20));
        container.add(msgPesquisa);
        msgPesquisa.setBounds(50, 0, 300, 50);

        barraPesquisa.setBounds(50, 25, 300, 50);
        container.add(barraPesquisa);

        botaoCarrinho.setBounds(1300, 25, 150, 50);
        container.add(botaoCarrinho);

        botaoConsultarPedido.setBounds(1100, 25, 150, 50);
        container.add(botaoConsultarPedido);

        botaoPerfil.setBounds(900, 25, 150, 50);
        container.add(botaoPerfil);


        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setContentPane(container);
        tela.pack();
        tela.setSize(1500, 800);
        tela.setLocationRelativeTo(null);
        tela.setVisible(true);

    }

}
