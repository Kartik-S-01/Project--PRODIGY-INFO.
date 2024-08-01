import java.util.*;

public class temperature 
{
    public static double celtofah(double cel)   //celsius to fahrenheit
    {
        return (cel * 9/5) + 32;          
    }                                      
    public static double celtokel(double cel)  //celsius to kelvin
    {
        return cel + 273.15;
    }
    public static double fahtocel(double fah)  //fahrenheit to celsius
    {
        return (fah - 32) * 5/9;
    }
    public static double keltocel(double kel)  //kelvin to celsius
    {
        return kel - 273.15;
    }
    public static double fahtokel(double fah)  //fahrenheit to kelvin
    {
        return (fah - 32) * 5/9 + 273.15;
    }
    public static double keltofah(double kel)  //kelvin to fahrenheit
    {
        return (kel - 273.15) * 9/5 + 32;
    }
    public static void main(String[] args) 
    {
        Scanner sc = new Scanner(System.in);
        boolean xyz = true;

        while (xyz) 
        {
       
        System.out.println("\nTemperature Conversion Project");
        System.out.println("Choose an option:");
        System.out.println("1. Celsius to Fahrenheit and Kelvin");
        System.out.println("2. Fahrenheit to Celsius");
        System.out.println("3. Kelvin to Celsius");
        System.out.println("4. Fahrenheit to Kelvin");
        System.out.println("5. Kelvin to Fahrenheit");

        System.out.println("\nPlease Enter your choice :");
        int choice = sc.nextInt();
        double tempa, tempb, tempc;

        switch (choice) 
        {
            case 1:
                System.out.print("\nEnter temperature in Celsius: ");
                tempa = sc.nextDouble();
                tempb = celtofah(tempa);
                tempc = celtokel(tempa);
                System.out.println("\nTemperature in Fahrenheit: " + tempb);
                System.out.println("\nTemperature in Kelvin: " + tempc);
                break;
           
            case 2:
                System.out.print("\nEnter temperature in Fahrenheit: ");
                tempa = sc.nextDouble();
                tempb = fahtocel(tempa);
                System.out.println("\nTemperature in Celsius: " + tempb);
                break;
            
            case 3:
                System.out.print("\nEnter temperature in Kelvin: ");
                tempa = sc.nextDouble();
                tempb = keltocel(tempa);
                System.out.println("\nTemperature in Celsius: " + tempb);
                break;
        
            case 4:
                System.out.print("\nEnter temperature in Fahrenheit: ");
                tempa = sc.nextDouble();
                tempb = fahtokel(tempa);
                System.out.println("\nTemperature in Kelvin: " + tempb);
                break;
    
            case 5:
                System.out.print("\nEnter temperature in Kelvin: ");
                tempa = sc.nextDouble();
                tempb = keltofah(tempa);
                System.out.println("\nTemperature in Fahrenheit: " + tempb);
                break;

            default:
                System.out.println("\nPlease Enter a valid choice! ");
                break;
        }

            System.out.print("\nDo you want to conversion again? (yes/no): ");
            String res = sc.next();
            
            if (!res.equalsIgnoreCase("yes")) 
            {
                xyz = false;
            }
        }
        
        sc.close();
    }
}
