package online.exam.dao;

import online.exam.pojo.StudentExamInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: OnlineExam
 * @Date: 2018/10/15 11:08
 * @Author: Mr.Zhang
 * @Description:
 */
@Repository
public interface StudentExamInfoDao {
    //后台教师根据查看某一班级下所有学生考试数量
    public List<StudentExamInfo> getStudentExamCountByClassId(int classId);

    //后台教师查看某一学生的考试信息
    public List<StudentExamInfo> getStudentExamInfo(int studentId);

    public List<StudentExamInfo> getAllStudentAvgScoreCount(int classId);
}
