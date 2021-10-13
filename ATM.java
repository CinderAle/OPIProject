import java.util.Scanner;

public class ATM {
	
	public static void main(String[] args) {
		
		//создаём сканер
		Scanner sc = new Scanner(System.in);
		
		//создаём банк
		Bank theBank = new Bank("Bank of Stolin");
		
		//добавляем пользователя, который уже создал аккаунт
		User aUser = theBank.addUser("John", "Doe", "1234");
		
		//добавляем проверенный аккаунт для пользователя
		Account newAccount = new Account("Checking", aUser, theBank);
		aUser.addAccount(newAccount);
		theBank.addAccount(newAccount);
		
		User curUser;
		while (true) {
			
			//оставаться в окне входа до успешного входа
			curUser = ATM.mainMenuPrompt(theBank, sc);
			
			//оставаться в главном меню пока пользователь не вышел
			ATM.printUserMenu(curUser, sc);
		}
	}
	/**
	 * Print the ATM's login menu
	 * @param theBank	the Bank object whose accounts to use
	 * @param sc		the Scanner object to user for user input
	 * @return
	 */
	public static User mainMenuPrompt(Bank theBank, Scanner sc) {
		
		//инициализациия
		String userID;
		String pin;
		User authUser;
		//запрашиваем ай-ди или пин пока не получим верные данные
		do {
		
			System.out.printf("\nWelcome to %s\n\n", theBank.getName());//вывод о банке
			System.out.print("Enter user ID: ");//запрос ай-ди
			userID = sc.nextLine();
			System.out.printf("Enter pin: ");
			pin = sc.nextLine();

			//пытаемся получить объект пользователя соответствующий ай-ди и паролю
			authUser = theBank.userLogin(userID, pin);
			if (authUser == null) {
				System.out.println("Incorrect user ID/pin combination. " +
							"Please try agaein.");
			}
			
			
			
		} while(authUser == null);//продолжаем запрашивать пока не будет успешного входа
		
		return authUser;
		
	}
	
