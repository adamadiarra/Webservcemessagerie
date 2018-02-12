/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ngsystem.dev.messagerieservice.entities;

import java.io.Serializable;
import java.util.Date;
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
@Table(name = "alias")
public class Alias implements Serializable{
    
    /*Les attributs privés de la classe*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Long idAlias;
    
    @Basic(optional = false)
    @NotNull
    private String nomAlias;
    
    private String etat = "actif";
    
    private Date dateCreation = new Date();
    private Date dateModification=new Date();
    
    //Les constructeurs 

    
    
    public Alias(String nomAlias,String etat){
    
    this.nomAlias=nomAlias;
    this.etat=etat;
    
    }
    
    public Alias(String nomAlias) {
        this.nomAlias = nomAlias;
    }
    
    

    public Alias() {
    }

    

    // Les accesseurs et modificateur des attributs privés
    public Long getIdAlias() {
        return idAlias;
    }

    public void setIdAlias(Long idAlias) {
        this.idAlias = idAlias;
    }

    public String getNomAlias() {
        return nomAlias;
    }

    public void setNomAlias(String nomAlias) {
        this.nomAlias = nomAlias;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateModification() {
        return dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.idAlias);
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
        final Alias other = (Alias) obj;
        if (this.idAlias != other.idAlias) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Alias {" + "idAlias=" + idAlias + ", nomAlias=" + nomAlias + "}";
    }
    
    
}
