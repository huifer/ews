# 实体与服务

- 规则说明表

| 规则ID | 规则名称       |
| ------ | -------------- |
| 1      | 发送邮件的规则 |
| 2      | XXX的规则      |
| 3      | xxx的规则      |

- 规则详情

| 检测值表达式 | 运算符 | 匹配值 | 逻辑运算符 |
| ------------ | ------ | ------ | ---------- |
| is_new       | ==     | true   | 并且       |
| age          | >      | 10     | 或者       |
| age          | <      | 45     |            |

- 事件（行为）表

| 规则ID | 行为接口地址 | 接口参数 |
| ------ | ------------ | -------- |
| 1      | 注册         | a        |
| 1      | 积分         |          |
|        |              |          |

- 对象转换表

| source   | target        |      |
| -------- | ------------- | ---- |
| age      | event['age']  |      |
| age      | age           |      |
| age      | event['aaaa'] |      |
| age      | array[0]      |      |
| username | array[1]      |      |
|          |               |      |
|          |               |      |



```json
{
    username:"aaa",
    age:"123",
    is_new:true,
    email:"aaa@qq.com"
}
```

```java
if (is_new == true && age > 10 || ange <45){
    
    // 发邮件
}
```

```json
{
    "age":123,
    "event":{
        "age":123,
        "aaa":123
    },
    "array":[123,"aaa"]
}
```





Role