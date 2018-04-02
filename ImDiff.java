
import java.util.*;



public class ImDiff {
 
   static int count=0;
	/* Reduced row echelon form , the results have been checked to be correct*/
	public static void ReducingMatrix(ArrayList<int []> matrix, List<Set<String>> constants)
	{
		int lead=0;
		int rowCount=matrix.size();
		int colCount=matrix.get(0).length;
		int r,i=0;
		for(r =0; r<rowCount; r++)
		{
			if (colCount <=lead) return;
			i=r;
			while(matrix.get(i)[lead]==0)
			{
				i = i+1;
				if (rowCount ==i)
				{
					i=r;
					lead=lead+1;
					if(colCount==lead)
						return;
				}
			}
		
			/*Swap row i and r of matrix and constants*/
			if(i!=r)
			{
				int temp;
				Set<String> tstr;
				for(int j=0;j<colCount; j++)
				{
					temp = matrix.get(i)[j];
					matrix.get(i)[j] = matrix.get(r)[j];
					matrix.get(r)[j] = temp;
				}
				tstr= new HashSet<String>(constants.get(i));
				constants.get(i).clear();
				constants.get(i).addAll(constants.get(r));
				
				constants.get(r).clear();
				constants.get(r).addAll(tstr);
			}
			// If matrix[r][lead] is not 0 divide row r by M[r][lead], 
			// This step is not required since all entries are in {0,1}.
			for(i=0; i<rowCount; i++)
			{
				if(i!=r)
				{
					int temp=matrix.get(i)[lead];
					if(temp!=0)
					{
						/*multiplied row r by temp, this step is omitted since
						 * all entries are in {0,1} */
						
						/*subtract row r from row i*/
						for(int j=lead; j<colCount; j++)
							matrix.get(i)[j] = matrix.get(i)[j]^matrix.get(r)[j];
						/* the xor operation of r and i */
					    Xor(constants.get(i),constants.get(r));
					}
				}
			}
			lead=lead+1;
//			printMatrix(matrix,constants);
//			System.out.println();
//			System.out.println(r+"  -----------------------------");
//			System.out.println();
		}
		
	}
	public static void Xor(Set<String> a, Set<String> b) //a = a xor b
	{
		Set<String> tmp = new HashSet<String>(a);
	    tmp.retainAll(b);
	    a.addAll(b);
	    a.removeAll(tmp);
	}
	
	//由表iv对matrix和constants进行简化
	//若iv中的iv[i]=0，则对应的matrix第i列为0，若iv[i]！=“？”且iv[i]!="0",则对应的第i列中的1全变为0，且相应的constant的值异或iv[i]的值。
	
	public static void InitialVector(ArrayList<int []> matrix, List<Set<String>> constants, List<Set<String>> iv)
	{
		
		for(int i=0; i<iv.size(); i++)
		{
			if(iv.get(i).isEmpty())
			{
				for(int j=0; j<matrix.size(); j++) //将第i列变为0
					matrix.get(j)[i]=0;
			}
			else if((!iv.get(i).contains("?")) && (!iv.get(i).contains("*")))  //将第i行后面加上相应的常量
			{
				for(int j=0; j<matrix.size(); j++)
					if(matrix.get(j)[i]==1) 
					{
						matrix.get(j)[i]=0;
						/* constants[j] = constants[j] xor iv[i] */
					    Set<String> siv = new HashSet<String>();
					    siv.addAll(iv.get(i));
					    Xor(constants.get(j),siv);
					}
			}
		}
	}
	
		
	
	public static void printMatrix(ArrayList<int []> m, List<Set<String>> constants)
	{

		for(int i=0; i<m.size();i++){
			for(int j=0; j<m.get(0).length; j++)
			{
				System.out.print(m.get(i)[j]+" ");
			}
			
			System.out.println(constants.get(i));
		}
	}
	public static void printMatrix(ArrayList<int []> m)
	{

		for(int i=0; i<m.size();i++){
			for(int j=0; j<m.get(0).length; j++)
			{
				System.out.print(m.get(i)[j]+" ");
			}
			System.out.println();
		}
	}
	public static ArrayList<int []> copyMatrix(ArrayList<int []> src)
	{
		int row=src.size();
		int col=src.get(0).length;
		ArrayList<int []> re= new ArrayList<int []> (row);
		
		for(int i=0; i<row; i++)
		{
			int[] temp= new int[col];
			for(int j=0; j<col;j++)
				temp[j]=src.get(i)[j];
			re.add(temp);
				
		}	
		return re;
		
	}
	
    public static void main(String[] args) {
        //String[][] choices16 = GenChoices();
        //String[][] choices = GenChoicesFull8();
        String[][] choices1 = GenChoices4("a");
        String[][] choices2 = GenChoices4ab();
    
        for(int i=0; i<choices1.length; i++)
        {
        	for(int j=0; j<choices2.length; j++)
        	{
        		//TestGenCAST256(choices1[i],choices2[j],19);
        		//TestGenCHAM(choices1[i],choices2[j],19);
        		//TestMisty(choices2[i],choices2[j],4);
        		//TestSkipjack(choices1[i],choices2[j],16);
        		//TestFourCell(choices1[i],choices2[j],18);
        		//TestMars(choices1[i],choices2[j],11);
        		TestRC6(choices1[i],choices2[j],9);
        		//TestSMS4(choices1[i],choices2[j],11);
        		//TestMIBS(choices16[i],choices16[j],8);
        		//TestCamellia(choices16[i],choices16[j],8);
        		//TestLBlock(choices16[i],choices16[j],14);
        		//ArrayList<int []> matrix=copyMatrix(m);
        		//TestE2(matrix, choices16[i],choices16[j],r);
        		//TestSNAKE2(choices[i],choices[j],11);
        		//TestSkipjackG(choices[i],choices[j],16);
        		//System.out.print(choices[i][j]+" ");
        	}
        }
        
/*        for(int i=0; i<choices8.length/2; i++)
        {
        	for(int j=choices8.length/2; j<choices8.length; j++)
        	{
        		TestSNAKE2(choices8[i],choices8[j],11);
        	}
        }*/
        System.out.println("Done!");
	 

    }
	
    public static void TestCamellia(String[] plaintext, String[] ciphertext, int r)
   	{
       	int c=16*r+16; 
       	
       	int[][] mibs = new int[8*r][c]; 
       	int[][] P={
       			{1,0,1,1,0,1,1,1},
       			{1,1,0,1,1,0,1,1},
       			{1,1,1,0,1,1,0,1},
       			{0,1,1,1,1,1,1,0},
       			{1,1,0,0,0,1,1,1},
       			{0,1,1,0,1,0,1,1},
       			{0,0,1,1,1,1,0,1},
       			{1,0,0,1,1,1,1,0}       			
       	};
       	
       	//现将第0行写好，第i行则为第0行向右平移第i位
       	
       	for(int i=0; i<r; i++)
       	{
       		for(int k=0; k<8; k++)
       		{
	       		mibs[8*i+k][8*i+k]=1;
	       		mibs[8*i+k][8*i+16+k]=1;
	       		for(int j=0; j<8; j++)
	       			mibs[8*i+k][8*r+16+8*i+j]=P[k][j];
       		}
       		
       	}
       	ArrayList<int []> matrix = new ArrayList<int []>();
           for(int i=0; i<mibs.length; i++)
           	matrix.add(mibs[i]);
           
           //constants vector
           List<Set<String>> constants = new ArrayList<Set<String>>();   
           for(int i=0; i<8*r; i++)
              	constants.add(new HashSet<String>());
           
           //initial value vector
           String[] strIV = new String[c];
           for(int i=0; i<c; i++) strIV[i]="?";
           //对(x3,y0,y1,y2) 和  x(3+r), y(r),y(1+r), y(2+r) 进行初始化。

           for(int i=0; i<8; i++)
           {
        	   strIV[i+8] = plaintext[i];
        	   strIV[i] = plaintext[i+8];
           }
           for(int i=0; i<8; i++)
           {
        	   strIV[8*r+8+i] = ciphertext[i];
               strIV[8*r+i] = ciphertext[i+8];
           }
          
           
           
           List<Set<String>> iv = new ArrayList<Set<String>>();
           for(int i=0; i<strIV.length; i++)
           {
           	iv.add(new HashSet<String>());
           	if(strIV[i]!="0")
           		iv.get(i).add(strIV[i]);
    
           }
           
         //  printMatrix(matrix,constants);
           
         	
           boolean ok=IDMIBS(matrix,constants,iv,r);
           if(ok)
           {
        	count++;            
           System.out.println(count+" Camellia r="+r+" plaintext = " + Arrays.asList(plaintext) + " ciphertext = " + Arrays.asList(ciphertext)+  " : "+ok);
           }
   	}
    
