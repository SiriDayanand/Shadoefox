import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Contact {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ArrayList<ContactEntry> contactList = new ArrayList<>();

    public static void main(String[] args) {
        int choice;
        do {
            displayMenu();
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            switch (choice) {
                case 1:
                    addContact();
                    break;
                case 2:
                    viewContacts();
                    break;
                case 3:
                    updateContact();
                    break;
                case 4:
                    deleteContact();
                    break;
                case 5:
                    searchContact();
                    break;
                case 6:
                    exportContacts();
                    break;
                case 7:
                    importContacts();
                    break;
                case 8:
                    backupContacts();
                    break;
                case 9:
                    restoreContacts();
                    break;
                case 10:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 10);
    }

    private static void displayMenu() {
        System.out.println("\n--- Contact Management System ---");
        System.out.println("1. Add Contact");
        System.out.println("2. View Contacts");
        System.out.println("3. Update Contact");
        System.out.println("4. Delete Contact");
        System.out.println("5. Search Contact");
        System.out.println("6. Export Contacts");
        System.out.println("7. Import Contacts");
        System.out.println("8. Backup Contacts");
        System.out.println("9. Restore Contacts");
        System.out.println("10. Exit");
        System.out.print("Choose an option: ");
    }

    private static void addContact() {
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Phone Number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter Email Address: ");
        String email = scanner.nextLine();

        if (isValidPhoneNumber(phoneNumber) && isValidEmail(email)) {
            contactList.add(new ContactEntry(name, phoneNumber, email));
            System.out.println("Contact added successfully.");
        } else {
            System.out.println("Invalid phone number or email address.");
        }
    }

    private static void viewContacts() {
        if (contactList.isEmpty()) {
            System.out.println("No contacts available.");
        } else {
            System.out.println("\n--- Contact List ---");
            for (ContactEntry contact : contactList) {
                System.out.println(contact);
            }
        }
    }

    private static void updateContact() {
        System.out.print("Enter the name of the contact to update: ");
        String name = scanner.nextLine();
        for (ContactEntry contact : contactList) {
            if (contact.getName().equalsIgnoreCase(name)) {
                System.out.print("Enter new Phone Number: ");
                String newPhoneNumber = scanner.nextLine();
                System.out.print("Enter new Email Address: ");
                String newEmail = scanner.nextLine();

                if (isValidPhoneNumber(newPhoneNumber) && isValidEmail(newEmail)) {
                    contact.setPhoneNumber(newPhoneNumber);
                    contact.setEmail(newEmail);
                    System.out.println("Contact updated successfully.");
                } else {
                    System.out.println("Invalid phone number or email address.");
                }
                return;
            }
        }
        System.out.println("Contact not found.");
    }

    private static void deleteContact() {
        System.out.print("Enter the name of the contact to delete: ");
        String name = scanner.nextLine();
        for (int i = 0; i < contactList.size(); i++) {
            if (contactList.get(i).getName().equalsIgnoreCase(name)) {
                contactList.remove(i);
                System.out.println("Contact deleted successfully.");
                return;
            }
        }
        System.out.println("Contact not found.");
    }

    private static void searchContact() {
        System.out.print("Enter the name of the contact to search: ");
        String name = scanner.nextLine();
        for (ContactEntry contact : contactList) {
            if (contact.getName().equalsIgnoreCase(name)) {
                System.out.println(contact);
                return;
            }
        }
        System.out.println("Contact not found.");
    }

    private static void exportContacts() {
        // Implement export logic to a file (CSV or another format)
        System.out.println("Export functionality not yet implemented.");
    }

    private static void importContacts() {
        // Implement import logic from a file
        System.out.println("Import functionality not yet implemented.");
    }

    private static void backupContacts() {
        // Implement backup logic (e.g., save to a file)
        System.out.println("Backup functionality not yet implemented.");
    }

    private static void restoreContacts() {
        // Implement restore logic from a backup
        System.out.println("Restore functionality not yet implemented.");
    }

    private static boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "^\\d{10}$"; // Simple regex for 10-digit phone number
        return Pattern.matches(regex, phoneNumber);
    }

    private static boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"; // Simple regex for email validation
        return Pattern.matches(regex, email);
    }
}

class ContactEntry {
    private String name;
    private String phoneNumber;
    private String email;

    public ContactEntry(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Phone Number: " + phoneNumber + ", Email: " + email;
    }
}
