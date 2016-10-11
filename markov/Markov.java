
package markov;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Markov {
	public static int TAILLE_FICHIER;
	public static String res[];
	public HashMap<String, HashMap<String,HashMap<String,Integer>>> MatriceT;
	public Markov(){
		
		MatriceT = new HashMap<String, HashMap<String,HashMap<String,Integer>>>();
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
			Sommedesoccurences += MatriceT.get(mot1).get(mot2).get(mapKey);
		}
	//	System.out.println(" Il y a "+MatriceT.get(mot1).get(mot2).get(mot3)+" occurences de "+mot1+" "+mot2+" "+mot3);
	//	System.out.println(" Il y a en tout "+Sommedesoccurences+" occurences de "+mot1+" "+mot2+" autrechose ");
		ProbaTrigrammes = (float)MatriceT.get(mot1).get(mot2).get(mot3)/(float)Sommedesoccurences;
		return ProbaTrigrammes;
	}
	
	public String getMotEtatSuivant(String mot1, String mot2){
		int max = 0;
		String motsuivant = "";
		for (String mapKey : MatriceT.get(mot1).get(mot2).keySet()) {
			// utilise ici hashMap.get(mapKey) pour accéder aux valeurs
			if (MatriceT.get(mot1).get(mot2).get(mapKey) > max){
				max = MatriceT.get(mot1).get(mot2).get(mapKey);
				motsuivant = mapKey;
			}
		}
		
		return motsuivant;
		
	}
	
	private void ecrire_fichier() {
		
		File f = new File("chanson_genere.txt");
		 
		try
		{
		    PrintWriter pw = new PrintWriter (new BufferedWriter (new FileWriter (f)));
		    String mot1, mot2;
		    String motsuivant = "";
		    
			
			pw.print("remember"+" ");
			pw.print("those"+" ");
			mot2 = "those";
			mot1 = "remember";
			
			for(int i = 0; i < 50; i++){
				
				motsuivant = getMotEtatSuivant(mot1, mot2);
		        pw.print(motsuivant+" ");
		        mot1 = mot2;
		        mot2 = motsuivant;
		    }
		 
		    pw.close();
		}
		catch (IOException exception)
		{
		    System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
		}
		
	}
	public float getProbaMot(String mot){
		int resultat = 0;
		
			
		for (int i = 0; i < res.length; i++) {
			
		
			if(res[i].equals(mot)){
				resultat++;
			}
		}
		
		return (float)resultat/TAILLE_FICHIER;
	}
	
	private float getProbaSuite(String[] suites) {
		float probamot1 = getProbaMot(suites[0]);
		float probamot21 = getProbaBigramme(suites[0],suites[1]);
		float sommesProbaTrigrammes = 1;
		for (int i = 2; i < suites.length; i++) {
			sommesProbaTrigrammes *= (float)getProba(suites[i-2], suites[i-1], suites[i]);
		}
		sommesProbaTrigrammes *= (float)probamot1;
		sommesProbaTrigrammes *= (float)probamot21;
		return sommesProbaTrigrammes;
	}
	
	private float getProbaBigramme(String string1, String string2) {
		HashMap<String, HashMap<String,Integer>> big1;
		HashMap<String, Integer> big2;
		big1 = new HashMap<String,HashMap<String,Integer>>();
		big2 = new HashMap<String,Integer>();
		String mot1,mot2;
		for (int i = 0; i < res.length-2; i++) {
			big2 = new HashMap<String,Integer>();
			mot1 = res[i];
			mot2 = res[i+1];
			if(big1.containsKey(mot1)){
					if(big1.get(mot1).containsKey(mot2)){
						
						big1.get(mot1).put(mot2,big1.get(mot1).get(mot2)+1);
					}else{
						big1.get(mot1).put(mot2, 1);
					}
					
			}else{
					big2.put(mot2, 1);	 
					big1.put(mot1, big2);
					
			}
		}
		int Sommedesoccurences = 0;
		for (String mapKey : big1.get(string1).keySet()) {
			Sommedesoccurences += big1.get(string1).get(mapKey);
		}
		float ProbaBigrammes = (float)big1.get(string1).get(string2)/(float)Sommedesoccurences;
		return ProbaBigrammes;
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
				res[cmpt] = ligne;
				cmpt++;
					
		}
		
		br.close();
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
		
		m.ecrire_fichier();
		String[] suites = {"they","didn","t","even"};
		System.out.println("Probabilité de la suite de mot :"+m.getProbaSuite(suites));
		//System.out.println("Proba P(asked / oh, i) = "+m.getProba("oh", "i", "asked"));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}















	
}
