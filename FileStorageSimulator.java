import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

/**
 * Created by Aldi Firmansyah 1606917790 on 11/29/2017.
 */
public class FileStorageSimulator {
    private static Folder root = new Folder("root");
    public static void main (String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));


        String input;
        String[] splitted;
        while ((input = in.readLine()) != null) {
            splitted = input.split(" ");
            Folder targetFolder;
            switch (splitted[0]) {
                case "add":
                    targetFolder = findFolder(new Folder(splitted[2]));
                    targetFolder.addFolder(new Folder(splitted[1]));
                    break;

                case "insert":
                    String[] nameAndType = splitted[1].split("\\.");
                    targetFolder = findFolder(new Folder(splitted[3]));

                    //targetFolder is Folder where File is inserted
                    targetFolder = targetFolder.insertFile(new File(nameAndType[0], nameAndType[1], Integer.parseInt(splitted[2])));
                    if (targetFolder!=null) {
                        System.out.println(splitted[1] + " added to " + targetFolder.getName());
                    }
                    break;

                case "print":
                    targetFolder = findFolder(new Folder(splitted[1]));
                    targetFolder.print();
                    break;  

                case "remove":
                    targetFolder = findFolder(new Folder(splitted[1]));
                    if (targetFolder != null) {
                        targetFolder.getParentFolder().removeFolder(targetFolder);
                        System.out.println("Folder " + splitted[1] + " removed");
                    }
                    else {
                        int removedFiles = removeFiles(new File(splitted[1]));
                        System.out.println(removedFiles + " File " + splitted[1] + " removed");
                    }
                    break;

                case "search":
                    targetFolder = findFolder(new Folder(splitted[1]));
                    if (targetFolder!=null) {
                        printFolder(targetFolder);
                    }
                    else printFiles(splitted[1]);
            }
        }
    }

    private static Folder findFolder(Folder folder) {
        Folder currentFolder = root;
        while (true) {
            if (currentFolder == null) {
                return null;
            }
            if (currentFolder.equals(folder)) {
                return currentFolder;
            }
            if (currentFolder.getFolders() != null) {
                currentFolder = currentFolder.getFolders().get(0);
            }
            else {
                currentFolder = currentFolder.next();
            }
        }
    }

    private static int removeFiles(File file) {
        int counter = 0;
        Folder currentFolder = root;
        ArrayList<File> files; //variable for store temporary currentFolder files

        //while loop until currentFolder is null
        while (currentFolder != null){

            //check all files if the Folder store files
            if (currentFolder.getFiles() != null) {

                files = currentFolder.getFiles();

                for (int i = files.size()-1; i >= 0; i--) {
                    if (files.get(i).equals(file)) {

                        currentFolder.removeFile(i);
                        counter++;
                    }
                }

                //set files to null if its empty
                if (currentFolder.getFiles().isEmpty()) {
                    currentFolder.setFiles(null);
                }
            }

            //set currentFolder to next Folder for checking
            if (currentFolder.getFolders() != null) {
                currentFolder = currentFolder.getFolders().get(0);
            }
            else {
                currentFolder = currentFolder.next();
            }
        }
        return counter;
    }

    private static void printFolder(Folder f) {
        Stack<Folder> s = new Stack<>();
        Folder currentFolder = f;

        while (currentFolder != null) {
            s.push(currentFolder);
            currentFolder = currentFolder.getParentFolder();
        }

        int indentTab = 0;
        while (!s.isEmpty()) {
            for (int i = 0; i < indentTab; i++) {
                System.out.print("  ");
            }
            System.out.println("> " + s.pop().getName());
            indentTab++;
        }
    }

    //print all File path with the same name
    private static void printFiles(String fileName) {
        //store all printedFolder so the same Folder wont be printed
        ArrayList<Folder> printedFolder = new ArrayList<>();
        //store all files to be printed
        ArrayList<File> files = searchFiles(new File(fileName));

        for (int i = 0; i<files.size(); i++) {
            printedFolder = printFile(files.get(i), printedFolder);
        }
    }
    //method helper to find all files to be printed
    private static ArrayList<File> searchFiles(File file) {
        Folder currentFolder = root;
        ArrayList<File> files; //for temporary store files children
        ArrayList<File> returnFiles = new ArrayList<>();//for return

        while (currentFolder != null){

            //check all files if the Folder store files
            if (currentFolder.getFiles() != null) {

                files = currentFolder.getFiles();

                for (int i = 0; i < files.size(); i++) {
                    if (files.get(i).equals(file)) {

                        //////.x/c'
                        returnFiles.add(files.get(i));
                    }
                }
            }

            //set currentFolder to next Folder for checking
            if (currentFolder.getFolders() != null) {
                currentFolder = currentFolder.getFolders().get(0);
            }
            else {
                currentFolder = currentFolder.next();
            }

        }

        return returnFiles;
    }

    //Method helper to print a single File path and return Arraylist that updated with Folder printed
    private static ArrayList<Folder> printFile(File file, ArrayList<Folder> printed){
        //store all the parent of File
        ArrayList<Folder> printedArr = printed;
        Stack<Folder> s = new Stack<>();
        Folder currentFolder = file.getParentFolder();

        while (currentFolder!= null) {
            s.push(currentFolder);
            currentFolder = currentFolder.getParentFolder();
        }

        int indentTab = 0;
        while (!s.isEmpty()) {
            currentFolder = s.pop();
            if (!currentFolder.isInArr(printedArr)) {
                for (int i = 0; i < indentTab; i++) {
                    System.out.print("  ");
                }
                System.out.println("> " + currentFolder.getName());
                printedArr.add(currentFolder);
            }
            indentTab++;
        }
        for (int i = 0; i<indentTab; i++) {
            System.out.print("  ");
        }
        System.out.println("> " + file.getName() + "." + file.getFileType());
        return printedArr;
    }

   // private static void search ()
}

