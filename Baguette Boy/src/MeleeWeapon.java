import processing.core.PApplet;
import processing.core.PImage;

public class MeleeWeapon extends Item{
	public static PImage sword;
	
	public static final int ATT_DMG = 18;
	public static final int ATT_DELAY = 500;
	private long lastHit;
	
	public MeleeWeapon(int x, int y, String type) {
		super(type, x, y);
		width = 60;
		height = 105;
		lastHit = 0;
		
	}
	
	public void display(PApplet g, int x, int y, float ratio, int fill) {
		g.pushStyle();
		g.tint(255, fill);
		sword.resize((int)(ratio*80), (int)(ratio*140));
		g.image(sword, x, y);
		//attack cooldown bar
		float cooldown = 1.0f - (System.currentTimeMillis() - lastHit)/(float)ATT_DELAY;
		if (cooldown >0) {
			g.fill(200, 150);
			g.rect(x, y, (ratio)*width * cooldown,(ratio)* height);
		}
		g.popStyle();
		
		
		g.text(type, x, y+40*ratio);
	}
	
	public void draw(PApplet g) { 
		if(!pickedUp) {
			sword.resize(width, height);
			g.image(sword, x, y + (float)(15*Math.sin(System.currentTimeMillis()/250.0)));
		}
	}
	
	public boolean attack() {
		if (lastHit + ATT_DELAY <= System.currentTimeMillis()) {
			lastHit = System.currentTimeMillis();
			return true;
		}
		return false;
	}
}
