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
		
		System.out.print("금액과 선택한 커피를 입력하세요: ");
		cash = sc.nextInt();
		selection = sc.next();
		
		//User가 돈과 선택을 넣음.
		UserPanel U = new UserPanel();
		U.accept(cash, selection, U);	
		
	}
	
}



class UserPanel {
	
	int cash;
	String selection;
	int change;  //잔돈
	String displayPharase = "";
	
	
	public void accept(int cash, String selection, UserPanel u) {
		//UserPanel u로서 객체를 '새로 만드는게 아니라' 전달받아서 계산함.
		
		this.cash = cash;
		this.selection = selection;
		
		System.out.println("\n\n\n\n왼쪽은 사용자가 보는 자판기입니다."
				+ "\t\t\t\t오른쪽은 사용자에게 보이지 않는 중간 동작 과정입니다. 원한다면 관리자는 볼 수 있습니다."
				+ "\n----------------------------------------------------------------"
				+ "------------------------------------------------------------------");
		System.out.println("사용자가 돈을 넣고 선택했다.");
		System.out.println("\t\t\t\t\t\t\tUserPanel은 돈을 받았다.");
		
		System.out.println("\t\t\t\t\t\t\tUserPanel이 돈을 Controller에게 전달한다.");
		Controller c = new Controller();
		c.getUserInput(this.cash, this.selection, u);
		
	}

	public void setDisplayPharase(String displayPharase) {
		this.displayPharase = displayPharase;
	}
	
	public int getCash() { return this.cash;}
	public String getSelection() { return this.selection;}
	public String getDisplayPharase() { return (this.displayPharase); }

	public void receiveCoffee(String selection) {
		System.out.print(selection); System.out.println(" 커피가 나왔다.");
	}
	
	public void receiveChange(int change) {
		if (change!=0) {
			System.out.println("\t\t\t\t\t\t\tMoneyManager UserPanel에 잔돈을 전달한다.");
			System.out.print(change);
			System.out.println("원의 잔돈이 UserPanel에 반환되었다.");
		}
	}
}



class Controller {

	int cash;
	String selection;	

	public void getUserInput(int cash, String seletion, UserPanel u) {
		this.cash = cash;
		this.selection = seletion;
		
		System.out.println("\n\t\t\t\t\t\t\tController은 UserPanel로부터 돈을 받았다.");
		
		//생성자-> 4000전달
		MoneyManager m = new MoneyManager(4000);			
		
		
		if (m.checkAvailibilityOfChangesAndAbovePrice(this.cash, this.selection)) {
			m.updateBalance(cash, selection);
			m.getBalance();		System.out.println();//자판기 잔돈 체크함. 
			
			//생성자 -> 10전달
			CupManager cm = new CupManager(10);
			//생성자 5, 10, 0으로 함-> NoChange를 위해 임의로 0으로 설정한 BlackCoffee
			IngredientManager im = new IngredientManager(5, 10, 0);
			//생성자 -> 1000(ml) 전달
			WaterManager wm = new WaterManager(1000);
			
			if (cm.checkAvailibility() && im.checkAvailibility(selection, u, cash) && wm.checkAvailibility(selection)) {
				//모든 조건(Availibility)이 충족되었을 때, operation Indicatotor 불켜지게 함
				OperationIndicator oi = new OperationIndicator();
				oi.turnLED_On();
				
				Manufacture mf = new Manufacture();
				mf.getRequestManufacture(this.selection, oi, u, m ,cm, im, wm);
				
			}
		}
		
		else {
			System.out.println("---NoChage---");
			u.receiveChange(cash);
		}
		
	}
	
	public int getCash() {
		System.out.print(this.cash);
		return this.cash;
	}
	
	public String getSelection() {
		System.out.println(this.selection);
		return this.selection;
	}
	
}



class OperationIndicator {
	boolean LEDStatus = false;
	
