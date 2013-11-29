package fr.french.verbs.lib;

public enum TempsEnum
{
	PRESENT("present"),
	IMPARFAIT("imparfait"),
	FUTUR("futur"),
	PASSE_SIMPLE("passe-simple"),
	PASSE("passe");
	
	private String temps;
	
	private TempsEnum(String t)
	{
		temps = t;
	}
	
	public String getValue()
	{
		return temps;
	}
}
