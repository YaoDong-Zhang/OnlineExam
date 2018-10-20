package online.exam.pojo;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @program: OnlineExam
 * @Date: 2018/10/1 10:07
 * @Author: Mr.Zhang
 * @Description:教师实体类
 */

@Component
@Scope("prototype")
public class TeacherInfo {

    private Integer teacherId;//主键
    private String teacherName;//姓名
    private String teacherAccount;//账号
    private String teacherPwd;//密码
    private int adminPower;//权限1或0
    private Integer isWork;

    private ClassInfo classInfo;//学生信息

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherAccount() {
        return teacherAccount;
    }

    public void setTeacherAccount(String teacherAccount) {
        this.teacherAccount = teacherAccount;
    }

    public String getTeacherPwd() {
        return teacherPwd;
    }

    public void setTeacherPwd(String teacherPwd) {
        this.teacherPwd = teacherPwd;
    }

    public int getAdminPower() {
        return adminPower;
    }

    public void setAdminPower(int adminPower) {
        this.adminPower = adminPower;
    }

    public Integer getIsWork() {
        return isWork;
    }

    public void setIsWork(Integer isWork) {
        this.isWork = isWork;
    }

    public ClassInfo getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(ClassInfo classInfo) {
        this.classInfo = classInfo;
    }

    @Override
    public String toString() {
        return "TeacherInfo [teacherId=" + teacherId + ", teacherName="
                + teacherName + ", teacherAccount=" + teacherAccount
                + ", teacherPwd=" + teacherPwd + ", adminPower=" + adminPower
                + ", isWork=" + isWork + "]";
    }

}
