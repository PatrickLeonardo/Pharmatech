package utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.json.JSONArray;
import org.json.JSONObject;

public class CartUtilities {

    private double totalValueOfCart = 0;

    public static boolean addMedicationOnCart(int medicationId, String CPFOfAuthenticatedClient) throws InterruptedException, IOException {

        int clientId = 0;

        try {
            
            clientId = findClientIdByCPF(CPFOfAuthenticatedClient);
            if(clientId == 0) return false;

        } catch(Exception exception) {
            exception.printStackTrace();
        }
        
        final String bodyPublisher = """
        {
            "idMedicamento": %d,
            "quantidade": %d,
            "idCliente": %d
        }
        """.formatted(medicationId, 1, clientId);
         
        final HttpRequest request = HttpRequest.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .header("Content-Type", "application/json")
            .uri(URI.create("http://localhost:8080/cart/insertItemInCart"))
            .POST(HttpRequest.BodyPublishers.ofString(bodyPublisher))
            .build();
        
        final HttpClient httpClient = HttpClient.newHttpClient();
        
        final HttpResponse<String> clientHttpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
        if(clientHttpResponse.statusCode() == 200) return true;
        else return false;
        
    }

    public boolean loadCart(String CPFOfAuthenticatedClient, JPanel defaultContainer) throws InterruptedException, IOException {

        this.totalValueOfCart = 0;
        defaultContainer.removeAll();
        //defaultContainer.setPreferredSize(new Dimension(1500, 660));

        if (CPFOfAuthenticatedClient == null) {
            //return false;
        }
        
        int clientId = findClientIdByCPF(CPFOfAuthenticatedClient);

        final HttpRequest request = HttpRequest.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .header("Content-Type", "application/json")
            .uri(URI.create("http://localhost:8080/cart/findByClientId?clientId=" + clientId))
            .GET()
            .build();
        
        final HttpClient httpClient = HttpClient.newHttpClient();
        final HttpResponse<String> clientHttpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
        JSONArray medicationsOnCartJSONArray = new JSONArray(clientHttpResponse.body());
         
        for(int counter = 0; counter <= medicationsOnCartJSONArray.length()-1; counter++) {
            
            JSONObject cartObject = medicationsOnCartJSONArray.getJSONObject(counter);

            final HttpRequest medicationRequest = HttpRequest.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .header("Content-Type", "application/json")
                .uri(URI.create("http://localhost:8080/medications/findById?id=" + cartObject.getInt("idMedicamento")))
                .GET()
                .build();
            
            final HttpClient httpClientMedicationRequest = HttpClient.newHttpClient();
            
            final HttpResponse<String> clientHttpResponseOfMedicationsRequest = httpClientMedicationRequest.send(medicationRequest, HttpResponse.BodyHandlers.ofString()); 

            JSONObject medicationJSONObject = new JSONObject(clientHttpResponseOfMedicationsRequest.body());
            
            // LINHA DO MEDICAMENTO NO CARRINHO 
            JPanel medicationLine = new JPanel();
            medicationLine.setBorder(BorderFactory.createLineBorder(Color.black));
            medicationLine.setPreferredSize(new Dimension(700, 48));
            
            // NOME 
            JLabel medicationName = new JLabel("  " + medicationJSONObject.getString("nome") + "      ");
            medicationName.setFont(new Font("Helvetica", Font.BOLD, 25));
            medicationLine.add(medicationName);

            // DOSAGEM 
            JLabel medicationDosage = new JLabel(medicationJSONObject.getString("dosagem") + "      ");
            medicationDosage.setFont(new Font("Helvetica", Font.BOLD, 25));
            medicationLine.add(medicationDosage);

            // PREÇO
            this.totalValueOfCart += medicationJSONObject.getDouble("preco") * cartObject.getInt("quantidade");
            String priceText = "R$ " + String.valueOf(medicationJSONObject.getDouble("preco")).replace(".", ",");
             
            if (Character.valueOf(',').equals(priceText.charAt(priceText.length()-2))) {
                priceText = priceText.concat("0");
            }

            JLabel medicationPrice = new JLabel(priceText + "   ");
            medicationPrice.setFont(new Font("Helvetica", Font.BOLD, 25));
            medicationLine.add(medicationPrice);

            JPanel line = new JPanel(new FlowLayout(FlowLayout.LEFT, 40, 40));
            line.setAlignmentX(JPanel.LEFT_ALIGNMENT);
            line.setBackground(new Color(207, 206, 206));
            line.setPreferredSize(new Dimension(1420, 108));
            line.setMaximumSize(new Dimension(1420, 108));
            line.setMinimumSize(new Dimension(1420, 108));

            line.add(medicationLine);
            
            // BOTÃO DIMINUIR 
            JButton btnDecrease = new JButton(" - ");
            btnDecrease.setFont(new Font("Helvetica", Font.BOLD, 25));

            // QUANTIDADE 
            JLabel quantityInCart = new JLabel("  " + String.valueOf(cartObject.getInt("quantidade")) + "  ");
            quantityInCart.setFont(new Font("Helvetica", Font.BOLD, 25));

            // BOTÃO AUMENTAR 
            JButton btnIncrease = new JButton(" + ");
            btnIncrease.setFont(new Font("Helvetica", Font.BOLD, 25));

            // BOTÃO REMOVER ITEM 
            JButton btnRemove = new JButton("X");
            btnRemove.setFont(new Font("Helvetica", Font.BOLD, 25));


            btnDecrease.addActionListener((event) -> {
                
                try {
                    
                    increaseOrDecreaseItemOnCart(cartObject.getInt("id"), cartObject.getInt("quantidade")-1);
                    loadCart(CPFOfAuthenticatedClient, defaultContainer);
                    
                } catch(Exception exception) {
                    exception.printStackTrace();
                }

            });

            btnIncrease.addActionListener((event) -> {
                
                try {
                    
                    increaseOrDecreaseItemOnCart(cartObject.getInt("id"), cartObject.getInt("quantidade")+1);                    
                    loadCart(CPFOfAuthenticatedClient, defaultContainer);
                    
                } catch(Exception exception) {
                    exception.printStackTrace();
                }

            });

            btnRemove.addActionListener((event) -> {
                
                try {
                    deleteItemOnCartById(cartObject.getInt("id"));
                    loadCart(CPFOfAuthenticatedClient, defaultContainer);
                } catch(Exception exception) {
                    exception.printStackTrace();
                }

            });

            line.add(btnDecrease);
            line.add(quantityInCart);
            line.add(btnIncrease);
            line.add(btnRemove);

            defaultContainer.add(line);

        }
        
        // VALOR TOTAL DO CARRINHO
        String textToLabelTotalValue = String.format("%.2f", this.totalValueOfCart);
        textToLabelTotalValue = "VALOR TOTAL DO CARRINHO:  R$ " + textToLabelTotalValue.replace(".", ",");
        
        JLabel totalValueLabel = new JLabel();
        totalValueLabel.setText(textToLabelTotalValue);
        totalValueLabel.setFont(new Font("Helvetica", Font.BOLD, 38));

        JPanel totalValuePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 640, 80));
        totalValuePanel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        totalValuePanel.setBackground(new Color(207, 206, 206));

        totalValuePanel.add(totalValueLabel);

        defaultContainer.add(totalValuePanel);
        defaultContainer.revalidate();
        defaultContainer.repaint();

        return true;

    }

    private static int findClientIdByCPF(String CPFOfAuthenticatedClient) throws InterruptedException, IOException {
       
        if (CPFOfAuthenticatedClient == null) {
            return 0;
        }
        
        final HttpRequest request = HttpRequest.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .header("Content-Type", "application/json")
            .uri(URI.create("http://localhost:8080/client/findClientByCPF?CPF=" + CPFOfAuthenticatedClient))
            .GET()
            .build();
        
        final HttpClient httpClient = HttpClient.newHttpClient();
        
        final HttpResponse<String> clientHttpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
        if(clientHttpResponse.statusCode() == 200) {
            JSONObject objectBodyClient = new JSONObject(clientHttpResponse.body());
            return objectBodyClient.getInt("id");
        }
        
        else return 0;
        
    }

    private static void increaseOrDecreaseItemOnCart(int cartId, int quantity) throws InterruptedException, IOException {
         
        final HttpRequest request = HttpRequest.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .header("Content-Type", "application/json")
            .uri(URI.create("http://localhost:8080/cart/increseOrDecreaseItemInCart?cartId=" + cartId + "&quantity=" + quantity))
            .PUT(HttpRequest.BodyPublishers.ofString(""))
            .build();

        HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

    }

    private static void deleteItemOnCartById(int cartId) throws InterruptedException, IOException {

        final HttpRequest request = HttpRequest.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .header("Content-Type", "application/json")
            .uri(URI.create("http://localhost:8080/cart/deleteItemInCartById?cartId=" + cartId))
            .DELETE()
            .build();

        HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

    }

    public boolean isEmptyCart() {

        if(this.totalValueOfCart == 0) return true;
        else return false;

    }

}
