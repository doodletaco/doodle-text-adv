/**
 * A class representing the player in a Text Adventure
 * 
 * @author dahlem.brian
 */
public class Player
{
    private String name;
    private String pronoun;
    private Location room;
    private String[] inventory;
    private String[] pronounList = new String[5];
    
    private static final int NUM_AVAILABLE_ITEMS = 6; // How many items are available to collect?
    int pos = 0;
    /**
     * Prepare the player
     */
    public Player(String name)
    {
        this.name = name;
        this.inventory = new String[NUM_AVAILABLE_ITEMS];
    }
    
    /**
     * Teleport the player to a location
     */
    public void setLocation(Location newRoom)
    {
        this.room = newRoom;
    }
    
    /**
     * Move the player to a location
     */
    public void moveTo(Location newRoom)
    {
        if (room != null)
        {
            room.exit(this); // Leave the current room
        }
        
        this.room = newRoom; // Enter the new room
        this.room.enter(this);
    }
    
    /**
     * Determine the room the player is currently in
     */
    public Location getLocation()
    {
        return room;
    }
    
    /**
     * Determine the player's name
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Set player's pronoun
     * Assigns an array depending on choice of gender,
     * 0 = lad/lass
     * 1 = nominative
     * 2 = accusative/dative
     * 3 = possessive
     * 
     * @param p A string which is either "boy" or "girl"
     */
    public void setPronoun(String p)
    {
        if (p.equals("boy"))
        {
            pronounList[0] = "lad";
            pronounList[1] = "he";
            pronounList[2] = "him";
            pronounList[3] = "his";
            pronounList[4] = "sir";
        }
        else
        {
            pronounList[0] = "lass";
            pronounList[1] = "she";
            pronounList[2] = "her";
            pronounList[3] = "hers";
            pronounList[4] = "ma'am";
        }
    }
    
    /**
     * Determine player's pronouns
     * 
     * @param c which case is to be used
     * @return the pronoun in the desired case
     */
    public String getPronoun(int c)
    {
        return pronounList[c];
    }
    
    /**
     * Add an item to the player's inventory if it isn't already there 
     */
    public void giveItem(String itemName)
    {
        if (!hasItem(itemName))
        {
            // look through inventory, find an available slot and
            // put the item there
            for (int i = 0; inventory != null && i < inventory.length; i++)
            {
                pos = i;
            }
            inventory[pos] = itemName;
        }
    }
    
    /**
     * Determine if an item is in the player's inventory
     */
    public boolean hasItem(String itemName)
    {
        for (int i = 0; i < inventory.length; i++)
        {
            // Look through the inventory
            //see if any matches itemName
            if (inventory[i] != null && inventory[i].equals(itemName))
            {
                return true;
            }        
        }
        return false;
    }
}