package org.persistence;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class ApplicationClass {
	private static SessionFactory factory;
	
	public static void main(String args[]) {
		try {
			factory = new Configuration().configure().buildSessionFactory();			
		} 
		catch (Throwable ex) {
			System.out.println("Factory construction failed");	
			ex.printStackTrace();
		}
		
		ORM_Manager ORM = new ORM_Manager(factory);
		
		ORM.addEmployee("Krzysztof", "Kowalak", new GregorianCalendar(1986,Calendar.DECEMBER,6), "Analyst", 6700);
	}
}

class ORM_Manager {
	private SessionFactory factory;
	
	public ORM_Manager(SessionFactory factory) {
		this.factory = factory;
	}
	public void addEmployee(String n, String sn, Calendar bd, String pos, int sal) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Employee newemployee = new Employee(n,sn,bd,pos,sal);
			
			session.save(newemployee);
			tx.commit();			
		}
		catch (Throwable ex) {
			if(tx!=null) 
				tx.rollback();			
		}
		finally {
			session.close();
		}
	}
}
