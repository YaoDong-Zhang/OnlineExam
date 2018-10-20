package online.exam.service;

import online.exam.pojo.StudentInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @program: OnlineExam
 * @Date: 2018/10/1 16:58
 * @Author: Mr.Zhang
 * @Description:
 */
@Repository
public interface StudentInfoService {
    public StudentInfo findByAccount(String stuAccount);

    public int regist(StudentInfo studentInfo);

    public StudentInfo findByID(Integer studentId);

    public int resetPwd(StudentInfo studentInfo);

    public List<StudentInfo> findAll(Map<String, Object> map);

    public int stuSum();

    public int updateStu(StudentInfo studentInfo);

    public int delStu(Integer studentId);

    public List<StudentInfo> findByClassId(Integer classId);

}
