package cz.muni.fi.event;

/**
 * Created by vaculik on 2.10.15.
 */
public class Stock {
    private double price;
    private String sign;

    public Stock(double price, String sign) {
        this.price = price;
        this.sign = sign;
    }

    public double getPrice() {
        return price;
    }

    public String getSign() {
        return sign;
    }

    @Override
    public String toString() {
        return "Stock: sign=" + sign + "  price=" + price;
    }
}
