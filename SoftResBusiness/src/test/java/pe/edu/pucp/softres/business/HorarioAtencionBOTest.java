
package pe.edu.pucp.softres.business;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import pe.edu.pucp.softres.model.DiaSemana;
import pe.edu.pucp.softres.model.HorarioAtencionDTO;
import pe.edu.pucp.softres.parametros.HorarioParametros;

/**
 *
 * @author frank
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HorarioAtencionBOTest {

    private HorarioAtencionBO horarioAtencionBO;
    private static Integer horarioIdCreado;

    @BeforeEach
    public void setUp() {
        this.horarioAtencionBO = new HorarioAtencionBO();
    }

    @Test
    @Order(1)
    public void testInsertar() {
        HorarioAtencionDTO horario= new HorarioAtencionDTO();
        horario.setDiaSemana(DiaSemana.VIERNES);
        horario.setEsFeriado(false);
        horario.setHoraFin(LocalTime.of(17, 0));
        horario.setHoraInicio(LocalTime.of(8, 0));
        horario.setEstado(true);
        horario.setFechaCreacion(new Date());
        horario.setUsuarioCreacion("test_insertar");
        HorarioAtencionDTO insertado = horarioAtencionBO.insertar(horario);
        assertNotNull(insertado.getIdHorario());
        assertTrue(insertado.getIdHorario() != 0);
        horarioIdCreado = insertado.getIdHorario();
    }

    @Test
    @Order(3)
    public void testModificar() {
        HorarioAtencionDTO horario = horarioAtencionBO.obtenerPorId(horarioIdCreado);
        horario.setDiaSemana(DiaSemana.JUEVES);
        horario.setEsFeriado(true);
        horario.setFechaModificacion(new Date());
        horario.setUsuarioModificacion("test_mod");
        int res = horarioAtencionBO.modificar(horario);
        assertEquals(1, res);

        HorarioAtencionDTO modHorario = horarioAtencionBO.obtenerPorId(horarioIdCreado);
        assertEquals(DiaSemana.JUEVES, modHorario.getDiaSemana());
        assertTrue(modHorario.getEsFeriado());
    }

    @Test
    @Order(5)
    public void testEliminar() {
        HorarioAtencionDTO horario = horarioAtencionBO.obtenerPorId(horarioIdCreado);
        horario.setEstado(false);
        horario.setUsuarioModificacion("Test_Eliminacion");
        horario.setFechaModificacion(new Date());
        Integer res = horarioAtencionBO.eliminar(horario);
        assertEquals(1, res);
        HorarioAtencionDTO eliminado = horarioAtencionBO.obtenerPorId(horarioIdCreado);
        assertFalse(eliminado.getEstado());
    }

    @Test
    @Order(2)
    public void testObtenerPorId() {
        HorarioAtencionDTO horario = horarioAtencionBO.obtenerPorId(horarioIdCreado);
        assertNotNull(horario);
        assertEquals(DiaSemana.VIERNES, horario.getDiaSemana());
    }

    @Test
    @Order(4)
    public void testListar() {
        HorarioParametros parametros = new HorarioParametros();
        // Sin filtros adicionales, lista todo lo que hay
        
        List<HorarioAtencionDTO> lista = horarioAtencionBO.listar(parametros);
        assertNotNull(lista);
        assertTrue(lista.size() >= 1);
    }
}

