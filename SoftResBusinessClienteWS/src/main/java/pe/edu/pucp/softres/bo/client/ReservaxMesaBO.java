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
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.core.Response;
import pe.edu.pucp.softres.model.EstadoMesa;
import pe.edu.pucp.softres.model.MesaDTO;
import pe.edu.pucp.softres.model.ReservaDTO;

/**
 *
 * @author Rosse
 */
public class ReservaxMesaBO {

    private final String url;
    private final ObjectMapper mapper;
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

    /**
     * DTO para respuesta de verificación de disponibilidad
     */
    public static class DisponibilidadResponse {
        private boolean disponible;
        private String mensaje;

        public DisponibilidadResponse() {}

        public boolean isDisponible() {
            return disponible;
        }

        public void setDisponible(boolean disponible) {
            this.disponible = disponible;
        }

        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }
    }

    /**
     * DTO para solicitudes de cambio de estado
     */
    public static class CambioEstadoRequest {
        private String nuevoEstado;

        public CambioEstadoRequest() {}

        public CambioEstadoRequest(String nuevoEstado) {
            this.nuevoEstado = nuevoEstado;
        }

        public String getNuevoEstado() {
            return nuevoEstado;
        }

        public void setNuevoEstado(String nuevoEstado) {
            this.nuevoEstado = nuevoEstado;
        }
    }

    /**
     * DTO para respuesta de estadísticas
     */
    public static class EstadisticasResponse {
        private int totalMesas;
        private int mesasDisponibles;
        private int mesasReservadas;
        private int mesasEnUso;
        private double porcentajeOcupacion;

        public EstadisticasResponse() {}

        public int getTotalMesas() {
            return totalMesas;
        }

        public void setTotalMesas(int totalMesas) {
            this.totalMesas = totalMesas;
        }

        public int getMesasDisponibles() {
            return mesasDisponibles;
        }

        public void setMesasDisponibles(int mesasDisponibles) {
            this.mesasDisponibles = mesasDisponibles;
        }

        public int getMesasReservadas() {
            return mesasReservadas;
        }

        public void setMesasReservadas(int mesasReservadas) {
            this.mesasReservadas = mesasReservadas;
        }

        public int getMesasEnUso() {
            return mesasEnUso;
        }

        public void setMesasEnUso(int mesasEnUso) {
            this.mesasEnUso = mesasEnUso;
        }

        public double getPorcentajeOcupacion() {
            return porcentajeOcupacion;
        }

        public void setPorcentajeOcupacion(double porcentajeOcupacion) {
            this.porcentajeOcupacion = porcentajeOcupacion;
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

    private DisponibilidadResponse deserializarDisponibilidad() throws JsonProcessingException {
        return this.mapper.readValue(this.response.body(), DisponibilidadResponse.class);
    }

    private List<MesaDTO> deserializarListaMesas() throws JsonProcessingException {
        return this.mapper.readValue(this.response.body(), new TypeReference<>() {});
    }

    private EstadisticasResponse deserializarEstadisticas() throws JsonProcessingException {
        return this.mapper.readValue(this.response.body(), EstadisticasResponse.class);
    }

    /**
     * Validaciones de parámetros de entrada
     */
    private void validarReserva(ReservaDTO reserva) {
        if (reserva == null) {
            throw new IllegalArgumentException("La reserva no puede ser null");
        }
        
        if (reserva.getLocal() == null || reserva.getLocal().getIdLocal() == null) {
            throw new IllegalArgumentException("El local es obligatorio para verificar disponibilidad");
        }
        
        if (reserva.getTipoMesa() == null || reserva.getTipoMesa().getIdTipoMesa() == null) {
            throw new IllegalArgumentException("El tipo de mesa es obligatorio");
        }
        
        if (reserva.getCantidadPersonas() == null || reserva.getCantidadPersonas() <= 0) {
            throw new IllegalArgumentException("La cantidad de personas debe ser mayor a 0");
        }
        
        if (reserva.getCantidadPersonas() > 100) {
            throw new IllegalArgumentException("No se permiten reservas para más de 100 personas");
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
                case 409:
                    throw new RuntimeException("Conflicto - " + responseBody);
                case 500:
                    throw new IOException("Error interno del servidor - " + responseBody);
                default:
                    throw new IOException(mensaje);
            }
        }
    }

    /**
     * Verifica la disponibilidad de mesas para una reserva
     */
    public boolean verificarDisponibilidad(ReservaDTO reserva) throws IOException, InterruptedException {
        try {
            validarReserva(reserva);
            
            String jsonRequest = serializar(reserva);
            this.crearHttpClient();
            this.crearHttpRequestPOST(jsonRequest, "verificarDisponibilidad");
            this.enviarRequest();
            this.cerrarHttpClient();

            validarRespuestaHTTP();

            if (response.statusCode() == Response.Status.OK.getStatusCode()) {
                DisponibilidadResponse disponibilidad = deserializarDisponibilidad();
                return disponibilidad.isDisponible();
            }
            return false;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (JsonProcessingException e) {
            throw new IOException("Error al procesar datos JSON: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al verificar disponibilidad: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene información detallada de disponibilidad
     */
    public DisponibilidadResponse obtenerDetalleDisponibilidad(ReservaDTO reserva) throws IOException, InterruptedException {
        try {
            validarReserva(reserva);
            
            String jsonRequest = serializar(reserva);
            this.crearHttpClient();
            this.crearHttpRequestPOST(jsonRequest, "verificarDisponibilidad");
            this.enviarRequest();
            this.cerrarHttpClient();

            validarRespuestaHTTP();
            return deserializarDisponibilidad();
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (JsonProcessingException e) {
            throw new IOException("Error al procesar datos JSON: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al obtener detalle de disponibilidad: " + e.getMessage(), e);
        }
    }

    /**
     * Asigna mesas a una reserva específica
     */
    public boolean asignarMesas(Integer reservaId, ReservaDTO reserva) throws IOException, InterruptedException {
        try {
            validarId(reservaId, "ID de reserva");
            validarReserva(reserva);
            
            // Validación adicional: verificar disponibilidad antes de asignar
            if (!verificarDisponibilidad(reserva)) {
                throw new RuntimeException("No hay mesas disponibles para esta reserva");
            }
            
            String jsonRequest = serializar(reserva);
            this.crearHttpClient();
            this.crearHttpRequestPOST(jsonRequest, "asignar/" + reservaId);
            this.enviarRequest();
            this.cerrarHttpClient();

            // Permitir respuesta 409 (Conflict) para casos de no disponibilidad
            if (response.statusCode() == 409) {
                return false;
            }
            
            validarRespuestaHTTP();
            return response.statusCode() == Response.Status.OK.getStatusCode();
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (JsonProcessingException e) {
            throw new IOException("Error al procesar datos JSON: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al asignar mesas: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene las mesas asignadas a una reserva
     */
    public List<MesaDTO> obtenerMesasDeReserva(Integer reservaId) throws IOException, InterruptedException {
        try {
            validarId(reservaId, "ID de reserva");
            
            this.crearHttpClient();
            this.crearHttpRequestGET("obtenerMesas/" + reservaId);
            this.enviarRequest();
            this.cerrarHttpClient();
            
            validarRespuestaHTTP();
            return deserializarListaMesas();
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (JsonProcessingException e) {
            throw new IOException("Error al procesar datos JSON: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al obtener mesas de reserva: " + e.getMessage(), e);
        }
    }

    /**
     * Cambia el estado de las mesas de una reserva
     */
    public boolean cambiarEstadoMesas(Integer reservaId, String nuevoEstado) throws IOException, InterruptedException {
        try {
            validarId(reservaId, "ID de reserva");
            validarEstado(nuevoEstado);
            
            CambioEstadoRequest request = new CambioEstadoRequest(nuevoEstado.toUpperCase());
            String jsonRequest = serializar(request);
            
            this.crearHttpClient();
            this.crearHttpRequestPUT(jsonRequest, "cambiarEstado/" + reservaId);
            this.enviarRequest();
            this.cerrarHttpClient();

            // Permitir respuesta 404 para casos donde no hay mesas asociadas
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
            throw new IOException("Error al cambiar estado de mesas: " + e.getMessage(), e);
        }
    }

    /**
     * Cambia el estado de las mesas usando el enum
     */
    public boolean cambiarEstadoMesas(Integer reservaId, EstadoMesa nuevoEstado) throws IOException, InterruptedException {
        if (nuevoEstado == null) {
            throw new IllegalArgumentException("El estado no puede ser null");
        }
        return cambiarEstadoMesas(reservaId, nuevoEstado.name());
    }

    /**
     * Libera las mesas asociadas a una reserva
     */
    public boolean liberarMesas(Integer reservaId) throws IOException, InterruptedException {
        try {
            validarId(reservaId, "ID de reserva");
            
            this.crearHttpClient();
            this.crearHttpRequestPUTSinBody("liberar/" + reservaId);
            this.enviarRequest();
            this.cerrarHttpClient();

            // Permitir respuesta 404 para casos donde no hay mesas asociadas
            if (response.statusCode() == 404) {
                return false;
            }
            
            validarRespuestaHTTP();
            return response.statusCode() == Response.Status.OK.getStatusCode();
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new IOException("Error al liberar mesas: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene estadísticas de ocupación de un local
     */
    public EstadisticasResponse obtenerEstadisticasOcupacion(Integer localId) throws IOException, InterruptedException {
        try {
            validarId(localId, "ID de local");
            
            this.crearHttpClient();
            this.crearHttpRequestGET("estadisticas/" + localId);
            this.enviarRequest();
            this.cerrarHttpClient();
            
            validarRespuestaHTTP();
            return deserializarEstadisticas();
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (JsonProcessingException e) {
            throw new IOException("Error al procesar datos JSON: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al obtener estadísticas: " + e.getMessage(), e);
        }
    }

    /**
     * Método de conveniencia para verificar si hay suficiente capacidad disponible
     */
    public boolean hayCapacidadSuficiente(Integer localId, Integer capacidadNecesaria) throws IOException, InterruptedException {
        if (capacidadNecesaria == null || capacidadNecesaria <= 0) {
            return false;
        }
        
        try {
            // Crear una reserva temporal para verificar disponibilidad
            ReservaDTO reservaTemp = new ReservaDTO();
            
            // Configurar local
            pe.edu.pucp.softres.model.LocalDTO local = new pe.edu.pucp.softres.model.LocalDTO();
            local.setIdLocal(localId);
            reservaTemp.setLocal(local);
            
            // Configurar tipo de mesa genérico (asumiendo que 1 es un tipo válido)
            pe.edu.pucp.softres.model.TipoMesaDTO tipoMesa = new pe.edu.pucp.softres.model.TipoMesaDTO();
            tipoMesa.setIdTipoMesa(1); // Tipo genérico
            reservaTemp.setTipoMesa(tipoMesa);
            
            reservaTemp.setCantidadPersonas(capacidadNecesaria);
            
            return verificarDisponibilidad(reservaTemp);
        } catch (Exception e) {
            // En caso de error, asumir que no hay capacidad
            return false;
        }
    }

    /**
     * Método de conveniencia para gestionar el ciclo completo de una reserva
     */
    public boolean procesarReservaCompleta(Integer reservaId, ReservaDTO reserva) throws IOException, InterruptedException {
        try {
            // 1. Verificar disponibilidad
            if (!verificarDisponibilidad(reserva)) {
                throw new RuntimeException("No hay disponibilidad para esta reserva");
            }
            
            // 2. Asignar mesas
            if (!asignarMesas(reservaId, reserva)) {
                throw new RuntimeException("No se pudieron asignar las mesas");
            }
            
            // 3. Cambiar estado a reservado
            if (!cambiarEstadoMesas(reservaId, EstadoMesa.RESERVADA)) {
                // Si falla el cambio de estado, intentar liberar las mesas asignadas
                liberarMesas(reservaId);
                throw new RuntimeException("No se pudo cambiar el estado de las mesas");
            }
            
            return true;
        } catch (Exception e) {
            // En caso de error, intentar limpiar cualquier asignación parcial
            try {
                liberarMesas(reservaId);
            } catch (Exception cleanupError) {
                // Log del error de limpieza, pero no relanzar
                System.err.println("Error durante limpieza: " + cleanupError.getMessage());
            }
            throw e;
        }
    }

    /**
     * Método de conveniencia para finalizar una reserva (liberar mesas)
     */
    public boolean finalizarReserva(Integer reservaId) throws IOException, InterruptedException {
        try {
            // 1. Cambiar estado a disponible
            boolean estadoCambiado = cambiarEstadoMesas(reservaId, EstadoMesa.DISPONIBLE);
            
            // 2. Liberar la asociación reserva-mesa
            boolean mesasLiberadas = liberarMesas(reservaId);
            
            // Se considera exitoso si al menos una operación fue exitosa
            return estadoCambiado || mesasLiberadas;
        } catch (Exception e) {
            throw new IOException("Error al finalizar reserva: " + e.getMessage(), e);
        }
    }
}