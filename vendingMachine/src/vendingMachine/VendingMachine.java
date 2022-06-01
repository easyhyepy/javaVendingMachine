package vendingMachine;
import java.util.Scanner;



public class VendingMachine {
	
	static void displayVM() {
		System.out.println(
				"---------------------------------------------\n"
				+ "| SpecialCoffee | PlainCoffee | BlackCoffee |\n"
				+ "|      2000     |    1000     |    1500     |\n"
				+ "---------------------------------------------");
	}
	
	public static void main(String[] args) {
		
		String selection="";
		int cash=0;
		Scanner sc = new Scanner(System.in);
		
		
		displayVM();
		
		System.out.print("금액과 선택 입력하세요: ");
		cash = sc.nextInt();
		selection = sc.next();
		
		//User가 돈과 선택을 넣음.
		UserPanel U = new UserPanel();
		U.accept(cash, selection, U);	
		//System.out.println("U출력입니다"); U.getCash();  U.getSelection(); //확인 완료
		
	}
	
}



class UserPanel {
	
	int cash;
	String selection;
	int change;  //잔돈
	String displayPharase = "";
	
	void accept(int cash, String selection, UserPanel u) {			//UserPanel u로서 객체를 '새로 만드는게 아니라' 전달받아서 계산함.
		this.cash = cash;
		this.selection = selection;
		
		System.out.println("\n\n\n\n왼쪽은 사용자가 보는 자판기입니다.\t\t\t\t오른쪽은 사용자에게 보이지 않는 중간 동작 과정입니다.\n------------------------------------------------------------------------------------------------------------");
		System.out.println("사용자가 돈을 넣고 선택했다.");
		System.out.println("\t\t\t\t\t\t\tUserPanel은 돈을 받았다.");
		Controller c = new Controller();		  //UserPanel u = new UserPanel();
		c.getUserInput(this.cash, this.selection, u);
		//System.out.print("c출력입니다: "); 		c.getCash();	System.out.print(" ");  	c.getSelection();
		
	}

	void setDisplayPharase(String displayPharase) { this.displayPharase = displayPharase; }
	int getCash() { return this.cash;}
	String getSelection() { return this.selection;}
	String getDisplayPharase() { return (this.displayPharase); }

	
	void receiveCoffee(String selection) {
		System.out.print(selection); System.out.println(" 커피가 나왔다.");
		//MoneyManager m = new MoneyManager();
		//m.setFinishFalse();
	}
	
	void receiveChange(int change) {
		if (change!=0) {System.out.print(change); System.out.println("원의 잔돈이 UserPanel에 반환되었다.");}
	}
	
}



class Controller {

	int cash;
	String selection;	

