import java.util.*;

public class ExtractNutrients {
	
	public static Map<String, Integer> ExtractNutr(List<String> input){
		Map<String, Integer> Facts = new HashMap<String, Integer>();
		
		for(String s: input) {
			String nutrient="";
			String value="";
			int val;
			int stop=0;
		
			for(int i=0; i<s.length();i++)
			{
				stop=i;
				char c = s.charAt(i);
				if(!Character.isDigit(c))
				{
					nutrient+=c;
				}
				else
					break;
			}
			
			// get value of nutrient
			int flag=0;
			for(int i=stop; i<s.length();i++)
			{
				char c=s.charAt(i);
				if(Character.isDigit(c) || c=='.')
				{
					value+=c;
					flag=1;
				}
				else if(c=='%')
				{
					value = "";
					flag=2;
				}
				else if(c==' ' && (flag==2 || flag==0))
					continue;
				else
					break;
			}
			val = Integer.parseInt(value);	
			
			if(nutrient.contains("rotien"))
				Facts.put("protien", val);
			else if(nutrient.contains("alorie") || nutrient.contains("nergy"))
				Facts.put("energy", val);
			else if(nutrient.contains("carb") || nutrient.contains("Carb"))
				Facts.put("carb", val);
			else if(nutrient.contains("fat") || nutrient.contains("Fat"))
				Facts.put("fat", val);
			else if(nutrient.contains("odium"))
				Facts.put("sodium", val);
			else if(nutrient.contains("erving") || nutrient.contains("per"))
				Facts.put("serving", val);
			else if(nutrient.contains("fiber") || nutrient.contains("Fiber"))
				Facts.put("fiber", val);
			
		}
		return Facts;	
	}	
}


