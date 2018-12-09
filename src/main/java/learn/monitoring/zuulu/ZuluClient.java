package learn.monitoring.zuulu;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@FeignClient(name = "zulu-client", url = "https://www.zulutrade.com/zulutrade-client/trading/api/providers/")
public interface ZuluClient {

    @ResponseBody
    @RequestMapping(value = "/{trader}/trades/open/all",  method = RequestMethod.GET)
    List<ZuluPosition> getOpenPositions(@RequestParam("trader") String trader);
}
