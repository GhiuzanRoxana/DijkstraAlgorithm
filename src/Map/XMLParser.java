package Map;

public class XMLParser
{
    public static MapData parse(String filePath) throws Exception
    {
        MapData mapData = new MapData();

        org.w3c.dom.Document document = javax.xml.parsers.DocumentBuilderFactory.newInstance()
                .newDocumentBuilder().parse(new java.io.File(filePath));

        org.w3c.dom.NodeList nodeElements = document.getElementsByTagName("node");
        for (int i = 0; i < nodeElements.getLength(); i++)
        {
            org.w3c.dom.Element element = (org.w3c.dom.Element) nodeElements.item(i);
            int id = Integer.parseInt(element.getAttribute("id"));
            double latitude = Double.parseDouble(element.getAttribute("latitude"));
            double longitude = Double.parseDouble(element.getAttribute("longitude"));
            mapData.addNode(new Node(id, latitude, longitude));
        }

        org.w3c.dom.NodeList arcElements = document.getElementsByTagName("arc");
        for (int i = 0; i < arcElements.getLength(); i++)
        {
            org.w3c.dom.Element element = (org.w3c.dom.Element) arcElements.item(i);
            int from = Integer.parseInt(element.getAttribute("from"));
            int to = Integer.parseInt(element.getAttribute("to"));
            int length = Integer.parseInt(element.getAttribute("length"));
            mapData.addArc(new Arc(from, to, length));
        }

        return mapData;
    }
}