	void getUserInput(int cash, String seletion, UserPanel u) {
		this.cash = cash;
		this.selection = seletion;
		
		System.out.println("\t\t\t\t\t\t\tUserPanel이 돈을 Controller에게 전달했다. Controller은 돈을 받았다.");
		MoneyManager m = new MoneyManager(4000);			//생성자-> 4000전달
		//System.out.println("잔액있는지, 그리고 투입금액 이상인지 확인"); 		System.out.println(m.checkAvailibilityOfChangesAndAboveprice(this.cash, this.selection));
		
		if (m.checkAvailibilityOfChangesAndAbovePrice(this.cash, this.selection)) {
			
			m.updateBalance(cash, selection);
			m.getBalance();		System.out.println();//자판기 잔돈 체크함. 
			
			CupManager cm = new CupManager(10);		//생성자 -> 10전달
			IngredientManager im = new IngredientManager(5, 10, 0);		//생성자 -> 1, 5, 10 전달이였는데 NoChange를 위해 5, 10, 0으로 함
			WaterManager wm = new WaterManager(1000);		//생성자 -> 1000(ml) 전달
			//System.out.println("manager들(cm, im, wm)의 체크"); 			System.out.println(cm.checkAvailibility());			System.out.println(im.checkAvailibility(selection));			System.out.println(wm.checkAvailibility(selection));
			if (cm.checkAvailibility() && im.checkAvailibility(selection, u, cash) && wm.checkAvailibility(selection)) {
				
				//m.updateBalance(cash, selection);
				//m.getBalance();		System.out.println();//자판기 잔돈 체크함. 		//위 주석 코드에서 바꾸..려다가 안바꿔도 되는 것 알게됨
				
				//operation Indicatotor 불켜지게 하는 코드 추가
				OperationIndicator oi = new OperationIndicator();
				oi.turnLED_On();
				
				
				Manufacture mf = new Manufacture();
				System.out.print("\t\t\t\t\t\t\tManufacture 체크: ");
				mf.getRequestManufacture(this.selection, oi, u, m ,cm, im, wm);
				
			}
			
			
		}
		
		else {
			System.out.println("---NoChage---");
			//System.out.println("sys balance 업데이트 안됨. 즉 사용자가 입력한 금액 문제있는겨 -> 오류 메시지 띄워야");
			//m.getBalance();
			u.receiveChange(cash);
		}
		
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



class OperationIndicator {		//TODO 클래스 다이어그램 내용인데 시퀀스에도 추가해야함~
	boolean LEDStatus = false;
	
	void turnLED_On() {
		System.out.println("LED 불이 켜졌다."); System.out.println();
		LEDStatus = true;
	}
	void turnLED_Off() {
		System.out.println("LED 불이 꺼졌다."); System.out.println();
		LEDStatus = false;
	}
	boolean getLEDStatus() {
		return this.LEDStatus;
	}
}



class MoneyManager {
	int balance;	//잔액
	boolean availibility;
	
	//생성자
	MoneyManager(int balance) {
		this.balance=balance;
	}
	
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
		
		System.out.print("\t\t\t\t\t\t\tController은 MoneyManager에게 사용자의 투입금액이 옳은지, 거슬러줄 잔돈이 있는지 확인한다. MoneyManager은 ");
		if ( availibility==true ) System.out.println("true라고 알려줬다.");
		else System.out.println("false라고 알려줬다.");
		
		return availibility;
	}
	
	void updateBalance(int cash, String selection) {
		if (selection.equals("SpecialCoffee")) { balance += 2000; }
		else if (selection.equals("PlainCoffee")) { balance += 1000; }
		else if (selection.equals("BlackCoffee")) {  balance += 1500; }
		
	}
	
	int getBalance() {
		System.out.print("\t\t\t\t\t\t\tMoneyManager는 계산하여 balance를 업데이트 했다. 자판기의 업데이트된 잔고 확인: ");
		System.out.println(this.balance);
		return this.balance;
	}
	
	void getFinsihCoffee(String selection, UserPanel u) {
		//UserPanel U = new UserPanel();	
		int cash = u.getCash();
		if (selection.equals("SpecialCoffee")) { u.receiveChange(cash-2000); }
		else if (selection.equals("PlainCoffee")) { u.receiveChange(cash-1000); }
		else if (selection.equals("BlackCoffee")) { u.receiveChange(cash-1500); }
	}
	
}



class CupManager {
	int cupCount;
	
	//생성자
	CupManager(int cupCount){
		this.cupCount=cupCount;
	}
	
	boolean checkAvailibility() {
		if (cupCount>=1) {System.out.println("\t\t\t\t\t\t\tcup 이용가능"); return true;}
		else {return false;}
	}
	int getOrderCup() {
		System.out.println("\t\t\t\t\t\t\tCupManager는 Manufacture에게 컵을 준다.");
		cupCount--;
		//System.out.print("\t\t\t\t\t\t\t");
		return 1;
	}
	int getCupCount() {
		return this.cupCount;
	}
}



class IngredientManager {
	int SpecialCoffeeCount;		//하나로 퉁쳐서 count하는 것은 말이 안돼서 추가했음.
	int PlainCoffeeCount;
	int BlackCoffeeCount;
	
