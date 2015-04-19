import java.util.logging.Level;
import java.util.logging.Logger;

import rendering.HeightRenderer;
import worldStage.data.Model;

public class Main {
	public static void main(String[] args) {
	//	Logger parent = Logger.getLogger("");
		try {
			//parent.addHandler(new ConsoleHandler());
			//parent.addHandler(new FileHandler("PATH TO FILE!").setFormatter(/*new SimpleFormatter()*//*new XMLFormatter()*/));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		Logger logger = Logger.getLogger(Main.class.getCanonicalName());
		logger.log(Level.INFO,"Initializing Model.");
		Model model = new Model();
		logger.log(Level.INFO,"Model initialized.");
		logger.log(Level.INFO,"Initializing HeightRenderer.");
		new HeightRenderer(model);
		logger.log(Level.INFO,"HeightRenderer initialized.");
		//new MaterialRenderer(model.getUnit("world"));
		

	}
}