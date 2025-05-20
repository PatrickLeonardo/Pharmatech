package utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.json.JSONArray;
import org.json.JSONObject;

public class MedicationsUtilities {

    public static String getMedications() throws IOException, InterruptedException {

        final HttpRequest request = HttpRequest.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .uri(URI.create("http://localhost:8080/medications"))
            .GET()
            .build();

        final HttpClient httpClient = HttpClient.newHttpClient();

        final HttpResponse<String> clientHttpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return clientHttpResponse.body();

    }

    public static void loadMedications(final JPanel defaultContainer) {
        
        try {
            
            final JSONArray jsonArray = new JSONArray(getMedications());

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
                
                final JLabel imagemLabel = new JLabel(imageIcon);
                imagemLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 10));
                jPanel.add(imagemLabel);
                
                // NOME
                final JLabel labelNome = new JLabel(JSONMedication.getString("nome"));
                labelNome.setFont(new Font("Helvetica", Font.BOLD, 22));
                labelNome.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 10));
                jPanel.add(labelNome);

                // DESCRIÇÃO 
                final JLabel labelDescricao = new JLabel(JSONMedication.getString("descricao"));
                labelDescricao.setFont(new Font("Helvetica", Font.PLAIN, 18));
                labelDescricao.setBorder(BorderFactory.createEmptyBorder(5, 20, 0, 10));
                jPanel.add(labelDescricao);

                // DOSAGEM
                final JLabel labelDosagem = new JLabel(JSONMedication.getString("dosagem"));
                labelDosagem.setFont(new Font("Helvetica", Font.PLAIN, 18));
                labelDosagem.setBorder(BorderFactory.createEmptyBorder(5, 20, 0, 10));
                jPanel.add(labelDosagem);

                // PREÇO
                String textoPreco = String.valueOf(JSONMedication.getDouble("preco")).replace(".", ",");
                
                if (Character.valueOf(',').equals(textoPreco.charAt(textoPreco.length()-2))) {
                    textoPreco = textoPreco.concat("0");
                }

                final JLabel labelPreco = new JLabel("R$ " + textoPreco);
                labelPreco.setFont(new Font("Helvetica", Font.BOLD, 18));
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
