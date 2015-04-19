package worldStage.data;

import worldStage.data.coordinates.Coordinate;
import worldStage.features.utility.HeightHandler;

public class EmptyZone extends MapZone{

	private int xSize;
	private int ySize;
	public EmptyZone(int xSize, int ySize) {
		super();
		this.xSize = xSize;
		this.ySize = ySize;
	}

	@Override
	public void addToZone(int x, int y, int startHeight) {
	}
	@Override
	public void initMetaData() {
	}

	@Override
	public void initHeights(HeightHandler handler) {
	}
	@Override
	public void apply() {
	}	

	@Override
	public double getDistanceToEdge(int x, int y) {
		return Integer.MIN_VALUE;
	}
	
	@Override
	public boolean isCoordinateInZone(Coordinate coord) {
		return true;
	}
	@Override
	public Coordinate getRandomPointWithin() {
		int x = (int)(Math.random()*xSize);
		int y = (int)(Math.random()*ySize);
		return new Coordinate(x,y,0);
	}
	@Override
	public int getGreatestHeight() {
		return 0;
	}
}