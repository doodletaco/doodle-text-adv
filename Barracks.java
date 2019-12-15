
/**
 * A location where you can speak with a friend, and grab your pack.
 *
 * @author doodletaco
 * @version 1.0
 */
public class Barracks extends Location
{
    // instance variables - replace the example below with your own
    private boolean friend;
    private boolean pack;

    /**
     * Constructor for objects of class Barracks
     */
    public Barracks()
    {
        // initialize location
        super("the barracks", "Most of the others have aready gotten up and are in the cafeteria"
              + "\non the East side of the base.");
        
        //initialize instance variables
        pack = true;
        friend = true;
    }

    /**
     * Handle a command that the player has typed
     */
    @Override
    public String doCommand(String command, Player player)
    {
        String result = null;        
        
        String[] words = command.split(" ");
        
        // If the user wants to pick up or take an object, the object's name
        // is the command
        if (words.length >= 3 
            && words[0].equals("PICK") && words[1].equals("UP"))
        {
           words[0] = words[2]; 
        }
        if (words.length >= 2
            && (words[0].equals("TAKE") || words[0].equals("GET") || words[0].equals("TALK")
                || words[0].equals("GO")))
        {
            words[0] = words[1];
        }
        
        switch (words[0])
        {
            // If the user picks up the pack
            case "PACK":
                player.giveItem("PACK");
                System.out.println("You grab your backpack. It has general supplies for missions.");
                pack = false;
                result = "TAKE";
                break;
                
            case "FRIEND":
                System.out.println("You exchange morning greetings and join with your friend,"
                                    + "\nready to get food. You can leave in the North exit.");
                friend = false;
                result = "TALK";
                break;
                
            case "NORTH":
                if (friend || pack)
                {
                    System.out.println("You can't go to the cafeteria yet, you have forgotten something.");
                    result = "BLOCK";
                }
                else
                {
                    player.moveTo(connectedLocation(words[0]));
                    result = "MOVE";
                }
                break;
                
            
        }
        // If we couldn't handle this command,
        if (result == null)
        {
            // Try out the default commands.
            result = super.doCommand(command, player);
        }
                
        return result;
    }
    
    /**
     * Called whenever the player looks
     */
    @Override
    public void look()
    {
        super.look(); // Use the location's look
        
        // If the player hasn't picked up the rock, report its presence
        if (pack)
        {
            System.out.println("Your pack is next to your bed");
        }
        if (friend)
        {
            System.out.println("Your friend waits for you to join him and go to the cafeteria.");
        }
    }
    
    /**
     * Called whenever the player asks for help
     */
    @Override
    public void help()
    {
        // Display generic commands
        super.help();
        
        // Add any other room specific commands here
        if (pack)
        {
            // only display if there is an item to take
            System.out.println("TAKE [item]");
            if (pack)
            {
                System.out.println("    Pack");
            }
        }
        if (friend)
        {
            // only display if there is a person to talk to
            System.out.println("TALK [person]");
            if (friend)
            {
                System.out.println("    Friend");
            }
        }
    }
}