    public static void TestMIBS(String[] plaintext, String[] ciphertext, int r)
   	{
       	int c=16*r+16; 
       	
       	int[][] mibs = new int[8*r][c]; 
       	int[][] P={
       			{1,1,0,1,1,0,1,1},
       			{0,1,1,1,1,1,1,0},
       			{1,1,1,0,1,1,0,1},
       			{0,1,1,1,0,0,1,1},
       			{1,0,1,1,1,0,0,1},
       			{1,1,0,1,1,1,0,0},
       			{1,1,1,0,0,1,1,0},
       			{1,0,1,1,0,1,1,1}       			
       	};
       	
       	//现将第0行写好，第i行则为第0行向右平移第i位
       	
       	for(int i=0; i<r; i++)
       	{
       		for(int k=0; k<8; k++)
       		{
	       		mibs[8*i+k][8*i+k]=1;
	       		mibs[8*i+k][8*i+16+k]=1;
	       		for(int j=0; j<8; j++)
	       			mibs[8*i+k][8*r+16+8*i+j]=P[k][j];
       		}
       		
       	}
       	ArrayList<int []> matrix = new ArrayList<int []>();
           for(int i=0; i<mibs.length; i++)
           	matrix.add(mibs[i]);
           
           //constants vector
           List<Set<String>> constants = new ArrayList<Set<String>>();   
           for(int i=0; i<8*r; i++)
              	constants.add(new HashSet<String>());
           
           //initial value vector
           String[] strIV = new String[c];
           for(int i=0; i<c; i++) strIV[i]="?";
           //对(x3,y0,y1,y2) 和  x(3+r), y(r),y(1+r), y(2+r) 进行初始化。

           for(int i=0; i<8; i++)
           {
        	   strIV[i+8] = plaintext[i];
        	   strIV[i] = plaintext[i+8];
           }
           for(int i=0; i<8; i++)
           {
        	   strIV[8*r+8+i] = ciphertext[i];
               strIV[8*r+i] = ciphertext[i+8];
           }
          
           
           
           List<Set<String>> iv = new ArrayList<Set<String>>();
           for(int i=0; i<strIV.length; i++)
           {
           	iv.add(new HashSet<String>());
           	if(strIV[i]!="0")
           		iv.get(i).add(strIV[i]);
    
           }
           
         //  printMatrix(matrix,constants);
           
         	
           boolean ok=IDMIBS(matrix,constants,iv,r);
           if(ok)
           {
        	count++;            
           System.out.println(count+" MIBS r="+r+" plaintext = " + Arrays.asList(plaintext) + " ciphertext = " + Arrays.asList(ciphertext)+  " : "+ok);
           }
   	}
    
    
    public static boolean IDMIBS(ArrayList<int []> matrix, List<Set<String>> constants, List<Set<String>> iv,int r)
   	{
   		boolean flag = false;
   		boolean index=true;
   		
   		  //初始化iv表
   		//XYDistance表示 xi 与 yi之间的间隔
   		//xLength 表示有用的xi变量的个数 
           int xLength=8*r+8;
           int XYDistance=xLength;
         	for(int i=8;i<xLength; i++)
         	{
         			if(iv.get(i).isEmpty()&&!iv.get(XYDistance+i).isEmpty()&&!iv.get(XYDistance+i).contains("?")) 
         				return true;
         			else if(iv.get(i).isEmpty()) 
         				iv.get(XYDistance+i).clear();
         			else if(!iv.get(i).contains("?") && iv.get(XYDistance+i).contains("?"))
         			{ 
         				iv.get(XYDistance+i).clear();
         				iv.get(XYDistance+i).add("*");
         			}
         	}
         	for(int i=8*r+16;i<16*r+16; i++)
         	{
         		
         			int k=i-XYDistance;
         			if(iv.get(i).isEmpty()&&!iv.get(k).isEmpty()&&!iv.get(k).contains("?"))
         				return true;
         			else if(iv.get(i).isEmpty()) 
         				iv.get(k).clear();
         			else if(!iv.get(i).contains("?") && iv.get(k).contains("?"))
         			{ 
         				iv.get(k).clear();
         				iv.get(k).add("*");
         			}

         	}
   		
   		while(index)
   		{
   			//InitialVector(matrix,constants, iv);
   			for(int i=0; i<iv.size(); i++)
   			{
   				if(iv.get(i).isEmpty())
   				{
   					for(int j=0; j<matrix.size(); j++) //将第i列变为0
   						matrix.get(j)[i]=0;
   				}
   				else if((!iv.get(i).contains("?")) && (!iv.get(i).contains("*")))  //将第i行后面加上相应的常量
   				{
   					for(int j=0; j<matrix.size(); j++)
   						if(matrix.get(j)[i]==1) 
   						{
   							matrix.get(j)[i]=0;
   							/* constants[j] = constants[j] xor iv[i] */
   						    Set<String> siv = new HashSet<String>();
   						    siv.addAll(iv.get(i));
   						    Xor(constants.get(j),siv);
   						}
   				}
   			}
   			//对matrix和constants进行三角式变换
   			ReducingMatrix( matrix,  constants);
   			//查看矩阵是否有解
   			for(int i=matrix.size()-1; i>=0; i--)
   			{
   				int tmp=0;
   				for(int j=0;j<matrix.get(0).length;j++)
   					tmp+=matrix.get(i)[j];
   				if(tmp==0 && !constants.get(i).isEmpty()) //方程无解  
   					return true;
   			}
   			
   			//由矩阵对iv表进行更新, 对矩阵每一行进行检查，看是否由一个1的
   			index = false;
   			for(int i=0; i<matrix.size(); i++)
   			{
   				int oneIndex=0, tmp=0;
   				for(int j=0; j<matrix.get(i).length; j++)
   				{
   					if(matrix.get(i)[j]==1)
   						oneIndex=j;
   					tmp+=matrix.get(i)[j];
   				}
   				if(tmp==1) //This row has exactly one '1' entry, and the index is oneIndex
   				{
   					if(oneIndex < 8*r+16) 
   					{
   						Set<String> stmp= new HashSet<String>(constants.get(i));
   						Xor(stmp,iv.get(oneIndex));
   						if(stmp.size()==1 &&!stmp.contains("?") ) //contradiction
   							return true;
   						else if(oneIndex >7 && oneIndex<8*r+8  && iv.get(oneIndex).size()==1 && iv.get(oneIndex).contains("?")) //iv[oneIndex]=="?"
   						{
   							if(constants.get(i).isEmpty())
   							{
   								if(iv.get(XYDistance+oneIndex).size()==1 && iv.get(XYDistance+oneIndex).contains("*"))//Contradiction
   								{
   									return true;
   								}
   								iv.get(oneIndex).clear();
   								iv.get(XYDistance+oneIndex).clear();
   								index=true;
   							}
   							else if(constants.get(i).size()==1)
   							{
   								iv.get(oneIndex).clear();
   								iv.get(oneIndex).addAll(constants.get(i));
   								iv.get(XYDistance+oneIndex).clear();
   								iv.get(XYDistance+oneIndex).add("*");
   								index=true;
   							}
   						}

   					}
   					else if(oneIndex >= 8*r+16) 
   					{
   						Set<String> stmp= new HashSet<String>(constants.get(i));
   						Xor(stmp,iv.get(oneIndex));
   						if(stmp.size()==1 &&!stmp.contains("?") ) //contradiction
   							return true;
   						else if(iv.get(oneIndex).size()==1 && iv.get(oneIndex).contains("?")) //iv[oneIndex]=="?"
   						{
   							if(constants.get(i).isEmpty())
   							{
   								iv.get(oneIndex).clear(); //modify Yi
   								iv.get(oneIndex-XYDistance).clear(); //modify Xi
   								index=true;
   							}
   						}
   					}
   					

   				
   				}
   			}
   			
   			
   			
   		}
   		
   		return flag;
   	}	
    
    
    public static void TestLBlock(String[] plaintext, String[] ciphertext, int r)
   	{
       	int c=16*r+16; 
       	
       	int[][] lblock = new int[8*r][c]; 
       	int[][] P={
       			{0,1,0,0,0,0,0,0},
       			{0,0,0,1,0,0,0,0},
       			{1,0,0,0,0,0,0,0},
       			{0,0,1,0,0,0,0,0},
       			{0,0,0,0,0,1,0,0},
       			{0,0,0,0,0,0,0,1},
       			{0,0,0,0,1,0,0,0},
       			{0,0,0,0,0,0,1,0}       			
       	};
       	
       	//现将第0行写好，第i行则为第0行向右平移第i位
       	
       	for(int i=0; i<r; i++)
       	{
       		for(int k=0; k<8; k++)
       		{
	       		lblock[8*i+k][8*i+(k+2)%8]=1;
	       		lblock[8*i+k][8*i+16+k]=1;
	       		for(int j=0; j<8; j++)
	       			lblock[8*i+k][8*r+16+8*i+j]=P[k][j];
       		}
       		
       	}
       	ArrayList<int []> matrix = new ArrayList<int []>();
           for(int i=0; i<lblock.length; i++)
           	matrix.add(lblock[i]);
           
           //constants vector
           List<Set<String>> constants = new ArrayList<Set<String>>();   
           for(int i=0; i<8*r; i++)
              	constants.add(new HashSet<String>());
           
           //initial value vector
           String[] strIV = new String[c];
           for(int i=0; i<c; i++) strIV[i]="?";
           //对(x3,y0,y1,y2) 和  x(3+r), y(r),y(1+r), y(2+r) 进行初始化。

           for(int i=0; i<8; i++)
           {
        	   strIV[i+8] = plaintext[i];
        	   strIV[i] = plaintext[i+8];
           }
           for(int i=0; i<8; i++)
           {
        	   strIV[8*r+8+i] = ciphertext[i];
               strIV[8*r+i] = ciphertext[i+8];
           }
          
           
           
           List<Set<String>> iv = new ArrayList<Set<String>>();
           for(int i=0; i<strIV.length; i++)
           {
           	iv.add(new HashSet<String>());
           	if(strIV[i]!="0")
           		iv.get(i).add(strIV[i]);
    
           }
           
         //  printMatrix(matrix,constants);
           
         	
           boolean ok=IDMIBS(matrix,constants,iv,r);
           if(ok)
           {
        	count++;            
           System.out.println(count+" LBlock r="+r+" plaintext = " + Arrays.asList(plaintext) + " ciphertext = " + Arrays.asList(ciphertext)+  " : "+ok);
           }
   	}
    
