
import Map.MapData;
import Map.MapPanel;
import Map.XMLParser;

import javax.swing.*;
import java.awt.*;

public class Main
{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() ->
        {
            JFrame frame = new JFrame("Map Viewer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            try
            {
                MapData mapData = XMLParser.parse("resources/hartaLuxembourg.xml");

                MapPanel mapPanel = new MapPanel(mapData);

                JButton dijkstraButton = new JButton("Calculează Dijkstra");
                dijkstraButton.addActionListener(e ->
                {
                    mapPanel.calculateDijkstra();
                });

                JPanel container = new JPanel();
                container.setLayout(new BorderLayout());
                container.add(mapPanel, BorderLayout.CENTER);
                container.add(dijkstraButton, BorderLayout.SOUTH);

                frame.add(container);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error loading map: " + e.getMessage());
            }

            frame.setVisible(true);
        });
    }
}
