package screens;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.json.JSONArray;
import org.json.JSONObject;

public class TelaPrincipalCliente {

    public TelaPrincipalCliente() {

        final Color azulPharmatech = new Color(1, 0, 127);
        final Color cinzaFundo = new Color (207, 206, 206);

        // TELA  
        final JFrame tela = new JFrame("Tela Principal");
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setSize(1500, 800);
        tela.setLocationRelativeTo(null);

        // CONTAINER PRINCIPAL
        final JPanel container = new JPanel(); 
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(cinzaFundo);
        
        // CABECALHO
        final JPanel cabecalho = new JPanel();
        cabecalho.setLayout(null);
        cabecalho.setPreferredSize(new Dimension(1500, 100));
        cabecalho.setBackground(cinzaFundo);
        
        // LOGO
        ImageIcon logo = new ImageIcon("C:\\Users\\oushe\\Downloads\\simboloFarmacia.jpeg");
        final Image imagemRedimensionada = logo.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        logo = new ImageIcon(imagemRedimensionada);
        final JLabel labelLogo = new JLabel(logo);
        labelLogo.setBounds(20, 35, 30, 30);
        cabecalho.add(labelLogo);

        // TITULO
        final JLabel msgPharmatech = new JLabel("Pharmatech");
        msgPharmatech.setBounds(50, 25, 500, 50);
        msgPharmatech.setFont(new Font("Arial", Font.PLAIN, 30));
        msgPharmatech.setForeground(azulPharmatech);
        cabecalho.add(msgPharmatech);

        // BARRA DE PESQUISA
        final JTextField barraPesquisa = new JTextField("");
        barraPesquisa.setBounds(250, 25, 500, 50);
        barraPesquisa.setFont(new Font("Arial", Font.PLAIN, 40));
        cabecalho.add(barraPesquisa);

        // BOTAO CARRINHP
        final JButton botaoCarrinho = new JButton("Carrinho");
        botaoCarrinho.setBounds(1300, 25, 150, 50);
        cabecalho.add(botaoCarrinho);

        // BOTAO CADASTRAR
        final JButton botaoCadastrar = new JButton("Cadastrar");
        botaoCadastrar.setBounds(1100, 25, 150, 50);
        cabecalho.add(botaoCadastrar);

        // BOTAO LOGAR
        final JButton botaoLogar = new JButton("Logar");
        botaoLogar.setBounds(900, 25, 150, 50);
        cabecalho.add(botaoLogar);
        
        container.add(cabecalho);
        
        // PAINEL EXIBIÇÃO MEDICAMENTOS
        final JPanel painelMedicamentos = new JPanel();
        painelMedicamentos.setLayout(new BoxLayout(painelMedicamentos, BoxLayout.Y_AXIS));
        painelMedicamentos.setBackground(cinzaFundo);
        painelMedicamentos.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        container.add(painelMedicamentos);

        botaoLogar.addActionListener((event) -> {
            new TelaLogin();
            tela.dispose();
        });

        botaoCadastrar.addActionListener((event) -> {
            new TelaCadastro();
            tela.dispose();
        });
        botaoCarrinho.addActionListener((event) -> {
            new TelaCarrinho();
            tela.dispose();
        });

        loadMedications(painelMedicamentos);

        final JScrollPane jScrollPane = new JScrollPane(container);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.getVerticalScrollBar().setUnitIncrement(16); // scroll mais suave

        tela.add(jScrollPane);
        tela.setVisible(true);

    }

    private String getMedicamentos() throws IOException, InterruptedException {

        final HttpRequest request = HttpRequest.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .uri(URI.create("http://localhost:8080/medications"))
            .GET()
            .build();

        final HttpClient httpClient = HttpClient.newHttpClient();

        final HttpResponse<String> clientHttpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return clientHttpResponse.body();

    }

    private void loadMedications(final JPanel defaultContainer) {
        
        try {
            
            final JSONArray jsonArray = new JSONArray(getMedicamentos());

            JPanel linhaPanel = null;
            int contadorColuna = 0;
            
            for(int counter = 0; counter <= jsonArray.length()-1; counter++) {

                if (contadorColuna == 0) {
                    linhaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 40, 40));
                    linhaPanel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
                    defaultContainer.add(linhaPanel);
                }

                final JPanel jPanel = new JPanel();
                
                // PAINEL DE CADA ELEMENTO
                jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
                jPanel.setBorder(BorderFactory.createLineBorder(Color.black));
                jPanel.setBackground(Color.WHITE);
                jPanel.setPreferredSize(new Dimension(320, 500));
                jPanel.setMaximumSize(new Dimension(320, 500));
                jPanel.setMinimumSize(new Dimension(320, 500));
                jPanel.setAlignmentX(JPanel.LEFT_ALIGNMENT); 

                final JSONObject JSONMedication = jsonArray.getJSONObject(counter);
                 
                // IMAGEM DO MEDICAMENTO
                final URL urlDaImagemDoMedicamento = new URI(JSONMedication.getString("imagemDoMedicamento")).toURL();
                ImageIcon imageIcon = new ImageIcon(urlDaImagemDoMedicamento);
                
                imageIcon = new ImageIcon(
                    imageIcon.getImage().getScaledInstance(290, 250, Image.SCALE_SMOOTH)
                );
                
                JLabel imagemLabel = new JLabel(imageIcon);
                imagemLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 10));
                jPanel.add(imagemLabel);
                
                // NOME
                final JLabel labelNome = new JLabel(JSONMedication.getString("nome"));
                labelNome.setFont(new Font("Helvetica", Font.PLAIN, 25));
                labelNome.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 10));
                jPanel.add(labelNome);

                // DESCRIÇÃO 
                final JLabel labelDescricao = new JLabel(JSONMedication.getString("descricao"));
                labelDescricao.setFont(new Font("Helvetica", Font.PLAIN, 17));
                labelDescricao.setBorder(BorderFactory.createEmptyBorder(5, 20, 0, 10));
                jPanel.add(labelDescricao);

                // DOSAGEM
                final JLabel labelDosagem = new JLabel(JSONMedication.getString("dosagem"));
                labelDosagem.setFont(new Font("Helvetica", Font.PLAIN, 20));
                labelDosagem.setBorder(BorderFactory.createEmptyBorder(5, 20, 0, 10));
                jPanel.add(labelDosagem);

                // PREÇO
                String textoPreco = String.valueOf(JSONMedication.getDouble("preco"));
                textoPreco = textoPreco.replace(".", ",");

                final JLabel labelPreco = new JLabel("R$" + textoPreco);
                labelPreco.setFont(new Font("Helvetica", Font.PLAIN, 20));
                labelPreco.setBorder(BorderFactory.createEmptyBorder(5, 20, 15, 10));
                jPanel.add(labelPreco);

                // BOTÃO ADICIONAR AO CARRINHO
                final JButton botaoReserva = new JButton("+ ADICIONAR AO CARRINHO");
                jPanel.add(botaoReserva);

                linhaPanel.add(jPanel);

                contadorColuna++;
                if (contadorColuna == 4) contadorColuna = 0;
                
            }

            defaultContainer.revalidate();
            defaultContainer.repaint();

        } catch(final Exception exception) {
            exception.printStackTrace();
        }

    }

}
