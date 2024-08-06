import java.sql.*;
import java.util.*;
public class Users {
	private Connection connection;
	private Scanner scanner;
	
	public Users(Connection connection,Scanner scanner) {
		this.connection=connection;
		this.scanner=scanner;
	}

	public void register() {
		scanner.nextLine();
		System.out.print("Full Name:");
		String fullName=scanner.nextLine();
		System.out.print("Email:");
		String email=scanner.nextLine();
		System.out.print("Password:");
		String password=scanner.nextLine();
		
		if(user_exit(email)) {
			System.out.println("User Already Exists for this Email Address!!");
            return;
		}
		String register_query="INSERT INTO USER(full_name,email,password) VALUES(?,?,?)";
		try {
			PreparedStatement preparedstatement =connection.prepareStatement(register_query);
			preparedstatement.setString(1, fullName);
			preparedstatement.setString(2,email);
			preparedstatement.setString(3, password);
			int affected_query=preparedstatement.executeUpdate();
			if(affected_query>0) {
				System.out.println("Registration Successfull!");
			}else {
				System.out.println("Registration Failed!");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
		
		

	private boolean user_exit(String email) {
		String query="SELECT * FROM user WHERE email=?";
		try {
			PreparedStatement preparedstatement=connection.prepareStatement(query);
			preparedstatement.setString(1, email);
			ResultSet rs=preparedstatement.executeQuery();
			if(rs.next()) {
				return true;
			}
			else {
				return false;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public String login() {
		scanner.nextLine();
		System.out.print("Email:");
		String email=scanner.nextLine();
		System.out.print("Password:");
		String password=scanner.nextLine();
		
		String login_query="SELECT * FROM USER WHERE email=? AND password=?";
		try {
			PreparedStatement preparestatement=connection.prepareStatement(login_query);
			preparestatement.setString(1, email);
			preparestatement.setString(2, password);
			ResultSet rs=preparestatement.executeQuery();
			if(rs.next()) {
				return email;
			}
			else {
				return null;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
