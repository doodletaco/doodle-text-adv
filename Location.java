/**
 *  A location the player can visit in the text adventure
 *  
 *  @author dahlem.brian
 */
public class Location {
    private String name;
    private String description;
    
    // Connections to other locations
    private Location north;
    private Location south;
    private Location east;
    private Location west;
    private Location up;
    private Location down;
    
    private String[] directions = {"North", "South", "East", "West",
                                    "Up", "Down"};
    
    /**
     * Initialize a simple location with a name and description
     */
    public Location(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    /**
     * Establish a connection to another location if the player moves in a
     * given direction.
     */
    public void connectTo(Location other, String direction) {
        switch (direction.toUpperCase().charAt(0)) {
            case 'N':
                north = other;
                break;
            case 'S':
                south = other;
                break;
            case 'E':
                east = other;
                break;
            case 'W':
                west = other;
                break;
            case 'U':
                up = other;
                break;
            case 'D':
                down = other;
                break;
        }
    }
    
    /**
     * This command is called whenever the player enters a command to
     * do something.
     */
    public String doCommand(String command, Player player) {
        // Format and break the command into individual words
        String[] words = command.split(" ");
        
        if (words.length >= 2
            && words[0].equals("GO")) {
            words[0] = words[1];
        }
        
        // Look at the first word to determine the command given
        switch (words[0]) {
            case "LOOK":
                look();
                break;
                
            case "NORTH":
            case "SOUTH":
            case "EAST":
            case "WEST":
            case "N":
            case "S":
            case "E":
            case "W":
            case "UP":
            case "DOWN":
            case "U":
            case "D":
                Location moveto = connectedLocation(words[0]);
                if (moveto == null) {
                    System.out.println("You cannot go that way.");
                }
                else {
                    player.moveTo(moveto);
                    return "MOVE";
                }
                break;
                
            case "HELP":
            case "?":
                help();
                return "HELP";
                
            default:
                System.out.println("I do not understand.");
        }
        
        return "";
    }
    
    /**
     * Get the location that the player would move to in the given direction,
     * null means it is not possible to move in that direction.
     */
    public Location connectedLocation(String direction) {
        switch (direction.toUpperCase().charAt(0)) {
            case 'N':
                return north;
            case 'S':
                return south;
            case 'E':
                return east;
            case 'W':
                return west;
            case 'U':
                return up;
            case 'D':
                return down;
        }
        
        return null;
    }

    /**
     * Get the name of this location
     * @return the name of this location
     */
    public String getName() {
        return name;
    }
    
    /**
     * Get the description of this location
     * @return the description of this location
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Called when the player enters this room from another room
     */
    public void enter(Player player) {
        look();
    }
    
    /**
     * Called when the player leaves this room
     */
    public void exit(Player player) {
        // Default to doing nothing
    }
    
    /**
     * Called when the player's current turn ends
     */
    public void turnDone(Player player) {
        // Default to doing nothing
    }
    
    /**
     * Called when the player LOOKs in this room
     */
    public void look() {
        System.out.println("You are in " + getName());
        System.out.println(getDescription());
    }
    
    /**
     * Called whenever the player asks for help
     */
    public void help() {
        System.out.println("Available options:");
        System.out.println("GO [direction]");
        
        // Print out all available directions
        for (String dir : directions) {
            if (connectedLocation(dir) != null) {
                System.out.println("    " + dir);
            }
        }
        
        System.out.println("LOOK");
        System.out.println("HELP");
        System.out.println("QUIT");
    }
}
