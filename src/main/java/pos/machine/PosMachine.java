package pos.machine;
import java.util.*;
import java.util.stream.Collectors;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        List<Item> itemsWithDetail = getItemInfos(barcodes);
        Receipt receipt = calculateCost(itemsWithDetail);
        return generateTotal(receipt);
    }

    private String generateTotal(Receipt receipt) {
        String printedReceipt = generateLine(receipt);
        printedReceipt += generateTotalLine(receipt);
        return printedReceipt;
    }

    private String generateLine(Receipt receipt) {
        String itemsDetail = "***<store earning no money>Receipt***\n";
        for (ReceiptItem receiptItem : receipt.getItemDetail()) {
            itemsDetail += "Name: " + receiptItem.getName() + ", " + "Quantity: " + receiptItem.getQuantity() + ", " + "Unit price: "
                    + receiptItem.getUnitPrice() + " (yuan), Subtotal: " + receiptItem.getSubTotal() + " (yuan)\n";
        }
        return itemsDetail;
    }

    private String generateTotalLine(Receipt receipt) {
        return "----------------------\n" +
                "Total: " + receipt.getTotalPrice() + " (yuan)" +
                "\n**********************";
    }

    private List<Item> getItemInfos(List<String> barcodes){
        List<Item> database = ItemDataLoader.loadAllItems();
        List<Item> itemsWithDetail = new ArrayList<>();
        for (String barcode : barcodes){
            for (int i = 0; i < database.size(); i++){
                if (barcode.equals(database.get(i).getBarcode())) {
                    itemsWithDetail.add(database.get(i));
                }
            }
        }
        return itemsWithDetail;
    }
    private Receipt calculateCost(List<Item> itemsWithDetail){
        List<ReceiptItem> receiptItems = calculateReceiptItems(itemsWithDetail);
        int totalPrice = calculateTotalPrice(receiptItems);
        return new Receipt(receiptItems, totalPrice);
    }
    private List<ReceiptItem> calculateReceiptItems(List<Item> itemsWithDetail) {
        List<ReceiptItem> receiptItems = new ArrayList<>();
        List<Item> itemsWithDetail_distinct = itemsWithDetail.stream().distinct().collect(Collectors.toList());
        for (Item item : itemsWithDetail_distinct) {
            int quantity = Collections.frequency(itemsWithDetail, item);
            receiptItems.add(new ReceiptItem(item.getName(), quantity, item.getPrice(), item.getPrice() * quantity));
        }
        return receiptItems;
    }
    private int calculateTotalPrice(List<ReceiptItem> receiptItems){
        int totalPrice = 0;
        for (ReceiptItem receiptItem : receiptItems){
            totalPrice = totalPrice + receiptItem.getSubTotal();
        }
        return totalPrice;
    }
}

