package pos.machine;

import java.util.ArrayList;
import java.util.List;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {

        Receipt receipt = new Receipt(ItemDataLoader.decodeToItems(barcodes));

        return receipt.generateReport();

    }
}
