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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import org.json.JSONObject;

import utils.CPFUtilities;

public class TelaLogin { 

    private String cpf;
    private char[] password;
    
    public TelaLogin(final String CPFOfAuthenticatedClient, final JFrame jFrameTelaPrincipal, final TelaPrincipal telaPrincipal) {

        // TELA
        final JFrame mainScreen = new JFrame("Pharmatech Login");
        mainScreen.setResizable(false);

        // IMAGEM DO JFRAME (CANTO SUPERIOR ESQUERDO)
        final Image imagemLogo = new ImageIcon("./img/logoComFundo.png").getImage();
        mainScreen.setIconImage(imagemLogo);

        // Container principal
        final Container mainContainer = mainScreen.getContentPane(); 
        mainContainer.setBackground(new Color(207, 206, 206));
        mainContainer.setLayout(null); 
        
        final Font labelsFont = new Font("Arial", Font.BOLD, 25);
        final Font inputsFont = new Font("Arial", Font.PLAIN, 25);
        final LineBorder inputsLineBorder = new LineBorder(Color.BLACK, 2, true);

        // Label para CPF
        final JLabel CPFLabel = new JLabel("Insira seu CPF: ");
        CPFLabel.setBounds(150, 60, 300, 25);
        CPFLabel.setFont(labelsFont);
        mainContainer.add(CPFLabel);
        
        // INPUT CPF
        final JTextField CPFInput = new JTextField(30);
        CPFInput.setBounds(90, 90, 300, 35);
        CPFInput.setFont(inputsFont);
        CPFInput.setBorder(inputsLineBorder);
        mainContainer.add(CPFInput);

        // Label para Sennha
        final JLabel passwordLabel = new JLabel("Insira sua Senha: ");
        passwordLabel.setBounds(150, 160, 300, 25);
        passwordLabel.setFont(labelsFont);
        mainContainer.add(passwordLabel);

        // INPUT SENHA
        final JPasswordField passwordInput = new JPasswordField(30);
        passwordInput.setEchoChar('*');
        passwordInput.setBounds(90, 190, 300, 35);
        passwordInput.setFont(inputsFont);
        passwordInput.setBorder(inputsLineBorder);
        mainContainer.add(passwordInput);

        // FONTE BOTÃO 
        final Font btnFont = new Font("Helvetica", Font.BOLD, 20);

        // BOTAO LOGIN
        final JButton btnLogin = new JButton("Entrar");
        btnLogin.setBounds(70, 280, 150, 35);;
        btnLogin.setFont(btnFont);
        btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mainContainer.add(btnLogin);

        // BOTAO CADASTRO
        final JButton btnTelaCadastro = new JButton("Cadastrar");
        btnTelaCadastro.setBounds(260, 280, 150, 35);
        btnTelaCadastro.setFont(btnFont);
        btnTelaCadastro.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mainContainer.add(btnTelaCadastro);

        // BOTAO TELA PRINCIPAL
        final JButton btnTelaPrincipal = new JButton("Tela Principal");
        btnTelaPrincipal.setBounds(145, 330, 200, 35);
        btnTelaPrincipal.setFont(btnFont);
        btnTelaPrincipal.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mainContainer.add(btnTelaPrincipal);

        // Evento que será acionado quando o usuário clicar no botão Login
        btnLogin.addActionListener((event) -> {
            
            // Verifica se os campos não estão vazios
            if (CPFInput.getText().isEmpty() || passwordInput.getPassword().length == 0) {

                // Exibir mensagem alertando que os campos estão vazios
                JOptionPane.showMessageDialog(mainScreen, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                
                // Armazena as entradas
                this.cpf = CPFUtilities.format(CPFInput.getText());
                this.password = passwordInput.getPassword();

                try {
                     
                    // Se login autenticado
                    if(makeLogin(this.cpf, this.password)) {
                        
                        // Carrega Tela Principal
                        mainScreen.dispose();
                        final String tipoDeUsuario = getTipoDeUsuario(cpf, password);

                        switch(tipoDeUsuario) {
                            
                            case("Cliente"):
                                telaPrincipal.setCPFOfAuthenticatedClient(this.cpf);
                                telaPrincipal.setPasswordOfAuthenticatedClient(new String(this.password));
                                telaPrincipal.isLogged();
                                jFrameTelaPrincipal.setVisible(true);
                                break;
                            
                            case("Farmaceutico"):
                                new TelaPrincipalFarmaceutico(this.cpf, jFrameTelaPrincipal, telaPrincipal);
                                break;

                            case("Almoxerife"):
                                new TelaPrincipalAlmoxerife(this.cpf, jFrameTelaPrincipal, telaPrincipal);
                                break;

                            case("Gerente"):
                                new TelaPrincipalGerente(this.cpf, jFrameTelaPrincipal, telaPrincipal);
                                break;

                        }
                        
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
            new TelaCadastro(jFrameTelaPrincipal, telaPrincipal); 

        });

        // Evento que será acionado quando o usuário clicar no botão Tela Principal
        btnTelaPrincipal.addActionListener(event -> {

            mainScreen.dispose();
            jFrameTelaPrincipal.setVisible(true);
               
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
    private boolean makeLogin(final String cpf, final char[] password) throws InterruptedException, IOException {
        
        final HttpRequest request = HttpRequest.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .uri(URI.create("http://localhost:8080/user/find/" + cpf + "/" + new String(password)))
            .GET()
            .build();

        final HttpClient httpClient = HttpClient.newHttpClient();

        final HttpResponse<String> clientHttpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
        if(clientHttpResponse.statusCode() == 302) return true;
        else if(clientHttpResponse.statusCode() == 404) return false;
        
        return false;

    }

    private String getTipoDeUsuario(final String cpf, final char[] password) throws InterruptedException, IOException {

        final HttpRequest request = HttpRequest.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .uri(URI.create("http://localhost:8080/user/getUser/" + cpf + "/" + new String(password)))
            .GET()
            .build();

        final HttpClient httpClient = HttpClient.newHttpClient();

        final HttpResponse<String> clientHttpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return new JSONObject(clientHttpResponse.body()).getString("tipoDeUsuario");

    }
    
}
