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
import java.util.ArrayList;

import pe.edu.pucp.softres.model.TipoMesaDTO;

public class TipoMesaBO {
    private String url;
    private ObjectMapper mapper;
    private HttpClient client;
    private HttpRequest request;
    private HttpResponse<String> response;
    
    public TipoMesaBO(){
        this.url = "http://localhost:8080/SoftResBusinessServidorWS/resources/TipoMesa";
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
//        this.client.close();
    }
    
    private void crearHttpRequestPOST(String jsonRequest,String urlExtra) {
        String urlPOST = this.url;
        if (urlExtra != null) {
            urlPOST = urlPOST.concat("/" + urlExtra);
        }
        this.request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();
    }

    private void crearHttpRequestGET(Integer tipoMesaId) {
        String urlGET = this.url;
        if (tipoMesaId != null) {
            urlGET = urlGET.concat("/" + tipoMesaId);
        }

        this.request = HttpRequest.newBuilder()
                .uri(URI.create(urlGET))
                .GET()
                .build();
    }
    
    private void crearHttpRequestPUT(String jsonRequest,String urlExtra) {
        String urlPUT = this.url;
        if (urlExtra != null) {
            urlPUT = urlPUT.concat("/" + urlExtra);
        }
        this.request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();
    }

    private void enviarRequest() throws IOException, InterruptedException {
        this.response = this.client.send(this.request, HttpResponse.BodyHandlers.ofString());
    }

    private TipoMesaDTO crearDTO(Integer idTipoMesa,String nombre) {
        TipoMesaDTO tipoMesaDTO = new TipoMesaDTO();
        tipoMesaDTO.setIdTipoMesa(idTipoMesa);
        tipoMesaDTO.setNombre(nombre);
        return tipoMesaDTO;
    }

    private String serializar(TipoMesaDTO tipoMesaDTO) throws JsonProcessingException {
        String jsonRequest = this.mapper.writeValueAsString(tipoMesaDTO);
        return jsonRequest;
    }

    private TipoMesaDTO deserializar(Class<TipoMesaDTO> clase) throws JsonProcessingException {
        TipoMesaDTO tipoMesaDTORespuesta = mapper.readValue(this.response.body(), clase);
        return tipoMesaDTORespuesta;
    }

    private ArrayList<TipoMesaDTO> deserializarListaDTO(TypeReference<ArrayList<TipoMesaDTO>> typeReference) throws JsonProcessingException {
        String json = this.response.body();
        ArrayList<TipoMesaDTO> listTipoMesa = this.mapper.readValue(json, typeReference);
        return listTipoMesa;
    }
    
    public Integer insertar(TipoMesaDTO tipoMesaDTO) throws JsonProcessingException, IOException, InterruptedException {
        String jsonRequest = this.serializar(tipoMesaDTO);
        this.crearHttpClient();
        this.crearHttpRequestPOST(jsonRequest,null);
        this.enviarRequest();
        TipoMesaDTO tipoMesaDTORespuesta = this.deserializar(TipoMesaDTO.class);
        this.cerrarHttpClient();

        if (response.statusCode() == Response.Status.CREATED.getStatusCode()) {
            return tipoMesaDTORespuesta.getIdTipoMesa();
        }
        return 0;
    }
    
    
    public TipoMesaDTO obtenerPorId(Integer tipoMesaId) throws IOException, InterruptedException {
        this.crearHttpClient();
        this.crearHttpRequestGET(tipoMesaId);
        this.enviarRequest();
        this.cerrarHttpClient();
        TipoMesaDTO tipoMesaDTORespuesta = this.deserializar(TipoMesaDTO.class);
        return tipoMesaDTORespuesta;
    }

    public ArrayList<TipoMesaDTO> listar() throws IOException, InterruptedException {
        String jsonRequest = this.mapper.writeValueAsString(null);
        this.crearHttpClient();
        this.crearHttpRequestPOST(jsonRequest, "listar");
        this.enviarRequest();
        this.cerrarHttpClient();
        ArrayList<TipoMesaDTO> listaTipoMesa = this.deserializarListaDTO(new TypeReference<ArrayList<TipoMesaDTO>>() {
        });
        return listaTipoMesa;
    }

    public Integer modificar(TipoMesaDTO tipoMesaDTO) throws JsonProcessingException, IOException, InterruptedException {
        String jsonRequest = this.serializar(tipoMesaDTO);
        this.crearHttpClient();
        this.crearHttpRequestPUT(jsonRequest, null);
        this.enviarRequest();
        this.cerrarHttpClient();

        if (this.response.statusCode() == Response.Status.OK.getStatusCode()) {
            return tipoMesaDTO.getIdTipoMesa();
        }
        return 0;
    }

    public Integer eliminar(TipoMesaDTO tipoMesaDTO) throws IOException, InterruptedException {
        String jsonRequest = this.serializar(tipoMesaDTO);
        this.crearHttpClient();
        this.crearHttpRequestPUT(jsonRequest, "eliminar");
        this.enviarRequest();
        this.cerrarHttpClient();
        
        if (this.response.statusCode() == Response.Status.NO_CONTENT.getStatusCode()) {
            return tipoMesaDTO.getIdTipoMesa();
        }
        return 0;
    }    
}
