package sample;

public class StudentRecord {
    private String studentID;
    private String grade;
    private float assignment;
    private float midterm;
    private float finalExam;
    private double finalMark;
    private char letterGrade;

    public StudentRecord(String studentID, float assignment, float midterm, float finalExam)
    {
            this.studentID = studentID;
            this.assignment = assignment;
            this.midterm = midterm;
            this.finalExam = finalExam;
    }
    public String getStudentID()
    {
        return this.studentID;
    }
    public float getAssignment()
    {
        return assignment;
    }
    public float getMidterm()
    {
        return midterm;
    }
    public float getFinalExam()
    {
        return finalExam;
    }
    public double getFinalMark()
    {
        finalMark = (this.assignment * 0.20) + (this.midterm * 0.30) + (this.finalExam * 0.50);
        return finalMark;
    }
    public char getLetterGrade()
    {
        if(finalMark >= 80 && finalMark <= 100)
        {
            letterGrade = 'A';
        }
        else if(finalMark >= 70 && finalMark<= 79)
        {
            letterGrade = 'B';
        }
        else if(finalMark >= 60 && finalMark <= 69)
        {
            letterGrade = 'C';
        }
        else if(finalMark >= 50 && finalMark <= 59)
        {
            letterGrade = 'D';
        }
        else if(finalMark >= 0 && finalMark <= 49)
        {
            letterGrade = 'F';
        }
        return letterGrade;
    }
}
