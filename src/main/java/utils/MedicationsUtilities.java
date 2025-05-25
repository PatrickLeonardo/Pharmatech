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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.json.JSONArray;
import org.json.JSONObject;

import screens.TelaPrincipalCliente;

public class MedicationsUtilities {

    /**
     * Pull all API Medications
     * @return JSONArray object with all API medications
     * @throws IOException
     * @throws InterruptedException
     */
    public static JSONArray getMedications() throws IOException, InterruptedException {

        final HttpRequest request = HttpRequest.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .uri(URI.create("http://localhost:8080/medications"))
            .GET()
            .build();

        final HttpClient httpClient = HttpClient.newHttpClient();

        final HttpResponse<String> clientHttpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        //this.responseBodyMedications = clientHttpResponse.body();
        return new JSONArray(clientHttpResponse.body());

    }

    public static JSONArray findMedications(String medicationName) throws InterruptedException, IOException {
         
        // Formatar nome do Medicamento pesquisado (Substitui o espaço em branco por %20) 
        medicationName = medicationName.strip().replace(" ", "%20");
        
        // Se medicamento for diferente de "" e se for diferente do texto de exibição padrão da caixa de pesquisa  
        if (!medicationName.equals("") && !medicationName.equals("Procure%20por%20um%20Medicamento...")) {
             
            final HttpRequest request = HttpRequest.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .uri(URI.create("http://localhost:8080/medications/findByNome?nome=" + medicationName))
                .GET()
                .build();

            final HttpClient httpClient = HttpClient.newHttpClient();
            final HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            return new JSONArray(httpResponse.body());
            
        } else return new JSONArray("[]");

    }

    /**
     * Load all Medications passed in {@code medicationsArray} into {@code defaultContainer}
     * @param medicationsArray
     * @param defaultContainer
     */
    public static void loadMedications(final JSONArray medicationsArray, final JPanel defaultContainer, final JFrame screenJFrame, String CPFOfAuthenticatedClient, TelaPrincipalCliente telaPrincipalCliente) {
        
        try {
            
            // JPanel para carregar a Linha com 4 Medicamentos na Horizontal
            JPanel linePanel = null;

            // Contador para cada iteração 
            int columnCounter = 0;

            // Remove os Elementos anteriores (caso haver) 
            defaultContainer.removeAll();
             
            // Para cada mediacamento 
            for(int counter = 0; counter <= medicationsArray.length()-1; counter++) {

                // Se contador estiver zerado, adiciona uma nova Linha Horizontal para carregar mais 4 Elementos
                if (columnCounter == 0) { // Se for o primeiro elemento da linha
                    linePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 40, 40)); // Cria um novo JPanel com FlowLayout para alinhar elementos à esquerda e espaçamento
                    linePanel.setAlignmentX(JPanel.LEFT_ALIGNMENT); // Alinha o painel à esquerda

                    if(medicationsArray.length() <= 4) linePanel.setPreferredSize(new Dimension(1500, 660)); // Se houver até 4 medicamentos, define tamanho fixo para o painel

                    defaultContainer.add(linePanel); // Adiciona o painel da linha ao container principal
                }

                final JPanel elementPanel = new JPanel(); // Cria um novo JPanel para o medicamento

                // PAINEL DE CADA ELEMENTO
                elementPanel.setLayout(new BoxLayout(elementPanel, BoxLayout.Y_AXIS)); // Define layout vertical para os componentes do medicamento
                elementPanel.setBorder(BorderFactory.createLineBorder(Color.black)); // Adiciona borda preta ao redor do painel
                elementPanel.setBackground(Color.WHITE); // Define fundo branco
                elementPanel.setPreferredSize(new Dimension(320, 500)); // Define tamanho preferido do painel
                elementPanel.setMaximumSize(new Dimension(320, 500)); // Define tamanho máximo do painel
                elementPanel.setMinimumSize(new Dimension(320, 500)); // Define tamanho mínimo do painel
                elementPanel.setAlignmentX(JPanel.LEFT_ALIGNMENT); // Alinha o painel à esquerda

                final JSONObject JSONMedication = medicationsArray.getJSONObject(counter);
                 
                // IMAGEM DO MEDICAMENTO
                final URL medicationImageUrl = new URI(JSONMedication.getString("imagemDoMedicamento")).toURL();
                ImageIcon resizedImage = new ImageIcon(medicationImageUrl);
                
                resizedImage = new ImageIcon(
                    resizedImage.getImage().getScaledInstance(290, 250, Image.SCALE_SMOOTH)
                );
                
                final JLabel imageLabel = new JLabel(resizedImage);
                imageLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 10));

                if(JSONMedication.getBoolean("precisaDeReceita")) {
                   imageLabel.setToolTipText("O Medicamento "+ JSONMedication.getString("nome") + " precisa de receita para ser retirado presencialmente!");
                }

                elementPanel.add(imageLabel);
                
                // NOME
                final JLabel nameLabel = new JLabel(JSONMedication.getString("nome"));
                nameLabel.setFont(new Font("Helvetica", Font.BOLD, 22));
                nameLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 10));
                elementPanel.add(nameLabel);

                // DESCRIÇÃO 
                final JLabel descriptionLabel = new JLabel(JSONMedication.getString("descricao"));
                descriptionLabel.setFont(new Font("Helvetica", Font.PLAIN, 18));
                descriptionLabel.setBorder(BorderFactory.createEmptyBorder(5, 20, 0, 10));
                elementPanel.add(descriptionLabel);

                // DOSAGEM 
                final JLabel dosageLabel = new JLabel(JSONMedication.getString("dosagem"));
                dosageLabel.setFont(new Font("Helvetica", Font.PLAIN, 18));
                dosageLabel.setBorder(BorderFactory.createEmptyBorder(5, 20, 0, 10));
                elementPanel.add(dosageLabel);

                // PREÇO 
                
                // Trocar ponto por virgula no texto do preço 
                String priceText = String.valueOf(JSONMedication.getDouble("preco")).replace(".", ",");
                
                // Adicionar um 0 no final do preço caso não tenha duas casas decimais depois da virgula 
                if (Character.valueOf(',').equals(priceText.charAt(priceText.length()-2))) {
                    priceText = priceText.concat("0");
                }

                final JLabel priceLabel = new JLabel("R$ " + priceText);
                priceLabel.setFont(new Font("Helvetica", Font.BOLD, 18));
                priceLabel.setBorder(BorderFactory.createEmptyBorder(5, 20, 15, 10));
                elementPanel.add(priceLabel);

                // BOTÃO ADICIONAR AO CARRINHO
                final JButton btnReservation = new JButton("+ ADICIONAR AO CARRINHO");
                elementPanel.add(btnReservation);

                btnReservation.addActionListener((event) -> {

                    try {

                        if(CartUtilities.addMedicationOnCart(JSONMedication.getInt("id"), telaPrincipalCliente.getCPFOfAuthenticatedClient())) {
                            JOptionPane.showMessageDialog(screenJFrame, "Medicamento adicionado ao Carrinho !!!");
                        } else {
                            JOptionPane.showMessageDialog(screenJFrame, "Faça login para adicionar itens ao carrinho !!!", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                        
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }

                });

                linePanel.add(elementPanel);
                
                columnCounter++;

                // Se Linha do painel tiver 4 Elementos, zera o contador
                if (columnCounter == 4) columnCounter = 0;
                
            }

            defaultContainer.revalidate();
            defaultContainer.repaint();

        } catch(final Exception exception) {
            exception.printStackTrace();
        }

    }

}
