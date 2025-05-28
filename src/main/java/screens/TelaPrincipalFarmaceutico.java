package screens;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.json.JSONArray;

public class TelaPrincipalFarmaceutico {

    public TelaPrincipalFarmaceutico() {

         // Instancia das cores utilizadas na tela
        final Color titleColor = new Color(1, 0, 127); // Variação de Azul
        final Color backgroundColor = new Color (207, 206, 206); // Variação de Cinza
        
        // TELA 
        final JFrame mainScreen = new JFrame("Carrinho");
        mainScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainScreen.setSize(1500, 800);
        mainScreen.setLocationRelativeTo(null);

        // IMAGEM DO JFRAME (CANTO SUPERIOR ESQUERDO)
        final Image imagemLogo = new ImageIcon("./img/logoComFundo.png").getImage();
        mainScreen.setIconImage(imagemLogo);

        // CONTAINER PRINCIPAL
        final JPanel mainContainer = new JPanel(); 
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
        mainContainer.setBackground(backgroundColor);
        
        // CABECALHO (header)
        JPanel header = new JPanel();
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
        
        mainContainer.add(header);
         
        mainContainer.add(Box.createVerticalGlue()); // Adiciona um espaço flexível
        
        // Configuranções ScrollPane (Rolagem da Tela) 
        final JScrollPane jScrollPane = new JScrollPane(mainContainer);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Desabilitar Scrollbar Horizontal
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Habilitar Scrollbar Vertical 
        jScrollPane.getVerticalScrollBar().setUnitIncrement(16); // Scroll mais suave (unidade de incremento)
        
        mainScreen.add(jScrollPane);
        mainScreen.setVisible(true);

    }

    private JSONArray getCarts() {

        try {
            
            final HttpRequest request = HttpRequest.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .header("Content-Type", "application/json")
                .uri(URI.create("http://localhost:8080/cart/findAll"))
                .GET()
                .build();
            
            final HttpClient httpClient = HttpClient.newHttpClient(); 
            final HttpResponse<String> clientHttpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return new JSONArray(clientHttpResponse.body());

        } catch(Exception exception) {
            exception.printStackTrace();
        }

        return new JSONArray();

    }

}
