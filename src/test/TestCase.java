import online.exam.dao.ClassInfoDao;
import online.exam.dao.ExamHistoryInfoDao;
import online.exam.dao.StudentInfoDao;
import online.exam.pojo.ClassInfo;
import online.exam.pojo.StudentInfo;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: OnlineExam
 * @Date: 2018/9/30 20:14
 * @Author: Mr.Zhang
 * @Description:
 */
public class TestCase {
    //测试spring.xml文件配置是否正确
    @Test
    public void test1() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
        System.out.println(ac.getBean("gson"));
    }

    //测试studentinfo查询功能
    @Test
    public void test2() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
        StudentInfoDao studentinfoDao = (StudentInfoDao) ac.getBean("a");
        System.out.println(studentinfoDao.findByAccount("lanni"));
    }

    //测试studentinfo修改密码
    @Test
    public void test3() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
        StudentInfoDao studentinfoDao = (StudentInfoDao) ac.getBean("a");
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setStudentPwd("222");
        studentInfo.setStudentId(185);
        System.out.println(studentinfoDao.resetPwd(studentInfo));
    }

    @Test
    public void test4() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
        ClassInfoDao classInfoDao = (ClassInfoDao) ac.getBean("a");
        ClassInfo classInfo = classInfoDao.findByTeacherId(20);
        System.out.println(classInfo);
        if (classInfo != null) {
            classInfoDao.setTeacher(classInfo.getClassId());
        }
    }

    @Test
    public void test5() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
        ExamHistoryInfoDao examHistoryInfoDao = (ExamHistoryInfoDao) ac.getBean("a");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("studentId", 1);
        map.put("examPaperId", 2);
        int a = examHistoryInfoDao.findHistoryInfoWithIds(map);
        System.out.println(a);
    }
}
