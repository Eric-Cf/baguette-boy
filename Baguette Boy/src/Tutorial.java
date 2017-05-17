import processing.core.PApplet;
import processing.core.PFont;

public class Tutorial {

	private Manager m;
	private int displayTimer;
	private int tutorialNumber;

	public Tutorial(Manager man)
	{
		m=man;
		displayTimer = 0;
		tutorialNumber =0;
	}

	public void act()
	{
		displayTimer--;
		if(m.getPlayerX()>10&&m.getPlayerX()<400&&tutorialNumber!=1)
		{
			System.out.println("tut 1");
			tutorialNumber=1;
			displayTimer = 400;
		}
		else if(m.getPlayerX()>2400&&m.getPlayerX()<3200&&tutorialNumber!=2)
		{
			System.out.println("tut 2");
			tutorialNumber=2;
			displayTimer = 700;
		}



	}

	public void draw(PApplet g)
	{
		g.pushStyle();
		PFont pf = new PFont();

		pf=g.createFont("Helvetica Neue", 35);
		g.textFont(pf);
		g.textAlign(g.CENTER);
		//g.textWidth(2);
		if(displayTimer<255)
		{
			g.fill(255,displayTimer);
		}
		if(displayTimer>0)
		{
			if(tutorialNumber==1)
			{
				g.text("Use WASD keys to move", 800, 150);
			}
			else if(tutorialNumber==2)
			{
				g.text("Use your environment to your advantage", 2900, 300);
			}
		}
		g.popStyle();
	}



}
