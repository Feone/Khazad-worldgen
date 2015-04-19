package worldStage.features;

import utility.Noise;
import worldStage.data.Unit;
import worldStage.data.coordinates.Coordinate;
import worldStage.data.coordinates.CoordinateMeta;
import worldStage.features.utility.EdgeDistanceHeightHandler;
import worldStage.features.utility.HeightHandler;


/**
 * Describes a continent, centre point with rough distances in all directions.
 * @author Feone
 *
 */
public class Mountain extends Unit {
	private int[]xPoints;
	private int[]yPoints;
	double xOffset,yOffset;

	public Mountain(Unit parent) {
		super(parent);
	}

	protected void initSpecifics() {
		initDataPoints(120, 400);
	}

	private void initDataPoints(int totalDistances, double distanceRange) {
		xOffset = getSemiRandom()*1000;
		yOffset = getSemiRandom()*1000;
		int mountainPoints = 15+(int)((1+getSemiRandom())*10);
		xPoints = new int[mountainPoints];
		yPoints = new int[mountainPoints];
		double direction = (getSemiRandom()*180);
		double wanderBias = getSemiRandom()*15;
		double wander = 0;
		double distance = 0;
		xPoints[0] = x;
		yPoints[0] = y;
		for (int i = 1; i < mountainPoints; i++) {
			wander = getSemiRandom()*15;
			direction = direction+wander+wanderBias;
			if (getSemiRandom() > 0)distance = (1+getSemiRandom())*2.5;
			xPoints[i] = xPoints[i-1]+getNextX(direction,distance)+(int)(getSemiRandom()*10); 
			yPoints[i] = yPoints[i-1]+getNextY(direction, distance)+(int)(getSemiRandom()*10);
		}
	}

	private int getNextX(double direction, double distance) {
		return (int)(Math.cos(Math.toRadians(direction))*distance);
	}
	private int getNextY(double direction, double distance) {
		return (int)(Math.sin(Math.toRadians(direction))*distance);
	}


	@Override
	protected HeightHandler getHeightHandler() {
		/*return new HeightHandler() {
			@Override
			public int getHeight(Coordinate location, CoordinateMeta meta) {
				return 50;
			}		
		};*/
		return new EdgeDistanceHeightHandler(0,150,zone.getLongestDistanceToEdge());
	}

	@Override
	protected boolean isInside(int x, int y) {
		int xDif,yDif;
		for (int i = 0; i < xPoints.length; i++) {
			xDif = x-(xPoints[i]+this.x);
			yDif = y-(yPoints[i]+this.y);

			double distance = Math.sqrt((xDif*xDif)+(yDif*yDif));
			if (distance < 15) {
				return true;
			}
		}
		return false;
	}
}