package shoppingtest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.*;
import static org.junit.Assert.*;

public class AddItemTest {
    private ShoppingCart cart;

    public static class Item {
        public enum Type {SECOND, REGULAR, SALE, DISCOUNT}


        public String title;
        public float price;
        public int quantity;
        public Type type;



        public Item(String title, float price, Type type, int quantity) {
            this.title = title;
            this.price = price;
            this.type = type;
            this.quantity = quantity;
        }
    }




    @Before
    public void createCart() {
        cart = new ShoppingCart();
    }

    @Test
    public void zeroPrice() {
        cart.addItem("Title", 0.00f, 1, Item.Type.REGULAR);
        System.out.println(cart);
    }





}




//class AddItemTest {
//
//    @Test
//    void main() {
//    }
//
//    @Test
//    void addItem() {
//    }
//
//    @Test
//    void formatTicket() {
//    }
//
//    @Test
//    void appendFormatted() {
//    }
//
//    @Test
//    void calculateDiscount() {
//    }
//}