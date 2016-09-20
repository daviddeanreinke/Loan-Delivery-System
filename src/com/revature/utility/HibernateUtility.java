package com.revature.utility;

import java.net.URL;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * This is a utility to build the session factory
 */
public class HibernateUtility {

	private static final SessionFactory sessionFactory = sessionFactoryCreator();

	private static ServiceRegistry serviceRegistry;

	private static SessionFactory sessionFactoryCreator() {
		try {
			URL r1 = HibernateUtility.class.getResource("/hibernate.cfg.xml");

			Configuration configuration = new Configuration();
			configuration.configure(r1);
			serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
					.buildServiceRegistry();

			return configuration.buildSessionFactory(serviceRegistry);
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
		
	}
	
	public static SessionFactory getSessionFactory(){

		return sessionFactory;
	}
}
