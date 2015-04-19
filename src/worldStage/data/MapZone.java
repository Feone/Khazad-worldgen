package worldStage.data;

import java.util.*;

import worldStage.data.coordinates.Coordinate;
import worldStage.data.coordinates.CoordinateMeta;
import worldStage.features.utility.HeightHandler;

public class MapZone {
	private HashMap<Coordinate, Boolean> zoneMap;
	private HashMap<Coordinate, CoordinateMeta> metaMap;
	private double longestDistanceToEdge;
	private int greatestHeight;

	public MapZone() {
		longestDistanceToEdge = Integer.MIN_VALUE;
		greatestHeight = Integer.MIN_VALUE;
		zoneMap = new HashMap<Coordinate, Boolean>();
		metaMap = new HashMap<Coordinate, CoordinateMeta>();
	}
	
	/*
	 * Usage. 
	 * Initialize MapZone.
	 * Use addToZone(x,y,startheight) to add the 2d space to this zone.
	 * Call initializeMetaData() to map the distance to the edge for each point in the zone;
	 * Call initHeights() to map the height of each zone and to add each piece to the map.
	 * 
	 * Regular info methods can now be used.
	 * 
	 * Call apply() to add this zone onto the model data.
	 * 
	 */

	
	public void addToZone(int x, int y, int startHeight) {
		CoordinateMeta meta = new CoordinateMeta(startHeight);
		Coordinate key = new Coordinate(x,y,0);
		metaMap.put(key, meta);
	}
	
	public void initMetaData() {
		HashSet<Coordinate> set = new HashSet<Coordinate>();
		Set<Coordinate> coords = metaMap.keySet();
		Iterator<Coordinate> iterator = coords.iterator();
		while (iterator.hasNext()) {
			set.add(iterator.next().copy());
		}
		mapNearestPoints(set,null);
	}

	
	public void initHeights(HeightHandler handler) {
		Set<Coordinate> keys = metaMap.keySet();
		Iterator<Coordinate> iterator = keys.iterator();
		Coordinate current;
		CoordinateMeta meta;
		int start;
		int height;
		while (iterator.hasNext()) {
			current = iterator.next();
			meta = metaMap.get(current);
			start=meta.getStartHeight();
			height = handler.getHeight(current, meta);
			meta.setHeight(height);
			if (height > greatestHeight) greatestHeight = height;
			for (int i = start; i <= start+height; i++) {
				Coordinate cell = new Coordinate(current.x, current.y, i);
				 if (zoneMap.get(cell) == null) {
					 zoneMap.put(cell, new Boolean(true));
				 }
			}
		}
	}
	
	public void apply() {
		Set<Coordinate> keys = metaMap.keySet();
		Iterator<Coordinate> iterator = keys.iterator();
		Coordinate current;
		Model model = Model.instance;
		while(iterator.hasNext()) {
			current = iterator.next();
			model.addHeight(current.x,  current.y, (short)metaMap.get(current).getHeight());
		}
	}
	
	private void mapNearestPoints(HashSet<Coordinate> keys, HashSet<Coordinate> previous) {
		Iterator<Coordinate> iterator = keys.iterator();
		HashSet<Coordinate> currentLayer = new HashSet<Coordinate>();
		Coordinate current;
		Coordinate neighbour;
		while(iterator.hasNext()) {
			current = iterator.next();
			neighbour = current.above();
			if (processCurrent(keys,currentLayer,previous,current,neighbour,iterator,0,-1)) {
				continue;
			}
			neighbour = current.below();
			if (processCurrent(keys,currentLayer,previous,current,neighbour,iterator,0,1)) {
				continue;
			}
			neighbour = current.left();
			if (processCurrent(keys,currentLayer,previous,current,neighbour,iterator,-1,0)) {
				continue;
			}
			neighbour = current.right();
			if (processCurrent(keys,currentLayer,previous,current,neighbour,iterator,1,0)) {
				continue;
			}
		}
		if (!keys.isEmpty()) {
			mapNearestPoints(keys,currentLayer);
		}
	}
	
	
	private boolean processCurrent(HashSet<Coordinate> keys, HashSet<Coordinate> currentLayer, HashSet<Coordinate> previous, Coordinate current, Coordinate neighbour, Iterator<Coordinate> it, int xDif, int yDif) {
		if ((!keys.contains(neighbour)) && (!currentLayer.contains(neighbour))) {
			CoordinateMeta meta = metaMap.get(current);
			CoordinateMeta metaOther = metaMap.get(neighbour);
			int newDifX ;
			int newDifY;
			if (previous != null) {
				metaOther = metaMap.get(neighbour);
				newDifX = metaOther.getEdgeDifX() + xDif;
				newDifY = metaOther.getEdgeDifY() + yDif;
				meta.setEdgeDifX(newDifX);
				meta.setEdgeDifY(newDifY);
				double distance = meta.getDistanceToEdge();
				if (distance > longestDistanceToEdge) {
					longestDistanceToEdge = distance;
				}
			} else {
				meta.setEdgeDifX(0);
				meta.setEdgeDifY(0);
			}
			currentLayer.add(current);
			it.remove();
			return true;
		}
		return false;
	}
	

	private CoordinateMeta getMeta(int x, int y) {
		return metaMap.get(new Coordinate(x,y,0));
	}

	//Utility methods.
	public double getLongestDistanceToEdge() {
		return longestDistanceToEdge;
	}
	
	public double getDistanceToEdge(int x, int y) {
		CoordinateMeta meta = getMeta(x,y);
		if (meta == null) return -1;
		int xDif = meta.getEdgeDifX();
		int yDif = meta.getEdgeDifY();
		return Math.sqrt(xDif*xDif + yDif*yDif);
	}
	
	public boolean isCoordinateInZone(Coordinate coord) {
		Boolean target = zoneMap.get(coord);
		return (target != null);
	}

	public Coordinate getRandomPointWithin() {
		Set<Coordinate> keys = zoneMap.keySet();
		int total = keys.size();
		Iterator<Coordinate> iterator = keys.iterator();
		int pick = (int)(Math.random() * total); //FIXME semi vs full random.
		for (int i = 0; i < pick-1; i++) {
			iterator.next();
		}
		return iterator.next();
	}
	
	public int getGreatestHeight() {
		return greatestHeight;
	}
}