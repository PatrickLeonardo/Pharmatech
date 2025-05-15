package screens;//eu amo indaiatuba

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class TelaPrincipalCliente {

    public TelaPrincipalCliente() {

        Color azulPharmatech = new Color(1, 0, 127);
        Color cinzaFundo = new Color (207, 206, 206);

        ImageIcon logo = new ImageIcon("C:\\Users\\oushe\\Downloads\\simboloFarmacia.jpeg");
        Image imagemRedimensionada = logo.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        logo = new ImageIcon(imagemRedimensionada);

        final JFrame tela = new JFrame("Tela Principal");
        final JLabel labelLogo = new JLabel(logo);
        final JLabel msgPharmatech = new JLabel("Pharmatech");
        final JButton botaoCarrinho= new JButton("Carrinho");
        final JButton botaoCadastrar = new JButton("Cadastrar");
        final JButton botaoLogar = new JButton("Logar");
        final JTextField barraPesquisa = new JTextField("");
        final Container container = tela.getContentPane(); 

        container.setLayout(null); 
        container.setBackground(cinzaFundo);

        labelLogo.setBounds(20, 35, 30, 30);
        container.add(labelLogo);

        msgPharmatech.setBounds(50, 25, 500, 50);
        msgPharmatech.setFont(new Font("Arial", Font.PLAIN, 30));
        msgPharmatech.setForeground(azulPharmatech);
        container.add(msgPharmatech);

        barraPesquisa.setBounds(250, 25, 500, 50);
        barraPesquisa.setFont(new Font("Arial", Font.PLAIN, 40));
        container.add(barraPesquisa);

        botaoCarrinho.setBounds(1300, 25, 150, 50);
        container.add(botaoCarrinho);

        botaoCadastrar.setBounds(1100, 25, 150, 50);
        container.add(botaoCadastrar);

        botaoLogar.setBounds(900, 25, 150, 50);
        container.add(botaoLogar);

        botaoLogar.addActionListener((event) -> {
            new TelaLogin();
            tela.dispose();
        });

        botaoCadastrar.addActionListener((event) -> {
            new TelaCadastro();
            tela.dispose();
        });
        botaoCarrinho.addActionListener((event) -> {
            new TelaCarrinho();
            tela.dispose();
        });


        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setContentPane(container);
        tela.pack();
        tela.setSize(1500, 800);
        tela.setLocationRelativeTo(null);
        tela.setVisible(true);

    }
}
