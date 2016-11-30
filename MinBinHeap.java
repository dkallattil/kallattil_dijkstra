package kallattil_dijkstra;

public class MinBinHeap implements Heap_Interface {
	
  private EntryPair[] array; //load this array
  private int size;
  private static final int arraySize = 10000; //Everything in the array will initially 
                                              //be null. 

  public MinBinHeap() {
    this.array = new EntryPair[arraySize];
    array[0] = new EntryPair(null, -100000); //0th will be unused for simplicity 
                                             //of child/parent computations...
  }
    
	@Override
	public EntryPair[] getHeap() { 
	    return this.array;
	}

	@Override
	public void insert(EntryPair entry) {
		// create hole at next available location, if x can be placed at the hole
		// then it is, else, slide element at hole's node into hole and try to 
		// place x there. keep going until you insert at the right spot. 
		
		if(size == 0) { 
			array[1] = entry;
			size++;
			return;
		}
		
		array[size+1] = entry;
		size++;
		int index = size;
		
		while(index > 0 && array[index].getPriority() <= array[index/2].getPriority()) { 
				EntryPair swap = array[index];
				array[index] = array[index/2];
				array[index/2] = swap;
				index = index/2;
		}
		
	}
	
	@Override
	public void delMin() {
		//create a hole at the root, detach the last element in the array, 
		// and put the smallest child element in the hole at the root, and keep 
		// going until you have a hole in a leaf with no children. Then you put 
		// the detached element in that hole.
		
		if(size == 0) { 
			return;
		} 
		int index = 1; 
		array[1] = array[size];
		percolateDown(index);
		size--;

	}
	
	@Override
	public EntryPair getMin() {
		if(size == 0) { 
			return null;
		} else { 
			return array[1];
		}
	}
	
	@Override
	public int size() {
		return size;
	}
	
	@Override
	public void build(EntryPair[] entries) {
		// you want to build the heap like he said in class, where you start halfway 
		// and compare/swap over and over again. 
		
		for(int i = 0; i < entries.length; i++) { 
			array[i+1] = entries[i];
		}
		
		size = entries.length; 
		int index = size/2;
		while(index > 0) { 
			percolateDown(index);
			index--;
		}
	
	}
	
	private void percolateDown(int i) { 
		EntryPair temp; 
		while(i*2 <= size) { 
			if(i*2 == size) { 
				if(array[i*2].getPriority() < array[i].getPriority()) { 
					temp = array[i*2];
					array[i*2] = array[i];
					array[i] = temp;
				} else { 
					i = i*2;
				}
			} else { 
					if(array[i*2 +1].getPriority() < array[i].getPriority() || array[i*2].getPriority() < array[i].getPriority()) { 
						if(array[i*2+1].getPriority() < array[i*2].getPriority()) { 
							temp = array[i*2+1];
							array[i*2+1] = array[i];
							array[i] = temp;
							i = i*2 + 1;
						} else { 
							temp = array[i*2];
							array[i*2] = array[i];
							array[i] = temp;
							i = i*2;
						}
					
					} else { 
						i = i*2;
					}
				}
			
		}
				
				
		}
	
	
	
}