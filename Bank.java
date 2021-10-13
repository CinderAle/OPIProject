import java.util.ArrayList;
import java.util.Random;

public class Bank {
	
	private String name;//Имя банка
	
	private ArrayList<User> users;//Список пользователей(элементы класса User)
	
	private ArrayList<Account> accounts;//Список аккаунтов(элементы класса Account)
	/**
	 * Create a new Bank object with empty lists of users and accounts
	 * @param name	the name of the bank
	 */
	public Bank(String name) {
		
		this.name = name;
		this.users = new ArrayList<User>();
		this.accounts = new ArrayList<Account>();
	}
	
	/**
	 * Generate a new universally unique ID for a user
	 * @return the uuid
	 */	
	public String getNewUserUUID() {//метод для получения ай-ди пользователя
	
		//ининциализирует
		String uuid;
		Random rng = new Random();
		int len = 6;//длина номера id
		boolean nonUnique;//переменная для проверки уникальности
		
		//продолжаем искать, пока не найдём уникальный ай-ди
		do {
			
			//генерируем число
			uuid = "";
			for (int c = 0; c < len; c++) {
				uuid += ((Integer)rng.nextInt(10)).toString();
			}
			nonUnique = false;
			//проверяем уникальность
			for (User u : this.users) {
				if (uuid.compareTo(u.getUUID()) == 0) {
					nonUnique = true;
					break;
				}
			}
			
		} while(nonUnique);
			
		return uuid;	
		
	}
	
	/**
	 * Generate a new university unique ID for an account
	 * @return
	 */
	public String getNewAccountUUID() {//метод для получения ай-ди аккаунта

		//ининциализирует
		String uuid;
		Random rng = new Random();
		int len = 10;//длина номера id
		boolean nonUnique;//переменная для проверки уникальности
				
		//продолжаем искать, пока не найдём уникальный ай-ди
		do {
					
			//генерируем число
			uuid = "";
			for (int c = 0; c < len; c++) {
				uuid += ((Integer)rng.nextInt(10)).toString();
			}
			nonUnique = false;
			//проверяем уникальность
			for (Account a : this.accounts) {
				if (uuid.compareTo(a.getUUID()) == 0) {
					nonUnique = true;
					break;
				}
			}
					
		} while(nonUnique);
					
		return uuid;	
	}
	/**
	 * Add ad account
	 * @param anAcct	the account to add
	 */
	public void addAccount(Account anAcct) {
		this.accounts.add(anAcct);
	}
	/**
	 * Create a new user of the bank
	 * @param firstName	the user's first name
	 * @param lastName	the user's last name
	 * @param pin		the user's pin
	 * @return			the new User object
	 */
	public User addUser(String firstName, String lastName, String pin) {
		
		//создаёт объект нового пользователя и добавляет в список пользователей
		User newUser = new User(firstName, lastName, pin, this);
		this.users.add(newUser);
		
		//создаём аккаунт для пользователя и добавляет в список пользователей
		//и список банка
		Account newAccount = new Account("Savings", newUser, this);
		newUser.addAccount(newAccount);
		this.accounts.add(newAccount);
		
		return newUser;
	}
	/**
	 * Get the User object associated with a particular userID and pin, if they are valid
	 * @param userID	the UUID of the user to log in
	 * @param pin		the pin of the user
	 * @return			the User object, if the login is successful,or null
	 */
	public User userLogin(String userID, String pin) {
		
		//ищет в списке пользователей
		for (User u : this.users) {
			
			//проверяем верность ай-ди и пароля
			if (u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)) {
				return u;
			}
			
		}
		//если мы не нашли такого пользователя или пароль неверный
		return null;
		
	}
	/**
	 * Get the name of the bank
	 * @return	the name of the bank
	 */
	public String getName() {
		return this.name;
	}
	
}
