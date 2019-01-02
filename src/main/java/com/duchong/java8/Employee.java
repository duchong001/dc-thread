package com.duchong.java8;

public class Employee {

	private String name;
	private int age;
	private char gender;
	private double salary;

	public Employee() {
	}

	public Employee(String name, int age, char gender, double salary) {
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.salary = salary;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "Employee [name=" + name + ", age=" + age + ", gender=" + gender
				+ ", salary=" + salary + "]";
	}

}
