package data;

import java.util.ArrayList;

public class Comic extends Data {
	
	private String title;
	private String genre;
	private int qty;
	
	private static final String[] fields =
		{"sTitle", "sGenre", "iQuantity"};
	
	public Comic(int id, ArrayList<Object> values) {
		this.id = id;
		this.setTitle((String)values.get(0));
		this.setGenre((String)values.get(1));
		this.setQty((int)values.get(2));
	}

	@Override
	public void print() {
		String id = Integer.toString(this.id);
		String qty = Integer.toString(this.getQty());
		System.out.println(id +"	"+ getTitle() +"		"+ getGenre() +"		"+ qty);
	}
	
	public static String[] getFields() {
		return fields;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

}
