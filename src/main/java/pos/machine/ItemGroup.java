package pos.machine;

public class ItemGroup {
    private Item item;
    private int count;
    private int subTotal;

    public ItemGroup(Item item, int count, int subTotal){
        this.item=item;
        this.count=count;
        this.subTotal=subTotal;
    }
    public Item getItem() {
        return item;
    }
    public void setItem(Item item) {
        this.item = item;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public int getSubTotal() {
        return subTotal;
    }
    public void setSubTotal(int subTotal) {
        this.subTotal = subTotal;
    }
    
}
