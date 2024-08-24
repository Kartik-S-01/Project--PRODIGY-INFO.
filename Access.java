import java.io.*;               //Inbuild Packages
import java.util.*;

class Contact        // Contact class representing a contact with attributes like name, phone number, email, etc. 
{
    private static int idCounter = 1;    // For genering user id's automatically
    private int id;
    private String name;
    private String phNumber;
    private String email;
    private String countrycode;

    public Contact(String name, String phNumber, String email, String countrycode)  //Parameters
    {
        this.id = idCounter++;
        this.name = name;
        this.phNumber = phNumber;
        this.email = email;
        this.countrycode = countrycode;
    }

    public int getId()              // Get and set contact detals 
    {
        return id;
    }

    public void setId(int id) 
    {
        this.id = id;
    }

    public static int getIdCounter() 
    {
        return idCounter;
    }

    public static void setIdCounter(int idCounter) 
    {
        Contact.idCounter = idCounter;
    }

    public String getName() 
    {
        return name;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getPhoneNumber() 
    {
        return phNumber;
    }

    public void setPhoneNumber(String phNumber) 
    {
        this.phNumber = phNumber;
    }

    public String getEmail() 
    {
        return email;
    }

    public void setEmail(String email) 
    {
        this.email = email;
    }

    public String getCountryCode() 
    {
        return countrycode;
    }

    public void setCountryCode(String countrycode) 
    {
        this.countrycode = countrycode;
    }

    @Override
    
    public String toString() 
    {
        return String.format("%-5s %-15s %-10s %-25s %-10s", id, name, countrycode, phNumber, email);
    }
}

class ContactManager        // This class is responsible for managing the list of contacts 
{
    private List<Contact> contacts;
    private static final String FILE_PATH = "contacts.txt";

    public ContactManager() 
    {
        contacts = new ArrayList<>();
        load();
    }

    public void add(Contact contact) 
    {
        if (isDuplicateContact(contact.getPhoneNumber(), contact.getEmail())) 
        {
            System.out.println("Contact with this phone number or email already exists.");
            return;
        }
        
        contacts.add(contact);
        save();
    }

    public void edit(int index, Contact newContact) 
    {
        if (index >= 0 && index < contacts.size()) 
        {
            // Check if the new contact details are duplicates (excluding the contact being edited)
            
            if (isDuplicateContact(newContact.getPhoneNumber(), newContact.getEmail(), contacts.get(index).getId())) 
            {
                System.out.println("Contact with this phone number or email already exists.");
                return;
            }
            
            newContact.setId(contacts.get(index).getId());       // Preserve the original ID
            contacts.set(index, newContact);
            save();
        } 
        
        else 
        {
            System.out.println("Invalid contact index.");
        }
    }

    public void delete(int index) 
    {
        if (index >= 0 && index < contacts.size()) 
        {
            contacts.remove(index);
            reassignIds();  // Reassign IDs after deletion
            save();
        } 
        
        else 
        {
            System.out.println("Invalid contact index.");
        }
    }

    public List<Contact> getAllContacts() 
    {
        return contacts;
    }

    public Contact ContactById(int id) 
    {
        for (Contact contact : contacts) 
        {
            if (contact.getId() == id) 
            {
                return contact;
            }
        }
        
        return null;
    }

    public List<Contact> searchContacts(String keyword) 
    {
        List<Contact> result = new ArrayList<>();
        for (Contact contact : contacts) 
        {
            if (contact.getName().toLowerCase().contains(keyword.toLowerCase())) 
            {
                result.add(contact);
            }
        }
        
        return result;
    }

    private boolean isDuplicateContact(String phNumber, String email)     // Check for duplicate contact number or email
    {
        for (Contact contact : contacts) 
        {
            if (contact.getPhoneNumber().equals(phNumber) || contact.getEmail().equals(email)) 
            {
                return true;
            }
        }
        
        return false;
    }

    private boolean isDuplicateContact(String phNumber, String email, int excludeId)      // Check for duplicate contact number or email excluding a specific contact ID
    {
        for (Contact contact : contacts) 
        {
            if (contact.getId() != excludeId && (contact.getPhoneNumber().equals(phNumber) || contact.getEmail().equals(email))) 
            {
                return true;
            }
        }
        
        return false;
    }

    private void reassignIds()        // Reassign IDs to maintain continuous sequence
    {
        int currentId = 1;
        for (Contact contact : contacts) 
        {
            contact.setId(currentId++);
        }
        
        Contact.setIdCounter(currentId);
    }

    private void save()      // Save contacts to a file and using try and catch block due to exception handling 
    {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH)))   
        {
            for (Contact contact : contacts) 
            {
                writer.println(contact.getId() + "," + contact.getName() + "," + contact.getPhoneNumber() + "," + contact.getEmail() + "," + contact.getCountryCode());
            }
        } 
        
        catch (IOException e) 
        {
            System.out.println("Error saving contacts: " + e.getMessage());
        }
    }

    private void load()       // Loading of contacts from a file
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                String[] parts = line.split(",");
                
                if (parts.length == 5) 
                {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String phNumber = parts[2];
                    String email = parts[3];
                    String countrycode = parts[4];
                    Contact contact = new Contact(name, phNumber, email, countrycode);
                    contact.setId(id);                                                  // Set the loaded contact ID
                    contacts.add(contact);
                    Contact.setIdCounter(Math.max(Contact.getIdCounter(), id + 1)); // Ensure the Id is set correctly
                }
            }
        } 
        
        catch (FileNotFoundException e) 
        {
            System.out.println("No previous contacts found. Starting fresh.");
        } 
        
        catch (IOException e) 
        {
            System.out.println("Error loading contacts: " + e.getMessage());
        }
    }
}

