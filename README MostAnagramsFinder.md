# Most Anagrams Finder

## Objective
This project demonstrates the use of several data structures and sorting algorithms to identify the word(s) with the most anagrams from a dictionary file.

The program highlights:
- Object-oriented programming techniques, including the use of polymorphism.
- Comparative performance analysis of data structures (`BSTreeMap`, `RBTreeMap`, and `MyHashMap`).
- Efficient implementation of sorting and mapping techniques.

## Features
- Command-line interface for specifying the dictionary file and the data structure (`bst`, `rbt`, or `hash`).
- Input file validation and error handling.
- Integration of custom data structures (`BSTreeMap`, `RBTreeMap`, `MyHashMap`).
- Outputs anagram groups and their counts, sorted lexicographically.

## Requirements
- Java Development Kit (JDK)
- A dictionary file in Unix/Mac format (one word per line).

## How It Works
1. **Input Parsing**: The program validates and processes command-line arguments, ensuring a valid dictionary file and data structure selection.
2. **Data Mapping**: Each word in the dictionary is converted to a key-value pair:
   - The key is the sorted, lowercase form of the word.
   - The value is a list of all words that are anagrams of the key.
3. **Output**: The program outputs:
   - Total groups of anagrams and the total count.
   - Each anagram group sorted lexicographically.

