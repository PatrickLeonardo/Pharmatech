package screens;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

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
        final JButton botaoLinkTelaDeCadastro = new JButton("Cadastrar");
        final JButton telaPrincipal = new JButton("Tela Principal");
        final Container container = tela.getContentPane(); 

        container.setBackground(new java.awt.Color(207, 206, 206));
        container.setLayout(null); 
        
        labelCPF.setBounds(150, 60, 300, 25);
        labelCPF.setFont(new Font("Arial", Font.PLAIN, 25));
        container.add(labelCPF);
        
        textoCPF.setBounds(90, 90, 300, 35);
        textoCPF.setFont(new Font("Arial", Font.PLAIN, 25));
        textoCPF.setBorder(new LineBorder(Color.BLACK, 2, true));
        container.add(textoCPF);

        labelSenha.setBounds(150, 160, 300, 25);
        labelSenha.setFont(new Font("Arial", Font.PLAIN, 25));
        container.add(labelSenha);

        textoSenha.setBounds(90, 190, 300, 35);
        textoSenha.setFont(new Font("Arial", Font.PLAIN, 25));
        textoSenha.setBorder(new LineBorder(Color.BLACK, 2, true));
        container.add(textoSenha);

        botaoLogin.setBounds(110, 280, 120, 35);
        container.add(botaoLogin);

        botaoLinkTelaDeCadastro.setBounds(250, 280, 120, 35);
        container.add(botaoLinkTelaDeCadastro);

        telaPrincipal.setBounds(180, 333, 120, 35);
        container.add(telaPrincipal);

        botaoLogin.addActionListener((event) -> {
            
            if (textoCPF.getText().isEmpty() || textoSenha.getText().isEmpty()) {
                JOptionPane.showMessageDialog(tela, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                
                this.cpf = textoCPF.getText();
                this.senha = textoSenha.getText();

                try {
                     
                    if(realizarLogin(this.cpf, this.senha)) {
                        tela.dispose();
                        new TelaPrincipalCliente();
                    } else {
                        JOptionPane.showMessageDialog(tela, "Login não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                    
                } catch(final Exception exception) {
                    exception.printStackTrace();
                }
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

    private boolean realizarLogin(final String cpf, final String senha) throws InterruptedException, IOException {
        
        final HttpRequest request = HttpRequest.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .uri(URI.create("http://localhost:8080/client/findUserByCPFAndPassword?CPF=" + this.cpf + "&password=" + this.senha))
            .GET()
            .build();

        final HttpClient httpClient = HttpClient.newHttpClient();

        final HttpResponse<String> clientHttpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
        if(clientHttpResponse.statusCode() == 302) return true;
        else if(clientHttpResponse.statusCode() == 404) return false;
        
        return false;

    }
    
}
