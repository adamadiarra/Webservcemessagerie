/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ngsystem.dev.messagerieservice.dao.factory;

import java.io.Serializable;
import java.util.HashMap;
import net.ngsystem.dev.messagerieservice.dao.Dao;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Net Killer
 */
public class DaoFactory implements Serializable  {
    
    private final static BeanFactory BEAN_FACTORY;
    private static final HashMap<String, Object> HASH_MAP = new HashMap<>();
    static {
        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});
// of course, an ApplicationContext is just a BeanFactory
        BEAN_FACTORY = (BeanFactory) appContext;
    }
    private DaoFactory() {
    }
    public static DaoFactory getInstance() {
        return new DaoFactory();
    }

    public Dao getDao() {
        Dao dao = (Dao) HASH_MAP.get("dao");
        if (dao == null) {
            dao = (Dao) BEAN_FACTORY.getBean("dao");
            HASH_MAP.put("dao", dao);
        }
        return dao;
    
}
}
