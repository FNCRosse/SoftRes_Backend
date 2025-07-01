/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.daoImp;

import pe.edu.pucp.softres.dao.HomeDashboardDAO;
import pe.edu.pucp.softres.db.DBManager;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BryanGnr
 */
public class HomeDashboardDAOImpl implements HomeDashboardDAO {

    @Override
    public Integer obtenerCantidadReservasDiarias() {
        String sql = "SELECT COUNT(*) FROM RES_RESERVAS WHERE DATE(FECHA_HORA_REGISTRO) = CURDATE()";
        try (Connection conn = DBManager.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            Logger.getLogger(HomeDashboardDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    @Override
    public Integer obtenerCantidadReservasSemanales() {
        String sql = "SELECT COUNT(*) FROM RES_RESERVAS WHERE YEARWEEK(FECHA_HORA_REGISTRO, 1) = YEARWEEK(CURDATE(), 1)";
        try (Connection conn = DBManager.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            Logger.getLogger(HomeDashboardDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    @Override
    public Double obtenerPorcentajeOcupacionMesas() {
        String sql = "SELECT "
                + "   (SELECT COUNT(*) "
                + "    FROM RES_MESAS "
                + "    WHERE ESTADO = 'EN_USO') AS usadas, "
                + "   (SELECT COUNT(*) "
                + "    FROM RES_MESAS "
                + "    WHERE ESTADO IN ('DISPONIBLE', 'RESERVADA', 'EN_USO')) AS totales";

        try (Connection conn = DBManager.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                int usadas = rs.getInt("usadas");
                int totales = rs.getInt("totales");
                return totales == 0 ? 0.0 : usadas * 100.0 / totales;
            }

        } catch (SQLException e) {
            Logger.getLogger(HomeDashboardDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0.0;
    }

    @Override
    public Integer obtenerCantidadCancelacionesRecientes() {
        String sql
                = "SELECT COUNT(*) FROM RES_RESERVAS "
                + "WHERE ESTADO = 'CANCELADA' "
                + "AND FECHA_MODIFICACION >= DATE_SUB(CURDATE(), INTERVAL 7 DAY)";

        try (Connection conn = DBManager.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            Logger.getLogger(HomeDashboardDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    @Override
    public List<Integer> obtenerReservasPorMes() {
        List<Integer> reservasMensuales = new ArrayList<>(Collections.nCopies(12, 0));
        String sql = "SELECT MONTH(FECHA_HORA_REGISTRO) AS mes, COUNT(*) AS total "
                + "FROM RES_RESERVAS GROUP BY MONTH(FECHA_HORA_REGISTRO)";
        try (Connection conn = DBManager.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int mes = rs.getInt("mes");
                int total = rs.getInt("total");
                reservasMensuales.set(mes - 1, total);
            }

        } catch (SQLException e) {
            Logger.getLogger(HomeDashboardDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return reservasMensuales;
    }

    @Override
    public Map<String, Double> obtenerPorcentajeReservasPorLocal() {
        Map<String, Double> mapa = new HashMap<>();
        String sql
                = "SELECT l.NOMBRE AS local, COUNT(*) AS total "
                + "FROM RES_RESERVAS r "
                + "INNER JOIN RES_LOCALES l ON r.LOCAL_ID = l.LOCAL_ID "
                + "GROUP BY l.NOMBRE";

        int totalReservas = 0;
        Map<String, Integer> conteoPorLocal = new HashMap<>();

        try (Connection conn = DBManager.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String nombre = rs.getString("local");
                int total = rs.getInt("total");
                conteoPorLocal.put(nombre, total);
                totalReservas += total;
            }

            for (Map.Entry<String, Integer> entry : conteoPorLocal.entrySet()) {
                mapa.put(entry.getKey(), totalReservas == 0 ? 0.0 : entry.getValue() * 100.0 / totalReservas);
            }

        } catch (SQLException e) {
            Logger.getLogger(HomeDashboardDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }

        return mapa;
    }

    @Override
    public Map<String, Integer> obtenerEstadoActualReservas() {
        Map<String, Integer> estados = new HashMap<>();
        String sql = "SELECT ESTADO, COUNT(*) AS total FROM RES_RESERVAS GROUP BY ESTADO";

        try (Connection conn = DBManager.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String estado = rs.getString("ESTADO");
                int total = rs.getInt("total");
                estados.put(estado, total);
            }

        } catch (SQLException e) {
            Logger.getLogger(HomeDashboardDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }

        return estados;
    }
}