    public static ArrayList<int []> E2Matrix(int r)
    {
    	int c=32*r+16; 
       	int[][] e2 = new int[16*r][c]; 
       	int[][] P={
       			{0,1,1,1,1,1,1,0},
       			{1,0,1,1,0,1,1,1},
       			{1,1,0,1,1,0,1,1},
       			{1,1,1,0,1,1,0,1},
       			{1,1,0,1,1,1,0,0},
       			{1,1,1,0,0,1,1,0},
       			{0,1,1,1,0,0,1,1},
       			{1,0,1,1,1,0,0,1}       			
       	};
    
/*      0 1 1 1 1 1 1 0
       	1 0 1 1 0 1 1 1
       	1 1 0 1 1 0 1 1
       	1 1 1 0 1 1 0 1
       	1 1 0 1 1 1 0 0
       	1 1 1 0 0 1 1 0
       	0 1 1 1 0 0 1 1
       	1 0 1 1 1 0 0 1*/
       	//填矩阵
       	//i=0
       	for(int k=0; k<8; k++)
   		{
       		e2[16*0+k][16*0+16+k]=1;
       		for(int j=0; j<8; j++)
       			e2[16*0+k][16*r+16*0+16+j]=P[k][j];
       		
       		e2[16*0+8+k][k]=1;
       		e2[16*0+8+k][16*1+8+k]=1;
       		e2[16*0+8+k][16*r+16+16*0+8+((1+k)%8)]=1;
       		
   		}

       	//i>0
       	for(int i=1; i<r; i++)
       	{
       		for(int k=0; k<8; k++)
       		{
	       		e2[16*i+k][16*i+16+k]=1;
	       		for(int j=0; j<8; j++)
	       			e2[16*i+k][16*r+16+16*i+j]=P[k][j];	       		
	       		
	       		e2[16*i+8+k][16*(i-1)+8+k]=1;
	       		e2[16*i+8+k][16*(i+1)+8+k]=1;
	       		e2[16*i+8+k][16*r+16+16*i+8+((1+k)%8)]=1;
       		}
       		
       	}
       	
       	ArrayList<int []> matrix = new ArrayList<int []>();
        for(int i=0; i<e2.length; i++)
        	matrix.add(e2[i]);
        return matrix;
    }
    public static void TestE2(ArrayList<int []> matrix, String[] plaintext, String[] ciphertext, int r)
   	{
    	   int c=32*r+16; 
       	           
           //constants vector
           List<Set<String>> constants = new ArrayList<Set<String>>();   
           for(int i=0; i<16*r; i++)
              	constants.add(new HashSet<String>());
           
           //initial value vector
           String[] strIV = new String[c];
           for(int i=0; i<c; i++) strIV[i]="?";
           //对(x3,y0,y1,y2) 和  x(3+r), y(r),y(1+r), y(2+r) 进行初始化。

           for(int i=0; i<8; i++)
           {
        	   strIV[i+8] = plaintext[i];
        	   strIV[i] = plaintext[i+8];
           }
           for(int i=0; i<8; i++)
           {
        	   strIV[16*r+8+i] = ciphertext[i];
               strIV[16*r+i] = ciphertext[i+8];
           }
          
           
           
           List<Set<String>> iv = new ArrayList<Set<String>>();
           for(int i=0; i<strIV.length; i++)
           {
           	iv.add(new HashSet<String>());
           	if(strIV[i]!="0")
           		iv.get(i).add(strIV[i]);
    
           }
           
         //  printMatrix(matrix,constants);
           
         	
           boolean ok=IDE2(matrix,constants,iv,r);
           if(ok)
           {
        	count++;            
           System.out.println(count+" E2 r="+r+" plaintext = " + Arrays.asList(plaintext) + " ciphertext = " + Arrays.asList(ciphertext)+  " : "+ok);
           }
   	}
    
    
    public static boolean IDE2(ArrayList<int []> matrix, List<Set<String>> constants, List<Set<String>> iv,int r)
   	{
   		boolean flag = false;
   		boolean index=true;
   		
   		  //初始化iv表
   		//XYDistance表示 xi 与 yi之间的间隔
   		//xLength 表示有用的xi变量的个数 
           int xLength=16*r+8;
           int XYDistance=xLength;
         	for(int i=8;i<xLength; i++)
         	{
         			if(iv.get(i).isEmpty()&&!iv.get(XYDistance+i).isEmpty()&&!iv.get(XYDistance+i).contains("?")) 
         				return true;
         			else if(iv.get(i).isEmpty()) 
         				iv.get(XYDistance+i).clear();
         			else if(!iv.get(i).contains("?") && iv.get(XYDistance+i).contains("?"))
         			{ 
         				iv.get(XYDistance+i).clear();
         				iv.get(XYDistance+i).add("*");
         			}
         	}
         	for(int i=16*r+16;i<32*r+16; i++)
         	{
         		
         			int k=i-XYDistance;
         			if(iv.get(i).isEmpty()&&!iv.get(k).isEmpty()&&!iv.get(k).contains("?"))
         				return true;
         			else if(iv.get(i).isEmpty()) 
         				iv.get(k).clear();
         			else if(!iv.get(i).contains("?") && iv.get(k).contains("?"))
         			{ 
         				iv.get(k).clear();
         				iv.get(k).add("*");
         			}

         	}
   		
   		while(index)
   		{
   			//InitialVector(matrix,constants, iv);
   			for(int i=0; i<iv.size(); i++)
   			{
   				if(iv.get(i).isEmpty())
   				{
   					for(int j=0; j<matrix.size(); j++) //将第i列变为0
   						matrix.get(j)[i]=0;
   				}
   				else if((!iv.get(i).contains("?")) && (!iv.get(i).contains("*")))  //将第i行后面加上相应的常量
   				{
   					for(int j=0; j<matrix.size(); j++)
   						if(matrix.get(j)[i]==1) 
   						{
   							matrix.get(j)[i]=0;
   							/* constants[j] = constants[j] xor iv[i] */
   						    Set<String> siv = new HashSet<String>();
   						    siv.addAll(iv.get(i));
   						    Xor(constants.get(j),siv);
   						}
   				}
   			}
   			//对matrix和constants进行三角式变换
   			ReducingMatrix( matrix,  constants);
   			//查看矩阵是否有解
   			for(int i=matrix.size()-1; i>=0; i--)
   			{
   				int tmp=0;
   				for(int j=0;j<matrix.get(0).length;j++)
   					tmp+=matrix.get(i)[j];
   				if(tmp==0 && !constants.get(i).isEmpty()) //方程无解  
   					return true;
   			}
   			
   			//由矩阵对iv表进行更新, 对矩阵每一行进行检查，看是否由一个1的
   			index = false;
   			for(int i=0; i<matrix.size(); i++)
   			{
   				int oneIndex=0, tmp=0;
   				for(int j=0; j<matrix.get(i).length; j++)
   				{
   					if(matrix.get(i)[j]==1)
   						oneIndex=j;
   					tmp+=matrix.get(i)[j];
   				}
   				if(tmp==1) //This row has exactly one '1' entry, and the index is oneIndex
   				{
   					if(oneIndex < 16*r+16) 
   					{
   						Set<String> stmp= new HashSet<String>(constants.get(i));
   						Xor(stmp,iv.get(oneIndex));
   						if(stmp.size()==1 &&!stmp.contains("?") ) //contradiction
   							return true;
   						else if(oneIndex >7 && oneIndex<16*r+8  && iv.get(oneIndex).size()==1 && iv.get(oneIndex).contains("?")) //iv[oneIndex]=="?"
   						{
   							if(constants.get(i).isEmpty())
   							{
   								if(iv.get(XYDistance+oneIndex).size()==1 && iv.get(XYDistance+oneIndex).contains("*"))//Contradiction
   								{
   									return true;
   								}
   								iv.get(oneIndex).clear();
   								iv.get(XYDistance+oneIndex).clear();
   								index=true;
   							}
   							else if(constants.get(i).size()==1)
   							{
   								iv.get(oneIndex).clear();
   								iv.get(oneIndex).addAll(constants.get(i));
   								iv.get(XYDistance+oneIndex).clear();
   								iv.get(XYDistance+oneIndex).add("*");
   								index=true;
   							}
   						}

   					}
   					else if(oneIndex >= 16*r+16) 
   					{
   						Set<String> stmp= new HashSet<String>(constants.get(i));
   						Xor(stmp,iv.get(oneIndex));
   						if(stmp.size()==1 &&!stmp.contains("?") ) //contradiction
   							return true;
   						else if(iv.get(oneIndex).size()==1 && iv.get(oneIndex).contains("?")) //iv[oneIndex]=="?"
   						{
   							if(constants.get(i).isEmpty())
   							{
   								iv.get(oneIndex).clear(); //modify Yi
   								iv.get(oneIndex-XYDistance).clear(); //modify Xi
   								index=true;
   							}
   						}
   					}
   					

   				
   				}
   			}
   			
   			
   			
   		}
   		
   		return flag;
   	}	 
    
    
/*   
 * SNAKE2 matrix
 *  1 1 1 1
    1 0 0 0
    1 1 0 0
    1 1 1 0*/
    
    
    public static void TestSNAKE2(String[] plaintext, String[] ciphertext, int r)
   	{
       	int c=8*r+8; 
       	
       	int[][] snake = new int[4*r][c]; 
       	int[][] P={
       			{1,1,0,1},
       			{1,0,0,0},
      			{1,1,0,0},
       			{1,1,1,0},
       	};
       	
       	//现将第0行写好，第i行则为第0行向右平移第i位
       	
       	for(int i=0; i<r; i++)
       	{
       		for(int k=0; k<4; k++)
       		{
	       		snake[4*i+k][4*i+k]=1;
	       		snake[4*i+k][4*i+8+k]=1;
	       		for(int j=0; j<4; j++)
	       			snake[4*i+k][4*r+8+4*i+j]=P[k][j];
       		}
       		
       	}
       	ArrayList<int []> matrix = new ArrayList<int []>();
           for(int i=0; i<snake.length; i++)
           	matrix.add(snake[i]);
           
           //constants vector
           List<Set<String>> constants = new ArrayList<Set<String>>();   
           for(int i=0; i<4*r; i++)
              	constants.add(new HashSet<String>());
           
           //initial value vector
           String[] strIV = new String[c];
           for(int i=0; i<c; i++) strIV[i]="?";
           //对(x3,y0,y1,y2) 和  x(3+r), y(r),y(1+r), y(2+r) 进行初始化。

           for(int i=0; i<4; i++)
           {
        	   strIV[i+4] = plaintext[i];
        	   strIV[i] = plaintext[i+4];
           }
           for(int i=0; i<4; i++)
           {
        	   strIV[4*r+4+i] = ciphertext[i];
               strIV[4*r+i] = ciphertext[i+4];
           }
          
           
           
           List<Set<String>> iv = new ArrayList<Set<String>>();
           for(int i=0; i<strIV.length; i++)
           {
           	iv.add(new HashSet<String>());
           	if(strIV[i]!="0")
           		iv.get(i).add(strIV[i]);
    
           }
           
         //  printMatrix(matrix,constants);
           
         	
           boolean ok=IDSNAKE2(matrix,constants,iv,r);
           if(ok)
           {
        	count++;            
           System.out.println(count+" SNAKE2 r="+r+" plaintext = " + Arrays.asList(plaintext) + " ciphertext = " + Arrays.asList(ciphertext)+  " : "+ok);
           }
   	}    
    public static boolean IDSNAKE2(ArrayList<int []> matrix, List<Set<String>> constants, List<Set<String>> iv,int r)
   	{
   		boolean flag = false;
   		boolean index=true;
   		
   		  //初始化iv表
   		//XYDistance表示 xi 与 yi之间的间隔
   		//xLength 表示有用的xi变量的个数 
           int xLength=4*r+4;
           int XYDistance=xLength;
         	for(int i=4;i<xLength; i++)
         	{
         			if(iv.get(i).isEmpty()&&!iv.get(XYDistance+i).isEmpty()&&!iv.get(XYDistance+i).contains("?")) 
         				return true;
         			else if(iv.get(i).isEmpty()) 
         				iv.get(XYDistance+i).clear();
         			else if(!iv.get(i).contains("?") && iv.get(XYDistance+i).contains("?"))
         			{ 
         				iv.get(XYDistance+i).clear();
         				iv.get(XYDistance+i).add("*");
         			}
         	}
         	for(int i=4*r+8;i<8*r+8; i++)
         	{
         		
         			int k=i-XYDistance;
         			if(iv.get(i).isEmpty()&&!iv.get(k).isEmpty()&&!iv.get(k).contains("?"))
         				return true;
         			else if(iv.get(i).isEmpty()) 
         				iv.get(k).clear();
         			else if(!iv.get(i).contains("?") && iv.get(k).contains("?"))
         			{ 
         				iv.get(k).clear();
         				iv.get(k).add("*");
         			}

         	}
   		
   		while(index)
   		{
   			//InitialVector(matrix,constants, iv);
   			for(int i=0; i<iv.size(); i++)
   			{
   				if(iv.get(i).isEmpty())
   				{
   					for(int j=0; j<matrix.size(); j++) //将第i列变为0
   						matrix.get(j)[i]=0;
   				}
   				else if((!iv.get(i).contains("?")) && (!iv.get(i).contains("*")))  //将第i行后面加上相应的常量
   				{
   					for(int j=0; j<matrix.size(); j++)
   						if(matrix.get(j)[i]==1) 
   						{
   							matrix.get(j)[i]=0;
   							/* constants[j] = constants[j] xor iv[i] */
   						    Set<String> siv = new HashSet<String>();
   						    siv.addAll(iv.get(i));
   						    Xor(constants.get(j),siv);
   						}
   				}
   			}
   			//对matrix和constants进行三角式变换
   			ReducingMatrix( matrix,  constants);
   			//查看矩阵是否有解
   			for(int i=matrix.size()-1; i>=0; i--)
   			{
   				int tmp=0;
   				for(int j=0;j<matrix.get(0).length;j++)
   					tmp+=matrix.get(i)[j];
   				if(tmp==0 && !constants.get(i).isEmpty()) //方程无解  
   					return true;
   			}
   			
   			//由矩阵对iv表进行更新, 对矩阵每一行进行检查，看是否由一个1的
   			index = false;
   			for(int i=0; i<matrix.size(); i++)
   			{
   				int oneIndex=0, tmp=0;
   				for(int j=0; j<matrix.get(i).length; j++)
   				{
   					if(matrix.get(i)[j]==1)
   						oneIndex=j;
   					tmp+=matrix.get(i)[j];
   				}
   				if(tmp==1) //This row has exactly one '1' entry, and the index is oneIndex
   				{
   					if(oneIndex < 4*r+4) 
   					{
   						Set<String> stmp= new HashSet<String>(constants.get(i));
   						Xor(stmp,iv.get(oneIndex));
   						if(stmp.size()==1 &&!stmp.contains("?") ) //contradiction
   							return true;
   						else if(oneIndex >3 && oneIndex<4*r+4  && iv.get(oneIndex).size()==1 && iv.get(oneIndex).contains("?")) //iv[oneIndex]=="?"
   						{
   							if(constants.get(i).isEmpty())
   							{
   								if(iv.get(XYDistance+oneIndex).size()==1 && iv.get(XYDistance+oneIndex).contains("*"))//Contradiction
   								{
   									return true;
   								}
   								iv.get(oneIndex).clear();
   								iv.get(XYDistance+oneIndex).clear();
   								index=true;
   							}
   							else if(constants.get(i).size()==1)
   							{
   								iv.get(oneIndex).clear();
   								iv.get(oneIndex).addAll(constants.get(i));
   								iv.get(XYDistance+oneIndex).clear();
   								iv.get(XYDistance+oneIndex).add("*");
   								index=true;
   							}
   						}

   					}
   					else if(oneIndex >= 4*r+8) 
   					{
   						Set<String> stmp= new HashSet<String>(constants.get(i));
   						Xor(stmp,iv.get(oneIndex));
   						if(stmp.size()==1 &&!stmp.contains("?") ) //contradiction
   							return true;
   						else if(iv.get(oneIndex).size()==1 && iv.get(oneIndex).contains("?")) //iv[oneIndex]=="?"
   						{
   							if(constants.get(i).isEmpty())
   							{
   								iv.get(oneIndex).clear(); //modify Yi
   								iv.get(oneIndex-XYDistance).clear(); //modify Xi
   								index=true;
   							}
   						}
   					}
   					

   				
   				}
   			}
   			
   			
   			
   		}
   		
   		return flag;
   	}	   

    
    public static void TestSkipjackG(String[] plaintext, String[] ciphertext, int r) //r>=3
	{
    	int col=10*r+8;
    	int row=6*r;
    	
    	int[][] skipjackg = new int[row][col]; 
    	
    	//先将第1-3行写好，第i行则为第0行向右平移第i位
    	//r>=3
		for (int i = 0; i < 3; i++) {
			for (int k = 0; k < 4; k++) {
				skipjackg[6 * i + k][6 * i + 6 + k] = 1;
				skipjackg[6 * i + k][6 * i + 8 + k] = 1;
				skipjackg[6 * i + k][6 * r + 8 + 4 * i + k] = 1;
			}

			skipjackg[6 * i + 4][6 * i + 10] = 1;
			skipjackg[6 * i + 4][6 * i + 12] = 1;
			skipjackg[6 * i + 4][2 * i] = 1;

			skipjackg[6 * i + 5][6 * i + 11] = 1;
			skipjackg[6 * i + 5][6 * i + 13] = 1;
			skipjackg[6 * i + 5][2 * i + 1] = 1;
		}
		// r>3
		for (int i = 3; i < r; i++) {
			for (int k = 0; k < 4; k++) {
				skipjackg[6 * i + k][6 * i + 6 + k] = 1;
				skipjackg[6 * i + k][6 * i + 8 + k] = 1;
				skipjackg[6 * i + k][6 * r + 8 + 4 * i + k] = 1;
			}

			skipjackg[6 * i + 4][6 * i + 10] = 1;
			skipjackg[6 * i + 4][6 * i + 12] = 1;
			skipjackg[6 * i + 4][6 * i - 8] = 1;

			skipjackg[6 * i + 5][6 * i + 11] = 1;
			skipjackg[6 * i + 5][6 * i + 13] = 1;
			skipjackg[6 * i + 5][6 * i - 7] = 1;
		}
    	
    	
    	
    	
    	
    	ArrayList<int []> matrix = new ArrayList<int []>();
        for(int i=0; i<skipjackg.length; i++)
        	matrix.add(skipjackg[i]);
        
        //constants vector
        List<Set<String>> constants = new ArrayList<Set<String>>();   
        for(int i=0; i<row; i++)
           	constants.add(new HashSet<String>());
        
        //initial value vector
        String[] strIV = new String[col];
        for(int i=0; i<col; i++) strIV[i]="?";
        //对(x3,y0,y1,y2) 和  x(3+r), y(r),y(1+r), y(2+r) 进行初始化。


		strIV[7] = plaintext[0];
		strIV[6] = plaintext[1];
		strIV[1] = plaintext[2];
		strIV[0] = plaintext[3];
		
		strIV[3] = plaintext[4];
		strIV[2] = plaintext[5];
		strIV[5] = plaintext[6];
		strIV[4] = plaintext[7];

        strIV[6*r+7] = ciphertext[0];
        strIV[6*r+6] = ciphertext[1];
        strIV[6*r-7] = ciphertext[2];
        strIV[6*r-8] = ciphertext[3];
        
        strIV[6*r-1] = ciphertext[4];
        strIV[6*r-2] = ciphertext[5];
        strIV[6*r+5] = ciphertext[6];
        strIV[6*r+4] = ciphertext[7];
        
        
        List<Set<String>> iv = new ArrayList<Set<String>>();
        for(int i=0; i<strIV.length; i++)
        {
        	iv.add(new HashSet<String>());
        	if(strIV[i]!="0")
        		iv.get(i).add(strIV[i]);
 
        }
        
       // printMatrix(matrix,constants);
        
      	
        boolean ok=IDSkipjackG(matrix,constants,iv,r);
        if(ok)
        System.out.println(++count +" Gen-SkipjackG r="+r+" plaintext = " + Arrays.asList(plaintext) + " ciphertext = " + Arrays.asList(ciphertext)+  " : "+ok);

	}
    
