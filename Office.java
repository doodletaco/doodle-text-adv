
/**
 * Office to talk to the captain and get an important document to say you can exist.
 *
 * @author doodletaco
 * @version 1.0
 */
public class Office extends Location
{
    // instance variables - replace the example below with your own
    private boolean captain;

    /**
     * Constructor for objects of class Barracks
     */
    public Office()
    {
        // initialize location
        super("the office", "You knock on the door, and are permitted to enter by the captain."
                + "\nYou stand in front of his desk.");
        
        //initialize instance variables
        captain = true;
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
            && (words[0].equals("TAKE") || words[0].equals("GET") || words[0].equals("TALK")))
        {
            words[0] = words[1];
        }
        
        switch (words[0])
        {
            case "CAPTAIN":
                System.out.println("G'mornin, " + player.getPronoun(0) + " , glad to see ya.");
                if (captain)
                {
                    System.out.println("I need to talk to ya 'bout a job I got for you.");
                }
                    
                if (!player.hasItem("Rations"))
                {
                    System.out.println("Make sure you get rations before you leave, you will be travelling." +
                        "I will tell you about it when you have them.");
                }
                
                else
                {
                    System.out.println("I need you to head to Nav port in Inleda." +
                        "\nI'm gettin wind of a guy who I don't want to get more power." +
                        "\nGo there and get more information about Salame, and do what you must to make him no longer a threat." +
                        "\nThis note will give you authorization to do what you need. At least in our country." +
                        "\nEnjoy your trip, the boat is ready now.");
                    player.giveItem("AuthorizationNote");
                }
                
                captain = false;
                result = "TALK";
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
        
        if (captain)
        {
            // only display if there is a person to talk to
            System.out.println("TALK [person]");
            if (captain)
            {
                System.out.println("    Captain");
            }
        }
    }
}