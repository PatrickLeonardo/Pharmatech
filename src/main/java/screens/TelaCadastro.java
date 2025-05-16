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
        final JButton botaoLinkTelaLogin = new JButton("Retornar");
        final Container container = tela.getContentPane();

        container.setLayout(null);
        container.setBackground(new java.awt.Color(207, 206, 206));
        
        //PARTE DO CPF
        labelCPF.setBounds(100, 60, 300, 25);
        labelCPF.setFont(new Font("Arial", Font.PLAIN, 25));
        container.add(labelCPF);
        textoCPF.setBounds(100, 90, 300, 35);
        textoCPF.setFont(new Font("Arial", Font.PLAIN, 25));
        textoCPF.setBorder(new LineBorder(Color.BLACK, 2, true));
        container.add(textoCPF);

        //PARTE DO NOME
        labelNome.setBounds(100, 160, 300, 25);
        labelNome.setFont(new Font("Arial", Font.PLAIN, 25));
        container.add(labelNome);
        textoNome.setBounds(100, 190, 300, 35);
        textoNome.setFont(new Font("Arial", Font.PLAIN, 25));
        textoNome.setBorder(new LineBorder(Color.BLACK, 2, true));
        container.add(textoNome);

        //PARTE DO TELEFONE
        labelTelefone.setBounds(100, 260, 300, 25);
        labelTelefone.setFont(new Font("Arial", Font.PLAIN, 25));
        container.add(labelTelefone);
        textoTelefone.setBounds(100, 290, 300, 35);
        textoTelefone.setFont(new Font("Arial", Font.PLAIN, 25));
        textoTelefone.setBorder(new LineBorder(Color.BLACK, 2, true));
        container.add(textoTelefone);

        //PARTE DO ENDEREÇO
        labelEndereco.setBounds(100, 360, 300, 25);
        labelEndereco.setFont(new Font("Arial", Font.PLAIN, 25));
        container.add(labelEndereco);   
        textoEndereco.setBounds(100, 390, 300, 35);
        textoEndereco.setFont(new Font("Arial", Font.PLAIN, 25));
        textoEndereco.setBorder(new LineBorder(Color.BLACK, 2, true));
        container.add(textoEndereco);

        //PARTE DA SENHA
        labelSenha.setBounds(100, 460, 300, 25);
        labelSenha.setFont(new Font("Arial", Font.PLAIN, 25));
        container.add(labelSenha);
        textoSenha.setBounds(100, 490, 300, 35);
        textoSenha.setFont(new Font("Arial", Font.PLAIN, 25));
        textoSenha.setBorder(new LineBorder(Color.BLACK, 2, true));
        container.add(textoSenha);

        botaoCadastro.setBounds(100, 570, 120, 60);
        container.add(botaoCadastro);

        botaoLinkTelaLogin.setBounds(270, 570, 120, 60);
        container.add(botaoLinkTelaLogin);

        botaoCadastro.addActionListener((event) -> { 

            final String cpf = textoCPF.getText();
            final String nome = textoNome.getText();
            final String telefone = textoTelefone.getText();
            final String endereco = textoEndereco.getText();
            final String senha = textoSenha.getText();
            
            if(cpf.isEmpty() || nome.isEmpty() || telefone.isEmpty() || endereco.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(tela, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                
                try {
                     
                    if(realizarCadastro(cpf, nome, telefone, endereco, senha)) {
                        tela.dispose();
                        new TelaPrincipalCliente();
                        JOptionPane.showMessageDialog(tela, "Cadastro realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(tela, "Login não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                    
                } catch(final Exception exception) {
                    exception.printStackTrace();
                }
            }

        });

        botaoLinkTelaLogin.addActionListener((event) -> {
            
            tela.dispose();
            new TelaLogin();

        });
        
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setContentPane(container);
        tela.pack();
        tela.setSize(500, 700);
        tela.setLocationRelativeTo(null);
        tela.setVisible(true);

    }

    private boolean realizarCadastro(final String cpf, final String nome, final String telefone, final String endereco, final String senha) throws InterruptedException, IOException {

        final String bodyPublisher = """
        {
            "cpf": "%s",
            "nome": "%s",
            "senha": "%s",
            "telefone": "%s",
            "endereco": "%s",
            "tipoDeUsuario": ""
        }
        """.formatted(cpf, nome, senha, telefone, endereco);

        final HttpRequest request = HttpRequest.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .header("Content-Type", "application/json")
            .uri(URI.create("http://localhost:8080/client/newClient"))
            .POST(HttpRequest.BodyPublishers.ofString(bodyPublisher))
            .build();

        final HttpClient httpClient = HttpClient.newHttpClient();

        final HttpResponse<String> clientHttpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
        if(clientHttpResponse.statusCode() == 202) return true;
        else if(clientHttpResponse.statusCode() == 408) return false;
        
        return false;

    }

}
