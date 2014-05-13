import java.util.Arrays;
import java.util.LinkedList;

public class MemoryManager{

	int mem_size;
	int[] mem;
	String strat;
	
	private int currentIndex;
	public int holesCounted;
	
	public LinkedList<MemoryBlock> list;
	
	public MemoryManager(int mem_size, String strat)
	{
		this.mem_size = mem_size;
		this.strat = strat;
		this.list = new LinkedList<MemoryBlock>();
		this.holesCounted = 0;
		
		this.list.add(mm_init());
		
		
	}
	
//	The three main memory manager functions
	public MemoryBlock mm_init()
	{
		this.currentIndex = 0;
		this.holesCounted = 0;
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
		int beg = 0;
		int sz = n;
		int end = 0;
		boolean insuffMem = true;
	
		MemoryBlock blockToRemove = null;
		MemoryBlock spaceBlock = null;
		
		if(strat.equals("firstfit"))
		{
			for(MemoryBlock i : list)
			{
				if (i.usableSize < 0)		// if there is a hole, count it
					holesCounted++;
				if ( -(i.usableSize) >= n ) // available size >= n
				{
					insuffMem = false;
					spaceBlock = i;
					
					// make a block, allocate, change array
					beg = i.beginningIndex;
					sz = n;
					end = i.beginningIndex + 3 + n;
					
					if( (i.endingIndex - end) >= 5 ) // if there is still a hole
					{
						i.usableSize = -(i.endingIndex - (end + 4));
						i.beginningIndex = end + 1;
						spaceBlock = i;
					}
					else							// else if there is no hole left
					{
						blockToRemove = i;
					}
					break;
				}
			}
			if(insuffMem)
				throw new InsufficientMemoryException();
			
			MemoryBlock addedBlock = new MemoryBlock(beg, end, sz);
			list.add(list.indexOf(spaceBlock), addedBlock);
			if (blockToRemove != null) // if there is a block to remove from the linked list, remove it
			{
				list.remove(blockToRemove);
			}
			return beg + 3;
			
		}
		else if(strat.equals("nextfit"))
		{
			boolean hasNotLooped = true;
			int temp = currentIndex;
			do
			{
				if(list.get(currentIndex).usableSize < 0) // if there is a hole, count it
				{
					holesCounted++;
					if( -(list.get(currentIndex).usableSize) >= n) // if there is a hole big enough
					{
						insuffMem = false;
						
						beg = list.get(currentIndex).beginningIndex;
						sz = n;
						end = list.get(currentIndex).endingIndex + n + 3;
						
						if( (list.get(currentIndex).endingIndex - end) >= 5 ) // if there is still a hole
						{
							list.get(currentIndex).usableSize = -(list.get(currentIndex).endingIndex - (end + 4));
							list.get(currentIndex).beginningIndex = end + 1;
							spaceBlock = list.get(currentIndex);
						}
						else							// else if there is no hole left
						{
							blockToRemove = list.get(currentIndex);
						}
						break;
					}
					
				}
				
				currentIndex++;
				if(currentIndex == list.size())
					currentIndex = 0;
				if(currentIndex == temp)
					hasNotLooped = false;
			} while (currentIndex < list.size() && hasNotLooped);
			
			if(insuffMem)
				throw new InsufficientMemoryException();
			
			MemoryBlock addedBlock = new MemoryBlock(beg, end, sz);
			list.add(list.indexOf(spaceBlock), addedBlock);
			if (blockToRemove != null) // if there is a block to remove from the linked list, remove it
			{
				list.remove(blockToRemove);
			}
		
			return beg + 3;
		}
		else
			return -1;
	}
	
	public void mm_release(int begIndex)
	{
		MemoryBlock blockToRemove = null;
		boolean shouldRemoveBlock = true;
		boolean leftHole = false;
		boolean rightHole = false;
		int sizeRemoved = 0;
		for(MemoryBlock i : list)
		{
			if(i.beginningIndex == begIndex)
			{
				blockToRemove = i;
				break;
			}
		}
		
		MemoryBlock blockToEdit = null;
		int index = 0;
		int indexLeft = 0;
		if(blockToRemove==null)
		{
			System.out.println("Cannot find a block with index of " + begIndex);
		}
		else
		{
		if(blockToRemove.beginningIndex-1 == -1) // if removed block is left-bound
		{
			shouldRemoveBlock = false;
			list.get(list.indexOf(blockToRemove)).usableSize = -list.get(list.indexOf(blockToRemove)).usableSize;
		}
		else
		{
			// check left side of the new hole
			for(MemoryBlock i : list)
			{
				if (blockToRemove.beginningIndex-1 >= 0				// if the left side is an adjacent hole
						&& blockToRemove.beginningIndex-1 == i.endingIndex
						&& i.usableSize < 0)
				{	
					leftHole = true;
					sizeRemoved = Math.abs(blockToRemove.usableSize);
					blockToEdit = i;
					index = list.indexOf(i);
					indexLeft = index;
					break;
				}	
			}
			// set new size of left hole
			blockToEdit.usableSize = -(-blockToEdit.usableSize + 4 + blockToRemove.usableSize);
			
			// set new endIndex of left hole
			blockToEdit.endingIndex = 4 + blockToRemove.usableSize;
			
			list.set(index, blockToEdit); // replace with a new left hole on linked list
		}
		
		if(blockToRemove.endingIndex+1 == mem_size) // if removed block is right-bound
		{
			shouldRemoveBlock = false;
			int size = list.get(list.indexOf(blockToRemove)).usableSize;
			if(size > 0)
				list.get(list.indexOf(blockToRemove)).usableSize = -size;
		}
		else
		{
			// check right side of the new hole
			for(MemoryBlock i : list)
			{
				if(blockToRemove.endingIndex+1 < mem_size
						&& blockToRemove.endingIndex+1 == i.beginningIndex
						&& i.usableSize < 0)
				{
					rightHole = true;
					blockToEdit = i;
					index = list.indexOf(i);
					break;
				}
			}
			// set new size of right hole
			blockToEdit.usableSize = -(-blockToEdit.usableSize + 4 + blockToRemove.usableSize);
			
			// set new beginningIndex
			blockToEdit.beginningIndex = blockToRemove.beginningIndex;
		
			list.set(index, blockToEdit);
		}
		
		if(shouldRemoveBlock)
		{
			list.remove(blockToRemove);
		}
		
		if(leftHole && rightHole)
		{
			int rightHolesSize = Math.abs(list.get(index).usableSize);
			list.get(indexLeft).endingIndex = 4 + sizeRemoved + 4 + rightHolesSize; 
			list.get(indexLeft).usableSize = -(1 + 4 + sizeRemoved + 4 + rightHolesSize);
			list.remove(index);
		}
		}
	}
	
	public void setFirstFit()
	{
		this.strat = "firstfit";
	}
	
	public void setNextFit()
	{
		this.strat = "nextfit";
	}
	
	class MemoryBlock
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
	
//	public int getHolesCounted()
//	{
//		return holesCounted;
//	}
	
	public double recordMemoryUtilization()
	{
		int usedSize = 0;
		for(MemoryBlock i : list)
		{
			if(i.usableSize > 0)
			{
				usedSize += i.usableSize;
			}
		}
		
		return usedSize / mem_size;
	}
	
	public class InsufficientMemoryException extends Exception
	{
		private static final long serialVersionUID = 1L;
	}
}
