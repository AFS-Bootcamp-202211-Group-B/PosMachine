package pos.machine;

import java.util.List;

public class Receipt {

    private List<ReceiptItem> receiptItems;
    private int total;

    public Receipt(List<ReceiptItem> receiptItems){
        this.receiptItems = receiptItems;
        int subTotal = 0;
        for(ReceiptItem item : receiptItems){
            subTotal += item.getSubTotal();
        }
        this.total = subTotal;
    }

    public String generateReport(){

        String result =  "***<store earning no money>Receipt***\n";

        for(ReceiptItem item : receiptItems){
            result += "Name: " + item.getName() + ", Quantity: " + item.getQuantity()
                    + ", Unit price: " + item.getUnitPrice() + " (yuan), Subtotal: " + item.getSubTotal() + " (yuan)" + "\n";
        }
        result+="----------------------\n";
        result += "Total: " + total + " (yuan)\n";
        result += "**********************";

        return result;
    }


}
