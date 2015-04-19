package worldStage.data;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import rendering.RenderData;
import rendering.WorldRenderData;
import worldStage.features.Continent;
import worldStage.features.Lake;
import worldStage.features.Mountain;

public class Model {
	public static Model instance;
	private short[][] world;
	private HashMap<String,Unit> unitMap;
	private RenderData renderData;
	
	public Model() {
		if (instance == null) instance = this;
		this.renderData = new WorldRenderData();
		unitMap = new HashMap<String,Unit>();
		world = new short[getSizeX()][getSizeY()];
		zero();
		int i,j;
		for (i = 0; i < getSizeX(); i++) {
			for (j = 0; j < getSizeY(); j++) {
				world[i][j] = 0;
			}
		}
		Logger logger = Logger.getLogger(getClass().getCanonicalName());
		logger.log(Level.INFO,"initializing world");
		ContainerUnit world = new ContainerUnit();
		logger.log(Level.INFO,"initializing continent");
		Unit continent = new Continent(world);
		logger.log(Level.INFO,"initializing mountains");
		new Mountain(continent);
		new Mountain(continent);
		new Mountain(continent);
		new Mountain(continent);
		new Mountain(continent);
		new Mountain(continent);
		logger.log(Level.INFO,"initializing lakes");
		new Lake(continent);
		new Lake(continent);
		new Lake(continent);
		new Lake(continent);
		new Lake(continent);
		logger.log(Level.INFO,"beginning processUnit");
		world.processUnit(0,0,getSizeX(), getSizeY());
		logger.log(Level.INFO,"beginning processChildren");
		world.processChildren(0,0,getSizeX(), getSizeY());
	}
	
	public void addHeight(int x, int y, short height) {
		world[x][y] += height;
	}
	
	public Unit getUnit(String tag) {
		return unitMap.get(tag);
	}
	
	public RenderData getRenderData() {
		return renderData;
	}
	
	public short getHeight(int x, int y) {
		return world[x][y];
	}
	
	public int getSizeX() {
		return 1000;
	}
	
	public int getSizeY() {
		return 1000;
	}
	
	private void zero() {
		int xSize = getSizeX();
		int ySize = getSizeY();
		int i,j;
		for (i = 0; i < xSize; i++) {
			for (j = 0; j < ySize; j++) {
				world[i][j] = 0;
			}
		}
	}
}