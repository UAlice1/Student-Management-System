package org.example;

public class Mark {
    private int studentId;
    private int courseId;
    private float marks;

    public Mark(int studentId, int courseId, float marks) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.marks = marks;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public float getMarks() {
        return marks;
    }

    public void setMarks(float marks) {
        this.marks = marks;
    }
}
