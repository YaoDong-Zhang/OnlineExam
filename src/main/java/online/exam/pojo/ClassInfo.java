package online.exam.pojo;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @program: OnlineExam
 * @Date: 2018/10/1 10:07
 * @Author: Mr.Zhang
 * @Description:班级实体类
 */

@Component
@Scope("prototype")
public class ClassInfo {

    private Integer classId;
    private String className;
    private GradeInfo grade;
    private TeacherInfo teacher;

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public GradeInfo getGrade() {
        return grade;
    }

    public void setGrade(GradeInfo grade) {
        this.grade = grade;
    }

    public TeacherInfo getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherInfo teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "ClassInfo{" +
                "classId=" + classId +
                ", className='" + className + '\'' +
                ", grade=" + grade +
                ", teacher=" + teacher +
                '}';
    }
}
