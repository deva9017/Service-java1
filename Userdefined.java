class Userdefined
{
static void validate(int age)throws Exception
{
if(age<18)
throw new Exception("not valid");
else
System.out.println("welcome to vote");
}
public static void main(String []args)
{
try
{
validate(15);
}
catch(Exception m)
{
System.out.println("Exception occured: "+m);
}
System.out.println("rest of the code...");
}
}