	public static void printUserMenu(User theUser, Scanner sc) {
		
		boolean isIncorrect;
		
		//выводит список аккаунтов пользователя
		theUser.printAccountsSummary();
		
		//инициализация
		int choice = 0;
		
		//пользвоательское меню
		do {
			System.out.printf("Welcome %s, what would you like to do?\n",
					theUser.getFirstName());
			System.out.println(" 1) Show account transaction history");//история транзакций
			System.out.println(" 2) Withdraw");//снять деньги
			System.out.println(" 3) Deposit");//пополнить счёт
			System.out.println(" 4) Transfer");//перевод
			System.out.println(" 5) Quit");//выход
			System.out.println();
			System.out.print("Enter choice: ");//выбор пункта меню
			do {
				isIncorrect = false;
				try {
					choice = Integer.parseInt(sc.nextLine());//ввод номера пункта
				}catch(Exception e ) {
					System.out.print("Invalid choice, try again. Enter choice: ");
					isIncorrect = true;
				}
			}while(isIncorrect);
			if (choice < 1 || choice > 5) {//проверка правильности выбора пункта
				System.out.println("Invalid choice. Please choose 1-5");
			}
		} while (choice < 1 || choice > 5 || isIncorrect);
		
		//процесс выбора
		switch(choice) {
		
		case 1:
			ATM.showTransHistory(theUser, sc);
			break;
		case 2:
			ATM.withdrawFunds(theUser, sc);
			break;
		case 3:
			ATM.depositFunds(theUser, sc);
			break;
		case 4:
			ATM.transferFunds(theUser, sc);
			break;
		case 5:	
			//gobble up rest of previous input
			sc.nextLine();
			break;
		}
		
		//убираем меню когда пользователь хочет его закрыть
		if (choice != 5) {
			ATM.printUserMenu(theUser, sc);
		}
	}
	/**
	 * Show the transaction history for an account
	 * @param theUser	the logged-in User object
	 * @param sc		the Scanner object used for user input
	 */
	public static void showTransHistory(User theUser, Scanner sc) {
		
		boolean isIncorrect;
		
		int theAcct = 0;
		
		//получаем аккаунт историю платежей которого запрашивают
		do {
			System.out.printf("Enter the number (1-%d) of the account\n" +
					"whose transactions you want to see: ", 
					theUser.numAccounts());
			do {
				isIncorrect = false;
				try {
					theAcct = Integer.parseInt(sc.nextLine()) - 1;
				}catch(Exception e ) {
					System.out.print("Invalid acct, try again. Enter acct number: ");
					isIncorrect = true;
				}
			}while(isIncorrect);
			if (theAcct < 0 || theAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
			
		} while(theAcct < 0 || theAcct >= theUser.numAccounts());
		
		//выводим историю транзакций
		theUser.printAcctTransHistory(theAcct);
		
	}
	/**
	 * Process transferring funds from one account to another
	 * @param theUser	the logged-in User object
	 * @param sc		the Scanner object used for user input
	 */
	public static void transferFunds(User theUser, Scanner sc) {
		
		boolean isIncorrect;
		
		//инициализация
		int fromAcct = 0;
		int toAcct = 0;
		double amount = 0;
		double acctBal;
		
		//получаем аккаунт откуда производим трансфер
		do {	
			System.out.printf("Enter the number (1-%d) of the account\n" +
					"to transfer from: ", theUser.numAccounts());
			do {
				isIncorrect = false;
				try {
					fromAcct = Integer.parseInt(sc.nextLine()) - 1;
				}catch(Exception e ) {
					System.out.print("Invalid acct, try again. Enter acct number: ");
					isIncorrect = true;
				}
			}while(isIncorrect);
			if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		} while(fromAcct < 0 || fromAcct >= theUser.numAccounts());
		acctBal = theUser.getAcctBalance(fromAcct);
		
		//получаем аккаунт на который переводим
		do {	
			System.out.printf("Enter the number (1-%d) of the account\n" +
					"to transfer to: ", theUser.numAccounts());
			do {
				isIncorrect = false;
				try {
					toAcct = Integer.parseInt(sc.nextLine()) - 1;
				}catch(Exception e ) {
					System.out.print("Invalid acct, try again. Enter acct number: ");
					isIncorrect = true;
				}
			}while(isIncorrect);
			if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		} while(toAcct < 0 || toAcct >= theUser.numAccounts());
		
		//получаем сумму которую переводим 
		do {
			System.out.printf("Enter the amount to transfer (max $%.02f): $",
					acctBal);
			do {
				isIncorrect = false;
				try {
					amount = Double.parseDouble(sc.nextLine());
				}catch(Exception e ) {
					System.out.print("Invalid amount, try again. Enter the amount: ");
					isIncorrect = true;
				}
			}while(isIncorrect);
			if (amount <= 0) {
				System.out.println("Amount must be greater then zero.");
			} else if (amount > acctBal) {
				System.out.printf("Amount must not be greater then\n" + 
						"balance of $%.02f.\n", acctBal);
			}
		} while(amount <= 0 || amount > acctBal);
		
		//наконец-то осуществляем сам перевод
		theUser.addAcctTransaction(fromAcct, -1*amount, String.format(
				"Transfer to account %s", theUser.getAcctUUID(toAcct)));
		theUser.addAcctTransaction(toAcct, amount, String.format(
				"Transfer to account %s", theUser.getAcctUUID(fromAcct)));
	}
	
	/**
	 * Process a fund withdraw from an account
	 * @param theUser	the logged-in User object
	 * @param sc		the Scanner object used for user input
	 */
	public static void withdrawFunds(User theUser, Scanner sc) {
		
		//инициализация
		int fromAcct = 0;
		double amount = 0;
		double acctBal;
		boolean isIncorrect;
		String memo;
		
		//получаем аккаунт откуда производим трансфер
		do {	
			System.out.printf("Enter the number (1-%d) of the account\n" +
					"to withdraw from: ", theUser.numAccounts());
			do {
				isIncorrect = false;
				try {
					fromAcct = Integer.parseInt(sc.nextLine()) - 1;
				}catch(Exception e ) {
					System.out.print("Invalid acct, try again. Enter acct number: ");
					isIncorrect = true;
				}
			}while(isIncorrect);
			if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		} while(fromAcct < 0 || fromAcct >= theUser.numAccounts());
		acctBal = theUser.getAcctBalance(fromAcct);
		
		//получаем сумму которую переводим 
		do {
			System.out.printf("Enter the amount to withdraw (max $%.02f): $",
					acctBal);
			do {
				isIncorrect = false;
				try {
					amount = Double.parseDouble(sc.nextLine());
				}catch(Exception e ) {
					System.out.print("Invalid amount, try again. Enter the amount: ");
					isIncorrect = true;
				}
			}while(isIncorrect);
			if (amount <= 0) {
				System.out.println("Amount must be greater then zero.");
			} else if (amount > acctBal) {
				System.out.printf("Amount must not be greater then\n" + 
						"balance of $%.02f.\n", acctBal);
			}
		} while(amount <= 0 || amount > acctBal);
		
		//gobble up rest of previous input
		sc.nextLine();
		
		//get the memo
		System.out.print("Enter a memo: ");
		memo = sc.nextLine();
		
		//do the withdraw
		theUser.addAcctTransaction(fromAcct, -1*amount, memo);
	}
	
	/**
	 * Process a fund deposit to an account
	 * @param theUser	the logged-in User object
	 * @param sc		the Scanner object used for user input
	 */
	public static void depositFunds(User theUser, Scanner sc) {
		
		//инициализация
		int toAcct = 0;
		double amount = 0;
		double acctBal;
		boolean isIncorrect;
		String memo = "";
		
		//получаем аккаунт откуда производим трансфер
		do {	
			System.out.printf("Enter the number (1-%d) of the account\n" +
					"to deposit in: ", theUser.numAccounts());
			do {
				isIncorrect = false;
				try {
					toAcct = Integer.parseInt(sc.nextLine()) - 1;
				}catch(Exception e ) {
					System.out.print("Invalid acct, try again. Enter acct number: ");
					isIncorrect = true;
				}
			}while(isIncorrect);
			if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		} while(toAcct < 0 || toAcct >= theUser.numAccounts());
		acctBal = theUser.getAcctBalance(toAcct);
		
		//получаем сумму которую переводим 
		do {
			System.out.printf("Enter the amount to transfer (max $%.02f): $",
					acctBal);
			do {
				isIncorrect = false;
				try {
					amount = Double.parseDouble(sc.nextLine());
				}catch(Exception e ) {
					System.out.print("Invalid amount, try again. Enter the amount: ");
					isIncorrect = true;
				}
			}while(isIncorrect);
			if (amount <= 0) {
				System.out.println("Amount must be greater then zero.");
			}
		} while(amount <= 0);
		
		//gobble up rest of previous input
		sc.nextLine();
		
		//get the memo
		System.out.print("Enter a memo: ");
		memo = sc.nextLine();
		
		//do the deposit
		theUser.addAcctTransaction(toAcct, amount, memo);
	}
}
