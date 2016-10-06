
package markov;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Markov {
	public static int TAILLE_FICHIER;
	public static String res[];
	public HashMap<String, HashMap<String,Integer>> MatriceT;
	public ArrayList<String> SuppressionDoublons;
	public Markov(){
		
		MatriceT = new HashMap();
		SuppressionDoublons = new ArrayList<String>();
	}
	
	public int getNbOccurence(String mot){
		int cmpt = 0;
		for(int i=0;i<res.length;i++){
			if(res[i].equals(mot))
				cmpt++;
			
		}
		
		return cmpt;
		
		
	}
	
	//public String getDernierMotFichier(){
	//	return res[res.length-1];
	//}
	public int getNbOccurenceSuite(String mot1, String mot2){
		int cmpt = 0;
		for(int i=0;i<res.length;i++){
			if(res[i].equals(mot1)){
				if(res[i+1].equals(mot2)){
					cmpt++;
				}
			}
			
		}
		return cmpt;
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
			if(!SuppressionDoublons.contains(ligne))
				SuppressionDoublons.add(ligne);
				res[cmpt] = ligne;
				cmpt++;
					
		}
		
		br.close();
		res[0] = ".";
		res[TAILLE_FICHIER+1] = ".";
		System.out.println("Remplissage tableau fichier Ok");
			

		return res;
		
		
	}
	private void afficherliste() {
		// TODO Auto-generated method stub
		System.out.println(SuppressionDoublons.toString());
	}

public static void main(String[] args){
	Markov m = new Markov();
	try {
		m.charger_fichier("Beyonce.txt.traite.txt");
		//System.out.println(m.getNbOccurence("me"));
		//System.out.println(res[res.length-1]);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}



	
}
