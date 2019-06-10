package org.beetl.core;

import java.util.Enumeration;
import java.util.Iterator;

public class IteratorLoopStatus implements  ILoopStatus  {
	Iterator it;
	int size = -1;
	int index = 0;
	boolean hasData = false;

	public  IteratorLoopStatus(Iterator it){
		this.it = it;
	}



	public final boolean hasNext()
	{
		return this.it.hasNext();
	}

	public final Object next()
	{
		index++;
		if (!hasData)
			hasData = true;
		return this.it.next();
	}

	public final int getIndex()
	{
		return this.index;
	}

	public final int getDataIndex(){
		return this.index-1;
	}

	public final boolean isFirst()
	{
		return index == 1;
	}

	public final boolean isLast()
	{
		return  !this.it.hasNext();
	}

	public final boolean isEven()
	{

		return index % 2 == 0;
	}

	public final boolean isOdd()
	{
		return index % 2 == 1;
	}

	public final boolean hasSize()
	{
		return false;
	}

	public int getSize()
	{
		throw new RuntimeException("集合长度未知,请勿使用size");
	}

	public final boolean hasData()
	{
		return hasData;
	}
}
