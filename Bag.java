package task8;

import task7.Item;
import task7.ProductType;

public class Bag {
    private final int bagCapacity;
    private Item[] items;
    private int differentObj = 0; 
    private int quantityOfItems = 0; 

    public Bag(int bagCapacity) throws IllegalArgumentException {
        if (bagCapacity > 0){
            this.bagCapacity = bagCapacity;
        } else{
            throw new IllegalArgumentException("bag capacity can not be less than 1");
        }
        this.items = new Item[bagCapacity];
    }	//Constructor to set the bag capacity if the capacity value is valid - and the set the items

    public void removeAllItems(){
        items = new Item[bagCapacity];
        differentObj = 0;
        quantityOfItems = 0;
    }	//SET THE NUMBER OF DIFFERENT OBJECTS AND QUANTITY OF ITEMS TO ZERO

    public boolean putIn(Item item){
        if (item.getQuantity() + quantityOfItems <= bagCapacity){
            items[differentObj] = new Item(item.getType(), item.getQuantity(), item.getComment());
            quantityOfItems += item.getQuantity();
            differentObj++;
            return true;
        }
        return false;
    }	//IF THE QUANTITY OF THE ITEM DOES NOT EXCEED THE BAG CAPACITY - ADD THE ITEM TO THE BAG
    	//returns false if the remaining bag capacity does not allow us to put that many objects.
    
    public boolean remove(ProductType product){
        if (!typeIn(items, product)){ return false; }
        Item[] help = new Item[bagCapacity];
        int index = 0;
        int subtractTotalQuantity = 0;
        for (int i = 0; i < differentObj; i++){
            if (!items[i].getType().equals(product)) {
                help[index] = items[i];
                index++;
            }
            else { subtractTotalQuantity += items[i].getQuantity(); }
        }
        quantityOfItems -= subtractTotalQuantity;
        differentObj = index;
        items = help;
        return true;
    }	//remove from bag all items  of the indicated type.

    public boolean remove(ProductType product, int n){
        if (getTypeQuantity(product) < n){ return false; }
        else if (getTypeQuantity(product) == n){
            remove(product);
            return true;
        }
        int m = n;
        for (int i = differentObj-1; i >= 0; i--){
            if (m == 0) { break; }
            if (items[i].getType().equals(product)) {
                if (items[i].getQuantity() <= m) {
                    items[i].setComment("12#%025436^&89fj");
                    m -= items[i].getQuantity();
                } else {
                    items[i].setComment("12#%025436^&89fj:" + m);
                    m = 0;
                }
            }
        }
        Item[] help = new Item[bagCapacity];
        int index = 0;
        for (int i = 0; i < differentObj; i++){
            if (!items[i].getComment().contains("12#%025436^&89fj")){
                help[index] = items[i];
                index++;
            }
            else {
                if (items[i].getComment().contains(":")){
                    String comment = items[i].getComment();
                    int colonIndex = comment.indexOf(":");
                    int subtract =  Integer.parseInt(comment.substring(colonIndex+1));
                    items[i].remove(subtract);
                    items[i].removeComment();
                    help[index] = items[i];
                    index++;
                }
            }
        }
        quantityOfItems -= n;
        differentObj = index;
        items = help;
        return true;
    }	//reduce by "n" the number of items of the indicated type.

    private boolean typeIn(Item[] items, ProductType product){
        for (int i = 0; i < differentObj; i++){
            if (items[i].getType().equals(product)){ return true; }
        }
        return false;
    }

    private int getTypeQuantity(ProductType type){
        int quantity = 0;
        for (int i = 0; i < differentObj; i++){
            if (items[i].getType().equals(type)){
                quantity += items[i].getQuantity();
            }
        }
        return quantity;
    }

    public int getBagCapacity(){
        return bagCapacity;
    }
    public int getDifferentObj(){
        return differentObj;
    }
    public int getQuantityOfItems(){
        return quantityOfItems;
    }
    public Item[] getItems(){
        return items;
    }

    @Override
    public String toString(){
        String bag = "Bag capacity: " + bagCapacity + ", Items: {";
        if (differentObj > 0){
            for (int i = 0; i < differentObj; i++){
                bag = bag + "\n\t" + items[i].toString();
            }
        }
        return differentObj > 0 ? bag + "\n}" : bag + "}";
    }

    @Override
    public boolean equals(Object other){
        if (other == this) return true;
        if (!(other instanceof Bag)) return false;
        Bag otherBag = (Bag) other;
        if (bagCapacity != otherBag.getBagCapacity()) return false;
        if (differentObj != otherBag.getDifferentObj()) return false;
        for (int i = 0; i < differentObj; i++){
            int same = 0;
            for (int j = 0; j < differentObj; j++){
                Item[] otherItems = otherBag.getItems();
                if (items[i].equals(otherItems[j])){
                    same++;
                }
            }
            if (same == 0) return false;
        }
        return true;
    }

}
