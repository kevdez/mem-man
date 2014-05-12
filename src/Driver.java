import java.util.LinkedList;
import java.util.Random;

public class Driver {
		
	private Random random;
	
//	 what to vary
	private double avg_req_sz;
	private double dist_req_sz;
	private String strategy; 		// "firstfit" or "nextfit"
	
//	 what to measure
	private double avg_mem_util;
	private double avg_srch_tm;
	
	private MemoryManager mem_man;
	
//	 to keep track of memory allocations
	private LinkedList<Integer> list;
	
	Driver(double avg_req_sz, double dist_req_sz, String strategy, MemoryManager mem_man)
	{
		this.random = new Random();
		
		this.avg_req_sz = avg_req_sz;
		this.dist_req_sz = dist_req_sz;
		
		this.strategy = strategy;
		
		this.mem_man = mem_man;
		
		this.list = new LinkedList<Integer>();
	}
	
	public void run()
	{
		
	}
	
	private int getSizeOfNextRequest()
	{
		double x = random.nextGaussian();
		double temp = dist_req_sz * x;
		return (int) (temp + avg_req_sz);
	}

	
}
