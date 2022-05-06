// 1.
/*
You are given an array of Positive numbers from 1 to n, such that all numbers from 1 to n are present except one number 'x'. You have to find 'x'.
The input array is not sorted.

Solution is O(n) time complexity
*/
public class ActivityOne {

  public int find_missing_number(List<Integer> input)
  {
    int n = input.size() + 1 // we are adding 1 because 1 digit is missing 
    int actual_sum = ( n*(n+1))/2; // arithmic expression to calculate the sum of 1 ... N
    int sum_of_array_values = 0;

    for ( Integer array_value : input)
    {
        sum_of_array_values += array_value;
    }
    
    return actual_sum - sum_of_array_values;      
  }

  public static void main (String[] args)
  {
     List<Integer> myTestList = new ArrayList<Integer>();
     myTestList.add(1);
     myTestList.add(4);
     myTestList.add(3);

     System.out.println("Missing number" || find_missing_number(myTestLIst));
  }

}
