package ver01;

import java.util.Scanner;

import ver01.Account;

public class Account {
	String accountNum, name;
	int balance;
	
	private Account[] accounts;
	private int accCnt;
	
	public Account(int num) {
		accounts = new Account[num];
		accCnt = 0;
	}
	
	public Account(String accountNum, String name, int balance) {
		this.accountNum = accountNum;
		this.name = name;
		this.balance = balance;
	}
	
	
	//메뉴출력
	public void showMenu() {
		System.out.println("-----Menu------");
		System.out.println("1. 계좌개설");
		System.out.println("2. 입금");
		System.out.println("3. 출금");
		System.out.println("4. 전체계좌정보출력");
		System.out.println("5. 프로그램종료");
		System.out.print("선택 : ");
	}
	
	public void start() {
		
		while(true) {

			showMenu();

			Scanner scan = new Scanner(System.in);
			int choice = scan.nextInt();
			scan.nextLine();
			
			switch (choice) {
			case MenuChoice.MAKE: 
				makeAccount();
				break;
			case MenuChoice.DEPOSIT:
				depositMoney();
				break;
			case MenuChoice.WITHDRAW:
				withdrawMoney();
				break;
			case MenuChoice.INQUIRE:
				showAccInfo();
				break;
			case MenuChoice.EXIT:
				System.out.println("프로그램을 종료합니다.");
				return;
			}
		}
	}
	
	//계좌개설
	public void makeAccount() {
		Scanner scan = new Scanner(System.in);
		
		System.out.println("***신규계좌개설***");
		
		System.out.println("계좌번호 : " );
		accountNum = scan.nextLine();
		
		System.out.println("이름 : " );
		name = scan.nextLine();
		
		System.out.println("잔고 : " );
		balance = scan.nextInt();
		
		Account acc = new Account
				(accountNum, name, balance);
		accounts[accCnt++] = acc;
		
	}
	
	//입금
	public void depositMoney() {
		int inputMoney;
		Scanner scan = new Scanner(System.in);
		System.out.println("***입   금***");
		System.out.println("계좌번호와 입금할 금액을 입력하세요.");
		System.out.println("계좌번호 : ");
		String s_AccNum = scan.nextLine();
		System.out.println("입금액 : ");
		inputMoney = scan.nextInt();
		
		for (int i = 0; i < accCnt; i++) {
			
			if(s_AccNum.compareTo(accounts[i].accountNum) == 0) {
				accounts[i].balance += inputMoney;
				
				System.out.println("입금이 완료되었습니다.\n");
			}
		}
	}
	
	//출금
	public void withdrawMoney() {
		
		int outputMoney;
		Scanner scan = new Scanner(System.in);
		System.out.println("***출   금***");
		System.out.println("계좌번호와 출금할 금액을 입력하세요.");
		System.out.println("계좌번호 : ");
		String s_AccNum = scan.nextLine();
		System.out.println("출금액 : ");
		outputMoney = scan.nextInt();
		
		for (int i = 0; i < accCnt; i++) {
			
			if(s_AccNum.compareTo(accounts[i].accountNum) == 0) {
				accounts[i].balance -= outputMoney;
				
				System.out.println("출금이 완료되었습니다.\n");
			}
		}
		
	}

	//전체계좌정보출력
	public void showAccInfo() {
		System.out.println("***계좌정보출력***");
		for (int i = 0; i < accCnt; i++) {
			System.out.println("-----------------");
			System.out.println("계좌번호 : " + accounts[i].accountNum);
			System.out.println("이름 : " + accounts[i].name);
			System.out.println("잔액 : " + accounts[i].balance);
			System.out.println("-----------------");
		}
		System.out.println("전체계좌정보 출력이 완료되었습니다.");		
	}

}
