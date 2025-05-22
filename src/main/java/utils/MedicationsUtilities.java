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

public class MedicationsUtilities {

    // Medicamentos do Corpo da Resposta
    String responseBodyMedications = null;

    // Exibição Padrão (Todos os Medicamentos carregados) 
    boolean defaultExhibition = true;

    String CPFOfAuthenticatedClient = null;

    public MedicationsUtilities(String CPFOfAuthenticatedClient) {
        this.CPFOfAuthenticatedClient = CPFOfAuthenticatedClient;
    }
    
    /**
     * Pull all API Medications
     * @return JSONArray object with all API medications
     * @throws IOException
     * @throws InterruptedException
     */
    public JSONArray getMedications() throws IOException, InterruptedException {

        final HttpRequest request = HttpRequest.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .uri(URI.create("http://localhost:8080/medications"))
            .GET()
            .build();

        final HttpClient httpClient = HttpClient.newHttpClient();

        final HttpResponse<String> clientHttpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        this.responseBodyMedications = clientHttpResponse.body();
        return new JSONArray(clientHttpResponse.body());

    }

    public void findMedicationAndLoad(String medication, final JPanel defaultContainer, final JFrame screen) throws InterruptedException, IOException {
         
        // Formatar nome do Medicamento pesquisado (Substitui o espaço em branco por %20) 
        medication = medication.strip().replace(" ", "%20");
        
        // Se medicamento for "" e se exibição padrão for falsa
        if (medication.equals("") && this.defaultExhibition == false) {
            
            // Carrega exibição Padrão novamente
            this.defaultExhibition = true;
            loadMedications(new JSONArray(this.responseBodyMedications), defaultContainer, screen);

        // Se medicamento for diferente de "" e se for diferente do texto de exibição padrão da caixa de pesquisa  
        } else if (!medication.equals("") && !medication.equals("Procure%20por%20um%20Medicamento...")) {

            // Instancia uma nova requisição com a busca do medicamento 
            final HttpRequest request = HttpRequest.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .uri(URI.create("http://localhost:8080/medications/findByNome?nome=" + medication))
                .GET()
                .build();

            // Envia a requisição 
            final HttpClient httpClient = HttpClient.newHttpClient();

            // Armazena Resposta da Requisição 
            final HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            // Se resposta da pesquisa for vazia ([])
            if(String.valueOf("[]").equals(httpResponse.body())) {
                
                loadMessageLabel("MEDICAMENTO NÃO ENCONTRADO !!!", defaultContainer); 
                this.defaultExhibition = false;

            // Se não, carrega o(s) medicamento(s) encontrado na pesquisa 
            } else {

                this.defaultExhibition = false;
                loadMedications(new JSONArray(httpResponse.body()), defaultContainer, screen);
                
            }
        }

    }

    /**
     * Load all Medications passed in {@code medicationsArray} into {@code defaultContainer}
     * @param medicationsArray
     * @param defaultContainer
     */
    public void loadMedications(final JSONArray medicationsArray, final JPanel defaultContainer, final JFrame screen) {
        
        try { 
            
            // JPanel para carregar a Linha com 4 Medicamentos na Horizontal
            JPanel linePanel = null;

            // Contador para cada iteração 
            int columnCounter = 0;

            // Remove os Elementos anteriores (caso haver) 
            defaultContainer.removeAll();
             
            // Para cada mediacamento 
            for(int counter = 0; counter <= medicationsArray.length()-1; counter++) {

                // Se contador estiver zerdao, adiciona uma nova Linha Horizontal para carregar mais 4 Elementos
                if (columnCounter == 0) {
                    linePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 40, 40));
                    linePanel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
                    
                    if(medicationsArray.length() <= 4) linePanel.setPreferredSize(new Dimension(1500, 660));
                    
                    defaultContainer.add(linePanel);
                }

                final JPanel elementPanel = new JPanel();
                
                // PAINEL DE CADA ELEMENTO
                elementPanel.setLayout(new BoxLayout(elementPanel, BoxLayout.Y_AXIS));
                elementPanel.setBorder(BorderFactory.createLineBorder(Color.black));
                elementPanel.setBackground(Color.WHITE);
                elementPanel.setPreferredSize(new Dimension(320, 500));
                elementPanel.setMaximumSize(new Dimension(320, 500));
                elementPanel.setMinimumSize(new Dimension(320, 500));
                elementPanel.setAlignmentX(JPanel.LEFT_ALIGNMENT); 

                final JSONObject JSONMedication = medicationsArray.getJSONObject(counter);
                 
                // IMAGEM DO MEDICAMENTO
                final URL medicationImageUrl = new URI(JSONMedication.getString("imagemDoMedicamento")).toURL();
                ImageIcon resizedImage = new ImageIcon(medicationImageUrl);
                
                resizedImage = new ImageIcon(
                    resizedImage.getImage().getScaledInstance(290, 250, Image.SCALE_SMOOTH)
                );
                
                final JLabel imageLabel = new JLabel(resizedImage);
                imageLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 10));
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
                        
                        if(CartUtilities.addMedication(JSONMedication.getInt("id"), CPFOfAuthenticatedClient)) {
                            JOptionPane.showMessageDialog(screen, "Medicamento adicionado ao Carrinho !!!");
                        } else {
                            JOptionPane.showMessageDialog(screen, "Faça Login para adicionar items ao Carrinho !!!", "Erro", JOptionPane.ERROR_MESSAGE);
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

    /**
     * Load the message passed in {@code messageToLoad} into {@code defaultContainer}
     * @param messageToLoad
     * @param defaultContainer
     * @return JPanel inputed into {@code defaultContainer} 
     */
    public JPanel loadMessageLabel(final String messageToLoad, final JPanel defaultContainer) {
        
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
        
        return exhibition;

    }

}
