import java.sql.*;
import java.util.*;
public class BankingApp {
	private static final String url="jdbc:mysql://localhost:3306/banking_system";
	private static final String username="root";
	private static final String password="122003";
	public static void main(String args []) throws ClassNotFoundException ,SQLException {
		try {
			Class.forName("com.sql.cj.jdbc.Driver");
		}
		catch(ClassNotFoundException e){
			System.out.println(e.getMessage());
		}
		try {
			Connection connection=DriverManager.getConnection(url, username, password);
			Scanner scanner =new Scanner(System.in);
			Users user =new Users(connection,scanner);
			Accounts account=new Accounts(connection,scanner);
			AccountManager accountmanager=new AccountManager(connection,scanner);
			
			String email;
			long accountNumber;
			
			while(true) {
				System.out.println("*** Welcome To Banking System");
				System.out.println();
				System.out.println("1. Register");
				System.out.println("2. Login");
				System.out.println("3. Exit");
				System.out.print("Enter Your Choice:");
				int choice=scanner.nextInt();
				
				switch(choice) {
				case 1:
					user.register();
					break;
				case 2:
					email=user.login();
					if(email!=null) {
						System.out.println();
						System.out.println("User Logged In!");
						if(!account.account_exist(email)) {
							System.out.println("");
							System.out.println("1. Open An Account");
							System.out.println("2. Exit!");
							if(scanner.nextInt()==1) {
								accountNumber=account.open_account(email);
								System.out.println("Account Created Successfully");
								System.out.println("Your Account Number :"+accountNumber);
							}
							else {
								break;
							}
						}
						accountNumber=account.getAccount_number(email);
						int choice1=0;
						while(choice1!=5) {
							System.out.println();
							System.out.println("1. Debit Money");
							System.out.println("2. Credit Money");
							System.out.println("3. Transfer Money");
							System.out.println("4. Check Balance");
							System.out.println("5. Log Out");
							System.out.print("Enter Your Choice:");
							choice1=scanner.nextInt();
							switch(choice1) {
							case 1:
								accountmanager.debit_money(accountNumber); 
								break;
							case 2:
								accountmanager.credit_money(accountNumber);
								break;	
							case 3:
								accountmanager.transfer_money(accountNumber);
								break;
							case 4:
								accountmanager.getBalance(accountNumber);
								break;
							default:
								System.out.println("Enter Valid Choice!");
								break;
							}
						}
					}else {
						System.out.println("Incorrect Password Or Email Adress!");
					}
				case 3:
					System.out.println("THANK YOU FOR USING BANKING SYSTEM!!!");
                    System.out.println("Exiting System!");
                    return;
                default:
                	System.out.println("Enter Valid Choice");
                	break;
				}
				
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	
	}
}
