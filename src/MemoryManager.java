import java.util.ArrayList;
import java.util.Arrays;

public class MemoryManager{

	int mem_size;
	int[] mem;
	String strat;
	
	private ArrayList<Integer> indices;
	private int currentIndex; // for NextFit
	public int holesCounted1;
	public int holesCounted2;
	
	public MemoryManager(int mem_size, String strat)
	{
		this.mem_size = mem_size;
		this.strat = strat;
		this.holesCounted1 = 0;
		this.holesCounted2 = 0;
		this.mm_init();
		this.indices = new ArrayList<Integer>();
		for(int i = 0; i < mem_size; i++)
			indices.add(i);	
	}
	
//	The three main memory manager functions
	public void mm_init()
	{
		this.mem = new int[mem_size];
		Arrays.fill(mem, 0);
		mem[0] = -(mem_size - 4);
		mem[mem_size -1] = mem[0];
		mem[1] = -1;
		mem[mem_size - 1] = mem_size;
		this.currentIndex = 0;
		this.holesCounted1 = 0;
		this.holesCounted2 = 0;
	}
	
//	returns index of first usable word or error if insufficient memory
	public int mm_request(int n) throws InsufficientMemoryException
	{
		int beg = 0;
		int sz = n;
		int end = 0;
		int returnResult = 0;
		boolean insuffMem = true;

		if(strat.equals("firstfit"))
		{
			for(int i = 0; i < mem_size; i++)
			{
				if(-mem[i] == n)
				{
					insuffMem = false;
					this.holesCounted1++;
					
					mem[i] = n;
					mem[i+n+1] = n;
					int predecessor = mem[i+1];
					mem[i+1] = 0;
					int successor = mem[i+2];
					mem[i+2] = 0;
					if(predecessor >= 0)
						mem[predecessor+2] = successor;
					
					if(successor < mem_size)
						mem[successor+1] = predecessor;
				}
				else if(-mem[i] > n)
				{
					insuffMem = false;
					this.holesCounted1++;
					int holeSize = -mem[i];
					int holeBegin = i + n + 2;
					int holeEnd = i + holeSize + 1;
					int predecessor = mem[i+1];
					mem[i+1] = 0;
					int successor = mem[i+2];
					mem[i+2] = 0;
					if(predecessor >= 0)
						mem[predecessor+2] = successor;
					if(successor < mem_size)
						mem[successor+1] = predecessor;
					mem[i] = n;
					mem[i+n+1] = n;	
					mem[holeBegin] = -(holeEnd-holeBegin-1);
					mem[holeEnd] = mem[holeBegin];
					if(-mem[holeBegin] >= 3 && holeEnd+3 < mem_size)	// if the remaining hole is bigger than 3
					{
						mem[holeBegin+1] = predecessor;
						mem[holeBegin+2] = successor;
						mem[holeEnd] = mem[holeBegin];
					}
					else						// else make the entire block allocated
					{
						mem[i] = holeSize;
						mem[i + holeSize + 1] = holeSize;
						for(int j = 0; j < holeSize; j++)
							mem[i+j+1] = 0;
					}
					returnResult = i+1;
				}
				else
				{
					if(mem[i] < 0)
					{
						this.holesCounted1++;
						if(i+2 < mem_size-4 && mem[i+2] > 0)
							i = mem[i+2];		// nextHole
					}
				}
			}
			if(insuffMem)
				throw new InsufficientMemoryException();
			return returnResult;
		}
		else if(strat.equals("nextfit"))
		{
			int copyIndex = currentIndex;
			while(copyIndex != currentIndex + 1)
			{
				if(-mem[currentIndex] == n)
				{
					insuffMem = false;
					this.holesCounted1++;
					
					mem[currentIndex] = n;
					mem[currentIndex+n+1] = n;
					int predecessor = mem[currentIndex+1];
					mem[currentIndex+1] = 0;
					int successor = mem[currentIndex+2];
					mem[currentIndex+2] = 0;
					if(predecessor >= 0)
						mem[predecessor+2] = successor;
					
					if(successor < mem_size)
						mem[successor+1] = predecessor;
				}
				else if(-mem[currentIndex] > n)
				{
					insuffMem = false;
					this.holesCounted1++;
					int holeSize = -mem[currentIndex];
					int holeBegin = currentIndex + n + 2;
					int holeEnd = currentIndex + holeSize + 1;
					int predecessor = mem[currentIndex+1];
					mem[currentIndex+1] = 0;
					int successor = mem[currentIndex+2];
					mem[currentIndex+2] = 0;
					if(predecessor >= 0)
						mem[predecessor+2] = successor;
					if(successor < mem_size)
						mem[successor+1] = predecessor;
					mem[currentIndex] = n;
					mem[currentIndex+n+1] = n;	
					mem[holeBegin] = -(holeEnd-holeBegin-1);
					mem[holeEnd] = mem[holeBegin];
					if(-mem[holeBegin] >= 3 && holeEnd+3 < mem_size)	// if the remaining hole is bigger than 3
					{
						mem[holeBegin+1] = predecessor;
						mem[holeBegin+2] = successor;
						mem[holeEnd] = mem[holeBegin];
					}
					else						// else make the entire block allocated
					{
						mem[currentIndex] = holeSize;
						mem[currentIndex + holeSize + 1] = holeSize;
						for(int j = 0; j < holeSize; j++)
							mem[currentIndex+j+1] = 0;
					}
					currentIndex = successor;
					returnResult = currentIndex+1;
				}
				else
				{
					if(mem[currentIndex] < 0)
					{
						this.holesCounted1++;
						if(currentIndex+2 < mem_size-4 && mem[currentIndex+2] > 0)
							currentIndex = mem[currentIndex+2];		// nextHole
					}
					currentIndex++;
					if(currentIndex >= mem_size)
						currentIndex = 0;
				}
			}
			if(insuffMem)
				throw new InsufficientMemoryException();
			return returnResult;
		}
		else
			return -1;
	}
	
	public void mm_release(int begIndex) throws Exception
	{
		boolean leftHole = false;
		boolean rightHole = false;
		int sizeRemoved = 0;
		
		
			
	}
	
	public void setFirstFit()
	{
		this.strat = "firstfit";
	}
	
	public void setNextFit()
	{
		this.strat = "nextfit";
	}
	
	
	public double recordMemoryUtilization()
	{
		int usedSize = 0;
//		for(int i = 0 : mem)
//		{
//			if(i.usableSize > 0)
//			{
//				usedSize += i.usableSize;
//			}
//		}
		double temp = (double) usedSize / (double) mem_size;
	//	System.out.println(temp);
		return temp;
	}
	
	public class InsufficientMemoryException extends Exception
	{
		private static final long serialVersionUID = 1L;
	}
}
