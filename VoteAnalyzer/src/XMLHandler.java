import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.sql.SQLException;

public class XMLHandler extends DefaultHandler {

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        try {
            if ("voter".equals(qName)) {
                String name = attributes.getValue("name");
                String birthDay = attributes.getValue("birthDay");
                DBConnection.countVoter(name, birthDay);
            } else if ("visit".equals(qName)) {
                Integer station = Integer.parseInt(attributes.getValue("station"));
                String visitTime = attributes.getValue("time");
                StationHandler.defineStationWorkTime(station, visitTime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void endDocument() {
        try {
            DBConnection.executeMultiQuery();
            DBConnection.getConnection().setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
