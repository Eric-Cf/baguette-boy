import java.util.ArrayList;

import processing.core.PApplet;

public class PlatformManager implements DestroyListener{

	private ArrayList<BoxPlatform> platforms;
	private Manager manager;

	public PlatformManager(Manager m)
	{
		platforms = new ArrayList<BoxPlatform>();
		manager = m;
		platforms.add(new BoxPlatform(-200, 450, 1170, 300));
		//platforms.add(new BoxPlatform(1040, 350, 100, 100));
		//platforms.add(new BoxPlatform(350, 300, 100, 150));
		//platforms.add(new BoxPlatform(300,0,400,100));
		//platforms.add(new RoundedPlatform(450, 150, 100, 150));

		platforms.add(new BoxPlatform(-20, 0, 40, 600));
		platforms.add(new BoxPlatform(1020,450,400,300));

		int tempY = 710;
		int x = 600;
		int tempX = 600;
		int width = 400;
		int height = 300;
		for(int i=0;i<40;i++)
		{
			double angle = Math.acos(-1*(x+(double)width/2-tempX)/width);
			tempX+=10;
			//System.out.println(angle);
			platforms.add(new BoxPlatform(tempX, (int)(tempY-Math.sin(angle)*height), 20, 32+(int)(Math.sin(angle)*height)));

		}
		
		x=1220;
		tempY=450;
		for(int i=0;i<60;i++)
		{
			platforms.add(new BoxPlatform(x+20*i,tempY-2*i,40,400));
		}
		
		//platforms.add(new BoxPlatform(tempX+800, 410, 200,500));
		platforms.add(new BoxPlatform(tempX+1400, 600, 1000,130));

		


	}



	public int checkCollision(int x, int y, int side,boolean isPlayer)
	{
		boolean collides = false;
		int min = 20000;
		for(BoxPlatform obj: platforms)
		{
			if(Math.abs(x-obj.getMiddleX())<obj.getWidth())
			{
				int current = obj.collideTest(x, y, side);
				if(current<min)
				{
					min=current;
				}
				if(current<0)                                                                                                                                                                                       
				{
					return -1;
				}
			}
		}
		if(side==2&&min<1000&&isPlayer)
		{
			//System.out.println(min+y);
			manager.sendPlatformY(min+y);
		}
		return min;

	}

	public void draw(PApplet g)
	{
		for(BoxPlatform obj: platforms)
		{
			obj.draw(g);
		}
	}

	public void destroy(Object a) {
		for (int i = platforms.size()-1; i >= 0; i--) {
			if (a == platforms.get(i))
				platforms.remove(i);
		}
	}



}
