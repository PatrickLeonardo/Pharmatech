package screens;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.json.JSONObject;

public class TelaPerfilCliente {

    private JPanel header = null;

    public TelaPerfilCliente(final JFrame jFramePrincipalCliente, final TelaPrincipal telaPrincipal, final String CPFOfAuthenticatedClient, final String passwordOfAuthenticatedClient) {
        
        final Color titleColor = new Color(1, 0, 127); // Variação de Azul
        final Color backgroundColor = new Color (207, 206, 206); // Variação de Cinza
        
        // TELA 
        final JFrame mainScreen = new JFrame("Meu Perfil");
        mainScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainScreen.setSize(700, 800);
        mainScreen.setLocationRelativeTo(null);

        // IMAGEM DO JFRAME (CANTO SUPERIOR ESQUERDO)
        final Image imagemLogo = new ImageIcon("./img/logoComFundo.png").getImage();
        mainScreen.setIconImage(imagemLogo);

        // CONTAINER PRINCIPAL
        final JPanel mainContainer = new JPanel(); 
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
        mainContainer.setBackground(backgroundColor);
        
        // CABECALHO (header)
        header = new JPanel();
        header.setLayout(null);
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        header.setPreferredSize(new Dimension(1500, 100));
        header.setMaximumSize(new Dimension(1500, 100));
        header.setMinimumSize(new Dimension(1500, 100));
        header.setBackground(backgroundColor);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.black));
        
        // LOGO 
        ImageIcon imageLogo = new ImageIcon("./img/icon.png");

        imageLogo = new ImageIcon(
            imageLogo.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH)
        );
         
        final JLabel labelLogo = new JLabel(imageLogo);
        labelLogo.setBounds(20, 5, 80, 80);
        header.add(labelLogo);

        // TITULO 
        final JLabel titlePharmatech = new JLabel("Pharmatech");
        titlePharmatech.setBounds(120, 25, 500, 50);
        titlePharmatech.setFont(new Font("Arial", Font.BOLD, 34));
        titlePharmatech.setForeground(titleColor);
        header.add(titlePharmatech);
         
        // CONFIGURAÇÃO DOS BOTÕES 
        final Font defaultFont = new Font("Helvetica", Font.BOLD, 20);

        // Mapeando font padrão para os botões para poder adicionar o Underline (TextAttribute) e Ativa-lo (Object) 
        final Map<TextAttribute, Object> defaultFontWithUnderline = (Map<TextAttribute, Object>) defaultFont.getAttributes();
        defaultFontWithUnderline.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        
        //BOTAO LOGOUT 
        final JButton btnLogout = new JButton("Logout");
        btnLogout.setBounds(400, 25, 140, 40);
        btnLogout.setContentAreaFilled(false);
        btnLogout.setBorderPainted(false);
        btnLogout.setOpaque(false);
        btnLogout.setFont(btnLogout.getFont().deriveFont(defaultFontWithUnderline));
        btnLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        header.add(btnLogout);

        btnLogout.addActionListener((event) -> {

            mainScreen.dispose();
            telaPrincipal.isLogoff();
            new TelaLogin(null, jFramePrincipalCliente, telaPrincipal);

        });

        //BOTAO TELA PRINCIPAL
        final JButton btnTelaMain = new JButton("Voltar");
        btnTelaMain.setBounds(550, 25, 140, 40);
        btnTelaMain.setContentAreaFilled(false);
        btnTelaMain.setBorderPainted(false);
        btnTelaMain.setOpaque(false);
        btnTelaMain.setFont(btnTelaMain.getFont().deriveFont(defaultFontWithUnderline));
        btnTelaMain.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        header.add(btnTelaMain);

        btnTelaMain.addActionListener((event) -> { 
            
            mainScreen.dispose();
            telaPrincipal.isLogged();
            jFramePrincipalCliente.setVisible(true);
            
        });

        mainContainer.add(header);

        final JPanel containerInfo = new JPanel();
        containerInfo.setLayout(null);

        final JLabel editLabel = new JLabel("Atualize suas informações: ");
        editLabel.setFont(new Font("Helvetica", Font.BOLD, 35));
        editLabel.setBounds(120, 50, 500, 100);
        
        containerInfo.add(editLabel);
        
        try {
            
            final JSONObject jsonOfClient =  new JSONObject(findClientByCPF(CPFOfAuthenticatedClient, passwordOfAuthenticatedClient));

            final Font fontForLabels = new Font("Helvetica", Font.BOLD, 28);
            final Font fontForInputs = new Font("Helvetiica", Font.PLAIN, 20);

            final JLabel webcomeLabel = new JLabel("Olá, " + jsonOfClient.getString("nome") + " :)");
            webcomeLabel.setFont(new Font("Helvetica", Font.BOLD, 35));
            webcomeLabel.setBounds(120, 0, 700, 100);
            containerInfo.add(webcomeLabel);

            final JLabel nameLabel = new JLabel("Nome: ");
            nameLabel.setFont(fontForLabels);
            nameLabel.setBounds(120, 150, 150, 100);
            containerInfo.add(nameLabel);

            final JTextField nameInput = new JTextField();
            nameInput.setText(jsonOfClient.getString("nome"));
            nameInput.setFont(fontForInputs);
            nameInput.setBounds(300, 180, 250, 40);
            containerInfo.add(nameInput);

            final JLabel telephoneLabel = new JLabel("Telefone: ");
            telephoneLabel.setFont(fontForLabels);
            telephoneLabel.setBounds(120, 220, 150, 100);
            containerInfo.add(telephoneLabel);

            final JTextField telephoneInput = new JTextField();
            telephoneInput.setText(jsonOfClient.getString("telefone"));
            telephoneInput.setFont(fontForInputs);
            telephoneInput.setBounds(300, 250, 250, 40);
            containerInfo.add(telephoneInput);

            final JLabel addressLabel = new JLabel("Endereço: ");
            addressLabel.setFont(fontForLabels);
            addressLabel.setBounds(120, 290, 150, 100);
            containerInfo.add(addressLabel);

            final JTextField addressInput = new JTextField();
            addressInput.setText(jsonOfClient.getString("endereco"));
            addressInput.setFont(fontForInputs);
            addressInput.setBounds(300, 320, 250, 40);
            containerInfo.add(addressInput);

            final JLabel passwordLabel = new JLabel("Senha: ");
            passwordLabel.setFont(fontForLabels);
            passwordLabel.setBounds(120, 360, 150, 100);
            containerInfo.add(passwordLabel);

            final JTextField passwordInput = new JTextField();
            passwordInput.setFont(fontForInputs);
            passwordInput.setText(jsonOfClient.getString("senha"));
            passwordInput.setBounds(300, 390, 250, 40);
            containerInfo.add(passwordInput);

            final JButton updateButton = new JButton("Atualizar Informações");
            updateButton.setFont(new Font("Helvetica", Font.BOLD, 20));
            updateButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            updateButton.setBounds(160, 500, 300, 50);

            updateButton.addActionListener((event) -> {

                final String bodyPublisher = """
                {
                    "nome": "%s",
                    "senha": "%s",
                    "telefone": "%s",
                    "endereco": "%s"
                }
                """.formatted(nameInput.getText(),passwordInput.getText(), telephoneInput.getText(), addressInput.getText());
                
                try {
                    
                    final HttpRequest request = HttpRequest.newBuilder()
                        .version(HttpClient.Version.HTTP_2)
                        .header("Content-Type", "application/json")
                        .uri(URI.create("http://localhost:8080/user/update/" + CPFOfAuthenticatedClient))
                        .PUT(HttpRequest.BodyPublishers.ofString(bodyPublisher))
                         .build();
                    
                    final HttpClient httpClient = HttpClient.newHttpClient();
                    
                    final HttpResponse<String> clientHttpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                    
                    if(clientHttpResponse.statusCode() == 200) {
                        JOptionPane.showMessageDialog(mainScreen, "Dados Atualizados!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    }

                } catch(final Exception exception) {
                    exception.printStackTrace();
                }


            });


            containerInfo.add(updateButton);

        } catch(final Exception exception) {
            exception.printStackTrace();
        }

        mainContainer.add(containerInfo);

        mainScreen.add(mainContainer);
        mainScreen.setVisible(true);

    }

    private String findClientByCPF(final String CPF, final String password) throws InterruptedException, IOException {

        final HttpRequest request = HttpRequest.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .header("Content-Type", "application/json")
            .uri(URI.create("http://localhost:8080/user/getUser/%s/%s".formatted(CPF, password)))
            .GET()
            .build();
        
        final HttpClient httpClient = HttpClient.newHttpClient();
        
        final HttpResponse<String> clientHttpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
        if(clientHttpResponse.statusCode() == 302) {
            return clientHttpResponse.body();
        }

        return "";

    }

}
