package online.exam.dao;

import online.exam.pojo.StudentInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @program: OnlineExam
 * @Date: 2018/10/1 16:04
 * @Author: Mr.Zhang
 * @Description:
 */
@Repository
public interface StudentInfoDao {
    //登录
    public StudentInfo findByAccount(String studentAccount);

    //注册
    public int insert(StudentInfo studentInfo);

    public StudentInfo findByID(Integer studentId);

    //修改密码
    public int resetPwd(StudentInfo studentInfo);

    public List<StudentInfo> findAll(Map<String, Object> map);

    public int stuSum();

    public int updateStu(StudentInfo studentInfo);

    public int delStu(Integer studentId);

    public List<StudentInfo> findByClassId(Integer classId);

}
