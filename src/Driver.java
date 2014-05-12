import java.util.LinkedList;
import java.util.Random;

public class Driver {
		
	private Random random;
	
//	 what to vary
	private double avg_req_sz;
	private double dist_req_sz;
	private int sim_steps;
	private String strategy; 		// "firstfit" or "nextfit"
	
//	 what to measure
	private double avg_mem_util;
	private double avg_srch_tm;
	
	private MemoryManager mem_man;
	
//	 to keep track of memory allocations
	private LinkedList<Integer> list;
	
	Driver(double avg_req_sz, double dist_req_sz, int sim_steps, int mem_size)
	{
		this.random = new Random();
		
		this.avg_req_sz = avg_req_sz;
		this.dist_req_sz = dist_req_sz;
		this.sim_steps = sim_steps;
		this.strategy = "firstfit";
		
		this.mem_man = new MemoryManager(mem_size);
		
		this.list = new LinkedList<Integer>();
	}
	
	public void setFirstFit()
	{
		this.strategy = "firstfit";
	}
	
	public void setNextFit()
	{
		this.strategy = "nextfit";
	}
	
	public void run()
	{
		if(strategy.equals("firstfit"))
		{
			firstFitRun();
		}
		else if (strategy.equals("nextfit"))
		{
			nextFitRun();
		}
	}
	
	private void firstFitRun()
	{
		
	}
	
	private void nextFitRun()
	{
		
	}
	
	private int getSizeOfNextRequest()
	{
		double x = random.nextGaussian();
		double temp = dist_req_sz * x;
		return (int) (temp + avg_req_sz);
	}

	
}
