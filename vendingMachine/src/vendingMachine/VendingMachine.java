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

	int cash;
	String selection;	

	void getUserInput(int cash, String seletion) {
		this.cash = cash;
		this.selection = seletion;
		
		MoneyManager m = new MoneyManager();
		//System.out.println("잔액있는지, 그리고 투입금액 이상인지 확인"); 		System.out.println(m.checkAvailibilityOfChangesAndAboveprice(this.cash, this.selection));
		if (m.checkAvailibilityOfChangesAndAboveprice(this.cash, this.selection)) {
			m.updateBalance(cash,selection);
			m.getBalance();
			
			CupManager cm = new CupManager();
			IngredientManager im = new IngredientManager();
			WaterManager wm = new WaterManager();
			//System.out.println("manager들(cm, im, wm)의 체크"); 			System.out.println(cm.checkAvailibility());			System.out.println(im.checkAvailibility(selection));			System.out.println(wm.checkAvailibility(selection));
			if (cm.checkAvailibility() && im.checkAvailibility(selection) && wm.checkAvailibility(selection)) {
				Manufacture mf = new Manufacture();
				//System.out.println("Manufacture 체크");mf.getRequestManufacture();
			}
			
		}
		else {System.out.println("sys balance 업데이트 안됨. 즉 사용자가 입력한 금액 문제있는겨 -> 오류 메시지 띄워야"); m.getBalance();}
	
		
	}
	
	//void getRecipe(String selection);

	void getCash() { System.out.println(this.cash); }
	void getSelection() { System.out.println(this.selection); }
	
}

class MoneyManager {
	int balance=4000;	//잔액
	boolean availibility;
	
	boolean checkAvailibilityOfChangesAndAboveprice(int cash, String selection) {		//잔액만 확인하는게 아니라 투입금액>=물품가액도 확인해야 & 마지막 else부분은 없는 문자열로 잘못 입력한겅미
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
		else availibility=false;	//선택 잘못한 경우
		
		return availibility;
	}
	
	void updateBalance(int cash, String selection) {
		if (selection.equals("SpecialCoffee")) { balance += 2000; }
		else if (selection.equals("PlainCoffee")) { balance += 1000; }
		else if (selection.equals("BlackCoffee")) {  balance += 1500; }
	}
	void getBalance() { System.out.println("자판기 잔고확인"); System.out.println(this.balance); }
		
}

class CupManager {
	int cupCount = 10;
	boolean checkAvailibility() {
		if (cupCount>=1) return true;
		else {return false;}
	}
}

class IngredientManager {
	int SpecialCoffee = 1;
	int PlainCoffee = 5;
	int BlackCoffee  = 10;
	
	boolean checkAvailibility(String selection) {
		if (SpecialCoffee>=1) return true;
		else if (PlainCoffee>=1) return true;
		else if (BlackCoffee>=1) return true;
		else {return false;}
	}
}

class WaterManager {
	int amountOfWater=1000;	//ml단위
	
	boolean checkAvailibility(String selection) {
		if (amountOfWater>=150) return true;		//레시피 따라서 해도 될듯
		else {return false;}
	}
}

class Manufacture {
	void getRequestManufacture() {System.out.println("제조했다");}
}