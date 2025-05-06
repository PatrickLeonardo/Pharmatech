import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class TelaLogin {
    
    public static void criarTela() {
        
        final JFrame tela = new JFrame("Pharmatech Login");
        
        tela.setLayout(new FlowLayout());
        tela.pack();
        tela.setSize(500, 500);
        tela.setLocationRelativeTo(null);
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setVisible(true);
        
        final JLabel labelCPF = new JLabel("Insira seu CPF: ");
        final JTextField textoCPF = new JTextField(30);
        
        final JLabel labelSenha = new JLabel("Insira sua Senha: ");
        final JTextField textoSenha = new JTextField(30);
        
        final JButton botaoLogin = new JButton("Realizar Login");
        
        final Container container = tela.getContentPane();
        
        container.setLayout(new GridLayout(5, 10));
        
        container.add(labelCPF, BorderLayout.CENTER);
        container.add(textoCPF, BorderLayout.CENTER);
        container.add(labelSenha, BorderLayout.CENTER);
        container.add(textoSenha, BorderLayout.CENTER);
        container.add(botaoLogin, BorderLayout.CENTER);
        
    }
    
}