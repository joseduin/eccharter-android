package flight.report.ec.charter.utils;

import java.util.Comparator;

import flight.report.ec.charter.modelo.Image;

/**
 *  Ordenamos Cronologicamente las imagenes de galeria / albun
 **/
public class MapComparator implements Comparator<Image> {
    private final String key;
    private final String order;

    public MapComparator(String key, String order) {
        this.key = key;
        this.order = order;
    }

    @Override
    public int compare(Image first, Image second) {
        String firstValue = first.getAlbum().get(key);
        String secondValue = second.getAlbum().get(key);
        if(this.order.toLowerCase().contentEquals("asc")) {
            return firstValue.compareTo(secondValue);
        } else {
            return secondValue.compareTo(firstValue);
        }
    }
}

