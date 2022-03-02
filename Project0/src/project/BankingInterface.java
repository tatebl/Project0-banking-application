package project;

public interface BankingInterface {
	public void withdraw(String username);
	public void transfer(String fromUsername, String toUserName);
	void deposit(String username);
}
