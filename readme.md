## Why I chose this Problem?
I chose the 1800-CODING-CHALLENGE problem because I enjoy solving this type of problem.
The type of problem requires a logical algorithmic approach and has many potentials for performance improvements by using different strategy and alogorithm.
I enjoy this kind of challenge.

## Approach and Explanation
My approach to this problem was to filter the dictionary (which simply is a collection of terms) recursively.
Progressing from left to right, every digit in a number block will further filter the dictionary according to the corresponding character encoding for the digit.
By the end of the recursion, any terms remaining in the dictionary would be a match for the number block.

The most complex part of this problem is the requirement to allow the skipping of every subseqeunt characters.
This essentially means, every block of number with (n) digits there are (n^2 - (n-1)^2) combinations.
To simplify the logic in the recursion, I first generate all permutations recursively, inserting a placeholder where a number can be replaced.

For example: [2252] would result in these permutations: [2 X 5 5], [2 X 5 X], [X 2 5 5], [X 2 X 5], [X 2 5 X], [2 2 X 5]
With the list of permutations, I can pass each of them to the matching algorithm to filter the dictionary, and replacement characters are simply handled by moving to the next recursion without filtering the dictionary.

In terms of optimisation, by generating all possible permutations I know the exact length of words that can match number block.  As such I can group them together, pre filter the dicionary based on word length, and then reuse the dictionary for any permutation that's looking words at this length.
If required I can perform further optimisations by caching filter results so that we look in the cache before we look up the dictionary.  This hasn't been implemented, but its definitely possible.

The final part was simply splitting the input number the specification did not specify the delimiter that splits each number block, so I simple use a regex which splits by ANY non numeric chars.
For number that has multiple blocks, once I have generated all possible matches for each block, I created a method that will perform a "JOIN" on the permutations of each block.


## Testing
For testing I provided unit testing for all functional modules that would benefit from testing.
I could have used Mockito to mock services (as you can see each modules inject its dependencies via the constructor.), however due to the simple nature of each service, it was easier to simply instantiate them and include them as part of the test.


## Running the application (Eclipse)
The application was written in eclipse.  If you are using eclipse, you can import the project into a new work space and run the project as a Java Application, and tests using JUnit.
You can use '-Ddictionary=<path to ur dictionary file>' VM arg to point to your dictionary file, if not the default one will be used. 
You will need to specify your input phone number files as a program arg.

Directories:  
src/main/java - Thats where the source files are.
src/main/test - Thats where the test files are.
src/main/resources - test input file and dictionary file.
bin - Thats where the compiled class files are.

## Running the application (Commandline)
# cd to the <bin> directory of the project 
# Update the following command to point to your dictionary and input file.  (if dictionary file is not supplied a default will be used.)
# Run:  java -Ddictionary="./dictionary.txt" com.arconex.exercise.Main ./testInput.txt
# Or (if you don't have an input file) 
# Run: java -Ddictionary="./dictionary.txt" com.arconex.exercise.Main

## Notes
# You will need Java 8 to run this.
# I think there are some abiguity around the actual requirement of the exercise specifically around the digit skipping bit.  If what I have done is not what's expected, I would appreciate a chance to explain what I have done.
# Any characters thats NOT a digit is used as a delimiter to break up a phone number into block (since no delimiter was specified.)
# All white spaces are removed from the input number.  E.g. 2255   .  6  3 will effectively be treated as 2255.63

