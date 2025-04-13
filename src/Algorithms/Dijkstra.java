package Algorithms;

import Map.Arc;
import Map.MapData;

import java.util.*;

public class Dijkstra
{
    public static Map<Integer, Integer> findShortestPath(MapData mapData, int startNode, int endNode)
    {
        int n = mapData.getNodes().size();
        double[] distances = new double[n];
        Arrays.fill(distances, Double.MAX_VALUE);
        distances[startNode] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingDouble(a -> a[1]));
        pq.add(new int[]{startNode, 0});

        Map<Integer, Integer> predecessors = new HashMap<>();
        Set<Integer> visited = new HashSet<>();

        while (!pq.isEmpty())
        {
            int[] current = pq.poll();
            int currentNode = current[0];

            if (visited.contains(currentNode)) continue;
            visited.add(currentNode);

            if (currentNode == endNode) break;

            for (Arc arc : mapData.getAdjacentArcs(currentNode))
            {
                int neighbor = arc.to;
                double newDist = distances[currentNode] + arc.length;

                if (newDist < distances[neighbor])
                {
                    distances[neighbor] = newDist;
                    predecessors.put(neighbor, currentNode);
                    pq.add(new int[]{neighbor, (int) newDist});
                }
            }
        }

        return predecessors;
    }

    public static List<Integer> reconstructPath(Map<Integer, Integer> predecessors, int startNode, int endNode)
    {
        List<Integer> path = new ArrayList<>();
        for (Integer at = endNode; at != null; at = predecessors.get(at))
        {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }
}
