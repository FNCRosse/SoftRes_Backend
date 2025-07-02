package pe.edu.pucp.softres.bo.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.core.Response;
import pe.edu.pucp.softres.model.ReservaDTO;
import pe.edu.pucp.softres.parametros.ReservaParametros;

/**
 * Business Object para operaciones de Reserva en el cliente
 * Conecta con el servicio REST del servidor para realizar operaciones CRUD
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

    /**
     * Clase DTO para confirmación de reservas
     * Coincide con la implementación del servidor
     */
    public static class ReservaConfirmacionDTO {
        private Integer reservaId;
        private Integer usuarioConfirmadorId;

        public ReservaConfirmacionDTO() {}

        public ReservaConfirmacionDTO(Integer reservaId, Integer usuarioConfirmadorId) {
            this.reservaId = reservaId;
            this.usuarioConfirmadorId = usuarioConfirmadorId;
        }

        public Integer getReservaId() {
            return reservaId;
        }

        public void setReservaId(Integer reservaId) {
            this.reservaId = reservaId;
        }

        public Integer getUsuarioConfirmadorId() {
            return usuarioConfirmadorId;
        }

        public void setUsuarioConfirmadorId(Integer usuarioConfirmadorId) {
            this.usuarioConfirmadorId = usuarioConfirmadorId;
        }
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

    private void crearHttpRequestPUTSinBody(String urlExtra) {
        String fullUrl = this.url;
        if (urlExtra != null) fullUrl += "/" + urlExtra;

        this.request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.noBody())
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

    /**
     * Validaciones de parámetros de entrada
     */
    private void validarReserva(ReservaDTO reserva) {
        if (reserva == null) {
            throw new IllegalArgumentException("La reserva no puede ser null");
        }
    }

    private void validarId(Integer id, String nombreParam) {
        if (id == null) {
            throw new IllegalArgumentException(nombreParam + " no puede ser null");
        }
        if (id <= 0) {
            throw new IllegalArgumentException(nombreParam + " debe ser un número positivo");
        }
    }

    private void validarParametros(ReservaParametros parametros) {
        if (parametros == null) {
            throw new IllegalArgumentException("Los parámetros de búsqueda no pueden ser null");
        }
    }

    /**
     * Manejo mejorado de respuestas HTTP con mensajes de error específicos
     */
    private void validarRespuestaHTTP() throws IOException {
        if (response == null) {
            throw new IOException("No se recibió respuesta del servidor");
        }

        int statusCode = response.statusCode();
        String responseBody = response.body();

        if (statusCode >= 400) {
            String mensaje = "Error HTTP " + statusCode;
            if (responseBody != null && !responseBody.trim().isEmpty()) {
                mensaje += ": " + responseBody;
            }
            
            switch (statusCode) {
                case 400:
                    throw new IllegalArgumentException("Solicitud inválida - " + responseBody);
                case 404:
                    throw new RuntimeException("Recurso no encontrado - " + responseBody);
                case 500:
                    throw new IOException("Error interno del servidor - " + responseBody);
                default:
                    throw new IOException(mensaje);
            }
        }
    }

    public Integer insertar(ReservaDTO reserva) throws IOException, InterruptedException {
        try {
            validarReserva(reserva);
            
            String jsonRequest = serializar(reserva);
            this.crearHttpClient();
            this.crearHttpRequestPOST(jsonRequest, null);
            this.enviarRequest();
            this.cerrarHttpClient();

            validarRespuestaHTTP();

            if (response.statusCode() == Response.Status.CREATED.getStatusCode()) {
                ReservaDTO reservaCreada = deserializarReserva();
                return reservaCreada.getIdReserva();
            }
            return 0;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (JsonProcessingException e) {
            throw new IOException("Error al procesar datos JSON: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al insertar reserva: " + e.getMessage(), e);
        }
    }

    public ReservaDTO obtenerPorId(Integer reservaId) throws IOException, InterruptedException {
        try {
            validarId(reservaId, "ID de reserva");
            
            this.crearHttpClient();
            this.crearHttpRequestGET(reservaId);
            this.enviarRequest();
            this.cerrarHttpClient();
            
            validarRespuestaHTTP();
            return deserializarReserva();
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (JsonProcessingException e) {
            throw new IOException("Error al procesar datos JSON: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al obtener reserva por ID: " + e.getMessage(), e);
        }
    }

    public List<ReservaDTO> listar(ReservaParametros parametros) throws IOException, InterruptedException {
        try {
            validarParametros(parametros);
            
            String jsonRequest = serializar(parametros);
            this.crearHttpClient();
            this.crearHttpRequestPOST(jsonRequest, "listar");
            this.enviarRequest();
            this.cerrarHttpClient();
            
            validarRespuestaHTTP();
            return deserializarLista();
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (JsonProcessingException e) {
            throw new IOException("Error al procesar datos JSON: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al listar reservas: " + e.getMessage(), e);
        }
    }

    public Integer modificar(ReservaDTO reserva) throws IOException, InterruptedException {
        try {
            validarReserva(reserva);
            validarId(reserva.getIdReserva(), "ID de reserva");
            
            String jsonRequest = serializar(reserva);
            this.crearHttpClient();
            this.crearHttpRequestPUT(jsonRequest, null);
            this.enviarRequest();
            this.cerrarHttpClient();

            validarRespuestaHTTP();

            if (response.statusCode() == Response.Status.OK.getStatusCode()) {
                return reserva.getIdReserva();
            }
            return 0;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (JsonProcessingException e) {
            throw new IOException("Error al procesar datos JSON: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al modificar reserva: " + e.getMessage(), e);
        }
    }

    public Integer eliminar(ReservaDTO reserva) throws IOException, InterruptedException {
        try {
            validarReserva(reserva);
            validarId(reserva.getIdReserva(), "ID de reserva");
            
            String jsonRequest = serializar(reserva);
            this.crearHttpClient();
            this.crearHttpRequestPUT(jsonRequest, "eliminar");
            this.enviarRequest();
            this.cerrarHttpClient();

            validarRespuestaHTTP();

            if (response.statusCode() == Response.Status.NO_CONTENT.getStatusCode()) {
                return reserva.getIdReserva();
            }
            return 0;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (JsonProcessingException e) {
            throw new IOException("Error al procesar datos JSON: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al eliminar reserva: " + e.getMessage(), e);
        }
    }

    /**
     * Confirma una reserva usando PUT con parámetros en la URL
     * @param reservaId ID de la reserva a confirmar
     * @param usuarioConfirmadorId ID del usuario que confirma
     * @return ID de la reserva si fue exitoso, 0 en caso contrario
     */
    public Integer confirmarReserva(Integer reservaId, Integer usuarioConfirmadorId) throws IOException, InterruptedException {
        try {
            validarId(reservaId, "ID de reserva");
            validarId(usuarioConfirmadorId, "ID de usuario confirmador");
            
            this.crearHttpClient();
            this.crearHttpRequestPUTSinBody("confirmar/" + reservaId + "/" + usuarioConfirmadorId);
            this.enviarRequest();
            this.cerrarHttpClient();

            validarRespuestaHTTP();

            if (response.statusCode() == Response.Status.OK.getStatusCode()) {
                return reservaId;
            }
            return 0;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new IOException("Error al confirmar reserva: " + e.getMessage(), e);
        }
    }

    /**
     * Confirma una reserva usando POST con DTO (método alternativo)
     * @param confirmacion DTO con los datos de confirmación
     * @return ID de la reserva si fue exitoso, 0 en caso contrario
     */
    public Integer confirmarReservaPost(ReservaConfirmacionDTO confirmacion) throws IOException, InterruptedException {
        try {
            if (confirmacion == null) {
                throw new IllegalArgumentException("Los datos de confirmación no pueden ser null");
            }
            validarId(confirmacion.getReservaId(), "ID de reserva en confirmación");
            validarId(confirmacion.getUsuarioConfirmadorId(), "ID de usuario confirmador en confirmación");
            
            String jsonRequest = serializar(confirmacion);
            this.crearHttpClient();
            this.crearHttpRequestPOST(jsonRequest, "confirmar");
            this.enviarRequest();
            this.cerrarHttpClient();

            validarRespuestaHTTP();

            if (response.statusCode() == Response.Status.OK.getStatusCode()) {
                return confirmacion.getReservaId();
            }
            return 0;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (JsonProcessingException e) {
            throw new IOException("Error al procesar datos JSON: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al confirmar reserva (POST): " + e.getMessage(), e);
        }
    }

    /**
     * Método de conveniencia para confirmar reserva usando POST
     * @param reservaId ID de la reserva a confirmar
     * @param usuarioConfirmadorId ID del usuario que confirma
     * @return ID de la reserva si fue exitoso, 0 en caso contrario
     */
    public Integer confirmarReservaPost(Integer reservaId, Integer usuarioConfirmadorId) throws IOException, InterruptedException {
        ReservaConfirmacionDTO confirmacion = new ReservaConfirmacionDTO(reservaId, usuarioConfirmadorId);
        return confirmarReservaPost(confirmacion);
    }

    public Integer cancelar(ReservaDTO reserva) throws IOException, InterruptedException {
        try {
            validarReserva(reserva);
            validarId(reserva.getIdReserva(), "ID de reserva");
            
            String jsonRequest = serializar(reserva);
            this.crearHttpClient();
            this.crearHttpRequestPUT(jsonRequest, "cancelar");
            this.enviarRequest();
            this.cerrarHttpClient();

            validarRespuestaHTTP();

            if (response.statusCode() == Response.Status.OK.getStatusCode()) {
                return reserva.getIdReserva();
            }
            return 0;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (JsonProcessingException e) {
            throw new IOException("Error al procesar datos JSON: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al cancelar reserva: " + e.getMessage(), e);
        }
    }
}