    public static boolean IDSkipjackG(ArrayList<int []> matrix, List<Set<String>> constants, List<Set<String>> iv,int r)
	{
		boolean flag = false;
		boolean index=true;
		//r>3
		  //初始化iv表
		//XYDistance表示 xi 与 yi之间的间隔
		//xLength 表示有用的xi变量的个数 
        int xLength=6*r+8;

		for (int i = 0; i < r; i++) {
			for (int j = 7; j < 11; j++) {
				if (iv.get(6*i+j).isEmpty() && !iv.get(xLength+4*i+j-7).isEmpty() && !iv.get(xLength+4*i+j-7).contains("?"))
					return true;
				else if (iv.get(6*i+j).isEmpty())
					iv.get(xLength+4*i+j-7).clear();
				else if (!iv.get(6*i+j).contains("?") && iv.get(xLength+4*i+j-7).contains("?")) {
					iv.get(xLength+4*i+j-7).clear();
					iv.get(xLength+4*i+j-7).add("*");
				}
			}
		}
      	for(int i=xLength;i<10*r+8; i++)
      	{
      			int m=(i-xLength)/4;
      			int n=(i-xLength)%4;
      			if(iv.get(i).isEmpty()&&!iv.get(6*m+7+n).isEmpty()&&!iv.get(6*m+7+n).contains("?")) return true;
      			else if(iv.get(i).isEmpty()) iv.get(6*m+7+n).clear();
      			else if(!iv.get(i).contains("?") && iv.get(6*m+7+n).contains("?"))
      			{ 
      				iv.get(6*m+7+n).clear();
      				iv.get(6*m+7+n).add("*");
      			}
      	}
		
		while(index)
		{
			InitialVector(matrix,constants, iv);
			//对matrix和constants进行三角式变换
			ReducingMatrix( matrix,  constants);
			//查看矩阵是否有解
			for(int i=matrix.size()-1; i>=0; i--)
			{
				int tmp=0;
				for(int j=0;j<matrix.get(0).length;j++)
					tmp+=matrix.get(i)[j];
				if(tmp==0 && !constants.get(i).isEmpty()) //方程无解  
					return true;
			}
			
			//由矩阵对iv表进行更新, 对矩阵每一行进行检查，看是否由一个1的
			index = false;
			for(int i=0; i<matrix.size(); i++)
			{
				int oneIndex=0, tmp=0;
				for(int j=0; j<matrix.get(i).length; j++)
				{
					if(matrix.get(i)[j]==1)
						oneIndex=j;
					tmp+=matrix.get(i)[j];
				}
				if(tmp==1) //This row has exactly one '1' entry, and the index is oneIndex
				{
					if(oneIndex < xLength) 
					{
						Set<String> stmp= new HashSet<String>(constants.get(i));
						Xor(stmp,iv.get(oneIndex));
						if(stmp.size()==1 &&!stmp.contains("?") ) //contradiction
							return true;
						else if(oneIndex%6>0 && oneIndex%6<5 && iv.get(oneIndex).size()==1 && iv.get(oneIndex).contains("?")) //iv[oneIndex]=="?"
						{
							int m=oneIndex/6-1;
								int n=oneIndex%6-1;
								int k=xLength+4*m+n;
							if(constants.get(i).isEmpty())
							{
								if(iv.get(k).size()==1 && iv.get(k).contains("*"))//Contradiction
								{
									return true;
								}
								iv.get(oneIndex).clear();
								iv.get(k).clear();
								index=true;
							}
							else if(constants.get(i).size()==1)
							{
								iv.get(oneIndex).clear();
								iv.get(oneIndex).addAll(constants.get(i));
								iv.get(k).clear();
								iv.get(k).add("*");
								index=true;
							}
						}

					}
					else if(oneIndex >= xLength) 
					{
						Set<String> stmp= new HashSet<String>(constants.get(i));
						Xor(stmp,iv.get(oneIndex));
						if(stmp.size()==1 &&!stmp.contains("?") ) //contradiction
							return true;
						else if(iv.get(oneIndex).size()==1 && iv.get(oneIndex).contains("?")) //iv[oneIndex]=="?"
						{
							if(constants.get(i).isEmpty())
							{
								int m=(oneIndex-xLength)/4;
				      			int n=(oneIndex-xLength)%4;
								iv.get(oneIndex).clear(); //modify Yi
								iv.get(6*m+7+n).clear(); //modify Xi
								index=true;
							}
						}
					}
					

				
				}
			}
			
			
			
		}
		
		return flag;
	} 
    
    
    public static String[][] GenChoices()
    {
    	String[][] str= new String[510][16];
    	
    	for(int i=0; i<255; i++) 
    		for(int j=0; j<8; j++)
    			str[i][j]="0";
    	for(int i=0; i<255; i++)
    	{
    		
    		for(int j=0; j<8; j++)
    		{
    			if((((i+1)>>j)&1)==1)
    				str[i][8+j]="a";
    			else
    				str[i][8+j]="0";
    		}
    	}
    	
    	
    	for(int i=255; i<510; i++) 
    		for(int j=0; j<8; j++)
    			str[i][j+8]="0";
    	for(int i=256; i<511; i++)
    	{
    		
    		for(int j=0; j<8; j++)
    		{
    			if((((i+1)>>j)&1)==1)
    				str[i-1][j]="b";
    			else
    				str[i-1][j]="0";
    		}
    	}

/*    	for(int i=0; i<510; i++)
    	{	
    		System.out.print("{");
    		for(int j=0; j<15; j++)
    		{
    			System.out.print("\""+str[i][j]+"\""+",");
    			
    		}
    		System.out.println("\""+str[i][15]+"\""+"},");
    	}*/
    	return str;
    }
    
    
    public static String[][] GenChoices8()
    {
    	String[][] str= new String[30][8];
    	
    	for(int i=0; i<15; i++) 
    		for(int j=0; j<4; j++)
    			str[i][j]="0";
    	for(int i=0; i<15; i++)
    	{
    		
    		for(int j=0; j<4; j++)
    		{
    			if((((i+1)>>j)&1)==1)
    				str[i][4+j]="a";
    			else
    				str[i][4+j]="0";
    		}
    	}
    	
    	
    	for(int i=15; i<30; i++) 
    		for(int j=0; j<4; j++)
    			str[i][j+4]="0";
    	for(int i=16; i<31; i++)
    	{
    		
    		for(int j=0; j<4; j++)
    		{
    			if((((i+1)>>j)&1)==1)
    				str[i-1][j]="b";
    			else
    				str[i-1][j]="0";
    		}
    	}

    	return str;
    }
  
    public static String[][] GenChoices4(String s)
    {
    	String[][] str= new String[15][4];
    	for(int i=1; i<16; i++)
    	{
    		
    		for(int j=0; j<4; j++)
    		{
    			if((((i)>>j)&1)==1)
    				str[i-1][j]=s;
    			else
    				str[i-1][j]="0";
    		}
    	}

    	return str;
    }   

    public static String[][] GenChoices4ab()
    {
    	String[][] str= new String[30][4];
    	String[][] a= GenChoices4("a");
    	String[][] b= GenChoices4("b");
    	for(int i=0; i<15; i++)
    	{
    		str[i] = a[i];    		
    	}
    	
    	for(int i=15; i<30; i++)
    	{ 
    		
    		str[i] = b[i-15];
    	}
    	return str;
    }   
    public static String[][] GenChoicesFull8()
    {
    	String[][] str= new String[255][8];
    	for(int i=1; i<256; i++)
    	{
    		
    		for(int j=0; j<8; j++)
    		{
    			if((((i)>>j)&1)==1)
    				str[i-1][j]="b";
    			else
    				str[i-1][j]="0";
    		}
    	}
    	
    	

    	return str;
    }
    
    
    /* Gen-CHAM
     *  x0           x1         x2         x3
	 *  x1           x2         x3        x4=x0+F(x1)
	 * y1=F(x1) 
	 * 1 轮就有 x0, x1, x2, x3, x4, y1 共 6 个变量
	 * 2 轮就有 x0, x1, x2, x3, x4, x5, y1, y2 共8个变量，每增加一轮，变量个数增加2个。
	 * 
	 */	
	
