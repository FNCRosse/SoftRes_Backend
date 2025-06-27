/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.business;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import pe.edu.pucp.softres.model.MesaDTO;
import pe.edu.pucp.softres.model.ReservaDTO;
import pe.edu.pucp.softres.model.ReservaxMesasDTO;

/**
 *
 * @author Rosse
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReservaxMesaBOTest {

    private ReservaxMesaBO reservaxMesaBO ;

    @BeforeEach
    public void setup() {
        reservaxMesaBO = new ReservaxMesaBO();
    }

    private ReservaxMesasDTO CrearRxM() {
        ReservaDTO reserva = new ReservaDTO();
        reserva.setIdReserva(2);
        MesaDTO mesa = new MesaDTO();
        mesa.setIdMesa(2);
        ReservaxMesasDTO rxm = new ReservaxMesasDTO();
        rxm.setReserva(reserva);
        rxm.setMesa(mesa);
        return rxm;
    }

    private Integer eliminar(ReservaxMesasDTO dato) {
        return this.reservaxMesaBO.eliminar(dato);
    }

    @Test
    @Order(1)
    public void testInsertar() {
        ReservaxMesasDTO hxs = CrearRxM();
        Integer insertado = this.reservaxMesaBO.insertar(hxs);
        assertNotNull(insertado);
        assertTrue(insertado > 0);
        eliminar(hxs);
    }

//    @Test
//    @Order(2)
//    public void testListar() {
//        ReservaxMesasDTO hxs = CrearRxM();
//        this.reservaxMesaBO.insertar(hxs);
//        List<ReservaxMesasDTO> lista = this.reservaxMesaBO.listar(-1);
//        assertNotNull(lista);
//        assertTrue(lista.size() >= 1);
//        eliminar(hxs);
//    }

    @Test
    @Order(3)
    public void testEliminar() {
        ReservaxMesasDTO hxs = CrearRxM();
        Integer insertado = this.reservaxMesaBO.insertar(hxs);
        assertNotNull(insertado);
        int resultado = eliminar(hxs);
        assertEquals(1, resultado);
    }
}
