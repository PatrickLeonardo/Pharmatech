package screens;

import java.awt.*;
import javax.swing.*;

public class TelaPrincipalCliente {

    public TelaPrincipalCliente() {

        final JFrame tela = new JFrame(" ");

        final JLabel msgPesquisa = new JLabel("Pharmatech");
        final JButton botaoCarrinho= new JButton("Carrinho");
        final JButton botaoConsultarPedido = new JButton("Consultar Pedido");
        final JButton botaoPerfil = new JButton("Acessar Perfil");
        final JTextField barraPesquisa = new JTextField("");

        Color azulPharmatech = new Color(1, 0, 127);
        Color cinzaFundo = new Color (207, 206, 206);

        

        final Container container = tela.getContentPane(); 
        container.setLayout(null); 
        container.setBackground(cinzaFundo);

        msgPesquisa.setBounds(50, 25, 500, 50);
        msgPesquisa.setFont(new Font("Arial", Font.PLAIN, 30));
        msgPesquisa.setForeground(azulPharmatech);
        container.add(msgPesquisa);

        barraPesquisa.setBounds(250, 25, 500, 50);
        barraPesquisa.setFont(new Font("Arial", Font.PLAIN, 40));
        container.add(barraPesquisa);

        botaoCarrinho.setBounds(1300, 25, 150, 50);
        botaoCarrinho.setFont(new Font("Arial", Font.PLAIN, 22));
        botaoCarrinho.setBackground(Color.white);
        container.add(botaoCarrinho);

        botaoConsultarPedido.setBounds(1100, 25, 150, 50);
        botaoConsultarPedido.setBackground(Color.white);
        container.add(botaoConsultarPedido);

        botaoPerfil.setBounds(900, 25, 150, 50);
        botaoPerfil.setBackground(Color.white);
        container.add(botaoPerfil);


        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setContentPane(container);
        tela.pack();
        tela.setSize(1500, 800);
        tela.setLocationRelativeTo(null);
        tela.setVisible(true);

    }
}
