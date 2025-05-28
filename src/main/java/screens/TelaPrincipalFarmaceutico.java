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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import org.json.JSONArray;
import org.json.JSONObject;

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
        mainContainer.setLayout(null);
        mainContainer.setBackground(backgroundColor);
        
        // CABECALHO (header)
        JPanel header = new JPanel();
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
        
        mainContainer.add(header);

        List<Object> cartList = new ArrayList<>();

        for (int i = 0; i < getCarts().length()-1; i++) {
            
            JSONObject cart = getCarts().getJSONObject(i);

            String cpf = cart.getString("CPF");
            String cliente = cart.getString("Cliente");
            String medicamento = cart.getString("Medicamento");
            int quantidade = cart.getInt("Quantidade");
            
            cartList.add(new Object[]{cpf, cliente, medicamento, quantidade, "Remover"});

        }

        Object[][] dados = cartList.toArray(new Object[0][0]);

        String[] colunas = {"CPF", "Cliente", "Medicamento", "Quantidade", "Remover"};

        DefaultTableModel modelo = new DefaultTableModel(dados, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; 
            }
        };

        JTable tabela = new JTable(modelo);

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
        tabela.setRowSorter(sorter);
        sorter.toggleSortOrder(0); 
        sorter.toggleSortOrder(1); 
        sorter.toggleSortOrder(2); 
        sorter.toggleSortOrder(3);

        // Configura a largura das colunas
        tabela.getColumn("Remover").setPreferredWidth(10); 

        // Define o renderizador e o editor para a coluna "Ação" (botão)
        tabela.getColumn("Remover").setCellRenderer(new ButtonRenderer());
        tabela.getColumn("Remover").setCellEditor(new ButtonEditor(new JCheckBox(), tabela));

        // Adiciona a tabela em um JScrollPane para exibir o cabeçalho
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBounds(35, 150, 1400, 500);
        mainContainer.add(scrollPane);

        tabela.setBounds(35, 150, 1400, 500);
         
        //mainContainer.add(Box.createVerticalGlue()); // Adiciona um espaço flexível
        
        // Configuranções ScrollPane (Rolagem da Tela) 
        final JScrollPane jScrollPane = new JScrollPane(mainContainer);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Desabilitar Scrollbar Horizontal
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Habilitar Scrollbar Vertical 
        jScrollPane.getVerticalScrollBar().setUnitIncrement(16); // Scroll mais suave (unidade de incremento)
        
        mainScreen.add(jScrollPane);
        mainScreen.setVisible(true);

    }

    static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setText(" X ");
        }
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    static class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private boolean isPushed;
        private JTable tabelaRef;

        public ButtonEditor(JCheckBox checkBox, JTable tabela) {
            super(checkBox);
            this.tabelaRef = tabela;
            button = new JButton(" X ");
            button.setOpaque(true);
            button.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                // Remove a linha selecionada ao clicar no botão
                int selectedRow = tabelaRef.getSelectedRow();
                if (selectedRow != -1) {
                    ((DefaultTableModel) tabelaRef.getModel()).removeRow(selectedRow);
                    JOptionPane.showMessageDialog(tabelaRef, "Linha removida com sucesso!", "Removido!", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            isPushed = false;
            return "Ação";
        }
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
