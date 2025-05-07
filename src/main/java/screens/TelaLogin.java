package screens;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import orm.DatabaseConnection;

public class TelaLogin {

    private String cpf;
    private String senha;
    
    public TelaLogin() {
        
        final JFrame tela = new JFrame("Pharmatech Login"); 
        
        final JLabel labelCPF = new JLabel("Insira seu CPF: ");
        labelCPF.setFont(new Font("Arial", Font.PLAIN, 20));

        final JTextField textoCPF = new JTextField(30);
        textoCPF.setPreferredSize(new Dimension(150, 30));
        labelCPF.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
         
        final JLabel labelSenha = new JLabel("Insira sua Senha: ");
        labelSenha.setFont(new Font("Arial", Font.PLAIN, 20));
        labelSenha.setBorder(BorderFactory.createEmptyBorder(60, 30, 30, 30));

        final JTextField textoSenha = new JTextField(30);
        textoSenha.setPreferredSize(new Dimension(150, 30));
        
        final JButton botaoLogin = new JButton("Realizar Login");
        botaoLogin.setPreferredSize(new Dimension(150, 50));
        botaoLogin.setBorder(BorderFactory.createEmptyBorder());

        final JButton botaoLinkTelaDeCadastro = new JButton("Criar Conta");
        botaoLinkTelaDeCadastro.setPreferredSize(new Dimension(150, 50));
        botaoLinkTelaDeCadastro.setBorder(BorderFactory.createEmptyBorder());
        
        final Container container = tela.getContentPane();
        
        container.setLayout(new GridLayout(5, 10));
        
        container.add(labelCPF);
        container.add(textoCPF);
        container.add(labelSenha);
        container.add(textoSenha);
        container.add(botaoLogin);
        container.add(botaoLinkTelaDeCadastro);

        botaoLogin.addActionListener((event) -> {

            this.cpf = textoCPF.getText();
            this.senha = textoSenha.getText(); 
            
            try {
                DatabaseConnection databaseConnection = new DatabaseConnection();
                databaseConnection.consultLogin(this.cpf, this.senha);
            } catch(SQLException sqlException) {
                sqlException.printStackTrace();
            } catch(ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }

        });

        botaoLinkTelaDeCadastro.addActionListener((event) -> {
            
            tela.setVisible(false);
            new TelaCadastro();

        });
        
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setLayout(new FlowLayout());
        tela.setContentPane(container);
        tela.pack();
        tela.setSize(500, 500);
        tela.setLocationRelativeTo(null);
        tela.setVisible(true);
        
    }
    
}
