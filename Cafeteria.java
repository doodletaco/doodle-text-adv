
/**
 * A location where you talk with a guard and get food. Can also talk to a soldier friend for
 * fun info about the world.
 *
 * @author doodletaco
 * @version 1.0
 */
public class Cafeteria extends Location
{
    // instance variables - replace the example below with your own
    private boolean food;
    private boolean guard;

    /**
     * Constructor for objects of class Barracks
     */
    public Cafeteria()
    {
        // initialize location
        super("the cafeteria", "The large room is filled with the laughter and din of happy people."
                + "\nYou sit next to James, your friend.");
        
        //initialize instance variables
        food = true;
        guard = true;
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
            case "GUARD":
                System.out.println("Hey, " + player.getName() + ". You have been assigned a task."
                + "\nGo to the office and talk to the Captain, as soon as you finish up here.");
                guard = false;
                result = "TALK";
                break;
                
            case "FOOD":
                player.giveItem("Rations");
                System.out.println("You successfully obtain breakfast and rations.");
                food = false;
                result = "TAKE";
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
        
        // Report presence of things
        if (food)
        {
            System.out.println("A woman stands at a counter, with breakfast and rations for" +
            "those who might need them.");
        }
        if (guard)
        {
            System.out.println("A guard seems to be staring you down, as if he needs to speak with you.");
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
        if (food)
        {
            // only display if there is an item to take
            System.out.println("TAKE [item]");
            if (food)
            {
                System.out.println("    Food");
            }
        }
        if (guard)
        {
            // only display if there is a person to talk to
            System.out.println("TALK [person]");
            if (guard)
            {
                System.out.println("    Guard");
            }
        }
    }
}