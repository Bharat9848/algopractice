package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Design a data structure that simulates an in-memory file system.
 *
 * Implement the FileSystem class:
 *
 *     FileSystem() Initializes the object of the system.
 *     List<String> ls(String path)
 *         If path is a file path, returns a list that only contains this file's name.
 *         If path is a directory path, returns the list of file and directory names in this directory.
 *     The answer should in lexicographic order.
 *     void mkdir(String path) Makes a new directory according to the given path. The given directory path does not exist. If the middle directories in the path do not exist, you should create them as well.
 *     void addContentToFile(String filePath, String content)
 *         If filePath does not exist, creates that file containing given content.
 *         If filePath already exists, appends the given content to original content.
 *     String readContentFromFile(String filePath) Returns the content in the file at filePath.
 *
 * Input
 * ["FileSystem", "ls", "mkdir", "addContentToFile", "ls", "readContentFromFile"]
 * [[], ["/"], ["/a/b/c"], ["/a/b/c/d", "hello"], ["/"], ["/a/b/c/d"]]
 * Output
 * [null, [], null, null, ["a"], "hello"]
 *
 * Explanation
 * FileSystem fileSystem = new FileSystem();
 * fileSystem.ls("/");                         // return []
 * fileSystem.mkdir("/a/b/c");
 * fileSystem.addContentToFile("/a/b/c/d", "hello");
 * fileSystem.ls("/");                         // return ["a"]
 * fileSystem.readContentFromFile("/a/b/c/d"); // return "hello"
 *
 *
 *
 * Constraints:
 *
 *     1 <= path.length, filePath.length <= 100
 *     path and filePath are absolute paths which begin with '/' and do not end with '/' except that the path is just "/".
 *     You can assume that all directory names and file names only contain lowercase letters, and the same names will not exist in the same directory.
 *     You can assume that all operations will be passed valid parameters, and users will not attempt to retrieve file content or list a directory or file that does not exist.
 *     1 <= content.length <= 50
 *     At most 300 calls will be made to ls, mkdir, addContentToFile, and readContentFromFile.
 */
public class P588DesignInMemoryFileSystem {

    private final Node root;
    public P588DesignInMemoryFileSystem() {
        root = new DirectoryNode("");
    }

    public List<String> ls(String path) {
        if("/".equals(path)){
            return ((DirectoryNode)root).listFileAndDirectoriesSorted();
        }
        DirectoryNode temp = (DirectoryNode) root;
        String[] pathParts = path.split("/");
        for (int i = 1; i < pathParts.length-1; i++) {
            String nodeStr = pathParts[i].trim();
            if(!nodeStr.isEmpty())
            {
                temp = temp.getDirectory(nodeStr);
            }
        }
        if(temp.getDirectory(pathParts[pathParts.length-1]) != null){
            return temp.getDirectory(pathParts[pathParts.length-1]).listFileAndDirectoriesSorted();
        } else{
            return Collections.singletonList(temp.getFile(pathParts[pathParts.length-1]).name());
        }
    }

    public void mkdir(String path) {
        DirectoryNode temp = (DirectoryNode) root;
        String[] pathParts = path.split("/");
        for (int i = 1; i < pathParts.length; i++) {
            String nodeStr = pathParts[i].trim();
            if(temp.getDirectory(nodeStr) == null){
                temp.addDirectory(new DirectoryNode(nodeStr));
            }
            temp = temp.getDirectory(nodeStr);
        }
    }

    public void addContentToFile(String filePath, String content) {
        DirectoryNode temp = (DirectoryNode) root;
        String[] pathParts = filePath.split("/");
        for (int i = 1; i < pathParts.length-1; i++) {
            String nodeStr = pathParts[i].trim();
            temp = temp.getDirectory(nodeStr);
        }
        String fileName = pathParts[pathParts.length - 1];
        FileNode fileNode = temp.getFile(fileName);
        if(fileNode == null){
            temp.addFile(new FileNode(fileName, content));
        }else {
           fileNode.appendContent(content);
        }
    }

