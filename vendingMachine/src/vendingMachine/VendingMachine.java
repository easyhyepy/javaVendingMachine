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
		
		U.receiveCoffee(selection);
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
		//System.out.print("c출력입니다: "); 		c.getCash();	System.out.print(" ");  	c.getSelection();		//TODO 의외로 accpet가 맨뒤에 출력되네???
		
	}

	void setDisplayPharase(String displayPharase) { this.displayPharase = displayPharase; }
	void getCash() { System.out.println(this.cash); }
	void getSelection() { System.out.println(this.selection); }
	String getDisplayPharase() { return (this.displayPharase); }

	
	void receiveCoffee(String selection) {
		System.out.print(selection); System.out.println(" 커피가 나왔다.");
		//MoneyManager m = new MoneyManager();
		//m.setFinishFalse();
	}
	
	void receiveChange(int change) { 
		System.out.print(change); System.out.println("원의 잔돈이 UserPanel에 반환되었다.");
	}
}



class OperationIndicator {		//TODO 클래스 다이어그램 내용인데 시퀀스에도 추가해야함~
	boolean LEDStatus = false;
	
	void turnLED_On() {
		LEDStatus = true;
	}
	void turnLED_Off() {
		LEDStatus = false;
	}
	boolean getLEDStatus() {
		return this.LEDStatus;
	}
}



class Controller {

	int cash;
	String selection;	

	void getUserInput(int cash, String seletion) {
		this.cash = cash;
		this.selection = seletion;
		
		MoneyManager m = new MoneyManager();
		//System.out.println("잔액있는지, 그리고 투입금액 이상인지 확인"); 		System.out.println(m.checkAvailibilityOfChangesAndAboveprice(this.cash, this.selection));
		
		if (m.checkAvailibilityOfChangesAndAbovePrice(this.cash, this.selection)) {
			m.updateBalance(cash,selection);
			m.getBalance();		System.out.println();//자판기 잔돈 체크함. 
			
			CupManager cm = new CupManager();
			IngredientManager im = new IngredientManager();
			WaterManager wm = new WaterManager();
			//System.out.println("manager들(cm, im, wm)의 체크"); 			System.out.println(cm.checkAvailibility());			System.out.println(im.checkAvailibility(selection));			System.out.println(wm.checkAvailibility(selection));
			if (cm.checkAvailibility() && im.checkAvailibility(selection) && wm.checkAvailibility(selection)) {
				Manufacture mf = new Manufacture();
				System.out.print("Manufacture 체크: ");
				mf.getRequestManufacture(this.selection);
			}	
		}
		
		else {
			System.out.println("sys balance 업데이트 안됨. 즉 사용자가 입력한 금액 문제있는겨 -> 오류 메시지 띄워야");
			m.getBalance();
		}					//TODO 오류메시지
		
	}
	
	//void getRecipe(String selection);
	int getCash() {
		System.out.print(this.cash);
		return this.cash;
	}			//첫코딩때 void였는데 바꿨음. 근데 6/1오전에 출력해보니 c출력입니다가 맨 뒤에 출력되더라?
	
	String getSelection() {
		System.out.println(this.selection);
		return this.selection;
	}
	
}



class MoneyManager {
	int balance=4000;	//잔액
	boolean availibility;
	
	//boolean notFinish = true;
	//void setFinishFalse() {this.notFinish = false;}
	
	boolean checkAvailibilityOfChangesAndAbovePrice(int cash, String selection) {		//잔액만 확인하는게 아니라 투입금액>=물품가액도 확인해야 & 마지막 else부분은 없는 문자열로 잘못 입력한겅미
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
		
		//아마 여기서 change 호출해야할듯.
		
		
		//while (notFinish){  //MoneyManager의 notFinish와 setFinishiFalse()로 잔돈 반환을 늦추려했으나 실패. 그래서 걍 주석해두겠음. UserPanel의 setFinishFalse()도 마찬가지로 주석해둠
		UserPanel u = new UserPanel();

		if (selection.equals("SpecialCoffee")) { u.receiveChange(cash-2000); }
		else if (selection.equals("PlainCoffee")) { u.receiveChange(cash-1000); }
		else if (selection.equals("BlackCoffee")) { u.receiveChange(cash-1500); }
	}
	
