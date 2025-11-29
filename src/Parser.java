/**
 * ---------------------------------------------------------------
 * Parser.java
 * 
 * PURPOSE:
 *     Implements an XML well-formedness checker using the custom
 *     MyStack class created in Assignment 2. The parser:
 *
 *       • Reads an XML file line-by-line.
 *       • Extracts XML tags (<tag>, </tag>, <tag/>, <?...?>).
 *       • Uses a stack to ensure correct tag nesting.
 *       • Validates opening/closing tag matching.
 *       • Detects self-closing tags.
 *       • Detects mismatched tags.
 *       • Detects unexpected closing tags.
 *       • Detects unclosed tags at end-of-file.
 *       • Ensures exactly one root element exists.
 *
 * USAGE (after export as JAR):
 *     java -jar Parser.jar <input.xml>
 *
 * ---------------------------------------------------------------
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import implementations.MyStack;

public class Parser {

    /**
     * Program entry point.
     * Ensures command-line arguments are correct and initiates parsing.
     */
    public static void main(String[] args) {

        // Must provide exactly 1 argument: the XML file name.
        if (args.length == 0) {
            System.out.println("Usage: java -jar Parser.jar <input.xml>");
            return;
        }

        String fileName = args[0];

        // Read file lines into memory.
        List<String> lines = readFile(fileName);

        // Null means file could not be opened.
        if (lines == null) {
            System.out.println("Error: Couldn't read file: " + fileName);
            return;
        }

        // Begin parsing.
        parseXML(lines);
    }

    /**
     * Reads an XML file into a List<String>,
     * one trimmed line per entry.
     */
    private static List<String> readFile(String fileName) {

        List<String> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            String line;

            // Read file line-by-line
            while ((line = br.readLine()) != null) {
                list.add(line.trim());  // remove leading/trailing spaces
            }

        } catch (IOException ex) {
            return null; // signals failure
        }

        return list;
    }

    /**
     * Main XML parsing logic using MyStack<String>.
     * Follows assignment rules:
     *   - Opening tags push onto stack
     *   - Closing tags pop from stack
     *   - Ensures proper nesting order
     *   - Handles processing instructions and self-closing tags
     */
    private static void parseXML(List<String> lines) {

        MyStack<String> stack = new MyStack<>();

        boolean hasRoot = false;  // has first root-level opening tag appeared?
        String rootName = null;   // name of root element
        int rootCount = 0;        // count number of root elements
        int lineNum = 0;          // current line number

        // Process each line
        for (String line : lines) {
            lineNum++;

            // Extract all <...> tags appearing inside this line.
            List<String> tags = extractTags(line);

            // Process each tag found
            for (String tag : tags) {

                // Skip XML processing instructions like <?xml version="1.0"?>
                if (tag.startsWith("<?") && tag.endsWith("?>")) {
                    continue;
                }

                // Skip self-closing tags (<tag/> or <tag attr="x" />)
                if (isSelfClosing(tag)) {
                    continue;
                }

                // ---------------------------------------------------------
                //  CLOSING TAG: </tag>
                // ---------------------------------------------------------
                if (tag.startsWith("</")) {

                    // Extract name inside closing tag.
                    String closingName = tag.substring(2, tag.length() - 1);

                    // If stack is empty → no matching opening tag exists.
                    if (stack.isEmpty()) {
                        printError(lineNum,
                                "Closing tag </" + closingName + "> has no matching opening tag");
                        continue;
                    }

                    // Pop most recent opening tag
                    String openingName = stack.pop();

                    // If names differ → mismatched tags
                    if (!openingName.equals(closingName)) {
                        printError(lineNum,
                                "Tag <" + openingName + ">is closed by </" + closingName + ">");
                    }

                    continue; // closing tag handled
                }

                // ---------------------------------------------------------
                //  OPENING TAG: <tag> or <tag attr="value">
                // ---------------------------------------------------------
                if (tag.startsWith("<") && tag.endsWith(">")) {

                    // Extract tag name without attributes
                    String openingName = extractTagName(tag);

                    // First root-level tag detected
                    if (!hasRoot) {
                        hasRoot = true;
                        rootName = openingName;
                        rootCount = 1;

                    // If stack is empty but root already exists → another root
                    } else if (stack.isEmpty()) {
                        rootCount++;
                        printError(lineNum,
                            "Multiple root elements detected: <" + rootName +
                            "> and <" + openingName + ">.");
                    }

                    // Push opening tag onto the stack
                    stack.push(openingName);
                }
            }
        }

        // ---------------------------------------------------------
        //  AFTER REACHING END OF FILE
        // ---------------------------------------------------------
        // Any tags left on stack were never closed.
        while (!stack.isEmpty()) {
            String unclosed = stack.pop();
            printError(lineNum,
                    "Tag <" + unclosed + "> was never closed.");
        }

        // If no root element ever appeared → invalid XML
        if (!hasRoot) {
            printError(1, "No root element found in document.");
        }
    }

    /**
     * Extracts every "<...>" tag substring from the given line.
     */
    private static List<String> extractTags(String line) {

        List<String> tags = new ArrayList<>();

        int start = 0;

        // Repeatedly find '<' then '>' pairs
        while ((start = line.indexOf("<", start)) != -1) {
            int end = line.indexOf(">", start);

            // Malformed line with no closing '>'
            if (end == -1) {
                break;
            }

            tags.add(line.substring(start, end + 1));
            start = end + 1; // continue searching after this tag
        }

        return tags;
    }

    /**
     * Detects self-closing tags (<tag/> or <tag ... />).
     */
    private static boolean isSelfClosing(String tag) {

        if (!tag.startsWith("<") || !tag.endsWith(">")) return false;

        // Remove < > and trim
        String inner = tag.substring(1, tag.length() - 1).trim();

        // A trailing slash indicates self-closing
        return inner.endsWith("/");
    }

    /**
     * Extracts only the tag name, removing attributes and slashes.
     * Example: <Driver code="123"> → "Driver"
     */
    private static String extractTagName(String tag) {

        // Remove < >
        String inner = tag.substring(1, tag.length() - 1).trim();

        // Remove trailing slash if present (<tag/>)
        if (inner.endsWith("/")) {
            inner = inner.substring(0, inner.length() - 1).trim();
        }

        // Remove everything after first space (attributes)
        int spaceIndex = inner.indexOf(' ');
        if (spaceIndex != -1) {
            inner = inner.substring(0, spaceIndex);
        }

        return inner;
    }

    /**
     * Prints formatted error messages.
     */
    private static void printError(int lineNum, String msg) {
        System.out.println("[Line " + lineNum + "] " + msg);
    }
}