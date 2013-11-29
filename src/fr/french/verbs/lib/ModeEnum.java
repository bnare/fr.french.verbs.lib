package fr.french.verbs.lib;

public enum ModeEnum
{
	INFINITIF("infinitif"),
	INDICATIF("indicatif"),
	CONDITIONNEL("conditionnel"),
	SUBJONCTIF("subjonctif"),
	IMPERATIF("imperatif"),
	PARTICIPE("participe");
	
	private String mode;
	
	private ModeEnum(String m)
	{
		mode = m;
	}
	
	public String getValue()
	{
		return mode;
	}
}
