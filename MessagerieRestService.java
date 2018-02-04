/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ngsystem.dev.messagerieservice.rest;

import com.oml.dsi.md5.MD5Encoder;
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
import java.util.ArrayList;
import java.util.Date;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javassist.CtMethod.ConstParameter.string;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import net.ngsystem.dev.messagerieservice.dao.Dao;
import net.ngsystem.dev.messagerieservice.dao.factory.DaoFactory;
import net.ngsystem.dev.messagerieservice.entities.Alias;
import net.ngsystem.dev.messagerieservice.entities.AttribuerDroit;
import net.ngsystem.dev.messagerieservice.entities.Domaine;
import net.ngsystem.dev.messagerieservice.entities.Droit;
import net.ngsystem.dev.messagerieservice.entities.Profil;
import net.ngsystem.dev.messagerieservice.entities.Utilisateur;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author meididiallo
 */
@Path("/messagerie-service")
public class MessagerieRestService {

    private final Dao dao;

    public MessagerieRestService() {
        dao = DaoFactory.getInstance().getDao();
    }

    @GET
    @Produces("text/html")
    public Response getStartingPage() {
        String output = "<h1>Hello World NGS!<h1>"
                + "<p>RESTful Service is running ... <br>Ping @ " + new Date().toString() + "</p<br>";
        System.out.println("bon");
        return Response.status(200).entity(output).build();
    }

    @GET
    @Path("/test")
    @Produces("application/json")
    public Response test() {
        System.out.println("****Test service called*****");
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("status", "OK");
        jSONObject.put("message", "RESTful Service is running......");
        return Response.status(200).entity(jSONObject.toString()).build();
    }

    @GET
    @Path("/hello/{name}")
    public Response hello1(@PathParam("name") String name) {
        return Response.status(Response.Status.OK).entity("Bonjour " + name).build();
    }

    @GET
    @Path("/hello")
    public Response hello2(@QueryParam("name") String name) {
        return Response.status(Response.Status.OK).entity("Bonjour " + name).build();
    }

    /**
     * Methode direBonsoir
     *
     * @return Response
     */
    @GET
    @Path("/direBonsoir")
    public Response direBonsoir(@QueryParam("nom") String nom) {
        String message = "Bonsoir " + nom;
        return Response.status(Response.Status.OK).entity(message).build();
    }

