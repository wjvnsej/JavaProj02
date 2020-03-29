import ver03.AccountManager;

/*
3단계[추상클래스&예외처리] : BankingSystemVer03.java
*/
public class BankingSystemVer03 {

	public static void main(String[] args) {
		
		AccountManager manager = new AccountManager(50);
		manager.start();

	}

}
