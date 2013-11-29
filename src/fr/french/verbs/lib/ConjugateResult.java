package fr.french.verbs.lib;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jdom.Element;

public class ConjugateResult
{
    private String radical;
    private Element VerbEle;
    
    public ConjugateResult(Element e, String verbe)
    {
        VerbEle = e;
        
        Element term = VerbEle.getChild("infinitif");
        String EndOfVerb = term.getChild("present").getChild("p").getChild("i").getText();
        int radpos = verbe.lastIndexOf(EndOfVerb);
        radical = verbe.substring(0, radpos);
    }

    @SuppressWarnings("unchecked")
	public ArrayList<String> Conjug(String temps, String temps1)
    {
    	ArrayList<String> verbe = new ArrayList<String>();
        List<Element> conjigaison = VerbEle.getChild(temps).getChild(temps1).getChildren();
        Iterator<Element> it = conjigaison.iterator();
        for(int i = 0; it.hasNext(); i++)
        {
        	verbe.add(Conjug(temps, temps1, i));
        	it.next();
        }

        return verbe;
    }

    @SuppressWarnings("unchecked")
	public String Conjug(String temps, String temps1, int stop)
    {
        String VerbFinal = null;

        List<Element> conjigaison = VerbEle.getChild(temps).getChild(temps1).getChildren();
        Iterator<Element> it = conjigaison.iterator();
        int i = 0;
        while(it.hasNext() && VerbFinal == null)
        {
            Element item = (Element)it.next();
            if(stop == i)
            {
                String ter = item.getChildText("i");
                VerbFinal = radical + ter;
            }
            i++;
        }
        return VerbFinal;
    }
}
