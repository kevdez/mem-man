
public class MemoryManager extends PackableMemory{

	public MemoryManager(int mem_size)
	{
		super(mem_size);
		
	}
	
	
//	The three main memory manager functions
	public void mm_init()
	{
		this.mem = new byte[this.size];
	}
	
	public int mm_request(int n)
	{
		
		return 0;
	}
	
	public void mm_release(int p)
	{
		
	}
	
}
