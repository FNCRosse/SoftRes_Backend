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
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.core.Response;
import pe.edu.pucp.softres.model.EstadoMesa;
import pe.edu.pucp.softres.model.MesaDTO;
import pe.edu.pucp.softres.parametros.MesaParametros;

/**
 * Business Object para operaciones de Mesa en el cliente
 * Conecta con el servicio REST del servidor para realizar operaciones CRUD
 * Aplica reglas de negocio del lado cliente antes de enviar al servidor
 * 
 * @author Rosse
 */
public class MesaBO {

    private final String url;
    private final ObjectMapper mapper;
    private HttpClient client;
    private HttpRequest request;
    private HttpResponse<String> response;

    public MesaBO() {
        this.url = "http://localhost:8080/SoftResBusinessServidorWS/resources/Mesa";
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

    private MesaDTO deserializarMesa() throws JsonProcessingException {
        return this.mapper.readValue(this.response.body(), MesaDTO.class);
    }

    private List<MesaDTO> deserializarLista() throws JsonProcessingException {
        return this.mapper.readValue(this.response.body(), new TypeReference<>() {});
    }

    /**
     * Validaciones de parámetros de entrada
     */
    private void validarMesa(MesaDTO mesa) {
        if (mesa == null) {
            throw new IllegalArgumentException("La mesa no puede ser null");
        }
        
        // Validaciones de negocio del lado cliente
        if (mesa.getNumeroMesa() == null || mesa.getNumeroMesa().trim().isEmpty()) {
            throw new IllegalArgumentException("El número de mesa es obligatorio");
        }
        
        if (mesa.getCapacidad() == null || mesa.getCapacidad() <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor a 0");
        }
        
        if (mesa.getCapacidad() > 20) {
            throw new IllegalArgumentException("La capacidad máxima permitida es 20 personas");
        }
        
        if (mesa.getLocal() == null || mesa.getLocal().getIdLocal() == null) {
            throw new IllegalArgumentException("El local es obligatorio");
        }
        
        if (mesa.getTipoMesa() == null || mesa.getTipoMesa().getIdTipoMesa() == null) {
            throw new IllegalArgumentException("El tipo de mesa es obligatorio");
        }
        
        // Validar formato del número de mesa (debe ser alfanumérico)
        if (!mesa.getNumeroMesa().matches("^[A-Za-z0-9\\-_]+$")) {
            throw new IllegalArgumentException("El número de mesa solo puede contener letras, números, guiones y guiones bajos");
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

    private void validarParametros(MesaParametros parametros) {
        if (parametros == null) {
            throw new IllegalArgumentException("Los parámetros de búsqueda no pueden ser null");
        }
    }

    private void validarEstado(String estado) {
        if (estado == null || estado.trim().isEmpty()) {
            throw new IllegalArgumentException("El estado es obligatorio");
        }
        
        try {
            EstadoMesa.valueOf(estado.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Estado inválido. Estados válidos: DISPONIBLE, RESERVADA, EN_USO, EN_MANTENIMIENTO, DESECHADA");
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
     * Inserta una nueva mesa
     */
    public MesaDTO insertar(MesaDTO mesa) throws IOException, InterruptedException {
        try {
            validarMesa(mesa);
            
            // Establecer valores por defecto si no están presentes
            if (mesa.getEstado() == null) {
                mesa.setEstado(EstadoMesa.DISPONIBLE);
            }
            
            if (mesa.getFechaCreacion() == null) {
                mesa.setFechaCreacion(new Date());
            }
            
            String jsonRequest = serializar(mesa);
            this.crearHttpClient();
            this.crearHttpRequestPOST(jsonRequest, null);
            this.enviarRequest();
            this.cerrarHttpClient();

            validarRespuestaHTTP();

            if (response.statusCode() == Response.Status.CREATED.getStatusCode()) {
                return deserializarMesa();
            }
            throw new IOException("No se pudo crear la mesa");
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (JsonProcessingException e) {
            throw new IOException("Error al procesar datos JSON: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al insertar mesa: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene una mesa por ID
     */
    public MesaDTO obtenerPorId(Integer mesaId) throws IOException, InterruptedException {
        try {
            validarId(mesaId, "ID de mesa");
            
            this.crearHttpClient();
            this.crearHttpRequestGET(mesaId.toString());
            this.enviarRequest();
            this.cerrarHttpClient();
            
            validarRespuestaHTTP();
            return deserializarMesa();
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (JsonProcessingException e) {
            throw new IOException("Error al procesar datos JSON: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al obtener mesa: " + e.getMessage(), e);
        }
    }

    /**
     * Lista mesas con parámetros
     */
    public List<MesaDTO> listar(MesaParametros parametros) throws IOException, InterruptedException {
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
            throw new IOException("Error al listar mesas: " + e.getMessage(), e);
        }
    }

    /**
     * Lista mesas disponibles
     */
    public List<MesaDTO> listarDisponibles(Integer localId, Integer tipoMesaId) throws IOException, InterruptedException {
        try {
            StringBuilder urlBuilder = new StringBuilder("listar/disponibles");
            boolean primerParametro = true;
            
            if (localId != null) {
                validarId(localId, "ID de local");
                urlBuilder.append(primerParametro ? "?" : "&").append("localId=").append(localId);
                primerParametro = false;
            }
            
            if (tipoMesaId != null) {
                validarId(tipoMesaId, "ID de tipo de mesa");
                urlBuilder.append(primerParametro ? "?" : "&").append("tipoMesaId=").append(tipoMesaId);
            }
            
            this.crearHttpClient();
            this.crearHttpRequestGET(urlBuilder.toString());
            this.enviarRequest();
            this.cerrarHttpClient();
            
            validarRespuestaHTTP();
            return deserializarLista();
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (JsonProcessingException e) {
            throw new IOException("Error al procesar datos JSON: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al listar mesas disponibles: " + e.getMessage(), e);
        }
    }

    /**
     * Lista mesas en uso
     */
    public List<MesaDTO> listarEnUso(Integer localId) throws IOException, InterruptedException {
        try {
            String urlExtra = "listar/ocupadas";
            if (localId != null) {
                validarId(localId, "ID de local");
                urlExtra += "?localId=" + localId;
            }
            
            this.crearHttpClient();
            this.crearHttpRequestGET(urlExtra);
            this.enviarRequest();
            this.cerrarHttpClient();
            
            validarRespuestaHTTP();
            return deserializarLista();
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (JsonProcessingException e) {
            throw new IOException("Error al procesar datos JSON: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al listar mesas en uso: " + e.getMessage(), e);
        }
    }

    /**
     * Lista mesas reservadas
     */
    public List<MesaDTO> listarReservadas(Integer localId) throws IOException, InterruptedException {
        try {
            String urlExtra = "listar/reservadas";
            if (localId != null) {
                validarId(localId, "ID de local");
                urlExtra += "?localId=" + localId;
            }
            
            this.crearHttpClient();
            this.crearHttpRequestGET(urlExtra);
            this.enviarRequest();
            this.cerrarHttpClient();
            
            validarRespuestaHTTP();
            return deserializarLista();
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (JsonProcessingException e) {
            throw new IOException("Error al procesar datos JSON: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al listar mesas reservadas: " + e.getMessage(), e);
        }
    }

    /**
     * Modifica una mesa
     */
    public Integer modificar(MesaDTO mesa) throws IOException, InterruptedException {
        try {
            validarMesa(mesa);
            validarId(mesa.getIdMesa(), "ID de mesa");
            
            // Establecer fecha de modificación
            mesa.setFechaModificacion(new Date());
            
            String jsonRequest = serializar(mesa);
            this.crearHttpClient();
            this.crearHttpRequestPUT(jsonRequest, null);
            this.enviarRequest();
            this.cerrarHttpClient();

            validarRespuestaHTTP();

            if (response.statusCode() == Response.Status.OK.getStatusCode()) {
                return mesa.getIdMesa();
            }
            return 0;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (JsonProcessingException e) {
            throw new IOException("Error al procesar datos JSON: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al modificar mesa: " + e.getMessage(), e);
        }
    }

    /**
     * Cambia el estado de una mesa específica
     */
    public Integer cambiarEstado(Integer mesaId, String estado) throws IOException, InterruptedException {
        try {
            validarId(mesaId, "ID de mesa");
            validarEstado(estado);
            
            this.crearHttpClient();
            this.crearHttpRequestPUTSinBody("cambiarEstado/" + mesaId + "/" + estado.toUpperCase());
            this.enviarRequest();
            this.cerrarHttpClient();

            validarRespuestaHTTP();

            if (response.statusCode() == Response.Status.OK.getStatusCode()) {
                return mesaId;
            }
            return 0;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new IOException("Error al cambiar estado de mesa: " + e.getMessage(), e);
        }
    }

    /**
     * Cambia el estado de una mesa usando el enum
     */
    public Integer cambiarEstado(Integer mesaId, EstadoMesa estado) throws IOException, InterruptedException {
        if (estado == null) {
            throw new IllegalArgumentException("El estado no puede ser null");
        }
        return cambiarEstado(mesaId, estado.name());
    }

    /**
     * Elimina una mesa
     */
    public Integer eliminar(MesaDTO mesa) throws IOException, InterruptedException {
        try {
            if (mesa == null) {
                throw new IllegalArgumentException("La mesa no puede ser null");
            }
            validarId(mesa.getIdMesa(), "ID de mesa");
            
            // Validar que la mesa no esté en uso antes de eliminar
            if (mesa.getEstado() == EstadoMesa.EN_USO) {
                throw new IllegalArgumentException("No se puede eliminar una mesa que está en uso");
            }
            
            if (mesa.getEstado() == EstadoMesa.RESERVADA) {
                throw new IllegalArgumentException("No se puede eliminar una mesa que está reservada");
            }
            
            String jsonRequest = serializar(mesa);
            this.crearHttpClient();
            this.crearHttpRequestPUT(jsonRequest, "eliminar");
            this.enviarRequest();
            this.cerrarHttpClient();

            validarRespuestaHTTP();

            if (response.statusCode() == Response.Status.NO_CONTENT.getStatusCode()) {
                return mesa.getIdMesa();
            }
            return 0;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (JsonProcessingException e) {
            throw new IOException("Error al procesar datos JSON: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al eliminar mesa: " + e.getMessage(), e);
        }
    }

    /**
     * Método de conveniencia para obtener mesas disponibles por capacidad mínima
     */
    public List<MesaDTO> obtenerMesasDisponiblesPorCapacidad(Integer localId, Integer capacidadMinima) 
            throws IOException, InterruptedException {
        List<MesaDTO> mesasDisponibles = listarDisponibles(localId, null);
        
        if (capacidadMinima != null && capacidadMinima > 0) {
            mesasDisponibles.removeIf(mesa -> 
                mesa.getCapacidad() == null || mesa.getCapacidad() < capacidadMinima);
        }
        
        return mesasDisponibles;
    }

    /**
     * Método de conveniencia para verificar disponibilidad por capacidad
     */
    public boolean hayDisponibilidadPorCapacidad(Integer localId, Integer capacidadNecesaria) 
            throws IOException, InterruptedException {
        if (capacidadNecesaria == null || capacidadNecesaria <= 0) {
            return false;
        }
        
        List<MesaDTO> mesasDisponibles = obtenerMesasDisponiblesPorCapacidad(localId, capacidadNecesaria);
        return !mesasDisponibles.isEmpty();
    }
} 