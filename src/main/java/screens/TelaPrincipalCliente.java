package screens;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import utils.MedicationsUtilities;

public class TelaPrincipalCliente {
    
    // Variavel para verificar se a caixa de pesquisa já foi clicada alguma vez  
    boolean searchBoxPressedForTheFirstTime = false;

    public TelaPrincipalCliente(String CPFOfAuthenticatedClient) {
        
        // Instancia das cores utilizadas na tela
        final Color titleColor = new Color(1, 0, 127); // Variação de Azul
        final Color backgroundColor = new Color (207, 206, 206); // Variação de Cinza
        
        final MedicationsUtilities medicationsUtilities = new MedicationsUtilities(CPFOfAuthenticatedClient);
        
        // Painel onde serão carregados os medicamentos
        final JPanel medicationsPanel = new JPanel();

        // TELA 
        final JFrame mainScreen = new JFrame("Tela Principal");
        mainScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainScreen.setSize(1500, 800);
        mainScreen.setLocationRelativeTo(null);

        // IMAGEM DO JFRAME (CANTO SUPERIOR ESQUERDO)
        Image imagemLogo = new ImageIcon("./img/icon.png").getImage();
        mainScreen.setIconImage(imagemLogo);

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

        // CAIXA DE PESQUISA 
        final JTextField searchBox = new JTextField("  Procure por um Medicamento...");
        searchBox.setBounds(450, 25, 500, 50); 
        searchBox.setFont(new Font("Arial", Font.PLAIN, 25));
        header.add(searchBox);

        // Evento que será acionado quando usuário clicar na barra de pesquisa 
        searchBox.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(final MouseEvent mouseEvent) {
                searchBox.setText("  ");
            }

        });

        // Evento que será acionado quando o usuário apertar Enter na barra de pesquisa 
        searchBox.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(final ActionEvent e) {
                
                try {
                    
                    //JPanel loadedMessageLabel = medicationsUtilities.loadMessageLabel("CARREGANDO MEDICAMENTOS...", painelMedicamentos);
                    //painelMedicamentos.remove(loadedMessageLabel);
                    
                    // Carregar medicamento pesquisado 
                    medicationsUtilities.findMedicationAndLoad(searchBox.getText(), medicationsPanel, mainScreen);
                    
                } catch (final Exception exception) {
                    exception.printStackTrace();
                }
                
            } 

        });

        // Evento que será acionado quando o usuário clicar na barra de pesquisa pela primeira vez 
        searchBox.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(final KeyEvent e) {

                if (!searchBoxPressedForTheFirstTime) {
                    final String newText = "  " + searchBox.getText().charAt(searchBox.getText().length()-1);
                    searchBox.setText(newText.replace(".", ""));
                    searchBoxPressedForTheFirstTime = true;
                }
                
            }

        });
        
        // CONFIGURAÇÃO DOS BOTÕES (Carrinho, Cadastrar e Logar) 
        final Font defaultFont = new Font("Helvetica", Font.BOLD, 20);

        // Mapeando font padrão para os botões para poder adicionar o Underline (TextAttribute) e Ativa-lo (Object) 
        final Map<TextAttribute, Object> defaultFontWithUnderline = (Map<TextAttribute, Object>) defaultFont.getAttributes();
        defaultFontWithUnderline.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);

        // BOTAO CARRINHO 
        final JButton btnCart = new JButton("Carrinho");
        btnCart.setBounds(1300, 25, 140, 40);
        btnCart.setContentAreaFilled(false);
        btnCart.setBorderPainted(false);
        btnCart.setOpaque(false);
        btnCart.setFont(btnCart.getFont().deriveFont(defaultFontWithUnderline));
        btnCart.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        header.add(btnCart);

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
        
        mainContainer.add(header);
        
        // PAINEL EXIBIÇÃO MEDICAMENTOS
        medicationsPanel.setLayout(new BoxLayout(medicationsPanel, BoxLayout.Y_AXIS));
        medicationsPanel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        mainContainer.add(medicationsPanel);
        
        // Evento que será acionado ao clicar no botão Logar
        btnLogin.addActionListener((event) -> {
            new TelaLogin(CPFOfAuthenticatedClient);
            mainScreen.dispose();
        });

        // Evento que será acionado ao clicar no botão Cadastrar
        btnRegister.addActionListener((event) -> {
            new TelaCadastro(CPFOfAuthenticatedClient);
            mainScreen.dispose();
        });
         
        // Evento que será acionado ao clicar no botão Carrinho
        btnCart.addActionListener((event) -> {
            new TelaCarrinho(CPFOfAuthenticatedClient);
            mainScreen.dispose();
        });

        // Carregar Medicamentos ao Carregar a Tela Principal 
        try {
            medicationsUtilities.loadMedications(medicationsUtilities.getMedications(), medicationsPanel, mainScreen);
        } catch (final Exception exception) {
            exception.printStackTrace();
        }

        // Configuranções ScrollPane (Rolagem da Tela) 
        final JScrollPane jScrollPane = new JScrollPane(mainContainer);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Desabilitar Scrollbar Horizontal
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Habilitar Scrollbar Vertical 
        jScrollPane.getVerticalScrollBar().setUnitIncrement(16); // Scroll mais suave (unidade de incremento)

        mainScreen.add(jScrollPane);
        mainScreen.setVisible(true);

    }    

}
