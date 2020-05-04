package collection;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static java.lang.String.valueOf;

/**
 * Класс "Receiver", отвечающий за хранение коллекции и управление
 */
public class CollectionManager {
    //Receiver

    ArrayList<Flat> flats = new ArrayList<>();

    private Date creationDate;

    public CollectionManager() {
        creationDate = new Date();
    }

    private static CollectionManager instance;

    public static CollectionManager getInstance() {
        if (instance == null) {
            instance = new CollectionManager();
        }
        return instance;
    }

    /**
     * Метод для добавление нового элемента в коллекцию
     * @param flat
     */
    public void add(Flat flat) {
        flats.add(flat);
    }

    /**
     * Метод для получение последнего элемента коллекции.
     * @return Последний элемент коллекции
     */
    public Flat getLast() { return flats.get(flats.size() - 1); }

    /**
     * Метод для очистки коллекции
     */
    public void clear() {
        flats.clear();
    }

    /**
     * Метод для получения размера коллекции.
     * @return Размер коллекции
     */
    public int size() {
        return flats.size();
    }

    /**
     * Метод для получения коллекции.
     * @return коллекция flats
     */
    public List<Flat> toList() {
        return flats;
    }

    /**
     * Метод для получения квартиры по ее id.
     * @param id
     * @return Квартира
     */
    public Flat getFlatById(int id) {
        for (Flat flat : flats) {
            if (flat.getId() == id) {
                return flat;
            }
        }
        return null;
    }

    /**
     * Метод для удаления квартиры по ее id.
     * @param id
     * @return true - если элемент коллекции успешно удален, false - если такого элемента не существует.
     */
    public boolean removeById(int id) {
        return flats.remove(getFlatById(id));
    }

    /**
     * Метод для проверки занятости id.
     * @param id
     * @return true - если id свободен, false - если id занят.
     */
    public boolean isIdFree(int id) {
        for (Flat flat : flats) {
            if (flat.getId() == id) {
                return false;
            }
        }
        return true;
    }

    /**
     * Метод для удаления квартиры по индексу элемента.
     * @param index
     */
    public void removeByIndex(int index) {
        flats.remove(index);
    }

    /**
     * Метод для сохранения в файл.
     * @param str Данные для сохранения.
     * @param path Путь к файлу для сохранения.
     * @throws IOException
     */
    public void save(String str, String path) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        writer.write(str);
        writer.close();
    }

    /**
     * Метод для получения минимального элента коллекции (Сортировка выполняется по времни до метро пешком).
     * @return Минимальный элемент коллекции.
     */
    public Flat getMinimal() {
        if (flats.size() > 0) {
            Collections.sort(flats);
            return flats.get(0);
        }
        return null;
    }

    /**
     * Метод для получения времени инициализации коллекции.
     * @return Время инициализации.
     */
    public Date getInitializationTime() {
        return creationDate;
    }

    /**
     * Метод для получения типа коллекции.
     * @return Тип коллекции.
     */
    public Class<?> getCollectionClass() {
        return flats.getClass();
    }

    /**
     * Метод для получения информации о коллекции.
     * @return Информация о коллекции.
     */
    public String info() {
        StringBuilder sb = new StringBuilder();
        sb.append("Время инициализации коллекции: ").append(getInitializationTime().toString()).append('\n');
        sb.append("Количество элементов в коллекции: ").append(size()).append('\n');
        sb.append("Тип коллекции: ").append(getCollectionClass().getName());
        return sb.toString();
    }

    @Override
    public String toString() {
        return "CollectionManager{" +
                "flats=" + flats +
                '}';
    }

    /**
     * Метод для преобразования коллекции в формат xml.
     * @return Данные в формате xml в строковом представлении.
     * @throws ParserConfigurationException
     */
    public String toXml() throws ParserConfigurationException {
        String str = " ";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();

        Element root = document.createElement("flats");
        document.appendChild(root);

        for (Flat flat : flats) {
            root.appendChild(generateNewFlat(document, flat));
        }

        DOMImplementation impl = document.getImplementation();
        DOMImplementationLS implLS = (DOMImplementationLS)impl.getFeature("LS","3.0");
        LSSerializer ser = implLS.createLSSerializer();
        ser.getDomConfig().setParameter("format-pretty-print", true);
        str = ser.writeToString(document);
        return str;

    }

    private static Node generateNewFlat(Document document, Flat flat) {
        Element newFlat = document.createElement("flat");
        Element coordinates = document.createElement("coordinates");
        Element house = document.createElement("house");

        newFlat.appendChild(generateElement(document, "id", valueOf(flat.getId())));
        newFlat.appendChild(generateElement(document, "name", flat.getName()));
        newFlat.appendChild(coordinates);
        coordinates.appendChild(generateElement(document, "x", valueOf(flat.getX())));
        coordinates.appendChild(generateElement(document, "y", valueOf(flat.getY())));
        newFlat.appendChild(generateElement(document, "creationDate", valueOf(flat.getCreationDate())));
        newFlat.appendChild(generateElement(document, "area", valueOf(flat.getArea())));
        newFlat.appendChild(generateElement(document, "numberOfRooms", valueOf(flat.getNumberOfRooms())));
        newFlat.appendChild(generateElement(document, "kitchenArea", valueOf(flat.getKitchenArea())));
        newFlat.appendChild(generateElement(document, "timeToMetroOnFoot", valueOf(flat.getTimeToMetroOnFoot())));
        newFlat.appendChild(generateElement(document, "furnish", valueOf(flat.getFurnish())));
        newFlat.appendChild(house);
        house.appendChild(generateElement(document, "houseName", flat.getHouseName()));
        house.appendChild(generateElement(document, "year", valueOf(flat.getHouseYear())));
        house.appendChild(generateElement(document, "numberOfFlatsOnFloor", valueOf(flat.getHouseNumberOfFlatsOnFloor())));

        return newFlat;
    }

    private static Node generateElement(Document document, String name, String value) {
        Element node = document.createElement(name);
        node.appendChild(document.createTextNode(value));

        return node;
    }


}
