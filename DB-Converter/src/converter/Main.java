package converter;

import db.ConnectionManager;
import db.DA;
import db.dto.ResultDTO;

public class Main {

	public static void main(String[] args) {
		
		ConnectionManager src;
		ConnectionManager dest;
		
		try {
			src = new ConnectionManager("src.db", true);
		} catch (Exception e) {
			System.err.println("AN PROBLEM OCCURED REGARDING THE src.db");
			e.printStackTrace();
			return;
		}
		
		try {
			dest = new ConnectionManager("dest.db", false);
		} catch (Exception e) {
			System.err.println("AN PROBLEM OCCURED REGARDING THE dest.db");
			e.printStackTrace();
			return;
		}
		
		try {
			ResultDTO read = DA.read(src);
			ResultDTO write = DA.write(dest, read);
			System.out.println("Read(src):\n\t" + read + "\n\nWrite(dest):\n\t" + write);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