    public static void TestGenCHAM(String[] plaintext, String[] ciphertext, int r)
	{
    	//int r=19;// round number
    	//Generate r*(2r+4) matrix
    	//r+4 x_i and r y_i
    	int c=2*r+4; 
    	//CHAM is similar to CAST256, thus the variable we use is the same 
    	int[][] cast256 = new int[r][c]; 
    	
    	//现将第0行写好，第i行则为第0行向右平移第i位
    	//x4=x0+y1
    	for(int i=0; i<r; i++)
    	{
    		cast256[i][0+i] = 1;
    		cast256[i][4+i] = 1;
    		cast256[i][r+4+i] = 1;
    	}
    	ArrayList<int []> matrix = new ArrayList<int []>();
        for(int i=0; i<cast256.length; i++)
        	matrix.add(cast256[i]);
        
        //constants vector
        List<Set<String>> constants = new ArrayList<Set<String>>();   
        for(int i=0; i<r; i++)
           	constants.add(new HashSet<String>());
        
        //initial value vector
        String[] strIV = new String[c];
        for(int i=0; i<c; i++) strIV[i]="?";
        //对x3,x0,x1,x2 和 x(3+r), x(0+r), x(2+r), x(3+r)进行初始化。
        // The bug is here? 
/*        for(int i=0; i<4; i++) 
        {
        	strIV[i]=plaintext[i];
        	strIV[i+r]=ciphertext[i];
       	}*/
      /*Gen-CHAM is different from Gen-CAST256 here*/
        strIV[0]=plaintext[0];
        strIV[1]=plaintext[1];
        strIV[2]=plaintext[2];
        strIV[3]=plaintext[3];
        
        strIV[r] = ciphertext[0];
        strIV[r+1] = ciphertext[1];
        strIV[r+2] = ciphertext[2];
        strIV[r+3] = ciphertext[3];		
        
        List<Set<String>> iv = new ArrayList<Set<String>>();
        for(int i=0; i<strIV.length; i++)
        {
        	iv.add(new HashSet<String>());
        	if(strIV[i]!="0")
        		iv.get(i).add(strIV[i]);
 
        }

      //初始化iv表
        int  nRightBlock = 1; // Gen-CHAM is 1, while Gen-CAST is 3 
        int xLength=r+4;
      	for(int i=nRightBlock;i<nRightBlock+r; i++)
      	{
      			if(iv.get(i).isEmpty()) iv.get(i+xLength-nRightBlock).clear();
      			else if(!iv.get(i).contains("?"))
      			{ 
      				iv.get(i+xLength-nRightBlock).clear();
      				iv.get(i+xLength-nRightBlock).add("*");
      			}
      	}
        //Gen-CHAM is similar to Gen-CAST256
        boolean ok=IDCAST256(matrix,constants,iv,r+4,nRightBlock);
        if(ok)
        System.out.println("Gen-CHAM r="+r+" plaintext = " + Arrays.asList(plaintext) + " ciphertext = " + Arrays.asList(ciphertext)+  " : "+ok);


	}

    
    /* Gen-CAST256
     *  x3           x0         x1         x2
	 * x4=x0+F(x3)   x1         x2         x3
	 * y3=F(x3) 
	 * 1 轮就有 x0, x1, x2, x3, x4, y3 共 6 个变量
	 * 2 轮就有 x0, x1, x2, x3, x4, x5, y3, y4 共8个变量，每增加一轮，变量个数增加2个。
	 * 
	 */	
	
    public static void TestGenCAST256(String[] plaintext, String[] ciphertext, int r)
	{
    	//int r=19;// round number
    	//Generate r*(2r+4) matrix
    	//r+4 x_i and r y_i
    	int c=2*r+4; 
    	
    	int[][] cast256 = new int[r][c]; 
    	
    	//现将第0行写好，第i行则为第0行向右平移第i位
    	//x4=x0+y3
    	for(int i=0; i<r; i++)
    	{
    		cast256[i][0+i] = 1;
    		cast256[i][4+i] = 1;
    		cast256[i][r+4+i] = 1;
    	}
    	ArrayList<int []> matrix = new ArrayList<int []>();
        for(int i=0; i<cast256.length; i++)
        	matrix.add(cast256[i]);
        
        //constants vector
        List<Set<String>> constants = new ArrayList<Set<String>>();   
        for(int i=0; i<r; i++)
           	constants.add(new HashSet<String>());
        
        //initial value vector
        String[] strIV = new String[c];
        for(int i=0; i<c; i++) strIV[i]="?";
        //对x3,x0,x1,x2 和 x(3+r), x(0+r), x(2+r), x(3+r)进行初始化。
        // The bug is here? 
/*        for(int i=0; i<4; i++) 
        {
        	strIV[i]=plaintext[i];
        	strIV[i+r]=ciphertext[i];
       	}*/
      /*fix the bug*/
        strIV[0]=plaintext[1];
        strIV[1]=plaintext[2];
        strIV[2]=plaintext[3];
        strIV[3]=plaintext[0];
        
        strIV[r+3] = ciphertext[0];
        strIV[r] = ciphertext[1];
        strIV[r+1] = ciphertext[2];
        strIV[r+2] = ciphertext[3];
        		
        List<Set<String>> iv = new ArrayList<Set<String>>();
        for(int i=0; i<strIV.length; i++)
        {
        	iv.add(new HashSet<String>());
        	if(strIV[i]!="0")
        		iv.get(i).add(strIV[i]);
 
        }

      //初始化iv表
        int  nRightBlock = 3;
        int xLength=r+4;
      	for(int i=nRightBlock;i<xLength-1; i++)
      	{
      			if(iv.get(i).isEmpty()) iv.get(i+xLength-nRightBlock).clear();
      			else if(!iv.get(i).contains("?"))
      			{ 
      				iv.get(i+xLength-nRightBlock).clear();
      				iv.get(i+xLength-nRightBlock).add("*");
      			}
      	}
        
        boolean ok=IDCAST256(matrix,constants,iv,r+4,nRightBlock);
        if(ok)
        System.out.println("Gen-CAST256 r="+r+" plaintext = " + Arrays.asList(plaintext) + " ciphertext = " + Arrays.asList(ciphertext)+  " : "+ok);


	}
    public static boolean IDCAST256(ArrayList<int []> matrix, List<Set<String>> constants, List<Set<String>> iv, int xLength, int nRightBlock)
	{
		boolean flag = false;
		boolean index=true;
		while(index)
		{
			InitialVector(matrix,constants, iv);
			//对matrix和constants进行三角式变换
			ReducingMatrix( matrix,  constants);

			//查看矩阵是否有解
			for(int i=matrix.size()-1; i>=0; i--)
			{
				int tmp=0;
				for(int j=0;j<matrix.get(0).length;j++)
					tmp+=matrix.get(i)[j];
				if(tmp==0 && !constants.get(i).isEmpty()) //方程无解  
					return true;
			}
			
			//由矩阵对iv表进行更新, 对矩阵每一行进行检查，看是否由一个1的
			index = false;
			for(int i=0; i<matrix.size(); i++)
			{
				int oneIndex=0, tmp=0;
				for(int j=0; j<matrix.get(i).length; j++)
				{
					if(matrix.get(i)[j]==1)
						oneIndex=j;
					tmp+=matrix.get(i)[j];
				}
				if(tmp==1) //This row has exactly one '1' entry, and the index is oneIndex
				{
					if(oneIndex < xLength) 
					{
						Set<String> stmp= new HashSet<String>(constants.get(i));
						Xor(stmp,iv.get(oneIndex));
						if(stmp.size()==1 &&!stmp.contains("?") ) //contradiction
							return true;
						else if(iv.get(oneIndex).size()==1 && iv.get(oneIndex).contains("?")) //iv[oneIndex]=="?"
						{
							if(constants.get(i).isEmpty())
							{
								if(iv.get(oneIndex+xLength-nRightBlock).size()==1 && iv.get(oneIndex+xLength-nRightBlock).contains("*"))//Contradiction
								{
									return true;
								}
								iv.get(oneIndex).clear();
								iv.get(oneIndex+xLength-nRightBlock).clear();
								index=true;
							}
							else if(constants.get(i).size()==1)
							{
								iv.get(oneIndex).clear();
								iv.get(oneIndex).addAll(constants.get(i));
								iv.get(oneIndex+xLength-nRightBlock).clear();
								iv.get(oneIndex+xLength-nRightBlock).add("*");
								index=true;
							}
						}

					}
					else
					{
						Set<String> stmp= new HashSet<String>(constants.get(i));
						Xor(stmp,iv.get(oneIndex));
						if(stmp.size()==1 &&!stmp.contains("?") ) //contradiction
							return true;
						else if(iv.get(oneIndex).size()==1 && iv.get(oneIndex).contains("?")) //iv[oneIndex]=="?"
						{
							if(constants.get(i).isEmpty())
							{
								iv.get(oneIndex).clear(); //modify Yi
								iv.get(oneIndex-(xLength-nRightBlock)).clear(); //modify Xi
								index=true;
							}
						}
					}
					

				
				}
			}
			
			
			
		}
		
		return flag;
	}
    
    /* MISTY2
     *  x3           x0         x1         x2
	 * 
	 */	
	
	
    public static void TestMisty(String[] plaintext, String[] ciphertext, int r)
	{
    	
    	int c=2*r+2; 
    	
    	int[][] misty = new int[r][c]; 
    	
    	//现将第0行写好，第i行则为第0行向右平移第i位
    	//x4=x0+y3
    	for(int i=0; i<r; i++)
    	{
    		misty[i][1+i] = 1;
    		misty[i][r+i+1] = 1;
    		misty[i][r+i+2] = 1;
    	}
    	ArrayList<int []> matrix = new ArrayList<int []>();
        for(int i=0; i<misty.length; i++)
        	matrix.add(misty[i]);
        
        //constants vector
        List<Set<String>> constants = new ArrayList<Set<String>>();   
        for(int i=0; i<r; i++)
           	constants.add(new HashSet<String>());
        
        //initial value vector
        String[] strIV = new String[c];
        for(int i=0; i<c; i++) strIV[i]="?";
        //对(x1,y0) 和 x(1+r), y(r)进行初始化。

        strIV[0] = plaintext[0];
        strIV[r+1] = plaintext[1];
        strIV[r] = ciphertext[0];
        strIV[2*r+1] = ciphertext[1];
        
        List<Set<String>> iv = new ArrayList<Set<String>>();
        for(int i=0; i<strIV.length; i++)
        {
        	iv.add(new HashSet<String>());
        	if(strIV[i]!="0")
        		iv.get(i).add(strIV[i]);
 
        }
        
      //  printMatrix(matrix,constants);
        
      	
        boolean ok=IDMisty(matrix,constants,iv,r+1,r);
        if(ok)
        System.out.println("Misty r="+r+" plaintext = " + Arrays.asList(plaintext) + " ciphertext = " + Arrays.asList(ciphertext)+  " : "+ok);

    	
	}
	
