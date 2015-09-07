package edu.emory.clir.clearnlp.extraction.attribute.ngram;

import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import edu.emory.clir.clearnlp.extraction.attribute.ngram.smoothing.ISmoothing;
import edu.emory.mathcs.nlp.common.collection.tuple.ObjectDoublePair;
import edu.emory.mathcs.nlp.common.util.FastUtils;
import edu.emory.mathcs.nlp.common.util.MathUtils;

public class Unigram<T> extends AbstractNGram<T>{
	private static final long serialVersionUID = -6515135404806585746L;

	private Object2IntMap<T> m_count;
	private Object2DoubleMap<T> m_liklihood;
	
	public Unigram() {
		super(1, null);
		m_count = new Object2IntOpenHashMap<>();
	}
	
	public Unigram(ISmoothing smoothing) {
		super(1, smoothing);
		m_count = new Object2IntOpenHashMap<>();
	}
	
	public int get(T key){
		return m_count.get(key);
	}
	
	@Override
	public ObjectDoublePair<T> getBest(){
		 return (best != null)? new ObjectDoublePair<T>(best, MathUtils.divide(get(best), i_totalCount)) : null;
	}

	@Override
	public int getKeyCount() {
		return m_count.size();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	protected void addAux(int inc, T... words) {
		T key = words[words.length - 1];
		int c = FastUtils.increment(m_count, key, inc);
		if (best == null || get(best) < c) best = key;
		i_totalCount += inc;
	}

	@Override
	public void estimateLikelihood() {
		// TODO Auto-generated method stub
		
	}

	@Override
	@SuppressWarnings("unchecked")
	protected double getLikelihoodAux(T... words) {
		return m_liklihood.get(words[words.length-1]);
	}
}
