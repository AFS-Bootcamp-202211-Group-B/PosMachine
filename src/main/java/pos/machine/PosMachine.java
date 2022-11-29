package pos.machine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        return null;
    }

    private Item getItem(String barcode, List<Item> items) {
        return items.stream().filter(item -> item.getBarcode().equals(barcode)).findFirst().orElse(null);

    }

    private int getQuantity(List<String> barcodes, String barcode) {
        return (int) barcodes.stream().filter(code -> code.equals(barcode)).count();
    }

    private int getSubtotal(Item item, int quantity) {
        return item.getPrice() * quantity;
    }

    private String generateLine(Item item, int quanitity) {
        return "Name: " + item.getName()
                + ", Quantity: " + quanitity
                + ", Unit price: " + item.getPrice()
                + ", Subtotal: " + getSubtotal(item, quanitity) + " (yuan)";
    }

    private int calculateTotal(int[] subtotal) {
        return IntStream.of(subtotal).sum();
    }

    private List<String> getUniqueBarcodes(List<String> barcodes) {
        return barcodes.stream().distinct().collect(Collectors.toList());
    }

    private String formatReceipt(List<String> barcodes) {
        List<String> uniqueBarcodes = getUniqueBarcodes(barcodes);
        String receipt = "***<store earning no money>Receipt ***\n";
        int[] subtotal = new int[uniqueBarcodes.size()];
        for (String barcode : uniqueBarcodes) {
            Item item = getItem(barcode, ItemDataLoader.loadAllItems());
            int quantity = getQuantity(barcodes, barcode);
            receipt += generateLine(item, quantity);
            subtotal[uniqueBarcodes.indexOf(barcode)] = getSubtotal(item, quantity);
            receipt += "\n";
        }
        receipt += "----------------------";
        receipt += "Total: " + calculateTotal(subtotal) + " (yuan)\n**********************";
        return receipt;
    }

}
