/**
 * A class representing the player in a Text Adventure
 * 
 * @author dahlem.brian
 */
public class Player {
    private String name;
    private Location room;
    private String[] inventory;
    
    private static final int NUM_AVAILABLE_ITEMS = 6; // How many items are available to collect?
    int pos = 0;
    /**
     * Prepare the player
     */
    public Player(String name) {
        this.name = name;
        this.inventory = new String[NUM_AVAILABLE_ITEMS];
        inventory[0] = "";
        inventory[1] = "";
        inventory[2] = "";
        inventory[3] = "";
        inventory[4] = "";
        inventory[5] = "";
    }
    
    /**
     * Teleport the player to a location
     */
    public void setLocation(Location newRoom) {
        this.room = newRoom;
    }
    
    /**
     * Move the player to a location
     */
    public void moveTo(Location newRoom) {
        if (room != null) {
            room.exit(this); // Leave the current room
        }
        
        this.room = newRoom; // Enter the new room
        this.room.enter(this);
    }
    
    /**
     * Determine the room the player is currently in
     */
    public Location getLocation() {
        return room;
    }
    
    /**
     * Determine the player's name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Add an item to the player's inventory if it isn't already there 
     */
    public void giveItem(String itemName) {
        if (!hasItem(itemName)) { // look through inventory, find an available slot and // put the item there
        for (int i = 0; !inventory.equals("") && i<inventory.length;i++) {
            pos = i;
        }
        inventory[pos] = itemName;
    }
}
    /**
     * Determine if an item is in the player's inventory
     */
    public boolean hasItem(String itemName) {
        for (int i = 0; i < inventory.length; i++) { // Look through the inventory, see if any matches itemName
        if (inventory[i].equals(itemName)) {
            return true;
        }        
    }
    return false;
}
}