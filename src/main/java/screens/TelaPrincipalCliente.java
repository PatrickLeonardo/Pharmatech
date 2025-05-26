package screens;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.json.JSONArray;
import org.json.JSONObject;

import utils.MedicationsUtilities;

public class TelaPrincipalCliente {
    
    // Variavel para verificar se a caixa de pesquisa já foi clicada alguma vez  
    private boolean searchBoxPressedForTheFirstTime = false;

    private String CPFOfAuthenticatedClient           = null;
    private JPanel header                             = null;
    private JLabel welcomeLabel                       = null;
    private JButton btnRegister                       = null;
    private JButton btnLogin                          = null;

    public TelaPrincipalCliente(final String CPFOfAuthenticatedClient) {
        
        this.CPFOfAuthenticatedClient = CPFOfAuthenticatedClient;
        TelaPrincipalCliente bufferedThis = this;

        // Instancia das cores utilizadas na tela
        final Color titleColor = new Color(1, 0, 127); // Variação de Azul
        final Color backgroundColor = new Color (207, 206, 206); // Variação de Cinza
         
        // TELA 
        final JFrame mainScreen = new JFrame("Tela Principal");
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

        // CABEÇALHO (header)
        header = new JPanel();
        header.setLayout(null);
        header.setPreferredSize(new Dimension(1500, 100));
        header.setBackground(backgroundColor);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.black));

        // PAINEL EXIBIÇÃO MEDICAMENTOS
        // Painel onde serão carregados os medicamentos
        final JPanel medicationsPanel = new JPanel();
        medicationsPanel.setLayout(new BoxLayout(medicationsPanel, BoxLayout.Y_AXIS));
        medicationsPanel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        
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
        
        searchBox.addFocusListener(new FocusAdapter() {
            
            @Override
            public void focusGained(FocusEvent e) {

                if(searchBox.getText().equals("  Procure por um Medicamento...") && searchBoxPressedForTheFirstTime) {
                    searchBox.setText("  ");
                } else if(searchBox.getText().strip().equals("")) {
                    searchBox.setText("  Procure por um Medicamento");
                    searchBoxPressedForTheFirstTime = false;
                }
            
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(searchBox.getText().strip().equals("")) {
                    searchBox.setText("  Procure por um Medicamento");
                    searchBoxPressedForTheFirstTime = false;
                }
            }

        });

        // Evento que será acionado quando o usuário clicar na barra de pesquisa pela primeira vez 
        searchBox.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(final KeyEvent e) {

                if (!searchBoxPressedForTheFirstTime) {
                    final String newText = "  " + searchBox.getText().charAt(searchBox.getText().length()-1);
                    searchBox.setText(newText.replace("o", ""));
                    searchBoxPressedForTheFirstTime = true;
                }
                
            }

        });

        mainContainer.addMouseListener(new MouseAdapter() {
            
            public void mouseClicked(MouseEvent e) {
                searchBox.setFocusable(false);
                searchBox.setFocusable(true);
                searchBox.requestFocus();
            }
            
        });
 
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
                    
                    // Carregar medicamento pesquisado 
                    JSONArray findedMedications = MedicationsUtilities.findMedications(searchBox.getText());
                     
                    if (searchBox.getText().replace(" ", "").equals("") && medicationsPanel.getComponents().length != 13) {
                        
                        TelaPrincipalCliente.loadMessageLabel("  CARREGANDO MEDICAMENTOS...", medicationsPanel);
                        SwingUtilities.invokeLater(() -> {
                            
                            try {
                                MedicationsUtilities.loadMedications(MedicationsUtilities.getMedications(), medicationsPanel, mainScreen, CPFOfAuthenticatedClient, bufferedThis);
                            } catch(Exception exception) {
                                exception.printStackTrace();
                            }
                            
                        });

                    } else if(searchBox.getText().replace(" ", "").equals("") && medicationsPanel.getComponents().length == 13) {
                        
                        return;
                        
                    } else if(String.valueOf("[]").equals(findedMedications.toString())) {
                        
                        TelaPrincipalCliente.loadMessageLabel("MEDICAMENTO NÃO ENCONTRADO !!!", medicationsPanel);    
                        
                    } else {
                        
                        TelaPrincipalCliente.loadMessageLabel("  CARREGANDO MEDICAMENTOS...", medicationsPanel);
                        System.gc();
                        
                        SwingUtilities.invokeLater(() -> {
                            MedicationsUtilities.loadMedications(findedMedications, medicationsPanel, mainScreen, CPFOfAuthenticatedClient, bufferedThis);
                        });

                        System.gc();

                    }
                    
                } catch (final Exception exception) {
                    exception.printStackTrace();
                }
                
            } 

        });

 
        
        // CONFIGURAÇÃO DOS BOTÕES (Carrinho, Cadastrar e Logar) 
        final Font defaultFont = new Font("Helvetica", Font.BOLD, 20);

        // Mapeando font padrão para os botões para poder adicionar o Underline (TextAttribute) e Ativa-lo (Object) 
        final Map<TextAttribute, Object> defaultFontWithUnderline = (Map<TextAttribute, Object>) defaultFont.getAttributes();
        defaultFontWithUnderline.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);

        // BOTAO LOGAR 
        btnLogin = new JButton("Logar");
        btnLogin.setBounds(1000, 25, 140, 40);
        btnLogin.setContentAreaFilled(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setOpaque(false);
        btnLogin.setFont(btnLogin.getFont().deriveFont(defaultFontWithUnderline));
        btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        header.add(btnLogin);
        
        // Evento que será acionado ao clicar no botão Logar
        btnLogin.addActionListener((event) -> {
            new TelaLogin(CPFOfAuthenticatedClient, mainScreen, this);
            mainScreen.setVisible(false);
        });

        // BOTAO CADASTRAR 
        btnRegister = new JButton("Cadastrar");
        btnRegister.setBounds(1150, 25, 140, 40);
        btnRegister.setContentAreaFilled(false);
        btnRegister.setBorderPainted(false);
        btnRegister.setOpaque(false);
        btnRegister.setFont(btnRegister.getFont().deriveFont(defaultFontWithUnderline));
        btnRegister.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        header.add(btnRegister);
        
        // Evento que será acionado ao clicar no botão Cadastrar
        btnRegister.addActionListener((event) -> {
            new TelaCadastro(mainScreen, this);
            mainScreen.dispose();
        });
        
        // BOTAO CARRINHO 
        final JButton btnCart = new JButton("Carrinho");
        btnCart.setBounds(1300, 25, 140, 40);
        btnCart.setContentAreaFilled(false);
        btnCart.setBorderPainted(false);
        btnCart.setOpaque(false);
        btnCart.setFont(btnCart.getFont().deriveFont(defaultFontWithUnderline));
        btnCart.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        header.add(btnCart);

        // Evento que será acionado ao clicar no botão Carrinho
        btnCart.addActionListener((event) -> {
            
            final TelaCarrinho telaCarrinho = new TelaCarrinho(this.CPFOfAuthenticatedClient, mainScreen, this);
            telaCarrinho.setCPFOfAuthenticatedClient(this.CPFOfAuthenticatedClient);
            
            if(this.CPFOfAuthenticatedClient != null) telaCarrinho.isLogged();
            
            mainScreen.dispose();

        });
        
        // Adicionando cabeçalho e painel dos medicamentos no container principal
        mainContainer.add(header);
        mainContainer.add(medicationsPanel);

        // Carregar Medicamentos ao Carregar a Tela Principal 
        try {

            System.gc();
            MedicationsUtilities.loadMedications(new JSONArray(MedicationsUtilities.getMedications()), medicationsPanel, mainScreen, CPFOfAuthenticatedClient, this);
            System.gc();

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

    public void isLogged() {

        //this.medicationsUtilities.setCPFOfAuthenticatedClient(this.CPFOfAuthenticatedClient);
        this.header.remove(this.btnLogin);
        this.header.remove(this.btnRegister);
        
        this.welcomeLabel = new JLabel();                
        this.welcomeLabel.setBounds(1000, 20, 300, 50);
        this.welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));

        try { 

            final HttpRequest request = HttpRequest.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .header("Content-Type", "application/json")
                .uri(URI.create("http://localhost:8080/user/findByCPF?CPF=" + this.CPFOfAuthenticatedClient))
                .GET()
                .build();
            
            final HttpClient httpClient = HttpClient.newHttpClient(); 
            final HttpResponse<String> clientHttpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            final JSONObject objectBodyClient = new JSONObject(clientHttpResponse.body()); 
            this.welcomeLabel.setText("Bem Vindo, " + objectBodyClient.getString("nome"));

        } catch(final Exception exception) { exception.printStackTrace(); }
        
        this.header.add(this.welcomeLabel);
         
    }
    
    /**
     * Load the message passed in {@code messageToLoad} into {@code defaultContainer}
     * @param messageToLoad
     * @param defaultContainer
     * @return JPanel inputed into {@code defaultContainer} 
     */
    public static void loadMessageLabel(final String messageToLoad, final JPanel defaultContainer) {
        
        // Painel para exibição da mensagem 
        final JPanel exhibition = new JPanel(new FlowLayout(FlowLayout.LEFT, 40, 40));
        exhibition.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        exhibition.setPreferredSize(new Dimension(1500, 660));
         
        // Mensagem para exibir
        final JLabel message = new JLabel(messageToLoad);
        message.setBorder(BorderFactory.createEmptyBorder(240, 370, 10, 10));
        message.setFont(new Font("Helvetica", Font.BOLD, 35));
        exhibition.add(message);

        // Carrega o painel de exibição da mensagem no container padrão 
        defaultContainer.removeAll();
        defaultContainer.add(exhibition);
        defaultContainer.revalidate();
        defaultContainer.repaint();
         

    }

    public void setCPFOfAuthenticatedClient(final String CPFOfAuthenticatedClient) {
        this.CPFOfAuthenticatedClient = CPFOfAuthenticatedClient;
    }

    public String getCPFOfAuthenticatedClient() {
        return this.CPFOfAuthenticatedClient;
    }
    
}
