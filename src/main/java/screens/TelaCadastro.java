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

public class TelaCadastro {

    public TelaCadastro(String CPFOfAuthenticatedClient) {

        final JFrame mainScreen = new JFrame("Tela Cadastro");

        final Container mainContainer = mainScreen.getContentPane();
        mainContainer.setLayout(null);
        mainContainer.setBackground(new java.awt.Color(207, 206, 206));

        // IMAGEM DO JFRAME (CANTO SUPERIOR ESQUERDO)
        Image imagemLogo = new ImageIcon("./img/logoComFundo.png").getImage();
        mainScreen.setIconImage(imagemLogo);
        
        // Label para CPF
        final JLabel CPFLabel = new JLabel("Insira seu CPF: ");
        CPFLabel.setBounds(100, 60, 300, 25);
        CPFLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        mainContainer.add(CPFLabel);

        // Input CPF
        final JTextField CPFInput = new JTextField(30);
        CPFInput.setBounds(100, 90, 300, 35);
        CPFInput.setFont(new Font("Arial", Font.PLAIN, 25));
        CPFInput.setBorder(new LineBorder(Color.BLACK, 2, true));
        mainContainer.add(CPFInput);

        // Label para Nome
        final JLabel nameLabel = new JLabel("Insira seu Nome: ");
        nameLabel.setBounds(100, 160, 300, 25);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        mainContainer.add(nameLabel);

        // Input Nome
        final JTextField nameInput = new JTextField(30);
        nameInput.setBounds(100, 190, 300, 35);
        nameInput.setFont(new Font("Arial", Font.PLAIN, 25));
        nameInput.setBorder(new LineBorder(Color.BLACK, 2, true));
        mainContainer.add(nameInput);

        // Label para Telefone
        final JLabel telephoneLabel = new JLabel("Insira sua Telefone: ");
        telephoneLabel.setBounds(100, 260, 300, 25);
        telephoneLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        mainContainer.add(telephoneLabel);

        // Input Telefone
        final JTextField telephoneInput = new JTextField(30);
        telephoneInput.setBounds(100, 290, 300, 35);
        telephoneInput.setFont(new Font("Arial", Font.PLAIN, 25));
        telephoneInput.setBorder(new LineBorder(Color.BLACK, 2, true));
        mainContainer.add(telephoneInput);

        // Label para Endereço
        final JLabel addressLabel = new JLabel("Insira seu Endereço: ");
        addressLabel.setBounds(100, 360, 300, 25);
        addressLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        mainContainer.add(addressLabel);

        // Input Endereço
        final JTextField addressInput = new JTextField(30);
        addressInput.setBounds(100, 390, 300, 35);
        addressInput.setFont(new Font("Arial", Font.PLAIN, 25));
        addressInput.setBorder(new LineBorder(Color.BLACK, 2, true));
        mainContainer.add(addressInput);

        // Label para Senha
        final JLabel passwordLabel = new JLabel("Insira sua Senha: ");
        passwordLabel.setBounds(100, 460, 300, 25);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        mainContainer.add(passwordLabel);

        // Input Senha
        final JTextField passwordInput = new JTextField(30);
        passwordInput.setBounds(100, 490, 300, 35);
        passwordInput.setFont(new Font("Arial", Font.PLAIN, 25));
        passwordInput.setBorder(new LineBorder(Color.BLACK, 2, true));
        mainContainer.add(passwordInput);

        // Botão Cadastro
        final JButton btnCadastro = new JButton("Criar Conta");
        btnCadastro.setBounds(100, 570, 120, 60);
        btnCadastro.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mainContainer.add(btnCadastro);

        // Botão Tela Login
        final JButton btnTelaLogin = new JButton("Retornar");
        btnTelaLogin.setBounds(270, 570, 120, 60);
        btnTelaLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mainContainer.add(btnTelaLogin);

        // Evento que será acionado quando o usuário clicar no Botão Login
        btnCadastro.addActionListener((event) -> { 

            // Armazena as entradas
            final String cpf = CPFInput.getText();
            final String name = nameInput.getText();
            final String telephone = telephoneInput.getText();
            final String address = addressInput.getText();
            final String password = passwordInput.getText();
            
            // Verifica se nenhuma delas está vazia
            if(cpf.isEmpty() || name.isEmpty() || telephone.isEmpty() || address.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(mainScreen, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                
                try {
                    
                    // Realiza o Cadastro com as entradas passadas
                    if(makeRegister(cpf, name, telephone, address, password)) {
                        JOptionPane.showMessageDialog(mainScreen, "Cadastro realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        mainScreen.dispose();
                        new TelaLogin(null);
                    } else {
                        // Caso não consiga realizar o cadastro
                        JOptionPane.showMessageDialog(mainScreen, "Falha ao realizar Cadastro... Tente novamente!", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                    
                } catch(final Exception exception) {
                    exception.printStackTrace();
                }
            }

        });

        // Evento que será acionado quando o usúario clicar no Botão Tela Login
        btnTelaLogin.addActionListener((event) -> {
            
            mainScreen.dispose();
            new TelaLogin(CPFOfAuthenticatedClient);

        });
        
        mainScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainScreen.setContentPane(mainContainer);
        mainScreen.pack();
        mainScreen.setSize(500, 700);
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
    private boolean makeRegister(final String cpf, final String name, final String telephone, final String address, final String password) throws InterruptedException, IOException {

        final String bodyPublisher = """
        {
            "cpf": "%s",
            "nome": "%s",
            "senha": "%s",
            "telefone": "%s",
            "endereco": "%s",
            "tipoDeUsuario": ""
        }
        """.formatted(cpf, name, password, telephone, address);

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
