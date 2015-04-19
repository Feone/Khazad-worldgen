package worldStage.data.coordinates;

/**
 * Generic coordinate class.
 * @author Feone
 *
 */
public class Coordinate {

	public int x,y,z;
	
	public Coordinate() {
		x = 0;
		y = 0;
		z = 0;
	}

	public Coordinate(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void set(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Coordinate copy() {
		return new Coordinate(x,y,z);
	}
	
	public Coordinate above() {
		return new Coordinate(x,y-1,z);
	}
	public Coordinate below() {
		return new Coordinate(x,y+1,z);
	}
	public Coordinate left() {
		return new Coordinate(x-1,y,z);
	}
	public Coordinate right() {
		return new Coordinate(x+1,y,z);
	}
	
	@Override
	public boolean equals(Object other) {
		Coordinate coord = (Coordinate)other;
		if (coord.x == x) {
			if (coord.y == y) {
				if (coord.z == z) {
					return true;
				}
			}
		}
		return false;
	}
	@Override
	public int hashCode() {
		String text = "x"+x+"y"+y+"z"+z;
		return text.hashCode();		
	}
}