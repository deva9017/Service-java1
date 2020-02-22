class TrycatchFinally
{
public static void main(String args[])
{
try
{
String a[]=new String[5];
System.out.println(a[6]);
 
}
catch(Exception e)
{
System.out.println(e);

}
finally
{
System.out.println("never mind");
}
}
}