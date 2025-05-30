package screens;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.font.TextAttribute;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.json.JSONObject;
import org.w3c.dom.events.MouseEvent;

import utils.CartUtilities;
import utils.MedicationsUtilities;

public class TelaCarrinho {

    private String CPFOfAuthenticatedClient = null;
    private JButton btnRegister = null;
    private JButton btnLogin = null;
    private JLabel welcomeLabel = null;
    private JPanel header = null;

    public TelaCarrinho(String CPFOfAuthenticatedClient, JFrame jFramePrincipalCliente, TelaPrincipal telaPrincipal) {
        
        this.CPFOfAuthenticatedClient = CPFOfAuthenticatedClient;
        CartUtilities cartUtilities = new CartUtilities();

        // Instancia das cores utilizadas na tela
        final Color titleColor = new Color(1, 0, 127); // Variação de Azul
        final Color backgroundColor = new Color (207, 206, 206); // Variação de Cinza
        
        // TELA 
        final JFrame mainScreen = new JFrame("Carrinho");
        mainScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainScreen.setSize(1500, 800);
        mainScreen.setLocationRelativeTo(null);

        // IMAGEM DO JFRAME (CANTO SUPERIOR ESQUERDO)
        Image imagemLogo = new ImageIcon("./img/logoComFundo.png").getImage();
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

        // BOTAO CADASTRAR 
        btnRegister = new JButton("Cadastrar");
        btnRegister.setBounds(1000, 25, 140, 40); // Define a posição e o tamanho do botão no painel (x, y, largura, altura)
        btnRegister.setContentAreaFilled(false); // Remove o preenchimento padrão do botão para deixá-lo transparente
        btnRegister.setBorderPainted(false); // Remove a borda padrão do botão
        btnRegister.setOpaque(false); // Deixa o botão opaco como falso para garantir transparência
        btnRegister.setFont(btnRegister.getFont().deriveFont(defaultFontWithUnderline)); // Define a fonte do botão com sublinhado 
        btnRegister.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Altera o cursor para "mão" ao passar o mouse sobre o botão        
        header.add(btnRegister); // Adiciona o botão ao painel de cabeçalho

        // BOTAO LOGAR 
        btnLogin = new JButton("Logar");
        btnLogin.setBounds(850, 25, 140, 40);
        btnLogin.setContentAreaFilled(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setOpaque(false);
        btnLogin.setFont(btnLogin.getFont().deriveFont(defaultFontWithUnderline));
        btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        header.add(btnLogin);

        //BOTAO TELA PRINCIPAL
        final JButton btnTelaMain = new JButton("Menu");
        btnTelaMain.setBounds(1150, 25, 140, 40);
        btnTelaMain.setContentAreaFilled(false);
        btnTelaMain.setBorderPainted(false);
        btnTelaMain.setOpaque(false);
        btnTelaMain.setFont(btnTelaMain.getFont().deriveFont(defaultFontWithUnderline));
        btnTelaMain.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        header.add(btnTelaMain);

        // BOTAO FINALIZAR RESERVA
        final JButton btnFinalizar = new JButton("Finalizar");
        btnFinalizar.setBounds(1300, 25, 140, 40);
        btnFinalizar.setContentAreaFilled(false);
        btnFinalizar.setBorderPainted(false);
        btnFinalizar.setOpaque(false);
        btnFinalizar.setFont(btnFinalizar.getFont().deriveFont(defaultFontWithUnderline));
        btnFinalizar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        header.add(btnFinalizar);

        btnTelaMain.addActionListener((event) -> { 
            
            mainScreen.dispose();
            telaPrincipal.setCPFOfAuthenticatedClient(CPFOfAuthenticatedClient);
            jFramePrincipalCliente.setVisible(true);
            
        });

        btnLogin.addActionListener((event) -> {
            new TelaLogin(CPFOfAuthenticatedClient, jFramePrincipalCliente, telaPrincipal);
            mainScreen.dispose();
        });

        // Evento que será acionado ao clicar no botão Cadastrar
        btnRegister.addActionListener((event) -> {
            new TelaCadastro(jFramePrincipalCliente, telaPrincipal);
            mainScreen.dispose();
        });

        // Evento que será acionado ao clicar no botão Finalizar 
        btnFinalizar.addActionListener((event) -> {
            
            try{
                
                if (CPFOfAuthenticatedClient == null || CPFOfAuthenticatedClient.isEmpty()) {
                    JOptionPane.showMessageDialog(mainScreen, "Você precisa estar logado para finalizar a reserva!", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                } else if (cartUtilities.isEmptyCart()) {
                    JOptionPane.showMessageDialog(mainScreen, "Você precisa adicionar ao menos um item para finalizar a reserva!", "Aviso", JOptionPane.WARNING_MESSAGE);
                }

                else {
                    JOptionPane.showMessageDialog(mainScreen, "Reserva finalizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        
        mainContainer.add(header);
        
        JPanel cartPanel = new JPanel();
        cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.Y_AXIS));
        cartPanel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        cartPanel.setBackground(backgroundColor);
        
        try {
            cartUtilities.loadCart(CPFOfAuthenticatedClient, cartPanel);
        } catch(Exception exception) {
            exception.printStackTrace();
        }
        
        mainContainer.add(cartPanel);

        mainContainer.add(Box.createVerticalGlue()); // Adiciona um espaço flexível
        
        // Configuranções ScrollPane (Rolagem da Tela) 
        final JScrollPane jScrollPane = new JScrollPane(mainContainer);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Desabilitar Scrollbar Horizontal
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Habilitar Scrollbar Vertical 
        jScrollPane.getVerticalScrollBar().setUnitIncrement(16); // Scroll mais suave (unidade de incremento)
        
        mainScreen.add(jScrollPane);
        mainScreen.setVisible(true);
        
    }

    public void isLogged() {
        
        this.header.remove(this.btnLogin);
        this.header.remove(this.btnRegister);
        
        this.welcomeLabel = new JLabel();                
        this.welcomeLabel.setBounds(850, 20, 300, 50);
        this.welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));

        try { 
            final HttpRequest request = HttpRequest.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .header("Content-Type", "application/json")
                .uri(URI.create("http://localhost:8080/user/findName/%s".formatted(this.CPFOfAuthenticatedClient)))
                .GET()
                .build();
            
            final HttpClient httpClient = HttpClient.newHttpClient(); 
            final HttpResponse<String> clientHttpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            String name = clientHttpResponse.body();
            this.welcomeLabel.setText("Bem Vindo, " + name);

        } catch(Exception exception) { exception.printStackTrace(); }
        
        this.header.add(this.welcomeLabel);
         
    }

    public void setCPFOfAuthenticatedClient(String cpf) {
        this.CPFOfAuthenticatedClient = cpf;
    }
    
}
