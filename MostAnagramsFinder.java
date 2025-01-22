import java.io.*;
import java.util.Arrays;
import java.util.*;
//only three imports allowed


/**
 * Javadoc 1
 * as defined in the spec, this program uses several data structures and
 * a sorting algorithm to find the word or words with the most anagrams.
 * the three data structures are a hashmap, a red-black tree map and a
 * binary search tree.
 * Anthony Saidenberg ajs2461
 *
 */
public class MostAnagramsFinder {
    private MyMap<String, MyList<String>> map;

    /**
     * Javadoc 2
     * the main method takes two arguments - the text file
     * and the type of data strucute eg hashmap
     *
     * @param args
     */
    public static void main(String[] args) {
        //make sure we get only the file and the datastructure requested
        if (args.length != 2) {
            System.err.println("Usage: java Most AnagramsFinder <dictionary file> <bst|rbt|hash>");
            //tells user we need two arguments and what those arguments can be
            System.exit(1);
        }

        //reiterating what we said in the javadoc
        String fileName = args[0];
        String dataStructure = args[1].toLowerCase();
        MostAnagramsFinder anagStore = new MostAnagramsFinder();
        //creating an object of the actual class

        //essentially makes the map according to hwat the user wants
        switch (dataStructure) {
            case "bst":
                anagStore.map = new BSTreeMap<>();
                break;
            case "rbt":
                anagStore.map = new RBTreeMap<>();
                break;

            case "hash":
                anagStore.map = new MyHashMap<>();
                break;

            default:
                System.err.println("Error: Invalid data structure ' + dataStructure + 'received.");
                System.exit(1);
        }


        anagStore.makeAnagramsFromFile(fileName);
        anagStore.outputsResults();


    }


    /**
     * Javadoc 3
     * essentially loads the file into the map
     *
     * @param fileName
     */
    private void makeAnagramsFromFile(String fileName) {
        //not allowed to use exceptions so do this instead
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            while((line = reader.readLine()) != null) {
                //reads each line from file, deletes white space and continues if line is empty
                String word = line.trim();
                if (word.isEmpty()) continue;
                String anagkey = getSortedLowerCaseKey(word);
                MyList<String> anagsList = map.get(anagkey);

                //if there is no linked list create one
                if(anagsList == null) {
                    anagsList = new MyLinkedList<>();
                    map.put(anagkey, anagsList);
                }

                //
                anagsList.add(word);
            }

            //print statements and exit if errors
        } catch(FileNotFoundException e) {
            System.err.println("Error: Cannot open file '" + fileName + "' for input.");
            System.exit(1);
        } catch(IOException e) {
            System.err.println("Error: An I/O error occured reading '" + fileName + "'.");
            System.exit(1);


        }
        //don't need to close file with changes

    }

    /**
     * Javadoc 4
     * method sorts the characters of the word in lowercaseto form a key for the map
     *
     * @param word
     * @return
     */
    private String getSortedLowerCaseKey(String word) {
        //convert to lowercase and sort
        char[] charsarray = word.toLowerCase().toCharArray();
        insertionSort(charsarray);

        return new String(charsarray);
    }

    /**
     * Javadoc 5
     * uses insertion sort for the array
     *
     * @param array
     */
    private void insertionSort(char[] array) {
        //its just insertion sort
        for (int i = 1; i < array.length; i++) {

            char anagkey = array[i];
            int j = i -1;
            while(j >= 0 && array[j] > anagkey) {
                array[j + 1] = array[j];
                j--;
            }
            array[j +1] = anagkey;

        }
    }

    /**
     * Javadoc 6
     * outputs results to user
     */
    private void outputsResults() {
        int max = 0;
        int numgroups = 0; //num groups with max number

        AnagramGroupList resultsToOutput = new AnagramGroupList();

        //MyList<MyList<String>> resultsToOutput = new MyLinkedList<>();

        Iterator<Entry<String, MyList<String>>> iterator = map.iterator();

        //finds groups with most anagrams
        while(iterator.hasNext()) {

            Entry<String, MyList<String>> entry = iterator.next();
            MyList<String> anagrams = entry.value;
            if (anagrams.size() > 1) {
                //System.out.print(anagrams)
                if (anagrams.size() > 1) {
                    if (anagrams.size() > max) {
                        max = anagrams.size();
                        //System.out.print(anagrams)
                        resultsToOutput.clear();
                        resultsToOutput.add(anagrams);
                        numgroups = 1;

                    } else if (anagrams.size() == max) {
                        //System.out.print(anagrams)
                        resultsToOutput.add(anagrams);
                        numgroups++;

                    }
                }
            }
        }
        //outputs results to user
        if (resultsToOutput.isEmpty()) {
            System.out.println("No anagrams found.");

        } else {
            System.out.printf("Groups: %d, Anagram count: %d%n", numgroups, max);
            insertionSort(resultsToOutput);
            Iterator<MyList<String>> resIterator = resultsToOutput.iterator();

            //have to output each group, but ensure in correct order
            while(resIterator.hasNext()) {
                MyList<String> group = resIterator.next();
                insertionSort(group);
                System.out.println(createStringForm(group));
            }

        }


    }

    /**
     * Javadoc 7
     * sorts list of words in the correct order
     *
     * @param lis
     */
    private void insertionSort(MyList<String> lis) {


        for (int i = 1; i < lis.size(); i++) {
            String anagskey = lis.get(i);
            int j = i - 1;

            while (j >= 0 && lis.get(j).compareTo(anagskey) > 0) {
                lis.set(j + 1, lis.get(j));
                j--;
            }
            lis.set(j + 1, anagskey);
        }
    }



    /**
     * Javadoc 8
     * creates a string from the anagrams
     *
     * @param group
     * @return
     */
    private String createStringForm(MyList<String> group) {
        //create a string from the anagrams
        StringBuilder builder = new StringBuilder("[");
        Iterator<String> iterator = group.iterator();

        while (iterator.hasNext()) {

            builder.append(iterator.next());
            if (iterator.hasNext()) {
                builder.append(", ");
            }
        }
        builder.append("]");
        return builder.toString();
    }

    /**
     * Javadoc 9
     * sorts the lists of anagrams in the correct order
     * do not think I should have to do this but with different data structures
     * it is giving me different results
     *
     * @param lis
     */
    private void insertionSort(AnagramGroupList lis) {
        //dont know why my data structures are giving differnt orders
        //this solved it and sorted them correctly
        //another insertion sort overload
        for(int i =1; i < lis.size(); i++) {
            MyList<String> keyList = lis.get(i);
            String anagskey = keyList.get(0);

            int j = i-1;

            while(j >= 0 && lis.get(j).get(0).compareTo(anagskey) > 0) {
                lis.set(j + 1, lis.get(j));
                j--;
            }

            lis.set(j +1, keyList);
        }
    }
}

class AnagramGroupList extends MyLinkedList<MyList<String>> {
}




