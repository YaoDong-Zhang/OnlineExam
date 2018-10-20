package online.exam.dao;

import online.exam.pojo.TeacherInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @program: OnlineExam
 * @Date: 2018/10/2 10:19
 * @Author: Mr.Zhang
 * @Description:
 */
@Repository
public interface TeacherInfoDao {
    public TeacherInfo findByAccount(String teacherAccount);

    public List<TeacherInfo> findAll(Map<String, Object> map);

    public int teacherSum();

    public TeacherInfo findById(Integer teacherId);

    public int addTeacher(TeacherInfo teacherInfo);

    public int updateTeacher(TeacherInfo teacherInfo);

    public int delTeacher(Integer teacherId);

    public int setTeacherWorke(TeacherInfo teacherInfo);

}
