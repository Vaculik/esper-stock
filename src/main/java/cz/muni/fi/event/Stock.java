package cz.muni.fi.event;

/**
 * Created by vaculik on 2.10.15.
 */
public class Stock {
    private double price;
    private String label;

    public Stock(double price, String label) {
        this.price = price;
        this.label = label;
    }

    public double getPrice() {
        return price;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "Stock: label=" + label + "  price=" + price;
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 13 + (int) Math.round(price * 100);
        hash = hash * 11 + label.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Stock)){
            return false;
        }
        Stock s = (Stock) obj;
        if ( Math.abs(s.getPrice() - price) > 0.001 ) {
            return false;
        }
        if (!s.getLabel().equals(label)) {
            return false;
        }
        return true;
    }
}
