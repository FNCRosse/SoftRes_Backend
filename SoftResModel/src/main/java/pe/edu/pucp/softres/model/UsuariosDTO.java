
package pe.edu.pucp.softres.model;

import java.util.Date;

/**
 *
 * @author frank
 */
public class UsuariosDTO {

    private Integer idUsuario;
    private RolDTO rol;
    private TipoDocumentoDTO tipoDocumento;
    private String nombreComp;
    private String numeroDocumento;
    private String contrasenha;
    private String email;
    private String telefono;
    private Double sueldo;
    private Date fechaContratacion;
    private Integer cantidadReservacion;
    private Boolean estado;
    private Date fechaCreacion;
    private String usuarioCreacion;
    private Date fechaModificacion;
    private String usuarioModificacion;

    // Constructor
    public UsuariosDTO() {
        this.idUsuario = null;
        this.rol = null;
        this.nombreComp = null;
        this.tipoDocumento = null;
        this.numeroDocumento = null;
        this.contrasenha = null;
        this.email = null;
        this.telefono = null;
        this.sueldo = null;
        this.fechaContratacion = null;
        this.cantidadReservacion = null;
        this.estado = null;
        this.fechaCreacion = null;
        this.usuarioCreacion = null;
        this.fechaModificacion = null;
        this.usuarioModificacion = null;
    }

    public UsuariosDTO(Integer idUsuario, RolDTO rol, String nombreComp, TipoDocumentoDTO tipoDocumento,
            String numeroDocumento, String contrasenha, String email, String telefono,
            Double sueldo,Date fechaContratacion, Integer cantidadReservacion,
            Boolean estado) {
        this.idUsuario = idUsuario;
        this.rol = rol;
        this.nombreComp = nombreComp;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.contrasenha = contrasenha;
        this.email = email;
        this.telefono = telefono;
        this.sueldo = sueldo;
        this.fechaContratacion = fechaContratacion;
        this.cantidadReservacion = cantidadReservacion;
        this.estado = estado;
    }

    public UsuariosDTO(UsuariosDTO original) {
        this.idUsuario = original.idUsuario;
        this.rol = original.rol;
        this.nombreComp = original.nombreComp;
        this.tipoDocumento = original.tipoDocumento;
        this.numeroDocumento = original.numeroDocumento;
        this.contrasenha = original.contrasenha;
        this.email = original.email;
        this.telefono = original.telefono;
        this.sueldo = original.sueldo;
        this.fechaContratacion = original.fechaContratacion;
        this.cantidadReservacion = original.cantidadReservacion;
        this.estado = original.estado;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public RolDTO getRol() {
        return rol;
    }

    public void setRol(RolDTO rol) {
        this.rol = rol;
    }

    public String getNombreComp() {
        return nombreComp;
    }

    public void setNombreComp(String nombreComp) {
        this.nombreComp = nombreComp;
    }

    public TipoDocumentoDTO getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumentoDTO tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getContrasenha() {
        return contrasenha;
    }

    public void setContrasenha(String contrasenha) {
        this.contrasenha = contrasenha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Double getSueldo() {
        return sueldo;
    }

    public void setSueldo(Double sueldo) {
        this.sueldo = sueldo;
    }

    public Date getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(Date fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public Integer getCantidadReservacion() {
        return cantidadReservacion;
    }

    public void setCantidadReservacion(Integer cantidadReservacion) {
        this.cantidadReservacion = cantidadReservacion;
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
