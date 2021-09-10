package tp.pr2.logic.multigames;

/**
 * The enum GameType contains all the kind of possible game rules, associated with each
 * respective GameRules.
 * */
public enum GameType {
	ORIG("2048, original version", "original", new Rules2048()),
	FIB("2048, Fibonacci version", "fib", new RulesFib()),
	INV("2048, inverse version", "inverse", new RulesInverse());
	
	private String userFriendlyName;
	private	String parameterName;
	private GameRules gameRules;
	
	/**
	 * The constructor for GameType.
	 * @param gr The gameRule associated with the enum component.
	 * @param friendly The game name, shown to the user
	 * @param param the name used to store the type of game internally.
	 */
	private GameType(String friendly, String param, GameRules gr)
	{
		userFriendlyName = friendly;
		parameterName = param;
		gameRules = gr;
	}
	
	/**
	 * Parses the given string to a gametype.
	 * @param param The string to parse
	 * @return The parsed GameType, or the 
	 */
	public static GameType parse(String param)
	{
		for	(GameType gameType : GameType.values()) 
		{
			if (gameType.parameterName.equals(param))
				return gameType;
		}
		return null;
	}
	
	/**
	 * Gets the set of rules associated with the enum component.
	 * @return The gameRule of the GameType.
	 */
	public GameRules getRules()
	{
		return gameRules;
	}
	
	/**
	 * @return The internal name of the GameType.
	 */
	public String externalise ()
	{
		return parameterName;
	}
	
	/**
	 * @return The name of the game as shown to the user. 
	 */
	public String toString()
	{
		return userFriendlyName;
	}
}