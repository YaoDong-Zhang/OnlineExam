package online.exam.service.impl;

import online.exam.dao.TeacherInfoDao;
import online.exam.pojo.TeacherInfo;
import online.exam.service.TeacherInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @program: OnlineExam
 * @Date: 2018/10/2 10:21
 * @Author: Mr.Zhang
 * @Description:
 */
@Service
public class TeacherInfoServiceImpl implements TeacherInfoService {
    @Autowired
    private TeacherInfoDao teacherInfoDao;

    public TeacherInfo findByAccount(String teacherAccount) {
        return teacherInfoDao.findByAccount(teacherAccount);
    }

    public List<TeacherInfo> findAll(Map<String, Object> map) {
        return teacherInfoDao.findAll(map);
    }

    public int teacherSum() {
        return teacherInfoDao.teacherSum();
    }

    public TeacherInfo findById(Integer teacherId) {
        return teacherInfoDao.findById(teacherId);
    }

    public int addTeacher(TeacherInfo teacherInfo) {
        return teacherInfoDao.addTeacher(teacherInfo);
    }

    public int updateTeacher(TeacherInfo teacherInfo) {
        return teacherInfoDao.updateTeacher(teacherInfo);
    }

    public int delTeacher(Integer teacherId) {
        return teacherInfoDao.delTeacher(teacherId);
    }

    public int setTeacherWorke(TeacherInfo teacherInfo) {
        return teacherInfoDao.setTeacherWorke(teacherInfo);
    }
}
