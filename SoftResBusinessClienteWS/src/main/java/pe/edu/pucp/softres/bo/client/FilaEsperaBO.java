/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.bo.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.List;
import pe.edu.pucp.softres.model.FilaEsperaDTO;
import pe.edu.pucp.softres.parametros.FilaEsperaParametros;

/**
 *
 * @author BryanGnr
 */
public class FilaEsperaBO {
     private String url;
    private ObjectMapper mapper;
    private HttpClient client;
    private HttpRequest request;
    private HttpResponse<String> response;

    public FilaEsperaBO() {
        this.url = "http://localhost:8080/SoftResBusinessServidorWS/resources/FilaEspera";
        this.mapper = new ObjectMapper();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        this.mapper.setDateFormat(sdf);
        this.mapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.mapper.disable(com.fasterxml.jackson.databind.DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        this.client = null;
        this.request = null;
        this.response = null;
    }
    
    private void crearHttpClient() {
        this.client = HttpClient.newHttpClient();
    }

    private void cerrarHttpClient() {
       this.client.close();
    }

    private void crearHttpRequestPOST(String jsonRequest, String urlExtra) {
        String urlPOST = this.url;
        if (urlExtra != null) {
            urlPOST = urlPOST.concat("/" + urlExtra);
        }
        this.request = HttpRequest.newBuilder()
                .uri(URI.create(urlPOST))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();
    }

    private void crearHttpRequestGET(Integer filaEsperaId) {
        String urlGET = this.url;
        if (filaEsperaId != null) {
            urlGET = urlGET.concat("/" + filaEsperaId);
        }

        this.request = HttpRequest.newBuilder()
                .uri(URI.create(urlGET))
                .GET()
                .build();
    }
    
    private void crearHttpRequestPUT(String jsonRequest, String urlExtra) {
        String urlPUT = this.url;
        if (urlExtra != null) {
            urlPUT = urlPUT.concat("/" + urlExtra);
        }
        this.request = HttpRequest.newBuilder()
                .uri(URI.create(urlPUT))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();
    }
    
    private void enviarRequest() throws IOException, InterruptedException {
        this.response = this.client.send(this.request, HttpResponse.BodyHandlers.ofString());
    }

    private String serializar(FilaEsperaDTO filaEsperaDTO) throws JsonProcessingException {
        String jsonRequest = this.mapper.writeValueAsString(filaEsperaDTO);
        return jsonRequest;
    }

    private FilaEsperaDTO deserializar(Class<FilaEsperaDTO> clase) throws JsonProcessingException {
        FilaEsperaDTO filasEsperaDTORespuesta = mapper.readValue(this.response.body(), clase);
        return filasEsperaDTORespuesta;
    }
    
    private List<FilaEsperaDTO> deserializarListaDTO(TypeReference<List<FilaEsperaDTO>> typeReference) throws JsonProcessingException {
        String json = this.response.body();
        List<FilaEsperaDTO> listFilasEspera = this.mapper.readValue(json, typeReference);
        return listFilasEspera;
    }

    public Integer insertar(FilaEsperaDTO filaEspera) throws JsonProcessingException, IOException, InterruptedException {
        String jsonRequest = this.serializar(filaEspera);
        this.crearHttpClient();
        this.crearHttpRequestPOST(jsonRequest, null);
        this.enviarRequest();
        FilaEsperaDTO filaEsperaDTORespuesta = this.deserializar(FilaEsperaDTO.class);
        this.cerrarHttpClient();

        if (response.statusCode() == Response.Status.CREATED.getStatusCode()) {
            return filaEsperaDTORespuesta.getIdFila();
        }
        return 0;
    }
    
    public FilaEsperaDTO obtenerPorId(Integer filaEsperaId) throws IOException, InterruptedException {
        this.crearHttpClient();
        this.crearHttpRequestGET(filaEsperaId);
        this.enviarRequest();
        this.cerrarHttpClient();
        FilaEsperaDTO filaEsperaDTORespuesta = this.deserializar(FilaEsperaDTO.class);
        return filaEsperaDTORespuesta;
    }

    public List<FilaEsperaDTO> listar(FilaEsperaParametros parametros) throws IOException, InterruptedException {
        String jsonRequest = this.mapper.writeValueAsString(parametros);
        this.crearHttpClient();
        this.crearHttpRequestPOST(jsonRequest, "listar");
        this.enviarRequest();
        this.cerrarHttpClient();
        List<FilaEsperaDTO> listaFilaEspera = this.deserializarListaDTO(new TypeReference<List<FilaEsperaDTO>>() {
        });
        return listaFilaEspera;
    }
    
    public Integer modificar(FilaEsperaDTO filaEspera) throws JsonProcessingException, IOException, InterruptedException {
        String jsonRequest = this.serializar(filaEspera);
        this.crearHttpClient();
        this.crearHttpRequestPUT(jsonRequest, null);
        this.enviarRequest();
        this.cerrarHttpClient();

        if (this.response.statusCode() == Response.Status.OK.getStatusCode()) {
            return filaEspera.getIdFila();
        }
        return 0;
    }

    public Integer eliminar(FilaEsperaDTO filaEspera) throws IOException, InterruptedException {
        String jsonRequest = this.serializar(filaEspera);
        this.crearHttpClient();
        this.crearHttpRequestPUT(jsonRequest, "eliminar");
        this.enviarRequest();
        this.cerrarHttpClient();

        if (this.response.statusCode() == Response.Status.NO_CONTENT.getStatusCode()) {
            return filaEspera.getIdFila();
        }
        return 0;
    }
}
