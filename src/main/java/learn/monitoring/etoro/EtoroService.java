package learn.monitoring.etoro;

import learn.user.history.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class EtoroService {

    @Autowired
    WebDriver driver;

    @Autowired
    EtoroPortfolioRepository portfolioRepository;

    @Autowired
    HistoryService historyService;

    public List<EtoroPosition> scanPositions(String traderId) {
        driver.navigate().to(String.format("https://www.etoro.com/sapi/trade-data-real/live/public/portfolios?cid=%s&format=json", traderId));

        String pageSrc = driver.getPageSource();
        log.info("Portfolio: " + pageSrc);
        JSONObject res = new JSONObject( Jsoup.parse(pageSrc).body().text());
        JSONArray groups = res.getJSONArray("AggregatedPositions");
        List<EtoroPosition> newPositions = new ArrayList<>();
        groups.forEach(g -> {
            JSONObject groupObj = (JSONObject)g;
            String instId = "" + groupObj.get("InstrumentID");
            driver.navigate().to(String.format("https://www.etoro.com/sapi/trade-data-real/live/public/positions?InstrumentID=%s&cid=%s&format=json", instId, traderId));
            JSONObject posJson = new JSONObject( Jsoup.parse( driver.getPageSource()).body().text());
            JSONArray posArray = posJson.getJSONArray("PublicPositions");
            posArray.forEach(pos -> {
                EtoroPosition posObj = new EtoroPosition();

                posObj.setPosId(((JSONObject)pos).get("CID") + ":" + ((JSONObject)pos).get("PositionID"));
                posObj.setInstrumentId("" + ((JSONObject)pos).get("InstrumentID"));
                posObj.setAmmount(new BigDecimal(String.valueOf(((JSONObject)pos).get("Amount"))));
                String type = ("" +((JSONObject)pos).get("IsBuy")).equals("true") ? "buy" : "sell";
                posObj.setType(type);
                posObj.setLeverage(String.valueOf(((JSONObject)pos).get("Leverage")));

                String s=String.valueOf(((JSONObject)pos).get("OpenDateTime"));

                TimeZone tz = TimeZone.getDefault();
                Calendar cal = Calendar.getInstance(tz);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                sdf.setCalendar(cal);
                try {
                    cal.setTime(sdf.parse(s));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date date = cal.getTime();
                posObj.setOpenTime(date);
                newPositions.add(posObj);
                // posObj.setAmmount(((JSONObject)pos).getString("id"));
            });

        });
        return newPositions;
    }
}
