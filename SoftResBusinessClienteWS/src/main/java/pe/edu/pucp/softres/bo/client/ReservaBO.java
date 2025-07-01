package pe.edu.pucp.softres.bo.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.List;
import pe.edu.pucp.softres.model.ReservaDTO;
import pe.edu.pucp.softres.parametros.ReservaParametros;

/**
 *
 * @author frank
 */
public class ReservaBO {

    private final String url;
    private final ObjectMapper mapper;
    private HttpClient client;
    private HttpRequest request;
    private HttpResponse<String> response;

    public ReservaBO() {
        this.url = "http://localhost:8080/SoftResBusinessServidorWS/resources/Reserva";
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
        // No es necesario cerrar client en Java 11+
    }

    private void crearHttpRequestPOST(String jsonRequest, String urlExtra) {
        String fullUrl = this.url;
        if (urlExtra != null) fullUrl += "/" + urlExtra;

        this.request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();
    }

    private void crearHttpRequestGET(Integer id) {
        String fullUrl = this.url;
        if (id != null) fullUrl += "/" + id;

        this.request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .GET()
                .build();
    }

    private void crearHttpRequestPUT(String jsonRequest, String urlExtra) {
        String fullUrl = this.url;
        if (urlExtra != null) fullUrl += "/" + urlExtra;

        this.request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();
    }

    private void enviarRequest() throws IOException, InterruptedException {
        this.response = this.client.send(this.request, HttpResponse.BodyHandlers.ofString());
    }

    private String serializar(Object obj) throws JsonProcessingException {
        return this.mapper.writeValueAsString(obj);
    }

    private ReservaDTO deserializarReserva() throws JsonProcessingException {
        return this.mapper.readValue(this.response.body(), ReservaDTO.class);
    }

    private List<ReservaDTO> deserializarLista() throws JsonProcessingException {
        return this.mapper.readValue(this.response.body(), new TypeReference<>() {});
    }

    public Integer insertar(ReservaDTO reserva) throws IOException, InterruptedException {
        String jsonRequest = serializar(reserva);
        this.crearHttpClient();
        this.crearHttpRequestPOST(jsonRequest, null);
        this.enviarRequest();
        this.cerrarHttpClient();

        if (response.statusCode() == Response.Status.CREATED.getStatusCode()) {
            return deserializarReserva().getIdReserva();
        }
        return 0;
    }

    public ReservaDTO obtenerPorId(Integer reservaId) throws IOException, InterruptedException {
        this.crearHttpClient();
        this.crearHttpRequestGET(reservaId);
        this.enviarRequest();
        this.cerrarHttpClient();
        return deserializarReserva();
    }

    public List<ReservaDTO> listar(ReservaParametros parametros) throws IOException, InterruptedException {
        String jsonRequest = serializar(parametros);
        this.crearHttpClient();
        this.crearHttpRequestPOST(jsonRequest, "listar");
        this.enviarRequest();
        this.cerrarHttpClient();
        return deserializarLista();
    }

    public Integer modificar(ReservaDTO reserva) throws IOException, InterruptedException {
        String jsonRequest = serializar(reserva);
        this.crearHttpClient();
        this.crearHttpRequestPUT(jsonRequest, null);
        this.enviarRequest();
        this.cerrarHttpClient();

        if (response.statusCode() == Response.Status.OK.getStatusCode()) {
            return reserva.getIdReserva();
        }
        return 0;
    }

    public Integer eliminar(ReservaDTO reserva) throws IOException, InterruptedException {
        String jsonRequest = serializar(reserva);
        this.crearHttpClient();
        this.crearHttpRequestPUT(jsonRequest, "eliminar");
        this.enviarRequest();
        this.cerrarHttpClient();

        if (response.statusCode() == Response.Status.NO_CONTENT.getStatusCode()) {
            return reserva.getIdReserva();
        }
        return 0;
    }
}
