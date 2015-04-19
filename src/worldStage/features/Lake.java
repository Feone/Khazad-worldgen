package worldStage.features;

import utility.Noise;
import worldStage.data.Model;
import worldStage.data.Unit;
import worldStage.features.utility.EdgeDistanceHeightHandler;
import worldStage.features.utility.HeightHandler;
import worldStage.features.utility.RandomizedEdgeDistanceHeightHandler;


/**
 * Describes a continent, centre point with rough distances in all directions.
 * @author Feone
 *
 */
public class Lake extends Unit {
	private int[] distances;
	double xOffset,yOffset;

	public Lake(Unit parent) {
		super(parent);
	}

	@Override
	protected void initSpecifics() {
		int totalDistances = 50;
		double distanceRange = 30;
		distances = new int[totalDistances];
		xOffset = getSemiRandom()*100;
		yOffset = getSemiRandom()*100;
		double radius = .9 ;
		double step = 360/(double)totalDistances;
		double angle;
		double noiseX,noiseY;
		double halfDist = distanceRange/2.0;
		for (int i = 0; i < totalDistances; i++) {		
			angle = step*i;
			noiseX = xOffset+(Math.cos(Math.toRadians(angle))*radius);
			noiseY = yOffset+(Math.sin(Math.toRadians(angle))*radius);
			distances[i] =15+ (int)((1+Noise.noise(noiseX,noiseY))*halfDist);
		}
	}

	private double getPointDistance(int x1, int y1, int x2, int y2) {
		int xDif = x2-x1;
		int yDif = y2-y1;
		int xSize = Model.instance.getSizeX();
		int ySize = Model.instance.getSizeY(); //TODO optimize.
		if (xDif > (xSize/2)) xDif -= xSize;
		if (yDif > (ySize/2)) yDif -= ySize;
		if (xDif < (xSize/-2)) xDif += xSize;
		if (yDif < (ySize/-2)) yDif += ySize;
		return Math.sqrt((xDif*xDif)+(yDif*yDif));
	}
	
	private double getDirection(int x1, int y1, int x2, int y2) {
		double xDif = x2 - x1;
		double yDif = y2 - y1;
		int xSize = Model.instance.getSizeX();
		int ySize = Model.instance.getSizeY(); //TODO optimize.
		if (xDif > (xSize/2)) xDif -= xSize;
		if (yDif > (ySize/2)) yDif -= ySize;
		if (xDif < (xSize/-2)) xDif += xSize;
		if (yDif < (ySize/-2)) yDif += ySize;
		return Math.toDegrees(Math.atan2(yDif, xDif));
	}
	
	private int getDistanceMax(double relativeAngle) {
		double step = 360.0/(double)distances.length;
		int index = (int) (relativeAngle/step);
		double weight = (relativeAngle%step)/step;
		double distance1,distance2;
		distance1 = getDistance(index);
		distance2 = (index < 0) ? getDistance(index-1):getDistance(index+1);
		return (int)(distance2*weight + distance1*(1-weight));
	}
	
	private int getDistance(int index) {
		if (index >= distances.length) index -= distances.length;
		if (index < 0) index += distances.length;
		return distances[index];
	}


	@Override
	protected HeightHandler getHeightHandler() {
		double seed = getSemiRandom();
		double step = 0.05;
		double range = 15;
		return new RandomizedEdgeDistanceHeightHandler(-5,-50, zone.getLongestDistanceToEdge(), seed, step, range);
	}

	@Override
	protected boolean isInside(int x, int y) {
		double distance = getPointDistance(this.x, this.y, x, y);
		double relativeAngle = getDirection(this.x, this.y, x, y)+180;
		if (relativeAngle == 360) relativeAngle -= 0.1;
		if (relativeAngle == 0) relativeAngle += 0.1; //prevent 1 pixel wide distortion due to hitting new index.
		return (distance <= getDistanceMax(relativeAngle));
	}
}