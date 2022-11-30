package pos.machine;

public class BarcodeAmount {
    private String barcode;
    private int amount;
    public BarcodeAmount(String barcode, int amount) {
        this.barcode = barcode;
        this.amount = amount;
    }

    public String getBarcode(){
        return barcode;
    }
    public int getAmount(){
        return amount;
    }
    public void setBarcode(String barcode){
        this.barcode = barcode;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
