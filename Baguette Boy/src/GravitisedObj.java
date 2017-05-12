

import processing.core.PApplet;

/*
 * Properties of this object:
 *   -affected by gravity
 *   -collides with all platforms
 */
public abstract class GravitisedObj {
	public static final int GRAVITY_POWER = 2;

	public final int mass;
	protected int x, y;
	protected int width, height;
	protected double xSpeed, ySpeed;
	public boolean grounded;
	private Manager m;
	protected boolean onCurve;
	private int curveTimer;

	/*
	 * x and y from top left corner of box
	 * width is left side to right side
	 * height is top to bottom
	 */
	public GravitisedObj(int x, int y, int mass, int width, int height, Manager m) {
		this.x = x;
		this.y = y;
		this.m = m;
		this.mass = mass;
		this.width = width;
		this.height = height;
		xSpeed = 0;
		ySpeed = 0;
		grounded = false;
		curveTimer = 0;
	}

	public void posUpdate(double ratio) {
		boolean isPlayer = false;
		if(this instanceof Player)
		{
			isPlayer = true;
		}
		else
		{
			//System.out.println("updating pos");
		}
		if(curveTimer<1)
		{
			onCurve=false;
		}
		else
		{
			curveTimer--;
			//System.out.println("On Curve");
		}
		int horizontalPlatform = 500;
		if(xSpeed>0)
		{
			horizontalPlatform = Math.min(m.checkPlatformCollision(x+width, y+90*height/100,2,isPlayer),m.checkPlatformCollision(x+width, y+10*height/100,2,isPlayer));
			if(horizontalPlatform<0&&isPlayer)
			{
				//System.out.println("bad");
				x+=-2;
			}
		}
		else if(xSpeed<0)
		{
			horizontalPlatform = -Math.min(m.checkPlatformCollision(x, y+90*height/100,1,isPlayer),m.checkPlatformCollision(x, y+5*height/100,1,isPlayer));
			if(horizontalPlatform>0&&isPlayer)
			{
				//System.out.println("bad");
				x+=2;
			}
		}
		if(Math.abs(horizontalPlatform)<Math.abs(xSpeed))
		{
			if(horizontalPlatform>5)
				x+=horizontalPlatform;
			xSpeed = 0;
		}
		else
			x += xSpeed*ratio;
		int closestPlatform;
		closestPlatform = Math.min(m.checkPlatformCollision(x+10*width/100, y+height,2,isPlayer),m.checkPlatformCollision(x+90*width/100, y+height,2,isPlayer)); //1 - horizontal, 2-vertical
		//int closestPlatform2 = Math.max(m.checkPlatformCollision(x+10*width/100, y,2),m.checkPlatformCollision(x+90*width/100, y,2)); //1 - horizontal, 2-vertical
		//int closer = Math.min(Math.abs(closestPlatform), Math.abs(closestPlatform2));
		//if(closer==Math.abs(closestPlatform))
		{

			if(closestPlatform<0)
			{
				onCurve = true;
				curveTimer = 3;
				ySpeed = 0;
				grounded = true;

				if(closestPlatform>-10)
				{
					y+=closestPlatform;

				}
			}
			else
			{
				//System.out.println(ySpeed);
				if(closestPlatform<ySpeed*ratio)
				{

					y+=closestPlatform;
					grounded= true;
					ySpeed = 0;
					//grounded = true;
				}
				else
				{
					//System.out.println(ySpeed);
					y += ySpeed*ratio;
				}
			}
		}
		/*
		else
		{
			//closestPlatform = Math.max(m.checkPlatformCollision(x+10*width/100, y,2),m.checkPlatformCollision(x+90*width/100, y,2)); //1 - horizontal, 2-vertical
			if(ySpeed<0&&closestPlatform2<ySpeed)
			{
				y+=closestPlatform2;
				grounded= false;
				ySpeed = 00;
			}
		}*/

		if(m.checkPlatformCollision(x+width/2, y+height,2,isPlayer)>2)
		{
			grounded=false;
		}

		if (!grounded)
		{
			if(!isPlayer)
			{
				System.out.println("object falling");
			}
			ySpeed += GRAVITY_POWER*ratio;
		}
	}
	public abstract void draw(PApplet g);

	public abstract void act(double ratio);

	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int collideTest(int otherX, int otherY, int side) {
		//check collision
		//check other's location relative to this (ex: y above certain amount and collided:  grounded = true)
		int min = 20000;
		if(otherX>x&&otherX<x+width&&otherY>y&&otherY<y+height)
		{
			if(side==2);
			return -1;
		}
		int current = Math.abs(x-otherX);
		if(side==1)
		{
			if(current<min&&otherY>y&&otherY<y+height)
			{
				min=current;
			}
			current = Math.abs(x-otherX+width);
			if(current<min&&otherY>y&&otherY<y+height)
			{
				min = current;
			}
			//System.out.println(min);
		}
		else
		{
			current = Math.abs(y-otherY);
			if(current<min&&otherX>x&&otherX<x+width)
			{
				min = current;
			}
			current = Math.abs(y-otherY+height);
			if(current<min&&otherX>x&&otherX<x+width)
			{
				min = current;
			}
		}

		return min;
	}

	public int getMiddleX()
	{
		return x+width/2;
	}

}
