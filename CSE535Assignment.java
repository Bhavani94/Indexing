import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class terms {
	String termname;
	int freq;
	public LinkedList<postings> postingsList= new LinkedList<postings>();
	public String getTermname() {
		return termname;
	}
	public void setTermname(String termname) {
		this.termname = termname;
	}
	public int getFreq() {
		return freq;
	}
	public void setFreq(int freq) {
		this.freq = freq;
	}
	public LinkedList<postings> getPostingsList() {
		return postingsList;
	}
	public void setPostingsList(LinkedList<postings> postingsList) {
		this.postingsList = postingsList;
	}
}

class postings implements Comparable<postings>{
	int postID;
	int frequency;
	
	public int compareTo(postings o) {
		int comparedSize = o.postID;
		if (this.postID > comparedSize) {
			return 1;
		} else if (this.postID == comparedSize) {
			return 0;
		} else {
			return -1;
		}
	}	
	public int getPostID() {
		return postID;
	}
	public void setPostID(int postID) {
		this.postID = postID;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

}

class frequencycomparator implements Comparator<terms> {
	public int compare(terms t1, terms t2) {
		if(t1.getFreq() < t2.getFreq()) {
			return 1;
		}
		else if(t1.getFreq()==t2.getFreq()) {
			return 0;
		}
		else
			return -1;
	}
 }

class PostingsFreq implements Comparable<PostingsFreq>{
	int postID;
	int frequency;
	
	public int compareTo(PostingsFreq o) {
		int comparedSize = o.frequency;
		if (this.frequency < comparedSize) {
			return 1;
		} else if (this.frequency == comparedSize) {
			return 0;
		} else {
			return -1;
		}
	}
	public int getPostID() {
		return postID;
	}
	public void setPostID(int postID) {
		this.postID = postID;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

}
public class CSE535Assignment {
	int condition = 0;
	
	// map structure to store terms with its postings
	Map<String, Map<String, LinkedList<Integer>>> postings;
	private LinkedList<terms> terms = new LinkedList<terms>();

	LinkedList<Integer> docIDList = new LinkedList<Integer>();
	Map<String, LinkedList<postings>> ListDocID = new HashMap<String, LinkedList<postings>>();
	Map<String, LinkedList<PostingsFreq>> ListByFreq = new HashMap<String, LinkedList<PostingsFreq>>();

	public void indexer(String fileName, String fileName1) throws Exception {
		FileReader inputFile = new FileReader(fileName);
		BufferedReader br = new BufferedReader(inputFile);
		String line;
		while ((line = br.readLine()) != null) {
			String[] part1 = line.split("\\\\c");
			String termname = part1[0];
			String[] part2 = part1[1].split("\\\\m");
			int freq = Integer.parseInt(part2[0]);
			part2[1] = part2[1].replace("[", "");
			part2[1] = part2[1].replace("]", "");
			part2[1] = part2[1].replace(" ", "");
			String[] part3 = part2[1].split(",");
			int docID = Integer.parseInt(part3[0].split("/")[0]);
			int occurance = Integer.parseInt(part3[0].split("/")[1]);
			LinkedList<postings> postings1 = new LinkedList<postings>();
			LinkedList<PostingsFreq> postings2 = new LinkedList<PostingsFreq>();
			
			getPostingObjectForLine(line, postings1,postings2);
			terms temp = new terms();
			temp.termname = termname;
			temp.freq = freq;
			terms.add(temp);
			postings temp1 = new postings();
			temp1.postID = docID;
			temp1.frequency = occurance;
			docIDList.add(docID);		
				ListDocID.put(termname, postings1); // order by docID	
				ListByFreq.put(termname, postings2); // order by decreasing term frequency				
		}
//		getPostings(fileName1);
//		TAATOR(fileName1);
//		MainTAATAND(fileName1);
//		MainDAAT(fileName1);
		br.close();		
	}
	
/**
 * This method prints document IDs in the increasing order of docID and by the order of decreasing term frequencies. It reads the file line by line and calls the functions to process them.
 * @param fileName1
 */
	
