class Try
{
public static void main(String []args)
{
int a;
try
{
a=100/0;
}
catch(ArithmeticException e)
{
System.out.println(e);
}
}
}