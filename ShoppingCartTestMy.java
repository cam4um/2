package shoppingtest;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@RunWith(Parameterized.class)
class ShoppingCartTestMy {

    int place;
    List<Item> items = new ArrayList<>();

    private static class Item{
        String title;
        double price;
        int quantity;
        ShoppingCart.ItemType type;
    }

    @Test
    void main() {
        ShoppingCartTestMy cart1 = new ShoppingCartTestMy();

        cart1.addItem("Apple", 0.99, 30, ShoppingCart.ItemType.NEW);
        cart1.addItem("Banana", 2.00, 4, ShoppingCart.ItemType.SECOND_FREE);
        cart1.addItem("A long piece of toilet paper", 7.20, 100, ShoppingCart.ItemType.SALE);
        cart1.addItem("Nails", 2.00, 500, ShoppingCart.ItemType.REGULAR);

        System.out.println(cart1.getFormat());
        System.out.println(cart1.alignInfo());
    }


    public int createRandom() {
        Random rd = new Random();
        int min = -1;
        int max = 1;
            place = rd.nextInt(max-min+1) + min;
        return place;
    }


public String alignInfo() {
String str;
    if (place == -1){
        str = "TABLE CONTENT IS ALIGNED TO THE LEFT";
    }else if (place == 0) {str = "TABLE CONTENT IS ALIGNED TO THE CENTER";}
    else    str = "TABLE CONTENT IS ALIGNED TO THE RIGHT";
    return str;
}

    public String getFormat() {
        if (items.size() == 0)
            return "No items.";
        List<String[]> lines = new ArrayList<>();
        String[] header = {"#","Item","Price","Quan.","Discount","Total"};
        int align = createRandom();
        // formatting each line
        double total = 0.00;
        int index = 0;
        for (Item item : items) {
            int discount = calculateDiscount(item.type, item.quantity);
            double itemTotal = item.price * item.quantity * (100.00 - discount) / 100.00;
            lines.add(new String[]{
                    String.valueOf(++index),
                    item.title,
                    MONEY.format(item.price),
                    String.valueOf(item.quantity),
                    (discount == 0) ? "-" : (discount + "%"),
                    MONEY.format(itemTotal)
            });
            total += itemTotal;
        }
        String[] footer = { String.valueOf(index),"","","","", MONEY.format(total) };
        // formatting table
        // column max length
        int[] width = new int[]{0,0,0,0,0,0};
        for (String[] line : lines)
            for (int i = 0; i < line.length; i++)
                width[i] = Math.max(width[i], line[i].length());
        for (int i = 0; i < header.length; i++)
            width[i] = Math.max(width[i], header[i].length());
        for (int i = 0; i < footer.length; i++)
            width[i] = Math.max(width[i], footer[i].length());
        // line length
        int lineLength = width.length -1;
        for (int w : width)
            lineLength += w;
        StringBuilder sb = new StringBuilder();
        // header
        for (int i = 0; i < header.length; i++)
            ShoppingCart.appendFormatted(sb, header[i], align, width[i]);
        sb.append("\n");
        // separator
        sb.append("-".repeat(Math.max(0, lineLength)));
        sb.append("\n");
        // lines
        for (String[] line : lines) {
            for (int i = 0; i < line.length; i++)
                ShoppingCart.appendFormatted(sb, line[i], align, width[i]);
            sb.append("\n");
        }
        if (lines.size() > 0) {
            // separator
            for (int i = 0; i < lineLength; i++)
                sb.append("-");
            sb.append("\n");
        }
        // footer
        for (int i = 0; i < footer.length; i++)
            ShoppingCart.appendFormatted(sb, footer[i], align, width[i]);
        return sb.toString();
    }

    private static final NumberFormat MONEY;
    static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        MONEY = new DecimalFormat("$#.00", symbols);
    }


    public void addItem(String title, double price, int quantity, ShoppingCart.ItemType type){
        if (title == null || title.length() == 0 || title.length() > 32)
            throw new IllegalArgumentException("Illegal title");
        if (price < 0.01)
            throw new IllegalArgumentException("Illegal price");
        if (quantity <= 0)
            throw new IllegalArgumentException("Illegal quantity");
        Item item = new Item();
        item.title = title;
        item.price = price;
        item.quantity = quantity;
        item.type = type;
        items.add(item);
    }

    public static int calculateDiscount(ShoppingCart.ItemType type, int quantity){
        int discount = 0;
        switch (type) {
            case NEW:
                return 0;
            case REGULAR:
                discount = 0;
                break;
            case SECOND_FREE:
                if (quantity > 1)
                    discount = 50;
                break;
            case SALE:
                discount = 70;
                break;
        }
        discount = discount + quantity / 10;
        if (discount > 80)
            discount = 80;
        return discount;
    }

}





//
//
//    @Test
//    void addItem() {
//    }
//
////    @Test
////    void formatTicket() {
////    }
//
//    @Test
//    void appendFormatted() {
//    }
//
//    @Test
//    void calculateDiscount() {
//    }
//}