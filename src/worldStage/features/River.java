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
public class River extends Unit {
	int xRoot;
	int yRoot;
	double xOffset,yOffset;

	public River(Unit parent) {
		super(parent);
	}

	protected void initSpecifics() {
		initDataPoints(120, 400);
	}

	private void initDataPoints(int totalDistances, double distanceRange) {
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
		return false;
	}
}