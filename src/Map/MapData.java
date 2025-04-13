package Map;

import java.util.*;

public class MapData
{
    private Map<Integer, Node> nodes = new HashMap<>();
    public List<List<Arc>> adjacencyList = new ArrayList<>();
    private List<Arc> arcs = new ArrayList<>();

    public void addNode(Node node)
    {
        nodes.put(node.id, node);
        adjacencyList.add(new ArrayList<>());
    }

    public void addArc(Arc arc)
    {
        arcs.add(arc);
        adjacencyList.get(arc.from).add(arc);
    }

    public List<Arc> getArcs()
    {
        return arcs;
    }

    public List<Arc> getAdjacentArcs(int nodeId)
    {
        return adjacencyList.get(nodeId);
    }

    public Map<Integer, Node> getNodes()
    {
        return nodes;
    }
}
