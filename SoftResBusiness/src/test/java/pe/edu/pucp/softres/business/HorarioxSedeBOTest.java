package pe.edu.pucp.softres.business;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import pe.edu.pucp.softres.model.HorarioAtencionDTO;
import pe.edu.pucp.softres.model.HorariosxSedesDTO;
import pe.edu.pucp.softres.model.SedeDTO;

/**
 *
 * @author Rosse
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HorarioxSedeBOTest {
    
    private  HorarioxSedeBO horarioxSedeBO;

    @BeforeEach
    public void setup() {
        horarioxSedeBO = new HorarioxSedeBO();
    }

    private HorariosxSedesDTO CrearHxS(){
        HorariosxSedesDTO hxs = new HorariosxSedesDTO();
        hxs.setIdHorario(2);
        hxs.setIdSede(2);
        return hxs;
    }
    private Integer eliminar (HorariosxSedesDTO dato){
        return this.horarioxSedeBO.eliminar(dato);
    }
    
    @Test
    @Order(1)
    public void testInsertar() {
        HorariosxSedesDTO hxs = CrearHxS();
        Integer insertado = this.horarioxSedeBO.insertar(hxs);
        assertNotNull(insertado);
        assertTrue(insertado > 0);
        eliminar(hxs);
    }

//    @Test
//    @Order(2)
//    public void testListar() {
//        HorariosxSedesDTO hxs = CrearHxS();
//        this.horarioxSedeBO.insertar(hxs);
//        List<HorariosxSedesDTO> lista = this.horarioxSedeBO.listar(-1);
//        assertNotNull(lista);
//        assertTrue(lista.size() >= 1);
//        eliminar(hxs);
//    }

    @Test
    @Order(3)
    public void testEliminar() {
        HorariosxSedesDTO hxs = CrearHxS();
        Integer insertado = this.horarioxSedeBO.insertar(hxs);
        assertNotNull(insertado);
        int resultado = eliminar(hxs);
        assertEquals(1, resultado);
    }
}
