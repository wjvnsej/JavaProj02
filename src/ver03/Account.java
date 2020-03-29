package ver03;
/*
3단계[추상클래스&예외처리] : BankingSystemVer03.java
*/
public abstract class Account {

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
