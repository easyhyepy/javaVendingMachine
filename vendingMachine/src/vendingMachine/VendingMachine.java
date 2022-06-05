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
		
		System.out.print("�ݾװ� ������ Ŀ�Ǹ� �Է��ϼ���: ");
		cash = sc.nextInt();
		selection = sc.next();
		
		//User�� ���� ������ ����.
		UserPanel U = new UserPanel();
		U.accept(cash, selection, U);	
		
	}
	
}



class UserPanel {
	
	int cash;
	String selection;
	int change;  //�ܵ�
	String displayPharase = "";
	
	
	public void accept(int cash, String selection, UserPanel u) {
		//UserPanel u�μ� ��ü�� '���� ����°� �ƴ϶�' ���޹޾Ƽ� �����.
		
		this.cash = cash;
		this.selection = selection;
		
		System.out.println("\n\n\n\n������ ����ڰ� ���� ���Ǳ��Դϴ�."
				+ "\t\t\t\t�������� ����ڿ��� ������ �ʴ� �߰� ���� �����Դϴ�. ���Ѵٸ� �����ڴ� �� �� �ֽ��ϴ�."
				+ "\n----------------------------------------------------------------"
				+ "------------------------------------------------------------------");
		System.out.println("����ڰ� ���� �ְ� �����ߴ�.");
		System.out.println("\t\t\t\t\t\t\tUserPanel�� ���� �޾Ҵ�.");
		
		System.out.println("\t\t\t\t\t\t\tUserPanel�� ���� Controller���� �����Ѵ�.");
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
		System.out.print(selection); System.out.println(" Ŀ�ǰ� ���Դ�.");
	}
	
	public void receiveChange(int change) {
		if (change!=0) {
			System.out.println("\t\t\t\t\t\t\tMoneyManager UserPanel�� �ܵ��� �����Ѵ�.");
			System.out.print(change);
			System.out.println("���� �ܵ��� UserPanel�� ��ȯ�Ǿ���.");
		}
	}
}



class Controller {

	int cash;
	String selection;	

