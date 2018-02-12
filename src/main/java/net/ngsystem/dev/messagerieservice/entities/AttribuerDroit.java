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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "AttribuerDroit")
public class AttribuerDroit implements Serializable {

    // Les attributs privés de la classe
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Long idAttribuerDroit;

    @ManyToOne
    private Droit droit;
    @ManyToOne
    private Profil profil;

    // Constructeurs
    public AttribuerDroit() {
    }

    public AttribuerDroit(Droit domaine, Profil profil) {
        this.droit = domaine;
        this.profil = profil;
    }

    public AttribuerDroit(Long idAttribuerDroit) {
        this.idAttribuerDroit = idAttribuerDroit;
    }

    // Les accesseurs et modificateur des attributs privés
    public Long getIdAttribuerDroit() {
        return idAttribuerDroit;
    }

    public void setIdAttribuerDroit(Long idAttribuerDroit) {
        this.idAttribuerDroit = idAttribuerDroit;
    }

    public Droit getDroit() {
        return droit;
    }

    public void setDroit(Droit droit) {
        this.droit = droit;
    }

    public Profil getProfil() {
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.idAttribuerDroit);
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
        final AttribuerDroit other = (AttribuerDroit) obj;
        if (!Objects.equals(this.idAttribuerDroit, other.idAttribuerDroit)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AttribuerDroit{" + "idAttribuerDroit=" + idAttribuerDroit + '}';
    }

}
