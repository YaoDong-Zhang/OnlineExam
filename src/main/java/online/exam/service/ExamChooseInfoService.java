package online.exam.service;

import online.exam.pojo.ExamChooseInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @program: OnlineExam
 * @Date: 2018/10/13 20:16
 * @Author: Mr.Zhang
 * @Description:
 */
@Repository
public interface ExamChooseInfoService {
    public List<ExamChooseInfo> getChooseInfoWithExamSubject(Map<String, Object> map);

    public List<ExamChooseInfo> getChooseInfoWithSumScore(Map<String, Object> map);

    public ExamChooseInfo getChooseWithIds(Map<String, Object> map);

    public int updateChooseWithIds(ExamChooseInfo examChoose);

    public int addChoose(Map<String, Object> map);

}