    /**
     * Methode saveEmployer
     */
    /*
    save Utilisateur
    
    
     */
    /*
    @GET
    @Path("/saveUtilisateur")
    public Response saveUser(@QueryParam("identifiant") String identifiant, @QueryParam("motDePasse") String motDePasse,
            @QueryParam("aliasId") String aliasId, @QueryParam("domaineId") String domaineId,
            @QueryParam("profilId") String profilId, @QueryParam("email") String email) {
        System.out.println("Debut ............");
        JSONObject jsonRet = new JSONObject();

        System.out.println("aliasId : " + aliasId);
        System.out.println("domaineId : " + domaineId);
        System.out.println("profilId : " + profilId);

        Utilisateur utilisateur = new Utilisateur();
        Alias alias = new Alias();
        Profil profil = new Profil();
        Domaine domaine = new Domaine();

        try {
            
            
            System.out.println("Debut ............");
            String hql = "select u from Utilisateur u where u.identifiant like :identifiant";
            List<Utilisateur> users = dao.findByNamedParameter(hql, "identifiant", identifiant);
            System.out.println("Debut ............");
            if (!users.isEmpty()) {
                jsonRet.put("message", "Un utilisateur existe déjà avec cet identifiant ...");
                jsonRet.put("status", "erreur1");
                return Response.status(Response.Status.OK).entity(jsonRet.toString()).build();
            }
            System.out.println("Debut ............");

            alias = dao.load("idAlias", Alias.class, Long.valueOf(aliasId));
            profil = dao.load("idProfil", Profil.class, Long.valueOf(profilId));
            domaine = dao.load("idDomaine", Domaine.class, Long.valueOf(domaineId));

            utilisateur.setIdentifiant(identifiant);
            utilisateur.setMotDePasse(MD5Encoder.encodedPassword(motDePasse));
            utilisateur.setEmail(email);
            utilisateur.setAlias(alias);
            utilisateur.setDomaine(domaine);
            utilisateur.setProfil(profil);
            dao.save(utilisateur);

            jsonRet.put("message", "Un utilisateur crée avec succès !");
            jsonRet.put("status", "ok");

        } catch (Exception e) {
            e.printStackTrace(System.err);
            jsonRet.put("message", "Un problème est survenu ...");
            jsonRet.put("status", "erreur");
            return Response.status(Response.Status.OK).entity(jsonRet.toString()).build();
        }

        return Response.status(Response.Status.OK).entity(jsonRet.toString()).build();
    }

    /*
        Save Domaine
        
        
        
     */
    /*
    @GET
    @Path("/saveDomaine")
    public Response savedomaine(@QueryParam("nomDomaine") String nomDomaine) {

        JSONObject jsonsavedomaine = new JSONObject();
        List<Criterion> restrictions = new ArrayList<>();

        try {

            restrictions.add(Restrictions.eq("nomDomaine", nomDomaine));
            List<Domaine> domaine = dao.findByCriterions(Domaine.class, restrictions);
            if (domaine.isEmpty()) {

                dao.save(new Domaine(nomDomaine));
                jsonsavedomaine.put("status", "ok");
                jsonsavedomaine.put("message", "Le domaine a été enregistré avec succès");

            } else {
                Domaine domain = domaine.get(0);

                if (domain.getNomDomaine().equals(nomDomaine)) {
                    jsonsavedomaine.put("status", "erreur1");
                    jsonsavedomaine.put("message", "Ce domaine existe deja");

                }

            }

            return Response.status(Response.Status.OK).entity(jsonsavedomaine.toString()).build();
        } catch (Exception ex) {
            Logger.getLogger(MessagerieRestService.class.getName()).log(Level.SEVERE, null, ex);
            jsonsavedomaine.put("status", "erreur");
            jsonsavedomaine.put("message", "Ereur d'enregistrement");
            return Response.status(Response.Status.OK).entity(jsonsavedomaine.toString()).build();

        }*/

