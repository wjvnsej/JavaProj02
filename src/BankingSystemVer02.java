import ver02.AccountManager;

/*
2단계[컨트롤클래스 생성과 상속] : BankingSystemVer02.java
*/
public class BankingSystemVer02 {

	public static void main(String[] args) {
		
		AccountManager manager = new AccountManager(50);
		manager.start();

	}

}
