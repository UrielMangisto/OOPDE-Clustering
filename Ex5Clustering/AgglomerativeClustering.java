import java.util.*;
import java.io.IOException;
import java.util.Set;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.stream.Collectors;
import java.util.HashSet;


/**
 * This class implements the Agglomerative Clustering algorithm.
 * It clusters data points by iteratively merging the closest clusters until a specified threshold is reached.
 *
 * @param <T> the type of elements to be clustered, must implement Clusterable interface
 */
public class AgglomerativeClustering<T extends Clusterable<T>> implements Clustering<T> {
	double threshold;

	/**
	 * Constructor to initialize the threshold for merging clusters.
	 *
	 * @param threshold the maximum distance between clusters to merge them
	 */
	public AgglomerativeClustering(double threshold) {
		this.threshold = threshold;
	}

	/**
	 * Clusters the given set of items using the Agglomerative Clustering algorithm.
	 *
	 * @param items the set of items to cluster
	 * @return a set of clusters, each represented by a set of items
	 */
	public Set<Set<T>> clusterSet(Set<T> items) {

		// Initialize each item as its own cluster
		Set<Set<T>> clusters = items.stream()
				.map(Collections::singleton)
				.collect(Collectors.toSet());

		// Continue merging clusters until only one cluster is left or no more merges are possible
		while (clusters.size() > 1) {
			// Find the closest pair of clusters
			Optional<AbstractMap.SimpleEntry<Set<T>, Set<T>>> closestPair = findClosestClusters(clusters);
			if (closestPair.isPresent()) {
				Set<T> c1 = closestPair.get().getKey();
				Set<T> c2 = closestPair.get().getValue();

				// If the closest distance exceeds the threshold, stop merging
				if (distance(c1, c2) > threshold) {
					break;
				}

				// Remove the two closest clusters from the set
				clusters.remove(c1);
				clusters.remove(c2);

				// Merge the two clusters into one
				Set<T> union = new HashSet<>(c1);
				union.addAll(c2);
				clusters.add(union);
			} else {
				// No more pairs to merge, exit the loop
				break;
			}
		}

		// Return the final set of clusters
		return clusters;
	}

	/**
	 * Finds the closest pair of clusters from the given set of clusters.
	 *
	 * @param clusters the set of clusters to search
	 * @return an Optional containing the closest pair of clusters
	 */
	private Optional<AbstractMap.SimpleEntry<Set<T>, Set<T>>> findClosestClusters(Set<Set<T>> clusters) {
		return clusters.stream()
				.flatMap(c1 -> clusters.stream()
						.filter(c2 -> c1 != c2)
						.map(c2 -> new AbstractMap.SimpleEntry<>(c1, c2)))
				.min((pair1, pair2) -> Double.compare(distance(pair1.getKey(), pair1.getValue()),
						distance(pair2.getKey(), pair2.getValue())));
	}

	/**
	 * Calculates the distance between two clusters.
	 * The distance is defined as the minimum distance between any pair of items from the two clusters.
	 *
	 * @param c1 the first cluster
	 * @param c2 the second cluster
	 * @return the minimum distance between the two clusters
	 */
	private double distance(Set<T> c1, Set<T> c2) {
		return c1.stream()
				.flatMap(item1 -> c2.stream().map(item1::distance))
				.min(Double::compare)
				.orElse(Double.MAX_VALUE);
	}
}
