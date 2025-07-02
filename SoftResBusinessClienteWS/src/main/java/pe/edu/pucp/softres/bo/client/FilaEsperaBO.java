/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.bo.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.core.Response;
import pe.edu.pucp.softres.model.FilaEsperaDTO;
import pe.edu.pucp.softres.model.TipoReserva;
import pe.edu.pucp.softres.parametros.FilaEsperaParametros;

/**
 * Business Object para operaciones de Fila de Espera en el cliente
 * Conecta con el servicio REST del servidor para realizar operaciones CRUD
 * Aplica reglas de negocio del lado cliente antes de enviar al servidor
 * 
 * @author Rosse
 */
public class FilaEsperaBO {

    private final String url;
    private final ObjectMapper mapper;
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

    /**
     * Clase DTO para notificaciones de fila de espera
     */
    public static class NotificacionFilaRequest {
        private Integer localId;
        private Date fechaHora;
        private String tipoReserva;
        private Integer tipoMesaId;

        public NotificacionFilaRequest() {}

        public NotificacionFilaRequest(Integer localId, Date fechaHora, String tipoReserva, Integer tipoMesaId) {
            this.localId = localId;
            this.fechaHora = fechaHora;
            this.tipoReserva = tipoReserva;
            this.tipoMesaId = tipoMesaId;
        }

        public Integer getLocalId() {
            return localId;
        }

        public void setLocalId(Integer localId) {
            this.localId = localId;
        }

        public Date getFechaHora() {
            return fechaHora;
        }

        public void setFechaHora(Date fechaHora) {
            this.fechaHora = fechaHora;
        }

        public String getTipoReserva() {
            return tipoReserva;
        }

        public void setTipoReserva(String tipoReserva) {
            this.tipoReserva = tipoReserva;
        }

        public Integer getTipoMesaId() {
            return tipoMesaId;
        }

