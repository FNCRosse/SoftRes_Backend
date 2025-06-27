/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.bo.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.net.URI;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.List;
import pe.edu.pucp.softres.model.HorarioAtencionDTO;
import pe.edu.pucp.softres.parametros.HorarioParametros;

/**
 *
 * @author Rosse
 */
public class HorarioAtencionBO {

    private String url;
    private ObjectMapper mapper;
    private HttpClient client;
    private HttpRequest request;
    private HttpResponse<String> response;

    public HorarioAtencionBO() {
        this.url = "http://localhost:8080/SoftResBusinessServidorWS/resources/HorarioAtencion";
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
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
//       this.client.close();
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

    private void crearHttpRequestGET(Integer rolId) {
        String urlGET = this.url;
        if (rolId != null) {
            urlGET = urlGET.concat("/" + rolId);
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

    private String serializar(HorarioAtencionDTO rolDTO) throws JsonProcessingException {
        String jsonRequest = this.mapper.writeValueAsString(rolDTO);
        return jsonRequest;
    }

    private HorarioAtencionDTO deserializar(Class<HorarioAtencionDTO> clase) throws JsonProcessingException {
        HorarioAtencionDTO horarioDTORespuesta = mapper.readValue(this.response.body(), clase);
        return horarioDTORespuesta;
    }

    private List<HorarioAtencionDTO> deserializarListaDTO(TypeReference<List<HorarioAtencionDTO>> typeReference) throws JsonProcessingException {
        String json = this.response.body();
        List<HorarioAtencionDTO> listHorario = this.mapper.readValue(json, typeReference);
        return listHorario;
    }

    public Integer insertar(HorarioAtencionDTO horario) throws JsonProcessingException, IOException, InterruptedException {
        String jsonRequest = this.serializar(horario);
        this.crearHttpClient();
        this.crearHttpRequestPOST(jsonRequest, null);
        this.enviarRequest();
        HorarioAtencionDTO horarioDTORespuesta = this.deserializar(HorarioAtencionDTO.class);
        this.cerrarHttpClient();

        if (response.statusCode() == Response.Status.CREATED.getStatusCode()) {
            return horarioDTORespuesta.getIdHorario();
        }
        return 0;
    }

    public HorarioAtencionDTO obtenerPorId(Integer horarioID) throws IOException, InterruptedException {
        this.crearHttpClient();
        this.crearHttpRequestGET(horarioID);
        this.enviarRequest();
        this.cerrarHttpClient();
        HorarioAtencionDTO horarioDTORespuesta = this.deserializar(HorarioAtencionDTO.class);
        return horarioDTORespuesta;
    }

    public List<HorarioAtencionDTO> listar(HorarioParametros parametros) throws IOException, InterruptedException {
        String jsonRequest = this.mapper.writeValueAsString(parametros);
        this.crearHttpClient();
        this.crearHttpRequestPOST(jsonRequest, "listar");
        this.enviarRequest();
        this.cerrarHttpClient();
        List<HorarioAtencionDTO> listaHorarios = this.deserializarListaDTO(new TypeReference<List<HorarioAtencionDTO>>() {
        });
        return listaHorarios;
    }

    public Integer modificar(HorarioAtencionDTO horario) throws JsonProcessingException, IOException, InterruptedException {
        String jsonRequest = this.serializar(horario);
        System.out.println("➡ JSON enviado al backend:");
        System.out.println(jsonRequest);
        this.crearHttpClient();
        this.crearHttpRequestPUT(jsonRequest, null);
        this.enviarRequest();
        this.cerrarHttpClient();

        System.out.println("⬅ Respuesta recibida del backend:");
        System.out.println(this.response.body());
        if (this.response.statusCode() == Response.Status.OK.getStatusCode()) {
            return horario.getIdHorario();
        }
        return 0;
    }

    public Integer eliminar(HorarioAtencionDTO horario) throws IOException, InterruptedException {
        String jsonRequest = this.serializar(horario);
        this.crearHttpClient();
        this.crearHttpRequestPUT(jsonRequest, "eliminar");
        this.enviarRequest();
        this.cerrarHttpClient();

        if (this.response.statusCode() == Response.Status.NO_CONTENT.getStatusCode()) {
            return horario.getIdHorario();
        }
        return 0;
    }

}
