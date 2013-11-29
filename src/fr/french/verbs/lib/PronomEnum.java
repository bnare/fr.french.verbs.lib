package fr.french.verbs.lib;

public enum PronomEnum
{
	JE(0),
	TU(1),
	IL(2),
	NOUS(3),
	VOUS(4),
	ILS(5);
	
	private int pronom;
	
	private PronomEnum(int p)
	{
		pronom = p;
	}
	
	public int getValue()
	{
		return pronom;
	}
}
