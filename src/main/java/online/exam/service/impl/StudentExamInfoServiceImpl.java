package online.exam.service.impl;

import online.exam.dao.StudentExamInfoDao;
import online.exam.pojo.StudentExamInfo;
import online.exam.service.StudentExamInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: OnlineExam
 * @Date: 2018/10/15 11:09
 * @Author: Mr.Zhang
 * @Description:
 */
@Service
public class StudentExamInfoServiceImpl implements StudentExamInfoService {
    @Autowired
    private StudentExamInfoDao studentExamInfoDao;

    public List<StudentExamInfo> getStudentExamCountByClassId(int classId) {
        return studentExamInfoDao.getStudentExamCountByClassId(classId);
    }

    public List<StudentExamInfo> getStudentExamInfo(int studentId) {
        return studentExamInfoDao.getStudentExamInfo(studentId);
    }

    public List<StudentExamInfo> getAllStudentAvgScoreCount(int classId) {
        return studentExamInfoDao.getAllStudentAvgScoreCount(classId);
    }
}
