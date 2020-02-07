
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
/**
 * Created by Danya on 24.02.2016.
 */
public class Loader {
    public static void main(String[] args) throws Exception {
        String fileName = "res/data-18M.xml";

        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        XMLHandler handler = new XMLHandler();
        System.out.println("start process...");
        long start = System.currentTimeMillis();
        saxParser.parse(new File(fileName), handler);
        System.out.println("Execution time: "+(System.currentTimeMillis() - start) + " ms");
        DBConnection.printVoterCounts();
        DBConnection.getConnection().close();
        StationHandler.printStationTime();
        System.out.println("finish process");
    }
}