	//생성자
	IngredientManager (int SpecialCoffeeCount, int PlainCoffeeCount, int BlackCoffeeCount) {
		this.SpecialCoffeeCount = SpecialCoffeeCount;
		this.PlainCoffeeCount = PlainCoffeeCount;
		this.BlackCoffeeCount = BlackCoffeeCount;
	}

	boolean checkAvailibility(String selection, UserPanel u, int cash) {
		if ((selection.equals("SpecialCoffee"))&&(SpecialCoffeeCount>=1)) {System.out.println("\t\t\t\t\t\t\tSpecialCoffee 이용가능"); return true;}
		else if ((selection.equals("PlainCoffee"))&&(PlainCoffeeCount>=1)) {System.out.println("\t\t\t\t\t\t\tPlainCoffee 이용가능"); return true;}
		else if ((selection.equals("BlackCoffee"))&&(BlackCoffeeCount>=1)) {System.out.println("\t\t\t\t\t\t\tBlackCoffee 이용가능"); return true;}
		
		else {
			System.out.println("---SoldOut---");
			u.receiveChange(cash);
			return false;
		}	//오류메시지
	}
	
	int getOrderIngredient(String selection) {
		System.out.println("\t\t\t\t\t\t\tIngredientManager는 Manufacture에게 Ingredient을 준다.");
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
	int amountOfWater;	//ml단위
	
	//생성자
	WaterManager(int amountOfWater) {
		this.amountOfWater = amountOfWater;
	}
	
	boolean checkAvailibility(String selection) {
		if (amountOfWater>=150) { System.out.println("\t\t\t\t\t\t\t물 이용가능\n"); return true;}
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
	void getRequestManufacture(String selection, OperationIndicator oi, UserPanel u, MoneyManager m, CupManager cm, IngredientManager im, WaterManager wm) {
		this.selection=selection;
		System.out.print("제조요청을 받았다 -> selection 출력: "); System.out.println(selection);
		
		//CupManager cm = new CupManager();   //객체 중복생성X
		//System.out.print(cm.getOrderCup()); 			System.out.println("개의 컵을 받았다.");
		System.out.print("\t\t\t\t\t\t\t");
		System.out.print(cm.getCupCount());				System.out.println("개의 컵이 남아있다.\n");
		
		//IngredientManager im = new IngredientManager();
		//System.out.print(im.getOrderIngredient(this.selection));	System.out.println("개의 재료를 받았다.");
		System.out.print("\t\t\t\t\t\t\t");
		System.out.print(im.getSpecialCoffeeCount());	System.out.print("개 SpecialCoffee가 남아있다. / ");
		System.out.print(im.getPlainCoffeeCount());		System.out.print("개 PlainCoffee가 남아있다. / ");
		System.out.print(im.getBlackCoffeeCount());		System.out.println("개 BlackCoffee가 남아있다.\n");
		
		//WaterManager wm = new WaterManager();
		//System.out.print(wm.getOrderWater());			System.out.println("ml의 물을 받았다.");
		System.out.print("\t\t\t\t\t\t\t");
		System.out.print(wm.getAmountOfWater());		System.out.println("ml의 물이 WaterManger에 남아있다.\n");
		
		CoffeeDispenser cd = new CoffeeDispenser();
		cd.getMixed(selection, cm.getOrderCup(), im.getOrderIngredient(this.selection), wm.getOrderWater(), oi, u, m);		//selection만 있었는데 추가함
	}
}



class CoffeeDispenser {
	boolean cup = false;
	boolean ingredient = false;
	boolean water = false;
	
	void getMixed(String selection, int cup, int ingredient, int water, OperationIndicator oi, UserPanel u, MoneyManager m) { 
		this.cup = true;
		this.ingredient =true;
		this.water = true;
		System.out.println("\n\t\t\t\t\t\t\tCoffeeDispenser은 컵과 재료와 물을 받았고, 이를 섞는다.\n");
		
		oi.turnLED_Off();		
		
		//UserPanel U = new UserPanel();
		u.receiveCoffee(selection);
		
		//MoneyManager m = new MoneyManager();
		m.getFinsihCoffee(selection, u);
		
	}
}