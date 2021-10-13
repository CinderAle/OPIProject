import java.util.ArrayList;

public class Account {

	private String name;//Имя аккаунта
	
	private String uuid;//Уникальное ай-ди пользователя
	
	private User holder;//Показывает, что пользователь владеет аккаунтом

	private ArrayList<Transaction> transactions;//Список транзакции на этом аккаунте
	//(элементами списка являются элементы класса Transaction)
	
	/**
	 * Create a new Account
	 * @param name			the name of the account
	 * @param holder		the User object that holds this account
	 * @param theBank		the bank that issues the account
	 */
	public Account(String name, User holder, Bank theBank) {//конструктор аккаунта
	
		//устанавливает имя аккаунта и статус владения
		this.name = name;//указывает на элементы класса, но не метода
		this.holder = holder;
		
		//получение нового ай-ди для аккаунта
		this.uuid = theBank.getNewAccountUUID();
		
		//инициализация операций
		this.transactions = new ArrayList<Transaction>();	
		
	}
	/**
	 * Get the account ID
	 * @return	the uuid
	 */
	public String getUUID() {
		return this.uuid;
	}
	/**
	 * Get summary line for the account
	 * @return	the string summary
	 */
	public String getSummaryLine() {
		
		//получаем баланс аккаунтов
		double balance = this.getBalance();
		
		//форматируем строку, если баланс отрицательный
		if (balance >= 0 ) {
			return String.format("%s : $%.02f : %s", this.uuid, balance, this.name);
		} else {
			return String.format("%s : $(%.02f) : %s", this.uuid, balance, this.name);
		}
	}
	
	public double getBalance() {
		
		double balance = 0;
		for (Transaction t : this.transactions) {
			balance += t.getAmount();
		}
		return balance;
	}
	/**
	 * Print the transaction history of the account
	 */
	public void printTransHistory() {
		
		System.out.printf("\nTransaction history for account %s\n", this.uuid);
		for (int t = this.transactions.size()-1; t >= 0; t--) {
			System.out.println(this.transactions.get(t).getSummaryLine());
		}
		System.out.println();
	}
	/**
	 * Add a new transaction in this account
	 * @param amount	the amount transacted
	 * @param memo		the transaction memo
	 */
	public void addTransaction(double amount, String memo) {

		
		//создаём новый объект перевода и добавляем его в список переводов
		Transaction newTrans = new Transaction(amount, memo, this);
		this.transactions.add(newTrans);
	}
}
