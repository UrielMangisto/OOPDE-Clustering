import java.util.*;
import java.io.IOException;
import java.util.Set;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.stream.Collectors;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;


public class AgglomerativeClustering <T extends Clusterable<T>> implements Clustering<T>{
	double threshold;
	public AgglomerativeClustering(double threshold) {
		this.threshold = threshold;
	}
	public Set<Set<T>> clusterSet(Set<T> items) {
		Set<Set<T>> clusters = items.stream()
				.map(Collections::singleton)
				.collect(Collectors.toSet());

		while (clusters.size() > 1) {
			Optional<AbstractMap.SimpleEntry<Set<T>, Set<T>>> closestPair = findClosestClusters(clusters);
			if (closestPair.isPresent()) {
				Set<T> c1 = closestPair.get().getKey();
				Set<T> c2 = closestPair.get().getValue();
				if (distance(c1, c2) > threshold) {
					break;
				}
				clusters.remove(c1);
				clusters.remove(c2);
				Set<T> union = new HashSet<>(c1);
				union.addAll(c2);
				clusters.add(union);
			} else {
				break;
			}
		}

		return clusters;
	}

	private Optional<AbstractMap.SimpleEntry<Set<T>, Set<T>>> findClosestClusters(Set<Set<T>> clusters) {
		return clusters.stream()
				.flatMap(c1 -> clusters.stream()
						.filter(c2 -> c1 != c2)
						.map(c2 -> new AbstractMap.SimpleEntry<>(c1, c2)))
				.min((pair1, pair2) -> Double.compare(distance(pair1.getKey(), pair1.getValue()),
						distance(pair2.getKey(), pair2.getValue())));
	}

	private double distance(Set<T> c1, Set<T> c2) {
		return c1.stream()
				.flatMap(item1 -> c2.stream().map(item1::distance))
				.min(Double::compare)
				.orElse(Double.MAX_VALUE);
	}
}
