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
import pe.edu.pucp.softres.model.NotificacionDTO;
import pe.edu.pucp.softres.parametros.NotificacionParametros;

/**
 *
 * @author BryanGnr
 */
public class NotificacionBO {
    private String url;
    private ObjectMapper mapper;
    private HttpClient client;
    private HttpRequest request;
    private HttpResponse<String> response;
    
    public NotificacionBO(){
        this.url = "http://localhost:8080/SoftResBusinessServidorWS/resources/Notificaciones";
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
    
    private void crearHttpRequestGET(Integer notificacionId) {
        String urlGET = this.url;
        if (notificacionId != null) {
            urlGET = urlGET.concat("/" + notificacionId);
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

    private String serializar(NotificacionDTO notificacionDTO) throws JsonProcessingException {
        String jsonRequest = this.mapper.writeValueAsString(notificacionDTO);
        return jsonRequest;
    }

    private NotificacionDTO deserializar(Class<NotificacionDTO> clase) throws JsonProcessingException {
        NotificacionDTO notificacionesDTORespuesta = mapper.readValue(this.response.body(), clase);
        return notificacionesDTORespuesta;
    }

    private List<NotificacionDTO> deserializarListaDTO(TypeReference<List<NotificacionDTO>> typeReference) throws JsonProcessingException {
        String json = this.response.body();
        List<NotificacionDTO> listNotificaciones = this.mapper.readValue(json, typeReference);
        return listNotificaciones;
    }
    
    public Integer insertar(NotificacionDTO notificacion) throws JsonProcessingException, IOException, InterruptedException {
        String jsonRequest = this.serializar(notificacion);
        this.crearHttpClient();
        this.crearHttpRequestPOST(jsonRequest, null);
        this.enviarRequest();
        NotificacionDTO notificacionDTORespuesta = this.deserializar(NotificacionDTO.class);
        this.cerrarHttpClient();

        if (response.statusCode() == Response.Status.CREATED.getStatusCode()) {
            return notificacionDTORespuesta.getIdNotificacion();
        }
        return 0;
    }
    
    public NotificacionDTO obtenerPorId(Integer notificacionId) throws IOException, InterruptedException {
        this.crearHttpClient();
        this.crearHttpRequestGET(notificacionId);
        this.enviarRequest();
        this.cerrarHttpClient();
        NotificacionDTO notificacionDTORespuesta = this.deserializar(NotificacionDTO.class);
        return notificacionDTORespuesta;
    }
    
    public List<NotificacionDTO> listar(NotificacionParametros parametros) throws IOException, InterruptedException {
        String jsonRequest = this.mapper.writeValueAsString(parametros);
        this.crearHttpClient();
        this.crearHttpRequestPOST(jsonRequest, "listar");
        this.enviarRequest();
        this.cerrarHttpClient();
        List<NotificacionDTO> listaNotificaciones = this.deserializarListaDTO(new TypeReference<List<NotificacionDTO>>() {
        });
        return listaNotificaciones;
    }
    
    public Integer modificar(NotificacionDTO notificacion) throws JsonProcessingException, IOException, InterruptedException {
        String jsonRequest = this.serializar(notificacion);
        this.crearHttpClient();
        this.crearHttpRequestPUT(jsonRequest, null);
        this.enviarRequest();
        this.cerrarHttpClient();

        if (this.response.statusCode() == Response.Status.OK.getStatusCode()) {
            return notificacion.getIdNotificacion();
        }
        return 0;
    }
    
    public Integer eliminar(NotificacionDTO notificacion) throws IOException, InterruptedException {
        String jsonRequest = this.serializar(notificacion);
        this.crearHttpClient();
        this.crearHttpRequestPUT(jsonRequest, "eliminar");
        this.enviarRequest();
        this.cerrarHttpClient();

        if (this.response.statusCode() == Response.Status.NO_CONTENT.getStatusCode()) {
            return notificacion.getIdNotificacion();
        }
        return 0;
    }
}
