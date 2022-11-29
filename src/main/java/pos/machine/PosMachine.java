package pos.machine;

import java.util.List;
import java.util.stream.Collectors;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        return null;
    }

    private Item getItem(String barcode, List<Item> items) {
        return items.stream().filter(item -> item.getBarcode().equals(barcode)).findFirst().orElse(null);

    }
}
