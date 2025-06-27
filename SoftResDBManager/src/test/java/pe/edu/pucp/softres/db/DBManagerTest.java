
package pe.edu.pucp.softres.db;

import java.sql.Connection;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author frank
 */
public class DBManagerTest {
    
    public DBManagerTest() {
    }

    /**
     * Test of getConnection method, of class DBManager.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
//        DBManager dbManager = new DBManager();
//        dbManager = new DBManager();
        DBManager dBManager = DBManager.getInstance();
        assertNotNull(dBManager);//assertNotNull lo que hace es verificar si es nulo el dbManager, en caso no sea nulo, compila bien, caso contrario NO
    
        DBManager dBManager2 = DBManager.getInstance();
        assertNotNull(dBManager2);//creamos otra instancia con otro nombre
        
        //abajo probamos si son iguales estas instancias, obviamente deben ser iguales
        assertEquals(dBManager, dBManager2);
    }
    
    @Test
    public void testGetConnection(){
        System.out.println("getConnection");
        DBManager dBManager = DBManager.getInstance();
    
        //LO DE ABAJO HICE PARA COMPARAR MI contra: frankybd; con su codigo cifrado
        //assertEquals("frankybd", Cifrado.descifrarMD5("kZC1TckVuSzIJz4nAw/+Ww=="));
        Connection connection = dBManager.getConnection();
        assertNotNull(connection);
    }
    
}
