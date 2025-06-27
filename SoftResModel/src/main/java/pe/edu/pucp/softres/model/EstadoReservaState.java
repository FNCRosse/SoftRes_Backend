/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.pucp.softres.model;

/**
 *
 * @author frank
 */
public interface EstadoReservaState {
    void confirmar(ReservaDTO reserva);
    void cancelar(ReservaDTO reserva);
    void notificar(ReservaDTO reserva);
    void pendiente(ReservaDTO reserva);
}
