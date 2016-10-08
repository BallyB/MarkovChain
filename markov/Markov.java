
package markov;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Markov {
	public static int TAILLE_FICHIER;
	public static String res[];
	public HashMap<String, HashMap<String,HashMap<String,Integer>>> MatriceT;
//	public ArrayList<String> SuppressionDoublons;
	public Markov(){
		
		MatriceT = new HashMap<String, HashMap<String,HashMap<String,Integer>>>();
	//	SuppressionDoublons = new ArrayList<String>();
	}
	
	
	private void remplirMatrice() {
		
		HashMap<String, HashMap<String,Integer>> Occurences;
		HashMap<String, Integer> Occurences2;
		
		String mot1,mot2,mot3;
		for (int i = 0; i < res.length-2; i++) {
			mot1 = res[i];
			mot2 = res[i+1];
			mot3 = res[i+2];
		//	System.out.println("Je traite le triplet "+mot1+"  "+mot2+" "+mot3);
			Occurences = new HashMap<String,HashMap<String,Integer>>();
			Occurences2 = new HashMap<String,Integer>();
			
			if(MatriceT.containsKey(mot1)){
			//	System.out.println("Existe déja dans la grande"); 
				if(MatriceT.get(mot1).containsKey(mot2)){
				//	System.out.println("Existe déja dans la moyenne"); 
					if(MatriceT.get(mot1).get(mot2).containsKey(mot3)){
						
						MatriceT.get(mot1).get(mot2).put(mot3,MatriceT.get(mot1).get(mot2).get(mot3)+1);
					//	System.out.println("Existe déja dans la petite, j'incrémente, ca devient : "+MatriceT.get(mot1).get(mot2).get(mot3)); 
					}else{
					//	System.out.println("Existe pas dans la petite"); 
						MatriceT.get(mot1).get(mot2).put(mot3, 1);
								 
					//	System.out.println("J'ai dans MatriceT :"+mot1+" "+mot2+" "+mot3+"  "+MatriceT.get(mot1).get(mot2).get(mot3));
					}
					
				}else{
				//	System.out.println("Existe pas dans la moyenne"); 
					Occurences2.put(mot3, 1);
					MatriceT.get(mot1).put(mot2, Occurences2);
				//	System.out.println("J'ai dans MatriceT :"+mot1+" "+mot2+" "+mot3+"  "+MatriceT.get(mot1).get(mot2).get(mot3));
				}
			}else{
			//	System.out.println("Existe pas dans la grande"); 
				Occurences2.put(mot3, 1);
				Occurences.put(mot2, Occurences2);	 
				MatriceT.put(mot1, Occurences);
			//	System.out.println("J'ai dans MatriceT :"+mot1+" "+mot2+" "+mot3+"  "+MatriceT.get(mot1).get(mot2).get(mot3));
				
			}
					
			
		}
			
		
	}
	
	
	
	public float getProba(String mot1, String mot2, String mot3){
		int Sommedesoccurences=0;
		if(!MatriceT.get(mot1).containsKey(mot2))return 0;
		if(!MatriceT.get(mot1).get(mot2).containsKey(mot3))return 0;
		float ProbaTrigrammes = 0;
		for (String mapKey : MatriceT.get(mot1).get(mot2).keySet()) {
			// utilise ici hashMap.get(mapKey) pour accéder aux valeurs
			Sommedesoccurences += MatriceT.get(mot1).get(mot2).get(mapKey);
		}
		System.out.println(" Il y a "+MatriceT.get(mot1).get(mot2).get(mot3)+" occurences de "+mot1+" "+mot2+" "+mot3);
		System.out.println(" Il y a en tout "+Sommedesoccurences+" occurences de "+mot1+" "+mot2+" autrechose ");
		ProbaTrigrammes = (float)MatriceT.get(mot1).get(mot2).get(mot3)/(float)Sommedesoccurences;
		return ProbaTrigrammes;
	}
	
	public String[] charger_fichier(String dico) throws IOException{
		
		BufferedReader br = null;
		FileReader fn;
		FileReader fn2;
		int nbLines = 0;
		
			
		fn = new FileReader(dico);
		fn2 = new FileReader(dico);
		String ligne2;
		BufferedReader reader = new BufferedReader(fn2);
		while ((ligne2 = reader.readLine()) != null)
			nbLines++;
		reader.close();
		fn2.close();
			
		TAILLE_FICHIER = nbLines;
		res = new String[TAILLE_FICHIER+2];
		br = new BufferedReader(fn);
		System.out.println("Lecture fichier Ok");
			
		String ligne;
		int cmpt = 1;
		while((ligne = br.readLine()) != null){
			//if(!SuppressionDoublons.contains(ligne))
		//		SuppressionDoublons.add(ligne);
				res[cmpt] = ligne;
				cmpt++;
					
		}
		
		br.close();
	//	SuppressionDoublons.add(".");
		res[0] = ".";
		res[TAILLE_FICHIER+1] = ".";
		System.out.println("Remplissage tableau fichier Ok");
			

		return res;
		
		
	}

public static void main(String[] args){
	Markov m = new Markov();
	try {
		m.charger_fichier("Beyonce.txt.traite.txt");
		m.remplirMatrice();
		System.out.println(m.MatriceT.get("i").containsKey("want"));
		//m.MatriceT.get("i").get("want").get("to");
		System.out.println("Proba P(asked / oh, i) = "+m.getProba("i", "asked", "you"));
	//	System.out.println(m.MatriceT.get("loves").get("me"));
	//	System.out.println(m.MatriceT.get("they").containsKey("didn"));
	//	System.out.println(m.getNbOccurenceSuite("remember", "those", "days"));
		//System.out.println(res[res.length-1]);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}





	
}
