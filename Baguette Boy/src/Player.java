

import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import processing.core.PApplet;

public class Player extends GravitisedObj implements Damagable{

	public static final int MAX_HP = 100;
	private int hp;
	private int currentInvSpot;
	private Manager manager;
	private boolean rightWalkCode;
	private boolean leftWalkCode;
	private boolean hasJumped;



	public Player(int x, int y, int mass, int width, int height, Manager m) {
		super(x, y, mass, width, height,m);
		hp = MAX_HP;
		currentInvSpot = 0;
		manager = m;
		rightWalkCode=false;
		hasJumped = true;
		leftWalkCode = false;
	}

	public void act(double ratio)
	{
		//ySpeed+=1;
		if(grounded)
		{
			hasJumped = false;
		}
		if(!rightWalkCode&&!leftWalkCode)
		{
			if(xSpeed>0)

			{
				xSpeed-=2;
			}
			else if(xSpeed<0)
			{
				xSpeed+=2;
			}
		}

		if(!rightWalkCode||!leftWalkCode)
		{
			if(rightWalkCode&&(xSpeed<10))
			{
				if(onCurve)
				{
					xSpeed+=2;
				}
				xSpeed+=2;
			}
			else if(leftWalkCode&&(xSpeed>-10))
			{
				xSpeed-=2;
				if(onCurve)
				{
					xSpeed-=2;
				}
			}
		}
		else
		{
			if(xSpeed>0)

			{
				xSpeed-=2;
			}
			else if(xSpeed<0)
			{
				xSpeed+=2;
			}
		}
		posUpdate(ratio);

	}

	/**
	 * Draws the player object
	 */
	public void draw(PApplet g)
	{
		g.pushStyle();
		g.fill(255,0,0);
		g.rect(x, y, width, height);
		g.text(""+currentInvSpot, x, y-20);
		g.popStyle();
	}

	/**
	 * Updates the current key code
	 * @param e
	 */
	public void sendKeyCode(char e)
	{
		if(e=='w')
		{
			if(grounded||!hasJumped||onCurve)
			{
				hasJumped = true;
				grounded=false;
				ySpeed=-30;
				if(onCurve)
				{
					y-=10;
				}
			}
		}
		else if(e=='a')
		{
			this.leftWalkCode = true;
		}
		else if(e=='d')
		{
			this.rightWalkCode = true;
		}
	}

	/**
	 * Updates the current release key code
	 * @param e
	 */
	public void releaseKeyCode(char e)
	{
		//System.out.println("hi");
		if(e=='d')
		{
			this.rightWalkCode = false;
		}
		else if(e=='a')
		{
			this.leftWalkCode = false;
		}



	}


	/**
	 * Updates the player with mouse wheel movements
	 * @param move
	 */
	public void getWheelMove(int move)
	{
		currentInvSpot+=move;
	}
	
	/**
	 * Returns an object representation of the player object
	 */
	@Override
	public Rectangle getRect() {
		return new Rectangle(getX(),getY(), getWidth(),getHeight());
	}

	/**
	 * Takes damage specified by the parameter
	 */
	@Override
	public void takeDamage(int damage) {
		hp-= damage;

	}


}
