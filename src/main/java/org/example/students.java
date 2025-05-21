package org.example;
public class students{
    private String student_firstname;
    private String student_lastname;
    private String student_email;
    private String date_of_birth;


    public students(String student_firstname, String student_lastname, String student_email, String date_of_birth) {
        this.student_firstname = student_firstname;
        this.student_lastname = student_lastname;
        this.student_email = student_email;
        this.date_of_birth = date_of_birth;
    }




    public String getstudent_firstname() {
        return student_firstname;

    }
    public void setstudent_firstname(String student_firstname) {
        this.student_firstname = student_firstname;
    }


    public String getstudent_lastname() {
        return student_lastname;
    }

    public void setStudent_lastname(String student_lastname) {
        this.student_lastname = student_lastname;
    }

    public String getStudent_email() {
        return student_email;
    }

    public void setStudent_email(String student_email) {
        this.student_email = student_email;
    }


    public String getDate_of_birth() {
        return date_of_birth;
    }
    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }


}
