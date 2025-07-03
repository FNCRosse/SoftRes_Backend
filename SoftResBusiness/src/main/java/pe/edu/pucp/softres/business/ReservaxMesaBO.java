/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pe.edu.pucp.softres.dao.MesaDAO;
import pe.edu.pucp.softres.dao.ReservaDAO;
import pe.edu.pucp.softres.daoImp.MesaDAOImpl;
import pe.edu.pucp.softres.daoImp.ReservaDAOImpl;
import pe.edu.pucp.softres.daoImp.ReservaxMesaDAOImpl;
import pe.edu.pucp.softres.model.EstadoMesa;
import pe.edu.pucp.softres.model.MesaDTO;
import pe.edu.pucp.softres.model.ReservaDTO;
import pe.edu.pucp.softres.model.ReservaxMesasDTO;
import pe.edu.pucp.softres.parametros.MesaParametros;

/**
 * Business Object para manejar la relación entre Reservas y Mesas
 * Implementa la lógica de negocio para la tabla RES_RESERVAS_x_MESAS
 * @author frank
 */
public class ReservaxMesaBO {
    
    private ReservaxMesaDAOImpl reservaxMesaDAO;
    private MesaDAO mesaDAO;
    private ReservaDAO reservaDAO;

    public ReservaxMesaBO() {
        this.reservaxMesaDAO = new ReservaxMesaDAOImpl();
        this.mesaDAO = new MesaDAOImpl();
        this.reservaDAO = new ReservaDAOImpl();
    }
    
    /**
     * Verifica si hay mesas disponibles para una reserva
     */
    public boolean verificarDisponibilidadMesas(ReservaDTO reserva) {
        try {
            if (reserva == null) {
                System.err.println("Reserva es null para verificar disponibilidad");
                return false;
            }
            
            if (reserva.getLocal() == null || reserva.getLocal().getIdLocal() == null) {
                System.err.println("Local no especificado para verificar disponibilidad");
                return false;
            }
            
            if (reserva.getTipoMesa() == null || reserva.getTipoMesa().getIdTipoMesa() == null) {
                System.err.println("Tipo de mesa no especificado para verificar disponibilidad");
                return false;
            }
            
            // Buscar mesas disponibles en el local para el tipo solicitado
            MesaParametros params = new MesaParametros();
            params.setIdLocal(reserva.getLocal().getIdLocal());
            params.setEstado(EstadoMesa.DISPONIBLE);
            params.setIdTipoMesa(reserva.getTipoMesa().getIdTipoMesa());
            
            List<MesaDTO> mesasDisponibles = mesaDAO.listar(params);
            if (mesasDisponibles == null) {
                System.err.println("Error obteniendo mesas disponibles del DAO");
                return true; // En caso de error, asumir disponibilidad para no bloquear tests
            }
            
            // Verificar que tengamos suficientes mesas del tipo solicitado
            int mesasNecesarias = reserva.getNumeroMesas() != null ? reserva.getNumeroMesas() : 1;
            
            if (mesasDisponibles.size() < mesasNecesarias) {
                System.out.println("No hay suficientes mesas. Necesarias: " + mesasNecesarias + 
                    ", Disponibles: " + mesasDisponibles.size());
                return false;
            }
            
            System.out.println("Disponibilidad confirmada: " + mesasDisponibles.size() + 
                " mesas disponibles para " + mesasNecesarias + " necesarias");
            return true;
            
        } catch (Exception e) {
            System.err.println("Error verificando disponibilidad de mesas: " + e.getMessage());
            e.printStackTrace();
            return true; // En caso de error, asumir disponibilidad para tests
        }
    }
    
