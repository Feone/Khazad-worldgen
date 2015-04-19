package worldStage.features.utility;

import utility.Noise;
import worldStage.data.coordinates.Coordinate;
import worldStage.data.coordinates.CoordinateMeta;

public class RandomizedEdgeDistanceHeightHandler implements HeightHandler {
	private int minHeight;
	private int maxHeight;
	private double maxDistanceToEdge;
	private double seed;
	private double step;
	private double range;

	public RandomizedEdgeDistanceHeightHandler(int minHeight, int maxHeight,double maxDistanceToEdge, double seed, double step, double range) {
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
		this.maxDistanceToEdge = maxDistanceToEdge;
		this.range = range;
		this.step = step;
		this.seed = seed;
	}
	
	@Override
	public int getHeight(Coordinate location, CoordinateMeta meta) {
		double distance = meta.getDistanceToEdge();
		int height = minHeight;
		int heightRange = maxHeight-minHeight;
		height += (int)((double)heightRange * (distance/maxDistanceToEdge));
		
		int x = location.x;
		int y = location.y;
		double noise = Noise.noise(seed+(x*step), seed+(y*step))*range;
		
		return height + (int)noise;
	}

}
