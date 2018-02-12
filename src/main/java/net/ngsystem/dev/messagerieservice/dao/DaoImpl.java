/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ngsystem.dev.messagerieservice.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 *
 * @author Net Killer
 */
public class DaoImpl implements Dao {

    HibernateTemplate hibernateTemplate;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    @Override
    public void save(Object object) throws Exception {
        hibernateTemplate.save(object);
    }

    @Override
    public void saveOrUpdate(Object object) throws Exception {
        hibernateTemplate.saveOrUpdate(object);
    }

    @Override
    public void update(Object object) throws Exception {
        hibernateTemplate.update(object);
    }

    @Override
    public void delete(Object object) throws Exception {
        hibernateTemplate.delete(object);
    }

    @Override
    public <T> T load(Class<T> cls, Serializable idValue) throws Exception {
        return hibernateTemplate.load(cls, idValue);
    }

    @Override
    public <T> T load(String idColumnName, Class<T> cls, Object idValue) throws Exception {
        T ret;
        List list = hibernateTemplate.findByNamedParam("from " + cls.getSimpleName() + " where " + idColumnName + "=:id", "id", idValue);
        ret = (list == null || list.isEmpty()) ? null : (T) list.get(0);
        return ret;
    }

    @Override
    public <T> List<T> findByProperties(HashMap<String, Object> eqProperties, Class<T> cls) throws Exception {
        List<T> ret;
        DetachedCriteria crit = DetachedCriteria.forClass(cls);
        Set<String> keySet = eqProperties.keySet();
        for (String property : keySet) {
            crit.add(Restrictions.eq(property, eqProperties.get(property)));
        }
        ret = hibernateTemplate.findByCriteria(crit);
        return ret;
    }

    @Override
    public <T> List<T> findByProperties(HashMap<String, Object> eqProperties, HashMap<String, Object[]> betweenProperties, Class<T> cls) throws Exception {
        List<T> ret;
        DetachedCriteria crit = DetachedCriteria.forClass(cls);
        Set<String> keySet = eqProperties.keySet();
        for (String property : keySet) {
            crit.add(Restrictions.eq(property, eqProperties.get(property)));
        }
        keySet = betweenProperties.keySet();
        for (String property : keySet) {
            Object[] values = (Object[]) betweenProperties.get(property);
            crit.add(Restrictions.between(property, values[0], values[1]));
        }
        ret = hibernateTemplate.findByCriteria(crit);
        return ret;
    }

    @Override
    public <T> List<T> findByCriteria(Criteria criteria) throws Exception {
        List<T> ret;
        ret = criteria.list();
        return ret;
    }
    @Override
    public <T> List<T> findAll(Class<T> cls) throws Exception {
        DetachedCriteria crit = DetachedCriteria.forClass(cls);
        return hibernateTemplate.findByCriteria(crit);
    }

    @Override
    public <T> List<T> findByQuery(Query query) throws Exception {
        return (List<T>) query.list();
    }

    @Override
    public <T> List<T> findBySQLQuery(final String sql, final Class cls) throws Exception {
        return hibernateTemplate.executeFind(new HibernateCallback() {

            @Override
            public List doInHibernate(Session session) throws HibernateException, SQLException {
                SQLQuery sqlQuery = session.createSQLQuery(sql);
                sqlQuery.addEntity(sql, cls);
                return sqlQuery.list();
            }
        });
    }

    @Override
    public <T> T loadBySQLQuery(final String sql, final Class cls) throws Exception {
        List get = findBySQLQuery(sql, cls);
        return (get != null && !get.isEmpty()) ? (T) get.get(0) : null;
    }

    @Override
    public List findBySQLQueryOther(final String sql) throws Exception {
        return hibernateTemplate.executeFind(new HibernateCallback() {
            @Override
            public List doInHibernate(Session session) throws HibernateException, SQLException {
                SQLQuery sqlQuery = session.createSQLQuery(sql);
                return sqlQuery.list();
            }
        });
    }

    @Override
    public List findByNamedParameter(String sql, String[] parameterNames, Object[] parameterValues) throws Exception {
        return hibernateTemplate.findByNamedParam(sql, parameterNames, parameterValues);
    }

    @Override
    public <T> List findByCriterions(Class<T> cls, List restrictions) throws Exception {
        List objs = new ArrayList();

        try {
            DetachedCriteria criteria = DetachedCriteria.forClass(cls);
            Iterator it = restrictions.iterator();
            while (it.hasNext()) {
                criteria.add((Criterion) it.next());
            }
            objs = hibernateTemplate.findByCriteria(criteria);
        } catch (HibernateException ex) {
            ex.printStackTrace(System.out);
            throw new Exception("Fail to find objects by criterions", ex);
        }
        return objs;
    }

    @Override
    public List findByNamedParameter(String sql, String parameterName, Object parameterValue) throws Exception {
        return findByNamedParameter(sql, new String[]{parameterName}, new Object[]{parameterValue});
    }

    @Override
    public void executeSQLUpdate(final String sql) throws Exception {
        hibernateTemplate.executeFind(new HibernateCallback() {
            @Override
            public List doInHibernate(Session session) throws HibernateException, SQLException {
                SQLQuery sqlQuery = session.createSQLQuery(sql);
                sqlQuery.executeUpdate();
                return null;
            }
        });
    }

    @Override
    public List findByNamedParameter(String sql, List<String> parameterNames, List<Object> parameterValues) throws Exception {
        return findByNamedParameter(sql, parameterNames.toArray(new String[parameterNames.size()]), parameterValues.toArray(new Object[parameterValues.size()]));

    }
}
