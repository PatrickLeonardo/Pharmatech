package screens;

import java.awt.Container;
import java.sql.SQLException;

import java.awt.Font;
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
        final JTextField textoCPF = new JTextField(30);         
        final JLabel labelSenha = new JLabel("Insira sua Senha: ");
        final JTextField textoSenha = new JTextField(30);
        final JButton botaoLogin = new JButton("Realizar Login");
        final JButton botaoLinkTelaDeCadastro = new JButton("Criar Conta");
        final JButton telaPrincipal = new JButton("Tela Principal");
        final Container container = tela.getContentPane(); 

        container.setBackground(new java.awt.Color(207, 206, 206));
        container.setLayout(null); 
        
        labelCPF.setBounds(150, 60, 300, 25);
        labelCPF.setFont(new Font("Arial", Font.PLAIN, 25));
        container.add(labelCPF);
        
        textoCPF.setBounds(90, 90, 300, 35);
        textoCPF.setFont(new Font("Arial", Font.PLAIN, 25));
        container.add(textoCPF);

        labelSenha.setBounds(150, 160, 300, 25);
        labelSenha.setFont(new Font("Arial", Font.PLAIN, 25));
        container.add(labelSenha);

        textoSenha.setBounds(90, 190, 300, 35);
        textoSenha.setFont(new Font("Arial", Font.PLAIN, 25));
        container.add(textoSenha);

        botaoLogin.setBounds(110, 280, 120, 35);
        container.add(botaoLogin);

        botaoLinkTelaDeCadastro.setBounds(250, 280, 120, 35);
        container.add(botaoLinkTelaDeCadastro);

        telaPrincipal.setBounds(180, 333, 120, 35);
        container.add(telaPrincipal);

        botaoLogin.addActionListener((event) -> {

            this.cpf = textoCPF.getText();
            this.senha = textoSenha.getText(); 
            
            try {
                final DatabaseConnection databaseConnection = new DatabaseConnection();
                databaseConnection.consultLogin(this.cpf, this.senha);
            } catch(final SQLException sqlException) {
                sqlException.printStackTrace();
            } catch(final ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }

        });

        botaoLinkTelaDeCadastro.addActionListener((event) -> {
            
            tela.dispose();
            new TelaCadastro();

        });

        telaPrincipal.addActionListener(event -> {
            tela.dispose();
            new TelaPrincipalCliente();
        });
        
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setContentPane(container);
        tela.pack();
        tela.setSize(500, 500);
        tela.setLocationRelativeTo(null);
        tela.setVisible(true);
        
    }
    
}
