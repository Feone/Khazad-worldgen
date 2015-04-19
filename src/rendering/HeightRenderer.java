package rendering;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import worldStage.data.Model;

@SuppressWarnings("serial")
public class HeightRenderer extends JFrame implements Runnable {
	public static int WIDTH;
	public static int HEIGHT;
	
	protected DrawPanel window;
	protected Container world;

	private int tileSizeX, tileSizeY;
	private int tilesX, tilesY;
	
	private Model model;
	
	public HeightRenderer(Model model) {
		super();
		this.model = model;
		init();
		showChanges();
		Thread thread = new Thread(this);
		thread.start();
	}
	
	class DrawPanel extends JPanel	{
		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g)	{
			super.paintComponent(g);
			super.setBackground(Color.white);
			draw(g);
			this.requestFocus();
		}

		public Dimension getPreferredSize(){
			return new Dimension(1000,1000);
		}
	}
	
	private void init() {
		int maxWidth = 1400;
		int maxHeight = 1400;
		window = new DrawPanel();
		world = this.getContentPane();
		world.setLayout(new BorderLayout());
		world.add(window, BorderLayout.CENTER);
		tilesX = model.getSizeX();
		tilesY = model.getSizeY();
		tileSizeX = maxWidth/tilesX;
		tileSizeY = maxHeight/tilesY;
		WIDTH = tileSizeX*tilesX;
		HEIGHT = tileSizeY * tilesY;
	}
	
	protected void showChanges(){
		this.setSize(WIDTH,HEIGHT);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void draw() {
		Graphics g =  window.getGraphics();
		draw(g);
	}
	
	public void draw(Graphics g) {
		int i,j;
		for (i = 0;i< tilesX; i++) {
			for (j = 0; j<tilesY; j++) {
				g.setColor(getTileColor(i,j));
				g.fillRect((int)(i*tileSizeX), (int)(j*tileSizeY),tileSizeX,tileSizeY);
			}
		}
	}
	
	private Color getTileColor(int x, int y) {
		return model.getRenderData().getHeightColor(model.getHeight(x,y));
	}
	
	
	@Override
	public void run() {
		while(true) { /*
			model.doTimeStep();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			draw();
			repaint(); */
		}
	}
}