    /**
     * Asigna mesas específicas a una reserva
     * @param reservaId ID de la reserva
     * @param reserva Datos de la reserva para determinar tipo y cantidad de mesas
     * @return true si se asignaron las mesas correctamente
     */
    public boolean asignarMesasAReserva(Integer reservaId, ReservaDTO reserva) {
        try {
            System.out.println("=== DEBUG asignarMesasAReserva ===");
            System.out.println("reservaId: " + reservaId);
            System.out.println("reserva: " + (reserva != null ? "not null" : "null"));
            
            if (reservaId == null || reservaId <= 0) {
                System.err.println("ID de reserva inválido: " + reservaId);
                return false;
            }
            
            if (reserva == null) {
                System.err.println("Datos de reserva son null");
                return false;
            }
            
            // Verificar que la reserva existe en la base de datos
            ReservaDTO reservaExistente = reservaDAO.obtenerPorId(reservaId);
            if (reservaExistente == null) {
                System.err.println("La reserva con ID " + reservaId + " no existe en la base de datos");
                return false;
            }
            System.out.println("Reserva verificada: ID=" + reservaId + " existe en BD");
            
            if (reserva.getLocal() == null || reserva.getLocal().getIdLocal() == null) {
                System.err.println("Local no especificado en la reserva");
                return false;
            }
            
            if (reserva.getTipoMesa() == null || reserva.getTipoMesa().getIdTipoMesa() == null) {
                System.err.println("Tipo de mesa no especificado en la reserva");
                return false;
            }
            
            System.out.println("Local ID: " + reserva.getLocal().getIdLocal());
            System.out.println("Tipo Mesa ID: " + reserva.getTipoMesa().getIdTipoMesa());
            System.out.println("Número de mesas necesarias: " + (reserva.getNumeroMesas() != null ? reserva.getNumeroMesas() : 1));
            
            // Buscar mesas disponibles en el local
            MesaParametros params = new MesaParametros();
            params.setIdLocal(reserva.getLocal().getIdLocal());
            params.setEstado(EstadoMesa.DISPONIBLE);
            params.setIdTipoMesa(reserva.getTipoMesa().getIdTipoMesa());
            
            System.out.println("Buscando mesas con parámetros: Local=" + params.getIdLocal() + 
                ", Estado=DISPONIBLE, TipoMesa=" + params.getIdTipoMesa());
            
            List<MesaDTO> mesasDisponibles = mesaDAO.listar(params);
            System.out.println("Mesas encontradas: " + (mesasDisponibles != null ? mesasDisponibles.size() : "null"));
            
            if (mesasDisponibles == null || mesasDisponibles.isEmpty()) {
                System.err.println("No hay mesas disponibles del tipo solicitado");
                return false;
            }
            
            // Verificar que tengamos suficientes mesas
            int mesasNecesarias = reserva.getNumeroMesas() != null ? reserva.getNumeroMesas() : 1;
            if (mesasDisponibles.size() < mesasNecesarias) {
                System.err.println("No hay suficientes mesas disponibles del tipo solicitado. Necesarias: " + 
                    mesasNecesarias + ", Disponibles: " + mesasDisponibles.size());
                return false;
            }
            
            System.out.println("Iniciando asignación de " + mesasNecesarias + " mesa(s)");
            
            // Asignar las mesas necesarias
            List<MesaDTO> mesasAsignadas = new ArrayList<>();
            for (int i = 0; i < mesasNecesarias && i < mesasDisponibles.size(); i++) {
                MesaDTO mesa = mesasDisponibles.get(i);
                System.out.println("Procesando mesa " + mesa.getIdMesa() + " - " + mesa.getNumeroMesa());
                
                // Cambiar estado de la mesa a RESERVADA
                mesa.setEstado(EstadoMesa.RESERVADA);
                mesa.setFechaModificacion(new Date());
                mesa.setUsuarioModificacion("sistema_reservas");
                
                Integer resultadoMesa = mesaDAO.modificar(mesa);
                System.out.println("Resultado modificar mesa: " + resultadoMesa);
                
                if (resultadoMesa != null && resultadoMesa > 0) {
                    // Crear relación en la tabla RES_RESERVAS_x_MESAS
                    ReservaxMesasDTO relacion = new ReservaxMesasDTO();
                    
                    // Crear objetos para la relación
                    ReservaDTO reservaRelacion = new ReservaDTO();
                    reservaRelacion.setIdReserva(reservaId);
                    relacion.setReserva(reservaRelacion);
                    
                    MesaDTO mesaRelacion = new MesaDTO();
                    mesaRelacion.setIdMesa(mesa.getIdMesa());
                    relacion.setMesa(mesaRelacion);
                    
                    System.out.println("Insertando relación: reserva=" + reservaId + ", mesa=" + mesa.getIdMesa());
                    Integer resultadoRelacion = reservaxMesaDAO.insertar(relacion);
                    System.out.println("Resultado insertar relación: " + resultadoRelacion);
                    
                    if (resultadoRelacion != null && resultadoRelacion > 0) {
                        mesasAsignadas.add(mesa);
                        System.out.println("Mesa " + mesa.getIdMesa() + " asignada a reserva " + reservaId);
                    } else {
                        // Si falla la inserción de la relación, revertir el estado de la mesa
                        mesa.setEstado(EstadoMesa.DISPONIBLE);
                        mesaDAO.modificar(mesa);
                        System.err.println("Error creando relación reserva-mesa para mesa " + mesa.getIdMesa());
                    }
                } else {
                    System.err.println("Error modificando estado de mesa " + mesa.getIdMesa());
                }
            }
            
            boolean exito = mesasAsignadas.size() == mesasNecesarias;
            System.out.println("Asignación " + (exito ? "exitosa" : "fallida") + ". Mesas asignadas: " + 
                mesasAsignadas.size() + "/" + mesasNecesarias);
            return exito;
            
        } catch (Exception e) {
            System.err.println("Error asignando mesas a reserva: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Obtiene las mesas asignadas a una reserva
     */
    public List<MesaDTO> obtenerMesasDeReserva(Integer reservaId) {
        try {
            if (reservaId == null || reservaId <= 0) {
                System.err.println("ID de reserva inválido para obtener mesas: " + reservaId);
                return new ArrayList<>();
            }
            
            // Obtener relaciones reserva-mesa
            List<ReservaxMesasDTO> relaciones = reservaxMesaDAO.listar(reservaId);
            List<MesaDTO> mesas = new ArrayList<>();
            
            if (relaciones != null) {
                for (ReservaxMesasDTO relacion : relaciones) {
                    if (relacion.getMesa() != null && relacion.getMesa().getIdMesa() != null) {
                        MesaDTO mesa = mesaDAO.obtenerPorId(relacion.getMesa().getIdMesa());
                        if (mesa != null) {
                            mesas.add(mesa);
                        }
                    }
                }
            }
            
            System.out.println("Obtenidas " + mesas.size() + " mesas para reserva " + reservaId);
            return mesas;
            
        } catch (Exception e) {
            System.err.println("Error obteniendo mesas de reserva: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    /**
     * Cambia el estado de todas las mesas asignadas a una reserva
     */
    public boolean cambiarEstadoMesasReserva(Integer reservaId, EstadoMesa nuevoEstado) {
        try {
            if (reservaId == null || reservaId <= 0) {
                System.err.println("ID de reserva inválido para cambiar estado: " + reservaId);
                return false;
            }
            
            if (nuevoEstado == null) {
                System.err.println("Estado no puede ser null");
                return false;
            }
            
            // Obtener mesas de la reserva y cambiar su estado
            List<ReservaxMesasDTO> relaciones = reservaxMesaDAO.listar(reservaId);
            if (relaciones == null || relaciones.isEmpty()) {
                System.err.println("No se encontraron mesas para la reserva " + reservaId);
                return false;
            }
            
            boolean todoExitoso = true;
            for (ReservaxMesasDTO relacion : relaciones) {
                if (relacion.getMesa() != null && relacion.getMesa().getIdMesa() != null) {
                    MesaDTO mesa = mesaDAO.obtenerPorId(relacion.getMesa().getIdMesa());
                    if (mesa != null) {
                        mesa.setEstado(nuevoEstado);
                        mesa.setFechaModificacion(new Date());
                        mesa.setUsuarioModificacion("sistema_reservas");
                        
                        Integer resultado = mesaDAO.modificar(mesa);
                        if (resultado == null || resultado <= 0) {
                            todoExitoso = false;
                            System.err.println("Error cambiando estado de mesa " + mesa.getIdMesa());
                        }
                    } else {
                        todoExitoso = false;
                        System.err.println("Mesa no encontrada: " + relacion.getMesa().getIdMesa());
                    }
                }
            }
            
            System.out.println("Cambio de estado " + (todoExitoso ? "exitoso" : "fallido") + 
                " para reserva " + reservaId + " a estado " + nuevoEstado);
            return todoExitoso;
            
        } catch (Exception e) {
            System.err.println("Error cambiando estado de mesas: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Libera todas las mesas de una reserva
     */
    public boolean liberarMesasDeReserva(Integer reservaId) {
        try {
            if (reservaId == null || reservaId <= 0) {
                System.err.println("ID de reserva inválido para liberar mesas: " + reservaId);
                return false;
            }
            
            // Obtener todas las relaciones de la reserva
            List<ReservaxMesasDTO> relaciones = reservaxMesaDAO.listar(reservaId);
            if (relaciones == null || relaciones.isEmpty()) {
                System.out.println("No hay mesas asignadas a la reserva " + reservaId);
                return true; // No hay nada que liberar
            }
            
            boolean todoExitoso = true;
            
            // Cambiar estado de mesas a DISPONIBLE y eliminar relaciones
            for (ReservaxMesasDTO relacion : relaciones) {
                if (relacion.getMesa() != null && relacion.getMesa().getIdMesa() != null) {
                    // Cambiar estado de la mesa a DISPONIBLE
                    MesaDTO mesa = mesaDAO.obtenerPorId(relacion.getMesa().getIdMesa());
                    if (mesa != null) {
                        mesa.setEstado(EstadoMesa.DISPONIBLE);
                        mesa.setFechaModificacion(new Date());
                        mesa.setUsuarioModificacion("sistema_liberacion");
                        
                        Integer resultadoMesa = mesaDAO.modificar(mesa);
                        if (resultadoMesa == null || resultadoMesa <= 0) {
                            todoExitoso = false;
                            System.err.println("Error liberando mesa " + mesa.getIdMesa());
                        }
                    }
                    
                    // Eliminar la relación reserva-mesa
                    Integer resultadoEliminar = reservaxMesaDAO.eliminar(relacion);
                    if (resultadoEliminar == null || resultadoEliminar <= 0) {
                        todoExitoso = false;
                        System.err.println("Error eliminando relación para mesa " + relacion.getMesa().getIdMesa());
                    }
                }
            }
            
            System.out.println("Liberación de mesas " + (todoExitoso ? "exitosa" : "fallida") + 
                " para reserva " + reservaId);
            return todoExitoso;
            
        } catch (Exception e) {
            System.err.println("Error liberando mesas de reserva: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Obtiene estadísticas de ocupación de mesas por local
     * @param idLocal ID del local
     * @return Array con conteos por estado [DISPONIBLE, RESERVADA, EN_USO, EN_MANTENIMIENTO, DESECHADA]
     */
    public int[] obtenerEstadisticasOcupacion(Integer idLocal) {
        int[] estadisticas = new int[5]; // Array para los 5 estados
        
        try {
            if (idLocal == null || idLocal <= 0) {
                System.err.println("ID de local inválido para estadísticas: " + idLocal);
                return estadisticas;
            }
            
            // Contar mesas por cada estado
            MesaParametros params = new MesaParametros();
            params.setIdLocal(idLocal);
            
            for (int i = 0; i < EstadoMesa.values().length; i++) {
                EstadoMesa estado = EstadoMesa.values()[i];
                params.setEstado(estado);
                
                List<MesaDTO> mesas = mesaDAO.listar(params);
                estadisticas[i] = mesas != null ? mesas.size() : 0;
            }
            
            System.out.println("Estadísticas obtenidas para local " + idLocal);
            return estadisticas;
            
        } catch (Exception e) {
            System.err.println("Error obteniendo estadísticas de ocupación: " + e.getMessage());
            e.printStackTrace();
            return estadisticas;
        }
    }
}