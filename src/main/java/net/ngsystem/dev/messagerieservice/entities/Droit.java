/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ngsystem.dev.messagerieservice.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "Droit")
public class Droit implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Long idDroit;
    
    @Basic(optional = false)
    @NotNull
    private String nomDroit;
    
    // Construteurs
    public Droit() {
    }

    public Droit(Long idDroit) {
        this.idDroit = idDroit;
    }
    
    public Droit(String nomDroit) {
        this.nomDroit = nomDroit;
    }

    public Droit(Long idDroit, String nomDroit) {
        this.idDroit = idDroit;
        this.nomDroit = nomDroit;
    }

    // Les accesseurs et modificateur des attributs privés
    public Long getIdDroit() {
        return idDroit;
    }

    public void setIdDroit(Long idDroit) {
        this.idDroit = idDroit;
    }

    public String getNomDroit() {
        return nomDroit;
    }

    public void setNomDroit(String nomDroit) {
        this.nomDroit = nomDroit;
    }

    @Override
    public String toString() {
        return "Droit{" + "idDroit=" + idDroit + ", nomDroit=" + nomDroit + '}';
    }
    
    
    
    

   
    
    
    
}
