import java.awt.event.KeyEvent;

import processing.core.PApplet;

//import entities.*;

public class Manager implements DestroyListener{

	private GObjectManager gObjects;
	private PlatformManager platforms;
	private CombatManager combat;
	private Player player;
	private int platformY;
	
	
	public Manager() 
	{
		gObjects = new GObjectManager(this);
		platforms = new PlatformManager(this);
		player = new Player(100, 50, 10, 100, 100, this);
		combat = new CombatManager(this);
		
		
	}
	
	public GObjectManager getgObjects() {
		return gObjects;
	}

	public PlatformManager getPlatforms() {
		return platforms;
	}

	public CombatManager getCombat() {
		return combat;
	}
	
	public Player getPlayer() {
		return player;
	}

	public int checkPlatformCollision(int x, int y, int side, boolean isPlayer)
	{
		//System.out.println(Math.min(platforms.checkCollision(x, y, side),gObjects.checkCollision(x, y, side)));
		return Math.min(platforms.checkCollision(x, y, side,isPlayer),gObjects.checkCollision(x, y, side, isPlayer));
	}
	
	public void draw(PApplet g)
	{
		gObjects.drawObjects(g);
		player.draw(g);
		platforms.draw(g);
		combat.drawObjects(g);
	}
	
	public void act(double ratio)
	{
		player.act(ratio);
		gObjects.actObjects(ratio);
		combat.actObjects();
		combat.checkHits();
	}
	
	public void mouseWheelMoved(int i)
	{
		player.getWheelMove(i);
	}
	
	public void sendKeyCode(char e)
	{
		player.sendKeyCode(e);
	}
	
	public void releaseKeyCode(char e)
	{
		player.releaseKeyCode(e);
	}

	@Override
	public void destroy(Object a) {
		gObjects.destroy(a);
		platforms.destroy(a);
		combat.destroy(a);
		
	}
	
	public int getPlayerX()
	{
		return player.getX();
	}
	
	public int getPlayerY()
	{
		return platformY;
	}
	
	public void sendPlatformY(int y)
	{
		platformY=y;
		//System.out.println(y);
	}
	
}