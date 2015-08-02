package data;

import java.util.ArrayList;

public class Person extends Data {
	
	private String name;
	private String email;
	
	private static final String[] fields =
		{"sName", "sE-mail"};
	
	public Person(int id, ArrayList<Object> values) {
		this.id = id;
		this.setName((String)values.get(0));
		this.setMail((String)values.get(1));
	}

	@Override
	public void print() {
		String id = Integer.toString(this.id);
		System.out.println(id +"	"+ getName() +"		"+ getMail());
	}
	
	public static String[] getFields() {
		return fields;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return email;
	}

	public void setMail(String mail) {
		this.email = mail;
	}
}
