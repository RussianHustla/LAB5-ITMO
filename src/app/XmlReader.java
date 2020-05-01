package app;

import collection.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class XmlReader {

    public static void read() throws IOException, ParserConfigurationException, SAXException {

        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser saxParser = spf.newSAXParser();
        XMLReader xmlReader = saxParser.getXMLReader();
        XMLHandler handler = new XMLHandler();
        xmlReader.setContentHandler(handler);
        xmlReader.parse("flats.xml");
    }

    private static class XMLHandler extends DefaultHandler {


        private Flat currentFlat;
        private Coordinates currentFlatCoords;
        private House currentFlatHouse;
        private String currentElement;
        private CollectionManager collection;

        CollectionManager getCollection() {
            return collection;
        }

        public void startDocument() {
            System.out.println("Загрузка коллекции из XML...");
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            currentElement = qName;

            switch (currentElement) {
                case "flats": {
                    collection = CollectionManager.getInstance();
                }

                case "flat": {
                    currentFlat = new Flat();
                } break;

                case "coordinates": {
                    currentFlatCoords = new Coordinates();
                } break;

                case "house": {
                    currentFlatHouse = new House();
                } break;

                default: {
                    //nothing;
                }
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) {

            switch (qName) {
                case "flat": {
                    try {
                        collection.add(currentFlat);
                    } catch (Exception e) {
                        System.err.println("Ошибка при добавлении элемента в коллекцию из файла");
                        e.printStackTrace();
                    }

                    currentFlat = null;
                    currentFlatHouse = null;
                    currentFlatCoords = null;
                } break;

                default: {
                    //nothing
                }
            }
            currentElement = null;
        }

        @Override
        public void endDocument() {
            System.out.println("Загрузка коллекции завершена.");
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            String text = new String(ch, start, length);

            if (text.contains("<") || currentElement == null) {
                return;
            }

            switch (currentElement) {
                case "id": {
                    currentFlat.setId(Integer.parseInt(text));
                }

                case "name": {
                    currentFlat.setName(text);
                } break;

                case "creationDate": {
                    DateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss zzz yyyy", Locale.US);

                    try {
                        Date date = dateFormat.parse(text);
                        currentFlat.setCreationDate(date);
                        System.out.println(text);
                    } catch (ParseException e) {
                        System.err.println("Встречен неправильный формат даты при загрузки коллекции" + text);
                        e.printStackTrace();
                    }
                } break;

                case "x": {
                    currentFlatCoords.setX(Double.parseDouble(text));
                    System.out.println(text);

                } break;

                case "y": {
                    currentFlatCoords.setY(Double.parseDouble(text));
                    currentFlat.setCoordinates(currentFlatCoords);
                    System.out.println(text);

                } break;

                case "area": {
                    currentFlat.setArea(Double.valueOf(text));
                    System.out.println(text);

                } break;

                case "numberOfRooms": {
                    currentFlat.setNumberOfRooms(Long.valueOf(text));
                    System.out.println(text);

                } break;

                case "kitchenArea": {
                    try {
                        currentFlat.setKitchenArea(Integer.parseInt(text));
                        System.out.println(text);

                    } catch (NumberFormatException e) {
                        System.err.println("kitchen ERR");
                    }

                } break;

                case "timeToMetroOnFoot": {
                    currentFlat.setTimeToMetroOnFoot(Double.valueOf(text));
                    System.out.println(text);

                } break;

                case "furnish": {
                    currentFlat.setFurnish(Furnish.valueOf(text));
                    System.out.println(text);

                } break;

                case "houseName": {
                    currentFlatHouse.setName(text);
                    System.out.println(text);

                } break;

                case "year": {
                    try {
                        currentFlatHouse.setYear(Integer.valueOf(text));
                        System.out.println(text);

                    } catch (NumberFormatException e) {
                        System.err.println("year ERR");
                    }

                } break;

                case "numberOfFlatsOnFloor": {
                    currentFlatHouse.setNumberOfFlatsOnFloor(Long.parseLong(text));
                    System.out.println(text);

                    currentFlat.setHouse(currentFlatHouse);
                } break;

                default: {
                    //nothing
                }
            }
        }
    }
}
