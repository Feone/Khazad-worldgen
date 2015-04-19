package worldStage.data.coordinates;

public class CoordinateMeta {
	private int startHeight;
	private int height;
	private int edgeDifX;
	private int edgeDifY;
	
	public CoordinateMeta(int startHeight) {
		this(startHeight,-1,-1,-1);
	}
	public CoordinateMeta(int startHeight, int height, int edgeDifX, int edgeDifY) {
		this.startHeight = startHeight;
		this.height = height;
		this.edgeDifX = edgeDifX;
		this.edgeDifY = edgeDifY;
	}
	
	public int getStartHeight() {
		return startHeight;
	}
	
	public void setStartHeight(int startHeight) {
		this.startHeight = startHeight;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getEdgeDifX() {
		return edgeDifX;
	}
	
	public void setEdgeDifX(int edgeDifX) {
		this.edgeDifX = edgeDifX;
	}
	
	public int getEdgeDifY() {
		return edgeDifY;
	}
	
	public void setEdgeDifY(int edgeDifY) {
		this.edgeDifY = edgeDifY;
	}
	
	public double getDistanceToEdge() {
		return Math.sqrt(edgeDifX*edgeDifX + edgeDifY*edgeDifY);
	}
}