    public static boolean IDMisty(ArrayList<int []> matrix, List<Set<String>> constants, List<Set<String>> iv, int xLength,int r)
	{
		boolean flag = false;
		boolean index=true;
		
		  //初始化iv表
        xLength=xLength+1;
      	for(int i=0;i<r; i++)
      	{
      			if(iv.get(i).isEmpty()&&!iv.get(i+xLength).isEmpty()&&!iv.get(i+xLength).contains("?")) return true;
      			else if(iv.get(i).isEmpty()) iv.get(i+xLength).clear();
      			else if(!iv.get(i).contains("?") && iv.get(i+xLength).contains("?"))
      			{ 
      				iv.get(i+xLength).clear();
      				iv.get(i+xLength).add("*");
      			}
      	}
      	for(int i=r+2;i<2*r+2; i++)
      	{
      			if(iv.get(i).isEmpty()&&!iv.get(i-xLength).isEmpty()&&!iv.get(i-xLength).contains("?")) return true;
      			else if(iv.get(i).isEmpty()) iv.get(i-xLength).clear();
      			else if(!iv.get(i).contains("?") && iv.get(i-xLength).contains("?"))
      			{ 
      				iv.get(i-xLength).clear();
      				iv.get(i-xLength).add("*");
      			}
      	}
		
		while(index)
		{
			InitialVector(matrix,constants, iv);
			//对matrix和constants进行三角式变换
			ReducingMatrix( matrix,  constants);
			//查看矩阵是否有解
			for(int i=matrix.size()-1; i>=0; i--)
			{
				int tmp=0;
				for(int j=0;j<matrix.get(0).length;j++)
					tmp+=matrix.get(i)[j];
				if(tmp==0 && !constants.get(i).isEmpty()) //方程无解  
					return true;
			}
			
			//由矩阵对iv表进行更新, 对矩阵每一行进行检查，看是否由一个1的
			index = false;
			for(int i=0; i<matrix.size(); i++)
			{
				int oneIndex=0, tmp=0;
				for(int j=0; j<matrix.get(i).length; j++)
				{
					if(matrix.get(i)[j]==1)
						oneIndex=j;
					tmp+=matrix.get(i)[j];
				}
				if(tmp==1) //This row has exactly one '1' entry, and the index is oneIndex
				{
					if(oneIndex < xLength) 
					{
						Set<String> stmp= new HashSet<String>(constants.get(i));
						Xor(stmp,iv.get(oneIndex));
						if(stmp.size()==1 &&!stmp.contains("?") ) //contradiction
							return true;
						else if(iv.get(oneIndex).size()==1 && iv.get(oneIndex).contains("?")) //iv[oneIndex]=="?"
						{
							if(constants.get(i).isEmpty())
							{
								if(iv.get(oneIndex+xLength).size()==1 && iv.get(oneIndex+xLength).contains("*"))//Contradiction
								{
									return true;
								}
								iv.get(oneIndex).clear();
								iv.get(oneIndex+xLength).clear();
								index=true;
							}
							else if(constants.get(i).size()==1)
							{
								iv.get(oneIndex).clear();
								iv.get(oneIndex).addAll(constants.get(i));
								iv.get(oneIndex+xLength).clear();
								iv.get(oneIndex+xLength).add("*");
								index=true;
							}
						}

					}
					else if(oneIndex > xLength) 
					{
						Set<String> stmp= new HashSet<String>(constants.get(i));
						Xor(stmp,iv.get(oneIndex));
						if(stmp.size()==1 &&!stmp.contains("?") ) //contradiction
							return true;
						else if(iv.get(oneIndex).size()==1 && iv.get(oneIndex).contains("?")) //iv[oneIndex]=="?"
						{
							if(constants.get(i).isEmpty())
							{
								iv.get(oneIndex).clear(); //modify Yi
								iv.get(oneIndex-xLength).clear(); //modify Xi
								index=true;
							}
						}
					}
					

				
				}
			}
			
			
			
		}
		
		return flag;
	}
	
    public static void TestSkipjack(String[] plaintext, String[] ciphertext, int r)
	{
    	int c=2*r+4; 
    	
    	int[][] skipjack = new int[r][c]; 
    	
    	//现将第0行写好，第i行则为第0行向右平移第i位
    	//x4=x0+y3
    	for(int i=0; i<r; i++)
    	{
    		skipjack[i][1+i] = 1;
    		skipjack[i][r+1+i] = 1;
    		skipjack[i][r+4+i] = 1;
    	}
    	ArrayList<int []> matrix = new ArrayList<int []>();
        for(int i=0; i<skipjack.length; i++)
        	matrix.add(skipjack[i]);
        
        //constants vector
        List<Set<String>> constants = new ArrayList<Set<String>>();   
        for(int i=0; i<r; i++)
           	constants.add(new HashSet<String>());
        
        //initial value vector
        String[] strIV = new String[c];
        for(int i=0; i<c; i++) strIV[i]="?";
        //对(x3,y0,y1,y2) 和  x(3+r), y(r),y(1+r), y(2+r) 进行初始化。

        strIV[0] = plaintext[0];
        strIV[r+1] = plaintext[1];
        strIV[r+2] = plaintext[2];
        strIV[r+3] = plaintext[3];
        
        strIV[r] = ciphertext[0];
        strIV[c-3] = ciphertext[1];
        strIV[c-2] = ciphertext[2];
        strIV[c-1] = ciphertext[3];
        
        
        List<Set<String>> iv = new ArrayList<Set<String>>();
        for(int i=0; i<strIV.length; i++)
        {
        	iv.add(new HashSet<String>());
        	if(strIV[i]!="0")
        		iv.get(i).add(strIV[i]);
 
        }
        
      //  printMatrix(matrix,constants);
        
      	
        boolean ok=IDSkipjack(matrix,constants,iv,r);
        if(ok)
        System.out.println("Gen-Skipjack r="+r+" plaintext = " + Arrays.asList(plaintext) + " ciphertext = " + Arrays.asList(ciphertext)+  " : "+ok);

	}
    
    public static boolean IDSkipjack(ArrayList<int []> matrix, List<Set<String>> constants, List<Set<String>> iv,int r)
	{
		boolean flag = false;
		boolean index=true;
		
		  //初始化iv表
		//XYDistance表示 xi 与 yi之间的间隔
		//xLength 表示有用的xi变量的个数 
        int xLength=r;
        int XYDistance=r+4;
      	for(int i=0;i<xLength; i++)
      	{
      			if(iv.get(i).isEmpty()&&!iv.get(i+XYDistance).isEmpty()&&!iv.get(i+XYDistance).contains("?")) return true;
      			else if(iv.get(i).isEmpty()) iv.get(i+XYDistance).clear();
      			else if(!iv.get(i).contains("?") && iv.get(i+XYDistance).contains("?"))
      			{ 
      				iv.get(i+XYDistance).clear();
      				iv.get(i+XYDistance).add("*");
      			}
      	}
      	for(int i=xLength+4;i<xLength+XYDistance; i++)
      	{
      			if(iv.get(i).isEmpty()&&!iv.get(i-XYDistance).isEmpty()&&!iv.get(i-XYDistance).contains("?")) return true;
      			else if(iv.get(i).isEmpty()) iv.get(i-XYDistance).clear();
      			else if(!iv.get(i).contains("?") && iv.get(i-XYDistance).contains("?"))
      			{ 
      				iv.get(i-XYDistance).clear();
      				iv.get(i-XYDistance).add("*");
      			}
      	}
		
		while(index)
		{
			InitialVector(matrix,constants, iv);
			//对matrix和constants进行三角式变换
			ReducingMatrix( matrix,  constants);
			//查看矩阵是否有解
			for(int i=matrix.size()-1; i>=0; i--)
			{
				int tmp=0;
				for(int j=0;j<matrix.get(0).length;j++)
					tmp+=matrix.get(i)[j];
				if(tmp==0 && !constants.get(i).isEmpty()) //方程无解  
					return true;
			}
			
			//由矩阵对iv表进行更新, 对矩阵每一行进行检查，看是否由一个1的
			index = false;
			for(int i=0; i<matrix.size(); i++)
			{
				int oneIndex=0, tmp=0;
				for(int j=0; j<matrix.get(i).length; j++)
				{
					if(matrix.get(i)[j]==1)
						oneIndex=j;
					tmp+=matrix.get(i)[j];
				}
				if(tmp==1) //This row has exactly one '1' entry, and the index is oneIndex
				{
					if(oneIndex < xLength) 
					{
						Set<String> stmp= new HashSet<String>(constants.get(i));
						Xor(stmp,iv.get(oneIndex));
						if(stmp.size()==1 &&!stmp.contains("?") ) //contradiction
							return true;
						else if(iv.get(oneIndex).size()==1 && iv.get(oneIndex).contains("?")) //iv[oneIndex]=="?"
						{
							if(constants.get(i).isEmpty())
							{
								if(iv.get(oneIndex+XYDistance).size()==1 && iv.get(oneIndex+XYDistance).contains("*"))//Contradiction
								{
									return true;
								}
								iv.get(oneIndex).clear();
								iv.get(oneIndex+XYDistance).clear();
								index=true;
							}
							else if(constants.get(i).size()==1)
							{
								iv.get(oneIndex).clear();
								iv.get(oneIndex).addAll(constants.get(i));
								iv.get(oneIndex+XYDistance).clear();
								iv.get(oneIndex+XYDistance).add("*");
								index=true;
							}
						}

					}
					else if(oneIndex > xLength) 
					{
						Set<String> stmp= new HashSet<String>(constants.get(i));
						Xor(stmp,iv.get(oneIndex));
						if(stmp.size()==1 &&!stmp.contains("?") ) //contradiction
							return true;
						else if(iv.get(oneIndex).size()==1 && iv.get(oneIndex).contains("?")) //iv[oneIndex]=="?"
						{
							if(constants.get(i).isEmpty())
							{
								iv.get(oneIndex).clear(); //modify Yi
								iv.get(oneIndex-XYDistance).clear(); //modify Xi
								index=true;
							}
						}
					}
					

				
				}
			}
			
			
			
		}
		
		return flag;
	} 
    
    public static void TestFourCell(String[] plaintext, String[] ciphertext, int r)
   	{
       	int c=2*r+4; 
       	
       	int[][] fourcell = new int[r][c]; 
       	
       	//现将第0行写好，第i行则为第0行向右平移第i位
       	//x4=x0+y3
       	for(int i=0; i<r; i++)
       	{
       		fourcell[i][1+i] = 1;
       		fourcell[i][2+i] = 1;
       		fourcell[i][3+i] = 1;
       		fourcell[i][4+i] = 1;
       		fourcell[i][r+4+i] = 1;
       		
       	}
       	ArrayList<int []> matrix = new ArrayList<int []>();
           for(int i=0; i<fourcell.length; i++)
           	matrix.add(fourcell[i]);
           
           //constants vector
           List<Set<String>> constants = new ArrayList<Set<String>>();   
           for(int i=0; i<r; i++)
              	constants.add(new HashSet<String>());
           
           //initial value vector
           String[] strIV = new String[c];
           for(int i=0; i<c; i++) strIV[i]="?";
           //对(x3,y0,y1,y2) 和  x(3+r), y(r),y(1+r), y(2+r) 进行初始化。

           strIV[0] = plaintext[0];
           strIV[1] = plaintext[1];
           strIV[2] = plaintext[2];
           strIV[3] = plaintext[3];
           
           strIV[r] = ciphertext[0];
           strIV[r+1] = ciphertext[1];
           strIV[r+2] = ciphertext[2];
           strIV[r+3] = ciphertext[3];
           
           
           List<Set<String>> iv = new ArrayList<Set<String>>();
           for(int i=0; i<strIV.length; i++)
           {
           	iv.add(new HashSet<String>());
           	if(strIV[i]!="0")
           		iv.get(i).add(strIV[i]);
    
           }
           
         //  printMatrix(matrix,constants);
           
         	
           boolean ok=IDFourcell(matrix,constants,iv,r);
           if(ok)
           System.out.println("Four-Cell r="+r+" plaintext = " + Arrays.asList(plaintext) + " ciphertext = " + Arrays.asList(ciphertext)+  " : "+ok);

   	}

