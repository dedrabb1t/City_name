package city_name;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class City_Name {
	
	
	static Node root = null;
	
	static Node currentPos =null;
	
	static class Node {
		
		HashMap<Character, Node> children = new HashMap<>();
		boolean isEndOfString;
		
		public HashMap<Character, Node> getChildren() {
			return children;
		}
		public void setChildren(HashMap<Character, Node> children) {
			this.children = children;
		}
		
		public boolean isEndOfString() {
			return isEndOfString;
		}
		
		public void setEndOfString(boolean isEndOfString) {
			this.isEndOfString = isEndOfString;
		}		
		
				
	}
	
	static void generateTrie(String word) {
		
		Node current = root;
		
		for(char c : word.toCharArray())
		{
			current = current.getChildren().computeIfAbsent(c, node->new Node());
			//System.out.println(current.getChildren()+" "+current.isEndOfString);
		}
		
		current.setEndOfString(true);
		
		
	}
	
	static int searchCity(String word)
	{
		
		Node current = currentPos;
		
		for(int x =0;x<word.length();x++)
		{
			Node temp = current.getChildren().get(word.charAt(x));
			//System.out.println(word.charAt(x));
			//System.out.println(temp);
			if(temp==null)
			{
				
				currentPos=root;
				return 0;
			
			}
				//System.out.println(temp.isEndOfString);
			current = temp;
		}
		
		if(current.isEndOfString)
		{
			if(current.children.containsKey(' '))
			{
				currentPos = current.children.get(' ');
				return 3;
			}
			currentPos=root;
			return 1;
		}
			
		else
			if(current.children.containsKey(' '))
			{
				currentPos = current.children.get(' ');
				return 2;
			}

		currentPos = root;
		return 0;
		
	}
	
	public static void main(String args[]) {
		
		root = new Node();
		
		currentPos=root;		

		try {
			Scanner sc = new Scanner(new File("E:\\aws-post-deplo\\city_name\\src\\city_name\\worldcitiesupdated.csv"));
			sc.useDelimiter(",");
			
			while(sc.hasNext())
			{
				
				String next = sc.next();
				//System.out.println(next+"word");
				generateTrie(next);
			}
			
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();
		
		String[] split = input.split("[ ,!.?]+");
		
		int status = 0;
		
		String op="";
		
		String tempBuffer="";
		
		for (String word : split) {
			
			status = searchCity(word);
			
			tempBuffer=tempBuffer+word;
			
			//System.out.println(word+" "+status+" "+op+" "+tempBuffer);
			
			
			
			if(status==1)
			{
				op=op+tempBuffer;
				System.out.println(op.trim());
				tempBuffer="";
				op="";
			}
			if(status==0)
			{
				System.out.println(op);
				op="";
				tempBuffer="";
			}
				
			if(status==2)
			{
				tempBuffer+=" ";
			}
				
			if(status == 3)
			{
				op+=tempBuffer+" ";
				
				tempBuffer="";
			}
			
			
		}
		
	}
}
