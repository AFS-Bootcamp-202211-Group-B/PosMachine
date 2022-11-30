package pos.machine;

import java.util.List;

public class Receipt {
    private final List<ReceiptItem> receiptItemDetails;
    private final int totalPrice;

    public Receipt(List<ReceiptItem> receiptItemDetails, int totalPrice) {
        this.receiptItemDetails = receiptItemDetails;
        this.totalPrice = totalPrice;
    }

    public List<ReceiptItem> getItemDetails() {
        return receiptItemDetails;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}
