
public class MemoryManager {

	private int mem_size;
<<<<<<< HEAD
	private byte[] array;
=======
	private int[] array;
>>>>>>> efa8a87f1e029cbeee6602c3ebb493a9b9af5085

	public MemoryManager(int mem_size)
	{
		this.mem_size = mem_size;
<<<<<<< HEAD
		this.array = new byte[mem_size];
	}
	
	
//	The three main memory manager functions
	public void mm_init()
	{
		this.array = new byte[mem_size];
	}
	
	public int mm_request(int n)
	{
		
		return 0;
	}
	
	public void mm_release(int p)
	{
		
	}
=======
		this.array = new int[mem_size];
	}
	
>>>>>>> efa8a87f1e029cbeee6602c3ebb493a9b9af5085
}
