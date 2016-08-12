package org.persistence;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Employee {
	private int id;
	private String name;
	private String surname;
	private Calendar birthdate;
	private String position;
	private int salary;
	
	public Employee() {}
	public Employee(String name, String surname,
					Calendar birthdate, String position, int salary) {
		
		this.name = name;
		this.surname = surname;
		this.birthdate = birthdate;
		this.position = position;
		this.salary = salary;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public Calendar getBirthdate() {
		return birthdate;
	}	
	public void setBirthdate(Calendar birthdate) {
		this.birthdate = birthdate;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
}
