package data;

import java.util.ArrayList;

public class Loan extends Data{
	
	private int person;
	private int comic;
	private String date;
	
	private static final String[] fields =
		{"iPerson", "iComic", "sDate"};
	
	public Loan(int id, ArrayList<Object> values) {
		this.id = id;
		this.setPerson((int)values.get(0));
		this.setComic((int)values.get(1));
		this.setDate((String)values.get(2));
	}

	@Override
	public void print() {
		String id = Integer.toString(this.id);
		String person = Integer.toString(this.getPerson());
		String comic = Integer.toString(this.getComic());
		System.out.println(id +"	"+ person +"		"+ comic +"		"+ getDate());
	}
	
	public static String[] getFields() {
		return fields;
	}
	
	public int getPerson() {
		return person;
	}

	public void setPerson(int person) {
		this.person = person;
	}

	public int getComic() {
		return comic;
	}

	public void setComic(int comic) {
		this.comic = comic;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
