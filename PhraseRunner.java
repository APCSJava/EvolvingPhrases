
public class PhraseRunner {

	private static EvolvingPhrase targetPhrase;
	private static EvolvingPhrase[] population;
	private static int populationFitness;
	private static final int POP_SIZE = 300;
	private static final int NUM_GENERATIONS = 2000;

	public static void main(String[] args) {
		String target = "to be or not to be that is the question";
		targetPhrase = new EvolvingPhrase(target);

		population = new EvolvingPhrase[POP_SIZE];
		for (int i = 0; i < population.length; i++) {
			population[i] = new EvolvingPhrase(target.length());
		}
		// initial population
		System.out.println("Initial population");
		for (EvolvingPhrase ep : population) {
			System.out.println(ep);
		}
		populationFitness = getPopulationFitness();
		//System.out.println("Total fitness: "+populationFitness);

		for (int gen = 0; gen < NUM_GENERATIONS; gen++) {
			createNextGeneration();
			//System.out.println("Generation "+gen+"\tTotal fitness: "+populationFitness);
		}

		System.out.println("Final population");
		int correctCount = 0;
		for (EvolvingPhrase ep : population) {
			System.out.println(ep);
			if (ep.toString().equals(target))
				correctCount++;
		}
		System.out.println("Optimal member count " + correctCount);
	}

	/**
	 * Sums the number of correct DNA matches across the entire population.
	 * 
	 * @return
	 */
	private static int getPopulationFitness() {
		int sum = 0;
		for (EvolvingPhrase ep : population) {
			sum += ep.countNumMatches(targetPhrase);
		}
		return sum;
	}

	private static void createNextGeneration() {
		populationFitness = getPopulationFitness();
		EvolvingPhrase[] nextPop = new EvolvingPhrase[POP_SIZE];
		for (int i = 0; i < POP_SIZE; i++) {
			EvolvingPhrase a = getRandomPartner();
			EvolvingPhrase b = getRandomPartner();
			nextPop[i] = a.produceOffspring(b);
			nextPop[i].potentiallyMutate(.0005);
		}
		population = nextPop;
	}
	
	private static void createNextGeneration2() {
		EvolvingPhrase[] pool = getMatingPool();
		EvolvingPhrase[] nextPop = new EvolvingPhrase[POP_SIZE];
		for (int i = 0; i<POP_SIZE; i++) {
			EvolvingPhrase a = getRandomPartner2(pool);
			EvolvingPhrase b = getRandomPartner2(pool);
			nextPop[i] = a.produceOffspring(b);
			nextPop[i].potentiallyMutate(.0005);
		}
	}

	private static EvolvingPhrase getRandomPartner2(
			EvolvingPhrase[] pool) {
		int randIndex = (int) (Math.random()*pool.length);
		return pool[randIndex];
	}

	/**
	 * Select at random a single member of the current population. If the member is
	 * "fit enough" to mate, return its reference. If not, try again.
	 * 
	 * @param population
	 * @return
	 */
	private static EvolvingPhrase getRandomPartner() {
		while (true) {
			int selectedIndex = (int) (Math.random() * population.length);
			double qualifying = Math.random();
			if (qualifying < population[selectedIndex].countNumMatches(targetPhrase)/(double) populationFitness) {
				return population[selectedIndex];
			}
		}
	}
	
	/**
	 * Creates a pool where each member occurs the number of times equal to its fitness
	 * @return
	 */
	private static EvolvingPhrase[] getMatingPool() {
		int totalFitness = getPopulationFitness();
		EvolvingPhrase[] pool = new EvolvingPhrase[totalFitness];
		int index = 0;
		for (EvolvingPhrase ep: population) {
			int numOccurrences = ep.countNumMatches(targetPhrase);
			for (int i = 0; i<numOccurrences; i++) {
				pool[index] = ep;
				index++;
			}
		}
		return pool;
	}

}
