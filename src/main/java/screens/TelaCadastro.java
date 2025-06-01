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

import utils.CPFUtilities;

public class TelaCadastro {

    public TelaCadastro(JFrame jFrameTelaPrincipal, TelaPrincipal telaPrincipal) {

        final JFrame mainScreen = new JFrame("Tela Cadastro");
        mainScreen.setResizable(false);

        final Container mainContainer = mainScreen.getContentPane();
        mainContainer.setLayout(null);
        mainContainer.setBackground(new java.awt.Color(207, 206, 206));

        // IMAGEM DO JFRAME (CANTO SUPERIOR ESQUERDO)
        Image imagemLogo = new ImageIcon("./img/logoComFundo.png").getImage();
        mainScreen.setIconImage(imagemLogo);
        
        Font labelsFont = new Font("Arial", Font.BOLD, 25);
        Font inputsFont = new Font("Arial", Font.PLAIN, 25);
        LineBorder inputsLineBorder = new LineBorder(Color.BLACK, 2, true);

        // Label para CPF
        final JLabel CPFLabel = new JLabel("Insira seu CPF: ");
        CPFLabel.setBounds(100, 60, 300, 25);
        CPFLabel.setFont(labelsFont);
        mainContainer.add(CPFLabel);

        // Input CPF
        final JTextField CPFInput = new JTextField(30);
        CPFInput.setBounds(100, 90, 300, 35);
        CPFInput.setFont(inputsFont);
        CPFInput.setBorder(inputsLineBorder);
        mainContainer.add(CPFInput);

        // Label para Nome
        final JLabel nameLabel = new JLabel("Insira seu Nome: ");
        nameLabel.setBounds(100, 160, 300, 25);
        nameLabel.setFont(labelsFont);
        mainContainer.add(nameLabel);

        // Input Nome
        final JTextField nameInput = new JTextField(30);
        nameInput.setBounds(100, 190, 300, 35);
        nameInput.setFont(inputsFont);
        nameInput.setBorder(inputsLineBorder);
        mainContainer.add(nameInput);

        // Label para Telefone
        final JLabel telephoneLabel = new JLabel("Insira seu Telefone: ");
        telephoneLabel.setBounds(100, 260, 300, 25);
        telephoneLabel.setFont(labelsFont);
        mainContainer.add(telephoneLabel);

        // Input Telefone
        final JTextField telephoneInput = new JTextField(30);
        telephoneInput.setBounds(100, 290, 300, 35);
        telephoneInput.setFont(inputsFont);
        telephoneInput.setBorder(inputsLineBorder);
        mainContainer.add(telephoneInput);

        // Label para Endereço
        final JLabel addressLabel = new JLabel("Insira seu Endereço: ");
        addressLabel.setBounds(100, 360, 300, 25);
        addressLabel.setFont(labelsFont);
        mainContainer.add(addressLabel);

        // Input Endereço
        final JTextField addressInput = new JTextField(30);
        addressInput.setBounds(100, 390, 300, 35);
        addressInput.setFont(inputsFont);
        addressInput.setBorder(inputsLineBorder);
        mainContainer.add(addressInput);

        // Label para Senha
        final JLabel passwordLabel = new JLabel("Insira sua Senha: ");
        passwordLabel.setBounds(100, 460, 300, 25);
        passwordLabel.setFont(labelsFont);
        mainContainer.add(passwordLabel);

        // Input Senha
        final JPasswordField passwordInput = new JPasswordField(30);
        passwordInput.setEchoChar('*');
        passwordInput.setBounds(100, 490, 300, 35);
        passwordInput.setFont(inputsFont);
        passwordInput.setBorder(inputsLineBorder);
        mainContainer.add(passwordInput);

        // FONTE BOTÃO 
        Font btnFont = new Font("Helvetica", Font.BOLD, 20);

        // Botão Cadastro
        final JButton btnCadastro = new JButton("Cadastrar");
        btnCadastro.setBounds(80, 570, 150, 35);
        btnCadastro.setFont(btnFont);
        btnCadastro.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mainContainer.add(btnCadastro);

        // Botão Tela Login
        final JButton btnLogin = new JButton("Entrar");
        btnLogin.setBounds(270, 570, 150, 35);
        btnLogin.setFont(btnFont);
        btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mainContainer.add(btnLogin);

        // Botão Tela Principal Cliente
        final JButton btnTelaPrincipal = new JButton("Tela Principal");
        btnTelaPrincipal.setBounds(145, 620, 200, 35);
        btnTelaPrincipal.setFont(btnFont);
        btnTelaPrincipal.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mainContainer.add(btnTelaPrincipal);

        // Evento que será acionado quando o usuário clicar no Botão Login
        btnCadastro.addActionListener((event) -> { 

            // Armazena as entradas
            String cpf = CPFInput.getText();
            final String name = nameInput.getText();
            final String telephone = telephoneInput.getText();
            final String address = addressInput.getText();
            final char[] password = passwordInput.getPassword();
            
            // Verifica se nenhuma delas está vazia
            if(cpf.isEmpty() || name.isEmpty() || telephone.isEmpty() || address.isEmpty() || password.length == 0) {
                JOptionPane.showMessageDialog(mainScreen, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                
                try {
                    
                    cpf = CPFUtilities.format(cpf);
                    
                    if(!CPFUtilities.validate(cpf)) {
                        
                        JOptionPane.showMessageDialog(mainScreen, "Insira um CPF válido...", "Erro", JOptionPane.ERROR_MESSAGE);
                        
                    } else {
                        
                        // Realiza o Cadastro com as entradas passadas
                        if(makeRegister(cpf, name, telephone, address, password)) {
                            
                            JOptionPane.showMessageDialog(mainScreen, "Cadastro realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                            mainScreen.dispose();
                            new TelaLogin(null, jFrameTelaPrincipal, telaPrincipal);
                            
                        } else {
                            // Caso não consiga realizar o cadastro
                            JOptionPane.showMessageDialog(mainScreen, "CPF já cadastrado...", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                        
                    }

                } catch(final Exception exception) {
                    exception.printStackTrace();
                }
            }

        });

        // Evento que será acionado quando o usúario clicar no Botão Tela Login
        btnLogin.addActionListener((event) -> {
            
            mainScreen.dispose();
            new TelaLogin(null, jFrameTelaPrincipal, telaPrincipal);

        });

        // Evento que será acionado quando o usúario clicar no Botão Tela Login
        btnTelaPrincipal.addActionListener((event) -> {
            
            mainScreen.dispose();
            jFrameTelaPrincipal.setVisible(true);

        });
        
        mainScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainScreen.setContentPane(mainContainer);
        mainScreen.pack();
        mainScreen.setSize(500, 750);
        mainScreen.setLocationRelativeTo(null);
        mainScreen.setVisible(true);

    }

    /**
     * Make a HTTP Request to make a new Register
     * @param cpf
     * @param name
     * @param telephone
     * @param address
     * @param password
     * @return boolean with status of request (true if success | false if a mistake occurs)
     * @throws InterruptedException
     * @throws IOException
     */
    private boolean makeRegister(final String cpf, final String name, final String telephone, final String address, final char[] password) throws InterruptedException, IOException {

        final String bodyPublisher = """
        {
            "cpf": "%s",
            "nome": "%s",
            "senha": "%s",
            "telefone": "%s",
            "endereco": "%s",
            "tipoDeUsuario": ""
        }
        """.formatted(cpf, name, new String(password), telephone, address);

        final HttpRequest request = HttpRequest.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .header("Content-Type", "application/json")
            .uri(URI.create("http://localhost:8080/client/newClient"))
            .POST(HttpRequest.BodyPublishers.ofString(bodyPublisher))
            .build();

        final HttpClient httpClient = HttpClient.newHttpClient();

        final HttpResponse<String> clientHttpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
        if(clientHttpResponse.statusCode() == 202) return true;
        else if(clientHttpResponse.statusCode() == 406) return false;
        else if(clientHttpResponse.statusCode() == 408) return false;
        
        return false;

    }

}
