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
import pe.edu.pucp.softres.model.ReservaxMesasDTO;

/**
 *
 * @author Rosse
 */
public class ReservaxMesaBO {

    private String url;
    private ObjectMapper mapper;
    private HttpClient client;
    private HttpRequest request;
    private HttpResponse<String> response;

    public ReservaxMesaBO() {
        this.url = "http://localhost:8080/SoftResBusinessServidorWS/resources/ReservaxMesa";
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

    private String serializar(ReservaxMesasDTO hxsDTO) throws JsonProcessingException {
        String jsonRequest = this.mapper.writeValueAsString(hxsDTO);
        return jsonRequest;
    }

    private ReservaxMesasDTO deserializar(Class<ReservaxMesasDTO> clase) throws JsonProcessingException {
        ReservaxMesasDTO hxsDTORespuesta = mapper.readValue(this.response.body(), clase);
        return hxsDTORespuesta;
    }

    private List<ReservaxMesasDTO> deserializarListaDTO(TypeReference<List<ReservaxMesasDTO>> typeReference) throws JsonProcessingException {
        String json = this.response.body();
        List<ReservaxMesasDTO> list = this.mapper.readValue(json, typeReference);
        return list;
    }

    public Integer insertar(ReservaxMesasDTO rxm) throws JsonProcessingException, IOException, InterruptedException {
        String jsonRequest = this.serializar(rxm);
        this.crearHttpClient();
        this.crearHttpRequestPOST(jsonRequest, null);
        this.enviarRequest();
        ReservaxMesasDTO rxmDTORespuesta = this.deserializar(ReservaxMesasDTO.class);
        this.cerrarHttpClient();

        if (response.statusCode() == Response.Status.CREATED.getStatusCode()) {
            return rxmDTORespuesta.getReserva().getIdReserva();
        }
        return 0;
    }

    public List<ReservaxMesasDTO> listar(Integer idSede) throws IOException, InterruptedException {
        String jsonRequest = this.mapper.writeValueAsString(idSede);
        this.crearHttpClient();
        this.crearHttpRequestPOST(jsonRequest, "listar");
        this.enviarRequest();
        this.cerrarHttpClient();
        List<ReservaxMesasDTO> listarxm= this.deserializarListaDTO(new TypeReference<List<ReservaxMesasDTO>>() {
        });
        return listarxm;
    }

    public Integer eliminar(ReservaxMesasDTO rxm) throws IOException, InterruptedException {
        String jsonRequest = this.serializar(rxm);
        this.crearHttpClient();
        this.crearHttpRequestPUT(jsonRequest, "eliminar");
        this.enviarRequest();
        this.cerrarHttpClient();

        if (this.response.statusCode() == Response.Status.NO_CONTENT.getStatusCode()) {
            return rxm.getReserva().getIdReserva();
        }
        return 0;
    }
}
