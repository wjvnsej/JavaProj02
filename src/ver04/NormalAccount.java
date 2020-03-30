package ver04;

/*
2단계[컨트롤클래스 생성과 상속] : BankingSystemVer02.java
*NormalAccount : 보통예금계좌 > 최소한의 이자를 지급하는 자율 입출금식 계좌
보통예금계좌를 의미한다.
생성자를 통해서 이율정보(이자비율의정보)를 초기화 할수있도록 정의한다.

이자계산에 대해서는 다음의 규칙을 적용한다.
이자계산은 입금시에만 잔고를 대상으로 계산한다. 출금할때는 이자계산을 하지 않는다.
이자계산방식 : 잔고:5000원, 기본이자:2%, 신용등급이자:4%, 입금액:2000원이라 가정하면….
일반계좌 : 잔고 + (잔고 * 기본이자) + 입금액 
Ex) 5000 + (5000 * 0.02) + 2000 = 7,100원
신용계좌 : 잔고 + (잔고 * 기본이자) + (잔고 * 추가이자) + 입금액
Ex) 5000 + (5000 * 0.02) + (5000 * 0.04) + 2000 = 7,300원
초기 계좌개설에서는 이자를 계산하지 않는다.
계좌개설 이후 입금을 할때만 이자가 발생한다.
이자의 계산과정에서 발생하는 소수점은 무시해도 된다.(무조건버림처리)
 */

public class NormalAccount extends Account {
	
	int rate;
	
	public NormalAccount
		(String accountNum, String name, int balance, int rate) {
		
		super(accountNum, name, balance);
		this.rate = rate;
	}
	
	@Override
	public void rateCalcul(int inputMoney) {
		balance = balance + (balance * rate / 100) + inputMoney;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + rate;
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
		return super.toString() 
				+ "\n기본이자 : " + rate + "%";
	}
	
	

}















