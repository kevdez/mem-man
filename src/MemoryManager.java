import java.util.Arrays;
import java.util.LinkedList;

public class MemoryManager{

	int mem_size;
	int[] mem;
	String strat;
	
	LinkedList<MemoryBlock> list;
	
	public MemoryManager(int mem_size, String strat)
	{
		this.mem_size = mem_size;
		this.strat = strat;
		this.list = new LinkedList<MemoryBlock>();
		
		this.list.add(mm_init());
	}
	
//	The three main memory manager functions
	public MemoryBlock mm_init()
	{
		this.mem = new int[mem_size];
		Arrays.fill(mem, 0);
		
		// tag + size
		mem[0] = -(mem_size - 4);
		mem[mem_size-1] = -(mem_size - 4);
		
		// predecessor and successor
		mem[1] = -1;			// purposefully out of bounds
		mem[2] = mem_size+1;	// purposefully out of bounds
		
		return new MemoryBlock(0, mem_size, -(mem_size-4));
	}
	
//	returns index of first usable word or error if insufficient memory
	public int mm_request(int n) throws InsufficientMemoryException
	{
		if(strat.equals("firstfit"))
		{
			for(MemoryBlock i : list)
			{
				if (-(i.usableSize) >= n) // available size >= n
				{
					// make a block, allocate, change array
				}
			}
			
			// throw insuff memory exception
		}
		else if(strat.equals("nextfit"))
		{
			
		}
		
		return 0;
	}
	
	public void mm_release(int begIndex)
	{
		
	}
	
	public void setFirstFit()
	{
		this.strat = "firstfit";
	}
	
	public void setNextFit()
	{
		this.strat = "nextfit";
	}
	
	
	
	
	
	
	
	
	private class MemoryBlock
	{
		int beginningIndex;
		int endingIndex;
		
		int usableSize;
		
		public MemoryBlock(int beg, int end, int sz)
		{
			this.beginningIndex = beg;
			this.endingIndex = end;
			this.usableSize = sz;
		}
	}
	
	class InsufficientMemoryException extends Exception
	{
		private static final long serialVersionUID = 1L;
	}
}
