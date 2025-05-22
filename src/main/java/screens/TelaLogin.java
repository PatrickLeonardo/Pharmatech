package screens;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class TelaLogin { 

    private String cpf;
    private String password;
    
    public TelaLogin() {
        
        // TELA
        final JFrame mainScreen = new JFrame("Pharmatech Login");

        // IMAGEM DO JFRAME (CANTO SUPERIOR ESQUERDO)
        Image imagemLogo = new ImageIcon("./img/icon.png").getImage();
        mainScreen.setIconImage(imagemLogo);

        // Container principal
        final Container mainContainer = mainScreen.getContentPane(); 
        mainContainer.setBackground(new java.awt.Color(207, 206, 206));
        mainContainer.setLayout(null); 
        
        // Label para CPF
        final JLabel CPFLabel = new JLabel("Insira seu CPF: ");
        CPFLabel.setBounds(150, 60, 300, 25);
        CPFLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        mainContainer.add(CPFLabel);
        
        // INPUT CPF
        final JTextField CPFInput = new JTextField(30);
        CPFInput.setBounds(90, 90, 300, 35);
        CPFInput.setFont(new Font("Arial", Font.PLAIN, 25));
        CPFInput.setBorder(new LineBorder(Color.BLACK, 2, true));
        mainContainer.add(CPFInput);

        // Label para Sennha
        final JLabel passwordLabel = new JLabel("Insira sua Senha: ");
        passwordLabel.setBounds(150, 160, 300, 25);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        mainContainer.add(passwordLabel);

        // INPUT SENHA
        final JTextField passwordInput = new JTextField(30);
        passwordInput.setBounds(90, 190, 300, 35);
        passwordInput.setFont(new Font("Arial", Font.PLAIN, 25));
        passwordInput.setBorder(new LineBorder(Color.BLACK, 2, true));
        mainContainer.add(passwordInput);

        // BOTAO LOGIN
        final JButton btnLogin = new JButton("Realizar Login");
        btnLogin.setBounds(110, 280, 120, 35);;
        btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mainContainer.add(btnLogin);

        // BOTAO CADASTRO
        final JButton btnTelaCadastro = new JButton("Cadastrar");
        btnTelaCadastro.setBounds(250, 280, 120, 35);
        btnTelaCadastro.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mainContainer.add(btnTelaCadastro);

        // BOTAO TELA PRINCIPAL
        final JButton btnTelaPrincipal = new JButton("Tela Principal");
        btnTelaPrincipal.setBounds(180, 333, 120, 35);
        btnTelaPrincipal.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mainContainer.add(btnTelaPrincipal);

        // Evento que será acionado quando o usuário clicar no botão Login
        btnLogin.addActionListener((event) -> {
            
            // Verifica se os campos não estão vazios
            if (CPFInput.getText().isEmpty() || passwordInput.getText().isEmpty()) {

                // Exibir mensagem alertando que os campos estão vazios
                JOptionPane.showMessageDialog(mainScreen, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                
                // Armazena as entradas
                this.cpf = CPFInput.getText();
                this.password = passwordInput.getText();

                try {
                     
                    // Se login autenticado
                    if(makeLogin(this.cpf, this.password)) {
                        
                        // Carrega Tela Principal
                        mainScreen.dispose();
                        new TelaPrincipalCliente(this.cpf);
                        
                    } else {

                        // Exibi uma mensagem falando que o Login não foi encontrado
                        JOptionPane.showMessageDialog(mainScreen, "Login não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                    
                } catch(final Exception exception) {
                    exception.printStackTrace();
                }
            }

        });

        // Evento que será acionado quando o usuário clicar no botão Cadastro
        btnTelaCadastro.addActionListener((event) -> {
            
            mainScreen.dispose();
            new TelaCadastro();

        });

        // Evento que será acionado quando o usuário clicar no botão Tela Principal
        btnTelaPrincipal.addActionListener(event -> {
            mainScreen.dispose();
            new TelaPrincipalCliente(null);
        });
        
        mainScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainScreen.setContentPane(mainContainer);
        mainScreen.pack();
        mainScreen.setSize(500, 500);
        mainScreen.setLocationRelativeTo(null);
        mainScreen.setVisible(true);
        
    }

    /**
     * Make a HTTPResquest to find client
     * @param cpf 
     * @param password 
     * @return boolean with result of request (authenticated = true | not authenticated = false)
     * @throws InterruptedException
     * @throws IOException
     */
    private boolean makeLogin(final String cpf, final String password) throws InterruptedException, IOException {
        
        final HttpRequest request = HttpRequest.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .uri(URI.create("http://localhost:8080/client/findUserByCPFAndPassword?CPF=" + this.cpf + "&password=" + this.password))
            .GET()
            .build();

        final HttpClient httpClient = HttpClient.newHttpClient();

        final HttpResponse<String> clientHttpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
        if(clientHttpResponse.statusCode() == 302) return true;
        else if(clientHttpResponse.statusCode() == 404) return false;
        
        return false;

    }
    
}
