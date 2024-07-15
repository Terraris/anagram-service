# Anagram Findr

Anagram Findr is a simple Java console application that uses the AnagramFindr service to provide the user with
information related to anagrams. The application allows you to add or remove match replacement modes.

## Features

This application provides the following features:

- Check if two strings are anagrams of each other.
- Get anagrams of a string from previously queried strings.
- Activating and Deactivating Replacement Modes: The user can activate or deactivate 'LATIN' and 'MODERN' replacement
- modes which affect how anagrams are matched.

## Installation

Ensure you have Java SDK 21 (https://www.oracle.com/java/technologies/downloads/) as well as maven
(https://maven.apache.org/install.html) installed on your system.

- Clone the repository: `git clone https://github.com/Terraris/anagram-service.git`
- Resolve maven dependencies: `mvn clean install`
- Execute the main method in the Main class: `mvn compile exec:java -D exec.mainClass=io.beyonnex.Main`

## Usage

When you run the application, you'll be given these options:

1. Check if two strings are anagrams: Enter two strings, separated by return, to check if they are anagrams.
2. Get anagrams of a string: Input a string to find its known anagrams from previously queried words.
3. Add anagram match replacement mode ('LATIN' or 'MODERN'): Activates a replacement mode that affects anagram matching.
4. Remove anagram match replacement mode ('LATIN' or 'MODERN'): Deactivates a replacement mode.
5. Exit: Exits the application.

### Example

1. Run the application. You'll see several option choices:

  ``` 
  **************************************************************
  [1] Check if two strings are anagrams
  [2] Get anagrams of a string
  [3] Add anagram match replacement mode: 'LATIN', 'MODERN'
  [4] Remove anagram match replacement mode: 'LATIN', 'MODERN'
  [5] Exit
  **************************************************************
  ```

2. Enter '1' and hit enter to check if two strings are anagrams. When prompted, input the two strings, confirming each
   with return.
3. The application will tell you whether the two strings are anagrams.
4. After that, you will be redirected back to the main menu to choose another option or exit the application.

## Sources:

### Exercise (requirements)

*Write an interactive Java program that provides these 2 features:*

- *1 - Checks if two texts are anagrams of each other.*
- *2 - Out of all inputs to feature#1: provides all the anagrams for a given string.*
    - *Inputs to feature#1 do not need to persisted across multiple runs of your program.*

  *The mode of interactivity is not important. Choose what's easiest and quickest for you, a simple CLI interface is
  enough.*
- *For feature #1:*
    - *Please follow the definition described in the English Wikipedia page for anagram.*
- *For feature #2:*
    - *Given these hypothetical invocations of the feature#1 function -> f1(A,B), f1(B,C), f1(A,D)*
    - *IF* A, B and D are anagrams
    - *f2(A) should return [B, D]*
    - *f2(B) should return [A, D]*
    - *f2(C) should return []*

#### Wikipedia Page of 'Anagram': https://en.wikipedia.org/wiki/Anagram, accessed 09-07-2024 (DD-MM-YYYY)

## License

This project is licensed under the GNU General Public License v3.0 - see
the [license](https://www.gnu.org/licenses/gpl-3.0.en.html) ot the `LICENSE` file in the root directory for details.

## Further Questions

If there are any questions or issues not covered in this document, please contact the project owner.

## Detailed Work Log

Below is a breakdown of how time was spent during the development of this project:

- 1 hour: Test Driven Development for proof-of-concept with implementation of the basic use case.
- 3 hours: 'Implementing it out', add interfaces, modes (and their application), logging, refactoring, unit-tests.
- 2 hours: Creating a CLI for the app.
- 1 hour: clean-up.
- 1 (additional) hour: more precision in the test cases, extend documentation

## Architectural Decisions
The architecture of the `AnagramFindr` is a reflection of balancing the efficiency of anagram operations and the 
flexibility required in a dynamic application setting. This balance is demonstrated in the decision to deviate 
from traditional methods, which included usage of more complex structures like `Anatree`.

The Anatree algorithm tends to be resource-intensive. It requires significant memory and computational power to maintain
an anagram dictionary in a trie-like structure. Such an exhaustive approach can result in potential scalability issues.

To mitigate these concerns a HashMap-based anagram dictionary with character histograms as keys has been implemented.
All anagrams, being permutations of a particular set of letters, have the same character histogram. The HashMap-based
dictionary strategy. All permutations are held as values against their corresponding character histogram key in the 
HashMap. The HashMap combined with character histogram generation provides constant-time *(O(1))* search and insert
operations.

The architectural design is built towards future adaptability. The service provides a dynamic functionality allowing 
users to activate or deactivate specific match replacement modes. 
### conclusion
The *adaptiveness* of the AnagramService design ensures minimal ripple effects when introducing new functionalities. 
Using the principles of open-closed design, the architecture ensures robustness while remaining adaptable to changing 
requirements. These decisions principles ensure effective and efficient anagram operations, 
circumvent scalability issues, and embrace system evolution for future needs. 
