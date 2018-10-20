package online.exam.service;

import online.exam.pojo.StudentExamInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: OnlineExam
 * @Date: 2018/10/15 11:09
 * @Author: Mr.Zhang
 * @Description:
 */
@Repository
public interface StudentExamInfoService {
    public List<StudentExamInfo> getStudentExamCountByClassId(int classId);

    public List<StudentExamInfo> getStudentExamInfo(int studentId);

    public List<StudentExamInfo> getAllStudentAvgScoreCount(int classId);
}
