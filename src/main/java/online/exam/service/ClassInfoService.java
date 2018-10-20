package online.exam.service;

import online.exam.pojo.ClassInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @program: OnlineExam
 * @Date: 2018/10/6 16:16
 * @Author: Mr.Zhang
 * @Description:
 */
@Repository
public interface ClassInfoService {
    public List<ClassInfo> findAll(ClassInfo classInfo);

    public List<ClassInfo> findByGradeId(Integer id);

    public ClassInfo findById(Integer id);

    public int addClass(ClassInfo classInfo);

    public int updateClass(ClassInfo classInfo);

    public int delClass(Integer id);

    public ClassInfo findByTeacherId(Integer id);

    public int setTeacher(Integer id);

    public Map<String, Object> getStudentCountForClass(Integer gradeId);
}
