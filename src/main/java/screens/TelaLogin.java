package screens;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JComponent;
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
        
        final ArrayList<JComponent> componentsList = new ArrayList<JComponent>(); 
        Collections.addAll(
            componentsList,
            labelCPF, textoCPF,
            labelSenha, textoSenha,
            botaoLogin, botaoLinkTelaDeCadastro
        );
        
        for(final JComponent component : componentsList){

            if(component instanceof JLabel) JFrameComponent.setProperty((JLabel) component);
            else if(component instanceof JTextField) JFrameComponent.setProperty((JTextField) component);
            else if(component instanceof JButton) JFrameComponent.setProperty((JButton) component);
            
        }

        final Container container = tela.getContentPane(); 
        container.setLayout(new GridLayout(5, 10)); 

        JFrameComponent.dump(container, componentsList);        

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
