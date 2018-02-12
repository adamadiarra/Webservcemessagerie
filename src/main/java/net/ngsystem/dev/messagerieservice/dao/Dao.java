/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ngsystem.dev.messagerieservice.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;

/**
 *
 * @author Net Killer
 */
public interface Dao {

    public void save(Object object) throws Exception;

    public void saveOrUpdate(Object object) throws Exception;

    public void update(Object object) throws Exception;

    public void delete(Object object) throws Exception;

    public <T> T load(Class<T> cls, Serializable idValue) throws Exception;

    public <T> T load(String idColumnName, Class<T> cls, Object idValue) throws Exception;

    public <T> List<T> findByProperties(HashMap<String, Object> eqProperties, Class<T> cls) throws Exception;

    public <T> List<T> findByProperties(HashMap<String, Object> eqProperties, HashMap<String, Object[]> betweenProperties, Class<T> cls) throws Exception;

    public <T> List<T> findByCriteria(Criteria criteria) throws Exception;

    public <T> List<T> findAll(Class<T> cls) throws Exception;

    public <T> List<T> findByQuery(Query query) throws Exception;

    public <T> List<T> findBySQLQuery(String sql, Class cls) throws Exception;

    public <T> T loadBySQLQuery(String sql, Class cls) throws Exception;

    public List findBySQLQueryOther(String sql) throws Exception;

    public List findByNamedParameter(String sql, String[] parameterNames, Object[] parameterValues) throws Exception;

    public <T> List findByCriterions(Class<T> cls, List restrictions) throws Exception;
    //public List findByIdCategories( Article article)throws Exception;

    /**
     *
     * @param sql
     * @param parameterName
     * @param parameterValue
     * @return list d'objets
     * @throws Exception
     */
    public List findByNamedParameter(String sql, String parameterName, Object parameterValue) throws Exception;

    public void executeSQLUpdate(String sql) throws Exception;

    public List findByNamedParameter(String sql, List<String> parameterNames, List<Object> parameterValues) throws Exception;

}
