import com.xinxing.demo.XinXingDemoApplication;
import com.xinxing.demo.rule.context.BizContext;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springblade.core.test.BladeBootTest;
import org.springblade.core.test.BladeSpringExtension;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * LiteFlow单元测试
 *
 * @author Chill
 */
@Slf4j
@ExtendWith(BladeSpringExtension.class)
@SpringBootTest(classes = XinXingDemoApplication.class)
@BladeBootTest(appName = "blade-demo", profile = "test", enableLoader = true)
public class LiteFlowTest {
	@Resource
	private FlowExecutor flowExecutor;

	@Test
	public void contextLoads() {
		// 构建上下文
		BizContext bizContext = new BizContext();
		bizContext.setId(1L);
		bizContext.setName("测试名称");
		bizContext.setCategory(1);
		bizContext.setIsPublish(Boolean.TRUE);
		// 启动 demoChain 的规则引擎
		LiteflowResponse resp = flowExecutor.execute2Resp("demoChain", null, bizContext);
		if (resp.isSuccess()) {
			BizContext contextBean = resp.getContextBean(BizContext.class);
			log.info(JsonUtil.toJson(contextBean));
		}
	}

}