class File implements Comparable<File> {

    private String name;
    private String fileType;
    private int size;
    private Folder parentFolder;

    public File(String name) {
        this.name = name;
    }

    public File(String name, String fileType, int size) {
        this.name = name;
        this.fileType = fileType;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Folder getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(Folder parentFolder) {
        this.parentFolder = parentFolder;
    }

    public void print(int indentTab) {
        for (int i = 0; i < indentTab; i++) {
            System.out.print("  ");
        }
        System.out.println("> " + name + "." + fileType + " " + size);
    }

    public boolean equals (Object o) {
        if (this == o) return true;

        if (o == null) return false;

        if (getClass() != o.getClass()) return false;

        File file = (File) o;
        return file.getName().equals(name);
    }

    @Override
    public int compareTo(File o) {
        if (!name.equals(o.getName())) return name.compareTo(o.getName());

        else if (!fileType.equals(o.getFileType())) return fileType.compareTo(o.getFileType());

        else return o.getSize()-size;
        //return name.compareTo(o.getName());
    }
}

class Folder implements Comparable<Folder> {

    private String name;
    private int size;
    private Folder parentFolder;
    //If Folder type is folders, files will be null and otherwise
    private ArrayList<Folder> folders;
    private ArrayList<File> files;

    public Folder(String name) {
        this.name = name;
        this.size = 1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Folder getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(Folder parentFolder) {
        this.parentFolder = parentFolder;
    }

    public ArrayList<Folder> getFolders() {
        return folders;
    }

    public void setFolders(ArrayList<Folder> folders) {
        this.folders = folders;
    }

    public ArrayList<File> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<File> files) {
        this.files = files;
    }

    public void addFolder(Folder folder) {
        if (folders == null && files == null) {
            folders = new ArrayList<>();
            folders.add(folder);
        }
        else if (files == null) {
            folders.add(folder);
        }
        else {
            migrateFiles(this, folder);
            folders = new ArrayList<>();
            folders.add(folder);
        }
        //change Folder parent, change parents size, and sort parent's folders by name
        folder.setParentFolder(this);
        increaseSize(folder.getSize());
        Collections.sort(folders);
    }

