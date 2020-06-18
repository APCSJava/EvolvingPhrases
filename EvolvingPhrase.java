
public class EvolvingPhrase {

	private char[] dna;
	private static final String allowedGenes = " abcdefghijklmnopqrstuvwxyz";

	/**
	 * Construct a new phrase of the indicated length. Fill the DNA array with chars
	 * drawn randomly from the allowed genes provided by geneString
	 * 
	 * @param n
	 */
	public EvolvingPhrase(int n) {
		dna = new char[n];
		randomizeDNA();
	}

	/**
	 * Construct a new phrase having as DNA the char sequence of the supplied
	 * string.
	 * 
	 * @param s
	 */
	public EvolvingPhrase(String s) {
		dna = s.toCharArray();
	}

	/**
	 * Assign each element of the DNA array a char drawn at random from the genes
	 * allowed by geneString.
	 */
	private void randomizeDNA() {
		for (int i = 0; i < dna.length; i++) {
			dna[i] = allowedGenes.charAt(
					(int) (Math.random() * allowedGenes.length()));
		}
	}

	public String toString() {
		return new String(dna);
	}

	/**
	 * Count how many chars in the DNA of this phrase match those in the DNA of the
	 * comparison phrase.
	 * 
	 * @param comparison
	 *            a reference collection of DNA
	 * @return the number of chars in common for each location in the DNA array
	 */
	public int countNumMatches(EvolvingPhrase comparison) {
		int numMatches = 0;
		for (int i = 0; i < dna.length; i++) {
			if (dna[i] == comparison.dna[i]) {
				numMatches++;
			}
		}
		return numMatches*10;
	}

	/**
	 * For each char in the DNA, generate a random number; if the random number
	 * falls below the mutation factor, swap the char at that index with a char
	 * drawn at random from the geneString.
	 * 
	 * @param mutationFactor
	 *            a double in the range [0.0, 1.0)
	 */
	public void potentiallyMutate(double mutationFactor) {
		for (int i = 0; i < dna.length; i++) {
			if (Math.random() < mutationFactor) {
				int randomIndex = (int) (Math.random()
						* allowedGenes.length());
				dna[i] = allowedGenes.charAt(randomIndex);
			}
		}
	}

	/**
	 * Construct and return a new evolving phrase. Each char in the DNA sequence of
	 * the offspring should match either the corresponding char of this phrase or of
	 * the partner phrase, determined randomly.
	 * 
	 * @param partner
	 *            a phrase acting as a second potential source of DNA
	 * @return a child with DNA sampled from both parents
	 */
	public EvolvingPhrase produceOffspring(EvolvingPhrase partner) {
		String newDNA = "";
		for (int i = 0; i < dna.length; i++) {
			if (Math.random() < 0.5) {
				newDNA += this.dna[i];
			} else {
				newDNA += partner.dna[i];
			}
		}
		return new EvolvingPhrase(newDNA);
	}
}
