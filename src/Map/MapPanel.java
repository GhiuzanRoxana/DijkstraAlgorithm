package Map;

import Algorithms.Dijkstra;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;

public class MapPanel extends JPanel
{
    private MapData mapData;
    private final int PADDING = 50;

    private Node selectedNode1 = null;
    private Node selectedNode2 = null;
    private List<Integer> shortestPath = null;

    public MapPanel(MapData mapData)
    {
        this.mapData = mapData;

        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                selectNode(e.getX(), e.getY());
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        double minLat = mapData.getNodes().values().stream().mapToDouble(n -> n.latitude).min().orElse(0);
        double maxLat = mapData.getNodes().values().stream().mapToDouble(n -> n.latitude).max().orElse(0);
        double minLon = mapData.getNodes().values().stream().mapToDouble(n -> n.longitude).min().orElse(0);
        double maxLon = mapData.getNodes().values().stream().mapToDouble(n -> n.longitude).max().orElse(0);

        double scaleX = (getWidth() - 2 * PADDING) / (maxLon - minLon);
        double scaleY = (getHeight() - 2 * PADDING) / (maxLat - minLat);

        for (Node node : mapData.getNodes().values())
        {
            int x = PADDING + (int) ((node.longitude - minLon) * scaleX);
            int y = getHeight() - PADDING - (int) ((node.latitude - minLat) * scaleY);

            if (node.equals(selectedNode1) || node.equals(selectedNode2))
            {
                g2d.setColor(Color.WHITE);
                g2d.fillOval(x - 6, y - 6, 12, 12);
                g2d.setColor(Color.RED);
                g2d.fillOval(x - 4, y - 4, 8, 8);
            }
            else
            {
                g2d.setColor(Color.BLACK);
                g2d.fillOval(x - 3, y - 3, 6, 6);
            }
        }

        g2d.setStroke(new BasicStroke(1));
        for (Arc arc : mapData.getArcs())
        {
            Node fromNode = mapData.getNodes().get(arc.from);
            Node toNode = mapData.getNodes().get(arc.to);

            int x1 = PADDING + (int) ((fromNode.longitude - minLon) * scaleX);
            int y1 = getHeight() - PADDING - (int) ((fromNode.latitude - minLat) * scaleY);
            int x2 = PADDING + (int) ((toNode.longitude - minLon) * scaleX);
            int y2 = getHeight() - PADDING - (int) ((toNode.latitude - minLat) * scaleY);

            g2d.setColor(Color.GRAY);
            g2d.drawLine(x1, y1, x2, y2);
        }

        if (shortestPath != null)
        {
            g2d.setStroke(new BasicStroke(4));
            g2d.setColor(Color.GREEN);

            for (int i = 0; i < shortestPath.size() - 1; i++) {
                Node fromNode = mapData.getNodes().get(shortestPath.get(i));
                Node toNode = mapData.getNodes().get(shortestPath.get(i + 1));

                int x1 = PADDING + (int) ((fromNode.longitude - minLon) * scaleX);
                int y1 = getHeight() - PADDING - (int) ((fromNode.latitude - minLat) * scaleY);
                int x2 = PADDING + (int) ((toNode.longitude - minLon) * scaleX);
                int y2 = getHeight() - PADDING - (int) ((toNode.latitude - minLat) * scaleY);

                g2d.drawLine(x1, y1, x2, y2);
            }
        }
    }


    private void selectNode(int mouseX, int mouseY)
    {
        double minLat = mapData.getNodes().values().stream().mapToDouble(n -> n.latitude).min().orElse(0);
        double maxLat = mapData.getNodes().values().stream().mapToDouble(n -> n.latitude).max().orElse(0);
        double minLon = mapData.getNodes().values().stream().mapToDouble(n -> n.longitude).min().orElse(0);
        double maxLon = mapData.getNodes().values().stream().mapToDouble(n -> n.longitude).max().orElse(0);

        double scaleX = (getWidth() - 2 * PADDING) / (maxLon - minLon);
        double scaleY = (getHeight() - 2 * PADDING) / (maxLat - minLat);

        Node closestNode = null;
        double closestDistance = Double.MAX_VALUE;

        for (Node node : mapData.getNodes().values())
        {
            int x = PADDING + (int) ((node.longitude - minLon) * scaleX);
            int y = getHeight() - PADDING - (int) ((node.latitude - minLat) * scaleY);

            double distance = Math.sqrt(Math.pow(mouseX - x, 2) + Math.pow(mouseY - y, 2));
            if (distance < closestDistance)
            {
                closestDistance = distance;
                closestNode = node;
            }
        }

        if (closestNode != null && closestDistance < 10)
        {
            if (selectedNode1 == null)
            {
                selectedNode1 = closestNode;
            }
            else
            {
                selectedNode2 = closestNode;
            }
        }
    }

    public void calculateDijkstra()
    {
        if (selectedNode1 != null && selectedNode2 != null)
        {
            Map<Integer, Integer> predecessors = Dijkstra.findShortestPath(mapData, selectedNode1.id, selectedNode2.id);
            shortestPath = Dijkstra.reconstructPath(predecessors, selectedNode1.id, selectedNode2.id);
            repaint();
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Selectează două noduri înainte de a rula algoritmul Dijkstra.");
        }
    }
}
