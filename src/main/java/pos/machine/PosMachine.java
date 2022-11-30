package pos.machine;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        List<ReceiptItem> itemsWithDetail = decodeToItems(barcodes);

        Receipt receipt = calculateCost(itemsWithDetail);

        return renderReceipt(receipt);
    }

    private List<ReceiptItem> decodeToItems(List<String> barcodes) {
        Map<String, Integer> barcodesWithCount = parse(barcodes);

        List<ReceiptItem> itemsWithDetail = getItemsDetail(barcodesWithCount);

        return itemsWithDetail;
    }

    private Map<String, Integer> parse(List<String> items) {
        LinkedHashMap<String, Integer> itemsWithCount = new LinkedHashMap<>();

        items.forEach(item -> {
            if (!item.contains("-")) {
                itemsWithCount.put(item, itemsWithCount.getOrDefault(item,  0) + 1);
            } else {
                String barcode = getBarcode(item);
                int count = getCount(item);
                itemsWithCount.put(barcode, itemsWithCount.getOrDefault(barcode, 0) + count);
            }
        });

        return itemsWithCount;
    }

    private int getCount(String item) {
        return Integer.parseInt(item.split("-")[1]);
    }

    private String getBarcode(String item) {
        return item.split("-")[0];
    }

    private List<ReceiptItem> getItemsDetail(Map<String, Integer> barcodesWithCount) {
        List<ItemInfo> allItemInfos = ItemDataLoader.loadAllItemInfos();
        List<ReceiptItem> receiptItemList = new ArrayList<ReceiptItem>();
        barcodesWithCount.keySet().forEach(barcode -> {
            ItemInfo item = allItemInfos.stream().filter(itemInfo -> itemInfo.getBarcode().equals(barcode)).findFirst().get();
            receiptItemList.add(new ReceiptItem(item.getName(), barcodesWithCount.get(barcode), item.getPrice()));
        });

        return receiptItemList;
    }

    private Receipt calculateCost(List<ReceiptItem> itemsWithDetail) {
        List<ReceiptItem> itemsWithCost = calculateItemsCost(itemsWithDetail);

        int totalPrice = calculateTotal(itemsWithCost);

        return new Receipt(itemsWithCost, totalPrice);
    }

    private List<ReceiptItem> calculateItemsCost(List<ReceiptItem> itemsWithDetail) {
        List<ReceiptItem> itemsWithSubtotal = new ArrayList<>(itemsWithDetail);

        itemsWithSubtotal.forEach(receiptItem -> receiptItem.setSubTotal(receiptItem.getUnitPrice() * receiptItem.getQuantity()));

        return itemsWithSubtotal;
    }

    private int calculateTotal(List<ReceiptItem> itemsWithSubtotal) {
        return itemsWithSubtotal.stream().mapToInt(ReceiptItem::getSubTotal).sum();
    }

    private String renderReceipt(Receipt receipt) {
        String itemsPart = generateItemsDetail(receipt.getItemDetails());

        return generateReceipt(itemsPart, receipt.getTotalPrice());
    }

    private String generateItemsDetail(List<ReceiptItem> itemsWithCost) {
        String itemDetail = "";
        for (ReceiptItem receiptItem : itemsWithCost) {
            itemDetail += String.format("Name: %s, Quantity: %s, Unit price: %s (yuan), Subtotal: %s (yuan)%n",
                    receiptItem.getName(), receiptItem.getQuantity(), receiptItem.getUnitPrice(), receiptItem.getSubTotal());
        }
        return itemDetail;
    }

    public String generateReceipt(String itemsPart, int totalPrice) {
        String header = "***<store earning no money>Receipt***";
        String splice = "----------------------";
        String total = String.format("Total: %s (yuan)", totalPrice);
        String end = "**********************";

        return String.format("%s%n%s%s%n%s%n%s", header, itemsPart, splice, total, end);
    }
}
