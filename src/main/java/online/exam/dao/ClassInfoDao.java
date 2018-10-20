package online.exam.dao;

import online.exam.pojo.ClassInfo;
import org.apache.ibatis.annotations.MapKey;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @program: OnlineExam
 * @Date: 2018/10/6 16:14
 * @Author: Mr.Zhang
 * @Description:
 */
@Repository
public interface ClassInfoDao {
    //查找所有班级信息
    public List<ClassInfo> findAll(ClassInfo classInfo);
    //通过年级ID查找班级信息
    public List<ClassInfo> findByGradeId(Integer id);
    //通过ID查找班级信息
    public ClassInfo findById(Integer id);

    public int addClass(ClassInfo classInfo);

    public int updateClass(ClassInfo classInfo);

    public int delClass(Integer id);

    public ClassInfo findByTeacherId(Integer id);

    public int setTeacher(Integer id);

    //获取各(指定年级下)班级下的学生总量
    //指定某一列的值作为 Map 的键
    @MapKey("className")
    public Map<String, Object> getStudentCountForClass(Integer gradeId);
}
