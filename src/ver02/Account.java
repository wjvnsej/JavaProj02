package ver02;

public class Account {

	String accountNum, name;
	int balance, rate;
		
	public Account
		(String accountNum, String name, int balance, int rate) {
		this.accountNum = accountNum;
		this.name = name;
		this.balance = balance;
		this.rate = rate;
	}
	
	public void rateCalcul(int inputMoney) {
		
	}
	
	public void showAccInfo() {
		
		System.out.println("계좌번호 : " + accountNum);
		System.out.println("이름 : " + name);
		System.out.println("잔액 : " + balance);
		
	}
	
	
}
