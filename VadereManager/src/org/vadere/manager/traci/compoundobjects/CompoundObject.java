package org.vadere.manager.traci.compoundobjects;

import org.apache.commons.lang3.tuple.Pair;
import org.vadere.manager.TraCIException;
import org.vadere.manager.traci.TraCIDataType;

import java.util.Iterator;

public class CompoundObject {

	private TraCIDataType[] type;
	private Object[] data;
	private int cur;

	public CompoundObject(int noElements) {
		this.type = new TraCIDataType[noElements];
		this.data = new Object[noElements];
		this.cur = 0;
	}

	public String types(){
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (TraCIDataType i : this.type) {
			sb.append(i.name()).append(", ");
		}
		sb.delete(sb.length()-2, sb.length());
		sb.append("]");
		return sb.toString();
	}

	public int size(){
		return data.length;
	}

	public CompoundObject add(int type, Object data){
		return  add(TraCIDataType.fromId(type), data);
	}

	public CompoundObject add(TraCIDataType type, Object data){
		if (cur > this.data.length)
			throw new TraCIException("CompoundObject already full. Received " + types());

		this.type[cur] = type;
		this.data[cur] = data;

		cur++;
		return this;
	}

	public Object getData(int index, TraCIDataType type){
		if (index > this.data.length)
			throw new TraCIException("Cannot access data with index %d", index);
		if (!this.type[index].equals(type))
			throw new TraCIException("Type mismatch of CompoundObject element %s != %s  at index %d", this.type[index].name(), type.name(), index);
		return this.data[index];
	}

	public Object getData(int index){
		if (index > this.data.length)
			throw new TraCIException("Cannot access data with index %d", index);
		return this.data[index];
	}

	public Iterator<Pair<TraCIDataType, Object>> itemIterator(){
		return new Iter(this);
	}



	private class Iter implements Iterator<Pair<TraCIDataType, Object>>{

		private final CompoundObject compoundObject;
		private int curr;

		Iter(CompoundObject compoundObject){
			this.compoundObject = compoundObject;
			this.curr = 0;
		}

		@Override
		public boolean hasNext() {
			return curr < compoundObject.size();
		}

		@Override
		public Pair<TraCIDataType, Object> next() {
			Pair<TraCIDataType, Object> p = Pair.of(compoundObject.type[curr], compoundObject.data[curr]);
			curr++;
			return p;
		}
	}

}