	int getBalance() { System.out.print("자판기 잔고확인: "); System.out.println(this.balance); return this.balance;}		//void에서 int로 바꿈: 컨트롤러의 m.getBalance();에서 작동됨
		
}



class CupManager {
	int cupCount = 10;
	
	boolean checkAvailibility() {
		if (cupCount>=1) return true;
		else {return false;}
	}
	int getOrderCup() {
		System.out.println("CupManager는 Manufacture에게 컵을 준다.");
		cupCount--;
		return 1;
	}
	int getCupCount() {
		return this.cupCount;
	}
}



class IngredientManager {
	int SpecialCoffeeCount = 1;		//하나로 퉁쳐서 count하는 것은 말이 안돼서 추가했음.
	int PlainCoffeeCount = 5;
	int BlackCoffeeCount  = 10;

	boolean checkAvailibility(String selection) {
		if (SpecialCoffeeCount>=1) return true;
		else if (PlainCoffeeCount>=1) return true;
		else if (BlackCoffeeCount>=1) return true;
		else {return false;}
	}
	
	int getOrderIngredient(String selection) {
		System.out.println("IngredientManager는 Manufacture에게 Ingredient을 준다.");
		if (selection.equals("SpecialCoffee")) { SpecialCoffeeCount--; }
		else if (selection.equals("PlainCoffee")) { PlainCoffeeCount--; }
		else if (selection.equals("BlackCoffee")) { BlackCoffeeCount--; }
		
		return 1;
	}
	
	int getSpecialCoffeeCount() {return SpecialCoffeeCount;}
	int getPlainCoffeeCount() {return PlainCoffeeCount;}
	int getBlackCoffeeCount() {return BlackCoffeeCount;}
}



class WaterManager {
	int amountOfWater=1000;	//ml단위
	
	boolean checkAvailibility(String selection) {
		if (amountOfWater>=150) return true;		//레시피 따라서 해도 될듯
		else {return false;}
	}
	int getOrderWater() {
		amountOfWater-=150;
		return 150;
	}
	int getAmountOfWater() {
		return amountOfWater;
	}
}



class Manufacture {
	String selection;
	void getRequestManufacture(String selection) {
		this.selection=selection;
		System.out.print("제조요청을 받았다. selection 출력: "); System.out.println(selection);
		
		CupManager cm = new CupManager();   //또 객체생성하면 안될 것 같음.
		System.out.print(cm.getOrderCup()); 			System.out.println("개의 컵을 받았다.");
		System.out.print(cm.getCupCount());				System.out.println("개의 컵이 남아있다.\n");
		
		IngredientManager im = new IngredientManager();
		System.out.print(im.getOrderIngredient(this.selection));	System.out.println("개의 재료를 받았다.");
		System.out.print(im.getSpecialCoffeeCount());	System.out.print("개 SpecialCoffee가 남아있다. / ");
		System.out.print(im.getPlainCoffeeCount());		System.out.print("개 PlainCoffee가 남아있다. / ");
		System.out.print(im.getBlackCoffeeCount());		System.out.println("개 BlackCoffee가 남아있다.\n");
		
		WaterManager wm = new WaterManager();
		System.out.print(wm.getOrderWater());			System.out.println("ml의 물을 받았다.");
		System.out.print(wm.getAmountOfWater());		System.out.println("ml의 물이 WaterManger에 남아있다.\n");
		
		CoffeeDispenser cd = new CoffeeDispenser();
		cd.getMixed(selection, cm.getOrderCup(), im.getOrderIngredient(this.selection), wm.getOrderWater());		//selection만 있었는데 추가함
		
	}
}



class CoffeeDispenser {
	boolean cup = false;
	boolean ingredient = false;
	boolean water = false;
	
	void getMixed(String selection, int cup, int ingredient, int water) { 
		this.cup = true;
		this.ingredient =true;
		this.water = true;
		
		System.out.println("\nCoffeeDispenser은 컵과 재료와 물을 받았고, 이를 섞는다.\n");
		//UserPanel u = new UserPanel(); 	-> 맞는지 모르겠는데 main으로 코드 옮겼더니 출력순서 똑같아.
		//u.receiveCoffee(selection);
		
	}
}