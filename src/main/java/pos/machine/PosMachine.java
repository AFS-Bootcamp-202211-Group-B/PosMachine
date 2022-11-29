package pos.machine;

import java.util.ArrayList;
import java.util.List;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        return null;
    }
    public int getItemPrice(String barcode, Item[] allItems){
        for (Item item:allItems){
            if(item.getBarcode().equals(barcode)){
                return item.getPrice();
            }
        }
        return 0;
    }
    public String getItemName(String barcode, Item[] allItems){
        for (Item item:allItems){
            if(item.getBarcode().equals(barcode)){
                return item.getName();
            }
        }
        return null;
    }
    public int calculateSubTotalItemPrice(List<BarcodeAmount> barcodeAmounts, Item[] allItems){
        for(BarcodeAmount barcodeAmount: barcodeAmounts){
            int price = getItemPrice(barcodeAmount.getBarcode(),allItems);
            int subTotal = price * barcodeAmount.getAmount();
            return subTotal;
        }
        return 0;
    }
    public int calculateTotalPrice(List<BarcodeAmount> barcodeAmount, Item[] allItems){
        return 0;
    }
    public String printReceiptLine(List<BarcodeAmount> barcodeAmount, Item[] allItems){
        return null;
    }
    public String printResult(List<BarcodeAmount> barcodeAmount, Item[] allItems){
        return null;
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
