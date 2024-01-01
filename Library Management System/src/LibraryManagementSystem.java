import java.time.temporal.ChronoUnit;
import java.util.*;
import java.time.*;

class Book{
    private int bookId;
    private String bookName;
    private String bookAuthor;
    private int bookQuantity;


    public Book(int bookId,String bookName,String bookAuthor,int bookQuantity){
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookQuantity = bookQuantity;

    }
    public int getBookId(){

        return bookId;
    }
    public String getBookName(){

        return bookName;
    }
    public String getBookAuthor(){

        return bookAuthor;
    }
    public int getBookQuantity(){

        return bookQuantity;
    }
    public void decreaseQuantity(){
        if (bookQuantity > 0){
            bookQuantity--;
        }
    }
    public void increaseQuantity() {
        bookQuantity++;
    }

}

class Student{
    private String studentName;
    private int studentRoll;
    private int studentSemester;
    private String studentBranch;
    private int issuedBooksCount = 0;


    public Student(String studentName,int studentRoll,int studentSemester,String studentBranch){
        this.studentName=studentName;
        this.studentRoll=studentRoll;
        this.studentSemester=studentSemester;
        this.studentBranch=studentBranch;
    }
    public String getStudentName(){

        return studentName;
    }
    public String getStudentBranch(){

        return studentBranch;
    }
    public int getStudentRoll(){

        return studentRoll;
    }
    public int getStudentSemester(){

        return studentSemester;
    }
    public int getIssuedBooksCount() {
        return issuedBooksCount;
    }
    public void incrementIssuedBooksCount() {

        issuedBooksCount++;
    }
    public void decrementIssuedBooksCount() {

        issuedBooksCount--;
    }
}

class LibraryRecord{
    private Student student;
    private Book book;
    private LocalDate issueDate;
    private LocalDate dueDate;

    public LibraryRecord(Book book, Student student, LocalDate issueDate, LocalDate dueDate){
        this.student = student;
        this.book = book;
        this.issueDate = issueDate;
        this.dueDate= dueDate;
    }
    public Student getStudent(){

        return student;
    }
    public Book getBook(){

        return book;
    }

    public LocalDate getIssueDate() {

        return issueDate;
    }

    public LocalDate getDueDate() {

        return dueDate;
    }
}

public class LibraryManagementSystem {

    private ArrayList<Student> registeredStudents;
    private ArrayList<Book> libraryBooks;
    private ArrayList<LibraryRecord> libraryRecords;


    public LibraryManagementSystem(){
        registeredStudents = new ArrayList<>();
        libraryBooks = new ArrayList<>();
        libraryRecords = new ArrayList<>();
    }

    public void addStudent(String studentName,int studentRoll , int studentSemester ,String studentBranch){
        for (Student existingStudent : registeredStudents) {
            if (existingStudent.getStudentRoll() == studentRoll && existingStudent.getStudentBranch().equalsIgnoreCase(studentBranch)) {
                System.out.println("Error: Student with the same Roll Number and Branch already exists. Student not added.");
                return;
            }
        }
        Student newStudent = new Student(studentName,studentRoll,studentSemester,studentBranch);
        registeredStudents.add(newStudent);
        System.out.println("Student added successfully.");
    }

    public void addBook(int bookID, String bookName , String bookAuthor , int bookQuantity){
        Book newBook = new Book(bookID,bookName,bookAuthor,bookQuantity);
        libraryBooks.add(newBook);
    }

    public void displayStudents(){
        for (Student student:registeredStudents){
            System.out.println();
            System.out.println("Student Name :- "+student.getStudentName());
            System.out.println("Student Roll :- "+student.getStudentRoll());
            System.out.println("Student Branch :- "+student.getStudentBranch());
            System.out.println("Student Semester :- "+student.getStudentSemester());
            System.out.println("-----------------------------");
        }
    }

    public void displayBooks(){
        for (Book book:libraryBooks){
            System.out.println();
            System.out.println("Book ID :- "+book.getBookId());
            System.out.println("Book Name :- "+book.getBookName());
            System.out.println("Book Author :- "+book.getBookAuthor());
            System.out.println("Book Quantity :- "+book.getBookQuantity());
            System.out.println("-------------------------------------");
        }
    }

    public void issueBook(Student student,Book book){
        if (student.getIssuedBooksCount() >= 2) {
            System.out.println("Error: You have already issued 2 books. Cannot issue more books until the previous ones are returned.");
            return;
        }
        if (book.getBookQuantity() > 0){
            LocalDate issueDate = LocalDate.now();
            LocalDate dueDate = LocalDate.now().plusDays(15);
            LibraryRecord record = new LibraryRecord(book,student,issueDate,dueDate);
            libraryRecords.add(record);
            book.decreaseQuantity();
            student.incrementIssuedBooksCount();
            System.out.println("Book Issued Successfully");
        }
        else {
            System.out.println("Book is not Available for Issue.We are Sorry :(");
        }
    }

