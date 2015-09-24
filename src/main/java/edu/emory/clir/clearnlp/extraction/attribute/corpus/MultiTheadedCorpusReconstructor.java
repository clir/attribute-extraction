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
package edu.emory.clir.clearnlp.extraction.attribute.corpus;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.emory.clir.clearnlp.util.DSUtils;

/**
 * @author 	Yu-Hsin(Henry) Chen ({@code yu-hsin.chen@emory.edu})
 * @version	1.0
 * @since 	Sep 24, 2015
 */
public class MultiTheadedCorpusReconstructor extends AbstractCorpusRecontructor{
	
	public static void main(String[] args){
		if(args.length != 5){
			throw new IllegalArgumentException("Invalid format: ThreadCount CorpusType(RAW, TSV) InputDir OutputDir Extension");
		}
		
		EntityTokenCorpusReconstructor core = new EntityTokenCorpusReconstructor(CorpusType.valueOf(args[1]), args[2], args[3], DSUtils.toHashSet(args[4]));
		MultiTheadedCorpusReconstructor constructor = new MultiTheadedCorpusReconstructor(Integer.parseInt(args[0]), core);
		
		constructor.reconstruct();
	}
	
	private int threadCount;
	private AbstractCorpusRecontructor constructor;
	
	public MultiTheadedCorpusReconstructor(int threadCount, AbstractCorpusRecontructor constructor){
		super(constructor.getCorpusType(), constructor.getInputDir(), constructor.getOutputDir());
		this.threadCount = threadCount;
		this.constructor = constructor;
	}

	@Override
	public void reconstruct(List<String> filePaths) {
		int i, l_size = filePaths.size(), b_size = l_size/threadCount;
		
		AbstractCorpusRecontructor constructor_new;
		ExecutorService executor = Executors.newFixedThreadPool(threadCount);
		for(i = 0; i < l_size; i+=b_size){
			if(i+b_size >= l_size){
				constructor_new = constructor.clone();
				constructor_new.setFilePaths(filePaths.subList(i, i+b_size));
				executor.submit(constructor_new);
			}
			else{
				constructor.setFilePaths(filePaths.subList(i, l_size));
				executor.submit(constructor);
			}
		}
		
	}

	@Override
	public AbstractCorpusRecontructor clone() {
		return null;
	}
}