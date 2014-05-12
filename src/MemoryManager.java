
public class MemoryManager {

	private int mem_size;
	private byte[] array;

	public MemoryManager(int mem_size)
	{
		this.mem_size = mem_size;
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
	
}