	public void turnLED_On() {
		System.out.println("LED 불이 켜졌다."); System.out.println();
		LEDStatus = true;
	}
	public void turnLED_Off() {
		System.out.println("LED 불이 꺼졌다."); System.out.println();
		LEDStatus = false;
	}
	public boolean getLEDStatus() {
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
	
	//잔액만 확인하는게 아니라 투입금액>=물품가액도 확인해야 & 마지막 else부분은 없는 문자열로 잘못 입력한 것임
	public boolean checkAvailibilityOfChangesAndAbovePrice(int cash, String selection) {
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
		
		System.out.println("\t\t\t\t\t\t\tController은 MoneyManager에게 사용자의 투입금액이 옳은지, 거슬러줄 잔돈이 있는지 확인한다. ");
		if ( availibility==true ) System.out.println("\t\t\t\t\t\t\tMoneyManager은 true라고 알려줬다.");
		else System.out.println("\t\t\t\t\t\t\tMoneyManager은 false라고 알려줬다.");
		
		return availibility;
	}
	
	public void updateBalance(int cash, String selection) {
		if (selection.equals("SpecialCoffee")) { balance += 2000; }
		else if (selection.equals("PlainCoffee")) { balance += 1000; }
		else if (selection.equals("BlackCoffee")) {  balance += 1500; }
		
	}
	
	public int getBalance() {
		System.out.println("\t\t\t\t\t\t\tMoneyManager는 계산하여 balance를 업데이트 했다.");
		System.out.print("\t\t\t\t\t\t\t[관리자는 확인 가능] 자판기의 업데이트된 잔고 확인: ");
		System.out.println(this.balance);
		return this.balance;
	}
	
	public void getFinsihCoffee(String selection, UserPanel u) {
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
	
	public boolean checkAvailibility() {
		if (cupCount>=1) {System.out.println("\t\t\t\t\t\t\t[관리자는 확인 가능] cup 이용가능"); return true;}
		else {return false;}
	}
	
	public int getOrderCup() {
		System.out.println("\t\t\t\t\t\t\tCupManager는 Manufacture에게 컵을 준다.");
		cupCount--;
		return 1;
	}
	public int getCupCount() {
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

	public boolean checkAvailibility(String selection, UserPanel u, int cash) {	
		if ( ((selection.equals("SpecialCoffee"))&&(SpecialCoffeeCount>=1))
				| ((selection.equals("PlainCoffee"))&&(PlainCoffeeCount>=1))
				| ((selection.equals("BlackCoffee"))&&(BlackCoffeeCount>=1)) ) {
			System.out.println("\t\t\t\t\t\t\t[관리자는 확인 가능] " + selection + " 이용가능");
			return true;
		}
		
		else {
			System.out.println("---SoldOut---");
			u.receiveChange(cash);
			return false;
		}
	}
	
	public int getOrderIngredient(String selection) {
		System.out.println("\t\t\t\t\t\t\tIngredientManager는 Manufacture에게 Ingredient을 준다.");
		
		if (selection.equals("SpecialCoffee")) { SpecialCoffeeCount--; }
		else if (selection.equals("PlainCoffee")) { PlainCoffeeCount--; }
		else if (selection.equals("BlackCoffee")) { BlackCoffeeCount--; }
		
		return 1;
	}
	
	public int getSpecialCoffeeCount() {return SpecialCoffeeCount;}
	public int getPlainCoffeeCount() {return PlainCoffeeCount;}
	public int getBlackCoffeeCount() {return BlackCoffeeCount;}
}



class WaterManager {
	int amountOfWater;	//ml단위
	
	//생성자
	WaterManager(int amountOfWater) {
		this.amountOfWater = amountOfWater;
	}
	
	public boolean checkAvailibility(String selection) {
		if (amountOfWater>=150) {
			System.out.println("\t\t\t\t\t\t\t[관리자는 확인 가능] 물 이용가능\n");
			return true;
		}
		else {
			return false;
		}
	}
	
	public int getOrderWater() {
		amountOfWater-=150;
		System.out.println("\t\t\t\t\t\t\tWaterManager는 Manufacture에게 물을 준다.");
		return 150;
	}
	public int getAmountOfWater() {
		return amountOfWater;
	}
	
}



class Manufacture {
	String selection;
	public void getRequestManufacture(String selection, OperationIndicator oi, UserPanel u, MoneyManager m, CupManager cm, IngredientManager im, WaterManager wm) {
		this.selection=selection;
		System.out.println("\t\t\t\t\t\t\tManufacture은 " + selection + "를 제조하라는 요청을 받았다.");
		
		System.out.println("\t\t\t\t\t\t\t1개의 컵과, 1개의 "+ selection + "과 150ml의 물을 이용해여 제조한다.");
		cm.getOrderCup();
		im.getOrderIngredient(this.selection);
		wm.getOrderWater();
		
		System.out.println("\n\t\t\t\t\t\t\t[관리자는 확인 가능] " + cm.getCupCount() + "개의 컵이 남아있다.");
		System.out.print("\t\t\t\t\t\t\t[관리자는 확인 가능] " + im.getSpecialCoffeeCount() + "개 SpecialCoffee가 남아있다. / ");
		System.out.print(im.getPlainCoffeeCount() + "개 PlainCoffee가 남아있다. / ");
		System.out.println(im.getBlackCoffeeCount() + "개 BlackCoffee가 남아있다.");
		System.out.println("\t\t\t\t\t\t\t[관리자는 확인 가능] " + wm.getAmountOfWater() + "ml의 물이 WaterManger에 남아있다.\n");
		
		
		CoffeeDispenser cd = new CoffeeDispenser();
		cd.getMixed(selection, oi, u, m);
		
		
	}
}



class CoffeeDispenser {
	boolean cup = false;
	boolean ingredient = false;
	boolean water = false;
	
	public void getMixed(String selection, OperationIndicator oi, UserPanel u, MoneyManager m ) { 
		this.cup = true;
		this.ingredient =true;
		this.water = true;
	
		System.out.println("\t\t\t\t\t\t\tCoffeeDispenser은 컵과 재료와 물을 받았고, 이를 섞는다.\n");
		
		oi.turnLED_Off();		
		System.out.println("\t\t\t\t\t\t\tCoffeeDispenser은 UserPanel에 커피를 전달한다.");
		u.receiveCoffee(selection);
		
		m.getFinsihCoffee(selection, u);
		
	}
}