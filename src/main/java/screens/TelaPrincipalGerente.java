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
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import org.json.JSONArray;
import org.json.JSONObject;

import utils.CPFUtilities;

public class TelaPrincipalGerente {
    
    JScrollPane scrollPane;

    public TelaPrincipalGerente(final String CPFOfAuthenticatedUser, JFrame jFrameTelaPrincipal, TelaPrincipal telaPrincipal) {
        // Instancia das cores utilizadas na tela
        final Color titleColor = new Color(1, 0, 127); // Variação de Azul
        final Color backgroundColor = new Color (207, 206, 206); // Variação de Cinza
        
        // TELA 
        final JFrame mainScreen = new JFrame("Gerenciamento");
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
            new TelaLogin(null, jFrameTelaPrincipal, telaPrincipal);

        }); 
        
        Font labelFont = new Font("Helvetica", Font.BOLD, 25);
        Font inputFont = new Font("Helvetica", Font.PLAIN, 20); 

        JLabel cpfLabel = new JLabel("CPF: ");
        cpfLabel.setFont(labelFont);
        cpfLabel.setBounds(100, 150, 70, 40);
        mainContainer.add(cpfLabel);
        
        JTextField cpfInput = new JTextField();
        cpfInput.setFont(inputFont);
        cpfInput.setBounds(170, 155, 200, 35);
        mainContainer.add(cpfInput);

        JLabel nameLabel = new JLabel("Nome: ");
        nameLabel.setFont(labelFont);
        nameLabel.setBounds(430, 155, 100, 35);
        mainContainer.add(nameLabel);

        JTextField nameInput = new JTextField();
        nameInput.setFont(inputFont);
        nameInput.setBounds(535, 155, 200, 35);
        mainContainer.add(nameInput);

        JLabel passwordLabel = new JLabel("Senha: ");
        passwordLabel.setBounds(800, 155, 100, 35);
        passwordLabel.setFont(labelFont);
        mainContainer.add(passwordLabel);

        JTextField passwordInput = new JTextField();
        passwordInput.setFont(inputFont);
        passwordInput.setBounds(900, 155, 180, 35);
        mainContainer.add(passwordInput);

        JLabel telephoneLabel = new JLabel("Telefone: ");
        telephoneLabel.setFont(labelFont);
        telephoneLabel.setBounds(35, 220, 150, 35);
        mainContainer.add(telephoneLabel);

        JTextField telephoneInput = new JTextField();
        telephoneInput.setFont(inputFont);
        telephoneInput.setBounds(170, 220, 200, 35);
        mainContainer.add(telephoneInput);

        JLabel addressLabel = new JLabel("Endereço: ");
        addressLabel.setFont(labelFont);
        addressLabel.setBounds(390, 220, 150, 35);
        mainContainer.add(addressLabel);
        
        JTextField addressInput = new JTextField();
        addressInput.setFont(inputFont);
        addressInput.setBounds(535, 220, 200, 35);
        mainContainer.add(addressInput);

        JLabel funcitonLabel = new JLabel("Cargo: ");
        funcitonLabel.setFont(labelFont);
        funcitonLabel.setBounds(800, 220, 100, 35);
        mainContainer.add(funcitonLabel);

        String[] options = {"Farmaceutico", "Almoxerife"};
        JComboBox<String> employeeTypeInput = new JComboBox<String>(options);
        employeeTypeInput.setBounds(900, 220, 180, 40);
        employeeTypeInput.setFont(inputFont);
        mainContainer.add(employeeTypeInput);

        mainContainer.add(header);
        scrollPane = loadEmployeeTable();

        JButton sendButton = new JButton("Cadastrar Funcionário");
        sendButton.setBounds(1140, 185, 270, 40);
        sendButton.setFont(new Font("Helvetica", Font.BOLD, 20));
        mainContainer.add(sendButton);

        sendButton.addActionListener((event) -> {
            
            try {
                
                cpfInput.setText(CPFUtilities.format(cpfInput.getText()));
                
                if(!CPFUtilities.validate(cpfInput.getText())) {
                       
                    JOptionPane.showMessageDialog(mainScreen, "Insira um CPF válido...", "Erro", JOptionPane.ERROR_MESSAGE);
                    
                } else {
                    
                    final String bodyPublisher = """
                    {
                        "cpf": "%s",
                        "nome": "%s",
                        "senha": "%s",
                        "telefone": "%s",
                        "endereco": "%s",
                        "tipoDeUsuario": "%s"
                    }
                    """.formatted(cpfInput.getText(), nameInput.getText(), passwordInput.getText(), telephoneInput.getText(), addressInput.getText(), employeeTypeInput.getSelectedItem());
                    
                    final HttpRequest request = HttpRequest.newBuilder()
                        .version(HttpClient.Version.HTTP_2)
                        .header("Content-Type", "application/json")
                        .uri(URI.create("http://localhost:8080/user/newEmployee"))
                        .POST(HttpRequest.BodyPublishers.ofString(bodyPublisher))
                        .build();
                    
                    final HttpClient httpClient = HttpClient.newHttpClient();
                    
                    final HttpResponse<String> clientHttpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                    
                    if(clientHttpResponse.statusCode() == 302) {
                        
                        mainContainer.remove(scrollPane);
                        scrollPane = loadEmployeeTable();
                        mainContainer.add(scrollPane);
                        
                        cpfInput.setText("");
                        nameInput.setText("");
                        passwordInput.setText("");
                        telephoneInput.setText("");
                        addressInput.setText("");

                        JOptionPane.showMessageDialog(mainScreen, "Funcionário cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        


                    }

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

    private static JScrollPane loadEmployeeTable() {
        
        final List<Object> employeeList = new ArrayList<>();

        for (int i = 0; i < getEmployees().length(); i++) {
            
            final JSONObject employee = getEmployees().getJSONObject(i);

            final String cpf = employee.getString("cpf");
            final String name = employee.getString("nome");
            final String password = employee.getString("senha");
            final String telephone = employee.getString("telefone");
            final String address = employee.getString("endereco");
            final String typeOfEmployee = employee.getString("tipoDeUsuario"); 
            
            employeeList.add(new Object[]{cpf, name, password, telephone, address, typeOfEmployee, "Remover"});

        }

        final Object[][] dados = employeeList.toArray(new Object[0][]);

        final String[] colunas = {"CPF", "Nome", "Senha", "Telefone", "Endereço", "Cargo", "Remover"};

        final DefaultTableModel modelo = new DefaultTableModel(dados, colunas) {
            @Override
            public boolean isCellEditable(final int row, final int column) {
                return column == 0 || column == 1 || column == 2 || column == 3 || column == 4 || column == 5 || column == 6; 
            }
        };

        final JTable tabela = new JTable(modelo);
        tabela.setFont(new Font("Helvetica", Font.BOLD, 16));
        tabela.setRowHeight(35);

        final TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
        tabela.setRowSorter(sorter);
        sorter.toggleSortOrder(0); 
        sorter.toggleSortOrder(1); 
        sorter.toggleSortOrder(2); 
        sorter.toggleSortOrder(3);
        sorter.toggleSortOrder(4);

        // Configura a ordenação inicial pela coluna "ID" (índice 0)
        sorter.setSortKeys(List.of(new RowSorter.SortKey(1, SortOrder.ASCENDING)));

        // Configura a largura das colunas
        
        tabela.getColumn("CPF").setPreferredWidth(200);
        tabela.getColumn("CPF").setMaxWidth(200);

        tabela.getColumn("Nome").setPreferredWidth(240);
        tabela.getColumn("Nome").setMaxWidth(240);

        tabela.getColumn("Senha").setPreferredWidth(140);
        tabela.getColumn("Senha").setMaxWidth(140);

        tabela.getColumn("Telefone").setPreferredWidth(200);
        tabela.getColumn("Telefone").setMaxWidth(200);
        
        tabela.getColumn("Cargo").setPreferredWidth(160);
        tabela.getColumn("Cargo").setMaxWidth(160);

        tabela.getColumn("Remover").setPreferredWidth(140);
        tabela.getColumn("Remover").setMaxWidth(140);

        // Define o renderizador e o editor para a coluna "Ação" (botão)
        tabela.getColumn("Remover").setCellRenderer(new ButtonRenderer());
        tabela.getColumn("Remover").setCellEditor(new ButtonEditor(new JCheckBox(), tabela));

        // Adiciona a tabela em um JScrollPane para exibir o cabeçalho
        final JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBounds(35, 300, 1400, 400);    

        tabela.setBounds(35, 300, 1400, 400);

        final DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER); // centraliza horizontalmente
        centerRenderer.setVerticalAlignment(SwingConstants.CENTER);   // centraliza verticalmente
        
        // Aplica o renderizador para todas as colunas
        for (int i = 0; i < tabela.getColumnCount(); i++) {
            tabela.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        return scrollPane;

    }

    static class ButtonRenderer extends JButton implements TableCellRenderer {
        
        public ButtonRenderer() {
            setText(" Remover ");
        }
        
        public Component getTableCellRendererComponent(final JTable table, final Object value,
            final boolean isSelected, final boolean hasFocus, final int row, final int column) {
            return this;
        }
        
    }

    static class ButtonEditor extends DefaultCellEditor {

        protected JButton button;
        private boolean isPushed;
        private final JTable tabelaRef;

        public ButtonEditor(final JCheckBox checkBox, final JTable tabela) {
            
            super(checkBox);
            this.tabelaRef = tabela;
            
            button = new JButton("Remover");
            button.setOpaque(true);
            
            button.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(final java.awt.event.ActionEvent e) {
                    fireEditingStopped();
                }
            });
        
        }

        public Component getTableCellEditorComponent(final JTable table, final Object value,
            
            final boolean isSelected, final int row, final int column) {
            isPushed = true;
            return button;
            
        }

        public Object getCellEditorValue() {
            
            if (isPushed) {
                
                // Remove a linha selecionada ao clicar no botão
                final int selectedRow = tabelaRef.getSelectedRow();
                
                if (selectedRow != -1) {
                    final String cpf = (String) tabelaRef.getValueAt(selectedRow, 0);

                    try {
                        
                        final HttpRequest request = HttpRequest.newBuilder()
                            .version(HttpClient.Version.HTTP_2)
                            .header("Content-Type", "application/json")
                            .uri(URI.create("http://localhost:8080/user/deleteUser/%s".formatted(cpf)))
                            .DELETE()
                            .build();
                        
                        final HttpClient httpClient = HttpClient.newHttpClient();
                        httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                    } catch(final Exception exception) {
                        exception.printStackTrace();
                    }

                    ((DefaultTableModel) tabelaRef.getModel()).removeRow(selectedRow);
                    JOptionPane.showMessageDialog(tabelaRef, "Funcionário removido com sucesso!", "Removido!", JOptionPane.INFORMATION_MESSAGE);
                }
                
            }
            
            isPushed = false;
            return "Remover";
            
        }

    }

    private static JSONArray getEmployees() {

        try {
            
            final HttpRequest request = HttpRequest.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .header("Content-Type", "application/json")
                .uri(URI.create("http://localhost:8080/user/findEmployees"))
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