	public static boolean IDFourcell(ArrayList<int []> matrix, List<Set<String>> constants, List<Set<String>> iv,int r)
	{
		boolean flag = false;
		boolean index=true;
		
		  //初始化iv表
		//XYDistance表示 xi 与 yi之间的间隔
		//xLength 表示有用的xi变量的个数 
        int xLength=r;
        int XYDistance=r+4;
      	for(int i=0;i<xLength; i++)
      	{
      			if(iv.get(i).isEmpty()&&!iv.get(i+XYDistance).isEmpty()&&!iv.get(i+XYDistance).contains("?")) return true;
      			else if(iv.get(i).isEmpty()) iv.get(i+XYDistance).clear();
      			else if(!iv.get(i).contains("?") && iv.get(i+XYDistance).contains("?"))
      			{ 
      				iv.get(i+XYDistance).clear();
      				iv.get(i+XYDistance).add("*");
      			}
      	}
      	for(int i=xLength+4;i<xLength+XYDistance; i++)
      	{
      			if(iv.get(i).isEmpty()&&!iv.get(i-XYDistance).isEmpty()&&!iv.get(i-XYDistance).contains("?")) return true;
      			else if(iv.get(i).isEmpty()) iv.get(i-XYDistance).clear();
      			else if(!iv.get(i).contains("?") && iv.get(i-XYDistance).contains("?"))
      			{ 
      				iv.get(i-XYDistance).clear();
      				iv.get(i-XYDistance).add("*");
      			}
      	}
		
		while(index)
		{
			InitialVector(matrix,constants, iv);
			//对matrix和constants进行三角式变换
			ReducingMatrix( matrix,  constants);
			//查看矩阵是否有解
			for(int i=matrix.size()-1; i>=0; i--)
			{
				int tmp=0;
				for(int j=0;j<matrix.get(0).length;j++)
					tmp+=matrix.get(i)[j];
				if(tmp==0 && !constants.get(i).isEmpty()) //方程无解  
					return true;
			}
			
			//由矩阵对iv表进行更新, 对矩阵每一行进行检查，看是否由一个1的
			index = false;
			for(int i=0; i<matrix.size(); i++)
			{
				int oneIndex=0, tmp=0;
				for(int j=0; j<matrix.get(i).length; j++)
				{
					if(matrix.get(i)[j]==1)
						oneIndex=j;
					tmp+=matrix.get(i)[j];
				}
				if(tmp==1) //This row has exactly one '1' entry, and the index is oneIndex
				{
					if(oneIndex < xLength) 
					{
						Set<String> stmp= new HashSet<String>(constants.get(i));
						Xor(stmp,iv.get(oneIndex));
						if(stmp.size()==1 &&!stmp.contains("?") ) //contradiction
							return true;
						else if(iv.get(oneIndex).size()==1 && iv.get(oneIndex).contains("?")) //iv[oneIndex]=="?"
						{
							if(constants.get(i).isEmpty())
							{
								if(iv.get(oneIndex+XYDistance).size()==1 && iv.get(oneIndex+XYDistance).contains("*"))//Contradiction
								{
									return true;
								}
								iv.get(oneIndex).clear();
								iv.get(oneIndex+XYDistance).clear();
								index=true;
							}
							else if(constants.get(i).size()==1)
							{
								iv.get(oneIndex).clear();
								iv.get(oneIndex).addAll(constants.get(i));
								iv.get(oneIndex+XYDistance).clear();
								iv.get(oneIndex+XYDistance).add("*");
								index=true;
							}
						}

					}
					else if(oneIndex > xLength) 
					{
						Set<String> stmp= new HashSet<String>(constants.get(i));
						Xor(stmp,iv.get(oneIndex));
						if(stmp.size()==1 &&!stmp.contains("?") ) //contradiction
							return true;
						else if(iv.get(oneIndex).size()==1 && iv.get(oneIndex).contains("?")) //iv[oneIndex]=="?"
						{
							if(constants.get(i).isEmpty())
							{
								iv.get(oneIndex).clear(); //modify Yi
								iv.get(oneIndex-XYDistance).clear(); //modify Xi
								index=true;
							}
						}
					}
					

				
				}
			}
			
			
			
		}
		
		return flag;
	}
	    
    
    public static void TestMars(String[] plaintext, String[] ciphertext, int r)
   	{
       	int c=4*r+4; 
       	
       	int[][] mars = new int[r*3][c]; 
       	
       	//现将第0行写好，第i行则为第0行向右平移第i位
       	
       	for(int i=0; i<r; i++)
       	{
       		mars[i*3][3*i] = 1;
       		mars[i*3][3*i+4] = 1;
       		mars[i*3][3*r+4+i] = 1;
       		
       		
       		mars[i*3+1][3*i+1] = 1;
       		mars[i*3+1][3*i+5] = 1;
       		mars[i*3+1][3*r+4+i] = 1;
       		
       		mars[i*3+2][3*i+2] = 1;
       		mars[i*3+2][3*i+6] = 1;
       		mars[i*3+2][3*r+4+i] = 1;
       		
       	}
       	ArrayList<int []> matrix = new ArrayList<int []>();
           for(int i=0; i<mars.length; i++)
           	matrix.add(mars[i]);
           
           //constants vector
           List<Set<String>> constants = new ArrayList<Set<String>>();   
           for(int i=0; i<3*r; i++)
              	constants.add(new HashSet<String>());
           
           //initial value vector
           String[] strIV = new String[c];
           for(int i=0; i<c; i++) strIV[i]="?";
           //对(x3,y0,y1,y2) 和  x(3+r), y(r),y(1+r), y(2+r) 进行初始化。

           strIV[0] = plaintext[3];
           strIV[1] = plaintext[2];
           strIV[2] = plaintext[1];
           strIV[3] = plaintext[0];
           
           strIV[3*r+3] = ciphertext[0];
           strIV[3*r+2] = ciphertext[1];
           strIV[3*r+1] = ciphertext[2];
           strIV[3*r] = ciphertext[3];
           
           
           List<Set<String>> iv = new ArrayList<Set<String>>();
           for(int i=0; i<strIV.length; i++)
           { 
           
         //  printMatrix(matrix,constants);
           
         	
           boolean ok=IDMars(matrix,constants,iv,r);
           if(ok)
           System.out.println("Gen-Mars r="+r+" plaintext = " + Arrays.asList(plaintext) + " ciphertext = " + Arrays.asList(ciphertext)+  " : "+ok);

   	}
}
    
	public static boolean IDMars(ArrayList<int []> matrix, List<Set<String>> constants, List<Set<String>> iv,int r)
	{
		boolean flag = false;
		boolean index=true;
		
		  //初始化iv表
		//XYDistance表示 xi 与 yi之间的间隔
		//xLength 表示有用的xi变量的个数 
        int xLength=3*r;
        int XYDistance=3*r+3;
      	for(int i=3;i<xLength+1; i+=3)
      	{
      			if(iv.get(i).isEmpty()&&!iv.get(XYDistance+i/3).isEmpty()&&!iv.get(XYDistance+i/3).contains("?")) return true;
      			else if(iv.get(i).isEmpty()) iv.get(XYDistance+i/3).clear();
      			else if(!iv.get(i).contains("?") && iv.get(XYDistance+i/3).contains("?"))
      			{ 
      				iv.get(XYDistance+i/3).clear();
      				iv.get(XYDistance+i/3).add("*");
      			}
      	}
      	for(int i=3*r+4;i<4*r+4; i++)
      	{
      		
      		int k=3*(i-3*r-3);
      			if(iv.get(i).isEmpty()&&!iv.get(k).isEmpty()&&!iv.get(k).contains("?")) return true;
      			else if(iv.get(i).isEmpty()) iv.get(k).clear();
      			else if(!iv.get(i).contains("?") && iv.get(k).contains("?"))
      			{ 
      				iv.get(k).clear();
      				iv.get(k).add("*");
      			}

      	}
		
		while(index)
		{
			//InitialVector(matrix,constants, iv);
			for(int i=0; i<iv.size(); i++)
			{
				if(iv.get(i).isEmpty())
				{
					for(int j=0; j<matrix.size(); j++) //将第i列变为0
						matrix.get(j)[i]=0;
				}
				else if((!iv.get(i).contains("?")) && (!iv.get(i).contains("*")))  //将第i行后面加上相应的常量
				{
					for(int j=0; j<matrix.size(); j++)
						if(matrix.get(j)[i]==1) 
						{
							matrix.get(j)[i]=0;
							/* constants[j] = constants[j] xor iv[i] */
						    Set<String> siv = new HashSet<String>();
						    siv.addAll(iv.get(i));
						    Xor(constants.get(j),siv);
						}
				}
			}
			//对matrix和constants进行三角式变换
			ReducingMatrix( matrix,  constants);
			//查看矩阵是否有解
			for(int i=matrix.size()-1; i>=0; i--)
			{
				int tmp=0;
				for(int j=0;j<matrix.get(0).length;j++)
					tmp+=matrix.get(i)[j];
				if(tmp==0 && !constants.get(i).isEmpty()) //方程无解  
					return true;
			}
			
			//由矩阵对iv表进行更新, 对矩阵每一行进行检查，看是否由一个1的
			index = false;
			for(int i=0; i<matrix.size(); i++)
			{
				int oneIndex=0, tmp=0;
				for(int j=0; j<matrix.get(i).length; j++)
				{
					if(matrix.get(i)[j]==1)
						oneIndex=j;
					tmp+=matrix.get(i)[j];
				}
				if(tmp==1) //This row has exactly one '1' entry, and the index is oneIndex
				{
					if(oneIndex < 3*r+4) 
					{
						Set<String> stmp= new HashSet<String>(constants.get(i));
						Xor(stmp,iv.get(oneIndex));
						if(stmp.size()==1 &&!stmp.contains("?") ) //contradiction
							return true;
						else if(oneIndex >0 && oneIndex %3==0 && iv.get(oneIndex).size()==1 && iv.get(oneIndex).contains("?")) //iv[oneIndex]=="?"
						{
							if(constants.get(i).isEmpty())
							{
								if(iv.get(XYDistance+oneIndex/3).size()==1 && iv.get(XYDistance+oneIndex/3).contains("*"))//Contradiction
								{
									return true;
								}
								iv.get(oneIndex).clear();
								iv.get(XYDistance+oneIndex/3).clear();
								index=true;
							}
							else if(constants.get(i).size()==1)
							{
								iv.get(oneIndex).clear();
								iv.get(oneIndex).addAll(constants.get(i));
								iv.get(XYDistance+oneIndex/3).clear();
								iv.get(XYDistance+oneIndex/3).add("*");
								index=true;
							}
						}

					}
					else if(oneIndex >= 3*r+4) 
					{
						Set<String> stmp= new HashSet<String>(constants.get(i));
						Xor(stmp,iv.get(oneIndex));
						if(stmp.size()==1 &&!stmp.contains("?") ) //contradiction
							return true;
						else if(iv.get(oneIndex).size()==1 && iv.get(oneIndex).contains("?")) //iv[oneIndex]=="?"
						{
							if(constants.get(i).isEmpty())
							{
								iv.get(oneIndex).clear(); //modify Yi
								iv.get(3*(oneIndex-3*r-3)).clear(); //modify Xi
								index=true;
							}
						}
					}
					

				
				}
			}
			
			
			
		}
		
		return flag;
	}	

