package pos.machine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ItemDataLoader {
    public static List<Item> loadAllItems() {
        Item firstItem = new Item("ITEM000000", "Coca-Cola", 3);
        Item secondItem = new Item("ITEM000001", "Sprite", 3);
        Item thirdItem = new Item("ITEM000004", "Battery", 2);
        List<Item> items = new ArrayList<>();
        items.add(firstItem);
        items.add(secondItem);
        items.add(thirdItem);

        return items;
    }

    public static List<ReceiptItem> decodeToItems(List<String> barcodes) {

        List<Item> itemList = ItemDataLoader.loadAllItems();

        List<ReceiptItem> receiptItems = new ArrayList<>();

        for(String barcode : barcodes){
            for(Item item : itemList){
                if(barcode.equals( item.getBarcode() ) ){
                    String name = item.getName();
                    int price = item.getPrice();
                    ReceiptItem searchReceiptItem = searchReceiptItem(receiptItems, name);
                    if(searchReceiptItem != null){
                        searchReceiptItem.setQuantity(searchReceiptItem.getQuantity()+1);
                    }
                    else{
                        ReceiptItem tmp = new ReceiptItem(name, price, 1);
                        receiptItems.add(tmp);
                    }
                }
            }
        }

        return receiptItems;
    }

    public static ReceiptItem searchReceiptItem(List<ReceiptItem> receiptItems, String name){
        for(ReceiptItem receiptItem : receiptItems){
            if(receiptItem.getName().equals(name))
                return receiptItem;
        }

        return null;
    }


    public static List<String> loadBarcodes() {
        return Arrays.asList("ITEM000000", "ITEM000000", "ITEM000000", "ITEM000000", "ITEM000001", "ITEM000001", "ITEM000004", "ITEM000004", "ITEM000004");
    }
}
