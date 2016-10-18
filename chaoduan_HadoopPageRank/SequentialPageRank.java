        package Pagerank;
        
        import java.io.*;
import java.util.*;
import java.util.Map.Entry;
        
        public class SequentialPageRank {
            // adjacency matrix read from file
            private HashMap<Integer, ArrayList<Integer>> adjMatrix = new HashMap<Integer, ArrayList<Integer>>();
            // input file name
            private String inputFile = "pagerank.input";
            // output file name
            private String outputFile = "";
            // number of iterations
            private int iterations = 100;
            // damping factor
            private double df = 0.85;
            // number of URLs
            private int size = 0;
            // calculating rank values
            private HashMap<Integer, Double> rankValues = new HashMap<Integer, Double>();
            private ArrayList<String> list10 = new ArrayList<String>();
            private ArrayList<String> listAll = new ArrayList<String>();
            /**
             * Parse the command line arguments and update the instance variables. Command line arguments are of the form
             * <input_file_name> <output_file_name> <num_iters> <damp_factor>
             *
             * @param args arguments
             */
            public void parseArgs(String[] args) {
                    
                    int length = args.length;
                if (length <= 0) {
                    System.out.println("Enter Some String");
                }
                for (int i = 0; i < length; i++) {
                    //System.out.println(args[i]);
                    this.inputFile = args[0];
                    this.outputFile= args[1];
                    this.iterations = Integer.parseInt(args[2]);
                    this.df = Double.parseDouble(args[3]);
                   // this.iterations=Integer.parseInt(args[2]);;
                    //this.df=Integer.parseInt(args[3]);
                }
            }
        
            
        
            /**
             * Read the input from the file and populate the adjacency matrix
             *
             * The input is of type
             *
             0
             1 2
             2 1
             3 0 1
             4 1 3 5
             5 1 4
             6 1 4
             7 1 4
             8 1 4
             9 4
             10 4
             * The first value in each line is a URL. Each value after the first value is the URLs referred by the first URL.
             * For example the page represented by the 0 URL doesn't refer any other URL. Page
             * represented by 1 refer the URL 2.
             *
             * @throws java.io.IOException if an error occurs
             */
            public void loadInput() throws IOException {
                    {
                            FileInputStream in = new FileInputStream(inputFile);

                     int c = in.read();
                     while (c != -1) {
                             //System.out.print(c + "  ");
                      while (c != 13 && c != 10 && c != -1) {
                            if (c != 13 && c != 10 && c != -1) {
                                    int num = 0;
                                             while( c >= 48 && c <= 57) {
                                                     num = num * 10 + c - 48;
                                                     c = in.read();
                                             }
                                             
                                    int key = num;
                                    ArrayList<Integer> arr = new ArrayList<Integer>();
                                    while (c != 13 && c != 10 && c != -1) {
                                            if (c >= 48 && c <= 57) {
                                                    int numin = 0;
                                                             while( c >= 48 && c <= 57) {
                                                                     numin = numin * 10 + c - 48;
                                                                     c = in.read();
                                                             }
                                                    arr.add(numin);
                                            } else {
                                                    c = in.read();
                                            }
                                            //c = in.read();
                                    }
                                    this.adjMatrix.put(key, arr);
                            } else { 
                                    c = in.read();
                            }
                            //c = in.read();
                        }
                        c = in.read();
                     }
                   // System.out.println(adjMatrix.toString());
                  } 
            }
        
            /**
             * Do fixed number of iterations and calculate the page rank values. You may keep the
             * intermediate page rank values in a hash table.
             */
            public void calculatePageRank() {
                    
                    size = adjMatrix.size();
                    
                    double cons = (1.0-df)/adjMatrix.size();
                    
                    double v=1.0/adjMatrix.size();
                    for (int i=0; i<adjMatrix.size(); i++){
                            rankValues.put(i, v);
                            
                    }
                    
                    //System.out.print(rankValues);
          for(int ite=0; ite<iterations; ite++)          {
                    HashMap<Integer, Double> rankValues01 = new HashMap<Integer, Double>();
            for (int k=0; k<adjMatrix.size(); k++){
                    double sum = 0;
                    for (int i=0; i<adjMatrix.size(); i++){
                            
                            //System.out.println(adjMatrix.get(i));
                            for(int j = 0; j<adjMatrix.get(i).size();j++ ){
                                    //System.out.println(adjMatrix.get(i).get(j));
                                    if(adjMatrix.get(i).get(j)==k){
                                             
                                            sum= sum + rankValues.get(i)/adjMatrix.get(i).size();
                                            //System.out.println("sum= "+sum);
                                            //System.out.println("rankValues["+i+"]= "+rankValues.get(i));
                                            //System.out.println("Size= "+adjMatrix.get(i).size());
                                            //System.out.println("rankValues["+i+"]/Size ="+rankValues.get(i)/adjMatrix.get(i).size());
                                    }
                            }
                            rankValues01.put(k, df*sum+cons);
                            //System.out.println(rankValues01);
                    }
                    //System.out.println(rankValues);
            }
            for (int copy=0; copy<rankValues01.size(); copy++){        
                    rankValues.put(copy, rankValues01.get(copy));
                    
            }
            //System.out.println(rankValues);
          
          }
         
          
          
          
     // System.out.println(rankValues);  
     ArrayList<Entry<Integer, Double>> list_Data = new ArrayList<Map.Entry<Integer, Double>>(rankValues.entrySet());
   
      Collections.sort(list_Data, new Comparator<Map.Entry<Integer, Double>>()  {   
            public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2)  
            {  
             if(o2.getValue()!=null&&o1.getValue()!=null&&o2.getValue().compareTo(o1.getValue())>0){  
              return 1;  
             }else{  
              return -1;  
             }  
                
            }  
        });  
     // System.out.println(list_Data);  
      //System.out.println(adjMatrix);
     // System.out.println(list_Data.get(0).toString().charAt(0));
      for(int i =0;i<10; i++){
               ;
               list10.add(list_Data.get(i).toString());
               
       }
      for(int i =0;i<list_Data.size(); i++){
          ;
          listAll.add(list_Data.get(i).toString());
          
  }
      
            }
        
            /**
             * Print the pagerank values. Before printing you should sort them according to decreasing order.
             * Print all the values to the output file. Print only the first 10 values to console.
             *
             * @throws IOException if an error occurs
             */
            public void printValues() throws IOException {
                    
                    
                       //System.out.println(this.inputFile);
                   // System.out.println(this.outputFile);
                  //  System.out.println(this.iterations);
                  //  System.out.println(this.df);
                  //  System.out.println(rankValues.toString());
                    System.out.println("the Top 10 is \r\n"+list10);
                    try {
                            //What ever the file path is.
                                    File output = new File(outputFile);
                                    FileOutputStream out = new FileOutputStream(output);
                                    OutputStreamWriter opw = new OutputStreamWriter(out);    
                                    Writer w = new BufferedWriter(opw);
                                    w.write(
                                             "*********************************************\r\n"
                                            +"* The  assignment#1 in P434 class           *\r\n"
                                            +"* PageRank input Data                       *\r\n"
                                            +"*********************************************\r\n"
                                                         
                                                         + "["+outputFile+"]\r\n["+size+"num of urls]\r\n[6 of groups(Zhexu FAN & Chao Duan)] \r\n" 
                                                         + "e.g.: "                      
                                                         + "java PageRank "+" "+ inputFile+" "+" "+" " +"iterations = "+iterations+"\r\n "+"df = "+ df+"\r\n"
                                                         
                                                        +"***************\r\n"
                                                        +"*Top 10 is :  *\r\n"
                                                        +"***************\r\n"
                                                        + list10
                                                        +"\r\n "
                                                        +"********************\r\n"
                                                        +"*all o sorted url:* \r\n"
                                                        +"*********************\r\n"
                                                        +listAll
                                       
                                                    );
                                               
                                                                
                                                    
                                    w.close();
                                } catch (IOException e) {
                                    System.err.println("Problem writing to the output file");
                                }
            }
        
            
        
            public static void main(String[] args) throws IOException {
                    long t1=System.currentTimeMillis();
                    
                    
                    
                SequentialPageRank sequentialPR = new SequentialPageRank();
        
                sequentialPR.parseArgs(args);
                sequentialPR.loadInput();
                sequentialPR.calculatePageRank();
                sequentialPR.printValues();
                long t2=System.currentTimeMillis();
                    System.out.println((t2-t1)+"ms");
            }
        }

        
