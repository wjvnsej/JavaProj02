package ver04;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

public class AccountManager {
	HashSet<Object> set;
	public AccountManager() {
		try {
			
			ObjectInputStream in = new ObjectInputStream(
					new FileInputStream("src/ver04/Account.obj"));
			
			set = (HashSet<Object>) in.readObject();
			
		} 
		catch (Exception e) {
			set = new HashSet<Object>();
		}
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
		
		while (true) {

			showMenu();
			Scanner scan = new Scanner(System.in);
			try {
				int choice = scan.nextInt();
				scan.nextLine();

				if (choice < 1 || choice > 5) {
					MenuSelectException notInt = 
							new MenuSelectException();
					throw notInt;
				}

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
					saveAccount();
					System.out.println("프로그램을 종료합니다.");
					return;
				}
			} 
			catch (MenuSelectException e) {
				System.out.println(e.getMessage());
			}
			catch (InputMismatchException  e) {
				System.out.println("문자말고 숫자를 입력해주세요.");
				scan.nextLine();
			}

		}
	}
	
	//중복검사
		public void checkAccNum(String accountNum) {
			
			Scanner scan = new Scanner(System.in);
			boolean checkFlag = false;
			int checkSelect;
		      
			Iterator<Object> itr = set.iterator();
			while(itr.hasNext()) {
				Account account = (Account) itr.next();
				if(accountNum.equals(account.name)) {
					checkFlag = true;
		         }
			}
			if(checkFlag == true) {
				System.out.println("**중복된 계좌번호가 있습니다.**");
				System.out.println("새로운 정보로 갱신하시겠습니까?");
				System.out.println("1.갱신하기, 2. 돌아가기");
				checkSelect = scan.nextInt();
				
				if(checkSelect == 1) {
					itr.remove();
					return;
				}
				else if(checkSelect == 2) {
					start();
				}
			}
			else {
				return;
			}
		}

	//계좌개설
	public void makeAccount() {
		String accountNum, name, grade;
		int select, balance, rate;
		Scanner scan = new Scanner(System.in);
		System.out.println("-----계좌선택------");
		System.out.println("1.보통계좌");
		System.out.println("2.신용신뢰계좌");
		System.out.println("선택 : ");
		select = scan.nextInt();
		scan.nextLine();
		
		System.out.println("계좌번호 : " );
		accountNum = scan.nextLine();
		
		checkAccNum(accountNum);
		
		System.out.println("이름 : " );
		name = scan.nextLine();
		
		System.out.println("잔고 : " );
		balance = scan.nextInt();
		
		System.out.println("기본이자%(정수형태로입력) : " );
		rate = scan.nextInt();
		scan.nextLine();
		
		switch (select) {
		
		case MenuChoice.NORMAL:
			set.add(new NormalAccount(accountNum, name, balance, rate));
			System.out.println("[ 저장 후 계좌 수 ] : " + set.size());
			break;
			
		case MenuChoice.High:
			System.out.println("신용등급(A,B,C등급) : ");
			grade = scan.nextLine();
			
			set.add(new HighCreditAccount
					(accountNum, name, balance, rate, grade));
			System.out.println("[ 저장 후 계좌 수 ] : " + set.size());
			break;
		}
		
	}
	
	//입금
	public void depositMoney() {

		int inputMoney;
		Scanner scan = new Scanner(System.in);
		System.out.println("***입   금***");
		System.out.println("계좌번호와 입금할 금액을 입력하세요.");
		System.out.println("계좌번호 : ");
		String s_AccNum = scan.nextLine();
		try {
			System.out.println("입금액 : ");
			inputMoney = scan.nextInt();

			if (inputMoney < 0) {
				System.out.println("0이하의 금액은 입금할 수 없습니다.");
				return;
			} else if (inputMoney % 500 != 0) {
				System.out.println("500원 단위로만 입금 가능합니다.");
				return;
			}

			boolean inputFlag = false;

			Iterator<Object> itr = set.iterator();
			while (itr.hasNext()) {
				Account account = (Account) itr.next();
				if (s_AccNum.equals(account.accountNum)) {
					inputFlag = true;
					account.rateCalcul(inputMoney);
					System.out.println("입금이 완료되었습니다.\n");

				}
			}
		} catch (InputMismatchException e) {
			System.out.println("문자말고 숫자를 입력해주세요.");
			scan.nextLine();
		}
	}
	
	//출금
	public void withdrawMoney() {

		int outputMoney;
		String selectAllMoney;
		Scanner scan = new Scanner(System.in);
		System.out.println("***출   금***");
		System.out.println("계좌번호와 출금할 금액을 입력하세요.");
		System.out.println("계좌번호 : ");
		String s_AccNum = scan.nextLine();
		try {
			System.out.println("출금액 : ");
			outputMoney = scan.nextInt();
			scan.nextLine();

			if (outputMoney < 0) {
				System.out.println("0보다 작은 금액은 출금 할 수 없습니다.");
				return;
			} else if (outputMoney % 1000 != 0) {
				System.out.println("1000원단위로만 출금이 가능합니다.");
				return;
			}

			boolean inputFlag = false;

			Iterator<Object> itr = set.iterator();
			while (itr.hasNext()) {

				Account account = (Account) itr.next();

				if (s_AccNum.equals(account.accountNum)) {

					inputFlag = true;

					if (outputMoney > account.balance) {
						System.out.println("잔고가 부족합니다. 금액전체를 출금할까요?");
						System.out.println("YES : 금액전체 출금처리\nNO : 출금요청취소");
						selectAllMoney = scan.nextLine();

						switch (selectAllMoney) {
						case "YES":
							account.balance = 0;
							break;
						case "NO":
							return;
						}
					} else {
						account.balance -= outputMoney;

						System.out.println("출금이 완료되었습니다.\n");
					}
				}
			}
		} catch (InputMismatchException e) {
			System.out.println("문자말고 숫자를 입력해주세요.");
			scan.nextLine();
		}
	}

	//전체계좌정보출력
	public void showAccInfo() {
		System.out.println("***계좌정보출력***");
		for (Object account : set) {
			System.out.println("---------------------------");
			System.out.println(account.toString());
			System.out.println("---------------------------");
		}
		System.out.println("전체계좌정보 출력이 완료되었습니다.");	
	}
	
	public void saveAccount() {
		
		try {
			ObjectOutputStream out = 
					new ObjectOutputStream(
							new FileOutputStream
								("src/ver04/Account.obj")
					);
					
				out.writeObject(set);
			
		}
		catch (IOException e) {
			System.out.println("파일이 저장되지 않았습니다.");
			e.printStackTrace();
		}
		
	}
}