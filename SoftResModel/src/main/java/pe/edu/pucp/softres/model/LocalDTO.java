
package pe.edu.pucp.softres.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author frank
 */
public class LocalDTO {

    private Integer idLocal;
    private SedeDTO sede;
    private String nombre;
    private String direccion;
    private String telefono;
    private Integer cantidadMesas;
    private Boolean estado;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date fechaCreacion;
    private String usuarioCreacion;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date fechaModificacion;
    private String usuarioModificacion;

    // Constructor
    public LocalDTO() {
        this.idLocal = null;
        this.sede = null;
        this.nombre = null;
        this.direccion = null;
        this.telefono = null;
        this.estado = null;
        this.cantidadMesas = null;
        this.fechaCreacion = null;
        this.usuarioCreacion = null;
        this.fechaModificacion = null;
        this.usuarioModificacion = null;
    }

    public LocalDTO(Integer idLocal, SedeDTO sede, String nombre, String direccion, Integer cantidadMesas,
            String telefono, Boolean estado) {
        this.idLocal = idLocal;
        this.sede = sede;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.cantidadMesas = cantidadMesas;
        this.estado = estado;
    }

    public LocalDTO(LocalDTO original) {
        this.idLocal = original.idLocal;
        this.sede = original.sede;
        this.nombre = original.nombre;
        this.direccion = original.direccion;
        this.telefono = original.telefono;
        this.estado = original.estado;
    }

    public Integer getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(Integer idLocal) {
        this.idLocal = idLocal;
    }

    public SedeDTO getSede() {
        return sede;
    }

    public void setSede(SedeDTO sede) {
        this.sede = sede;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public Integer getCantidadMesas() {
        return cantidadMesas;
    }

    public void setCantidadMesas(Integer cantidadMesas) {
        this.cantidadMesas = cantidadMesas;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }
}