    public String readContentFromFile(String filePath) {
        DirectoryNode temp = (DirectoryNode) root;
        String[] pathParts = filePath.split("/");
        for (int i = 1; i < pathParts.length-1; i++) {
            String nodeStr = pathParts[i].trim();
            temp = temp.getDirectory(nodeStr);
        }
        String fileName = pathParts[pathParts.length - 1];
        FileNode fileNode = temp.getFile(fileName);
        return fileNode.getContent();
    }

    private interface Node{
        boolean isDirectory();
        String name();
    }
    private class DirectoryNode implements Node {

        private Map<String, DirectoryNode> subDirectories;
        private Map<String, FileNode> files;
        private final String name;

        DirectoryNode(String name){
            this.name = name;
            subDirectories = new HashMap<>();
            files = new HashMap<>();
        }
        @Override
        public boolean isDirectory() {
            return true;
        }

        @Override
        public String name() {
            return name;
        }

        List<String> listFileAndDirectoriesSorted(){
            Set<String> subDirs = subDirectories.keySet();
            Set<String> fileNames = files.keySet();
            List<String> result = new ArrayList<>(subDirs.size() + fileNames.size());
            result.addAll(subDirs);
            result.addAll(fileNames);
            Collections.sort(result);
            return result;
        }

        DirectoryNode getDirectory(String name){
            return subDirectories.get(name);
        }

        FileNode getFile(String name){
            return files.get(name);
        }

        void addDirectory(DirectoryNode directoryNode) {
            subDirectories.put(directoryNode.name(), directoryNode);
        }

        void addFile(FileNode fileNode) {
            files.put(fileNode.name(), fileNode);
        }
    }

    private class FileNode implements Node{

        final private String name;
        final private StringBuilder content;

        FileNode(String name, String content) {
            this.name = name;
            this.content = new StringBuilder(content);
        }

        @Override
        public boolean isDirectory() {
            return false;
        }

        @Override
        public String name() {
            return name;
        }

        void appendContent(String cont){
            content.append(cont);
        }

        String getContent(){
            return content.toString();
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "FileSystem,ls,mkdir,mkdir,mkdir,ls|;/;/a/b/c;/a/b/c/d;/a/b/c/e;/a/b/c|null,,null,null,null,d;e",
            "FileSystem,ls,mkdir,addContentToFile,ls,readContentFromFile,addContentToFile;readContentFromFile|;/;/a/b/c;/a/b/c/d,hello;/;/a/b/c/d;/a/b/c/d,world;/a/b/c/d|null,,null,null,a,hello,null,helloworld",
            "FileSystem,mkdir,ls,ls,mkdir,ls,ls,addContentToFile,ls,ls,ls|;/goowmfn;/goowmfn;/;/z;/;/;/goowmfn/c,shetopcy;/z;/goowmfn/c;/goowmfn|null,null,,goowmfn,null,goowmfn;z,goowmfn;z,null,,c,c"
    })
    void test(String operationStr, String argStr, String expectedStr){
        String[] operations = operationStr.split(",");
        String[] arguments = argStr.split(";");
        String[] expected = expectedStr.split(",");
        P588DesignInMemoryFileSystem fs = null;
        for (int i = 0; i < operations.length; i++) {
            String operation = operations[i];
            switch (operation) {
                case "FileSystem":
                    fs = new P588DesignInMemoryFileSystem();
                    break;
                case "ls": {
                    if (expected[i].trim().isEmpty()) {
                        Assert.assertTrue(fs.ls(arguments[i]).isEmpty());
                    } else {
                        List<String> expectedList = Arrays.stream(expected[i].split(";")).collect(Collectors.toList());
                        Assert.assertEquals(expectedList, fs.ls(arguments[i]));
                    }
                }
                break;
                case "mkdir": {
                    fs.mkdir(arguments[i]);
                }
                break;
                case "addContentToFile": {
                    String[] arg = arguments[i].split(",");
                    fs.addContentToFile(arg[0], arg[1]);
                }
                break;

                case "readContentFromFile": {
                    Assert.assertEquals(expected[i], fs.readContentFromFile(arguments[i]));
                }

                break;

                default:
            }
        }
    }
}
