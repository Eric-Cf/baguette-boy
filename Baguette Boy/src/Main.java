
import java.awt.Dimension;


import javax.swing.JFrame;

import processing.awt.PSurfaceAWT;

public class Main {
	
	DrawingSurface drawing;
	
	public static void main(String args[]) {
		Main m = new Main();
		
		
	}
	
	public Main()
	{
		drawing = new DrawingSurface();		
		PSurfaceAWT surf = (PSurfaceAWT) drawing.getSurface();
		PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();                                                                                                         
		JFrame window = (JFrame)canvas.getFrame();
		
		window.setSize(600, 600);
		window.setMinimumSize(new Dimension(600,600));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.addMouseWheelListener(drawing);
		//window.addKeyListener(drawing);
		window.setResizable(true);
		window.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		drawing.sendSize(window.getWidth(), window.getHeight());
		//window.setUndecorated(true);
		
		//window.add(this);
		

		window.setVisible(true);
		canvas.requestFocus();
		
	}

		
	

}
