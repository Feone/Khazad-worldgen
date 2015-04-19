package worldStage.features.utility;

import worldStage.data.coordinates.Coordinate;
import worldStage.data.coordinates.CoordinateMeta;

public class EdgeDistanceHeightHandler implements HeightHandler {
	private int minHeight;
	private int maxHeight;
	private double maxDistanceToEdge;

	public EdgeDistanceHeightHandler(int minHeight, int maxHeight,double maxDistanceToEdge) {
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
		this.maxDistanceToEdge = maxDistanceToEdge;
	}
	
	@Override
	public int getHeight(Coordinate location, CoordinateMeta meta) {
		double distance = meta.getDistanceToEdge();
		int height = minHeight;
		int heightRange = maxHeight-minHeight;
		height += (int)((double)heightRange * (distance/maxDistanceToEdge));
		return height;
	}

}
