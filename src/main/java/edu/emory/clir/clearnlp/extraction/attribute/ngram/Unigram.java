package edu.emory.clir.clearnlp.extraction.attribute.ngram;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import edu.emory.clir.clearnlp.extraction.attribute.ngram.smoothing.ISmoothing;
import edu.emory.mathcs.nlp.common.collection.tuple.ObjectDoublePair;
import edu.emory.mathcs.nlp.common.util.FastUtils;
import edu.emory.mathcs.nlp.common.util.MathUtils;

public class Unigram extends AbstractSuperNGram{
	private static final long serialVersionUID = -6515135404806585746L;

	private String best;
	private Object2IntMap<String> m_count;
	
	public Unigram() {
		super(1, null);
		m_count = new Object2IntOpenHashMap<>();
	}
	
	public Unigram(ISmoothing smoothing) {
		super(1, smoothing);
		m_count = new Object2IntOpenHashMap<>();
	}
	
	public int get(String key){
		return m_count.get(key);
	}
	
	public ObjectDoublePair<String> getBest(){
		 return (best != null)? new ObjectDoublePair<String>(best, MathUtils.divide(get(best), i_totalCount)) : null;
	}

	@Override
	protected void addAux(int inc, String... words) {
		int c = FastUtils.increment(m_count, words[0], inc);
		if (best == null || get(best) < c) best = words[0];
		i_totalCount += inc;
	}

	@Override
	public void estimateLikelihood() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getLikelihood(String... words) {
		// TODO Auto-generated method stub
		return 0;
	}

}
