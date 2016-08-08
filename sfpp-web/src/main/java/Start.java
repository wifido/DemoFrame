import com.sf.demo.Demo;
import com.sf.demo.DemoObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/8
 */
public class Start {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:META-INF/spring/dubbo-demo-consumer.xml");
        Demo demoService = (Demo) applicationContext.getBean("demoService");
        DemoObject demoObject = new DemoObject();
        demoObject.setA(1);
        demoObject.setB("awsd");
        demoService.printObject(demoObject);
    }
}
