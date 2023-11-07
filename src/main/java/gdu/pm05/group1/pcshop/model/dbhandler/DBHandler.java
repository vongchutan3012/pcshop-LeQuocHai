package gdu.pm05.group1.pcshop.model.dbhandler;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.RollbackException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import gdu.pm05.group1.pcshop.interfaces.Destroyable;
import jakarta.servlet.ServletContext;

public class DBHandler implements IDBHandler, Destroyable {
    // FIELDS:
    private SessionFactory sessionFactory;

    // CONSTRUCTORS:
    public DBHandler(ServletContext context) {
        // Create a blank configuration
        Configuration config = new Configuration();

        // Get config info from hibernate.cfg.xml file
        try {
            config = config.configure(
                context.getResource("hibernate.cfg.xml")
            );
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        // Build session factory
        try {
            sessionFactory = config.buildSessionFactory();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DBHandler(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // METHODS:
    @Override
    public void destroy() {
        // Close sessionFactory
        sessionFactory.close();
    }

    @Override
    public void save(Object... objects) {
        // Open session
        Session session = this.openSession();

        // Session null case
        if (session == null) {
            return;
        }

        // Begin transaction
        Transaction transaction = session.beginTransaction();

        // Save all objects in objects
        for (Object object : objects) {
            session.saveOrUpdate(object);
        }

        // Committing changes
        this.commitTransaction(transaction);

        // Close session
        this.closeSession(session);
    }

    @Override
    public void remove(Object... objects) {
        // Open session
        Session session = this.openSession();

        // Session null case
        if (session == null) {
            return;
        }

        // Begin transaction
        Transaction transaction = session.beginTransaction();

        // Removing objects
        for (Object object : objects) {
            session.remove(object);
        }

        // Commit transaction
        this.commitTransaction(transaction);

        // Close session
        this.closeSession(session);
    }

    @Override
    public <T> T get(Class<T> objClass, T target) {
        // Get objClass's entity name
        String entityName = this.getEntityName(objClass);

        // Not entity case
        if (entityName == null) {
            return null;
        }

        // Open session
        Session session = this.openSession();

        // Session null case
        if (session == null) {
            return null;
        }

        // Create get query
        Query<T> query = this.createGetQuery(session, entityName, objClass, target);

        // List
        List<T> list = query.list();

        // Result declaration
        T result = null;
        
        // List null case
        if (list == null) {
            result = null;
        }

        // List's empty case
        else if (list.isEmpty()) {
            result = null;
        }

        // Found case
        else {
            // Get target entity in DB and assign into result
            result = list.get(0);
        }

        // Close session
        this.closeSession(session);

        // Return result
        return result;
    }

    @Override
    public <T> T get(Class<T> objClass, HQLParameter... parameters) {
        // Get objClass's entity name
        String entityName = this.getEntityName(objClass);

        // objClass not entity case
        if (entityName == null) {
            return null;
        }

        // Open session
        Session session = this.openSession();

        // Session null case
        if (session == null) {
            return null;
        }

        // Create get query
        Query<T> query = this.createGetQuery(
            session, entityName, objClass, parameters
        );

        // List
        List<T> list = query.list();

        // Result declaration
        T result = null;

        // List null case
        if (list == null) {
            result = null;
        }

        // List empty case
        else if (list.isEmpty()) {
            result = null;
        }

        // Found case
        else {
            result = list.get(0);
        }

        // Close session
        this.closeSession(session);

        // Return result
        return result;
    }

    @Override
    public <T> List<T> getAll(Class<T> objClass) {
        // Get objClass's entity name
        String entityName = this.getEntityName(objClass);

        // Object class not entity class case
        if (entityName == null) {
            return null;
        }

        // Open session
        Session session = this.openSession();

        // Create get all query
        Query<T> query = this.createGetAllQuery(session, entityName, objClass);

        // List
        List<T> all = query.list();

        // Close session
        this.closeSession(session);

        // Return all
        return all;
    }

    @Override
    public void refresh(Object... objects) {
        // Open session
        Session session = this.openSession();

        // Session null case
        if (session == null) {
            return;
        }

        // Refreshing objects
        for (Object object : objects) {
            session.refresh(object);
        }

        // Close session
        this.closeSession(session);
    }

    private <T> Query<T> createGetAllQuery(
        Session session,
        String entityName,
        Class<T> objClass
    ) {
        // Create hql string
        String hql = "FROM @entityName".replace("@entityName", entityName);

        // Create query
        Query<T> query = session.createQuery(hql, objClass);

        // Return query
        return query;
    }

    private <T> Query<T> createGetQuery(
        Session session,
        String entityName,
        Class<T> objClass,
        HQLParameter... parameters
    ) {
        // Create hql string
        String hql = "FROM @entityName AS E WHERE @conditions";
        
        // Assign entity name for hql
        hql = hql.replace("@entityName", entityName);

        //  Conditions making
        String condition = "";
        for (HQLParameter parameter : parameters) {
            if (!condition.isBlank()) {
                condition += " AND ";
            }

            condition += "@paramName=:@paramName".replaceAll(
                "@paramName", parameter.getName()
            );
        }

        // Assign conditions for hql
        hql = hql.replace(
            "@conditions", condition
        );

        // Create query
        Query<T> query = session.createQuery(hql, objClass);

        // Set parameters for query
        for (HQLParameter parameter : parameters) {
            query.setParameter(parameter.getName(), parameter.getValue());
        }

        // Return query
        return query;
    }

    private <T> Query<T> createGetQuery(
        Session session,
        String entityName,
        Class<T> objClass,
        T target
    ) {
        // Create hql string
        String hql = "FROM @entityName AS E WHERE @conditions";
        
        // Format entity name
        hql = hql.replace("@entityName", entityName);

        // Format conditions
        String condition = "";
        Field[] fields = objClass.getDeclaredFields();
        List<HQLParameter> parameters = new ArrayList<>();
        for (Field field : fields) {
            // Get id annotation of field
            Id[] ids = field.getAnnotationsByType(Id.class);

            // Field not id case
            if (ids.length < 1) {
                continue;
            }

            // Field id case
            // Get field's name
            String fieldName = field.getName();

            // Add AND clause to condition if condition not blank
            if (!condition.isBlank()) {
                condition += " AND ";
            }

            // Add condition
            condition += "E.@fieldName=:@fieldName".replaceAll(
                "@fieldName", fieldName
            );

            // Get field's getter method
            Method fieldGetter = null;
            try {
                fieldGetter = objClass.getDeclaredMethod(
                    "get" + fieldName.replaceFirst(
                        ""+fieldName.charAt(0),
                        ""+Character.toUpperCase(fieldName.charAt(0))
                    )
                );
            }
            catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            catch (SecurityException e) {
                e.printStackTrace();
            }

            // Get field's value
            Object fieldValue = null;
            try {
                fieldValue = fieldGetter.invoke(target);
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            // Create HQLParameter
            HQLParameter hqlParameter = new HQLParameter();
            hqlParameter.setName(fieldName);
            hqlParameter.setValue(fieldValue);

            // Add HQLParameter into list of parameters
            parameters.add(hqlParameter);
        }
        hql = hql.replace("@conditions", condition);

        // Create query
        Query<T> query = session.createQuery(hql, objClass);

        // Set parameters for query
        for (HQLParameter parameter : parameters) {
            query.setParameter(
                parameter.getName(),
                parameter.getValue()
            );
        }

        // Return query
        return query;
    }

    private <T> String getEntityName(Class<T> objClass) {
        // Get entity annotation in objClass
        Entity[] annotations = objClass.getAnnotationsByType(Entity.class);
        
        // Not entity case
        if (annotations.length < 1) {
            return null;
        }

        // Get first entity annotation
        Entity entity = annotations[0];

        // Get entity name
        String name = entity.name();

        // Return name
        return name;
    }

    private Session openSession() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return session;
    }

    private void commitTransaction(Transaction transaction) {
        try {
            transaction.commit();
        }
        catch (RollbackException e) {
            try {
                transaction.rollback();
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    private void closeSession(Session session) {
        try {
            session.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
