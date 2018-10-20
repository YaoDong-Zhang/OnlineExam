package online.exam.service.impl;

import online.exam.dao.StudentInfoDao;
import online.exam.pojo.StudentInfo;
import online.exam.service.StudentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @program: OnlineExam
 * @Date: 2018/10/1 17:00
 * @Author: Mr.Zhang
 * @Description:
 */
@Service
public class StudentInfoServiceImpl implements StudentInfoService {
    @Autowired
    private StudentInfoDao studentInfoDao;

    public StudentInfo findByAccount(String stuAccount) {
        return studentInfoDao.findByAccount(stuAccount);
    }

    public int regist(StudentInfo studentInfo) {
        return studentInfoDao.insert(studentInfo);
    }

    public StudentInfo findByID(Integer studentId) {
        return studentInfoDao.findByID(studentId);
    }

    public int resetPwd(StudentInfo studentInfo) {
        return studentInfoDao.resetPwd(studentInfo);
    }

    public List<StudentInfo> findAll(Map<String, Object> map) {
        return studentInfoDao.findAll(map);
    }

    public int stuSum() {
        return studentInfoDao.stuSum();
    }

    public int updateStu(StudentInfo studentInfo) {
        return studentInfoDao.updateStu(studentInfo);
    }

    public int delStu(Integer studentId) {
        return studentInfoDao.delStu(studentId);
    }

    public List<StudentInfo> findByClassId(Integer classId) {
        return studentInfoDao.findByClassId(classId);
    }
}
