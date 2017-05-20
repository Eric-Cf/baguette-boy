import java.awt.event.KeyEvent;

import processing.core.PApplet;

//import entities.*;

public class Manager implements DestroyListener{

	private GObjectManager gObjects;
	private PlatformManager platforms;
	private CombatManager combat;
	private Player player;
	private int platformY;
	private Tutorial tutorial;
	
	
	public Manager() 
	{
		gObjects = new GObjectManager(this);
		platforms = new PlatformManager(this);
		player = new Player(100, 50, 10, 100, 100, this);
		combat = new CombatManager(this);
		tutorial = new Tutorial(this);
		
		
	}
	
	/**
	 * Returns all gravitised objects
	 * @return
	 */
	public GObjectManager getgObjects() {
		return gObjects;
	}
	
	public void setUpSprites(PApplet g) {
		Charger.star = g.loadImage("star.png");
		Potion.hpPotion = g.loadImage("Potion Life.png");
		MeleeWeapon.sword = g.loadImage("rusty sword.png");
		RangedWeapon.gun = g.loadImage("gun.png");
	}

	/**
	 * Returns all platforms
	 * @return
	 */
	public PlatformManager getPlatforms() {
		return platforms;
	}

	/**
	 * Returns the combat manager
	 * @return
	 */
	public CombatManager getCombat() {
		return combat;
	}
	
	/**
	 * Returns the player object
	 * @return
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Provides an interface for objects in the manager to check collisions
	 * @param x
	 * @param y
	 * @param side
	 * @param isPlayer
	 * @return
	 */
	public int checkPlatformCollision(int x, int y, int side, boolean isPlayer, GravitisedObj caller)
	{
		//System.out.println(Math.min(platforms.checkCollision(x, y, side),gObjects.checkCollision(x, y, side)));
		//int gColl = 
		
		int returned = Math.min(platforms.checkCollision(x, y, side,isPlayer),gObjects.checkCollision(x, y, side, isPlayer, caller));
		
		return returned;
	}
	
	/**
	 * Draws all objects
	 * @param g
	 */
	public void draw(PApplet g)
	{
		gObjects.drawObjects(g);
		player.draw(g);
		//g.alpha(rgb)
		platforms.draw(g);
		combat.drawObjects(g);
		tutorial.draw(g);
	}
	
	/**
	 * Acts all objects
	 * @param ratio
	 */
	public void act(double ratio)
	{
		player.act(ratio);
		combat.checkHits();
		gObjects.actObjects(ratio);
		combat.actObjects();
		tutorial.act();
	}
	
	/**
	 * Sends the mouse wheel movement to the player to the player object
	 * @param i
	 */
	public void mouseWheelMoved(int i)
	{
		player.getWheelMove(i);
	}
	
	/**
	 * Sends a key code to the player object
	 * @param e
	 */
	public void sendKeyCode(char e)
	{
		player.sendKeyCode(e);
	}
	
	public void sendSpecialKeyCode(int code) {
		player.sendSpecialKeyCode(code);
	}
	
	/**
	 * Returns the HP of the player
	 * @return
	 */
	public int getHP()
	{
		return player.getHP();
	}
	
	/**
	 * Sends a release key code to the player object
	 * @param e
	 */
	public void releaseKeyCode(char e)
	{
		player.releaseKeyCode(e);
	}
	
	/**
	 * Destroys the object in the environment matching the parameter
	 */
	@Override
	public void destroy(Object a) {
		gObjects.destroy(a);
		platforms.destroy(a);
		combat.destroy(a);
		
	}
	
	/**
	 * Returns the x value of the player
	 * @return
	 */
	public int getPlayerX()
	{
		return player.getX();
	}
	
	
	/**
	 * Returns the x value of the player
	 * @return
	 */
	public int getPlayerY()
	{
		return player.getY();
	}
	
	
	
	/**
	 * Returns the y value of the platform under the player
	 * @return
	 */
	public int getPlatformY()
	{
		return platformY;
	}
	
	/**
	 * Updates the y value of the platform directly under the player
	 * @param y
	 */
	public void sendPlatformY(int y)
	{
		platformY=y;
		//System.out.println(y);
	}
	
}