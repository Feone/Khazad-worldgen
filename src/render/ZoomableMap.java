package render;

import java.util.ArrayList;
import java.util.HashMap;

import worldStage.data.coordinates.Coordinate;
import worldStage.data.coordinates.CoordinateMeta;

public class ZoomableMap {
	
	private HashMap<Coordinate,ZoomableTile> map;
	
	private ZoomableMap() {
		
	}
	

	
	protected class ZoomableTile {
		private int ROOT_SIZE = 16;
		private int[] sizes = new int[]{1,2,4,8,16,32,64,128};
		private short[] highestPoints;
		private ArrayList<short[][]> heightData;
		private ArrayList<short[][]> tileData;
		private Coordinate location;
		private CoordinateMeta meta;
		
		public ZoomableTile() {
			highestPoints = new short[sizes.length];
			heightData = new ArrayList<short[][]>();
			tileData = new ArrayList<short[][]>();
			for (int i = 0; i < sizes.length; i++) {
				heightData.add(new short[sizes[i]*ROOT_SIZE][sizes[i]*ROOT_SIZE]);
				tileData.add(new short[sizes[i]*ROOT_SIZE][sizes[i]*ROOT_SIZE]);
			}
		}
		
		public short[][] getHeight(int index) {
			return heightData.get(index);
		}
		public short[][] getData(int index) {
			return tileData.get(index);
		}
		
		public int getTotalIndices() {
			return sizes.length;
		}
		
		public void setTileData(int x, int y, int layer, short value) {
			tileData.get(layer)[x][y] = value;
			if (highestPoints[layer] < value) {
				highestPoints[layer] = value;
			}
		}
		
		public void setHeightData(int x, int y, int layer, short value) {
			heightData.get(layer)[x][y] = value;
			if (highestPoints[layer] < value) {
				highestPoints[layer] = value;
			}
		}
	}

}