	public void getUserInput(int cash, String seletion, UserPanel u) {
		this.cash = cash;
		this.selection = seletion;
		
		System.out.println("\n\t\t\t\t\t\t\tController�� UserPanel�κ��� ���� �޾Ҵ�.");
		
		//������-> 4000����
		MoneyManager m = new MoneyManager(4000);			
		
		
		if (m.checkAvailibilityOfChangesAndAbovePrice(this.cash, this.selection)) {
			m.updateBalance(cash, selection);
			m.getBalance();		System.out.println();//���Ǳ� �ܵ� üũ��. 
			
			//������ -> 10����
			CupManager cm = new CupManager(10);
			//������ 5, 10, 0���� ��-> NoChange�� ���� ���Ƿ� 0���� ������ BlackCoffee
			IngredientManager im = new IngredientManager(5, 10, 0);
			//������ -> 1000(ml) ����
			WaterManager wm = new WaterManager(1000);
			
			if (cm.checkAvailibility() && im.checkAvailibility(selection, u, cash) && wm.checkAvailibility(selection)) {
				//��� ����(Availibility)�� �����Ǿ��� ��, operation Indicatotor �������� ��
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
		System.out.println("LED ���� ������."); System.out.println();
		LEDStatus = true;
	}
	public void turnLED_Off() {
		System.out.println("LED ���� ������."); System.out.println();
		LEDStatus = false;
	}
	public boolean getLEDStatus() {
		return this.LEDStatus;
	}
}



class MoneyManager {
	int balance;	//�ܾ�
	boolean availibility;
	
	//������
	MoneyManager(int balance) {
		this.balance=balance;
	}
	
	//�ܾ׸� Ȯ���ϴ°� �ƴ϶� ���Աݾ�>=��ǰ���׵� Ȯ���ؾ� & ������ else�κ��� ���� ���ڿ��� �߸� �Է��� ����
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
		else availibility=false;	//���� �߸��� ���
		
		System.out.println("\t\t\t\t\t\t\tController�� MoneyManager���� ������� ���Աݾ��� ������, �Ž����� �ܵ��� �ִ��� Ȯ���Ѵ�. ");
		if ( availibility==true ) System.out.println("\t\t\t\t\t\t\tMoneyManager�� true��� �˷����.");
		else System.out.println("\t\t\t\t\t\t\tMoneyManager�� false��� �˷����.");
		
		return availibility;
	}
	
	public void updateBalance(int cash, String selection) {
		if (selection.equals("SpecialCoffee")) { balance += 2000; }
		else if (selection.equals("PlainCoffee")) { balance += 1000; }
		else if (selection.equals("BlackCoffee")) {  balance += 1500; }
		
	}
	
	public int getBalance() {
		System.out.println("\t\t\t\t\t\t\tMoneyManager�� ����Ͽ� balance�� ������Ʈ �ߴ�.");
		System.out.print("\t\t\t\t\t\t\t[�����ڴ� Ȯ�� ����] ���Ǳ��� ������Ʈ�� �ܰ� Ȯ��: ");
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
	
	//������
	CupManager(int cupCount){
		this.cupCount=cupCount;
	}
	
	public boolean checkAvailibility() {
		if (cupCount>=1) {System.out.println("\t\t\t\t\t\t\t[�����ڴ� Ȯ�� ����] cup �̿밡��"); return true;}
		else {return false;}
	}
	
	public int getOrderCup() {
		System.out.println("\t\t\t\t\t\t\tCupManager�� Manufacture���� ���� �ش�.");
		cupCount--;
		return 1;
	}
	public int getCupCount() {
		return this.cupCount;
	}
}



class IngredientManager {
	int SpecialCoffeeCount;		//�ϳ��� ���ļ� count�ϴ� ���� ���� �ȵż� �߰�����.
	int PlainCoffeeCount;
	int BlackCoffeeCount;
	
	//������
	IngredientManager (int SpecialCoffeeCount, int PlainCoffeeCount, int BlackCoffeeCount) {
		this.SpecialCoffeeCount = SpecialCoffeeCount;
		this.PlainCoffeeCount = PlainCoffeeCount;
		this.BlackCoffeeCount = BlackCoffeeCount;
	}

	public boolean checkAvailibility(String selection, UserPanel u, int cash) {	
		if ( ((selection.equals("SpecialCoffee"))&&(SpecialCoffeeCount>=1))
				| ((selection.equals("PlainCoffee"))&&(PlainCoffeeCount>=1))
				| ((selection.equals("BlackCoffee"))&&(BlackCoffeeCount>=1)) ) {
			System.out.println("\t\t\t\t\t\t\t[�����ڴ� Ȯ�� ����] " + selection + " �̿밡��");
			return true;
		}
		
		else {
			System.out.println("---SoldOut---");
			u.receiveChange(cash);
			return false;
		}
	}
	
	public int getOrderIngredient(String selection) {
		System.out.println("\t\t\t\t\t\t\tIngredientManager�� Manufacture���� Ingredient�� �ش�.");
		
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
	int amountOfWater;	//ml����
	
	//������
	WaterManager(int amountOfWater) {
		this.amountOfWater = amountOfWater;
	}
	
	public boolean checkAvailibility(String selection) {
		if (amountOfWater>=150) {
			System.out.println("\t\t\t\t\t\t\t[�����ڴ� Ȯ�� ����] �� �̿밡��\n");
			return true;
		}
		else {
			return false;
		}
	}
	
	public int getOrderWater() {
		amountOfWater-=150;
		System.out.println("\t\t\t\t\t\t\tWaterManager�� Manufacture���� ���� �ش�.");
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
		System.out.println("\t\t\t\t\t\t\tManufacture�� " + selection + "�� �����϶�� ��û�� �޾Ҵ�.");
		
		System.out.println("\t\t\t\t\t\t\t1���� �Ű�, 1���� "+ selection + "�� 150ml�� ���� �̿��ؿ� �����Ѵ�.");
		cm.getOrderCup();
		im.getOrderIngredient(this.selection);
		wm.getOrderWater();
		
		System.out.println("\n\t\t\t\t\t\t\t[�����ڴ� Ȯ�� ����] " + cm.getCupCount() + "���� ���� �����ִ�.");
		System.out.print("\t\t\t\t\t\t\t[�����ڴ� Ȯ�� ����] " + im.getSpecialCoffeeCount() + "�� SpecialCoffee�� �����ִ�. / ");
		System.out.print(im.getPlainCoffeeCount() + "�� PlainCoffee�� �����ִ�. / ");
		System.out.println(im.getBlackCoffeeCount() + "�� BlackCoffee�� �����ִ�.");
		System.out.println("\t\t\t\t\t\t\t[�����ڴ� Ȯ�� ����] " + wm.getAmountOfWater() + "ml�� ���� WaterManger�� �����ִ�.\n");
		
		
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
	
		System.out.println("\t\t\t\t\t\t\tCoffeeDispenser�� �Ű� ���� ���� �޾Ұ�, �̸� ���´�.\n");
		
		oi.turnLED_Off();		
		System.out.println("\t\t\t\t\t\t\tCoffeeDispenser�� UserPanel�� Ŀ�Ǹ� �����Ѵ�.");
		u.receiveCoffee(selection);
		
		m.getFinsihCoffee(selection, u);
		
	}
}