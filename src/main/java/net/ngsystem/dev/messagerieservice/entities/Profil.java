/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ngsystem.dev.messagerieservice.entities;

import java.io.Serializable;
import java.util.Objects;
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
@Table(name = "Profil")
public class Profil implements Serializable{
    
    // Les attributs privés de la classe
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Long idProfil;
   
    @Basic(optional = false)
    @NotNull
    private String nomProfil;

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
     @Basic(optional = false)
    @NotNull
    private String etat="actif";
    //Les constructeurs

    public Profil() {
    }

    public Profil(String nomProfil) {
        this.nomProfil = nomProfil;
    }
    
    

    public Profil(Long idProfil) {
        this.idProfil = idProfil;
    }
    
    // Les accesseurs et modificateur des attributs privés

    public Long getIdProfil() {
        return idProfil;
    }

    public void setIdProfil(Long idProfil) {
        this.idProfil = idProfil;
    }

    public String getNomProfil() {
        return nomProfil;
    }

    public void setNomProfil(String nomProfil) {
        this.nomProfil = nomProfil;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.idProfil);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Profil other = (Profil) obj;
        if (!Objects.equals(this.idProfil, other.idProfil)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Profil{" + "idProfil=" + idProfil + ", nomProfil=" + nomProfil + '}';
    }
    
    
    
}
