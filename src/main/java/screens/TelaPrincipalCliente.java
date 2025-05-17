package screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Iterator;

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

        ImageIcon logo = new ImageIcon("C:\\Users\\oushe\\Downloads\\simboloFarmacia.jpeg");
        final Image imagemRedimensionada = logo.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        logo = new ImageIcon(imagemRedimensionada);

        final JFrame tela = new JFrame("Tela Principal");
        final JLabel labelLogo = new JLabel(logo);
        final JLabel msgPharmatech = new JLabel("Pharmatech");
        final JButton botaoCarrinho= new JButton("Carrinho");
        final JButton botaoCadastrar = new JButton("Cadastrar");
        final JButton botaoLogar = new JButton("Logar");
        final JTextField barraPesquisa = new JTextField("");
        final JPanel container = new JPanel(); 

        container.setLayout(null); 
        container.setBackground(cinzaFundo);

        labelLogo.setBounds(20, 35, 30, 30);
        container.add(labelLogo);

        msgPharmatech.setBounds(50, 25, 500, 50);
        msgPharmatech.setFont(new Font("Arial", Font.PLAIN, 30));
        msgPharmatech.setForeground(azulPharmatech);
        container.add(msgPharmatech);

        barraPesquisa.setBounds(250, 25, 500, 50);
        barraPesquisa.setFont(new Font("Arial", Font.PLAIN, 40));
        container.add(barraPesquisa);

        botaoCarrinho.setBounds(1300, 25, 150, 50);
        container.add(botaoCarrinho);

        botaoCadastrar.setBounds(1100, 25, 150, 50);
        container.add(botaoCadastrar);

        botaoLogar.setBounds(900, 25, 150, 50);
        container.add(botaoLogar);
          
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

        loadMedications(container);

        final JScrollPane jScrollPane = new JScrollPane(container);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        //tela.add(jScrollPane);
        //tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //tela.setContentPane(jScrollPane);
        //tela.pack();
        //tela.setSize(1500, 800);
        //tela.setLocationRelativeTo(null);
        //tela.setVisible(true);

        tela.add(jScrollPane);
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setSize(1500, 800);
        tela.setLocationRelativeTo(null);
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
            
            int firstLine = 0;
            int lineCounter = 1;
            int couterCopyBuffer = 0;

            for(int counter = 0; counter <= jsonArray.length()-1; counter++) {

                final JPanel jPanel = new JPanel();
                jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));

                 
                jPanel.setLayout(null);
                if (counter > 3) firstLine = 50;
                jPanel.setBounds(60 + (couterCopyBuffer * 350), (200 * lineCounter + firstLine), 500, 50);
                jPanel.setSize(300, 400);
                jPanel.setBorder(BorderFactory.createLineBorder(Color.black));

                JLabel jLabel = new JLabel();
                
                final JSONObject JSONMedication = jsonArray.getJSONObject(counter);
                
                final Iterator<String> keysOfJSONMedication = JSONMedication.keys();
                
                int i = 0;

                while (keysOfJSONMedication.hasNext()) {
                    
                    final String key = keysOfJSONMedication.next();
                    final String text = String.valueOf(JSONMedication.get(key));
                    
                    // IMAGEM DO MEDICAMENTO
                    if (String.valueOf(key).equals("imagemDoMedicamento")) {
                        
                        final URL urlDaImagemDoMedicamento = new URI(text).toURL();
                        ImageIcon imageIcon = new ImageIcon(urlDaImagemDoMedicamento);
                        
                        imageIcon = new ImageIcon(
                            imageIcon.getImage().getScaledInstance(290, 250, Image.SCALE_SMOOTH)
                        );
                        
                        jLabel = new JLabel(imageIcon);
                        jLabel.setBounds(5, 5, imageIcon.getIconWidth(), imageIcon.getIconHeight());

                    // NOME
                    } else if (String.valueOf(key).equals("nome")) {
                        
                        jLabel = new JLabel(text);
                        jLabel.setFont(new Font("Helvetica", Font.PLAIN, 30));
                        jLabel.setBounds(20, 240+(i*20), 500, 50);
                        
                    // Descrição    
                    } else if (String.valueOf(key).equals("descricao")) {
                        
                        jLabel = new JLabel(text);
                        jLabel.setFont(new Font("Helvetica", Font.PLAIN, 17));
                        jLabel.setBounds(20, 240+(i*22), 500, 50);
                        
                    // DOSAGEM
                    } else if (String.valueOf(key).equals("dosagem")) {

                        jLabel = new JLabel(text);
                        jLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
                        jLabel.setBounds(20, 240+(i*20), 500, 50);

                    }

                    jPanel.add(jLabel);
                    defaultContainer.add(jPanel);
                    
                    i++;
                    
                }

                couterCopyBuffer++;

                if ((counter + 1) % 4 == 0) {
                    lineCounter += 2; 
                    couterCopyBuffer= 0;
                }

            }

            defaultContainer.revalidate();
            defaultContainer.repaint();

        } catch(final Exception exception) {
            exception.printStackTrace();
        }

    }

}
