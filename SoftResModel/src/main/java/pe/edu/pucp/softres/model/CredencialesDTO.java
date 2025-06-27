/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.model;

/**
 *
 * @author Rosse
 */
public class CredencialesDTO {
    private String email;
    private String contrasenha;
    
    public CredencialesDTO() {
        this.email = null;
        this.contrasenha = null;
    }

    public CredencialesDTO(String email, String contrasenha) {
        this.email = email;
        this.contrasenha = contrasenha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenha() {
        return contrasenha;
    }

    public void setContrasenha(String contrasenha) {
        this.contrasenha = contrasenha;
    }
}