    //return Folder which File is inserted or null if File is not inserted
    public Folder insertFile(File file) {
        Folder currentFolder = this;
        ArrayList<Folder> visitedFolder = new ArrayList<>();

        //looping until file inserted or file cannot be inserted to any Folder
        while (true) {
            if (currentFolder == null) {
                return null;
            }

            //add File if currFolder doesnt have any files and folders
            if (currentFolder.getFolders() == null && currentFolder.getFiles() == null) {
                currentFolder.setFiles(new ArrayList<File>());
                currentFolder.getFiles().add(file);
                currentFolder.increaseSize(file.getSize());
                file.setParentFolder(currentFolder);
                return currentFolder;
            }

            else if (currentFolder.getFiles() != null) {
                //if currFolder has files and it store the same type of Files, insert
                if (currentFolder.getFiles().get(0).getFileType().equals(file.getFileType())) {
                    currentFolder.getFiles().add(file);
                    currentFolder.increaseSize(file.getSize());
                    file.setParentFolder(currentFolder);
                    Collections.sort(currentFolder.getFiles());
                    return currentFolder;
                }

                else {
                    visitedFolder.add(currentFolder);
                    currentFolder = currentFolder.nextFolder(visitedFolder);
                }
            }
            else {
                visitedFolder.add(currentFolder);
                currentFolder = currentFolder.nextFolder(visitedFolder);
            }
        }

    }
    //method helper for nextFolder check in insertFile
    private Folder nextFolder(ArrayList<Folder> visited) {
        //return children Folder if its not visited
        if (folders != null) {
            if (!folders.get(0).isInArr(visited)) {
                return folders.get(0);
            }
        }

        //if Folder has siblings folder and has not visited, visit
        if (hasSiblings()) {
            if (!nextSiblings().isInArr(visited)) {
                return nextSiblings();
            }
        }
        return parentFolder;
    }
    public boolean isInArr(ArrayList<Folder> arr) {
        for (Folder f: arr) {
            if (f.equals(this)) {
                return true;
            }
        }
        return false;
    }
    private boolean hasSiblings() {
        if (parentFolder == null) {
            return false;
        }
        if (parentFolder.getFolders().size() == 1) {
            return false;
        }
        return true;
    }
    //return next index of Folder or first index if last index is reached
    private Folder nextSiblings() {
        ArrayList<Folder> siblings = parentFolder.getFolders();
        for (int i = 0; i < siblings.size(); i++) {
            if (siblings.get(i).equals(this)) {
                try {
                    return siblings.get(i+1);
                }
                catch (IndexOutOfBoundsException e) {
                    return siblings.get(0);
                }
            }
        }
        return null;
    }

    //delete Folder with parameter the desired Folder to delete
    public void removeFolder(Folder folder) {
        for (int i = 0; i < folders.size(); i++) {
            if (folders.get(i).equals(folder)) {
                decreaseSize(folder.size);
                folders.remove(i);
            }
        }
        if (folders.isEmpty()) {
            folders = null;
        }
    }

    //delete File with parameter index in files
    public void removeFile(int index) {
        int sizeRemoved = files.get(index).getSize();
        files.remove(index);
        decreaseSize(sizeRemoved);
    }

    //Print all Folders and Files with this Folder as root
    public void print() {
        print(0);
    }
    public void print (int indentTab) {
        for (int i = 0; i < indentTab; i++) {
            System.out.print("  ");
        }
        System.out.println("> " + name + " " + size);

        if (folders != null) {
            for (Folder folder: folders) {
                folder.print(indentTab + 1);
            }
        }
        else if (files != null) {
            for (File file: files) {
                file.print(indentTab + 1);
            }
        }
    }

    //Return next Folder or sibling of Parent and so on (method helper for
    public Folder next() {
        if (parentFolder == null) {
            return null;
        }
        ArrayList<Folder> folderSiblings = this.getParentFolder().getFolders();
        int index = 0;
        for (int i = 0; i < folderSiblings.size(); i++) {
            if (this.equals(folderSiblings.get(i))) {
                index = i;
                break;
            }
        }

        try {
            return folderSiblings.get(index+1);
        }
        catch (IndexOutOfBoundsException e) {
            return getParentFolder().next();
        }
    }

    public void increaseSize(int size) {
        Folder currentFolder = this;

        while (currentFolder != null) {
            currentFolder.setSize(currentFolder.getSize() + size);
            currentFolder = currentFolder.parentFolder;
        }
    }

    private void decreaseSize(int size) {
        Folder currentFolder = this;

        while (currentFolder != null) {
            currentFolder.setSize(currentFolder.getSize() - size);
            currentFolder = currentFolder.parentFolder;
        }
    }

    //For move files from source Folder to target Folder
    private void migrateFiles(Folder source, Folder target) {
        ArrayList<File> migratedFiles = source.getFiles();
        for (File file: migratedFiles) {
            file.setParentFolder(target);
        }

        target.setSize(source.getSize()); //change the target size with source size
        source.decreaseSize(source.getSize()-1); //reduce size with files size

        //Move files to target Folder
        target.setFiles(migratedFiles);
        source.setFiles(null);
    }

    public boolean equals (Object o) {
        if (this == o) return true;

        if (o == null) return false;

        if (getClass() != o.getClass()) return false;

        Folder folder = (Folder) o;
        return folder.getName().equals(name);
    }

    @Override
    public int compareTo(Folder o) {
        return name.compareTo(o.getName());
    }
}