        /*
        Save profil
        
        
         */
    //}
/*
    @GET
    @Path("/saveProfil")
    public Response saveprofil(@QueryParam("nomProfil") String nomProfil, @QueryParam("listDroitId") String listDroiId) {
        String[] droitIs = listDroiId.split(",");
        JSONObject jsonsaveprofil = new JSONObject();
        try {
            Profil profil = new Profil(nomProfil);
            dao.saveOrUpdate(profil);

            for (String droitId : droitIs) {
                AttribuerDroit attribuerDroit = new AttribuerDroit();
                
               Droit droit = dao.load("idDroit", Droit.class, Long.valueOf(droitId));
           
                attribuerDroit.setDroit(droit);
                attribuerDroit.setProfil(profil);
                dao.saveOrUpdate(attribuerDroit);
            }

            jsonsaveprofil.put("status", "ok");
            jsonsaveprofil.put("message", "succès profil");

        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            jsonsaveprofil.put("message", "Erreur profil");
            jsonsaveprofil.put("status", "Erreur");
            return Response.status(Response.Status.OK).entity(jsonsaveprofil.toString()).build();
        }

        return Response.status(Response.Status.OK).entity(jsonsaveprofil.toString()).build();
    }
*/
    /*
   
   
   Suppression Des alias,domaines,utilisateurs 
    
   
   
   
     */
    /*
    @GET
    @Path("/deleteAlias")
    public Response deletealias(@QueryParam("nomAlias") String nomAlias) {
        JSONObject jsondeletealias = new JSONObject();
        List<Alias> alias;
        Alias alias1;
        List<Criterion> restrictions = new ArrayList<>();
        try {

            restrictions.add(Restrictions.eq("etat", "actif"));
            restrictions.add(Restrictions.eq("nomAlias", nomAlias));
            alias = dao.findByCriterions(Alias.class, restrictions);
            alias1 = alias.get(0);
            alias1.setDateModification(new Date());
            alias1.setEtat("inactif");
            dao.update(alias1);
            jsondeletealias.put("status", "ok");
            jsondeletealias.put("message", "Alias a été supprimé");

            return Response.status(Response.Status.OK).entity(jsondeletealias.toString()).build();
        } catch (Exception ex) {
            Logger.getLogger(MessagerieRestService.class.getName()).log(Level.SEVERE, null, ex);
            jsondeletealias.put("status", "Erreur");
            jsondeletealias.put("message", "action iopérant");

            return Response.status(Response.Status.OK).entity(jsondeletealias.toString()).build();

        }

    }

    @GET
    @Path("/deleteDomaine")
    public Response deletedomaine(@QueryParam("nomDomaine") String nomDomaine) {
        JSONObject jsondeletedomaine = new JSONObject();
        List<Domaine> domaine;
        Domaine domain;
        List<Criterion> restrictions = new ArrayList<>();
        try {
            restrictions.add(Restrictions.eq("etat", "actif"));
            restrictions.add(Restrictions.eq("nomDomaine", nomDomaine));
            domaine = dao.findByCriterions(Domaine.class, restrictions);
            domain = domaine.get(0);
            domain.setDateModification(new Date());
            domain.setEtat("inactif");

            dao.update(domain);
            jsondeletedomaine.put("status", "ok");
            jsondeletedomaine.put("message", "Domaine a été supprimé avec succès");

            return Response.status(Response.Status.OK).entity(jsondeletedomaine.toString()).build();

        } catch (Exception ex) {
            Logger.getLogger(MessagerieRestService.class.getName()).log(Level.SEVERE, null, ex);

            jsondeletedomaine.put("status", "Erreur");
            jsondeletedomaine.put("message", "Action inopérante");
            return Response.status(Response.Status.OK).entity(jsondeletedomaine.toString()).build();

        }

    }

    @GET
    @Path("/deleteUtilisateur")
    public Response deleteUtilisateur(@QueryParam("identifiant") String identifiant) {
        JSONObject jsondeleteutilisateur = new JSONObject();
        List<Utilisateur> utilisateur;
        Utilisateur utili;

        List<Criterion> restrictions = new ArrayList<>();

        try {

            restrictions.add(Restrictions.eq("etat", "actif"));
            restrictions.add(Restrictions.eq("identifiant", identifiant));
            utilisateur = dao.findByCriterions(Utilisateur.class, restrictions);
            utili = utilisateur.get(0);
            utili.setEtat("inactif");
            utili.setDateModification(new Date());
            dao.saveOrUpdate(utili);
            jsondeleteutilisateur.put("status", "ok");
            jsondeleteutilisateur.put("message", "Utilisateur a été supprimé avec succès");
            return Response.status(Response.Status.OK).entity(jsondeleteutilisateur.toString()).build();
        } catch (Exception ex) {
            Logger.getLogger(MessagerieRestService.class.getName()).log(Level.SEVERE, null, ex);

            jsondeleteutilisateur.put("status", "Erreur");
            jsondeleteutilisateur.put("message", "Action inopérante");
            return Response.status(Response.Status.OK).entity(jsondeleteutilisateur.toString()).build();

        }
    }
    
    @GET
    @Path("/deleteProfil")
    public Response deleteProfil(@QueryParam("idProfil") String idProfil){
       JSONObject json = new JSONObject();
        
        
        try {
            Profil profil = dao.load("idProfil", Profil.class, Long.valueOf(idProfil));
            profil.setEtat("inactif");
            dao.saveOrUpdate(profil);
            json.put("status", "OK");
            json.put("message","Le profil a été supprimé avec succès");
        } catch (Exception ex) {
              json.put("status", "KO");
            json.put("message","Une erreur est survenue");
            Logger.getLogger(MessagerieRestService.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    return Response.status(Response.Status.OK).entity(json.toString()).build(); 
    }*/
    
    
    //La liste des utilisateurs   
   /* @GET
    @Path("/listUtilisateur")
    public Response listutilisateur() {
        List<Criterion> restrictions = new ArrayList<>();
        JSONObject json = new JSONObject();
        JSONArray userArray = new JSONArray();

        try {

            restrictions.add(Restrictions.eq("etat", "actif"));

            List<Utilisateur> utilisateur = dao.findByCriterions(Utilisateur.class, restrictions);

            for (Utilisateur user : utilisateur) {
                JSONObject userObject = new JSONObject();
                userObject.put("id", user.getIdUtilisateur());
                userObject.put("identifiant", user.getIdentifiant());

                userArray.put(userObject);
            }
            json.put("users", userArray);
            json.put("status", "ok");
            json.put("message", "Liste des Utilisateurs");
        } catch (Exception ex) {
            Logger.getLogger(MessagerieRestService.class.getName()).log(Level.SEVERE, null, ex);
            json.put("status", "Erreur");
            json.put("message", "Erreur d'envoie");
            return Response.status(Response.Status.OK).entity(json.toString()).build();
        }
        return Response.status(Response.Status.OK).entity(json.toString()).build();
    }

    @GET
    @Path("/listDomaine")
    public Response listDomaine() {

        List<Criterion> restrictions = new ArrayList<>();
        JSONObject json = new JSONObject();
        JSONArray userArray = new JSONArray();

        try {

            restrictions.add(Restrictions.eq("etat", "actif"));
            List<Domaine> domaine = dao.findByCriterions(Domaine.class, restrictions);

            for (Domaine user : domaine) {
                JSONObject userObject = new JSONObject();

                userObject.put("id", user.getIdDomaine());
                userObject.put("nom", user.getNomDomaine());

                userArray.put(userObject);
            }
            json.put("status", "ok");
            json.put("message", "Liste des domaines");
            json.put("users", userArray);

        } catch (Exception ex) {
            Logger.getLogger(MessagerieRestService.class.getName()).log(Level.SEVERE, null, ex);

            json.put("status", "Erreur");
            json.put("message", "Erreur d'envoie");
        }

        return Response.status(Response.Status.OK).entity(json.toString()).build();
    }

    @GET
    @Path("/listAlias")
    public Response listAlias() {

        List<Criterion> restrictions = new ArrayList<>();
        JSONObject json = new JSONObject();
        JSONArray userArray = new JSONArray();

        try {

            restrictions.add(Restrictions.eq("etat", "actif"));
            List<Alias> alias = dao.findByCriterions(Alias.class, restrictions);

            for (Alias user : alias) {
                JSONObject userObject = new JSONObject();

                userObject.put("id", user.getIdAlias());
                userObject.put("nom", user.getNomAlias());

                userArray.put(userObject);
            }
            json.put("users", userArray);
            json.put("status", "ok");
            json.put("message", "Liste des domaines");

        } catch (Exception ex) {
            Logger.getLogger(MessagerieRestService.class.getName()).log(Level.SEVERE, null, ex);

            json.put("status", "erreur");
            json.put("message", "Erreur d'envoie");
        }

        return Response.status(Response.Status.OK).entity(json.toString()).build();
    }

    @GET
    @Path("/listProfil")
    public Response listProfil() {

        List<Criterion> restrictions = new ArrayList<>();
        JSONObject json = new JSONObject();
        JSONArray userArray = new JSONArray();

        try {

            restrictions.add(Restrictions.eq("etat", "actif"));
            List<Profil> profil = dao.findByCriterions(Profil.class, restrictions);

            for (Profil user : profil) {
                JSONObject userObject = new JSONObject();

                userObject.put("id", user.getIdProfil());
                userObject.put("nom", user.getNomProfil());

                userArray.put(userObject);
            }
            json.put("users", userArray);
            json.put("status", "ok");
            json.put("message", "Liste des profils");

        } catch (Exception ex) {
            Logger.getLogger(MessagerieRestService.class.getName()).log(Level.SEVERE, null, ex);

            json.put("status", "erreur");
            json.put("message", "Erreur d'envoie");
        }

        return Response.status(Response.Status.OK).entity(json.toString()).build();
    }

    @GET
    @Path("/listDroit")
    public Response listDroit() {
        List<Criterion> restrictions = new ArrayList<>();
        JSONObject json = new JSONObject();
        JSONArray userArray = new JSONArray();

        try {
            List<Droit> droits = dao.findAll(Droit.class);
            for (Droit droit : droits) {
 
                JSONObject userObject = new JSONObject();
                userObject.put("id", droit.getIdDroit());
                userObject.put("nom", droit.getNomDroit());

                userArray.put(userObject);
            }
            json.put("users", userArray);
            json.put("status", "ok");
            json.put("message", "Liste des droits");
            return Response.status(Response.Status.OK).entity(json.toString()).build();

        } catch (Exception ex) {
            Logger.getLogger(MessagerieRestService.class.getName()).log(Level.SEVERE, null, ex);
            json.put("status", "erreur");
            json.put("message", "Liste des droits");
            return Response.status(Response.Status.OK).entity(json.toString()).build();
        }

    }

    @GET
    @Path("/modifyAlias")
    public Response modifyAlias(@QueryParam("nomAlias") String nomAlias, @QueryParam("nouveauAlias") String nouveauNom) {

        JSONObject json = new JSONObject();
        List<Criterion> restrictions = new ArrayList<>();

        try {

            //restrictions.add(Restrictions.eq("etat", "actif"));
            restrictions.add(Restrictions.eq("nomAlias", nomAlias));
            List<Alias> alias = dao.findByCriterions(Alias.class, restrictions);
            Alias alia = alias.get(0);

            alia.setDateModification(new Date());
            alia.setEtat("actif");
            System.out.println(nouveauNom);

            alia.setNomAlias(nouveauNom);
            dao.saveOrUpdate(alia);

            json.put("status", "ok");
            json.put("message", "Modification a été fait ");
        } catch (Exception ex) {
            Logger.getLogger(MessagerieRestService.class.getName()).log(Level.SEVERE, null, ex);
            json.put("status", "erreur");
            json.put("message", "erreur de modification");

        }

        return Response.status(Response.Status.OK).entity(json.toString()).build();

    }

    @GET
    @Path("/modifyDomaine")

    public Response modifyDomaine(@QueryParam("nomDomaine") String nomDomaine, @QueryParam("nouveauDomaine") String nouveauNom) {

        JSONObject json = new JSONObject();
        List<Criterion> restrictions = new ArrayList<>();

        try {

            //restrictions.add(Restrictions.eq("etat", "actif"));
            restrictions.add(Restrictions.eq("nomDomaine", nomDomaine));
            List<Domaine> domaine = dao.findByCriterions(Domaine.class, restrictions);
            Domaine alia = domaine.get(0);

            alia.setDateModification(new Date());
            alia.setEtat("actif");
            alia.setNomDomaine(nouveauNom);
            dao.saveOrUpdate(alia);
            json.put("status", "ok1");
            json.put("message", "Modification a été fait ");

        } catch (Exception ex) {
            Logger.getLogger(MessagerieRestService.class.getName()).log(Level.SEVERE, null, ex);
            json.put("status", "erreur");
            json.put("message", "erreur de modification");

        }

        return Response.status(Response.Status.OK).entity(json.toString()).build();

    }

    @GET
    @Path("/modifierUtilisateur")
     public Response modifierUtilisateur(@QueryParam("identifiant") String identifiant,@QueryParam("password") String password) {
        JSONObject jsondeleteutilisateur = new JSONObject();
        List<Utilisateur> utilisateur;
        Utilisateur utili;

        List<Criterion> restrictions = new ArrayList<>();

        try {
            restrictions.add(Restrictions.eq("identifiant", identifiant));
            utilisateur = dao.findByCriterions(Utilisateur.class, restrictions);
            utili = utilisateur.get(0);
            utili.setMotDePasse(MD5Encoder.encodedPassword(password));
            utili.setDateModification(new Date());
            System.out.println("Mon mdp:"+utili.getMotDePasse());
            dao.saveOrUpdate(utili);
            jsondeleteutilisateur.put("status", "ok");
            jsondeleteutilisateur.put("message", "Le mot de passe a été modifié avec succès");
            
        } catch (Exception ex) {
            Logger.getLogger(MessagerieRestService.class.getName()).log(Level.SEVERE, null, ex);

            jsondeleteutilisateur.put("status", "Erreur");
            jsondeleteutilisateur.put("message", "Action inopérante");
            

        }
      return Response.status(Response.Status.OK).entity(jsondeleteutilisateur.toString()).build();  
    }*/
    
    
    
    
    
