package system;

import java.util.ArrayList;
import java.util.Scanner;

import data.*;
import dao.*;

public class Main {

	protected static final Person admin = createAdmin("Sheldon", "Bazinga");
	static ComicDAO comics = new ComicDAO();
	static PersonDAO people = new PersonDAO();
	static LoanDAO loans = new LoanDAO();
	static Scanner input = new Scanner(System.in);
	
	private static Person createAdmin(String user, String pass) {
		ArrayList<Object> values = new ArrayList<>();
		values.add(user);
		values.add(pass);
		Person admin = new Person(0, values);
		return admin;
	}
	
	static void display(String dtype) {
		System.out.println();
		UI.print("retrieving");
		System.out.println(dtype + "...");
		System.out.println();
		ArrayList<Data> data = DAO.selectAll(dtype);
		if (data.isEmpty()) {
			UI.print("noData");
			UI.print(dtype);
			System.out.println();
		}
		else {
			UI.printTitle(dtype.toUpperCase());
			switch (dtype) {
			case "comics":
				UI.printHeader(Comic.getFields());
				break;
			case "people":
				UI.printHeader(Person.getFields());
				break;
			case "loans":
				UI.printHeader(Loan.getFields());
				break;
			default:
				UI.print("errDataType");
				UI.print(dtype);
				break;
			}
			for (Data d : data) {
				d.print();
			}
		}
	}
	
	static void execute(String action, String dtype) {
		System.out.println();
		System.out.println("EXECUTING " + (action +" "+ dtype).toUpperCase());
		UI.print("pmtEnterId");
		int id = askForInt(UI.locale.get("pmtEnterId"));
		if (id != 0) {
			boolean ok = false;
			UI.print("newInfo");
			try {
				switch (dtype) {
				case "comics":
					if (action == "delete") {
						ok = comics.delete(id);
					} else {
						Comic comic = createComic(id);
						ok = comics.insert(comic, action == "edit");
					}
					break;
				case "people":
					if (action == "delete") {
						ok = people.delete(id);
					} else {
						Person person = createPerson(id);
						ok = people.insert(person, action == "edit");
					}
					break;
				case "loans":
					if (action == "delete") {
						ok = loans.delete(id);
					} else {
						Loan loan = createLoan(id);
						ok = loans.insert(loan, action == "edit");
					}
					break;
				default:
					UI.print("errDataType");
					UI.print(dtype);
					break;
				}
			} catch(Exception e) {
				System.out.println("App Error: " + e.getMessage());
			}
			if (ok) {
				UI.print("could");
				UI.print(action);
				UI.print("item");
			} else {
				UI.print("couldNot");
				UI.print(action);
				UI.print("item");
			}
		} else UI.print("cancel");
	}
	
	static Comic createComic(int id) throws Exception {
		ArrayList<Object> values = askForValues(Comic.getFields());
		return new Comic(id, values);
	}

	static Person createPerson(int id) throws Exception {
		ArrayList<Object> values = askForValues(Person.getFields());
		return new Person(id, values);
	}
	
	static Loan createLoan(int id) throws Exception {
		ArrayList<Object> values = askForValues(Loan.getFields());
		return new Loan(id, values);
	}
	
	static ArrayList<Object> askForValues(String[] fields) throws Exception {
		ArrayList<Object> values = new ArrayList<>();
		for (String f : fields) {
			Object v = new Object();
			System.out.print(UI.prompt + f + ": ");
			if (f.startsWith("s")) {
				v = input.next();
			} else if (f.startsWith("i")) {
				v = askForInt(f);
			} else {
				String msg = UI.locale.get("errFieldType1") +
						f + UI.locale.get("errFieldType2");
				throw(new Exception(msg));
			}
			values.add(v);
		}
		return values;
	}

	static int askForInt(String field) {
		int i = 0;
		try {
			i = Integer.parseInt(input.next());
		} catch (NumberFormatException e) {
			UI.print("mustBeInt");
			System.out.print(field + ": ");
			askForInt(field);
		}
		return i;
	}
	
	public static void main(String[] args) {
		UI.start(false);
	}

}
