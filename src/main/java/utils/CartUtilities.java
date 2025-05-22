package utils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CartUtilities {

    public static boolean addMedication(int medicationId, String CPFOfAuthenticatedClient) throws InterruptedException, IOException {

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

    private static int findClientIdByCPF(String CPFOfAuthenticatedClient) throws InterruptedException, IOException {
        
        if (CPFOfAuthenticatedClient == null) {
            return 0;
        }
        
        final HttpRequest request = HttpRequest.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .header("Content-Type", "application/json")
            .uri(URI.create("http://localhost:8080/client/findClientIdByCPF?CPF=" + CPFOfAuthenticatedClient))
            .GET()
            .build();
        
        final HttpClient httpClient = HttpClient.newHttpClient();
        
        final HttpResponse<String> clientHttpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
        if(clientHttpResponse.statusCode() == 200) {
            return Integer.parseInt(clientHttpResponse.body());
        }
        
        else return 0;
        
    }

}
