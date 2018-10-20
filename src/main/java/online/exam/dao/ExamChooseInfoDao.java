package online.exam.dao;

import online.exam.pojo.ExamChooseInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @program: OnlineExam
 * @Date: 2018/10/13 20:15
 * @Author: Mr.Zhang
 * @Description:选择答案功能类
 */
@Repository
public interface ExamChooseInfoDao {
    public List<ExamChooseInfo> getChooseInfoWithExamSubject(Map<String, Object> map);

    public List<ExamChooseInfo> getChooseInfoWithSumScore(Map<String, Object> map);

    public ExamChooseInfo getChooseWithIds(Map<String, Object> map);
    //修改选择答案
    public int updateChooseWithIds(ExamChooseInfo examChoose);

    public int addChoose(Map<String, Object> map);
}
