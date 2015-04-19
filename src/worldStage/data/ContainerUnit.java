package worldStage.data;

import worldStage.data.coordinates.Coordinate;
import worldStage.features.utility.HeightHandler;


public class ContainerUnit extends Unit{
	
	public ContainerUnit() {
		super(null, -1, -1, 1, 1);	
		Model model = Model.instance;
		this.zone = new EmptyZone(model.getSizeX(), model.getSizeY());
	}

	@Override
	protected HeightHandler getHeightHandler() {
		return null;
	}

	@Override
	protected boolean isInside(int x, int y) {
		return true;
	}

	@Override
	protected void initSpecifics() {
	}

	@Override
	public Coordinate getPointWithin() {
		int x = (int)(Math.random() * Model.instance.getSizeX());
		int y = (int)(Math.random() * Model.instance.getSizeY());
		return new Coordinate(x,y,0);
	}
}