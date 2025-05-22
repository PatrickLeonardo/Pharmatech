package screens;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.font.TextAttribute;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.w3c.dom.events.MouseEvent;

import utils.CartUtilities;
import utils.MedicationsUtilities;

public class TelaCarrinho {

    public TelaCarrinho(String CPFOfAuthenticatedClient) {

        // Instancia das cores utilizadas na tela
        final Color titleColor = new Color(1, 0, 127); // Variação de Azul
        final Color backgroundColor = new Color (207, 206, 206); // Variação de Cinza
        
        // TELA 
        final JFrame mainScreen = new JFrame("Carrinho");
        mainScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainScreen.setSize(1500, 800);
        mainScreen.setLocationRelativeTo(null);

        // CONTAINER PRINCIPAL
        final JPanel mainContainer = new JPanel(); 
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
        mainContainer.setBackground(backgroundColor);
        
        // CABECALHO (header)
        final JPanel header = new JPanel();
        header.setLayout(null);
        header.setPreferredSize(new Dimension(1500, 100));
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
   
        // CONFIGURAÇÃO DOS BOTÕES (Carrinho, Cadastrar e Logar) 
        final Font defaultFont = new Font("Helvetica", Font.BOLD, 20);

        // Mapeando font padrão para os botões para poder adicionar o Underline (TextAttribute) e Ativa-lo (Object) 
        final Map<TextAttribute, Object> defaultFontWithUnderline = (Map<TextAttribute, Object>) defaultFont.getAttributes();
        defaultFontWithUnderline.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);

        // BOTAO CADASTRAR 
        final JButton btnRegister = new JButton("Cadastrar");
        btnRegister.setBounds(1150, 25, 140, 40);
        btnRegister.setContentAreaFilled(false);
        btnRegister.setBorderPainted(false);
        btnRegister.setOpaque(false);
        btnRegister.setFont(btnRegister.getFont().deriveFont(defaultFontWithUnderline));
        btnRegister.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        header.add(btnRegister);

        // BOTAO LOGAR 
        final JButton btnLogin = new JButton("Logar");
        btnLogin.setBounds(1000, 25, 140, 40);
        btnLogin.setContentAreaFilled(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setOpaque(false);
        btnLogin.setFont(btnLogin.getFont().deriveFont(defaultFontWithUnderline));
        btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        header.add(btnLogin);

        btnLogin.addActionListener((event) -> {
            new TelaLogin();
            mainScreen.dispose();
        });

        // Evento que será acionado ao clicar no botão Cadastrar
        btnRegister.addActionListener((event) -> {
            new TelaCadastro();
            mainScreen.dispose();
        });
        
        mainContainer.add(header);
        
        JPanel cartPanel = new JPanel();
        cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.Y_AXIS));
        cartPanel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        cartPanel.setBackground(backgroundColor);
        
        try {
            new CartUtilities().loadCart(CPFOfAuthenticatedClient, cartPanel);
        } catch(Exception exception) {
            exception.printStackTrace();
        }
        
        mainContainer.add(cartPanel);

        // Configuranções ScrollPane (Rolagem da Tela) 
        final JScrollPane jScrollPane = new JScrollPane(mainContainer);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Desabilitar Scrollbar Horizontal
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Habilitar Scrollbar Vertical 
        jScrollPane.getVerticalScrollBar().setUnitIncrement(16); // Scroll mais suave (unidade de incremento)

        mainScreen.add(jScrollPane);
        mainScreen.setVisible(true);

    }

}
