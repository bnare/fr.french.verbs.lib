package fr.french.verbs.lib;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class Verbe
{
	private List<Element> verbes;
	private Iterator<Element> verbesIterator;
	private List<Element> conjugaison;
	private Iterator<Element> conjugaisonIterator;
	
	private Element VerbEle;
	private String radical;
	
	public Verbe ()
	{
		try
		{
			verbes = ChargerFichier("/xml/verbes.xml");
			conjugaison = ChargerFichier("/xml/conjugaison.xml");
		}
		catch(Exception e)
        {
            e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private List<Element> ChargerFichier(String fichier) throws FileNotFoundException, JDOMException, IOException
    {
    	SAXBuilder sxb = new SAXBuilder();
    	Document document = sxb.build(getClass().getResourceAsStream(fichier));
    	Element racine = document.getRootElement();
    	return racine.getChildren();
    }
	
	private Element getElemConjugaison(final String verb)
	{
		String template = getTemplate(verb);
		if(template != null)
		{
	        Element result = null;
	        while(conjugaisonIterator.hasNext() && result == null)
	        {
	            Element verbe = conjugaisonIterator.next();
	            String att = verbe.getAttributeValue("name");
	            if(att.equals(template))
	            {
	                result = verbe;
	            }
	        }
	        return result;
		}
        else
        {
        	return null;
        }
    }
	
	private String getTemplate(final String inVerb)
    {
        String groupe = null;
        while(verbesIterator.hasNext() && groupe == null)
        {
            Element verbe = verbesIterator.next();
            String verbeinf = verbe.getChildText("i");
            if(verbeinf.equals(inVerb))
            {
            	groupe = verbe.getChildText("t");
            }
        }
        return groupe;
    }
	
	private String initConjugue(String verbe)
	{
		verbesIterator = verbes.listIterator();
		conjugaisonIterator = conjugaison.listIterator();
		VerbEle = getElemConjugaison(verbe);
		if(VerbEle != null)
		{
			Element term = VerbEle.getChild("infinitif");
	        String EndOfVerb = term.getChild("present").getChild("p").getChild("i").getText();
	        int radpos = verbe.lastIndexOf(EndOfVerb);
	        radical = verbe.substring(0, radpos);
	        return "parfait";
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Conjuguer un verbe
	 * @param verbe le verbe à conjuguer
	 * @param mode le mode de la conjugaison
	 * @param temps le temps de la conjugaison
	 * @return la liste du verbe conjugué ou null si le verbe n'existe pas dans le dictionnaire
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<String> conjuguer(final String verbe, final ModeEnum mode, final TempsEnum temps)
	{
		if(initConjugue(verbe) != null)
		{
			ArrayList<String> verbeConjugue = new ArrayList<String>();
	        List<Element> conjigaison = VerbEle.getChild(mode.getValue()).getChild(temps.getValue()).getChildren();
	        Iterator<Element> it = conjigaison.iterator();
	        while(it.hasNext())
	        {
	        	Element item = (Element)it.next();
	        	String ter = item.getChildText("i");
	            verbeConjugue.add(radical + ter);
	        }
	        return verbeConjugue;
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Conjuguer un verbe
	 * @param verbe le verbe à conjuguer
	 * @param mode le mode de la conjugaison
	 * @param temps le temps de la conjugaison
	 * @param pronom la personne à conjuguer
	 * @return le verbe conjugué ou null si le verbe n'existe pas dans le dictionnaire
	 */
	@SuppressWarnings("unchecked")
	public String conjuguer(final String verbe, final ModeEnum mode, final TempsEnum temps, final PronomEnum pronom)
	{
		if(initConjugue(verbe) != null)
		{
			String verbeConjugue = null;
			List<Element> conjigaison = VerbEle.getChild(mode.getValue()).getChild(temps.getValue()).getChildren();
	        Iterator<Element> it = conjigaison.iterator();
	        int i = 0;
	        while(it.hasNext() && verbeConjugue == null)
	        {
	            Element item = (Element)it.next();
	            if(pronom.getValue() == i)
	            {
	                String ter = item.getChildText("i");
	                verbeConjugue = radical + ter;
	            }
	            i++;
	        }
			return verbeConjugue;
		}
		else
		{
			return null;
		}
	}
	
	public ArrayList<String> getListeVerbes()
	{
		verbesIterator = verbes.listIterator();
		ArrayList<String> listeVerbes = new ArrayList<String>();
		while(verbesIterator.hasNext())
		{
			listeVerbes.add(verbesIterator.next().getChildText("i"));
		}
		return listeVerbes;
	}
}
