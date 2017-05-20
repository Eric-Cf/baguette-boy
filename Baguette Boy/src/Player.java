

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import processing.core.PApplet;

public class Player extends GravitisedObj implements Damagable{

	public static final int MAX_HP = 100;
	private final int ATT_DELAY = 20;
	private final int ATT_DMG = 18;
	private int hp;
	private Manager manager;
	private boolean rightWalkCode;
	private boolean leftWalkCode;
	private boolean hasJumped;
	private boolean spacePressed;
	private boolean rightFacing;
	private int attTmr;

	private Inventory inventory;



	public Player(int x, int y, int mass, int width, int height, Manager m) {
		super(x, y, mass, width, height,m);
		hp = MAX_HP;
		manager = m;
		rightWalkCode=false;
		hasJumped = true;
		leftWalkCode = false;
		spacePressed = false;
		rightFacing = true;
		attTmr = 0;

		inventory = new Inventory();
	}

	public void act(double ratio)
	{
		System.out.println("Grounded: " + grounded);
		System.out.println("hasJumped: " + hasJumped);
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

		if(spacePressed) {
			int tempAttack = inventory.weaponDamage();
			if (tempAttack != 0) {
				Hitbox test;
				float tempProjxV;
				if (xSpeed >=0) {
					tempProjxV = (float)Math.sqrt(xSpeed);
				} else {
					tempProjxV = -1f * (float)Math.sqrt(-xSpeed);
				}
				if (rightFacing)
					test = new Hitbox(true, ATT_DMG, super.getX() + width, super.getY(), 80, super.height, 250, tempProjxV, 0);
				else
					test = new Hitbox(true, ATT_DMG, super.getX() -80, super.getY(), 80, super.height, 250, tempProjxV, 0);


				test.addDestroyListener(super.getManager().getCombat());
				super.getManager().getCombat().addHitbox(test);
			} else if (inventory.rangedWeapon(this)) {
				((RangedWeapon)inventory.getItem()).rangeAttack(this);
			}
			else {
				int tempHeal = inventory.useItem();
				if (tempHeal != 0) {
					hp += tempHeal;
					if (hp > MAX_HP)
						hp = MAX_HP;
				}
			}
			
			System.out.println(hp);
		}
		//attTmr--;


		posUpdate(ratio);

	}

	public void draw(PApplet g)
	{
		g.pushStyle();
		g.fill(255,0,0);
		g.rect(x, y, width, height);
		//g.text(""+currentInvSpot, x, y-20);
		g.popStyle();
	}

	public void sendKeyCode(char e)
	{
		if(e=='w')
		{
			if(grounded||!hasJumped||onCurve)
			{
				hasJumped = true;
				grounded=false;
				ySpeed=-30;
				System.out.println("jump");
				if(onCurve)
				{
					y-=10;
				}
			}
		}
		else if(e=='a')
		{
			this.leftWalkCode = true;
			rightFacing = false;
		}
		else if(e=='d')
		{
			this.rightWalkCode = true;
			rightFacing = true;
		}
		else if (e == ' ') {
			this.spacePressed = true;
		}
	}

	public void releaseKeyCode(char e)
	{
		if(e=='d')
		{
			this.rightWalkCode = false;
		}
		else if(e=='a')
		{
			this.leftWalkCode = false;
		}
		else if(e == ' ') {
			this.spacePressed = false;
		}



	}

	public void sendSpecialKeyCode(int code) {
		if (code == PApplet.SHIFT) {
			System.out.println("shift");
			inventory.moveInventory();
		}
	}



	public void getWheelMove(int move)
	{
		//currentInvSpot+=move;
		inventory.moveInventory();
	}

	public int getInvSpot() {
		return inventory.getCurrentPos();
	}

	public ArrayList<Item> getInv() {
		return inventory.getInventory();
	}

	@Override
	public Rectangle getRect() {
		return new Rectangle(getX(),getY(), getWidth(),getHeight());
	}

	@Override
	public void takeDamage(int damage) {
		hp-= damage;
		System.out.println("takes damage");

	}

	public int getHP() {
		return hp;
	}
	
	public boolean getRightFacing() {
		return rightFacing;
	}

	public void addItem (Item a) {
		inventory.addItem(a);
	}


}