    /* RC6
     *  input:  x0           x2         x1         x3
	 * output:  x2       x4=x1+F(x3)    x3        x5=x0+F(x2)
	 * y2=F(x2);   y3=F(x3)  
	 * 1 轮就有 x0, x1, x2, x3, x4, x5, y2, y3 共 8个变量
	 * 2 轮就有 x0, x1, x2, x3, x4, x5, x6, x7, y2, y3, y4, y8	 共12个变量，
	 * 每增加一轮，变量个数增加4个。
	 * 
	 */	
    
    
    public static void TestRC6(String[] plaintext, String[] ciphertext, int r)
   	{
       	int c=4*r+4; 
       	
       	int[][] rc6 = new int[2*r][c]; 
       	
       	//先将第0行写好，第i行则为第0行向右平移第i位
       	
       	for(int i=0; i<r; i++)
       	{
       		rc6[i*2][2*i] = 1;
       		rc6[i*2][2*i+5] = 1;
       		rc6[i*2][2*r+4+2*i] = 1;
       		       		 
       		rc6[i*2+1][2*i+1] = 1;
       		rc6[i*2+1][2*i+4] = 1;
       		rc6[i*2+1][2*r+5+2*i] = 1;
       		
       		
       	}
       	ArrayList<int []> matrix = new ArrayList<int []>();
           for(int i=0; i<rc6.length; i++)
           	matrix.add(rc6[i]);
           
           //constants vector
           List<Set<String>> constants = new ArrayList<Set<String>>();   
           for(int i=0; i<2*r; i++)
              	constants.add(new HashSet<String>());
           
           //initial value vector
           String[] strIV = new String[c];
           for(int i=0; i<c; i++) strIV[i]="?";
           //

           strIV[0] = plaintext[0];
           strIV[1] = plaintext[2];
           strIV[2] = plaintext[1];
           strIV[3] = plaintext[3];
           
           strIV[2*r+3] = ciphertext[3];
           strIV[2*r+2] = ciphertext[1];
           strIV[2*r+1] = ciphertext[2];
           strIV[2*r] = ciphertext[0];
           
           
           List<Set<String>> iv = new ArrayList<Set<String>>();
           for(int i=0; i<strIV.length; i++)
           {
           	iv.add(new HashSet<String>());
           	if(strIV[i]!="0")
           		iv.get(i).add(strIV[i]);
    
           }
           
         //  printMatrix(matrix,constants);
           
         	
           boolean ok=IDRC6(matrix,constants,iv,r);
           if(ok)
           System.out.println("Gen-RC6 r="+r+" plaintext = " + Arrays.asList(plaintext) + " ciphertext = " + Arrays.asList(ciphertext)+  " : "+ok);

   	}
    public static boolean IDRC6(ArrayList<int []> matrix, List<Set<String>> constants, List<Set<String>> iv,int r)
	{
		boolean flag = false;
		boolean index=true;
		
		  //初始化iv表
		//XYDistance表示 xi 与 yi之间的间隔
		//xLength 表示有用的xi变量的个数 
        int xLength=2*r+2;
        int XYDistance=2*r+2;
      	for(int i=2;i<xLength; i++)
      	{
      			if(iv.get(i).isEmpty()&&!iv.get(XYDistance+i).isEmpty()&&!iv.get(XYDistance+i).contains("?")) return true;
      			else if(iv.get(i).isEmpty()) iv.get(XYDistance+i).clear();
      			else if(!iv.get(i).contains("?") && iv.get(XYDistance+i).contains("?"))
      			{ 
      				iv.get(XYDistance+i).clear();
      				iv.get(XYDistance+i).add("*");
      			}
      	}
      	for(int i=2*r+4;i<4*r+4; i++)
      	{
      		
      			int k=i-XYDistance;
      			if(iv.get(i).isEmpty()&&!iv.get(k).isEmpty()&&!iv.get(k).contains("?")) return true;
      			else if(iv.get(i).isEmpty()) iv.get(k).clear();
      			else if(!iv.get(i).contains("?") && iv.get(k).contains("?"))
      			{ 
      				iv.get(k).clear();
      				iv.get(k).add("*");
      			}

      	}
		
		while(index)
		{
			//InitialVector(matrix,constants, iv);
			for(int i=0; i<iv.size(); i++)
			{
				if(iv.get(i).isEmpty())
				{
					for(int j=0; j<matrix.size(); j++) //将第i列变为0
						matrix.get(j)[i]=0;
				}
				else if((!iv.get(i).contains("?")) && (!iv.get(i).contains("*")))  //将第i行后面加上相应的常量
				{
					for(int j=0; j<matrix.size(); j++)
						if(matrix.get(j)[i]==1) 
						{
							matrix.get(j)[i]=0;
							/* constants[j] = constants[j] xor iv[i] */
						    Set<String> siv = new HashSet<String>();
						    siv.addAll(iv.get(i));
						    Xor(constants.get(j),siv);
						}
				}
			}
			//对matrix和constants进行三角式变换
			ReducingMatrix( matrix,  constants);
			//查看矩阵是否有解
			for(int i=matrix.size()-1; i>=0; i--)
			{
				int tmp=0;
				for(int j=0;j<matrix.get(0).length;j++)
					tmp+=matrix.get(i)[j];
				if(tmp==0 && !constants.get(i).isEmpty()) //方程无解  
					return true;
			}
			
			//由矩阵对iv表进行更新, 对矩阵每一行进行检查，看是否由一个1的
			index = false;
			for(int i=0; i<matrix.size(); i++)
			{
				int oneIndex=0, tmp=0;
				for(int j=0; j<matrix.get(i).length; j++)
				{
					if(matrix.get(i)[j]==1)
						oneIndex=j;
					tmp+=matrix.get(i)[j];
				}
				if(tmp==1) //This row has exactly one '1' entry, and the index is oneIndex
				{
					if(oneIndex < 2*r+4) 
					{
						Set<String> stmp= new HashSet<String>(constants.get(i));
						Xor(stmp,iv.get(oneIndex));
						if(stmp.size()==1 &&!stmp.contains("?") ) //contradiction
							return true;
						else if(oneIndex >1  && iv.get(oneIndex).size()==1 && iv.get(oneIndex).contains("?")) //iv[oneIndex]=="?"
						{
							if(constants.get(i).isEmpty())
							{
								if(iv.get(XYDistance+oneIndex).size()==1 && iv.get(XYDistance+oneIndex).contains("*"))//Contradiction
								{
									return true;
								}
								iv.get(oneIndex).clear();
								iv.get(XYDistance+oneIndex).clear();
								index=true;
							}
							else if(constants.get(i).size()==1)
							{
								iv.get(oneIndex).clear();
								iv.get(oneIndex).addAll(constants.get(i));
								iv.get(XYDistance+oneIndex).clear();
								iv.get(XYDistance+oneIndex).add("*");
								index=true;
							}
						}

					}
					else if(oneIndex >= 2*r+4) 
					{
						Set<String> stmp= new HashSet<String>(constants.get(i));
						Xor(stmp,iv.get(oneIndex));
						if(stmp.size()==1 &&!stmp.contains("?") ) //contradiction
							return true;
						else if(iv.get(oneIndex).size()==1 && iv.get(oneIndex).contains("?")) //iv[oneIndex]=="?"
						{
							if(constants.get(i).isEmpty())
							{
								iv.get(oneIndex).clear(); //modify Yi
								iv.get(oneIndex-XYDistance).clear(); //modify Xi
								index=true;
							}
						}
					}
					

				
				}
			}
			
			
			
		}
		
		return flag;
	}	
    
    
    public static boolean IDSMS4(ArrayList<int []> matrix, List<Set<String>> constants, List<Set<String>> iv,int r)
	{
		boolean flag = false;
		boolean index=true;
		
		  //初始化iv表
		//XYDistance表示 xi 与 yi之间的间隔
		//xLength 表示有用的xi变量的个数 
        int xLength=2*r+4;
        int XYDistance=r;
      	for(int i=r+4;i<xLength; i++)
      	{
      			if(iv.get(i).isEmpty()&&!iv.get(XYDistance+i).isEmpty()&&!iv.get(XYDistance+i).contains("?")) return true;
      			else if(iv.get(i).isEmpty()) iv.get(XYDistance+i).clear();
      			else if(!iv.get(i).contains("?") && iv.get(XYDistance+i).contains("?"))
      			{ 
      				iv.get(XYDistance+i).clear();
      				iv.get(XYDistance+i).add("*");
      			}
      	}
      	for(int i=2*r+4;i<3*r+4; i++)
      	{
      		
      			int k=i-XYDistance;
      			if(iv.get(i).isEmpty()&&!iv.get(k).isEmpty()&&!iv.get(k).contains("?")) return true;
      			else if(iv.get(i).isEmpty()) iv.get(k).clear();
      			else if(!iv.get(i).contains("?") && iv.get(k).contains("?"))
      			{ 
      				iv.get(k).clear();
      				iv.get(k).add("*");
      			}

      	}
		
		while(index)
		{
			//InitialVector(matrix,constants, iv);
			for(int i=0; i<iv.size(); i++)
			{
				if(iv.get(i).isEmpty())
				{
					for(int j=0; j<matrix.size(); j++) //将第i列变为0
						matrix.get(j)[i]=0;
				}
				else if((!iv.get(i).contains("?")) && (!iv.get(i).contains("*")))  //将第i行后面加上相应的常量
				{
					for(int j=0; j<matrix.size(); j++)
						if(matrix.get(j)[i]==1) 
						{
							matrix.get(j)[i]=0;
							/* constants[j] = constants[j] xor iv[i] */
						    Set<String> siv = new HashSet<String>();
						    siv.addAll(iv.get(i));
						    Xor(constants.get(j),siv);
						}
				}
			}
			//对matrix和constants进行三角式变换
			ReducingMatrix( matrix,  constants);
			//查看矩阵是否有解
			for(int i=matrix.size()-1; i>=0; i--)
			{
				int tmp=0;
				for(int j=0;j<matrix.get(0).length;j++)
					tmp+=matrix.get(i)[j];
				if(tmp==0 && !constants.get(i).isEmpty()) //方程无解  
					return true;
			}
			
			//由矩阵对iv表进行更新, 对矩阵每一行进行检查，看是否由一个1的
			index = false;
			for(int i=0; i<matrix.size(); i++)
			{
				int oneIndex=0, tmp=0;
				for(int j=0; j<matrix.get(i).length; j++)
				{
					if(matrix.get(i)[j]==1)
						oneIndex=j;
					tmp+=matrix.get(i)[j];
				}
				if(tmp==1) //This row has exactly one '1' entry, and the index is oneIndex
				{
					if(oneIndex < 2*r+4) 
					{
						Set<String> stmp= new HashSet<String>(constants.get(i));
						Xor(stmp,iv.get(oneIndex));
						if(stmp.size()==1 &&!stmp.contains("?") ) //contradiction
							return true;
						else if(oneIndex >r+3  && iv.get(oneIndex).size()==1 && iv.get(oneIndex).contains("?")) //iv[oneIndex]=="?"
						{
							if(constants.get(i).isEmpty())
							{
								if(iv.get(XYDistance+oneIndex).size()==1 && iv.get(XYDistance+oneIndex).contains("*"))//Contradiction
								{
									return true;
								}
								iv.get(oneIndex).clear();
								iv.get(XYDistance+oneIndex).clear();
								index=true;
							}
							else if(constants.get(i).size()==1)
							{
								iv.get(oneIndex).clear();
								iv.get(oneIndex).addAll(constants.get(i));
								iv.get(XYDistance+oneIndex).clear();
								iv.get(XYDistance+oneIndex).add("*");
								index=true;
							}
						}

					}
					else if(oneIndex >= 2*r+4) 
					{
						Set<String> stmp= new HashSet<String>(constants.get(i));
						Xor(stmp,iv.get(oneIndex));
						if(stmp.size()==1 &&!stmp.contains("?") ) //contradiction
							return true;
						else if(iv.get(oneIndex).size()==1 && iv.get(oneIndex).contains("?")) //iv[oneIndex]=="?"
						{
							if(constants.get(i).isEmpty())
							{
								iv.get(oneIndex).clear(); //modify Yi
								iv.get(oneIndex-XYDistance).clear(); //modify Xi
								index=true;
							}
						}
					}
					

				
				}
			}
			
			
			
		}
		
		return flag;
	}	
    
    
    public static void TestSMS4(String[] plaintext, String[] ciphertext, int r)
   	{
       	int c=3*r+4; 
       	
       	int[][] sms4 = new int[2*r][c]; 
       	
       	//现将第0行写好，第i行则为第0行向右平移第i位
       	
       	for(int i=0; i<r; i++)
       	{
       		sms4[i*2][i] = 1;
       		sms4[i*2][i+4] = 1;
       		sms4[i*2][2*r+4+i] = 1;
       		       		
       		sms4[i*2+1][i+1] = 1;
       		sms4[i*2+1][i+2] = 1;
       		sms4[i*2+1][i+3] = 1;
       		sms4[i*2+1][r+4+i] = 1;
       		
       		
       	}
       	ArrayList<int []> matrix = new ArrayList<int []>();
           for(int i=0; i<sms4.length; i++)
           	matrix.add(sms4[i]);
           
           //constants vector
           List<Set<String>> constants = new ArrayList<Set<String>>();   
           for(int i=0; i<2*r; i++)
              	constants.add(new HashSet<String>());
           
           //initial value vector
           String[] strIV = new String[c];
           for(int i=0; i<c; i++) strIV[i]="?";
           //对(x3,y0,y1,y2) 和  x(3+r), y(r),y(1+r), y(2+r) 进行初始化。

           strIV[0] = plaintext[0];
           strIV[1] = plaintext[1];
           strIV[2] = plaintext[2];
           strIV[3] = plaintext[3];
           
           strIV[r+3] = ciphertext[3];
           strIV[r+2] = ciphertext[2];
           strIV[r+1] = ciphertext[1];
           strIV[r] = ciphertext[0];
           
           
           List<Set<String>> iv = new ArrayList<Set<String>>();
           for(int i=0; i<strIV.length; i++)
           {
           	iv.add(new HashSet<String>());
           	if(strIV[i]!="0")
           		iv.get(i).add(strIV[i]);
    
           }
           
         //  printMatrix(matrix,constants);
           
         	
           boolean ok=IDSMS4(matrix,constants,iv,r);
           if(ok)
           System.out.println("SMS4 r="+r+" plaintext = " + Arrays.asList(plaintext) + " ciphertext = " + Arrays.asList(ciphertext)+  " : "+ok);

   	}
}

