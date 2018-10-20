package online.exam.pojo;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @program: OnlineExam
 * @Date: 2018/10/1 10:07
 * @Author: Mr.Zhang
 * @Description:年级实体类
 */

@Component
@Scope("prototype")
public class GradeInfo {

    private Integer gradeId;
    private String gradeName;

    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public GradeInfo(Integer gradeId) {
        super();
        this.gradeId = gradeId;
    }

    public GradeInfo() {
        super();
    }

    @Override
    public String toString() {
        return "GradeInfo [gradeId=" + gradeId + ", gradeName=" + gradeName
                + "]";
    }

}
