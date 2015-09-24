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

import java.util.Set;

import org.junit.Test;

import edu.emory.clir.clearnlp.util.DSUtils;

/**
 * @author 	Yu-Hsin(Henry) Chen ({@code yu-hsin.chen@emory.edu})
 * @version	1.0
 * @since 	Sep 23, 2015
 */
public class EntityTokenCorpusReconstructorTest{
	public final Set<String> EXT = DSUtils.toHashSet(".txt");
	public final String INPUT_DIR = "/Users/HenryChen/Documents/clearnlp-qa/corpus/NYT";
	public final String OUPUT_DIR = "/Users/HenryChen/Documents/clearnlp-qa/corpus/NYT_OUT";
	public final Set<String> NERLabels = DSUtils.toHashSet("PERSON", "ORG", "LOC", "GPE");
	
	@Test
	public void testReconstructor(){
		EntityTokenCorpusReconstructor constructor 
			= new EntityTokenCorpusReconstructor(CorpusType.RAW, INPUT_DIR, OUPUT_DIR, EXT, NERLabels);
		
		constructor.reconstruct();
	}
}
