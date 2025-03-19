#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX 1024 // Maximum buffer size for reading and writing files

// Function prototypes
void openFile(char *filename);
void writeFile(char *filename);
void appendToFile(char *filename);
void displayMenu();

int main() {
    char filename[100];
    int choice;
    
    // Display the menu in a loop until the user exits
    do {
        displayMenu();
        printf("Enter your choice: ");
        scanf("%d", &choice);
        
        switch(choice) {
            case 1:
                printf("Enter the file name to open: ");
                scanf("%s", filename);
                openFile(filename);
                break;
            case 2:
                printf("Enter the file name to write to: ");
                scanf("%s", filename);
                writeFile(filename);
                break;
            case 3:
                printf("Enter the file name to append to: ");
                scanf("%s", filename);
                appendToFile(filename);
                break;
            case 4:
                printf("Exiting...\n");
                break;
            default:
                printf("Invalid choice! Please try again.\n");
        }
    } while (choice != 4);
    
    return 0;
}

// Function to display the menu
void displayMenu() {
    printf("\n--- Text Editor Menu ---\n");
    printf("1. Open and read a file\n");
    printf("2. Write to a file (overwrite)\n");
    printf("3. Append to a file\n");
    printf("4. Exit\n");
}

// Function to open and read a file
void openFile(char *filename) {
    FILE *file = fopen(filename, "r");
    if (file == NULL) {
        printf("Error: Could not open file %s for reading\n", filename);
        return;
    }
    
    char buffer[MAX];
    printf("\n--- File Content ---\n");
    while (fgets(buffer, MAX, file) != NULL) {
        printf("%s", buffer);
    }
    printf("\n--- End of File ---\n");
    
    fclose(file);
}

// Function to write to a file (overwrite)
void writeFile(char *filename) {
    FILE *file = fopen(filename, "w");
    if (file == NULL) {
        printf("Error: Could not open file %s for writing\n", filename);
        return;
    }
    
    char buffer[MAX];
    printf("Enter the text to write to the file (press Enter twice to finish):\n");
    getchar(); // To consume the newline character after previous input
    while (fgets(buffer, MAX, stdin) && strcmp(buffer, "\n") != 0) {
        fputs(buffer, file);
    }
    
    fclose(file);
    printf("File written successfully.\n");
}

// Function to append to a file
void appendToFile(char *filename) {
    FILE *file = fopen(filename, "a");
    if (file == NULL) {
        printf("Error: Could not open file %s for appending\n", filename);
        return;
    }
    
    char buffer[MAX];
    printf("Enter the text to append to the file (press Enter twice to finish):\n");
    getchar(); // To consume the newline character after previous input
    while (fgets(buffer, MAX, stdin) && strcmp(buffer, "\n") != 0) {
        fputs(buffer, file);
    }
    
    fclose(file);
    printf("File appended successfully.\n");
}