	private void getPostings(String fileName1) 
	{
		try {	    
			FileReader inputFile1 = new FileReader(fileName1);
			BufferedReader br1 = new BufferedReader(inputFile1);
			String line1;
			while ((line1 = br1.readLine()) != null) 
			{
				String[] words = line1.split(" ");
				for (String word : words) {
					outputlog("FUNCTION: getPostings " + word +"\n");
			    outputlog("Ordered by DocIDs: ");			
				for (postings post1 : ListDocID.get(word)) {
					outputlog(post1.getPostID()+ ", ");
				}
				outputlog("\n");
				outputlog("Ordered by TF:" );
					for (PostingsFreq post2 : ListByFreq.get(word)) {
						outputlog(post2.getPostID() + ", ");
					}
					outputlog("\n");
				}
				MainTAATAND(words);
				TAATOR(words);
				DAATAND(words);
				DAATOR(words);
			}
			br1.close();
			} 
			catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
	}
	/**
	 * This method performs Term at a time AND operation
	 * @param first
	 * @param second
	 * @return
	 */
	public LinkedList<PostingsFreq> TAATAND(LinkedList<PostingsFreq> first,LinkedList<PostingsFreq> second)
	{
		LinkedList<PostingsFreq> resultList=new LinkedList<PostingsFreq>();
		LinkedList<PostingsFreq> lst1=first;
		LinkedList<PostingsFreq> lst2=second;
		for(PostingsFreq pst:lst1){
			boolean flag=false;
//			LinkedList<PostingsFreq> resultList=new LinkedList<PostingsFreq>();
			for(PostingsFreq tmplst:lst2){
				compForTAATAND++;
				if(pst.getPostID()==tmplst.getPostID()){			
					flag=true;
					break;
				}
			}
			if(flag){
				resultList.add(pst);
			}
		}	
		return resultList ;
	}
/**
 * This method calls the TAAT AND function to perform the AND operation and print the output to the log file
 * @param words
 */
	private void MainTAATAND(String[] words) {
		try {
				outputlog("FUNCTION: termAtATimeAnd " + Arrays.toString(words).replace("[", "").replace("]", "").replace(",", " ")+"\n");
				int i=0;
				StringBuffer wordsperLine=new StringBuffer();
				LinkedList<PostingsFreq> lst2=new LinkedList<PostingsFreq>();
				LinkedList<PostingsFreq> finalList=new LinkedList<PostingsFreq>();
				//String word;
				long startTime=System.currentTimeMillis();
				for (String word : words) {
					wordsperLine.append(word).append(" AND ");
					if(i==0){
						i++;
						finalList.addAll(ListByFreq.get(word));
						Collections.sort(finalList);
					}
					else{
						lst2.addAll(ListByFreq.get(word));
						Collections.sort(lst2);
						finalList=TAATAND(finalList,lst2);
					}	
				}
				long endTime=System.currentTimeMillis();
				long totalTime= endTime-startTime;
				    StringBuffer buff=new StringBuffer();
					for (int k = 0; k < finalList.size(); k++) {
						for (int j = k + 1; j < finalList.size(); j++) {
							if (finalList.get(k).equals(finalList.get(j))) {
								finalList.remove(j);
								j--;
							}
						}
					}
				outputlog(Integer.toString(finalList.size()) + " number of documents found.\n");
				outputlog(String.valueOf(compForTAATAND) + " comparisons are made\n");
				outputlog(Long.toString(totalTime) + " seconds are used\n");
				outputlog("Result: ");
				ArrayList<Integer> out = new ArrayList<>();
				for (PostingsFreq post3: finalList)
					{
						out.add(post3.getPostID());
					}
				Collections.sort(out);
				for(Integer pid: out){
					buff.append(Integer.valueOf(pid) +", ");
				}
					outputlog(buff.toString() + "\n");
							} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
				    
/**
 * This function performs TAAT OR operation and writes output to the log file		    
 * @param words
 */
	public void TAATOR(String[] words){
		try {
				outputlog("FUNCTION: termAtATimeOr " + Arrays.toString(words).replace("[", "").replace("]", "").replace(",", " ")+"\n");
				int i=0;
				StringBuffer wordsperLine=new StringBuffer();
				LinkedList<PostingsFreq> lst=new LinkedList<PostingsFreq>();
				LinkedList<PostingsFreq> glbllst=new LinkedList<PostingsFreq>();
				//String word;
				int comparisons=0;
				long startTime=System.currentTimeMillis();
				for (String word : words) {
					wordsperLine.append(word).append(" OR ");
					
					if(i==0){
						i++;
						lst.addAll(ListByFreq.get(word));
						glbllst.addAll(ListByFreq.get(word));
						Collections.sort(lst);
						Collections.sort(glbllst);
						
					}
					else{
						LinkedList<PostingsFreq> templst=ListByFreq.get(word);
						for(PostingsFreq tmplst:templst){
						
							boolean flag=true;
									
							for(PostingsFreq pst:glbllst)
							{
								comparisons++;
								if(pst.getPostID()==tmplst.getPostID()){
									
									flag=false;	
								}
							}
							if(flag)
							{
								glbllst.add(tmplst);
							}
						}
					}
				}
				long endTime=System.currentTimeMillis();
			    long totalTime=endTime-startTime;
				Collections.sort(glbllst);
				for (int k = 0; k < glbllst.size(); k++) {
					for (int j = k + 1; j < glbllst.size(); j++) {
						if (glbllst.get(k).equals(glbllst.get(j))) {
							glbllst.remove(j);
							j--;
						}
					}
				}
				outputlog(Integer.toString(glbllst.size()) + " number of documents found.\n");
				outputlog(String.valueOf(comparisons) + " comparisons are made\n");
				outputlog(Long.toString(totalTime) + " seconds are used\n");
				outputlog("Result: ");
				
				StringBuffer buff=new StringBuffer();
				ArrayList<Integer> out = new ArrayList<>();
				for (PostingsFreq post3: glbllst)
				{
					out.add(post3.getPostID());
				}
				Collections.sort(out);
				for(Integer pid: out){
					buff.append(Integer.valueOf(pid) +", ");
				}
				outputlog(buff.toString()+"\n");
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
		
	public void DAATAND(String[] words) throws IOException{
		int checker=0;	
		outputlog("FUNCTION: docAtATimeAnd " + Arrays.toString(words).replace("[", "").replace("]", "").replace(",", " ")+"\n");
		long startTime=System.currentTimeMillis();
		int[] len=new int[words.length];
		int comparisons=0;
		StringBuffer wordsperLine=new StringBuffer();
		for(int i=0;i<words.length;i++){
            if(i==words.length-1){
            	wordsperLine.append(words[i]+":");
            }
            else{
            	wordsperLine.append(words[i]+" AND ");
            }
        }
        try{
            for(int i=0;i<words.length;i++){
                checker=ListDocID.get(words[i]).size();
            }
        }
        catch (NullPointerException e){
            System.out.println("terms not found");
            return;
        }
        
        ArrayList<ArrayList<Integer>> listarrlist = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> DAATANDList = new ArrayList<Integer>();
        for(int i=0;i<words.length;i++){
            len[i] = 0;
            ArrayList<Integer> arrlist = new ArrayList<Integer>();
            for(int j=0;j<ListDocID.get(words[i]).size();j++){
                arrlist.add(ListDocID.get(words[i]).get(j).postID);
            }
            listarrlist.add(arrlist);
        }
        int max=0;

        int flag=0;
        while(flag==0){
            int c=0;
            for(int i=0;i<listarrlist.size();i++){
            	comparisons=comparisons+1;
                if(listarrlist.get(i).get(len[i])>max){
                    max=listarrlist.get(i).get(len[i]);
                }
            }
            for(int i=0;i<listarrlist.size();i++){
                if(listarrlist.get(i).get(len[i])==max){
                    c=c+1;
                    comparisons=comparisons+1;
                    if(c==listarrlist.size()){
//                        doccount=doccount+1;
                    	DAATANDList.add(listarrlist.get(i).get(len[i]));
                        for(int k=0;k<listarrlist.size();k++){
                        	len[k]=len[k]+1;
                        	comparisons=comparisons+1;
                            if(len[k]==listarrlist.get(k).size()){
                                flag=1;
                                break;
                            }
                        }
                    }
                }
                if(flag==1){
                    break;
                }
                if(listarrlist.get(i).get(len[i])<max){
                	len[i]=len[i]+1;
                	comparisons=comparisons+1;
                    if(len[i]==listarrlist.get(i).size()){
                        flag=1;
                        break;
                    }
                }
                if(flag==1){
                    break;
                }
            }
        }
        long endTime=System.currentTimeMillis();
        long totalTime=endTime-startTime;
        StringBuffer buff=new StringBuffer();
		for (Integer post3: DAATANDList)
		{
			buff.append(post3).append(" ");
		}
		outputlog(Integer.toString(DAATANDList.size()) + " number of documents found.\n");
		outputlog(String.valueOf(comparisons) + " comparisons are made\n");
		outputlog(Long.toString(totalTime) + " seconds are used\n");
		outputlog("Result: " + buff.toString() + "\n");
	}

	 public void DAATOR(String[] word) throws IOException
	    {
			outputlog("FUNCTION: docAtATimeOr " + Arrays.toString(word).replace("[", "").replace("]", "").replace(",", " ")+"\n");
		 long startTime=System.currentTimeMillis();
	        int[] len=new int[word.length];
			int comparisons=0;
			LinkedList<postings> lp = new LinkedList<postings>();
	        int ind=0;
	        int tq1=-1;

	        for(int i=0;i<word.length;i++)
	        {
	            lp=ListDocID.get(word[i]);
	            if(lp!=null)
	            {
	                ind=ind+1;
	            }
	        }
	        String[] daquery= new String[ind];
	        for(int i=0;i<word.length;i++)
	        {
	            lp=ListDocID.get(word[i]);
	            if(lp!=null)
	            {
	                tq1=tq1+1;
	                daquery[tq1]=word[i];
	            }
	        }

	        ArrayList<ArrayList<Integer>> listarrlist = new ArrayList<ArrayList<Integer>>();
	        ArrayList<Integer> DAATORList = new ArrayList<Integer>();


	        for(int i=0;i<daquery.length;i++)
	        {
	            len[i] = 0;
	            ArrayList<Integer> arrlist = new ArrayList<Integer>();
	            for(int j=0;j<ListDocID.get(daquery[i]).size();j++)
	            {
	                arrlist.add(ListDocID.get(daquery[i]).get(j).postID);
	            }
	            listarrlist.add(arrlist);
	        }

	        int min=Integer.MAX_VALUE;

	        int flag=0;
	        int dup=0;
	        while(flag==0)
	        {
	            min=Integer.MAX_VALUE;
	            int c=0;
	            for(int i=0;i<listarrlist.size();i++)
	            {
	            	comparisons=comparisons+1;
	                if (len[i]<listarrlist.get(i).size()){
	                    if(listarrlist.get(i).get(len[i])<min){
	                        min=listarrlist.get(i).get(len[i]);
	                    }
	                }
	                else{
	                    c=c+1;
	                    if(c==listarrlist.size()){
	                        flag=1;
	                        break;
	                    }
	                }
	            }
	            if(flag==1){
	                break;
	            }
	            c=0;
	            
	            for(int i=0;i<listarrlist.size();i++){
	                if(len[i]<listarrlist.get(i).size()){
	                	comparisons=comparisons+1;
	                    if(listarrlist.get(i).get(len[i])==min){
	                        if(dup==0){
	                        	DAATORList.add(listarrlist.get(i).get(len[i]));
	                            len[i]=len[i]+1;
	                            dup=1;
	                        }
	                        else{
	                            len[i]=len[i]+1;
	                        }
	                    }
	                }
	                else{
	                    c=c+1;
	                    if(c==listarrlist.size()){
	                        flag=1;
	                        break;
	                    }
	                }
	            }
	            if(flag==1){
	                break;
	            }
	            dup=0;
	        }
	        long endTime=System.currentTimeMillis();
	        long totalTime=endTime-startTime;
	        StringBuffer buff=new StringBuffer();
	        for (Integer post3: DAATORList)
			{
				buff.append(post3).append(" ");
			}
			outputlog(Integer.toString(DAATORList.size()) + " number of documents found.\n");
			outputlog(String.valueOf(comparisons) + " comparisons are made\n");
			outputlog(Long.toString(totalTime) + " seconds are used\n");
			outputlog("Result: " + buff.toString() + "\n");
	    }

	
	/**
	 * This method returns the posting object for a line in the file.
	 * 
	 * @param line
	 */
	private void getPostingObjectForLine(String line, LinkedList<postings> postings1,LinkedList<PostingsFreq> posting2) {
		String innerString = line.substring(line.indexOf("[") + 1, line.indexOf("]"));
		innerString = innerString.replace(" ", "");

		String[] array = innerString.split(",");
		for (String str : array) {
			int i = 0;
			postings temp = new postings();
			PostingsFreq temp2 = new PostingsFreq();

			String[] arr2 = str.split("/");
			for (String str1 : arr2) {
				if (i == 0) {
					temp.setPostID(Integer.parseInt(str1));
					temp2.setPostID(Integer.parseInt(str1));

				} else {
					temp.setFrequency(Integer.parseInt(str1));
					temp2.setFrequency(Integer.parseInt(str1));
				}
				i++;
			}
			postings1.add(temp);
			posting2.add(temp2);

		}
		Collections.sort(postings1);
		Collections.sort(posting2);
	}
	
	
/**
 * This method computes and displays the top k terms
 * @param k
 * @throws IOException
 */
	public void getTopK(int k) throws IOException {
		LinkedList<terms> terms123 = terms;
		Collections.sort(terms123, new frequencycomparator());
		StringBuffer str = new StringBuffer();
		outputlog("FUNCTION: getTopk " + Integer.toString(k) + "\nResult: ");
		for (int i = 0; i < k; i++) {
			str.append(terms123.get(i).getTermname() + ", ");
		}
		outputlog(str.toString()+"\n");
	}
/**
 * This method writes output to the file
 * @param termsabc
 * @throws IOException
 */
	public void outputlog(String termsabc) throws IOException {
		try {
			File file = new File("E:\\output.txt");// new File(outputFileName);
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(termsabc);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private String outputFileName;
	public int compForTAATAND=0;
	public static void main(String[] args) throws Exception {
		String absolutePath = System.getProperty("user.dir") + "\\";
		String fileName = "E:\\term.idx";//absolutePath + args[0];
		String fileName1 = "E:\\sample_input.txt";//absolutePath + args[3];
		try {
			CSE535Assignment indexed = new CSE535Assignment();
			indexed.outputFileName = absolutePath + args[1];
			indexed.indexer(fileName, fileName1);
			indexed.getTopK(10);//(Integer.parseInt(args[2]));
			indexed.getPostings(fileName1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
