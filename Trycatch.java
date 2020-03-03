class Trycatch
{
public static void main(String args[])
{
try
{
int c=40/0;
}
catch(ArithmeticException e)
{
System.out.println(e);
}
}
}