package com.github.huifer.ews;

import com.github.huifer.ews.entity.RuleInfoEntity;
import com.github.huifer.ews.operator.LogicalOperators;
import com.github.huifer.ews.operator.OperatorEnums;
import com.github.huifer.ews.persistence.RuleInfoEntityRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App implements ApplicationRunner {

  @Autowired
  private RuleInfoEntityRepo ruleInfoEntityRepo;
  @Override
  public void run(ApplicationArguments args) throws Exception {
//    RuleInfoEntity s = new RuleInfoEntity();
//    s.setLogicalOperator(LogicalOperators.OR);
//    s.setOperatorEnum(OperatorEnums.NEQ);
//    s.setValueExpression("abc");
//    s.setRelValue("1");
//    ruleInfoEntityRepo.save(s);
    //
    List<RuleInfoEntity> all = ruleInfoEntityRepo.findAll();
    System.out.println();
  }

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }
}
