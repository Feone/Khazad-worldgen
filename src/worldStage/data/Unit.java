package worldStage.data;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import worldStage.data.coordinates.Coordinate;
import worldStage.features.utility.HeightHandler;

/**
 * Single defined set of data on an area with fixed dimentions.
 * @author Feone
 *
 */
public abstract class Unit {
	protected int x, y;
	protected int genLevel;
	private int priority; 
	private ArrayList<Unit> children;
	protected MapZone zone;
	private Unit parent;
	private SemiRandomGen semiRandom;
	public Unit(Unit parent) {
		this(parent,-1,-1);
	}
	
	public Unit(Unit parent,int genLevel,int priority) {
		this(parent,genLevel,priority, 100, 5000);
	}
	
	public Unit(Unit parent,int genLevel,int priority, int seedCount, double seedRange) {
		this.zone = new MapZone();
		this.parent = parent;
		if (parent != null) {
			parent.addChild(this);
		}
		children = new ArrayList<Unit>();
		this.priority = priority;
		this.genLevel = genLevel;
		this.semiRandom = new SemiRandomGen(seedCount, seedRange);
		initSpecifics();
	}
	
	protected abstract boolean isInside(int x, int y); //Check if point is part of area.
	protected abstract void initSpecifics(); //Initialize unit-specific fields.


	
	public void addChild(Unit child) {
		children.add(child);
	}
	
	public void processUnit(int xStart, int yStart, int xEnd, int yEnd) {
		Logger logger = Logger.getLogger(getClass().getCanonicalName());
		logger.log(Level.INFO,"Processing unit for "+this.getClass().getName());
		logger.log(Level.INFO,"Processing included space.");
		int i,j;
		Model model = Model.instance;
		if (parent != null) {
			Coordinate random = parent.getPointWithin();
			this.x = random.x;
			this.y = random.y;
		} else {
			this.x = 0;
			this.y = 0;
		}
		for (i = xStart; i < xEnd; i++) {
			for (j = yStart; j < yEnd; j++) {
				if (isInside(i,j)) {
					zone.addToZone(i,j,model.getHeight(i,j));
				}
			}
		}
		logger.log(Level.INFO,"Processing meta data");
		zone.initMetaData();
		logger.log(Level.INFO,"Processing heights");
		zone.initHeights(getHeightHandler());
		logger.log(Level.INFO,"Applying differences to world");
		zone.apply();
	}
		
	protected abstract HeightHandler getHeightHandler();
	public void processChildren(int xStart, int yStart, int xEnd,  int yEnd) { 
		if (children.isEmpty()) {
			return;
		}
		Unit child;
		Logger.getLogger(this.getClass().getCanonicalName()).log(Level.INFO,"Processing child Units.");
		for (int i = 0; i < children.size(); i++)  {
			child = children.get(i);
			if (child.getGenLevel() != genLevel-1) {
				Logger.getLogger(this.getClass().getCanonicalName()).log(Level.WARNING,"Child unit genLevel inconsistent."); //TODO add useful info.
			}
			child.processUnit(xStart, yStart, xEnd, yEnd);
		}
		for (int i = 0; i < children.size(); i++) {
			child = children.get(i);
			child.processChildren(xStart, yStart, xEnd, yEnd);
		}
	}
	
	protected double getSemiRandom() {
		return semiRandom.get();
	}

	public boolean isInside(int x, int y, int z) {
		return zone.isCoordinateInZone(new Coordinate(x,y,z));
	}
	
	public int getPriority() {
		return priority;
	}
	
	public int getGenLevel() {
		return genLevel;
	}
	
	public Unit getParent() {
		return parent;
	}
	
	public double getDistanceToEdge(int x, int y) {
		return zone.getDistanceToEdge(x,y);
	}
	
	public double getLongestDistanceToEdge() {
		return zone.getLongestDistanceToEdge();
	}
	
	public Coordinate getPointWithin() {
		return zone.getRandomPointWithin();
	}
}
