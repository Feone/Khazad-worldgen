package worldStage.data;

import utility.Noise;

public class SemiRandomGen {
	private int seedCounter;
	private int totalSeeds;
	private double[] seeds;
	private double range;
	
	public SemiRandomGen(int amount, double range) {
		seedCounter = -1;
		totalSeeds = amount;
		this.range = range;
		seeds = new double[amount];
		for (int i = 0; i < amount; i++) {
			seeds[i] = Math.random() * range;
		}
	}
	
	public double get() {
		seedCounter += 1;
		if (seedCounter >= totalSeeds ) {
			seedCounter = 0;
		}
		return Noise.noise(seeds[seedCounter], -seeds[seedCounter]);
	}
	public double getRange() {
		return range;
	}
	public int getAmountOfSeeds() {
		return totalSeeds;
	}
}