package br.upf.topicos.especiais.ged.grupo1.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.hibernate.engine.spi.SessionImplementor;


public class JpaUtil {

	private EntityManagerFactory factory;

	public JpaUtil() {
		
	}

	public static JpaUtil getInstance() {
		return JpaUtilInstance.INSTANCE;
	}

	public EntityManager getEntityManager() {
		if (factory == null) {
			factory = Persistence.createEntityManagerFactory("gedGrupo1");
		}
		return factory.createEntityManager();
	}

	private static class JpaUtilInstance {
		public static final JpaUtil INSTANCE = new JpaUtil();
	}

    public Connection getEntityManagerJDBCConnection() throws SQLException { 
        EntityManager em = getEntityManager();
        @SuppressWarnings("resource")
		Session s = (Session) em;
        SessionImplementor sim = (SessionImplementor) s;
        Connection conexao = sim.connection();
        em.close();
        return conexao; 
    }

}
