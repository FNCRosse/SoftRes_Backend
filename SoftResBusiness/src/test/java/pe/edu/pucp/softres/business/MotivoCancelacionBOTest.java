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
import pe.edu.pucp.softres.model.MotivosCancelacionDTO;
/**
 *
 * @author Rosse
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MotivoCancelacionBOTest {
    
    private  MotivoCancelacionBO motivoCancelacionBO;
    private static Integer idInsertado;

    @BeforeEach
    public void setup() {
        this.motivoCancelacionBO = new MotivoCancelacionBO();
    }
    
    @Test
    @Order(1)
    public void testInsertar() {
        MotivosCancelacionDTO mCancelacion = new MotivosCancelacionDTO();
        mCancelacion.setDescripcion("test_insertar");
        MotivosCancelacionDTO insertado = this.motivoCancelacionBO.insertar(mCancelacion);
        idInsertado = insertado.getIdMotivo();
        assertNotNull(idInsertado);
        assertTrue(idInsertado > 0);
    }
    
    @Test
    @Order(2)
    public void testObtenerPorId() {
        MotivosCancelacionDTO mCancelacion = this.motivoCancelacionBO.obtenerPorId(idInsertado);
        assertNotNull(mCancelacion);
        assertEquals("test_insertar", mCancelacion.getDescripcion());
    }
    
    @Test
    @Order(3)
    public void testListar() {
        List<MotivosCancelacionDTO> lista = this.motivoCancelacionBO.listar();
        assertNotNull(lista);
        assertTrue(lista.size() >= 1);
    }
    
    @Test
    @Order(4)
    public void testModificar() {
        MotivosCancelacionDTO mCancelacion = this.motivoCancelacionBO.obtenerPorId(idInsertado);
        mCancelacion.setDescripcion("test_modificado");
        Integer resultado = this.motivoCancelacionBO.modificar(mCancelacion);
        assertEquals(1, resultado);

        MotivosCancelacionDTO actualizado = motivoCancelacionBO.obtenerPorId(idInsertado);
        assertEquals("test_modificado", actualizado.getDescripcion());
    }
    
    @Test
    @Order(5)
    public void testEliminar() {
        MotivosCancelacionDTO mCancelacion =  this.motivoCancelacionBO.obtenerPorId(idInsertado);
        Integer resultado =  this.motivoCancelacionBO.eliminar(mCancelacion);
        assertEquals(1, resultado);

        MotivosCancelacionDTO eliminado =  this.motivoCancelacionBO.obtenerPorId(idInsertado);
        assertNull(eliminado);
    }
}