    public void displayIssuedBook(){
        System.out.println("Issued Books :-");
        for(LibraryRecord record :libraryRecords){
            Student student = record.getStudent();
            Book book = record.getBook();
            System.out.println();
            System.out.println("Student Name :- "+student.getStudentName());
            System.out.println("Student Roll :- "+student.getStudentRoll());
            System.out.println("Book Name :- "+book.getBookName());
            System.out.println("Book Author :- "+book.getBookAuthor());
            System.out.println("Issuing Date :- "+record.getIssueDate());
            System.out.println("Due Date :- "+record.getDueDate());
            System.out.println("----------------------");


        }
    }
    public void displayReturnIssueBook(){
        System.out.println("Issued Books :-");
        for(LibraryRecord record :libraryRecords){
            Student student = record.getStudent();
            Book book = record.getBook();
            System.out.println();
            System.out.println("Book ID :- "+book.getBookId());
            System.out.println("Student Roll :- "+student.getStudentRoll());
            System.out.println("----------------------");


        }
    }

    public void returnBook(int bookID, int studentRoll) {
        for (LibraryRecord record : libraryRecords) {
            Student student = record.getStudent();
            Book book = record.getBook();
            if (book.getBookId() == bookID && student.getStudentRoll() == studentRoll) {
                LocalDate returnDate = LocalDate.now();
                LocalDate dueDate = record.getDueDate();
                long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
                if (daysLate <= 0) {
                    System.out.println("Book returned on time. No fine issued.");
                } else {
                    int fine = 20 * (int) daysLate;
                    System.out.println("Book returned late. Fine issued: Rs " + fine);
                }
                libraryRecords.remove(record);
                book.increaseQuantity();
                System.out.println("Book returned successfully.");
                return;
            }
        }
        System.out.println("No matching record found. Unable to return the book.");
    }


    public static void main(String[] args) {
        LibraryManagementSystem lms = new LibraryManagementSystem();

        lms.addStudent("Newton",40501,4,"Mechanical");
        lms.addStudent("Ramanujan",40502,3,"Electrical");

        lms.addBook(001,"Programming in C++","Opera",10);
        lms.addBook(002,"Coding in Java","P.Banerjee",12);
        lms.addBook(003,"Management Professional","K.Das",2);

        Scanner sc = new Scanner(System.in);

        int choice;
        do {
            System.out.println();
            System.out.println("Library Management System Menu:");
            System.out.println("1. Register a Student");
            System.out.println("2. Add a Book");
            System.out.println("3. Display Students");
            System.out.println("4. Display Books");
            System.out.println("5. Issue a Book");
            System.out.println("6. Display Issued Books");
            System.out.println("7. Return Issued Book");
            System.out.println("8. Exit");
            System.out.println();
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.println();
                    System.out.print("Enter Student Name: ");
                    String studentName = sc.nextLine();
                    System.out.print("Enter Student Roll number: ");
                    int studentRoll = sc.nextInt();
                    System.out.print("Enter Semester: ");
                    int studentSemester = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Branch: ");
                    String studentBranch = sc.nextLine();

                    if (studentName.isEmpty() || studentBranch.isEmpty()) {
                        System.out.println("Error: Student Name and Branch are required. Student not added.");
                    } else {
                        lms.addStudent(studentName, studentRoll, studentSemester, studentBranch);
                    }
                    break;

                case 2:
                    System.out.println();
                    System.out.print("Enter Book ID: ");
                    int bookID = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Book Name: ");
                    String bookName = sc.nextLine();
                    System.out.print("Enter Author Name: ");
                    String bookAuthor = sc.nextLine();
                    System.out.print("Enter Quantity: ");
                    int bookQuantity = sc.nextInt();

                    if (bookName.isEmpty() || bookAuthor.isEmpty() || bookQuantity <= 0) {
                        System.out.println("Error: Book Name, Author Name, and Quantity are required, and Quantity should be greater than 0. Book not added.");
                    } else {
                        lms.addBook(bookID, bookName, bookAuthor, bookQuantity);
                        System.out.println("Book added successfully.");
                    }
                    break;
                case 3:
                    lms.displayStudents();
                    break;
                case 4:
                    lms.displayBooks();
                    break;
                case 5:
                    lms.displayStudents();
                    lms.displayBooks();
                    System.out.print("Enter Student Roll Number :- ");
                    studentRoll = sc.nextInt();
                    sc.nextLine();
                    Student student = lms.findStudentByRoll(studentRoll);
                    if (student == null) {
                        System.out.println("Student Not Found.");
                        break;
                    }
                    System.out.print("Enter the Book Name to Issue: ");
                    String bookToIssue = sc.nextLine();
                    Book book = lms.findBookByName(bookToIssue);
                    if (book == null) {
                        System.out.println("Book Not Found.");
                        break;
                    }
                    lms.issueBook(student, book);
                    break;
                case 6:
                    lms.displayIssuedBook();
                    break;
                case 7:
                    lms.displayReturnIssueBook();
                    System.out.print("Enter Book ID to Return: ");
                    int returnBookID = sc.nextInt();
                    System.out.print("Enter Student Roll to Return: ");
                    int returnStudentRoll = sc.nextInt();
                    lms.returnBook(returnBookID, returnStudentRoll);
                    break;
                case 8:
                    System.out.println("Exiting Program.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 8);
    }

    public Student findStudentByRoll(int roll) {
        for (Student student : registeredStudents) {
            if (student.getStudentRoll() == roll) {
                return student;
            }
        }
        return null;
    }

    public Book findBookByName(String bookName) {
        for (Book book : libraryBooks) {
            if (book.getBookName().equalsIgnoreCase(bookName)) {
                return book;
            }
        }
        return null;
    }
}
