// Memory utilization and search times are significant differences
// Current bugs:	
//	Fragmenting occurs on the manager
//	The LinkedList implementation should be implemented as int[] array
//	Consider using the byte[] array implementation, with packing and unpacking

public class Main {
	
	public static void main(String[] args) {
		
		double average = 200;
		double distribution = 100;
		int simulation_steps = 10000;
		int memory_size = 5000;

		Driver dr = new Driver(average, distribution, simulation_steps, memory_size);
		dr.run();
		dr.displayResults();
		
		System.exit(0);
	}
}
