import java.util.ArrayList;
import java.security.MessageDigest;//Используется для получения
import java.security.NoSuchAlgorithmException;
//хэшкода, для повышения безопасности

public class User { //Класс описывающий пользователя
	
//функция private закрывает доступ всем кроме пользователя
	
    private String firstName; //Данные пользователя

    private String lastName; //Данные пользователя

    private String uuid; //Уникальное ай-ди пользователя

    private byte pinHash[]; //Пароль пользователя записанный
    //через hash

    private ArrayList<Account> accounts; // Список аккаунтов
    //пользователя(элементами списка являются элементы класса Account)
    
    /**
     * Create a new user
     * @param firstName	the user's first name
     * @param lastName	the user's last name
     * @param pin		the user's account pin number
     * @param theBank	the Bank object that the user is a customer of
     */
    
    public User(String firstName, String lastName, String pin, Bank theBank) {
    
    	//Устанавливает имя пользователя
    	this.firstName = firstName;
    	this.lastName = lastName;//Слово this указывает на 
    	//переменную класса User, вместо переменной метода
    
    	//Сохраняет MDS хэш пинкода, а не исходное значение,по
    	//соображениям безопасности
    	try {//проверяет работу вычисления хэша
    		MessageDigest md = MessageDigest.getInstance("MD5");
    		this.pinHash = md.digest(pin.getBytes());//превращает
    		//пароль в хэш
    	} catch(NoSuchAlgorithmException e) {//ловит ошибки
    		System.err.println("Ошибка типа NoSuchAlgorithmException");//выводит текст об ошибке
    		e.printStackTrace();//помогает разобраться с ошибкои выводит
    		//информацию о ней
    		System.exit(1);//выход из текущей программы
    	}
    	
    	//получение нового уникального ай-ди пользователя
    	this.uuid = theBank.getNewUserUUID();//Использование
    	//метода класса Bank для получения нового ай-ди
    	
    	//создаём пустой список аккаунтов
    	this.accounts = new ArrayList<Account>();
    	
    	//выводим системное сообщение
    	System.out.printf("Новый пользователь %s, %s with ID %s created.\n", lastName,
    			firstName, this.uuid);
    	
    }
    
    /**
     * Add an account for the user
     * @param anAcct	the account to add
     */
    
    public void addAccount(Account anAcct) {//метод только принимает данные, но не возвращает
    	this.accounts.add(anAcct);
    	
    }
    
    /**
     * Return the user's UUId
     * @return	the uuid
     */
    
    public String getUUID() {//метод для поиска ай-ди
    	return this.uuid;
    }
    /**
     * Check whether a given pin matches the true User pin
     * @param aPin	the pin to check
     * @return		whether the pin is valid or not
     */
    public boolean validatePin(String aPin) {

    	
    	try {//проверяет работу вычисления хэша
    		MessageDigest md = MessageDigest.getInstance("MD5");
    		return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash);
    		//возвращает булевское значение верности пароля
    	} catch(NoSuchAlgorithmException e) {//ловит ошибки
    		System.err.println("Ошибка типа NoSuchAlgorithmException");//выводит текст об ошибке
    		e.printStackTrace();//помогает разобраться с ошибкои выводит
    		//информацию о ней
    		System.exit(1);//выход из текущей программы
    	}
    	
    	return false;
    }

    public String getFirstName() {
    	return this.firstName;
    }
    /**
     * Print summaries for the accounts of this user
     */
    public void printAccountsSummary() {
    	
    	System.out.printf("\n\n%s's accounts summary\n", this.firstName);
    	for (int a = 0; a < this.accounts.size(); a++) {		
    		System.out.printf("  %d) %s\n", a+1, this.accounts.get(a).getSummaryLine());
    	}
    	System.out.println();
    
    
    }
    
    public int numAccounts() {
    	return this.accounts.size();
    }
    /**
     * Print transaction history for a particular account
     * @param accIdx	the index of the account to use
     */
    public void printAcctTransHistory(int accIdx) {
    	this.accounts.get(accIdx).printTransHistory();
    }
    /**
     * Get the balance of a particular account
     * @param acctIdx	the index of the account to use
     * @return			the balance of the account
     */
    public double getAcctBalance(int acctIdx) {
    	return this.accounts.get(acctIdx).getBalance();
    }
    /**
     * GEt the UUID of a particular account
     * @param acctIdx	the index of the account to use
     * @return			the UUID of the account 
     */
    public String getAcctUUID(int acctIdx) {
    	return this.accounts.get(acctIdx).getUUID();
    }
    
    public void addAcctTransaction(int acctIdx, double amount, String memo) {
    	this.accounts.get(acctIdx).addTransaction(amount, memo);
    }
    
}
