package edu.stanford.nlp.stanford_corenlp;

import java.util.Properties;
import java.util.Scanner;

import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;


public class MainClass {
	public static void main(String[] args) {
		Properties props = new Properties();
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		props.setProperty("annotators", "tokenize, ssplit,pos,parse,dcoref");
		System.out.println("Ask a question");
		Scanner in = new Scanner(System.in);
		String text = in.nextLine();
		Annotation document = new Annotation(text);
		pipeline.annotate(document);
		Tree tree = null;
		String queryString = "";
		java.util.List<CoreMap> sentences = document
				.get(SentencesAnnotation.class);

		for (CoreMap sentence : sentences) {

			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
				// this is the text of the token
				String word = token.get(TextAnnotation.class);
//				System.out.println("The word is" + word);
				// this is the POS tag of the token
				String pos = token.get(PartOfSpeechAnnotation.class);
//				System.out.println("The pos is" + pos);

				if (pos.startsWith("NN")) {
					queryString = queryString + " " + word;
				}
				String ne = token.get(NamedEntityTagAnnotation.class);
//				System.out.println("The entity is" + ne);
			}

			tree = sentence.get(TreeAnnotation.class);
			SemanticGraph dependencies = sentence
					.get(CollapsedCCProcessedDependenciesAnnotation.class);

		}

		// This is the coreference link graph
		// Each chain stores a set of mentions that link to each other,
		// along with a method for getting the most representative mention
		// Both sentence and token offsets start at 1!
		/*
		 * Map<Integer, CorefChain> graph =
		 * document.get(CorefChainAnnotation.class);
		 * 
		 * TreebankLanguagePack tlp = new PennTreebankLanguagePack();
		 * GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
		 * GrammaticalStructure gs = gsf.newGrammaticalStructure(tree);
		 * Collection<TypedDependency> tdl = gs.typedDependenciesCollapsed();
		 * 
		 * System.out.println("typedDependencies: "+tdl);
		 */
		System.out.println("The query string is "+queryString);
		
		GetCategory categories = new GetCategory();
		categories.getCategory(queryString);
	}
}
