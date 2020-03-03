package com.Exception;

public class Exception {
  static void method(int age)throws ArithmeticException
  {
	  try
	  {
		 if(age<18)
			 throw new ArithmeticException("not valid");
		 else
			 System.out.println("welcome to vote");
	  }
  
  catch( ArithmeticException ae)
  {
	  System.out.println(ae);
  }
  }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
    method(21);
   {
	   System.out.println("hello");
   }
	}

}
