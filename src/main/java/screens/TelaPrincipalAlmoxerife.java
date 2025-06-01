package screens;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.font.TextAttribute;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import org.json.JSONArray;
import org.json.JSONObject;

public class TelaPrincipalAlmoxerife {

    JScrollPane scrollPane;

    public TelaPrincipalAlmoxerife(final String CPFOfAuthenticatedUser, JFrame jFramePrincipalCliente, TelaPrincipal telaPrincipal) {

        // Instancia das cores utilizadas na tela
        final Color titleColor = new Color(1, 0, 127); // Variação de Azul
        final Color backgroundColor = new Color (207, 206, 206); // Variação de Cinza
        
        // TELA 
        final JFrame mainScreen = new JFrame("Estoque de Medicamentos");
        mainScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainScreen.setSize(1500, 800);
        mainScreen.setLocationRelativeTo(null);

        // IMAGEM DO JFRAME (CANTO SUPERIOR ESQUERDO)
        final Image imagemLogo = new ImageIcon("./img/logoComFundo.png").getImage();
        mainScreen.setIconImage(imagemLogo);

        // CONTAINER PRINCIPAL
        final JPanel mainContainer = new JPanel(); 
        mainContainer.setLayout(null);
        mainContainer.setBackground(backgroundColor);
        
        // CABECALHO (header)
        final JPanel header = new JPanel();
        header.setLayout(null);
        header.setBounds(0, 0, 1500, 100);
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
        
        String nameOfUser = null;

        try {

            final HttpRequest request = HttpRequest.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .header("Content-Type", "application/json")
                .uri(URI.create("http://localhost:8080/user/findName/%s".formatted(CPFOfAuthenticatedUser)))
                .GET()
                .build();
            
            final HttpClient httpClient = HttpClient.newHttpClient(); 
            final HttpResponse<String> clientHttpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            nameOfUser = clientHttpResponse.body();

        } catch(final Exception exception) {
            exception.printStackTrace();
        }

        final JLabel welcomeLabel = new JLabel("Bem Vindo(a) de volta, " + nameOfUser);
        welcomeLabel.setBounds(700, 20, 700, 50);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));

        header.add(welcomeLabel);

        //BOTAO LOGOUT 
        final JButton btnLogout = new JButton("Logout");
        btnLogout.setBounds(1250, 25, 140, 40);
        btnLogout.setContentAreaFilled(false);
        btnLogout.setBorderPainted(false);
        btnLogout.setOpaque(false);
        btnLogout.setFont(btnLogout.getFont().deriveFont(defaultFontWithUnderline));
        btnLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        header.add(btnLogout);

        btnLogout.addActionListener((event) -> {

            mainScreen.dispose();
            new TelaLogin(null, jFramePrincipalCliente, telaPrincipal);

        });

        mainContainer.add(header);

        scrollPane = loadMedicationsTable();
         
        Font labelFont = new Font("Helvetica", Font.BOLD, 25);
        Font inputFont = new Font("Helvetica", Font.PLAIN, 20); 

        JLabel nameLabel = new JLabel("Nome: ");
        nameLabel.setFont(labelFont);
        nameLabel.setBounds(70, 150, 100, 35);
        mainContainer.add(nameLabel);
        
        JTextField nameInput = new JTextField();
        nameInput.setFont(inputFont);
        nameInput.setBounds(170, 155, 200, 35);
        mainContainer.add(nameInput);

        JLabel dosageLabel = new JLabel("Dosagem: ");
        dosageLabel.setFont(labelFont);
        dosageLabel.setBounds(450, 155, 150, 35);
        mainContainer.add(dosageLabel);

        JTextField dosageInput = new JTextField();
        dosageInput.setFont(inputFont);
        dosageInput.setBounds(590, 155, 200, 35);
        mainContainer.add(dosageInput);

        JLabel urlLabel = new JLabel("URL da Imagem: ");
        urlLabel.setFont(labelFont);
        urlLabel.setBounds(900, 155, 250, 35);
        mainContainer.add(urlLabel);

        JTextField urlInput = new JTextField();
        urlInput.setFont(inputFont);
        urlInput.setBounds(1140, 155, 260, 35);
        mainContainer.add(urlInput);

        JLabel quantityLabel = new JLabel("Quantidade: ");
        quantityLabel.setBounds(70, 220, 170, 35);
        quantityLabel.setFont(labelFont);
        mainContainer.add(quantityLabel);

        JTextField quantityInput = new JTextField();
        quantityInput.setFont(inputFont);
        quantityInput.setBounds(250, 220, 120, 35);
        mainContainer.add(quantityInput);

        JLabel descriptionLabel = new JLabel("Descrição: ");
        descriptionLabel.setBounds(450, 220, 140, 35);
        descriptionLabel.setFont(labelFont);
        mainContainer.add(descriptionLabel);

        JTextField descriptionInput = new JTextField();
        descriptionInput.setFont(inputFont);
        descriptionInput.setBounds(590, 220, 200, 35);
        mainContainer.add(descriptionInput);

        JLabel revenueLabel = new JLabel("Precisa de Receita Receita ? ");
        revenueLabel.setFont(labelFont);
        revenueLabel.setBounds(900, 220, 400, 35);
        mainContainer.add(revenueLabel);

        String[] options = {"Não", "Sim"};
        JComboBox<String> revenueInput = new JComboBox<String>(options);
        revenueInput.setBounds(1300, 220, 100, 40);
        revenueInput.setFont(inputFont);
        mainContainer.add(revenueInput);

        JLabel priceLabel = new JLabel("Preço Unitário: ");
        priceLabel.setFont(labelFont);
        priceLabel.setBounds(70, 285, 300, 35);
        mainContainer.add(priceLabel);
        
        JTextField priceInput = new JTextField();
        priceInput.setFont(inputFont);
        priceInput.setBounds(280, 285, 90, 35);
        mainContainer.add(priceInput);

        mainContainer.add(header);

        JButton sendButton = new JButton("Cadastrar Medicamento");
        sendButton.setBounds(1085, 330, 350, 40);
        sendButton.setFont(new Font("Helvetica", Font.BOLD, 20));
        mainContainer.add(sendButton);

        sendButton.addActionListener((event) -> {
            
            try {
                
                final boolean revenue = ((revenueInput.getSelectedItem().equals("Sim") ? true : false));

                final String bodyPublisher = """
                {
                    "nome": "%s",
                    "dosagem": "%s",
                    "descricao": "%s",
                    "imagemDoMedicamento": "%s",
                    "preco": "%s",
                    "quantidadeDisponivel": "%s",
                    "precisaDeReceita": "%s"
                }
                """.formatted(nameInput.getText(), dosageInput.getText(), descriptionInput.getText(), urlInput.getText(), priceInput.getText(), quantityInput.getText(), revenue);
                    
                final HttpRequest request = HttpRequest.newBuilder()
                    .version(HttpClient.Version.HTTP_2)
                    .header("Content-Type", "application/json")
                    .uri(URI.create("http://localhost:8080/medications/newMedication"))
                    .POST(HttpRequest.BodyPublishers.ofString(bodyPublisher))
                    .build();
                
                final HttpClient httpClient = HttpClient.newHttpClient();
                
                final HttpResponse<String> clientHttpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                 
                if(clientHttpResponse.statusCode() == 202) {
                    
                    mainContainer.remove(scrollPane);
                    scrollPane = loadMedicationsTable();
                    mainContainer.add(scrollPane);
                    
                    nameInput.setText("");
                    dosageInput.setText("");
                    descriptionInput.setText("");
                    urlInput.setText("");
                    quantityInput.setText("");
                    priceInput.setText("");

                    JOptionPane.showMessageDialog(mainScreen, "Medicamento cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    
                } else {

                    JOptionPane.showMessageDialog(mainScreen, "Erro ao cadastrar Medicamento, tente novamente...!", "Erro", JOptionPane.ERROR_MESSAGE);

                }

            } catch(Exception exception) {
                exception.printStackTrace();
            }
             
        });

        mainContainer.add(scrollPane);

        // Configuranções ScrollPane (Rolagem da Tela) 
        final JScrollPane jScrollPane = new JScrollPane(mainContainer);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Desabilitar Scrollbar Horizontal
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Habilitar Scrollbar Vertical 
        jScrollPane.getVerticalScrollBar().setUnitIncrement(16); // Scroll mais suave (unidade de incremento)
        
        mainScreen.add(jScrollPane);
        mainScreen.setVisible(true);

    }

    private JScrollPane loadMedicationsTable() {

        final List<Object> medicationsList = new ArrayList<>();

        for (int i = 0; i < getMedications().length(); i++) {
            
            final JSONObject medication = getMedications().getJSONObject(i);

            final int id = medication.getInt("id");
            final String name = medication.getString("nome");
            final String dosage = medication.getString("dosagem");
            final String description = medication.getString("descricao");
            
            final double price = medication.getDouble("preco");
            String formattedPrice = String.valueOf(price).replace(".", ",");
            formattedPrice = formattedPrice.concat((formattedPrice.charAt(formattedPrice.length()-2) == ',') ? "0" : "");
            
            final int quantity = medication.getInt("quantidadeDisponivel");
            final String receita = ((medication.getBoolean("precisaDeReceita")) ? "Sim" :  "Não");
            
            medicationsList.add(new Object[]{id, name, dosage, description, formattedPrice, quantity, receita});

        }

        final Object[][] dados = medicationsList.toArray(new Object[0][]);

        final String[] colunas = {"ID", "Nome", "Dosagem", "Descricao", "Preço", "Quantidade", "Receita"};

        final DefaultTableModel modelo = new DefaultTableModel(dados, colunas) {
            
            @Override
            public boolean isCellEditable(final int row, final int column) {
                return column == 5;
            }

        };

        modelo.addTableModelListener(e -> {
            
            // Verifica se foi uma alteração de célula (não adição ou remoção de linha)
            if (e.getType() == TableModelEvent.UPDATE) {
                
                final int row = e.getFirstRow();
                final int column = e.getColumn();
                
                if (column == 5) {
                    
                    // Obtenha o modelo para os dados corretos
                    final Object idValue = modelo.getValueAt(row, 0); // Supondo que coluna 0 seja ID
                    final Object novaQuantidade = modelo.getValueAt(row, column);
                    
                    try {
                        
                        final HttpRequest request = HttpRequest.newBuilder()
                            .version(HttpClient.Version.HTTP_2)
                            .header("Content-Type", "application/json")
                            .uri(URI.create("http://localhost:8080/medications/updateQuantity/%s/%s".formatted(idValue, novaQuantidade)))
                            .PUT(HttpRequest.BodyPublishers.ofString(""))
                            .build();
                            
                        final HttpClient httpClient = HttpClient.newHttpClient(); 
                        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                        
                    } catch(final Exception exception) {
                        exception.printStackTrace();
                    }
                    
                }

            }

        });

        final JTable tabela = new JTable(modelo);
        tabela.setFont(new Font("Helvetica", Font.BOLD, 16));
        tabela.setRowHeight(35);

        // Configura o TableRowSorter
        final TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
        tabela.setRowSorter(sorter);
        
        tabela.getColumn("ID").setPreferredWidth(80);
        tabela.getColumn("ID").setMaxWidth(80);

        tabela.getColumn("Nome").setPreferredWidth(340);
        tabela.getColumn("Nome").setMaxWidth(340);

        tabela.getColumn("Descricao").setPreferredWidth(280);
        tabela.getColumn("Descricao").setMaxWidth(280);

        tabela.getColumn("Preço").setPreferredWidth(140);
        tabela.getColumn("Preço").setMaxWidth(140);

        tabela.getColumn("Quantidade").setPreferredWidth(140);
        tabela.getColumn("Quantidade").setMaxWidth(140);    

        tabela.getColumn("Receita").setPreferredWidth(140);
        tabela.getColumn("Receita").setMaxWidth(140);

        // Adiciona a tabela em um JScrollPane para exibir o cabeçalho
        final JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBounds(35, 400, 1400, 300);

        tabela.setBounds(35, 400, 1400, 300);

        final DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER); // centraliza horizontalmente
        centerRenderer.setVerticalAlignment(SwingConstants.CENTER);   // centraliza verticalmente
        
        // Aplica o renderizador para todas as colunas
        for (int i = 0; i < tabela.getColumnCount(); i++) {
            
            tabela.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            
        }
        
        return scrollPane;

    }

    private JSONArray getMedications() {

        try {
            
            final HttpRequest request = HttpRequest.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .header("Content-Type", "application/json")
                .uri(URI.create("http://localhost:8080/medications"))
                .GET()
                .build();
            
            final HttpClient httpClient = HttpClient.newHttpClient(); 
            final HttpResponse<String> clientHttpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return new JSONArray(clientHttpResponse.body());

        } catch(final Exception exception) {
            exception.printStackTrace();
        }

        return new JSONArray();

    }

}
