# Anagram Findr
Anagram Findr is a simple Java console application that uses the `AnagramFindrService` to provide the user with information related to anagrams.

## Features
This application provides the following features:

- Check if two strings are anagrams of each other.
- Get anagrams of a string from previously queried strings.

## Installation
Ensure you have Java SDK 21 installed on your system.

- Clone the repository: `git clone https://github.com/Terraris/anagram-service.git`
- Resolve maven dependencies: `mvn clean install`
- Execute the main method in the Main class: `mvn compile exec:java -D exec.mainClass=io.beyonnex.Main`


## Usage
The application is a CLI (Command Line Interface) and prompts you with three options once you run it:
1. Check if two strings are anagrams: Allows you to check if two given strings are anagrams. You will need to input two strings, confirming each with return.
2. Get anagrams of a string: After inputting a string, you will be shown a list of known anagrams of the string. If no known anagrams, you will be notified.
3. Exit: Exit from the application.
### Example
1. Run the application
2. You'll see the following prompt:
```  Choose an option (confirm with return):
   [1] Check if two strings are anagrams
   [2] Get anagrams of a string
   [3] Exit 
```
3. Enter 1 and hit return to check if two strings are anagrams.
4. You'll be prompted to enter the two strings. Enter each string and hit return.
5. The application will then tell you if the two strings are anagrams.

## Sources:
### Exercise (requirements)
Write an interactive Java program that provides these 2 features:
- 1 - Checks if two texts are anagrams of each other. 
- 2 - Out of all inputs to feature#1: provides all the anagrams for a given string. 
  - Inputs to feature#1 do not need to persisted across multiple runs of your program. 
  
  The mode of interactivity is not important. Choose what's easiest and quickest for you, a simple CLI interface is enough. 
- For feature #1: 
  - Please follow the definition described in the english wikipedia page for anagram.
- For feature #2: 
  - Given these hypothetical invocations of the feature#1 function -> f1(A,B), f1(B,C), f1(A,D)
  - *IF* A, B and D are anagrams
  - f2(A) should return [B, D]
  - f2(B) should return [A, D]
  - f2(C) should return []
#### Wikipedia Page of 'Anagram': https://en.wikipedia.org/wiki/Anagram, access 09-07-2024 (DD-MM-YYYY)


## License
This project is licensed under the GNU GENERAL PUBLIC License.

## Further Questions
If there are any questions or issues not covered in this document, please contact the project owner.
