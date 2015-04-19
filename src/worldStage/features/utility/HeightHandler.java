package worldStage.features.utility;

import worldStage.data.coordinates.Coordinate;
import worldStage.data.coordinates.CoordinateMeta;

public interface HeightHandler {
	public int getHeight(Coordinate location, CoordinateMeta meta);
}