    //Login
   /* @GET
    @Path("/login")
    public Response login(@QueryParam("identifiant") String identifiant,
            @QueryParam("motDepasse") String motDepasse) {
        JSONObject json = new JSONObject();
        JSONArray userArray = new JSONArray();
        List<Criterion> restrictions = new ArrayList<>();
        String requete="Select d from Droit d,AttribuerDroit ad where ad.droit=d and ad.profil=:profil";
        try {
           
            
            restrictions.add(Restrictions.eq("etat", "actif"));
            restrictions.add(Restrictions.eq("identifiant", identifiant));
            List<Utilisateur> utilisateur = dao.findByCriterions(Utilisateur.class, restrictions);
            Utilisateur utili = utilisateur.get(0);
            System.out.println("Lmot de passe:"+utili.getMotDePasse());
            if(utili.getMotDePasse().equals(MD5Encoder.encodedPassword(motDepasse))){
            System.out.println(utili.getProfil());
            
             System.out.println(" coucou");
            Profil profil = dao.load("idProfil", Profil.class, utili.getProfil().getIdProfil());
            System.out.println("Le profil"+profil);
           List<Droit> attribuerDroit=dao.findByNamedParameter(requete, "profil", profil);
            System.out.println("Tous les droits du profil: "+attribuerDroit);
            for(Droit d:attribuerDroit){
            
                JSONObject userObject = new JSONObject();
                userObject.put("id", d.getIdDroit());
                userObject.put("nom", d.getNomDroit());
                userArray.put(userObject);
            }
            json.put("droits", userArray);
            json.put("status", "ok");
            json.put("message", "profil existe");
            json.put("identifiant", utili.getIdentifiant());}
            else{
                 json.put("status", "ERREUR");
            json.put("message", "mot de passe incorrect");}
        } catch (Exception ex) {
            Logger.getLogger(MessagerieRestService.class.getName()).log(Level.SEVERE, null, ex);
            json.put("status", "erreur");
            json.put("message", "profil n'existe pas");
        }

        return Response.status(Response.Status.OK).entity(json.toString()).build();

    }
    
    @GET
    @Path("/tableauBord")
    public Response tableauBord(){
     List<Criterion> restrictionsD = new ArrayList<>();
     List<Criterion> restrictionsA = new ArrayList<>();
     List<Criterion> restrictionsU = new ArrayList<>();
     List<Criterion> restrictionsP = new ArrayList<>();
     
      List<Criterion> restrictionsiD = new ArrayList<>();
     List<Criterion> restrictionsiA = new ArrayList<>();
     List<Criterion> restrictionsiU = new ArrayList<>();
     List<Criterion> restrictionsiP = new ArrayList<>();
        JSONObject json = new JSONObject();
    try{
     restrictionsU.add(Restrictions.eq("etat", "actif"));
            List<Utilisateur> utilisateur = dao.findByCriterions(Utilisateur.class, restrictionsU);
             restrictionsD.add(Restrictions.eq("etat", "actif"));
            List<Domaine> domaine = dao.findByCriterions(Domaine.class, restrictionsD);
             restrictionsA.add(Restrictions.eq("etat", "actif"));
            List<Alias> alias= dao.findByCriterions(Alias.class, restrictionsA);
         restrictionsP.add(Restrictions.eq("etat", "actif"));
            List<Profil> profil = dao.findByCriterions(Profil.class, restrictionsP);
            
            
            restrictionsiU.add(Restrictions.eq("etat", "inactif"));
            List<Utilisateur> iutilisateur = dao.findByCriterions(Utilisateur.class, restrictionsiU);
             restrictionsiD.add(Restrictions.eq("etat", "inactif"));
            List<Domaine> idomaine = dao.findByCriterions(Domaine.class, restrictionsiD);
             restrictionsiA.add(Restrictions.eq("etat", "inactif"));
            List<Alias> ialias= dao.findByCriterions(Alias.class, restrictionsiA);
         restrictionsiP.add(Restrictions.eq("etat", "inactif"));
            List<Profil> iprofil = dao.findByCriterions(Profil.class, restrictionsiP);
            
            
            json.put("status", "OK");
            json.put("utilisateur",utilisateur.size() );
            json.put("domaine",domaine.size());
            json.put("alias",alias.size());
            json.put("profil",profil.size());
            json.put("iutilisateur",iutilisateur.size() );
            json.put("idomaine",idomaine.size());
            json.put("ialias",ialias.size());
            json.put("iprofil",iprofil.size());
            
        
            json.put("message","Succès");
    
    }catch(Exception ex){
    json.put("status", "KO");
     json.put("message","Erreur survenue");
    }
    return Response.status(Response.Status.OK).entity(json.toString()).build();
    }*/
 /*   
  @GET
  @Path("/listDroits")
 public Response listDroits(@QueryParam("identifiant") String identifiant,
            @QueryParam("motDepasse") String motDepasse){
 
     JSONObject json = new JSONObject();
            JSONArray userArray = new JSONArray();
            List<Criterion> restrictions = new ArrayList<>();
            
            String requete="Select d from Droit d,AttribuerDroit ad where ad.droit=d and ad.profil=:profil";
     
     
        try {
            
            
            
            
            restrictions.add(Restrictions.eq("etat", "actif"));
            restrictions.add(Restrictions.eq("identifiant", identifiant));
            List<Utilisateur> utilisateur = dao.findByCriterions(Utilisateur.class, restrictions);
            Utilisateur utili = utilisateur.get(0);
            System.out.println("Lmot de passe:"+utili.getMotDePasse());
            
                System.out.println(utili.getProfil());
                
                System.out.println(" Liste des droits");
                Profil profil = dao.load("idProfil", Profil.class, utili.getProfil().getIdProfil());
                System.out.println("Le profil"+profil);
                
                List<Droit> attribuerDroit=dao.findByNamedParameter(requete, "profil", profil);
                System.out.println("Tous les droits du profil: "+attribuerDroit);
                for(Droit d:attribuerDroit){
                    
                    JSONObject userObject = new JSONObject();
                    userObject.put("id", d.getIdDroit());
                    userObject.put("nom", d.getNomDroit());
                    userArray.put(userObject);
                }
                json.put("droits", userArray);
                json.put("status", "ok");
                json.put("message", "profil existe");
                json.put("identifiant", utili.getIdentifiant());
                
                 } catch (Exception ex) {
                
                json.put ("status","ERREUR");
                json.put("message", "Une erreur est survenue");
            }
          return Response.status(Response.Status.OK).entity(json.toString()).build();  
        }*/
    

}

