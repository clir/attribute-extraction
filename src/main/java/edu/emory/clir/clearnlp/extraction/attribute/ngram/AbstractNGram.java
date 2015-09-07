/**
 * Copyright 2015, Emory University
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.emory.clir.clearnlp.extraction.attribute.ngram;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import edu.emory.clir.clearnlp.extraction.attribute.ngram.smoothing.AbstractSmoothing;
import edu.emory.mathcs.nlp.common.collection.tuple.ObjectDoublePair;
import edu.emory.mathcs.nlp.common.collection.tuple.ObjectIntPair;
import edu.emory.mathcs.nlp.common.util.FastUtils;
import edu.emory.mathcs.nlp.common.util.MathUtils;

/**
 * @author 	Yu-Hsin(Henry) Chen ({@code yu-hsin.chen@emory.edu})
 * @version	1.0
 * @since 	Sep 7, 2015
 */
public abstract class AbstractNGram<T> implements Serializable{
	private static final long serialVersionUID = 7598468521841113603L;
	
	protected T best;
	protected int i_totalCount;
	protected Object2IntMap<T> m_count;
	protected AbstractSmoothing smoothing;
	
	public AbstractNGram() {
		init(null);
	}
	
	public AbstractNGram(AbstractSmoothing smoothing){
		init(smoothing);
	}
	
	private void init(AbstractSmoothing smoothing){
		this.smoothing = smoothing;
		m_count = new Object2IntOpenHashMap<>();	
	}
	
	public void add(T key){
		add(key, 1);
	}
	
	public void add(T key, int inc){
		int c = FastUtils.increment(m_count, key, inc);
		if (best == null || get(best) < c) best = key;
		i_totalCount += inc;
	}
	
	public int get(T key){
		return m_count.get(key);
	}
	
	public ObjectDoublePair<T> getBest(){
		 return (best != null) ? new ObjectDoublePair<T>(best, MathUtils.divide(get(best), i_totalCount)) : null;
	}
	
	public boolean contains(T key){
		return m_count.containsKey(key);
	}
	
	public double getProbability(T key){
		return MathUtils.divide(get(key), i_totalCount);
	}
	
	public List<ObjectIntPair<T>> toList(int cutoff){
		List<ObjectIntPair<T>> list = new ArrayList<>();
		
		for (Entry<T,Integer> p : m_count.entrySet())
		{
			if (p.getValue() > cutoff)
				list.add(new ObjectIntPair<>(p.getKey(), p.getValue()));
		}
		
		return list;
	}
	
	public List<ObjectDoublePair<T>> toList(double threshold){
		List<ObjectDoublePair<T>> list = new ArrayList<>();
		double d;
		
		for (Entry<T,Integer> p : m_count.entrySet())
		{
			d = MathUtils.divide(p.getValue(), i_totalCount);
			if (d > threshold) list.add(new ObjectDoublePair<T>(p.getKey(), d));
		}
		
		return list;
	}
	
	public Set<T> keySet(){
		return keySet(0);
	}
	
	/** @return a set of keys whose values are greater than the specific cutoff. */
	public Set<T> keySet(int cutoff){
		Set<T> set = new HashSet<>();
		
		for (Entry<T,Integer> p : m_count.entrySet())
		{
			if (p.getValue() > cutoff) set.add(p.getKey());
		}
		
		return set;
	}
	
	public Set<T> keySet(double threshold){
		Set<T> set = new HashSet<>();
		double d;
		
		for (Entry<T,Integer> p : m_count.entrySet())
		{
			d = MathUtils.divide(p.getValue(), i_totalCount);
			if (d > threshold) set.add(p.getKey());
		}
		
		return set;
	}
}
