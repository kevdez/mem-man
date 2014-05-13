
public class Main {
	
	public static void main(String[] args) {
		
		double average = 3000;
		double distribution = 300;
		int simulation_steps = 10000;
		int memory_size = 5000;

		Driver dr = new Driver(average, distribution, simulation_steps, memory_size);
		dr.run();
		dr.displayResults();
		
		System.exit(0);
	}
}
