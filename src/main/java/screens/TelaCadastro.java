package screens;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class TelaCadastro {

    public TelaCadastro() {

        final JFrame tela = new JFrame("Tela Cadastro");
        
        final JLabel labelCPF = new JLabel("Insira seu CPF: ");
        labelCPF.setFont(new Font("Arial", Font.PLAIN, 20));
        labelCPF.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        final JTextField textoCPF = new JTextField(30);
        textoCPF.setPreferredSize(new Dimension(150, 30));
        
        final JLabel labelNome = new JLabel("Insira seu Nome: ");
        labelNome.setFont(new Font("Arial", Font.PLAIN, 20));
        labelNome.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        final JTextField textoNome = new JTextField(30);
        textoNome.setPreferredSize(new Dimension(150, 30));

        final JLabel labelTelefone = new JLabel("Insira sua Telefone: ");
        labelTelefone.setFont(new Font("Arial", Font.PLAIN, 20));
        labelTelefone.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        final JTextField textoTelefone = new JTextField(30);
        textoTelefone.setPreferredSize(new Dimension(150, 30));

        final JLabel labelEndereco = new JLabel("Insira seu Endereço: ");
        labelEndereco.setFont(new Font("Arial", Font.PLAIN, 20));
        labelEndereco.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        final JTextField textoEndereco = new JTextField(30);
        textoEndereco.setPreferredSize(new Dimension(150, 30));

        final JLabel labelSenha = new JLabel("Insira sua Senha: ");
        labelSenha.setFont(new Font("Arial", Font.PLAIN, 20));
        labelSenha.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        final JTextField textoSenha = new JTextField(30);
        textoSenha.setPreferredSize(new Dimension(150, 30));
        
        final JButton botaoCadastro = new JButton("Criar Conta");
        botaoCadastro.setPreferredSize(new Dimension(150, 50));
        botaoCadastro.setBorder(BorderFactory.createEmptyBorder());
        
        final JButton botaoLinkTelaLogin = new JButton("Realizar Login");
        botaoLinkTelaLogin.setPreferredSize(new Dimension(150, 50));
        botaoLinkTelaLogin.setBorder(BorderFactory.createEmptyBorder());

        final Container container = tela.getContentPane();

        container.setLayout(new GridLayout(14, 10));

        container.add(labelCPF);
        container.add(textoCPF);
        container.add(labelNome);
        container.add(textoNome);
        container.add(labelTelefone);
        container.add(textoTelefone);
        container.add(labelEndereco);
        container.add(textoEndereco);
        container.add(labelSenha);
        container.add(textoSenha);
        container.add(botaoCadastro);
        container.add(botaoLinkTelaLogin);

        botaoCadastro.addActionListener((event) -> { 

        });

        botaoLinkTelaLogin.addActionListener((event) -> {
            
            tela.setVisible(false);
            new TelaLogin();

        });

        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setLayout(new FlowLayout());
        tela.setContentPane(container);
        tela.pack();
        tela.setSize(500, 800);
        tela.setLocationRelativeTo(null);
        tela.setVisible(true);

    }

}
