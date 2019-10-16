package McElice;

/*
 * AUTHOR: M R MAHIMA
 * BE CSE
 * PSG COLLEGE OF TECHNOLOGY
 */

import java.util.Random;

class alice{
	
	public  int[][] G_prime = {{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0}};
	 
	
	//Alice breaks down message into segments of length  k = 4.
	//let message = 1101

	static int[] x = {1,1,0,1};
	
	public alice(int[][] G_prime)
	{
		this.G_prime = G_prime;
		
	}
	public static int[]multiply_find_G_prime(int m,int p , int q,int[] mat1,int[][] mat2)
	{
		int d,k;
		int sum = 0;
		
		int [] tempp = new int[q];
		
           for (d = 0; d < q; d++)
           {  
              for (k = 0; k < p; k++)
              {
                 sum = sum + mat1[k]*mat2[k][d];
              }

              tempp[d] = (sum%2);
              sum = 0;
           }
        
		
		return tempp;
		
		
	}
	
	public int[] add(int[] one,int[] two,int a)
	{
		int[] res = new int[a];
		for(int i = 0; i<a;i++)
		{
			res[i] += one[i] +two[i];
			res[i] = res[i]%2;
		}
		return res;
	}
	
	public int[] encrypt_message()
	{
		//Alice constructs a weight 1 error vector of length n = 7, say e = 0000100; t = 1
		//Alice randomly places t 1's in a zero vector of length n.
		//int[] e ={0,0,0,0,1,0,0};
		Random rand = new Random(); 
		int rand_int1 = rand.nextInt(7); 
		
		int[] e = new int[7];
		e[rand_int1] = 1;
		
				
				
		//Alice sends to Bob the vector :
		//y = x .G_prime +e
		
		int[] temp = multiply_find_G_prime(1,4,7,x,G_prime);
		
		
		
		int[] y = add(temp,e,7);
		
		return y;
		
		
				
	}
	
	
	
	
			
	
}

 class bob{
	 
	  static int[][] G_prime = {{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0}};
	 
	 
	//(7,4) HAMMING CODE WHICH CORRECTS ALL SINGLE ERRORS
		
	 static int[][] G = {{1,0,0,0,1,1,0},{0,1,0,0,1,0,1},{0,0,1,0,0,1,1},{0,0,0,1,1,1,1}};
			
			
	//generate a non singular matrix S(4X4)(KXK)(HAS TO BE INVERTIBLE,I.E. DET(D) !=0)
			
			
	 static int[][] S = {{1,1,0,1},{1,0,0,1},{0,1,1,1},{1,1,0,0}};
	 
	 
			
	//randomly generate Permutation Matrix P (nxn) such that it has single 1 in each row and coumn, and zeros every where else
			
	 //static int[][] P = {{0,1,0,0,0,0,0},{0,0,0,1,0,0,0},{0,0,0,0,0,0,1},{1,0,0,0,0,0,0},{0,0,1,0,0,0,0},{0,0,0,0,0,1,0},{0,0,0,0,1,0,0}};
	 static int[][] P = randomly_generate(7);
	 
	 
	 public static int[][] randomly_generate(int n)
	 {
		 Random random = new Random();
		 int randd[][] = new int[n][n];
		 
		 int[] rows = new int[n];
		 for (int i = 0; i < n; i++) {
             rows[i] = i;
         }
         for (int i = 0; i < n; i++) {
             int j = random.nextInt(n);
             int rowToSwap = rows[i];
             rows[i] = rows[j];
             rows[j] = rowToSwap;
         }
         
         for (int i = 0; i < n; i++) {
             randd[rows[i]][i] = 1;
         }
         
         System.out.println("p:\n");
         print(n,n,randd);
         return randd;
         
		 
	 }
	 
	 
	 
	 public static int[]multiply_find_G_prime(int m,int p , int q,int[] mat1,int[][] mat2)
		{
			int d,k;
			int sum = 0;
			
			int [] tempp = new int[q];
			
	           for (d = 0; d < q; d++)
	           {  
	              for (k = 0; k < p; k++)
	              {
	                 sum = sum + mat1[k]*mat2[k][d];
	              }

	              tempp[d] = (sum%2);
	              sum = 0;
	           }
	        
			
			return tempp;
			
			
		}		

	public static int[][]multiply_find_G_prime(int m,int p , int q,int[][] mat1,int[][] mat2)
	{
		int c,d,k;
		int sum = 0;
		
		int [][] tempp = new int[m][q];
		for (c = 0; c < m; c++)
        {
           for (d = 0; d < q; d++)
           {  
              for (k = 0; k < p; k++)
              {
                 sum = sum + mat1[c][k]*mat2[k][d];
              }

              tempp[c][d] = (sum%2);
              sum = 0;
           }
        }
		
		return tempp;
		
		
	}
	public static void printt(int m,int[] temp)
	{
		 int c, d;
        for (c = 0; c < m; c++)
        {
          
              System.out.print(temp[c]+"\t");

          	}
        System.out.print("\n");
        
	}
	
	
	
	
	
	
	public static int[][] bob_makes_public_key()
	{
		//G_prime = SGP
		
		int[][] temp = multiply_find_G_prime(4,4,7,S,G);
		G_prime = multiply_find_G_prime(4,7,7,temp,P);
				
		return G_prime;
	}
	
	public static int difference(int x[],int y[] )
	{
		int sum = 0;
		for(int i = 0; i<x.length;i++)
		{
			if(x[i] != y[i])
				sum++;
			
		}
		return sum;
	}
	
	//***************
	 public static int[][] invert(int a[][]) 
	    {
	        int n = a.length;
	        int x[][] = new int[n][n];
	        int b[][] = new int[n][n];
	        int index[] = new int[n];
	        for (int i=0; i<n; ++i) 
	            b[i][i] = 1;
	 
	 // Transform the matrix into an upper triangle
	        gaussian(a, index);
	 
	 // Update the matrix b[i][j] with the ratios stored
	        for (int i=0; i<n-1; ++i)
	            for (int j=i+1; j<n; ++j)
	                for (int k=0; k<n; ++k)
	                    b[index[j]][k]
	                    	    -= a[index[j]][i]*b[index[i]][k];
	 
	 // Perform backward substitutions
	        for (int i=0; i<n; ++i) 
	        {
	            x[n-1][i] = b[index[n-1]][i]/a[index[n-1]][n-1];
	            for (int j=n-2; j>=0; --j) 
	            {
	                x[j][i] = b[index[j]][i];
	                for (int k=j+1; k<n; ++k) 
	                {
	                    x[j][i] -= a[index[j]][k]*x[k][i];
	                }
	                x[j][i] /= a[index[j]][j];
	            }
	        }
	        return x;
	    }
	 
	// Method to carry out the partial-pivoting Gaussian
	// elimination.  Here index[] stores pivoting order.
	 
	    public static void gaussian(int a[][], int index[]) 
	    {
	        int n = index.length;
	        int c[] = new int[n];
	 
	 // Initialize the index
	        for (int i=0; i<n; ++i) 
	            index[i] = i;
	 
	 // Find the rescaling factors, one from each row
	        for (int i=0; i<n; ++i) 
	        {
	            int c1 = 0;
	            for (int j=0; j<n; ++j) 
	            {
	                int c0 = Math.abs(a[i][j]);
	                if (c0 > c1) c1 = c0;
	            }
	            c[i] = c1;
	        }
	 
	 // Search the pivoting element from each column
	        int k = 0;
	        for (int j=0; j<n-1; ++j) 
	        {
	            int pi1 = 0;
	            for (int i=j; i<n; ++i) 
	            {
	                int pi0 = Math.abs(a[index[i]][j]);
	                pi0 /= c[index[i]];
	                if (pi0 > pi1) 
	                {
	                    pi1 = pi0;
	                    k = i;
	                }
	            }
	 
	   // Interchange rows according to the pivoting order
	            int itmp = index[j];
	            index[j] = index[k];
	            index[k] = itmp;
	            for (int i=j+1; i<n; ++i) 	
	            {
	                int pj = a[index[i]][j]/a[index[j]][j];
	 
	 // Record pivoting ratios below the diagonal
	                a[index[i]][j] = pj;
	 
	 // Modify other elements accordingly
	                for (int l=j+1; l<n; ++l)
	                    a[index[i]][l] -= pj*a[index[j]][l];
	            }
	        }
	    }
	
	//****************
	    public static void print(int m, int q,int[][] temp)
		{
			 int c, d;
	        for (c = 0; c < m; c++)
	        {
	           for (d = 0; d < q; d++)
	              System.out.print(temp[c][d]+"\t");

	           System.out.print("\n");
	        }
		}
	
	public static void bob_decode(int[] y)
	{
		
		//int[][] p_inverse = {{0,0,0,1,0,0,0},{1,0,0,0,0,0,0},{0,0,0,0,1,0,0},{0,1,0,0,0,0,0},{0,0,0,0,0,0,1},{0,0,0,0,0,1,0},{0,0,1,0,0,0,0}};
		
		int[][] p_inverse= invert(P);
		
		int[] temp = multiply_find_G_prime(1,7,7,y,p_inverse);
		
		
		//hamming decoding.
		int[]xsg;
		int codeword_num = 0;
		for(int[]codeword:G)
		{
			
			if(difference(codeword,temp) == 1)
			{
				
				xsg = codeword;
				//printt(7,xsg);
			   // System.out.println("\n" + codeword_num);
			
				break;
			}
			codeword_num++;
		}
		
		
		
		//finding xs:
		int[] xs = new int[4];
		xs[codeword_num] = 1;
		
		
		//printt(4,xs);
		
		//x = xs . s_inverse
		
		int[][]S_inverse = invert(S);
		
		//System.out.println();
		
		//print(4,4,S_inverse);
		//performing mod 2
		for(int i =0; i<4;i++)
		{
			for (int j = 0; j<4;j++)
			{
				if(S_inverse[i][j]>=0)
				S_inverse[i][j] = S_inverse[i][j]%2;
				
				else
					S_inverse[i][j] = S_inverse[i][j] + 2;
					
			}
		}
		
		//System.out.println();
		
		//print(4,4,S_inverse);
		
		
		int[]x = multiply_find_G_prime(1,4,4,xs,S_inverse);
		
		System.out.println();
		System.out.println("message decoded by bob , x:");
		printt(4,x);
		
		
		
		
		
		
		
		
			
	}
		
		
	 
	
}


public class McElice {
	
	//public key :G'
	
	public static int[][] G_prime = {{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0}};
	
	public static void print(int m, int q,int[][] temp)
	{
		 int c, d;
        for (c = 0; c < m; c++)
        {
           for (d = 0; d < q; d++)
              System.out.print(temp[c][d]+"\t");

           System.out.print("\n");
        }
	}
	
	public static void print(int m,int[] temp)
	{
		 int c, d;
        for (c = 0; c < m; c++)
        {
          
              System.out.print(temp[c]+"\t");

          	}
        System.out.print("\n");
        
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//bob side
		bob b1 = new bob();
		G_prime = b1.bob_makes_public_key();
			
		System.out.println("G_prime:\n");
		print(4,7,G_prime);
		
		//Alice part
		
		alice a1 = new alice(G_prime);
		
		//message on the way from Alice to Bob
		int[] y = a1.encrypt_message();
		System.out.println("\ny:");
		print(7,y);
		
		//bob upon receiving y 
		
		b1.bob_decode(y);
		
		
		}



}
