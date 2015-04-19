package render;

import java.util.ArrayList;

import worldStage.data.Unit;

public class Features {
	
	private ArrayList<ArrayList<Unit>> features;
	
	public Features(int layers) {
		features = new ArrayList<ArrayList<Unit>>();
		for (int i = 0; i < layers; i++) {
			features.add(new ArrayList<Unit>());
		}
		
		init();
	}
	
	private void init() {
		//TODO init features here.c
	}
	private void addFeature(Unit unit, int layer) {
		features.get(layer).add(unit);
	}
	
	public ArrayList<Unit> getFeatures(int layer) {
		return features.get(layer);
	}

}
