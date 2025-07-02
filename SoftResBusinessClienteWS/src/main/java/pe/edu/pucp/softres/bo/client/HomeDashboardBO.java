/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.bo.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author BryanGnr
 */
public class HomeDashboardBO {

    private String url;
    private ObjectMapper mapper;
    private HttpClient client;
    private HttpRequest request;
    private HttpResponse<String> response;

    public HomeDashboardBO() {
        this.url = "http://localhost:8080/SoftResBusinessServidorWS/resources/dashboard"; // URL del endpoint de tu RESTful Dashboard
        this.mapper = new ObjectMapper();
        this.client = null;
        this.request = null;
        this.response = null;
    }

    private void crearHttpClient() {
        this.client = HttpClient.newHttpClient();
    }

    private void cerrarHttpClient() {
        // this.client.close(); // Si es necesario
    }

    private void crearHttpRequestGET(String endpoint) {
        String urlGET = this.url + "/" + endpoint;
        this.request = HttpRequest.newBuilder()
                .uri(URI.create(urlGET))
                .GET()
                .build();
    }

    private void enviarRequest() throws IOException, InterruptedException {
        this.response = this.client.send(this.request, HttpResponse.BodyHandlers.ofString());
    }

    private Integer deserializarInteger() throws JsonProcessingException {
        return mapper.readValue(this.response.body(), Integer.class);
    }

    private Double deserializarDouble() throws JsonProcessingException {
        return mapper.readValue(this.response.body(), Double.class);
    }

    private List<Integer> deserializarLista(Integer[] array) throws JsonProcessingException {
        return Arrays.asList(array);
    }

    public Integer obtenerCantidadReservasDiarias() throws IOException, InterruptedException {
        this.crearHttpClient();
        this.crearHttpRequestGET("reservasDiarias");
        this.enviarRequest();
        this.cerrarHttpClient();
        return this.deserializarInteger();
    }

    public Integer obtenerCantidadReservasSemanales() throws IOException, InterruptedException {
        this.crearHttpClient();
        this.crearHttpRequestGET("reservasSemanales");
        this.enviarRequest();
        this.cerrarHttpClient();
        return this.deserializarInteger();
    }

    public Double obtenerPorcentajeOcupacionMesas() throws IOException, InterruptedException {
        this.crearHttpClient();
        this.crearHttpRequestGET("porcentajeOcupacionMesas");
        this.enviarRequest();
        this.cerrarHttpClient();
        return this.deserializarDouble();
    }

    public Integer obtenerCantidadCancelacionesRecientes() throws IOException, InterruptedException {
        this.crearHttpClient();
        this.crearHttpRequestGET("cancelacionesRecientes");
        this.enviarRequest();
        this.cerrarHttpClient();
        return this.deserializarInteger();
    }
}
