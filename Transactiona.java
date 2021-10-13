import java.util.Date;

public class Transaction {

	private double amount;//Сумма финансовой операции
	
	private Date timestamp;//Время и дата операции
	
	private String memo;//Приписка к данной транзакции
	
	private Account inAccount;//С какого аккаунта произошла транзакция
	
	/**
	 * Create a new transaction
	 * @param amount	the amount transacted
	 * @param inAccount	the account the transaction belongs to
	 */
	public Transaction(double amount, Account inAccount) {
	
		this.amount = amount;
		this.inAccount = inAccount;
		this.timestamp = new Date();
		this.memo = "";
		
	}
	/**
	 * Create a new transaction
	 * @param amount	the amount transacted
	 * @param memo		the memo for the transaction
	 * @param inAccount	the account the transaction belongs to
	 */
	public Transaction(double amount, String memo, Account inAccount) {
	
		//вызовем 2-аргументный конструктор
		this(amount, inAccount);
		
		//создаём memo
		this.memo = memo;
	}
	
	public double getAmount() {
		return this.amount;
	}
	/**
	 * Get a string summarizing the transaction
	 * @return	the summary string
	 */
	public String getSummaryLine() {
		
		if (this.amount >= 0) {
			return String.format("%s : $%.02f : %s", this.timestamp.toString(),
					this.amount, this.memo);
		} else {
		return String.format("%s : $(%.02f) : %s", this.timestamp.toString(),
				-this.amount, this.memo);
		}
	}
}
