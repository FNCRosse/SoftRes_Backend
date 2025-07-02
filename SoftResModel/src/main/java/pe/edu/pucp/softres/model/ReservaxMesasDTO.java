/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.model;

/**
 * DTO para la tabla de relación RES_RESERVAS_x_MESAS
 * Representa la asignación de mesas específicas a una reserva
 * Esta clase maneja la relación many-to-many entre reservas y mesas
 * 
 * @author Rosse
 */
public class ReservaxMesasDTO {
    
    private ReservaDTO reserva;
    private MesaDTO mesa;
    
    // Constructor por defecto
    public ReservaxMesasDTO(){
        this.reserva = null;
        this.mesa = null;
    }

    // Constructor completo
    public ReservaxMesasDTO(ReservaDTO reserva, MesaDTO mesa) {
        this.reserva = reserva;
        this.mesa = mesa;
    }

    // Constructor con IDs
    public ReservaxMesasDTO(Integer reservaId, Integer mesaId) {
        this.reserva = new ReservaDTO();
        this.reserva.setIdReserva(reservaId);
        this.mesa = new MesaDTO();
        this.mesa.setIdMesa(mesaId);
    }

    // Constructor copia
    public ReservaxMesasDTO(ReservaxMesasDTO original) {
        this.reserva = original.reserva != null ? new ReservaDTO(original.reserva) : null;
        this.mesa = original.mesa != null ? new MesaDTO(original.mesa) : null;
    }

    public ReservaDTO getReserva() {
        return reserva;
    }

    public void setReserva(ReservaDTO reserva) {
        this.reserva = reserva;
    }

    public MesaDTO getMesa() {
        return mesa;
    }

    public void setMesa(MesaDTO mesa) {
        this.mesa = mesa;
    }
    
    // Métodos de conveniencia para trabajar con IDs
    
    /**
     * Obtiene el ID de la reserva
     * @return ID de la reserva o null si no hay reserva asignada
     */
    public Integer getReservaId() {
        return this.reserva != null ? this.reserva.getIdReserva() : null;
    }
    
    /**
     * Establece el ID de la reserva creando un objeto ReservaDTO básico
     * @param reservaId ID de la reserva
     */
    public void setReservaId(Integer reservaId) {
        if (reservaId != null) {
            if (this.reserva == null) {
                this.reserva = new ReservaDTO();
            }
            this.reserva.setIdReserva(reservaId);
        } else {
            this.reserva = null;
        }
    }
    
    /**
     * Obtiene el ID de la mesa
     * @return ID de la mesa o null si no hay mesa asignada
     */
    public Integer getMesaId() {
        return this.mesa != null ? this.mesa.getIdMesa() : null;
    }
    
    /**
     * Establece el ID de la mesa creando un objeto MesaDTO básico
     * @param mesaId ID de la mesa
     */
    public void setMesaId(Integer mesaId) {
        if (mesaId != null) {
            if (this.mesa == null) {
                this.mesa = new MesaDTO();
            }
            this.mesa.setIdMesa(mesaId);
        } else {
            this.mesa = null;
        }
    }

    /**
     * Método de utilidad para verificar si la asignación es válida
     * @return true si tanto la reserva como la mesa están asignadas
     */
    public boolean esAsignacionValida() {
        return this.reserva != null && this.mesa != null && 
               this.reserva.getIdReserva() != null && this.mesa.getIdMesa() != null;
    }

    /**
     * Método de utilidad para verificar si la mesa puede acomodar la reserva
     * @return true si la mesa tiene capacidad suficiente para la reserva
     */
    public boolean mesaTieneCapacidadSuficiente() {
        if (this.reserva == null || this.mesa == null || 
            this.reserva.getCantidadPersonas() == null || this.mesa.getCapacidad() == null) {
            return false;
        }
        return this.mesa.getCapacidad() >= this.reserva.getCantidadPersonas();
    }

    /**
     * Método de utilidad para verificar si la mesa está disponible
     * @return true si la mesa está en estado DISPONIBLE
     */
    public boolean mesaEstaDisponible() {
        return this.mesa != null && this.mesa.estaDisponible();
    }

    /**
     * Método de utilidad para verificar si la reserva está confirmada
     * @return true si la reserva está en estado CONFIRMADA
     */
    public boolean reservaEstaConfirmada() {
        return this.reserva != null && this.reserva.estaConfirmada();
    }

    /**
     * Método de utilidad para obtener información de la asignación
     * @return String con información básica de la asignación
     */
    public String getInfoAsignacion() {
        if (!esAsignacionValida()) {
            return "Asignación inválida";
        }
        
        return String.format("Reserva %d - Mesa %s (Capacidad: %d)", 
                this.reserva.getIdReserva(),
                this.mesa.getNumeroMesa(),
                this.mesa.getCapacidad());
    }

    @Override
    public String toString() {
        return "ReservaxMesasDTO{" +
                "reservaId=" + getReservaId() +
                ", mesaId=" + getMesaId() +
                ", numeroMesa=" + (mesa != null ? mesa.getNumeroMesa() : "null") +
                ", capacidadMesa=" + (mesa != null ? mesa.getCapacidad() : "null") +
                ", cantidadPersonas=" + (reserva != null ? reserva.getCantidadPersonas() : "null") +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        ReservaxMesasDTO that = (ReservaxMesasDTO) obj;
        
        Integer thisReservaId = getReservaId();
        Integer thisMesaId = getMesaId();
        Integer thatReservaId = that.getReservaId();
        Integer thatMesaId = that.getMesaId();
        
        return thisReservaId != null && thisMesaId != null &&
               thisReservaId.equals(thatReservaId) && thisMesaId.equals(thatMesaId);
    }

    @Override
    public int hashCode() {
        Integer reservaId = getReservaId();
        Integer mesaId = getMesaId();
        
        int result = reservaId != null ? reservaId.hashCode() : 0;
        result = 31 * result + (mesaId != null ? mesaId.hashCode() : 0);
        return result;
    }
}
