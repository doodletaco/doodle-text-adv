import java.util.Scanner;

/**
 * The control class for a Text Adventure
 * 
 * @author dahlem.brian, doodletaco, and thomas french
 */
public class TextAdventure {
    private Scanner scan;
    private Player player;
    private Location[] room;
    
    /**
     * Create the world of the text adventure
     */
    public TextAdventure() {
        // Initialize the scanner to get user input
        scan = new Scanner(System.in);
        
        // Prompt for the player's name
        System.out.println("\fWelcome adventurer! Please state your name.");
        System.out.print("> ");
        
        String name = scan.nextLine();
        
        // Confirm the player's name and create the player
        System.out.println("Hello, " + name + ".");        
        player = new Player(name);
        
        //Prompt for player's pronouns
        System.out.println("Are you a boy or a girl?");
        System.out.print("> ");
        
        String pronoun = scan.nextLine();
        
        //Check to make sure a valid keyword is used, then set pronouns for game.
        String[] pronouns = {"boy", "girl"};
        boolean valid = false;
        while (!valid)
        {
            for (int i = 0; i < pronouns.length; i ++)
            {
                if (pronoun.equals(pronouns[i]))
                {
                    valid = true;
                }
            }
            if (!valid)
            {
                System.out.println("Your input is not valid, current options are: \"boy\" or \"girl\"");
                System.out.print("> ");
                
                pronoun = scan.nextLine();
            }
        }
        player.setPronoun(pronoun);
        System.out.println("Welcome to Östrivo, " + player.getPronoun(4) + ".");
        
        // Build the locations in this world
        makeRooms();
    }
    
    /**
     * Build all of the locations in this adventure
     */
    private void makeRooms()
    {
        // Create an array to hold all of the locations
        room = new Location[16];
        
        // Create the rooms
        room[0] = new Location("bed", 
                                 "You wake up, it is a new day. You have to get out of bed before you can do anything.");        
        room[1] = new Barracks();
        
        room[2] = new Location("a hallway", "To the east is the cafeteria, to the north is the office, and west is the exit.");
        
        room[3] = new Cafeteria();
        room[4] = new Office();
        
        room[5] = new Location("the courtyard", "The port is south of you, and you can see a boat ready to take you to Nav.");
        room[6] = new PortVaka();
        
        room[7] = new Nav();
        room[8] = new Market();
        room[9] = new Weaponry();
        room[10] = new Aviary();
        
        room[11] = new Ruins();
        room[12] = new Buko();
        room[13] = new Tavern();
        room[14] = new Dungeon();
        
        room[15] = new Location("a small boat", "You are on your way to the small town of Taos," +
                                "where rumors of Salame lie.");
        room[16] = new Bar();
        
        // Connect the rooms
        room[0].connectTo(room[1], "Up"); // You can get up out of bed
        room[1].connectTo(room[0], "Down"); // You can lay down into bed
        room[1].connectTo(room[2], "North"); //Leave into the hallway
        room[2].connectTo(room[3], "East"); //go to cafeteria
        room[3].connectTo(room[2], "West"); //go back
        room[2].connectTo(room[4], "North"); //go to office
        room[4].connectTo(room[2], "South"); //go back
        room[2].connectTo(room[5], "West"); //go to courtyard
        room[5].connectTo(room[2], "East"); //go back
        room[5].connectTo(room[6], "South"); //go to port
        room[6].connectTo(room[5], "North"); //go back
        room[6].connectTo(room[7], "South"); //go to nav
        //start of Thomas' part
        room[7].connectTo(room[8], "South"); //go to the market
        room[8].connectTo(room[7], "North"); // go back
        room[8].connectTo(room[7], "Die"); //if the player dies, go back to the last room
        room[8].connectTo(room[9],"West"); //go to weaponry
        room[9].connectTo(room[8], "East"); //go back from the weaponry
        room[8].connectTo(room[10], "East"); //go to aviary from market
        room[10].connectTo(room[8], "West"); //go back
        room[7].connectTo(room[11], "West"); //go to ruins
        room[11].connectTo(room[7], "East"); //go back from ruins
        room[7].connectTo(room[16], "West"); //go to tavern
        room[16].connectTo(room[7], "East"); //go back
        room[7].connectTo(room[12], "East"); //go to buko
        room[12].connectTo(room[13], "East"); //go to the tavern from buko
        room[13].connectTo(room[12], "West"); //go back
        room[12].connectTo(room[14], "North"); //go to dungeon
        room[14].connectTo(room[12], "South"); //go back
        
    }
    
    /**
     * The main loop for the text adventure
     */
    public void start() {
        Location currentLocation = room[0];
        player.moveTo(currentLocation);        

        while (true)
        {
            // Display a prompt
            System.out.print("> ");
            
            // Get the user's command
            String command = scan.nextLine();
            command = command.trim().toUpperCase();
            
            // Handle a quit command
            if (command.equals("QUIT") || command.equals("Q"))
            {
                break;
            }
            
            // Handle any other command
            String result = currentLocation.doCommand(command, player);
            if (result.equals("DEATH"))
            {
                player.moveTo(room[0]);
            }
            //if (player.hasItem("BONE"))
            //{ // Check if player has bone before opening the path to the left.
            //    room[8].connectTo(room[9],"Left"); //connect weaponry to the market
            //}
            ///if (player.hasItem("NOTE"))
            //{ //Check if player has the note before opening the path to the ruins
            //    room[7].connectTo(room[11], "West");
            //}
            if (player.hasItem("STRANGEMAN"))
            { //check if you have talked to the strange man before dungeon
                room[12].connectTo(room[14], "North");
            }
            if (player.hasItem("EXCALIBUR"))
            { //Check if player has excalibur before moving on
                room[14].connectTo(room[12], "South");
            }
        
            // Update the currentLocation in case the player has moved due 
            // to the command
            currentLocation = player.getLocation();
            currentLocation.turnDone(player);
                        
            // Update the currentLocation in case the player has moved
            // after their turn ended
            currentLocation = player.getLocation();
        }
        
        // If the loop ends, quit.
        System.out.println("Safe travels.");
    }
    
    /**
     * The main entry point for the text adventure program.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // Create the adventure game
        TextAdventure game = new TextAdventure();
        
        // Start the game
        game.start();              
    }
}