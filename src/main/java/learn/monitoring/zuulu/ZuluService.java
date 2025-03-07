package learn.monitoring.zuulu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ZuluService {

    @Autowired
    private ZuluClient client;

    @Autowired
    private ZuluPortfolioRepository portfolioRepository;

    public List<ZuluPosition> scanPositions(String id) {
        List<ZuluPosition> res = client.getOpenPositions(id).stream().map(zp -> {
            zp.setId(id + ":" + zp.getStdLotds().toString().replaceAll("\\.", "") + ":" + zp.getDateTime().getTime());
            return zp;
        }).collect(Collectors.toList());
        return res;
    }


    public void addPortfolio(ZuluPortfolio p) {
        portfolioRepository.save(p);
    }

    public void deletePortfolio(ZuluPortfolio p) {
        portfolioRepository.delete(p.getId());
    }

    public List<ZuluPortfolio> getPortfolios() {
        return portfolioRepository.findAll();
    }

    public void setFactor(String id, Double factor) {
        ZuluPortfolio zp = portfolioRepository.findOne(id);
        zp.setFactor(factor);
        portfolioRepository.save(zp);
    }
}
