package pos.machine;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import pos.machine.Item;
import pos.machine.ItemDataLoader;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        
        return null;
    }

    public List<ItemGroup> aggregateAllProducts(List<BarcodeGroup> barcodeGroups){
        List<Item> allItems = ItemDataLoader.loadAllItems();
        return barcodeGroups.stream().map(barcodeGroup->aggregateProduct(barcodeGroup,allItems))
            .collect(Collectors.toList());
    }

    public ItemGroup aggregateProduct(BarcodeGroup barcodeGroup, List<Item> allItems){
        Item item = mapBarcodeToDetails(barcodeGroup.getBarcode(),allItems);
        ItemGroup itemGroup = new ItemGroup(item, barcodeGroup.getCount(), barcodeGroup.getCount()*item.getPrice());
        return itemGroup;
    }

    public Item mapBarcodeToDetails(String Barcode, List<Item> allItems){
        return allItems.stream()
                .filter(item-> item.getBarcode().equals(Barcode))
                .collect(Collectors.toList()).get(0);    
    }

    public int sumCost(List<ItemGroup> itemGroups){
        return itemGroups.stream().map(itemGroup->itemGroup.getSubTotal()).reduce(0, Integer::sum);
    }
}