        public void setTipoMesaId(Integer tipoMesaId) {
            this.tipoMesaId = tipoMesaId;
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

    private void crearHttpRequestGET(String urlExtra) {
        String fullUrl = this.url;
        if (urlExtra != null) fullUrl += "/" + urlExtra;

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

    private FilaEsperaDTO deserializarFilaEspera() throws JsonProcessingException {
        return this.mapper.readValue(this.response.body(), FilaEsperaDTO.class);
    }

    private ArrayList<FilaEsperaDTO> deserializarLista() throws JsonProcessingException {
        return this.mapper.readValue(this.response.body(), new TypeReference<>() {});
    }

    private List<FilaEsperaDTO> deserializarListaGenerica() throws JsonProcessingException {
        return this.mapper.readValue(this.response.body(), new TypeReference<>() {});
    }

    /**
     * Validaciones de parámetros de entrada
     */
    private void validarFilaEspera(FilaEsperaDTO filaEspera) {
        if (filaEspera == null) {
            throw new IllegalArgumentException("La fila de espera no puede ser null");
        }
        
        // Validaciones de negocio del lado cliente
        if (filaEspera.getUsuario() == null || filaEspera.getUsuario().getIdUsuario() == null) {
            throw new IllegalArgumentException("El usuario es obligatorio");
        }
        
        if (filaEspera.getLocal() == null || filaEspera.getLocal().getIdLocal() == null) {
            throw new IllegalArgumentException("El local es obligatorio");
        }
        
        if (filaEspera.getFechaHoraDeseada() == null) {
            throw new IllegalArgumentException("La fecha y hora deseada son obligatorias");
        }
        
        if (filaEspera.getCantidadPersonas() == null || filaEspera.getCantidadPersonas() <= 0) {
            throw new IllegalArgumentException("La cantidad de personas debe ser mayor a 0");
        }
        
        if (filaEspera.getCantidadPersonas() > 100) {
            throw new IllegalArgumentException("No se permiten solicitudes para más de 100 personas");
        }
        
        // Validar que la fecha sea futura
        Date ahora = new Date();
        if (filaEspera.getFechaHoraDeseada().before(ahora)) {
            throw new IllegalArgumentException("La fecha deseada debe ser futura");
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

    private void validarParametros(FilaEsperaParametros parametros) {
        if (parametros == null) {
            throw new IllegalArgumentException("Los parámetros de búsqueda no pueden ser null");
        }
    }

    /**
     * Manejo de respuestas HTTP con mensajes de error específicos
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

    /**
     * Inserta una nueva solicitud en la fila de espera
     */
    public FilaEsperaDTO insertar(FilaEsperaDTO filaEspera) throws IOException, InterruptedException {
        try {
            validarFilaEspera(filaEspera);
            
            // Establecer fecha de registro si no está presente
            if (filaEspera.getFechaRegistro() == null) {
                filaEspera.setFechaRegistro(new Date());
            }
            
            String jsonRequest = serializar(filaEspera);
            this.crearHttpClient();
            this.crearHttpRequestPOST(jsonRequest, null);
            this.enviarRequest();
            this.cerrarHttpClient();

            validarRespuestaHTTP();

            if (response.statusCode() == Response.Status.CREATED.getStatusCode()) {
                return deserializarFilaEspera();
            }
            throw new IOException("No se pudo crear la solicitud de fila de espera");
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (JsonProcessingException e) {
            throw new IOException("Error al procesar datos JSON: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al insertar fila de espera: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene una solicitud específica de fila de espera
     */
    public FilaEsperaDTO obtenerPorId(Integer idFila, Integer idUsuario) throws IOException, InterruptedException {
        try {
            validarId(idFila, "ID de fila");
            validarId(idUsuario, "ID de usuario");
            
            this.crearHttpClient();
            this.crearHttpRequestGET(idFila + "/" + idUsuario);
            this.enviarRequest();
            this.cerrarHttpClient();
            
            validarRespuestaHTTP();
            return deserializarFilaEspera();
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (JsonProcessingException e) {
            throw new IOException("Error al procesar datos JSON: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al obtener fila de espera: " + e.getMessage(), e);
        }
    }

    /**
     * Lista solicitudes de fila de espera con parámetros
     */
    public ArrayList<FilaEsperaDTO> listar(FilaEsperaParametros parametros) throws IOException, InterruptedException {
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
            throw new IOException("Error al listar fila de espera: " + e.getMessage(), e);
        }
    }

    /**
     * Lista solicitudes pendientes ordenadas por prioridad
     */
    public List<FilaEsperaDTO> listarPendientesPorPrioridad(Integer localId) throws IOException, InterruptedException {
        try {
            if (localId != null) {
                validarId(localId, "ID de local");
            }
            
            String urlExtra = "pendientes";
            if (localId != null) {
                urlExtra += "?localId=" + localId;
            }
            
            this.crearHttpClient();
            this.crearHttpRequestGET(urlExtra);
            this.enviarRequest();
            this.cerrarHttpClient();
            
            validarRespuestaHTTP();
            return deserializarListaGenerica();
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (JsonProcessingException e) {
            throw new IOException("Error al procesar datos JSON: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al listar pendientes: " + e.getMessage(), e);
        }
    }

    /**
     * Busca la siguiente solicitud compatible
     */
    public FilaEsperaDTO buscarSiguienteCompatible(Integer localId, Date fechaHora, 
                                                  TipoReserva tipoReserva, Integer tipoMesaId) 
                                                  throws IOException, InterruptedException {
        try {
            validarId(localId, "ID de local");
            
            StringBuilder urlBuilder = new StringBuilder("siguiente?localId=" + localId);
            
            if (fechaHora != null) {
                urlBuilder.append("&fechaHora=").append(fechaHora.getTime());
            }
            
            if (tipoReserva != null) {
                urlBuilder.append("&tipoReserva=").append(tipoReserva.name());
            }
            
            if (tipoMesaId != null) {
                urlBuilder.append("&tipoMesaId=").append(tipoMesaId);
            }
            
            this.crearHttpClient();
            this.crearHttpRequestGET(urlBuilder.toString());
            this.enviarRequest();
            this.cerrarHttpClient();
            
            // Permitir respuesta 404 para casos donde no hay siguiente compatible
            if (response.statusCode() == 404) {
                return null;
            }
            
            validarRespuestaHTTP();
            return deserializarFilaEspera();
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (JsonProcessingException e) {
            throw new IOException("Error al procesar datos JSON: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al buscar siguiente compatible: " + e.getMessage(), e);
        }
    }

    /**
     * Notifica al siguiente en la fila de espera
     */
    public boolean notificarSiguiente(NotificacionFilaRequest request) throws IOException, InterruptedException {
        try {
            if (request == null) {
                throw new IllegalArgumentException("Los datos de notificación son requeridos");
            }
            
            validarId(request.getLocalId(), "ID de local en notificación");
            
            String jsonRequest = serializar(request);
            this.crearHttpClient();
            this.crearHttpRequestPUT(jsonRequest, "notificar");
            this.enviarRequest();
            this.cerrarHttpClient();

            // Permitir respuesta 404 para casos donde no hay nadie que notificar
            if (response.statusCode() == 404) {
                return false;
            }
            
            validarRespuestaHTTP();
            return response.statusCode() == Response.Status.OK.getStatusCode();
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (JsonProcessingException e) {
            throw new IOException("Error al procesar datos JSON: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al notificar siguiente: " + e.getMessage(), e);
        }
    }

    /**
     * Método de conveniencia para notificar siguiente
     */
    public boolean notificarSiguiente(Integer localId, Date fechaHora, TipoReserva tipoReserva, Integer tipoMesaId) 
                                     throws IOException, InterruptedException {
        String tipoReservaStr = tipoReserva != null ? tipoReserva.name() : null;
        NotificacionFilaRequest request = new NotificacionFilaRequest(localId, fechaHora, tipoReservaStr, tipoMesaId);
        return notificarSiguiente(request);
    }

    /**
     * Modifica una solicitud de fila de espera
     */
    public Integer modificar(FilaEsperaDTO filaEspera) throws IOException, InterruptedException {
        try {
            validarFilaEspera(filaEspera);
            validarId(filaEspera.getIdFila(), "ID de fila");
            
            String jsonRequest = serializar(filaEspera);
            this.crearHttpClient();
            this.crearHttpRequestPUT(jsonRequest, null);
            this.enviarRequest();
            this.cerrarHttpClient();

            validarRespuestaHTTP();

            if (response.statusCode() == Response.Status.OK.getStatusCode()) {
                return filaEspera.getIdFila();
            }
            return 0;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (JsonProcessingException e) {
            throw new IOException("Error al procesar datos JSON: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al modificar fila de espera: " + e.getMessage(), e);
        }
    }

    /**
     * Elimina (cancela) una solicitud de fila de espera
     */
    public Integer eliminar(FilaEsperaDTO filaEspera) throws IOException, InterruptedException {
        try {
            if (filaEspera == null) {
                throw new IllegalArgumentException("La fila de espera no puede ser null");
            }
            validarId(filaEspera.getIdFila(), "ID de fila");
            
            String jsonRequest = serializar(filaEspera);
            this.crearHttpClient();
            this.crearHttpRequestPUT(jsonRequest, "eliminar");
            this.enviarRequest();
            this.cerrarHttpClient();

            validarRespuestaHTTP();

            if (response.statusCode() == Response.Status.NO_CONTENT.getStatusCode()) {
                return filaEspera.getIdFila();
            }
            return 0;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (JsonProcessingException e) {
            throw new IOException("Error al procesar datos JSON: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al eliminar fila de espera: " + e.getMessage(), e);
        }
    }

    /**
     * Confirma la disponibilidad notificada
     */
    public Integer confirmarDisponibilidad(Integer idFila, Integer idUsuario) throws IOException, InterruptedException {
        try {
            validarId(idFila, "ID de fila");
            validarId(idUsuario, "ID de usuario");
            
            this.crearHttpClient();
            this.crearHttpRequestPUTSinBody("confirmar/" + idFila + "/" + idUsuario);
            this.enviarRequest();
            this.cerrarHttpClient();

            validarRespuestaHTTP();

            if (response.statusCode() == Response.Status.OK.getStatusCode()) {
                return idFila;
            }
            return 0;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new IOException("Error al confirmar disponibilidad: " + e.getMessage(), e);
        }
    }
}
