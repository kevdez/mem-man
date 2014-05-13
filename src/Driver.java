
import java.util.Random;

public class Driver {
		
	private Random random;
	
	//statistics
	double[] util1;
	int[] searchtime1;
	double[] util2;
	int[] searchtime2;
	
//	 what to vary
	private double avg_req_sz;
	private double dist_req_sz;
	private int sim_steps;
	private String strategy; 		// "firstfit" or "nextfit"
	
	private MemoryManager mem_man;
	
	Driver(double avg_req_sz, double dist_req_sz, int sim_steps, int mem_size)
	{
		this.random = new Random();
		
		this.avg_req_sz = avg_req_sz;
		this.dist_req_sz = dist_req_sz;
		this.sim_steps = sim_steps;
		this.strategy = "firstfit";	
		this.mem_man = new MemoryManager(mem_size, strategy);
	}
	
	public void run()
	{
		boolean continueRunning = true;
		this.util1 = new double[sim_steps];
		this.searchtime1 = new int[sim_steps];
		for(int i = 0; i < sim_steps; i++)
		{
			do
			{
				int n = getSizeOfNextRequest();
				try {
					mem_man.mm_request(n);
				} catch (Exception e) {
					continueRunning = false;
				}
			} while (continueRunning);
			
			// record memory utilization
			util1[i] = mem_man.recordMemoryUtilization();
			searchtime1[i] = mem_man.holesCounted;
			
			// select block p to be released
			try {
				mem_man.mm_release(nextIndexToRemove());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			continueRunning = true;
		}
		
		this.util2 = new double[sim_steps];
		this.searchtime2 = new int[sim_steps];
		
		mem_man.setNextFit();
		mem_man.mm_init();
		
		for(int i = 0; i < sim_steps; i++)
		{
			do
			{
				int n = getSizeOfNextRequest();
				try {
					mem_man.mm_request(n);
				} catch (Exception e) {
					continueRunning = false;
				}
			} while (continueRunning);
			
			// record memory utilization
			util2[i] = mem_man.recordMemoryUtilization();
			searchtime2[i] = mem_man.holesCounted;
			
			// select block p to be released
			try {
				mem_man.mm_release(nextIndexToRemove());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			continueRunning = true;
		}
	}
	
	private int getSizeOfNextRequest()
	{
		double x = random.nextGaussian();
		double temp = dist_req_sz * x;
		return (int) (temp + avg_req_sz);
	}

	private int nextIndexToRemove()
	{
		int i = random.nextInt(mem_man.list.size());
		while(mem_man.list.get(i).usableSize < 0)
		{
			i = random.nextInt(mem_man.list.size());
		}
		return i;
	}

	public void displayResults()
	{
		double totalUtil = 0;
		double totalAvg = 0;
		for(int i = 0; i < sim_steps; i++)
		{
			totalUtil += this.util1[i];
			totalAvg += this.searchtime1[i];
		}
		System.out.println("Avg Mem Util for FIRST FIT:" + totalUtil/sim_steps);
		System.out.println("Avg Search Time for FIRST FIT:" + totalAvg/sim_steps);
		
		totalUtil = 0;
		totalAvg = 0;
		for(int i = 0; i < sim_steps; i++)
		{
			totalUtil += this.util2[i];
			totalAvg += this.searchtime2[i];
		}
		System.out.println("Avg Mem Util for NEXT FIT:" + totalUtil/sim_steps);
		System.out.println("Avg Search Time for NEXT FIT:" + totalAvg/sim_steps);
	}
	
}