public class Access      // Access class providing a user interface for managing contacts (search, edit, delete etc....)
{
    private static ContactManager contactManager = new ContactManager();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) 
    {
        while (true) 
        {
            System.out.println("\nContact Management System");
            System.out.println("1. Add Contact");
            System.out.println("2. Edit Contact");
            System.out.println("3. Delete Contact");
            System.out.println("4. View All Contacts");
            System.out.println("5. Search Contact by Name");
            System.out.println("6. Search Contact by ID");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            
            int choice = sc.nextInt();
            sc.nextLine();              // Consume newline

            switch (choice) 
            {
                case 1:
                    add();
                    break;
                case 2:
                    edit();
                    break;
                case 3:
                    delete();
                    break;
                case 4:
                    viewAllContacts();
                    break;
                case 5:
                    searchContactByName();
                    break;
                case 6:
                    ContactById();
                    break;
                case 7:
                    System.out.println("Thank's For Using...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again later!");
            }
        }
    }

    private static void add()         //For adding new contact 
    {
        System.out.print("\nEnter name: ");
        String name = sc.nextLine();
        
        String phNumber;
        while (true) 
        {
            System.out.print("Enter phone number (1 to 10 digits): ");
            phNumber = sc.nextLine();
            
            if (phNumber.matches("\\d{1,10}")) 
            {
                break;
            } 
            
            else 
            {
                System.out.println("Phone number must be 1 to 10 digits.");
            }
        }

        System.out.print("Enter email: ");
        String email = sc.nextLine();

        String countrycode;
        while (true) 
        {
            System.out.print("Enter country code (e.g., +1, +91): ");
            countrycode = sc.nextLine();
            
            if (countrycode.matches("\\+\\d{1,3}")) 
            {
                break;
            } 
            
            else 
            {
                System.out.println("Invalid country code format.");
            }
        }

        Contact contact = new Contact(name, phNumber, email, countrycode);
        contactManager.add(contact);
        System.out.println("Contact added successfully.");
    }

    private static void edit()     //For any kind of modifictaion in available contact
    {
        viewAllContacts();
        System.out.print("Enter contact index to edit: ");
        int index = sc.nextInt();
        sc.nextLine(); // Consume newline

        System.out.print("Enter new name: ");
        String name = sc.nextLine();
        
        String phNumber;
        while (true) 
        {
            System.out.print("Enter new phone number (1 to 10 digits): ");
            phNumber = sc.nextLine();
            
            if (phNumber.matches("\\d{1,10}")) 
            {
                break;
            } 
            
            else 
            {
                System.out.println("Phone number must be 1 to 10 digits.");
            }
        }

        System.out.print("Enter new email: ");
        String email = sc.nextLine();

        String countrycode;
        while (true) 
        {
            System.out.print("Enter new country code (e.g., +1, +91): ");
            countrycode = sc.nextLine();
           
            if (countrycode.matches("\\+\\d{1,3}")) 
            {
                break;
            } 
            
            else 
            {
                System.out.println("Invalid country code format.");
            }
        }

        Contact newContact = new Contact(name, phNumber, email, countrycode);
        contactManager.edit(index - 1, newContact); // Adjust for 0-based index
        System.out.println("Contact updated successfully.");
    }

    private static void delete()     //For removing existing contact
    {
        viewAllContacts();
        System.out.print("\nEnter contact index to delete: ");
        int index = sc.nextInt();
        sc.nextLine(); // Consume newline

        contactManager.delete(index - 1); // Adjust for 0-based index
        System.out.println("Contact deleted successfully.");
    }

    private static void viewAllContacts()     //For displaying all available contacts in record/file
    {
        List<Contact> contacts = contactManager.getAllContacts();
        
        if (contacts.isEmpty()) 
        {
            System.out.println("No contacts available.");
        } 
        
        else 
        {
            System.out.printf("%-5s %-15s %-10s %-25s %-10s%n", "\nID", "Name", "Country Code", "Phone Number", "Email");
            System.out.println("---------------------------------------------------------------");
            
            for (Contact contact : contacts) 
            {
                System.out.println(contact);
            }
        }
    }

    private static void searchContactByName()       //For seraching any contact by user name 
    {
        System.out.print("\nEnter search keyword: ");
        String keyword = sc.nextLine();
        List<Contact> contacts = contactManager.searchContacts(keyword);
        
        if (contacts.isEmpty()) 
        {
            System.out.println("No contacts found.");
        } 
        
        else 
        {
            System.out.printf("%-5s %-15s %-10s %-25s %-10s%n", "\nID", "Name", "Country Code", "Phone Number", "Email");
            System.out.println("---------------------------------------------------------------");
            for (Contact contact : contacts) 
            {
                System.out.println(contact);
            }
        }
    }

    private static void ContactById()      //For seraching any contact by user's Id number
    {
        System.out.print("\nEnter contact ID to search: ");
        int id = sc.nextInt();
        sc.nextLine(); // Consume newline
        Contact contact = contactManager.ContactById(id);
        
        if (contact == null) 
        {
            System.out.println("No contact found with ID: " + id);
        } 
        
        else 
        {
            System.out.printf("%-5s %-15s %-10s %-25s %-10s%n", "\nID", "Name", "Country Code", "Phone Number", "Email");
            System.out.println("---------------------------------------------------------------");
            System.out.println(contact);
        }
    }
}
