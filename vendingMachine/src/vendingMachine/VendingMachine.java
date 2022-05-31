package vendingMachine;
import java.util.Scanner;

public class VendingMachine {

	static void displayVM() {
		System.out.println( "---------------------------------------------\n| SpecialCoffee | PlainCoffee | BlackCoffee |\n|      2000     |    1000     |    1500     |\n---------------------------------------------\n\n\n금액과 선택 입력하세요:");
	}
	
	public static void main(String[] args) {
		String selection="";
		int cash=0;
		
		//입력받기
		displayVM();
		Scanner sc = new Scanner(System.in);
		cash = sc.nextInt();
		selection = sc.next();
		
		//User가 돈과 선택을 넣음.
		UserPanel U = new UserPanel();
		U.accept(cash, selection);
		//System.out.println("U출력입니다"); U.getCash();  U.getSelection(); //확인 완료
		
	}
	
}

class UserPanel {
	int cash;
	String selection;
	int change;  //잔돈
	String displayPharase = "화면출력입니다";	//화면 출력
	
	void accept(int cash, String selection) {
		this.cash = cash;
		this.selection = selection;
		Controller c = new Controller();
		c.getUserInput(this.cash, this.selection);
		
		// System.out.println("c출력입니다"); 		c.getCash();		c.getSelection();
		
	}
	
	void setDisplayPharase(String displayPharase) { this.displayPharase = displayPharase; }
	//void receiveCoffee(String selection)
	//void receiveChange(String change);

	void getCash() { System.out.println(this.cash); }
	void getSelection() { System.out.println(this.selection); }
	void getDisplayPharase() { System.out.println(this.displayPharase); }
	
}

class Controller {

	Controller() {
	}
	
	int cash;
	String selection;
	String recipe;
	

	void getUserInput(int cash, String seletion) {
		this.cash = cash;
		this.selection = seletion;
		
		MoneyManager m = new MoneyManager();
		//System.out.println("잔액있는지, 그리고 투입금액 이상인지 확인"); 		System.out.println(m.checkAvailibilityOfChangesAndAboveprice(this.cash, this.selection));
		if (m.checkAvailibilityOfChangesAndAboveprice(this.cash, this.selection)) {
			
		}
		
	}
	
	//void getRecipe(String selection);

	void getCash() { System.out.println(this.cash); }
	void getSelection() { System.out.println(this.selection); }
	
}

class MoneyManager {
	int balance=4000;	//잔액
	boolean availibility;
	
	boolean checkAvailibilityOfChangesAndAboveprice(int cash, String selection) {		//잔액만 확인하는게 아니라 투입금액>=물품가액도 확인해야
		if (selection.equals("SpecialCoffee")) {
			if(cash>=2000) {
				availibility = (balance>=cash-2000)?true:false;
			} else availibility=false;
		}
		else if (selection.equals("PlainCoffee")) {
			if(cash>=1000) {
				availibility = (balance>=cash-1000)?true:false;
			} else availibility=false;
		}
		else if (selection.equals("BlackCoffee")) {
			if(cash>=1000) {
				availibility = (balance>=cash-1500)?true:false;
			} else availibility=false;
		}
		return availibility;
	}
	
	//void updateBalance(int cash, int price);
	//void getBalance();
		
};