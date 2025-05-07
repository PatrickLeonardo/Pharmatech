package screens;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class TelaCadastro {

    public TelaCadastro() {

        final JFrame tela = new JFrame("Tela Cadastro");
        
        final JLabel labelCPF = new JLabel("Insira seu CPF: ");
        final JTextField textoCPF = new JTextField(30);
        final JLabel labelNome = new JLabel("Insira seu Nome: ");
        final JTextField textoNome = new JTextField(30);
        final JLabel labelTelefone = new JLabel("Insira sua Telefone: ");
        final JTextField textoTelefone = new JTextField(30);
        final JLabel labelEndereco = new JLabel("Insira seu Endereço: ");
        final JTextField textoEndereco = new JTextField(30);
        final JLabel labelSenha = new JLabel("Insira sua Senha: ");
        final JTextField textoSenha = new JTextField(30);
        final JButton botaoCadastro = new JButton("Criar Conta");
        final JButton botaoLinkTelaLogin = new JButton("Realizar Login");
        
        final ArrayList<JComponent> componentsList = new ArrayList<JComponent>();
        Collections.addAll(
            componentsList,
            labelCPF, textoCPF,
            labelNome, textoNome,
            labelTelefone, textoTelefone,
            labelEndereco, textoEndereco,
            labelSenha, textoSenha,
            botaoCadastro, botaoLinkTelaLogin
        );

        for(final JComponent component : componentsList){

            if(component instanceof JLabel) JFrameComponent.setProperty((JLabel) component);
            else if(component instanceof JTextField) JFrameComponent.setProperty((JTextField) component); 
            else if(component instanceof JButton) JFrameComponent.setProperty((JButton) component);

        }

        final Container container = tela.getContentPane();
        container.setLayout(new GridLayout(14, 10));

        JFrameComponent.dump(container, componentsList);

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
