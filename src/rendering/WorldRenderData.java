package rendering;

import java.awt.Color;

public class WorldRenderData implements RenderData{
	
	private double highest = -1;
	
	@Override
	public Color getHeightColor(short height) {
		if (height <= 0) return Color.blue;
		if (highest < height) highest = height;
		int color = (int)(255-((255.0 / highest)*height));
		return new Color(0,color,0);
	}
	
	@Override
	public Color getMaterialColor(short material) {
		if (material == 0) return Color.BLACK;
		if (highest < material) highest = material;
		int color = (int)((255.0 / highest)*material);
		return new Color(255-color,color,255-color);
	}

}
