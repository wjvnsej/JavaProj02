package ver04;

import java.io.Serializable;

/*
4단계[컬렉션&IO] : BankingSystemVer04.java
인스턴스배열 기반의 프로그램을 HashSet<E> 컬렉션으로 변경해보자.
“계좌번호가 동일한 경우 중복된 계좌로 처리한다.”
즉 새로운 정보로 갱신할 지 여부를 물어본후 선택에 따라 처리한다.

컬렉션 기반으로 변경후 인스턴스를 저장하기 위해 입출력을 적용하자.
ObjectInputStream, ObjectOutputStream 클래스를 기반으로 제작한다.
파일의 저장은 프로그램을 종료하는 시점에 이루어져야 하고, 
프로그램 시작 직후 전체정보를 조회하면 기존에 입력된 정보들이 출력되어야 한다.
*/
public abstract class Account implements Serializable {

	String accountNum, name;
	int balance;
		
	public Account
		(String accountNum, String name, int balance) {
		this.accountNum = accountNum;
		this.name = name;
		this.balance = balance;
	}
	
	public void rateCalcul(int inputMoney) {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountNum == null) ? 0 : accountNum.hashCode());
		result = prime * result + balance;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		
		Account account = (Account)obj;
		if(account.accountNum.equals(this.accountNum)) {
			return true;
		}
		else {
			return false;
		}
		
	}

	@Override
	public String toString() {
		return "***계좌정보***\n계좌번호 : " + accountNum 
				+ "\n이름 : " + name 
				+ "\n잔고 : " + balance;
	}
}
