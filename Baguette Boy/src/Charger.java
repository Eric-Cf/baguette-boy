
public class Charger extends Enemy{

	public Charger(int x, int y, int mass, int width, int height, Manager m) {
		super(x, y, mass, width, height, m, 100);
		// TODO Auto-generated constructor stub
	}
	
	
	public void act(double ratio)
	{
//		//System.out.println(ratio);
//		xSpeed=-10;
//		//x-=1;
//		super.posUpdate(ratio);
		super.act(ratio);
	}
	
	

}
