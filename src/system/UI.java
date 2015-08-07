package system;

import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UI extends Main {

	static final int linelength = 50;
	
	static final String prompt = ">> ";

	static final String border = "*";
	
	static final String menuLang = "\n[1] English\n[2] Español\n[3] Argentino\n\n";
	
	static final String lineSep = System.getProperty("line.separator");
	
	static final String usrdir = System.getProperty("user.dir");
	
	public static Map<String, String> locale = new HashMap<>();
	
	static {
		loadDefaultLocale();
	}
	
	static void loadDefaultLocale() {
		try {
			loadLocale("EN");
		} catch (FileNotFoundException e) {
			System.out.println("Error: Default locale file not found. Cannot load UI.");
		}
	}
	
	static void loadLocale(String fileName) throws FileNotFoundException {
	    locale.clear();
		String filePath = usrdir + "/locales/" + fileName + ".txt";
		try(Scanner in = new Scanner(new File(filePath), "UTF-8")) {
	        while(in.hasNextLine()) {
	        	String parts[] = in.nextLine().split(" = ");
		        locale.put(parts[0], parts[1]);
	        }
		}
	}
	
	static void start(boolean looping) {
		if (!looping) {
			print("welcome");
			print("menuMain");
			print("pmtChoose");
		}
		String res = Main.input.next();
		switch (res) {
		case "1":
			login();
			break;
		case "2":
			showMenuGuest(false);
			break;
		case "3":
			selectLanguage(false);
			break;
		default:
			print("pmtChoose2");
			start(true);
			break;
		}
		
	}

	static void login() {
		boolean isAdmin;
		Console cons;
		char[] passwd = null;
		print("login");
		print("User: ");
		String user = Main.input.next();
		if ((cons = System.console()) != null &&
		    (passwd = cons.readPassword("%s", "Pass: ")) != null) {
			String pass = "";
			for (char c : passwd) pass += c;
		    isAdmin = isAdmin(user, pass);
		    pass = "";
		    Arrays.fill(passwd, ' ');
		} else {
			print("Pass: ");
			isAdmin = isAdmin(user, Main.input.next());
		}
		if (isAdmin) {
			showMenuAdmin(false);
		} else {
			print("nope");
			start(false);
		}
	}

	static boolean isAdmin(String user, String pass) {
		return user.equals(Main.admin.getName()) && pass.equals(Main.admin.getMail());
	}

	static void showMenuAdmin(boolean looping) {
		if (!looping){
			clearConsole();
			print("menuAdmin");
			print("pmtChoose");
		}
		String opt1 = Main.input.next();
		switch (opt1) {
		case "1":
			opt1 = "comics";
			break;
		case "2":
			opt1 = "people";
			break;
		case "3":
			opt1 = "loans";
			break;
		case "4":
			print("logout");
			start(false);
			break;
		default:
			print("pmtChoose2");
			showMenuAdmin(true);
			break;
		}
		showMenuAdmin2(opt1, false);
	}

	static void showMenuAdmin2(String data, boolean looping) {
		if (!looping) {
			clearConsole();
			Main.display(data);
			printTitle(locale.get("options"));
			print("menuAdmin2");
			print(draw(border, linelength)+"\n");
			print("pmtChoose");
		}
		String opt2 = Main.input.next();
		if (opt2.equals("4")) {
			showMenuAdmin(false);
		} else {
			switch (opt2) {
			case "1":
				Main.execute("add", data);
				break;
			case "2":
				Main.execute("delete", data);
				break;
			case "3":
				Main.execute("edit", data);
				break;
			default:
				print("pmtChoose2");
				showMenuAdmin2(data, true);
				break;
			}
			showMenuAdmin2(data, false);
		}
	}

	static void showMenuGuest(boolean looping) {
		if(!looping) {
			print("menuGuest");
			print("pmtChoose");
		}
		String opt1 = Main.input.next();
		switch (opt1) {
		case "1":
			Main.display("comics");
			break;
		case "2":
			login();
			break;
		default:
			print("pmtChoose2");
			showMenuGuest(true);
			break;
		}
		showMenuGuest(false);
	}

	static void selectLanguage(boolean looping) {
		if (!looping) {
			print(menuLang);
			print("pmtChoose");
		}
		String res = Main.input.next();
		try {
			switch (res) {
			case "1":
				loadLocale("EN");
				break;
			case "2":
				loadLocale("ES");
				break;
			case "3":
				loadLocale("AR");
				break;
			default:
				print("pmtChoose2");
				selectLanguage(true);
				break;
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error: Locale file not found. Loading default locale.");
			loadDefaultLocale();
		}
		print("localized");
		start(false);
	}
	
	static String draw(String s, int i) {
		String res = "";
		while (res.length()<i) res += s;
		return res;
	}
	/*
	static String readFile(String filePath) throws IOException {
		//http://stackoverflow.com/questions/326390/
		//oneliner alternative:
		//Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
		byte[] encoded = Files.readAllBytes(Paths.get(filePath));
		return new String(encoded, StandardCharsets.UTF_8);
	}
	*/
	static void print(String str) {
		String ls;
		if ((ls = locale.get(str)) != null) {
			if (str.startsWith("pmt")) ls = prompt + ls;
			System.out.print(ls.replace("\\n", lineSep));
		} else {
			System.out.print(str);
		}
	}
	
	static void printTitle(String title) {
		int middle = title.length() + 2;
		int before = linelength/2 - middle/2;
		int after = linelength - before - middle;
		String out = draw(border, before)
				+" "+ title.toUpperCase() +" "
				+ draw(border, after);
		System.out.println(out);
	}
	
	static void printHeader(String[] fields) {
		String header = "Id	";
		for (String f : fields) {
			header += f + "		";
		}
		print(header + "\n");
	}
	
	static void clearConsole() {
		//FIXME: this is not working
	    try {
	        final String os = System.getProperty("os.name");
	        if (os.contains("Windows")) {
	            Runtime.getRuntime().exec("cls");
	        } else {
	            Runtime.getRuntime().exec("clear");
	        }
	    } catch (final Exception e) {
	        System.out.println(e.getMessage());
	    }
	}
	
}
