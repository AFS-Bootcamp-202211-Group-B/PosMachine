package pos.machine;

import java.util.ArrayList;
import java.util.List;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        List<Item> allItems = ItemDataLoader.loadAllItems();
        List<BarcodeAmount> barcodeAmounts =  countItemAmount(barcodes);
        String receipt = printResult(barcodeAmounts,allItems);
        return receipt;
    }
    public int getItemPrice(String barcode, List<Item> allItems){
        for (Item item:allItems){
            if(item.getBarcode().equals(barcode)){
                return item.getPrice();
            }
        }
        return 0;
    }
    public String getItemName(String barcode, List<Item> allItems){
        for (Item item:allItems){
            if(item.getBarcode().equals(barcode)){
                return item.getName();
            }
        }
        return null;
    }
    public int calculateSubTotalItemPrice(BarcodeAmount barcodeAmount, List<Item> allItems){
        int price = getItemPrice(barcodeAmount.getBarcode(),allItems);
        int subTotal = price * barcodeAmount.getAmount();
        return subTotal;
    }
    public int calculateTotalPrice(List<BarcodeAmount> barcodeAmounts, List<Item> allItems){
        int totalPrice = 0;
        for(BarcodeAmount barcodeAmount: barcodeAmounts){
            totalPrice += calculateSubTotalItemPrice(barcodeAmount,allItems);
        }
        return totalPrice;
    }
    public String printReceiptLine(BarcodeAmount barcodeAmount, List<Item> allItems){
        String itemName = getItemName(barcodeAmount.getBarcode(),allItems);
        int unitPrice = getItemPrice(barcodeAmount.getBarcode(), allItems);
        String resultLine = "Name: " + itemName + ", Quantity: " + barcodeAmount.getAmount() + ", Unit price: " + unitPrice + " (yuan), Subtotal: " + calculateSubTotalItemPrice(barcodeAmount,allItems) + " (yuan)";
        return resultLine;
    }
    public String printResult(List<BarcodeAmount> barcodeAmounts, List<Item> allItems){
        String receipt = "***<store earning no money>Receipt***\n";
        for(BarcodeAmount barcodeAmount: barcodeAmounts){
            receipt += printReceiptLine(barcodeAmount,allItems) + "\n";
        }
        receipt += "----------------------\n" +
                "Total: " + calculateTotalPrice(barcodeAmounts,allItems) + " (yuan)\n"
                + "**********************";

        return receipt;
    }
    public List<BarcodeAmount> countItemAmount(List<String>barcodes){
        List<BarcodeAmount>barcodeAmounts = new ArrayList<BarcodeAmount>();
        for (String barcode: barcodes){
            boolean isCount = false;
            for(BarcodeAmount barcodeAmount:barcodeAmounts){
                if(barcodeAmount.getBarcode().equals(barcode)){
                    barcodeAmount.setAmount(barcodeAmount.getAmount()+1);
                    isCount = true;
                    break;
                }
            }
            if(!isCount){
                BarcodeAmount newBarcodeAmount = new BarcodeAmount(barcode,1);
                barcodeAmounts.add(newBarcodeAmount);
            }
        }
        System.out.println(barcodeAmounts);
        return barcodeAmounts;
    }

}
