package online.exam.service.impl;

import online.exam.dao.ClassInfoDao;
import online.exam.pojo.ClassInfo;
import online.exam.service.ClassInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @program: OnlineExam
 * @Date: 2018/10/6 16:16
 * @Author: Mr.Zhang
 * @Description:
 */
@Service
public class ClassInfoServiceImpl implements ClassInfoService {
    @Autowired
    private ClassInfoDao classInfoDao;

    public List<ClassInfo> findAll(ClassInfo classInfo) {
        return classInfoDao.findAll(classInfo);
    }

    public List<ClassInfo> findByGradeId(Integer id) {
        return classInfoDao.findByGradeId(id);
    }

    public ClassInfo findById(Integer id) {
        return classInfoDao.findById(id);
    }

    public int addClass(ClassInfo classInfo) {
        return classInfoDao.addClass(classInfo);
    }

    public int updateClass(ClassInfo classInfo) {
        return classInfoDao.updateClass(classInfo);
    }

    public int delClass(Integer id) {
        return classInfoDao.delClass(id);
    }

    public ClassInfo findByTeacherId(Integer id) {
        return classInfoDao.findByTeacherId(id);
    }

    public int setTeacher(Integer id) {
        return classInfoDao.setTeacher(id);
    }

    public Map<String, Object> getStudentCountForClass(Integer gradeId) {
        return classInfoDao.getStudentCountForClass(gradeId);
    }
}
