package pos.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        List<ReceiptItem> receiptItems = decodeToItems(barcodes);
        Receipt receipt = calculateCost(receiptItems);
        return renderReceipt(receipt);
    }
    public List<ReceiptItem> decodeToItems(List<String> barcodes) {
        List<Item> items = ItemDataLoader.loadAllItems();
        Map<String, Integer> barcodesQuantitiesMap = calculateQuantites(barcodes);

        List<ReceiptItem> receiptItems = new ArrayList<>();
        for (Map.Entry<String, Integer> barcodesQuantity : barcodesQuantitiesMap.entrySet()) {
            Item currentItem = items.stream()
                .filter( item -> item.getBarcode().equals(barcodesQuantity.getKey()))
                .findFirst()
                .get();

            int quantity = barcodesQuantity.getValue();

            receiptItems.add(new ReceiptItem(
                currentItem.getName(),
                quantity,
                currentItem.getPrice(),
                quantity*currentItem.getPrice()
            ));
        }
        return receiptItems;
    }
    public Map<String, Integer> calculateQuantites(List<String> barcodes){
        Map<String, Integer> barcodesQuantitiesMap = new TreeMap<>();
        for(String barcode:barcodes){
            if (barcodesQuantitiesMap.containsKey(barcode)){
                barcodesQuantitiesMap.put(barcode, barcodesQuantitiesMap.get(barcode) + 1);
            }
            else{
                barcodesQuantitiesMap.put(barcode, 1);
            }
        }
        return barcodesQuantitiesMap;
    }
    public Receipt calculateCost(List<ReceiptItem> receiptItems){
        int totalPrice = receiptItems.stream()
            .map( receiptItem -> receiptItem.getSubTotal())
            .reduce( 0, Integer::sum);
        return new Receipt(receiptItems, totalPrice);
    }
    public String generateItemsReceipt(Receipt receipt){
        String itemReceipt = "";
        List<ReceiptItem> receiptItems = receipt.getReceiptItems();
        for(ReceiptItem receiptItem: receiptItems){
            itemReceipt += String.format("Name: %s, Quantity: %d, Unit price: %d (yuan), Subtotal: %d (yuan)\n",
                receiptItem.getName(),
                receiptItem.getQuantity(),
                receiptItem.getUnitPrice(),
                receiptItem.getSubTotal());
        }

        return itemReceipt;
    }
    public String generateReceipt(int totalPrice){
        return String.format("Total: %d (yuan)\n", totalPrice);
    }
    public String renderReceipt(Receipt receipt){
        String itemsReceipt = generateItemsReceipt(receipt);
        return
            "***<store earning no money>Receipt***\n" +
            itemsReceipt +
            "----------------------\n" +
            generateReceipt(receipt.getTotalPrice()) +
            "**********************";
    }
}
