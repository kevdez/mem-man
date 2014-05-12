
public class Main {

	public static void main(String[] args) {
		
		double average = 0;
		double distribution = 2;
		int simulation_steps = 10000;
		int memory_size = 10000;

		Driver dr = new Driver(average, distribution, simulation_steps, memory_size);
		dr.run();
			
		System.exit(0);
	}

}
